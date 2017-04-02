package Tigerisland;

public class Game {

    GameBoard gameBoard = new GameBoard();
    Player white = new Player();
    Player black = new Player();
    int turn = 1;
    TileValidator validator = new TileValidator();

    public boolean checkHexLocation(Coordinate cord) {
        boolean existence = false;
        existence = gameBoard.checkForHex(cord);

        return existence;
    }

    public void placeFirstTile() {
        Hex main = new Hex(TerrainType.VOLCANO, 1);
        Hex leftTop = new Hex(TerrainType.JUNGLE, 1);
        Hex rightTop = new Hex(TerrainType.LAKE, 1);
        Hex leftBot = new Hex(TerrainType.ROCK, 1);
        Hex rightBot = new Hex(TerrainType.GRASS, 1);

        gameBoard.placeHex(main, new Coordinate(0,0,0));
        gameBoard.placeHex(leftTop, new Coordinate(0,1,-1));
        gameBoard.placeHex(rightTop, new Coordinate(1,0,-1));
        gameBoard.placeHex(leftBot, new Coordinate(-1,0,1));
        gameBoard.placeHex(rightBot, new Coordinate(0,-1,1));
    }

    public void incrementTurn() {
        turn++;
    }

    public TerrainType stringToTerrain(String terrain) throws Throwable {
        if(terrain.equals("JUNGLE")){
            return TerrainType.JUNGLE;
        }
        else if(terrain.equals("ROCK")) {
            return TerrainType.ROCK;
        }
        else if(terrain.equals("LAKE")) {
            return TerrainType.LAKE;
        }
        else if(terrain.equals("GRASS")) {
            return TerrainType.GRASS;
        }
        else if(terrain.equals("VOLCANO")) {
            return TerrainType.VOLCANO;
        }
        throw new Error("Incorrect terrain formatting");
    }

    public void placeTile(Tile tile, Coordinate target) {
        gameBoard.placeHex(tile.getMainTerrain(), target);
        int orientation = tile.getOrientation();
    }
}
