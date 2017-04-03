package Tigerisland;

/**
 * Created by Alexander Gonzalez on 4/1/2017.
 */

/* AI will be our new API and AI in one. Adaptor passes information to the AI in the following process:

   <pid><place><build>
    |
    |
   \/
   <pid><tile><coordinate><orientation><build option><coordinate>(<terrain>)
    |
    |
   \/
   <boolean><tile><coordinate><orientation><build option><coordinate>(<terrain>)

    if the boolean is "true", this means it's the AI's turn and thinks about the move and then moves
    if the boolean is "false", this means the AI directly calls the move functions.

    sections are delimited by spaces.
*/

public class AI {
    GameBoard map;
    boolean myTurn = true;
    Tile currentTile;
    Coordinate tileCord;
    Orientation orient;
    String buildChoice;
    Coordinate buildCord;
    TerrainType expandArea;

    public AI() {
        map = new GameBoard();
    }

    public void placeTile(Tile tile, Coordinate c, Orientation o) {
        map.placeTile(tile, c, o);
    }

    public void parseMessage(String message) {

    }

}
