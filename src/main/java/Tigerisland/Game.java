package Tigerisland;

public class Game {

    GameBoard map = new GameBoard();
    Player computer = new Player();
    Player opponent = new Player();
    TileValidator validator = new TileValidator();
    CoordinatesLocator locator = new CoordinatesLocator();
    Tile currentTile;
    TerrainType a;
    TerrainType b;
    int xCord;
    int yCord;
    int zCord;
    int orient;
    Coordinate tileLoc;
    Coordinate buildLoc;
    TerrainType expandTerrain;

    public boolean checkHexLocation(Coordinate cord) {
        return map.checkForHex(cord);
    }

    public void placeFirstTile() {
        Hex main = new Hex(TerrainType.VOLCANO, 1);
        Hex leftTop = new Hex(TerrainType.JUNGLE, 1);
        Hex rightTop = new Hex(TerrainType.LAKE, 1);
        Hex leftBot = new Hex(TerrainType.ROCK, 1);
        Hex rightBot = new Hex(TerrainType.GRASS, 1);

        map.placeHex(main, new Coordinate(0,0,0));
        map.placeHex(leftTop, new Coordinate(0,1,-1));
        map.placeHex(rightTop, new Coordinate(1,0,-1));
        map.placeHex(leftBot, new Coordinate(-1,0,1));
        map.placeHex(rightBot, new Coordinate(0,-1,1));
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

    public void setCurrentTile(TerrainType a, TerrainType b) {
        currentTile = new Tile(a, b);
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    // returns true if the tile was placed, false if it wasn't
    public boolean checkAndPlaceTile(Tile tile, Coordinate target, int orientation) {
        if (validator.canPlaceTile(target, orientation)) {
            placeTile(tile, target, orientation);
            return true;
        }
        return false;
    }

    public void placeTile(Tile tile, Coordinate target, int orientation) {
        Coordinate[] nearby = locator.produceCoordinatesFromOrientation(target, orientation);
        map.placeHex(tile.getLeftOfMainTerrain(), nearby[0]);
        map.placeHex(tile.getRightOfMainTerrain(), nearby[1]);
        map.placeHex(tile.getMainTerrain(), target);
        
        // todo: check for settlement split
    }

    public void clearBoard() {
        map.clearBoard();
    }

    public int checkLevel(Coordinate c) {
        return map.getHex(c).getLevel();
    }

    public Player getComputer() {
        return computer;
    }

    public Player getOpponent() {
        return opponent;
    }


}
