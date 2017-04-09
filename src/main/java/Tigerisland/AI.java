package Tigerisland;

import Tigerisland.AIHelpers.AIHelper;
import Tigerisland.AIHelpers.ExpandingParameters;
import Tigerisland.AIHelpers.TileParameters;

/**
 * Created by Alexander Gonzalez on 4/1/2017.
 */
public class AI {
    public Game map = new Game();
    private AIHelper helper = new AIHelper();
    private String message;

    public void setServerMessage(String message){
        this.message = message;
    }

    public void placeOpponentMove() {
        map.setCurrentPlayer(2);
        String[] split = message.split(" ");
        int i = 0;
        String pid = split[5];
        String xTile = split[9];
        String yTile = split[11];
        String[] tempSplit = split[7].split("[+]");
        String leftTerrainType = tempSplit[0];
        String rightTerrainType = tempSplit[1];
        String intOrientation = split[12];
        String terrainType = "";

        Choice move = Choice.TIGER;
        String xBuild = split[9];
        String yBuild = split[11];

        if (split[13].equals("FOUNDED")) {
            move = Choice.FOUNDED;
            xBuild = split[16];
            yBuild = split[18];
        } else if (split[13].equals("EXPANDED")) {
            move = Choice.EXPANDED;
            xBuild = split[16];
            yBuild = split[18];
            terrainType = split[19];
        } else if (split[14].equals("TOTORO")) {
            move = Choice.TOTORO;
            xBuild = split[17];
            yBuild = split[19];
        } else if (split[14].equals("TIGER")) {
            move = Choice.TIGER;
            xBuild = split[17];
            yBuild = split[19];
        }

        Orientation orientation;
        switch(Integer.parseInt(intOrientation)) {
            case 1:
                orientation = Orientation.FromTop;
                break;
            case 2:
                orientation = Orientation.FromTopRight;
                break;
            case 3:
                orientation = Orientation.FromBottomRight;
                break;
            case 4:
                orientation = Orientation.FromBottom;
                break;
            case 5:
                orientation = Orientation.FromBottomLeft;
                break;
            case 6:
                orientation = Orientation.FromTopLeft;
                break;
            default:
                orientation = Orientation.FromBottom;
        }

        map.placeTile(new Tile(TerrainType.valueOf(leftTerrainType), TerrainType.valueOf(rightTerrainType))
                , new Coordinate(Integer.parseInt(xTile), Integer.parseInt(yTile))
                , orientation);

        map.setCurrentPlayer(Integer.parseInt(pid));
        switch (move){
            case FOUNDED:
                map.foundNewSettlement(new Coordinate(Integer.parseInt(xBuild), Integer.parseInt(yBuild)));
                break;
            case EXPANDED:
                map.expandSettlement(new Coordinate(Integer.parseInt(xBuild), Integer.parseInt(yBuild)), TerrainType.valueOf(terrainType));
                break;
            case TOTORO:
                map.placeTotoro(new Coordinate(Integer.parseInt(xBuild), Integer.parseInt(yBuild)));
                break;
            case TIGER:
                map.placeTiger(new Coordinate(Integer.parseInt(xBuild), Integer.parseInt(yBuild)));
                break;
        }
    }

