/**
 * Created by NotKali on 3/8/2017.
 */

public class Hex {

    private int [] sidesConnected;
    public enum Feature{
        VOLCANO, DESERT, WATER, JUNGLE, PLAIN;
    }


    private Feature features;

    public Hex(int[] sidesConnected, Feature features) {
        this.sidesConnected = sidesConnected;
        this.features = features;
    }

    public int[] getSidesConnected() {
        return sidesConnected;
    }

    public boolean isConnected() {
        for(int i = 0; i < sidesConnected.length; i++){
            if(sidesConnected[i]==1){
                return true;
            }
        }
        return false;
    }

    public boolean hasFeatures() {
        if(features!=null){
            return true;
        }
        return false;
    }

}
