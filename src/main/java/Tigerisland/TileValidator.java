package Tigerisland;

/**
 * Created by nathanbarnavon on 4/2/17.
 */
public class TileValidator extends GameBoard {
    // 0 is A, 1 is B
    private Coordinate[] orientationCoordinates = new Coordinate[2];

    public void produceCoordinatesFromOrientation(Coordinate c, int orientation) {
        switch (orientation) {
            case 1: orientationCoordinates[0] = new Coordinate(c.getX(), c.getY()+1, c.getZ()-1);
                    orientationCoordinates[1] = new Coordinate(c.getX()+1, c.getY(), c.getZ()-1);
                    break;
            case 2: orientationCoordinates[0] = new Coordinate(c.getX()+1, c.getY(), c.getZ()-1);
                    orientationCoordinates[1] = new Coordinate(c.getX()+1, c.getY()-1, c.getZ());
                    break;
            case 3: orientationCoordinates[0] = new Coordinate(c.getX()+1, c.getY()-1, c.getZ());
                    orientationCoordinates[1] = new Coordinate(c.getX(), c.getY()-1, c.getZ()+1);
                    break;
            case 4: orientationCoordinates[0] = new Coordinate(c.getX(), c.getY()-1, c.getZ()+1);
                    orientationCoordinates[1] = new Coordinate(c.getX()-1, c.getY(), c.getZ()+1);
                    break;
            case 5: orientationCoordinates[0] = new Coordinate(c.getX()-1, c.getY(), c.getZ()+1);
                    orientationCoordinates[1] = new Coordinate(c.getX()-1, c.getY()+1, c.getZ());
                    break;
            case 6: orientationCoordinates[0] = new Coordinate(c.getX()-1, c.getY()+1, c.getZ());
                    orientationCoordinates[1] = new Coordinate(c.getX(), c.getY()+1, c.getZ()-1);
                    break;
        }

    }

    public boolean canPlaceTileOnLevelOne(Coordinate c, int orientation) {

        return false;
    }
}
