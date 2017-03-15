/**
 * Created by NotKali on 3/8/2017.
 */

public class Hex {
    public enum Feature{
        VOLCANO, WATER, JUNGLE, PLAIN, MOUNTAIN;
    }


    private Feature features;
    private boolean occupied;

    public Hex(Feature features) {
        this.features = features;
    }

    public boolean hasFeatures() {
        if(features!=null){
            return true;
        }
        return false;
    }

    public boolean hasVillagers() {
        return occupied;
    }

}
