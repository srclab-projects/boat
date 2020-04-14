package test.xyz.srclab.common.lang

import org.testng.annotations.Test
import xyz.srclab.common.lang.Ref
import xyz.srclab.test.doAssertEquals

/**
 * @author sunqian
 */
object RefTest {

    @Test
    fun testRef() {
        val ref = Ref.withEmpty<Int>()
        doAssertEquals(ref.get(), null)
        ref.set(1)
        doAssertEquals(ref.get(), 1)

        val ref2 = Ref.with("1")
        doAssertEquals(ref.get(), 1)
    }
}