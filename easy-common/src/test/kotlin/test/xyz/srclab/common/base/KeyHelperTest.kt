package test.xyz.srclab.common.base

import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import test.xyz.srclab.common.doAssert
import xyz.srclab.common.base.KeyHelper

object KeyHelperTest {

    @Test(dataProvider = "keyHelperDataProvider")
    fun testKeyHelper(actual: Any, expected: Any) {
        doAssert(actual, expected)
    }

    @DataProvider
    fun keyHelperDataProvider(): Array<Array<*>> {
        return arrayOf(
            arrayOf(KeyHelper.buildKey(Object::class.java, "ss"), ":Ljava/lang/Object;:ss")
        )
    }
}