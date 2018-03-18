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
        carriageOfColumn = 0;
        carriageOfLine = 0;
        coordinateX = 0;
        coordinateY = 15;
    }

    public int getCarriageOfLine() {
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
