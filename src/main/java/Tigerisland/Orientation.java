package Tigerisland;

import java.util.Random;

public enum Orientation {
    FromBottom(4), FromBottomRight(3), FromTopRight(2),
    FromTop(1), FromTopLeft(6), FromBottomLeft(5);

    private int orientationVal;

    Orientation(int orientationVal){
        this.orientationVal = orientationVal;
    }

    public int getOrientationVal(){
        return orientationVal;
    }

    public static Orientation[] getOrientations(){
        Orientation[] orientations = new Orientation[6];
        orientations[0] = FromTop;
        orientations[1] = FromTopRight;
        orientations[2] = FromBottomRight;
        orientations[3] = FromBottom;
        orientations[4] = FromBottomLeft;
        orientations[5] = FromTopLeft;
        shuffleArray(orientations);
        return orientations;
    }

    // Implementing Fisher–Yates shuffle
    static void shuffleArray(Orientation[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Orientation a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}