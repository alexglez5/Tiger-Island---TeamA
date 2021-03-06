package Tigerisland.PlayerActions;

import Tigerisland.Coordinate;
import Tigerisland.Hex;
import Tigerisland.Player;
import Tigerisland.Settlement;

import java.util.HashMap;

public class ComponentsDTO {
    private Player player = new Player();
    private HashMap<Coordinate, Hex> gameBoard = new HashMap<>();
    private HashMap<Integer, Settlement> settlements = new HashMap<>();
    private ActionHelper locator = new ActionHelper();

    public ComponentsDTO(
            HashMap<Coordinate, Hex> gameBoard,
            HashMap<Integer, Settlement> settlements,
            Player player, ActionHelper locator){
        this.gameBoard = gameBoard;
        this.settlements = settlements;
        this.player = player;
        this.locator = locator;
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

    public ActionHelper getLocator() {
        return locator;
    }
}
