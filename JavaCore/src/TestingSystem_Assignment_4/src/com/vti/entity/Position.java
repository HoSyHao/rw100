package TestingSystem_Assignment_4.src.com.vti.entity;

import TestingSystem_Assignment_4.src.com.vti.Enum.PositionName;

public class Position {
    private int positionID;
    private PositionName positionName;

    public Position(int positionID, PositionName positionName) {
        this.positionID = positionID;
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
}
