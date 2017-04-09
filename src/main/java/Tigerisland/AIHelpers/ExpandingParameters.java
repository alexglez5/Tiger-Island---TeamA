package Tigerisland.AIHelpers;

import Tigerisland.Coordinate;
import Tigerisland.TerrainType;

/**
 * Created by Alexander Gonzalez on 4/9/2017.
 */
public class ExpandingParameters {
    private int settlementID;
    private TerrainType terrainType;

    public ExpandingParameters(int settlementID, TerrainType terrainType){
        this.settlementID = settlementID;
        this.terrainType = terrainType;
    }


    public int getSettlementID() {
        return settlementID;
    }

    public void setSettlementID(int settlementID) {
        this.settlementID = settlementID;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public void setTerrainType(TerrainType terrainType) {
        this.terrainType = terrainType;
    }
}
