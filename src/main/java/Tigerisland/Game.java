package Tigerisland;

public class Game {

    GameBoard gameBoard = new GameBoard();
    Player white = new Player();
    Player black = new Player();
    int turn = 1;

    public boolean checkHexLocation(Coordinate cord) {
        boolean existence = false;
        gameBoard.checkForHex(cord);

        return existence;
    }

    public void placeFirstTile() {
        Hex main = new Hex(TerrainType.VOLCANO, turn);
        Hex leftTop = new Hex(TerrainType.JUNGLE, turn);
        Hex rightTop = new Hex(TerrainType.LAKE, turn);

    }

    public void incrementTurn() {
        turn++;
    }
}
