package sample;

/**
 * Created by Lenovo on 13.03.2018.
 */
public class Carriage {
    private int coordinateX;
    private int coordinateY;
    private int carriageOfColumn;
    private int carriageOfLine;


    public Carriage(){
        carriageOfLine = 0;
        coordinateX = 5;
        coordinateY = 5;
        carriageOfColumn = 0;
    }

    public int getcarriageOfLine() {
        return carriageOfLine;
    }

    public void setCarriageOfLine(int carriageOfLine) {
        this.carriageOfLine = carriageOfLine;
    }

    public int getCarriageOfColumn() {
        return  carriageOfColumn;
    }

    public void setCarriageOfColumn(int  carriageOfColumn) {
        this. carriageOfColumn = carriageOfColumn;
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }
}
