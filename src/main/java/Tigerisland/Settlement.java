package Tigerisland;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Reed on 3/20/2017.
 */
public class Settlement {
    private Player Player;  //what player settlement belongs to
    private int settlementID;   //what settlement it is
    private int size;   //size of settlement

    public void Settlement(Player Player, int settlementNumber, int size){
        this.Player = Player;
        this.settlementID = settlementNumber;
        this.size = size;
    }

    public Player getPlayer(){return this.Player;}

    public int getSettlementID(){return this.settlementID;}

    public int getsize(){return this.size;}
}
