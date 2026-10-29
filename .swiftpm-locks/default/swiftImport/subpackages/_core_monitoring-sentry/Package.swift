// swift-tools-version: 5.9
import PackageDescription
let package = Package(
  name: "_core_monitoring-sentry",
  platforms: [
    .iOS("15.0")
  ],
  products: [
    .library(
      name: "_core_monitoring-sentry",
      type: .none,
      targets: ["_core_monitoring-sentry"]
    )
  ],
  dependencies: [
    .package(
      url: "https://github.com/getsentry/sentry-cocoa.git",
      exact: "9.30.2"
    )
  ],
  targets: [
    .target(
      name: "_core_monitoring-sentry",
      dependencies: [
        .product(
          name: "Sentry",
          package: "sentry-cocoa",
          condition: .when(platforms: [.iOS])
        )
      ]
    )
  ]
)
