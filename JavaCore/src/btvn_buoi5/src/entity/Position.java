package btvn_buoi5.src.entity;


import btvn_buoi5.src.enums.PositionName;

public class Position {
    private int positionID;
    private PositionName positionName;
    private static int AUTO_ID = 1;

    public Position( PositionName positionName) {
        this.positionID = AUTO_ID++;
        this.positionName = positionName;
    }

    public int getPositionID() {
        return positionID;
    }

    public void setPositionID(int positionID) {
        this.positionID = positionID;
    }

    public PositionName getPositionName() {
        return positionName;
    }

    public void setPositionName(PositionName positionName) {
        this.positionName = positionName;
    }

    @Override
    public String toString() {
        return "Position{" +
                "positionName=" + positionName +
                ", positionID=" + positionID +
                '}';
    }
}

