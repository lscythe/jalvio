# Git workflow

The contributor-facing rules (setup, commit format, scopes, pull request checklist) are in [CONTRIBUTING](../../.github/CONTRIBUTING.md). This guide adds the branch table, hooks, CI checks and the release flow.

## Branches

| Branch | From | Merges into | Merge method |
|---|---|---|---|
| `feature/<short-description>` | `develop` | `develop` | squash |
| `fix/<short-description>` | `develop` | `develop` | squash |
| `release/<version>` | `develop` | `main`, then back into `develop` | merge commit |
| `hotfix/<version>` | `main` | `main`, then back into `develop` | merge commit |

`develop` and `main` are protected: no deletion, no force push, pull requests only, and the `Android` CI check must pass. Merged branches are deleted automatically.

## Commits

```text
<type>(<scope>): <imperative summary, lowercase, no trailing period>

<optional body explaining why>
```

- At most 72 characters in the header.
- Types: `feat`, `fix`, `refactor`, `perf`, `test`, `docs`, `build`, `ci`, `chore`, `revert`.
- Scope: a module path without the leading colon (`core:network`, `app:android`), a top-level directory (`core`, `app`), or an area (`build`, `build-logic`, `ci`, `deps`, `tooling`, `docs`, `release`). `ci` and `docs` may omit it.
- The body explains why, when the header cannot.

On an unpushed branch, fix-ups are amended into the commit they fix instead of piling up as separate commits. Once a branch is pushed and shared, add new commits instead of rewriting it.

## Hooks

`hk install --mise` installs the hooks from `hk.pkl`:

| Hook | Runs |
|---|---|
| `pre-commit` | kempt on staged files, and the translation parity check when `core/resources` strings change |
| `commit-msg` | `.github/scripts/commit_scope.py` |
| `pre-push` | `checkModuleBoundaries` and `mise run test` |

## Pull requests

- One complete feature per pull request: logic, contract, wiring and tests together. Unrelated changes go in their own pull request.
- The title is a valid commit header, because it becomes the squash commit on `develop`.
- CI runs on every pull request:

  | Check | Runs | Required |
  |---|---|---|
  | Android | `mise run check`, `mise run test`, `mise run build`, Kover to Codecov | yes |
  | iOS | `mise run test-ios`, `mise run build-ios` | not yet |
  | CodeQL | Kotlin and Java analysis of `assembleDebug` | no |
  | Dependency review | new dependencies with known vulnerabilities or denied licenses | no |

- Dependabot opens weekly `build(deps)` and `ci(deps)` pull requests against `develop`. Shorten the title before squashing if it is over 72 characters.

## Releases

1. Branch `release/<version>` from `develop`.
2. Bump the version, update store listings and fix only release blockers on the branch.
3. Open a pull request into `main` and merge it with a merge commit.
4. Tag the merge commit `v<version>`; Play Store, GitHub releases and F-Droid build from the tag.
5. Merge `main` back into `develop`.

Beta and release candidate builds use Play Console testing tracks and GitHub pre-releases, not separate flavors. Hotfixes follow the same steps from a `hotfix/<version>` branch cut from `main`.
