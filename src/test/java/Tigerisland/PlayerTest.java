package Tigerisland;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Reed on 3/22/2017.
 */
public class PlayerTest {
    Player player;
    @Before
    public void initializePlayer(){
        player = new Player();
    }
    @Test
    public void getPoints() throws Exception{
        Assert.assertTrue(player.getPoints() == 0);
    }
    @Test
    public void addPlayerPoints(){
        player.addPlayerPoints(50);
        Assert.assertTrue(player.getPoints() == 50);
    }
    @Test
    public void placedVilagers(){
        System.out.println(player.getNumberOfVilagersLeft());
        player.updatePlacedVilagers(5);
        System.out.println(player.getNumberOfVilagersLeft());
        Assert.assertTrue(player.getNumberOfVilagersLeft() == 15);
    }
}
