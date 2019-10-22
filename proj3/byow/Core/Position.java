package byow.Core;

import byow.TileEngine.TETile;

public class Position {
    private int x;
    private int y;
    private int id;
    private TETile tileType;
    private boolean traveresed;

    public Position(int inputX, int inputY, int uniqueID, TETile type) {
        x = inputX;
        y = inputY;
        id = uniqueID;
        tileType = type;
        traveresed = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getID() {
        return id;
    }

    public TETile gettileType() {
        return tileType;
    }

    public boolean getTraveresed() {
        return traveresed;
    }

    public void updateTileType(TETile tile) {
        tileType = tile;
    }

    public void updateTraveresed(Boolean beenTraveresed) {
        traveresed = beenTraveresed;
    }

    public void updateX(int newX) {
        x = newX;
    }

    public void updateY(int newY) {
        y = newY;
    }
}
