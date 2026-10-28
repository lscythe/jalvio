import tempfile
import unittest
from pathlib import Path
from unittest import mock

from commit_scope import allowed_scopes, check_message, first_message_line

SETTINGS = 'include(":core:network", ":core:common", ":feature:home:api", ":feature:home:impl", ":app:android")'


class AllowedScopesTest(unittest.TestCase):
    def test_module_paths_and_groups_are_allowed(self):
        scopes = allowed_scopes(SETTINGS)
        for s in [
            "core:network",
            "feature:home:impl",
            "feature:home",
            "app:android",
            "build",
            "build-logic",
            "ci",
            "deps",
            "tooling",
            "docs",
            "release",
        ]:
            self.assertIn(s, scopes)

    def test_single_segment_groups_are_not_allowed(self):
        self.assertNotIn("feature", allowed_scopes(SETTINGS))


class CheckMessageTest(unittest.TestCase):
    def test_valid_scope_passes(self):
        self.assertIsNone(check_message("feat(core:network): add problem decoding", allowed_scopes(SETTINGS)))

    def test_breaking_marker_passes(self):
        self.assertIsNone(check_message("feat(core:network)!: drop v0 client", allowed_scopes(SETTINGS)))

    def test_unknown_scope_fails_naming_it(self):
        self.assertIn("core:netwrk", check_message("fix(core:netwrk): typo", allowed_scopes(SETTINGS)))

    def test_missing_scope_fails(self):
        self.assertIsNotNone(check_message("fix: something", allowed_scopes(SETTINGS)))

    def test_ci_and_docs_may_omit_scope(self):
        for m in ["ci: add workflows", "docs: add adrs"]:
            self.assertIsNone(check_message(m, allowed_scopes(SETTINGS)))

    def test_build_logic_scope_passes(self):
        self.assertIsNone(check_message("build(build-logic): add convention plugins", allowed_scopes(SETTINGS)))

    def test_git_generated_messages_pass(self):
        for m in [
            "Merge branch 'develop' into feature/x",
            'Revert "feat(core:network): x"',
            "fixup! feat(core:network): x",
            "squash! feat(core:network): x",
        ]:
            self.assertIsNone(check_message(m, allowed_scopes(SETTINGS)))

    def test_amend_prefix_passes(self):
        self.assertIsNone(check_message("amend! feat(core:common): x", allowed_scopes(SETTINGS)))

    def test_colon_scope_passes(self):
        self.assertIsNone(check_message("feat(core:common): add dispatchers", allowed_scopes(SETTINGS)))

    def test_colon_scope_with_breaking_marker_passes(self):
        self.assertIsNone(check_message("feat(feature:home:impl)!: drop legacy entry", allowed_scopes(SETTINGS)))

    def test_unknown_type_fails_naming_it(self):
        self.assertIn("feature", check_message("feature(core:common): x", allowed_scopes(SETTINGS)))

    def test_uppercase_summary_fails(self):
        self.assertIsNotNone(check_message("feat(core:common): Add dispatchers", allowed_scopes(SETTINGS)))

    def test_trailing_period_fails(self):
        self.assertIsNotNone(check_message("feat(core:common): add dispatchers.", allowed_scopes(SETTINGS)))

    def test_missing_space_after_colon_fails(self):
        self.assertIsNotNone(check_message("feat(core:common):add dispatchers", allowed_scopes(SETTINGS)))

    def test_empty_summary_fails(self):
        self.assertIsNotNone(check_message("feat(core:common): ", allowed_scopes(SETTINGS)))

    def test_header_length_limit_is_72(self):
        prefix = "feat(core:common): "
        at_limit = prefix + "a" * (72 - len(prefix))
        self.assertEqual(72, len(at_limit))
        self.assertIsNone(check_message(at_limit, allowed_scopes(SETTINGS)))
        self.assertIsNotNone(check_message(at_limit + "a", allowed_scopes(SETTINGS)))


class FirstMessageLineTest(unittest.TestCase):
    def test_file_is_read_as_utf_8(self):
        with tempfile.TemporaryDirectory() as directory:
            path = Path(directory) / "COMMIT_EDITMSG"
            path.write_text("# café comment\nfeat(core:common): add dispatchers\n", encoding="utf-8")
            with mock.patch("io.text_encoding", side_effect=lambda encoding, stacklevel=2: encoding or "ascii"):
                self.assertEqual("feat(core:common): add dispatchers", first_message_line(str(path)))


if __name__ == "__main__":
    unittest.main()
