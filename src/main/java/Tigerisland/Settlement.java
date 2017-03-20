package Tigerisland;

/**
 * Created by Reed on 3/20/2017.
 */
public class Settlement {
    private Player Player;  //what player settlement belongs to
    private int settlementNumber;   //what settlement it is
    private int size;   //size of settlement

    public void Settlement(Player Player, int settlementNumber, int size){
        this.Player = Player;
        this.settlementNumber = settlementNumber;
        this.size = size;
    }

    public Player getPlayer(){return this.Player;}

    public int getSettlementNumber(){return this.settlementNumber;}

    public int getsize(){return this.size;}
}
