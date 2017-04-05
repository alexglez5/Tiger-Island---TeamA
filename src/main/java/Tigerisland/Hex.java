package Tigerisland;

public class Hex {
    private TerrainType terrainType;
    private boolean hasVillagers;
    private boolean hasTotoro;
    private boolean hasTiger;
    private int tileID;
    private int level;

    public Hex(TerrainType terrainType, int tileID){
        this.terrainType = terrainType;
        this.tileID = tileID;
        level = 1;
    }

    public void placeVillagers(){
        hasVillagers = true;
    }

    public void placeTotoro(){
        hasTotoro = true;
    }

    public void placeTiger() { hasTiger = true; }

    public boolean hasVillager(){
        return hasVillagers;
    }

    public boolean hasTotoro(){
        return hasTotoro;
    }

    public boolean hasTiger() { return hasTiger; }

    public TerrainType getTerrainType(){
        return terrainType;
    }

    public int getTileID() {
        return tileID;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
