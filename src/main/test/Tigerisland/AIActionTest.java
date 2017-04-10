package Tigerisland;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Alexander Gonzalez on 4/8/2017.
 */
public class AIActionTest {
    AI ai;

    @Before
    public void setUp() throws Exception{
        ai = new AI();
    }

    @Test
    public void shouldgetTheRightMessage() throws Exception{
        String message  = "GAME A MOVE 1 PLAYER 1 PLACED ROCK+LAKE AT 0 0 0 5 FOUNDED SETTLEMENT AT -1 0 1";
        ai.setServerMessage(message);
//        ai.placeOpponentMove();
//        Assert.assertTrue(ai.map.getBoard().containsKey(new Coordinate(-1,1)));
//        Assert.assertTrue(ai.map.getBoard().get(new Coordinate(-1,1)).hasVillager());
    }
}
