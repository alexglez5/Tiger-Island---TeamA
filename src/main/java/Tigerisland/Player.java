package Tigerisland;

public class Player {
    private int playerID;
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

    public int getPoints() { return score; }

    public void addPoints(int points) { score += points; }

    public int getNumberOfVillagersLeft() { return numberOfVillagersLeft; }

    public int getNumberOfTotoroLeft() { return numberOfTotoroLeft; }

    public int getNumberOfTigersLeft() { return numberOfTigersLeft; }

    public void useVillagers(int num) { numberOfVillagersLeft -= num; }

    public void useTotoro() { numberOfTotoroLeft -= 1; }

    public void useTiger() { numberOfTigersLeft -= 1; }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public void resetScoreAndInventory() {
        score = 0;
        numberOfVillagersLeft = 20;
        numberOfTotoroLeft = 3;
        numberOfTigersLeft = 2;
    }
}