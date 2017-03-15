/**
 * Created by NotKali on 3/15/2017.
 */
public class Graph {
    int horizontalLength = 10;
    int verticalLength = 10;
    Hex [] [] board = new Hex[horizontalLength][verticalLength];

    public Graph() {
    }

    public void addHex(int x, int y) {
        board[x][y] = new Hex(Hex.TerrainType.VOLCANO);
    }

    public boolean hexExists(int x, int y){
        return(board[x][y]!=null);
    }

    public void changeTileIdentifer(int x, int y, int tileID_number) {
        board[x][y].setTileIdentifier(tileID_number);
    }
}
