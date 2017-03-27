package Tigerisland;

/**
 * Created by nathanbarnavon on 3/27/17.
 */
public class Player {

    private Tile currentTile;
    private int score;
    private int numberOfVillagersLeft;
    private int numberOfTotoroLeft;
    private int numberOfTigersLeft;

    public Player() {
        score = 0;
        numberOfVillagersLeft = 20;
        numberOfTotoroLeft = 3;
        numberOfTigersLeft = 2;
    }

    public int getScore() { return score; }

    public void addPoints(int points) { score += points; }

    public int getNumberOfVillagersLeft() { return numberOfVillagersLeft; }

    public int getNumberOfTotoroLeft() { return numberOfTotoroLeft; }

    public int getNumberOfTigersLeft() { return numberOfTigersLeft; }

    public void useVillagers(int num) { numberOfVillagersLeft -= num; }

    public void useTotoro() { numberOfTotoroLeft--; }

    public void useTiger() { numberOfTigersLeft--; }

    public Tile getCurrentTile() { return currentTile; }

    public void setCurrentTile(Tile t) { currentTile = t; }
}
