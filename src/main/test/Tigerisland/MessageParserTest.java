package Tigerisland;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Alexander Gonzalez on 4/8/2017.
 */
public class MessageParserTest {
    AI ai;

    @Before
    public void setUp() throws Exception{
        ai = new AI();
    }

    @Test
    public void shouldgetTheRightMessage() throws Exception{
        String message  = "GAME A MOVE 1 PLAYER 1 PLACED ROCKY+LAKE AT 0 -1 1 2 BUILT TIGER PLAYGROUND AT -1 0 1";
        ai.setServerMessage(message);
//        ai.placeOpponentMove();
    }


}
