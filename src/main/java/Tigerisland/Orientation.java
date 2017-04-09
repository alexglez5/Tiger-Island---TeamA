package Tigerisland;

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
        return orientations;
    }

//
//    public enum EXIT_CODE {
//        A(104), B(203);
//
//        private int numVal;
//
//        EXIT_CODE(int numVal) {
//            this.numVal = numVal;
//        }
//
//        public int getNumVal() {
//            return numVal;
//        }
}
