import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by NotKali on 3/15/2017.
 */
public class GraphTest {
    Graph board = new Graph();

    @Test
    public void hexExists() throws Exception {
        board.addHex(5,5);
        assertTrue(board.hexExists(5,5));
    }

}