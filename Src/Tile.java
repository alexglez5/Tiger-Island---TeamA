/**
 * Created by NotKali on 3/15/2017.
 */
public class Tile {
    private Hex [] ownedHexes;

    public Tile(Hex[] tileHexes) {
        this.ownedHexes = tileHexes;
    }

    public boolean hasThreeHexes () {
        return (ownedHexes.length == 3);
    }
}
