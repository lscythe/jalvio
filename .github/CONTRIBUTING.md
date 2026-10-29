# Contributing to Jalvio

Thank you for contributing. Keep changes focused, tested and easy to review.

## Before you start

1. Search existing issues and pull requests.
2. For a new feature or an architectural change, open an issue first so the approach can be agreed.
3. Run `mise install` and `hk install --mise` so the git hooks run locally.

## Branch flow

```text
feature/*, fix/*  →  develop  →  release/*  →  main
hotfix/*          →  main
```

| Branch | Created from | Merged into | Merge method |
|---|---|---|---|
| `feature/*` | `develop` | `develop` | Squash |
| `fix/*` | `develop` | `develop` | Squash |
| `release/*` | `develop` | `main`, then back into `develop` | Merge commit into `main` |
| `hotfix/*` | `main` | `main`, then back into `develop` | Merge commit into `main` |

`develop` and `main` are protected: changes go through pull requests, and force pushes and deletions are blocked. Merged branches are deleted automatically.

## Commit messages

Commits follow [Conventional Commits](https://www.conventionalcommits.org) and are checked by the `commit-msg` hook:

```text
type(scope): summary
```

- **Types:** `feat`, `fix`, `refactor`, `perf`, `test`, `docs`, `build`, `ci`, `chore`, `revert`. Add `!` after the scope for a breaking change.
- **Scope:** a Gradle module path such as `core:common` or `feature:home:impl`, a module group such as `feature:home` or `core` (for a change across a whole directory), or one of the areas `build`, `build-logic`, `ci`, `deps`, `tooling`, `docs`, `release`. The scope is optional for `ci` and `docs`.
- **Summary:** imperative, starts with a lowercase letter, no trailing period. The whole header is at most 72 characters.

Examples:

```text
feat(core:common): add dispatchers and geo point
fix(data:catalog): keep the previous catalog when a download fails
docs: describe the release flow
```

## Pull requests

- Use the pull request template.
- The pull request title must be a valid commit header: it becomes the squash commit on `develop`.
- One pull request is one complete change: logic, wiring and tests together. Keep unrelated refactors out.
- Explain architectural changes in the pull request description, and update `docs/conventions` when a rule changes.
- Resolve all review conversations before merging.

## Code style

- Kotlin is formatted by kempt (ktfmt, Kotlin style) on commit. Run `mise run format` to format manually.
- License headers are added by kempt. Do not write them by hand.
- Prefer clear names and small functions over comments. Comment only on a non-obvious reason.
- Write tests first. Tests use Kotest `BehaviorSpec` unless a platform runner requires otherwise.

## Local verification

```sh
mise run check
mise run test
```

With Xcode installed, also run `mise run test-ios` and `mise run build-ios`.

The `pre-push` hook runs the module boundary check and `mise run test`. On every pull request CI runs:

- **Android**: `mise run check`, `mise run test`, `mise run build` and Kover coverage uploaded to Codecov
- **iOS**: `mise run test-ios` and `mise run build-ios` on macOS
- **CodeQL** for Kotlin and Java, and **Dependency review** for new dependencies

The Android job must pass before a pull request can merge. The iOS job runs on every pull request but is not required yet.

## Secrets

Never commit signing keys, tokens or personal data. Endpoints in `config/endpoints.properties` are public configuration; anything private belongs in `local.properties` or CI secrets.
