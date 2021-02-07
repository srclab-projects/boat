package xyz.srclab.common.run

import java.util.concurrent.Executors

/**
 * A type of [Runner] always use current thread.
 */
object AsyncRunner : Runner by asyncRunner

private val asyncRunner = Runner.newExecutorServiceRunner(Executors.newCachedThreadPool())