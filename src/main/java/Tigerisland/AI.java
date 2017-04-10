package Tigerisland;

import Tigerisland.AIHelpers.AIHelper;
import Tigerisland.AIHelpers.Choice;
import Tigerisland.AIHelpers.ExpandingParameters;
import Tigerisland.AIHelpers.TileParameters;

/**
 * Created by Alexander Gonzalez on 4/1/2017.
 */
public class AI {
    public Game map = new Game();
    public AIHelper helper = new AIHelper();
    private String message;

    public void setServerMessage(String message) {
        this.message = message;
    }

    public void placeOpponentMove() {
        map.setCurrentPlayer(2);
        String[] split = message.split(" ");
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
        switch (Integer.parseInt(intOrientation)) {
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

        switch (move) {
            case FOUNDED:
                helper.map.foundNewSettlement(new Coordinate(Integer.parseInt(xBuild), Integer.parseInt(yBuild)));
                break;
            case EXPANDED:
                helper.map.expandSettlement(new Coordinate(Integer.parseInt(xBuild), Integer.parseInt(yBuild)), TerrainType.valueOf(terrainType));
                break;
            case TOTORO:
                helper.map.placeTotoro(new Coordinate(Integer.parseInt(xBuild), Integer.parseInt(yBuild)));
                break;
            case TIGER:
                helper.map.placeTiger(new Coordinate(Integer.parseInt(xBuild), Integer.parseInt(yBuild)));
                break;
        }
        helper.setMap(getMap());
    }

    public Game getMap() {
        return map;
    }

    public void setMap(Game map) {
        this.map = map;
    }

    public String placeAIMove() {
        map.setCurrentPlayer(1);
        String[] terrains = message.split(" ");
        TerrainType leftTerrain = TerrainType.valueOf(terrains[0]);
        TerrainType rightTerrain = TerrainType.valueOf(terrains[1]);

        String outMessage = "";
        if(helper.getPlaceWhereTileCanBePlaced(leftTerrain, rightTerrain) != null){
            TileParameters parameters = helper.getPlaceWhereTileCanBePlaced(leftTerrain, rightTerrain);
            map.placeTile(new Tile(parameters.getLeftTerrainType(), parameters.getRightTerrainType()),
                    parameters.getMainTerrainCoordinate(), parameters.getOrientattion());
            int x = parameters.getMainTerrainCoordinate().getXCoordinate();
            int y = parameters.getMainTerrainCoordinate().getYCoordinate();
            int z = -1 * x - y;
            outMessage += x + " " + y + " " + z + " " + parameters.getOrientattion().getOrientationVal() + " ";
        }
        helper.setMap(getMap());
        if (helper.getPlaceWhereTotoroCanBePlaced() != null) {
            int x = helper.getPlaceWhereTotoroCanBePlaced().getXCoordinate();
            int y = helper.getPlaceWhereTotoroCanBePlaced().getYCoordinate();
            int z = -1 * x - y;
            map.placeTotoro(helper.getPlaceWhereTotoroCanBePlaced());
            message += "BUILD TOTORO SANCTUARY AT " + x + " " + y + " " + z;
        } else if (helper.getPlaceWhereTigerCanBePlaced() != null) {
            int x = helper.getPlaceWhereTigerCanBePlaced().getXCoordinate();
            int y = helper.getPlaceWhereTigerCanBePlaced().getYCoordinate();
            int z = -1 * x - y;
            map.placeTiger(helper.getPlaceWhereTigerCanBePlaced());
            message += "BUILD TIGER PLAYGROUND AT " + x + " " + y + " " + z;
        } else if (helper.getPlaceWhereSettlementCanBeExpanded() != null) {
            ExpandingParameters parameters = helper.getPlaceWhereSettlementCanBeExpanded();
            Coordinate coordinate = parameters.getCoordinate();
            int x = coordinate.getXCoordinate();
            int y = coordinate.getYCoordinate();
            int z = -1 * x - y;
            map.expandSettlement(coordinate, parameters.getTerrainType());
            message += "EXPAND SETTLEMENT AT " + x + " " + y + " " + z + " " + parameters.getTerrainType().toString();
        } else if (helper.getPlaceWhereSettlementCanBeFound() != null) {
            int x = helper.getPlaceWhereSettlementCanBeFound().getXCoordinate();
            int y = helper.getPlaceWhereSettlementCanBeFound().getYCoordinate();
            int z = -1 * x - y;
            map.foundNewSettlement(helper.getPlaceWhereSettlementCanBeFound());
            message += "FOUND SETTLEMENT AT " + x + " " + y + " " + z;
        } else
            message += "UNABLE TO BUILD";

        helper.setMap(getMap());
        return message;
    }
}
