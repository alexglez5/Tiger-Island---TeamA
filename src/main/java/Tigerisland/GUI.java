package Tigerisland;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

/**
 * Created by Julia on 4/7/2017.
 */
public class GUI  {


    private static final int WIDTH = 800; //GUI size
    private static final int HEIGHT = 800;
    static JFrame frame;


    public static void main (String[] args)
    {
        SwingUtilities.invokeLater(
                new Runnable()
                {
                    public void run()
                    {
                        createGUI();
                    }
                }

        );

    }

    public static void createGUI()
    {
        frame = new ImageFrame(WIDTH, HEIGHT); //setting up JFrame
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE); //exit application
        frame.setVisible(true); //create frame
    }
}



class ImageFrame extends JFrame
{
    final BufferedImage bufferedImage = new BufferedImage (800, 800, BufferedImage.TYPE_INT_ARGB);
    final Font font = new Font("Serif", Font.PLAIN, 15);
    Graphics2D g = bufferedImage.createGraphics();
    public Game map = new Game();



    public ImageFrame( int width, int height)
    {
        this.setTitle( "TigerIsland"); // JFrame qualities
        this.setSize(width, height);
        addMenu(); //Options
        gameGUI(); //method that does action
    }

    private void addMenu() //select exit
    {
        JMenu menuOptions = new JMenu("Options");
        JMenuItem exit = new JMenuItem("Exit");


        exit.addActionListener( new ActionListener()
        {
            public void actionPerformed (ActionEvent event)
            {
                System.exit(0); //Exit
            }
        });

        menuOptions.add(exit);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menuOptions);
        this.setJMenuBar(menuBar);
    }


    public void gameGUI()
    {
        RenderingHints hint = new RenderingHints( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g.setRenderingHints( hint );
        int count = 0;
        g.setFont(font);

        //Test code
        /*map.placeStartingTile();
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK),
                new Coordinate(2,-1), Orientation.FromBottom);
       map.foundNewSettlement(new Coordinate(2,-1));*/

        for(Coordinate c : map.getBoard().keySet()){
            count++;
            isFirstTile(count);

            String countString  = Integer.toString(count);
            //set location of rectangle
            int xTest = 400+(c.getXCoordinate()*40);
            int yTest = 400+(c.getYCoordinate()*40)+100;
            placeHex( xTest, yTest);
            isVolcano(c,xTest,yTest);
            hasVillager(c, xTest, yTest);
            hasTotoro(c,xTest,yTest);

            //set text for number hex played
            g.setColor(Color.BLACK);
            g.drawString(countString,xTest,yTest+20);
            
        }
        //display image
        display(bufferedImage);
    }

    public void placeHex(int x, int y)
    {
        g.fillRect(x,y,30,30);
        currentPlayer(x, y);
    }

    public void currentPlayer(int x, int y)
    {
        int currentPlayerID = map.getCurrentPlayerId();
        if(currentPlayerID==1) {
            g.setColor(Color.GREEN);
            g.drawRect(x, y, 30, 30);
        }
        else if(currentPlayerID ==2)
        {
            g.setColor(Color.BLUE);
            g.drawRect(x, y, 30, 30);
        }

    }

    public void hasVillager(Coordinate c, int x, int y)
    {
        if(map.getBoard().get(c).hasVillager())
        {
            g.setColor(Color.GRAY);
            g.fillRect(x, y, 30, 30);
            currentPlayer(x, y);
        }

    }

    public void hasTotoro(Coordinate c, int x, int y)
    {
        if(map.getBoard().get(c).hasTotoro())
        {
            g.setColor(Color.MAGENTA);
            g.fillRect(x, y, 30, 30);
            currentPlayer(x, y);
        }
    }

    public void isFirstTile(int count)
    {
        if(count <=5)
        {
            g.setColor(Color.RED);
        }
        else
        {
            g.setColor(Color.LIGHT_GRAY);
        }

    }

    public void isVolcano(Coordinate c, int x, int y)
    {
        TerrainType currentTerrain = map.getBoard().get(c).getTerrainType();
        TerrainType vol = TerrainType.VOLCANO;
        if(currentTerrain == vol)
        {
            g.setColor(Color.YELLOW);
            g.fillRect(x, y, 30, 30);
            currentPlayer(x, y);
        }
    }

    //display image

    public  void display(BufferedImage bufferedImage)
    {
        this.setContentPane( new JScrollPane( new JLabel(new ImageIcon(bufferedImage) )));
        this.validate();
    }
}
