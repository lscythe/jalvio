// swift-tools-version: 5.9
import PackageDescription
let package = Package(
  name: "KotlinMultiplatformLinkedPackage",
  platforms: [
    .iOS("15.0")
  ],
  products: [
    .library(
      name: "KotlinMultiplatformLinkedPackage",
      type: .none,
      targets: ["KotlinMultiplatformLinkedPackage"]
    )
  ],
  dependencies: [
    .package(path: "subpackages/_core_analytics-firebase"),
    .package(path: "subpackages/_core_monitoring-sentry"),
    .package(path: "subpackages/dev_gitlive_firebase_analytics_3_0_0_alpha02"),
    .package(path: "subpackages/dev_gitlive_firebase_app_3_0_0_alpha02")
  ],
  targets: [
    .target(
      name: "KotlinMultiplatformLinkedPackage",
      dependencies: [
        .product(name: "_core_analytics-firebase", package: "_core_analytics-firebase"),
        .product(name: "_core_monitoring-sentry", package: "_core_monitoring-sentry"),
        .product(name: "dev_gitlive_firebase_analytics_3_0_0_alpha02", package: "dev_gitlive_firebase_analytics_3_0_0_alpha02"),
        .product(name: "dev_gitlive_firebase_app_3_0_0_alpha02", package: "dev_gitlive_firebase_app_3_0_0_alpha02")
      ]
    )
  ]
)
