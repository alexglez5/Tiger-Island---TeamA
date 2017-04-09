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
