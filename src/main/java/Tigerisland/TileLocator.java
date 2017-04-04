package Tigerisland;

/**
 * Created by nathanbarnavon on 4/4/17.
 */
public class TileLocator extends GameBoard {

    public Coordinate[] produceClockwiseNeighborCoordinates(Coordinate c) {
        Coordinate[] clockwiseCoordinates = new Coordinate[6];
        clockwiseCoordinates[0] = new Coordinate(c.getX() + 1, c.getY(), c.getZ()-1);
        clockwiseCoordinates[1] = new Coordinate(c.getX()+1, c.getY()-1, c.getZ());
        clockwiseCoordinates[2] = new Coordinate(c.getX(), c.getY()-1, c.getZ()+1);
        clockwiseCoordinates[3] = new Coordinate(c.getX()-1, c.getY(), c.getZ()+1);
        clockwiseCoordinates[4] = new Coordinate(c.getX()-1, c.getY()+1, c.getZ());
        clockwiseCoordinates[5] = new Coordinate(c.getX(), c.getY()+1, c.getZ()-1);
        return clockwiseCoordinates;
    }

    // 0 is A, 1 is B
    public Coordinate[] produceCoordinatesFromOrientation(Coordinate c, int orientation) {
        Coordinate[] orientationCoordinates = new Coordinate[2];

        switch (orientation) {
            case 1:
                orientationCoordinates[0] = new Coordinate(c.getX(), c.getY() + 1, c.getZ() - 1);
                orientationCoordinates[1] = new Coordinate(c.getX() + 1, c.getY(), c.getZ() - 1);
                break;
            case 2:
                orientationCoordinates[0] = new Coordinate(c.getX() + 1, c.getY(), c.getZ() - 1);
                orientationCoordinates[1] = new Coordinate(c.getX() + 1, c.getY() - 1, c.getZ());
                break;
            case 3:
                orientationCoordinates[0] = new Coordinate(c.getX() + 1, c.getY() - 1, c.getZ());
                orientationCoordinates[1] = new Coordinate(c.getX(), c.getY() - 1, c.getZ() + 1);
                break;
            case 4:
                orientationCoordinates[0] = new Coordinate(c.getX(), c.getY() - 1, c.getZ() + 1);
                orientationCoordinates[1] = new Coordinate(c.getX() - 1, c.getY(), c.getZ() + 1);
                break;
            case 5:
                orientationCoordinates[0] = new Coordinate(c.getX() - 1, c.getY(), c.getZ() + 1);
                orientationCoordinates[1] = new Coordinate(c.getX() - 1, c.getY() + 1, c.getZ());
                break;
            case 6:
                orientationCoordinates[0] = new Coordinate(c.getX() - 1, c.getY() + 1, c.getZ());
                orientationCoordinates[1] = new Coordinate(c.getX(), c.getY() + 1, c.getZ() - 1);
                break;
        }
        return orientationCoordinates;
    }


}
