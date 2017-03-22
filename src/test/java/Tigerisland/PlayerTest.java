package Tigerisland;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Reed on 3/22/2017.
 */
public class PlayerTest {
    Player player = new Player();
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
    public void placedVillagers(){
        player.updatePlacedVillagers(5);
        Assert.assertTrue(player.getNumberOfVillagersLeft() == 15);
    }
    @Test
    public void placedTotoro(){
        player.updatePlacedTotoro();
        Assert.assertTrue((player.getNumberOfTotoroLeft() == 2));
    }
    @Test
    public void placedTiger() {
        System.out.println(player.getNumberOfVillagersLeft());
        player.updatePlacedTiger();
        Assert.assertTrue(player.getNumberOfTigersLeft() == 1);
    }
    @Test
    public void placedVillagers2() {
        player.updatePlacedVillagers(5);
        Assert.assertTrue(player.getNumberOfVillagersLeft() == 10);
    }
}
