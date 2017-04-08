package Tigerisland.PlayerActions;

import Tigerisland.Coordinate;
import Tigerisland.Hex;
import Tigerisland.Player;
import Tigerisland.Settlement;

import java.util.HashMap;

/**
 * Created by Alexander Gonzalez on 4/8/2017.
 */
public class ComponentsDTO {
    private Player player = new Player();
    private HashMap<Coordinate, Hex> gameBoard = new HashMap<>();
    private HashMap<Integer, Settlement> settlements = new HashMap<>();

    public ComponentsDTO(
            HashMap<Coordinate, Hex> gameBoard,
            HashMap<Integer, Settlement> settlements,
            Player player){
        this.gameBoard = gameBoard;
        this.settlements = settlements;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public HashMap<Coordinate, Hex> getGameBoard() {
        return gameBoard;
    }

    public HashMap<Integer, Settlement> getSettlements() {
        return settlements;
    }

    public void setSettlements(HashMap<Integer, Settlement> settlements) {
        this.settlements = settlements;
    }
}
