import BackgroundTasks
import JalvioApp
import SwiftUI

private let syncTaskIdentifier = "dev.lscythe.app.jalvio.sync"

@main
struct iOSApp: App {
    init() {
        BGTaskScheduler.shared.register(forTaskWithIdentifier: syncTaskIdentifier, using: nil) { task in
            guard let refresh = task as? BGAppRefreshTask else { return }
            handleSync(refresh)
        }
        JalvioIos.shared.start()
        scheduleSync()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

private func scheduleSync() {
    let request = BGAppRefreshTaskRequest(identifier: syncTaskIdentifier)
    request.earliestBeginDate = Date(timeIntervalSinceNow: 24 * 60 * 60)
    try? BGTaskScheduler.shared.submit(request)
}

private func handleSync(_ task: BGAppRefreshTask) {
    scheduleSync()
    let completion = SyncTaskCompletion(task)
    let sync = Task {
        let succeeded = (try? await JalvioIos.shared.backgroundSync())?.boolValue ?? false
        completion.finish(success: succeeded)
    }
    task.expirationHandler = {
        sync.cancel()
        completion.finish(success: false)
    }
}

private final class SyncTaskCompletion: @unchecked Sendable {
    private let task: BGTask
    private let lock = NSLock()
    private var finished = false

    init(_ task: BGTask) {
        self.task = task
    }

    func finish(success: Bool) {
        lock.lock()
        defer { lock.unlock() }
        guard !finished else { return }
        finished = true
        task.setTaskCompleted(success: success)
    }
}
