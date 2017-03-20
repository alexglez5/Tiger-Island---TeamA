package Tigerisland;
/**
 * Created by Alexander Gonzalez on 3/19/2017.
 */
public class TilePlacer extends GameBoard{
    private int mainTerrainXCoordinate;
    private int mainTerrainYCoordinate;

    public void placeTile(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation){
        mainTerrainXCoordinate = mainTerrainCoordinate.getXCoordinate();
        mainTerrainYCoordinate = mainTerrainCoordinate.getYCoordinate();

        gameBoard.put(mainTerrainCoordinate,
                tile.getMainTerrain());

        switch (terrainsOrientation){
            case FromBottom:
                gameBoard.put(belowAndToTheLeftOfMain(), tile.getLeftOfMainTerrain());
                gameBoard.put(belowAndToTheRightOfMain(), tile.getRightOfMainTerrain());
                break;
            case FromBottomRight:
                gameBoard.put(belowAndToTheRightOfMain(), tile.getLeftOfMainTerrain());
                gameBoard.put(toTheRightOfMain(), tile.getRightOfMainTerrain());
                break;
            case FromTopRight:
                gameBoard.put(toTheRightOfMain(), tile.getLeftOfMainTerrain());
                gameBoard.put(overAndToTheRightOfMain(), tile.getRightOfMainTerrain());
                break;
            case FromTop:
                gameBoard.put(overAndToTheRightOfMain(), tile.getLeftOfMainTerrain());
                gameBoard.put(overAndToTheLeftOfMain(), tile.getRightOfMainTerrain());
                break;
            case FromTopLeft:
                gameBoard.put(overAndToTheLeftOfMain(), tile.getLeftOfMainTerrain());
                gameBoard.put(toTheLeftOfMain(), tile.getRightOfMainTerrain());
                break;
            case FromBottomLeft:
                gameBoard.put(toTheLeftOfMain(), tile.getLeftOfMainTerrain());
                gameBoard.put(belowAndToTheLeftOfMain(), tile.getRightOfMainTerrain());
                break;
        }
    }

    /*
    //TODO check if it's legal to place hex at given location
    private boolean isTaken(mainTerrainXCoordinate, mainTerrainYCoordinate){
        return gameBoard.containsKey(new Coordinate(mainTerrainXCoordinate,
                mainTerrainYCoordinate));
    }*/

    private Coordinate belowAndToTheLeftOfMain(){
        return new Coordinate(mainTerrainXCoordinate - 1, mainTerrainYCoordinate + 1);
    }

    private Coordinate belowAndToTheRightOfMain(){
        return new Coordinate(mainTerrainXCoordinate, mainTerrainYCoordinate + 1);
    }

    private Coordinate toTheLeftOfMain(){
        return new Coordinate(mainTerrainXCoordinate - 1, mainTerrainYCoordinate);
    }

    private Coordinate toTheRightOfMain(){
        return new Coordinate(mainTerrainXCoordinate + 1, mainTerrainYCoordinate);
    }

    private Coordinate overAndToTheLeftOfMain(){
        return new Coordinate(mainTerrainXCoordinate, mainTerrainYCoordinate - 1);
    }

    private Coordinate overAndToTheRightOfMain(){
        return new Coordinate(mainTerrainXCoordinate + 1, mainTerrainYCoordinate - 1);
    }
}
