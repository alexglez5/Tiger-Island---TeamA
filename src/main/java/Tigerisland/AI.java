package Tigerisland;

/**
 * Created by Alexander Gonzalez on 4/1/2017.
 */

/* AI will be our new API and AI in one. Adaptor passes information to the AI in the following process:

   <pid><place><build>
    |
    |
   \/
   <tile><coordinate><orientation><build option><coordinate>(<terrain>)
    |
    |
   \/
   <terrainA><terrainB><xCord><yCord><orientation><build option><xCord><yCord>(<terrain>)

   OR


    <build option> = "expand", "found", "totoro", "tiger"
    <terrain> = "JUNGLE", "LAKE", "ROCK", "GRASS"

    sections are delimited by spaces.
*/

public class AI {
    GameBoard map;
    boolean myTurn;
    Tile currentTile;
    Coordinate tileCord;
    Orientation orient;
    PlayerChoice buildChoice;
    Coordinate buildCord;
    TerrainType expandArea;

    public AI() {
        map = new GameBoard();
    }

    public void placeTile(Tile tile, Coordinate c, Orientation o) {
        map.placeTile(tile, c, o);
    }

    public void parseOtherPlayerMessage(String message) {
        String[] m = message.split(" ");

        TerrainType a = parseTerrain(m[0]);
        TerrainType b = parseTerrain(m[1]);
        currentTile = new Tile(a, b);

        int xTile = Integer.parseInt(m[2]);
        int yTile = Integer.parseInt(m[3]);
        tileCord = new Coordinate(xTile, yTile);

        orient = parseOrientation(m[4]);

        buildChoice = parseBuildOption(m[5]);

        int xBuild = Integer.parseInt(m[6]);
        int yBuild = Integer.parseInt(m[7]);
        buildCord = new Coordinate(xBuild, yBuild);

        if (buildChoice.equals(PlayerChoice.expandSettlement)) {
            expandArea = parseTerrain(m[8]);
        }
    }

    public Orientation parseOrientation(String orientation) {
        if (orientation.equals("FromBottom")) {
            return Orientation.FromBottom;
        } else if (orientation.equals("FromTop")) {
            return Orientation.FromTop;
        } else if (orientation.equals("FromTopLeft")) {
            return Orientation.FromTopLeft;
        } else if (orientation.equals("FromTopRight")) {
            return Orientation.FromTopRight;
        } else if (orientation.equals("FromBottomLeft")) {
            return Orientation.FromBottomLeft;
        } else if (orientation.equals("FromBottomRight")) {
            return Orientation.FromBottomRight;
        } else {
            System.out.println("Invalid Orientation");
            return null;
        }
    }

    public PlayerChoice parseBuildOption(String choice) {
        if (choice.equals("found")) {
            return PlayerChoice.foundSettlement;
        } else if (choice.equals("expand")) {
            return PlayerChoice.expandSettlement;
        } else if (choice.equals("totoro")) {
            return PlayerChoice.placeTotoro;
        } else if (choice.equals("tiger")) {
            return PlayerChoice.placeTiger;
        } else {
            System.out.println("Invalid Player Choice");
            return null;
        }
    }

    public TerrainType parseTerrain(String terrain) {

        if(terrain.equals("JUNGLE")){
            return TerrainType.Jungle;
        }
        else if(terrain.equals("ROCK")) {
            return TerrainType.Rocky;
        }
        else if(terrain.equals("LAKE")) {
            return TerrainType.Lake;
        }
        else if(terrain.equals("GRASS")) {
            return TerrainType.Grasslands;
        }
        else if(terrain.equals("VOLCANO") ) {
            return TerrainType.Volcano;
        }
        else {
            System.out.println("Invalid Terrain");
            return null;
        }
    }
}
