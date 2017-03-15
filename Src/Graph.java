/**
 * Created by NotKali on 3/15/2017.
 */
public class Graph {
<<<<<<< HEAD
    int horizontalLength = 10;
    int verticalLength = 10;
    Hex [] [] board = new Hex[horizontalLength][verticalLength];
=======
    Hex [][] board;
    int horizontalLength;
    int verticalLength;
>>>>>>> origin/master

    public void changeTileIdentifier(int x, int y) {
        Hex hexToBeChanged = board[x][y];
    }

<<<<<<< HEAD
    public void addHex(int x, int y) {
        board[x][y] = new Hex(Hex.TerrainType.VOLCANO);
    }

    public boolean hexExists(int x, int y){
        return(board[x][y]!=null);
    }

    public void changeTileIdentifer(int x, int y, int tileID_number) {
        board[x][y].setTileIdentifier(tileID_number);
    }
=======
>>>>>>> origin/master
}