    public String placeAIMove(){
        map.setCurrentPlayer(1);
        String[] terrains = message.split(" ");
        TerrainType leftTerrain = TerrainType.valueOf(terrains[0]);
        TerrainType rightTerrain = TerrainType.valueOf(terrains[1]);

        message = "";
        helper.findPossibleMoves();
        boolean[] moves = helper.getMoves();
        if(moves[0]){
            helper.findPlaceWhereTileCanBePlaced(leftTerrain, rightTerrain);
            TileParameters parameters = helper.getPlaceWhereTileCanBePlaced();
            map.placeTile(new Tile(parameters.getLeftTerrainType(), parameters.getRightTerrainType()),
                    parameters.getMainTerrainCoordinate(), parameters.getOrientattion());
            int x = parameters.getMainTerrainCoordinate().getXCoordinate();
            int y = parameters.getMainTerrainCoordinate().getYCoordinate();
            int z = -1 * x - y;
            message += "PLACE " + parameters.getLeftTerrainType().toString()
                    + "+" + parameters.getRightTerrainType().toString()
                    + " AT " + parameters.getMainTerrainCoordinate().getXCoordinate()
                    + " " + x + " " + y + " " + z + " " + parameters.getOrientattion().getOrientationVal()
                    + " ";
        }
        for(int i = 1; i < moves.length; i++){
            if(moves[1]) {
                int x = helper.getPlaceWhereTotoroCanBePlaced().getXCoordinate();
                int y = helper.getPlaceWhereTotoroCanBePlaced().getYCoordinate();
                int z = -1 * x - y;
                map.placeTotoro(helper.getPlaceWhereTotoroCanBePlaced());
                message += "BUILD TOTORO SANCTUARY AT " + x + " " + y + " " + z;
            }
            else if(moves[2]) {
                int x = helper.getPlaceWhereTigerCanBePlaced().getXCoordinate();
                int y = helper.getPlaceWhereTigerCanBePlaced().getYCoordinate();
                int z = -1 * x - y;
                map.placeTotoro(helper.getPlaceWhereTigerCanBePlaced());
                message += "BUILD TIGER PLAYGROUND AT " + x + " " + y + " " + z;
            }
            else if (moves[3]) {
                ExpandingParameters parameters = helper.getPlaceWhereSettlementCanBeExpanded();
                Coordinate coordinate = parameters.getCoordinate();
                int x = coordinate.getXCoordinate();
                int y = coordinate.getYCoordinate();
                int z = -1 * x - y;
                map.expandSettlement(coordinate, parameters.getTerrainType());
                message += "EXPAND SETTLEMENT AT " + x + " " + y + " " + z + " " + parameters.getTerrainType().toString();
            }
            else if(moves[4]) {
                int x = helper.getPlaceWhereSettlementCanBeFound().getXCoordinate();
                int y = helper.getPlaceWhereSettlementCanBeFound().getYCoordinate();
                int z = -1 * x - y;
                map.placeTotoro(helper.getPlaceWhereSettlementCanBeFound());
                message += "FOUND SETTLEMENT AT " + x + " " + y + " " + z;
                map.foundNewSettlement(helper.getPlaceWhereSettlementCanBeFound());
            }
            else
                message += "UNABLE TO BUILD";
        }
        return message;
    }

    //////////////////////////////////////Nathan stuff////////////////////////////////////
    /* AI will be our new API and AI in one. Adaptor passes information to the AI in the following process:

   <place><build>
    |
    |
   \/
   <tile><coordinate><orientation><build option><coordinate>(<terrain>)
    |
    |
   \/
   <terrainA><terrainB><xCord><yCord><orientation><build option><xCord><yCord>(<terrain>)

   OR

    <tile>
    |
    |
    \/
    <terrainA><terrainB>
d
    <build option> = "expand", "found", "totoro", "tiger"
    <terrain> = "JUNGLE", "LAKE", "ROCK", "GRASS"

    sections are delimited by spaces.
*/

    boolean myTurn;
    Tile currentTile;
    Coordinate tileCord;
    Orientation orient;
    Choice buildChoice;
    Coordinate buildCord;
    TerrainType expandArea;

    public AI() {
        map = new Game();
    }

    public void placeTile(Tile tile, Coordinate c, Orientation o) {
        map.placeTile(tile, c, o);
    }

    public void parseTileMessage(String message) {
        String[] m = message.split(" ");
        TerrainType a = parseTerrain(m[0]);
        TerrainType b = parseTerrain(m[1]);
        currentTile = new Tile(a, b);
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

        if (buildChoice.equals(Choice.EXPANDED)) {
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

    public Choice parseBuildOption(String choice) {
        if (choice.equals("found")) {
            return Choice.EXPANDED;
        } else if (choice.equals("expand")) {
            return Choice.EXPANDED;
        } else if (choice.equals("totoro")) {
            return Choice.TOTORO;
        } else if (choice.equals("tiger")) {
            return Choice.TIGER;
        } else {
            System.out.println("Invalid Player Choice");
            return null;
        }
    }

    public TerrainType parseTerrain(String terrain) {

        if(terrain.equals("JUNGLE")){
            return TerrainType.JUNGLE;
        }
        else if(terrain.equals("ROCK")) {
            return TerrainType.ROCKY;
        }
        else if(terrain.equals("LAKE")) {
            return TerrainType.LAKE;
        }
        else if(terrain.equals("GRASS")) {
            return TerrainType.GRASSLANDS;
        }
        else if(terrain.equals("VOLCANO") ) {
            return TerrainType.VOLCANO;
        }
        else {
            System.out.println("Invalid Terrain");
            return null;
        }
    }
}
