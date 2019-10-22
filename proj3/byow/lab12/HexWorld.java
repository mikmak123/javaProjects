package byow.lab12;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import byow.Core.Position;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    public static void addHexagon(int length, Position pos,
                                  TETile[][] world, TETile t) {
        if (length < 2) {
            throw new IllegalArgumentException();
        }
        drawBottomHalf(length, pos, world, t);
        drawTopHalf(length, pos, world, t);
    }

    private static void drawBottomHalf(int length, Position pos,
                                       TETile[][] world, TETile t) {

        int startingX = pos.getX();
        int endingX = startingX + length - 1;
        for (int row = pos.getY(); row < pos.getY() + length; row += 1) {
            addtoRow(world, row, startingX, endingX, t);
            startingX -= 1;
            endingX += 1;
        }
    }

    private static void addtoRow(TETile[][] world, int row, int startX,
                                 int endX, TETile t) {
        while (startX <= endX) {
            world[startX][row] = t;
            startX += 1;
        }
    }

    private static void drawTopHalf(int length, Position pos,
                                    TETile[][] world, TETile t) {
        int startingX = pos.getX();
        int endingX = startingX + length - 1;
        for (int row = pos.getY() + 2*length - 1; row > pos.getY() + length - 1; row -= 1) {
            addtoRow(world, row, startingX, endingX, t);
            startingX -= 1;
            endingX += 1;
        }
    }

    /*private static Position botHexPos(Position hexPos, int length) {
        int newHexY = hexPos.getY() - 2*length;
        int newHexX = hexPos.getX();

        Position botPos = new Position(newHexX, newHexY, 1);
        return botPos;
    }*/

    private static void drawCol(int length, int numHex, int colStart, int height,
                                TETile[][] world) {
        int startingRow;
        if (numHex % 2 == 0) {
            startingRow = (height / 2) + ((numHex / 2) * length * 2);
        }
        else {
            startingRow = 0;
        }
    }


    public static void main(String[] args) {

        int width = 50;
        int height = 50;

        TERenderer ter = new TERenderer();
        ter.initialize(width, height);

        TETile[][] world = new TETile[width][height];
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        /*Position pos = new Position(5, 40, 1);
        for (int i = 0; i < 3; i += 1) {
            addHexagon(4, pos, world, Tileset.FLOWER);
            pos = botHexPos(pos, 4);
        }*/

        ter.renderFrame(world);
    }

}
