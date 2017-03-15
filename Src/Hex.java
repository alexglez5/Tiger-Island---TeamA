/**
 * Created by NotKali on 3/8/2017.
 */

public class Hex {
    public enum Feature{
        VOLCANO, DESERT, WATER, JUNGLE, PLAIN;
    }


    private Feature features;

    public Hex(Feature features) {
        this.features = features;
    }

    public boolean hasFeatures() {
        if(features!=null){
            return true;
        }
        return false;
    }

}
