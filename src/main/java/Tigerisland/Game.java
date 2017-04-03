package Tigerisland;

public class Game {

    GameBoard gameBoard = new GameBoard();
    Player white = new Player();
    Player black = new Player();
    int turn = 1;
    TileValidator validator = new TileValidator();
    Tile currentTile;

    public boolean checkHexLocation(Coordinate cord) {
        return gameBoard.checkForHex(cord);
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

    public Tile createTile(TerrainType a, TerrainType b) {
        currentTile = new Tile(a, b);
        return currentTile;
    }

    public void placeTile(Tile tile, Coordinate target, int orientation) {
        Coordinate[] nearby = validator.produceCoordinatesFromOrientation(target, orientation);
        gameBoard.placeHex(tile.getLeftOfMainTerrain(), nearby[0]);
        gameBoard.placeHex(tile.getRightOfMainTerrain(), nearby[1]);
        gameBoard.placeHex(tile.getMainTerrain(), target);
    }

    public void clearBoard() {
        gameBoard.clearBoard();
    }
}
