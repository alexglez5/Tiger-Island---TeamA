package Tigerisland;

/**
 * Created by Reed on 3/20/2017.
 */
public class Player {
    Tile currentTile;
    private int points;
    int numberOfVilagersLeft;
    int numberOfTotoroLeft;
    int numberOfTigersLeft;

    public void Player(){
        this.numberOfVilagersLeft = 20;
        this.numberOfTotoroLeft = 3;
        this.numberOfTigersLeft = 2;
    }

    public int getPoints(){return this.points;}

    public void addPlayerPoints(int pointsAdded){this.points += pointsAdded;}

    public int getNumberOfVilagersLeft(){ return this.numberOfVilagersLeft;}

    public int getNumberOfTotoroLeft(){return this.numberOfTotoroLeft;}

    public int getNumberOfTigersLeft(){return this.numberOfTigersLeft;}

    public void updatePlacedVilagers(int numberOfVilagersPlaced){this.numberOfVilagersLeft -= numberOfVilagersPlaced;}

    public void updatePlacedTotoro(int numberOfTotoroPlaced){this.numberOfTotoroLeft -= numberOfTotoroPlaced;}

    public void updateTigersPlaced(int numberOfTigerPlaced){this.numberOfTigersLeft -= numberOfTigerPlaced;}

    public void grantTile(Tile currentTile) {this.currentTile = currentTile;}

    public void removeCurrentTile() {this.currentTile = null;}
}
