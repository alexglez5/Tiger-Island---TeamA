/**
 * Created by NotKali on 3/15/2017.
 */
public class Tile {
    private Hex [] threeHexes;

    public Tile(Hex[] threeHexes) {
        this.threeHexes = threeHexes;
    }

    public Hex[] getThreeHexes() {
        return threeHexes;
    }

    public boolean hasThreeHexes () {
        if(threeHexes.length != 3) {
            return false;
        }
        else {
            return true;
        }
    }
}
