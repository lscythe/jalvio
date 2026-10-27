import unittest

from commit_scope import allowed_scopes, check_message

SETTINGS = 'include(":core:network", ":feature:home:api", ":feature:home:impl", ":app:android")'


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


if __name__ == "__main__":
    unittest.main()
