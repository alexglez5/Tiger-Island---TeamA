package Tigerisland;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Player {

    private static int numCreatedPlayers;
    private int playerID;
    private int score;
    private int numberOfVillagersLeft;
    private int numberOfTotoroLeft;
    private int numberOfTigersLeft;
    private ArrayList<Settlement> settlements;

    public Player() {
        numCreatedPlayers++;
        playerID = numCreatedPlayers;
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

    public void addSettlement(Settlement s) { settlements.add(s); }

    public void removeSettlement(Settlement s) { settlements.remove(s); }

    public Settlement findSettlement(int sid) {
        for (Settlement s : settlements) {
            if (s.getSettlementID() == sid)
                return s;
        }
        return null;
    }

    public boolean containsKey(int id){
        return findSettlement(id) != null;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }
}