package test.xyz.srclab.common.egg;

import org.testng.Assert;
import org.testng.annotations.Test;
import xyz.srclab.common.egg.BoatEggManager;
import xyz.srclab.common.egg.Egg;
import xyz.srclab.common.egg.EggNotFoundException;
import xyz.srclab.common.test.TestLogger;

import java.awt.*;

/**
 * @author sunqian
 */
public class EggTest {

    private static TestLogger logger = TestLogger.DEFAULT;

    @Test
    public void testEgg() {
        BoatEggManager eggManager = BoatEggManager.INSTANCE;
        Egg egg = eggManager.pick("Hello, Boat Egg!");
        egg.hatchOut("Any Spell!");

        Assert.expectThrows(EggNotFoundException.class, () -> {
            eggManager.pick("Hello, Egg!");
        });

        try {
            eggManager.pick("Hello, Egg!");
        } catch (EggNotFoundException e) {
            logger.log(e);
        }
    }

    @Test
    public void testOSpaceBattle() {
        if (GraphicsEnvironment.isHeadless()) {
            return;
        }
        BoatEggManager eggManager = BoatEggManager.INSTANCE;
        Egg egg = eggManager.pick("O Battle");
        egg.hatchOut("Thank you, Taro.");
        //Or
        //egg.hatchOut("谢谢你，泰罗。");
    }

    public static void main(String[] args) {
        new EggTest().testOSpaceBattle();
    }
}
