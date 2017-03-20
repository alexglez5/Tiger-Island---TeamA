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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (x != that.x) return false;
        return y == that.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
