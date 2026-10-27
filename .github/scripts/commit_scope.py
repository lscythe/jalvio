import re
import sys
from pathlib import Path

AREA_SCOPES = {"build", "build-logic", "ci", "deps", "tooling", "docs", "release"}
TYPES_WITHOUT_REQUIRED_SCOPE = {"ci", "docs"}
GIT_GENERATED_PREFIXES = ("Merge ", "Revert ", "fixup! ", "squash! ")
HEADER = re.compile(r"^(?P<type>\w+)(?:\((?P<scope>[^)]*)\))?!?: ")
MODULE_PATH = re.compile(r'"(:[\w-]+(?::[\w-]+)*)"')


def allowed_scopes(settings_text: str) -> set[str]:
    scopes = set(AREA_SCOPES)
    for path in MODULE_PATH.findall(settings_text):
        segments = path.strip(":").split(":")
        for depth in range(2, len(segments) + 1):
            scopes.add(":".join(segments[:depth]))
    return scopes


def check_message(message: str, scopes: set[str]) -> str | None:
    if message.startswith(GIT_GENERATED_PREFIXES):
        return None
    header = HEADER.match(message)
    if header is None:
        return f"Commit message must look like 'type(scope): subject', got: {message!r}"
    scope = header.group("scope")
    if scope is None:
        if header.group("type") in TYPES_WITHOUT_REQUIRED_SCOPE:
            return None
        return f"Commit type '{header.group('type')}' requires a scope. Allowed scopes: {', '.join(sorted(scopes))}"
    if scope not in scopes:
        return f"Unknown commit scope '{scope}'. Allowed scopes: {', '.join(sorted(scopes))}"
    return None


def first_message_line(path: str) -> str:
    for line in Path(path).read_text().splitlines():
        if line.strip() and not line.startswith("#"):
            return line
    return ""


def main(argv: list[str]) -> int:
    settings = Path(__file__).resolve().parents[2] / "settings.gradle.kts"
    error = check_message(first_message_line(argv[1]), allowed_scopes(settings.read_text()))
    if error:
        print(error, file=sys.stderr)
        return 1
    return 0


if __name__ == "__main__":
    sys.exit(main(sys.argv))
