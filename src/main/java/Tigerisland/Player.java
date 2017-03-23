package Tigerisland;

/**
 * Created by Reed on 3/20/2017.
 */
public class Player {
    private Tile currentTile;
    private int points = 0;
    private int numberOfVillagersLeft = 20;
    private int numberOfTotoroLeft = 3;
    private int numberOfTigersLeft = 2;

    public void Player(){}

    public int getPoints(){return this.points;}

    public void addPlayerPoints(int pointsAdded){this.points += pointsAdded;}

    public int getNumberOfVillagersLeft(){ return this.numberOfVillagersLeft;}

    public int getNumberOfTotoroLeft(){return this.numberOfTotoroLeft;}

    public int getNumberOfTigersLeft(){return this.numberOfTigersLeft;}

    public void updatePlacedVillagers(int numberOfVillagersPlaced){this.numberOfVillagersLeft -= numberOfVillagersPlaced;}

    public void updatePlacedTotoro(){this.numberOfTotoroLeft -= 1;}

    public void updatePlacedTiger(){this.numberOfTigersLeft -= 1;}

    public void grantTile(Tile currentTile) {this.currentTile = currentTile;}

    public void removeCurrentTile() {this.currentTile = null;}

    public Tile getCurrentTile(){return this.currentTile;}
}
