package Tigerisland;

/**
 * Created by Reed on 3/20/2017.
 */
public class Totoro {
    private Player Player; //black or white
    private int settlement; //what settlement it belongs to

    public void Totoro(Player Player, int settlement){
        this.Player = Player;
        this.settlement = settlement;
    }

    public Player whichPlayer(){return this.Player;}

    public int whichSettlement(){return this.settlement;}
}
