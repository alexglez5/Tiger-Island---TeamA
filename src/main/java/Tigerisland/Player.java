package Tigerisland;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Player {
    private int playerID;
    private int score;
    private int numberOfVillagersLeft;
    private int numberOfTotoroLeft;
    private int numberOfTigersLeft;
//    private ArrayList<Settlement> settlements;

    public Player() {
        score = 0;
//        settlements = new ArrayList<>();
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

    public void useTotoro() { numberOfTotoroLeft--; }

    public void useTiger() { numberOfTigersLeft--; }

//    public void addSettlement(Settlement s) { getSettlements().add(s); }

//    public void removeSettlement(Settlement s) { settlements.remove(s); }

//    public Settlement findSettlement(int sid) {
//        for (Settlement s : settlements) {
//            if (s.getSettlementID() == sid)
//                return s;
//        }
//        return null;
//    }

//    public ArrayList<Settlement> getSettlements() {
//        return settlements;
//    }

//    public boolean containsKey(int id){
//        return findSettlement(id) != null;
//    }

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