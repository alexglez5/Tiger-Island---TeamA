package Tigerisland;
import java.util.Objects;

/**
 * Created by Alexander Gonzalez on 3/17/2017.
 */
public class Coordinate {
    int x;
    int y;

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getXCoordinate(){
        return x;
    }

    public int getYCoordinate(){
        return y;
    }

    @Override
    public boolean equals(Object o){
        if(o == this)
            return true;
        if(!(o instanceof Coordinate))
            return false;
        Coordinate coord = (Coordinate) o;
        return x == coord.x &&
                Objects.equals(y, coord.y);
    }

    @Override
    public int hashCode(){
        return Objects.hash(x, y);
    }
}
