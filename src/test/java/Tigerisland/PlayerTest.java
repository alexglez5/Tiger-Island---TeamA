package Tigerisland;

import org.junit.*;

/**
 * Created by Reed on 3/22/2017.
 */
public class PlayerTest {
    static Player player = new Player();
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
    public void placedVillagers1(){
        player.updatePlacedVillagers(5);
        Assert.assertTrue(player.getNumberOfVillagersLeft() == 15);
    }
    @Test
    public void placedTotoro1(){
        System.out.println("first: " + player.getNumberOfTotoroLeft());
        player.updatePlacedTotoro();
        System.out.println("second: " + player.getNumberOfTotoroLeft());
        Assert.assertTrue((player.getNumberOfTotoroLeft() == 2));
    }
    @Test
    public void placedTiger1() {
        player.updatePlacedTiger();
        Assert.assertTrue(player.getNumberOfTigersLeft() == 1);
    }
    @Test
    public void placedVillagers2() {
        player.updatePlacedVillagers(5);
        Assert.assertTrue(player.getNumberOfVillagersLeft() == 10);
    }
    @Test
    public void placedTotoro2(){
        System.out.println("Third " + player.getNumberOfTotoroLeft());
        player.updatePlacedTotoro();
        System.out.println("Fourth: " + player.getNumberOfTotoroLeft());
        Assert.assertTrue((player.getNumberOfTotoroLeft() == 1));
    }
    @Test
    public void placedTiger2() {
        player.updatePlacedTiger();
        Assert.assertTrue(player.getNumberOfTigersLeft() == 0);
    }
}
