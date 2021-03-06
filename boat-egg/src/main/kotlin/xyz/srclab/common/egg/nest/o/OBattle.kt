package xyz.srclab.common.egg.nest.o

import xyz.srclab.common.Boat
import xyz.srclab.common.egg.Egg

/**
 * @author sunqian
 */
class OBattle : Egg {

    override val shell: String = Boat::class.java.name

    override fun hatchOut(spell: CharSequence) {
        for (secretCode in Boat.secretCodes) {
            if (secretCode == spell) {
                run()
                return
            }
        }
        throw IAmSevenNotTaro()
    }

    private fun run() {
        OView().isVisible = true
    }

    private class IAmSevenNotTaro : RuntimeException(
        """
        I AM ULTRA SEVEN, NOT ULTRAMAN TARO, YOU SHOULD THANK FOR SEVEN NOT TARO, FUCK YOU!
              fﾆヽ
        　　　 |_||
        　　　 |= |
        　　　 |_ |
        　　/⌒|~ |⌒i-、
        　 /|　|　|　| ｜
        　｜(　(　(　( ｜
        　｜　　　　　 ｜ 　
        　 ＼　　　　　/
        　　 ＼　　　 　
        """.trimIndent()
    )
}