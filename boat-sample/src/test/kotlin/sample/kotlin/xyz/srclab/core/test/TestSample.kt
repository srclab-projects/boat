package sample.kotlin.xyz.srclab.core.test

import org.testng.annotations.Test
import xyz.srclab.common.test.TestLogger
import xyz.srclab.common.test.TestTask
import xyz.srclab.common.test.testTasks

class TestSample {

    @Test
    fun testTests() {
        testTasks(
            listOf(
                TestTask.newTask { logger.log("Run test task!") }
            )
        )
    }

    companion object {
        private val logger = TestLogger.DEFAULT
    }
}