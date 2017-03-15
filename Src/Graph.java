/**
 * Created by NotKali on 3/15/2017.
 */
public class Graph {
    int horizontalLength = 10;
    int verticalLength = 10;
    Hex [] [] hexes = new Hex[horizontalLength][verticalLength];

    public Graph() {
    }

    public void addHex(int x, int y) {
        hexes[x][y] = new Hex(Hex.TerrainType.VOLCANO);
    }

    public boolean hexExists(int x, int y){
        return(hexes[x][y]!=null);
    }
}
