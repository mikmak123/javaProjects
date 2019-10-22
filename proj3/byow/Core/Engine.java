package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.*;
import java.io.*;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.introcs.StdDraw;

public class Engine {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 35;
    private Random random;
    private WeightedQuickUnionUF connectedRooms;
    private Position[][] positionWorld;
    private List<List<Position>> listRooms;
    private int numRooms;
    private TETile[][] world;
    private Position avatar;
    private String keysEntered;
    private Boolean colonEntered;
    private Boolean qEntered;
    private Boolean nEntered;
    private Boolean lEntered;
    private Boolean rEntered;
    private int flowerScore;


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        TERenderer t = new TERenderer();
        t.initialize(WIDTH + 10, HEIGHT + 10, 5, 5);

        world = new TETile[WIDTH][HEIGHT];
        positionWorld = new Position[WIDTH][HEIGHT];
        connectedRooms = new WeightedQuickUnionUF(WIDTH * HEIGHT);
        listRooms = new ArrayList<>();
        keysEntered = "";
        colonEntered = false;
        qEntered = false;
        nEntered = false;
        lEntered = false;
        rEntered = false;
        flowerScore = 0;

        makeMenu();
        keyboardInputs(t);

    }

    private void makeMenu() {
        int height = (int) (HEIGHT * 0.8);
        int midWidth = WIDTH / 2;
        int midHeight = HEIGHT / 2;
        Font font = new Font("Monaco", Font.BOLD, (int) (WIDTH * 0.5));
        StdDraw.setFont(font);
        String title = "CS61B: THE GAME";
        String ng = "New Game (N)";
        String lg = "Load Game (L)";
        String r = "Replay (R)";
        String q = "Quit (Q)";

        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.ORANGE);
        StdDraw.text(midWidth, height, title);
        StdDraw.text(midWidth, midHeight, ng);
        StdDraw.text(midWidth, midHeight * 0.8, lg);
        StdDraw.text(midWidth, midHeight * 0.6, r);
        StdDraw.text(midWidth, midHeight * 0.4, q);
        StdDraw.show();

    }

    private void replay(TERenderer t) {
        String originalKeys = keysEntered;
        String similarWorld = "";
        int i = 0;
        while (i < originalKeys.length()
                && Character.toLowerCase(originalKeys.charAt(i)) != 's') {
            similarWorld += originalKeys.charAt(i);
            i++;
        }
        similarWorld += originalKeys.charAt(i);
        i++;
        world = interactWithInputString(similarWorld);
        render(t);
        StdDraw.pause(100);
        while (i < originalKeys.length()) {
            flowerScore = 0;
            similarWorld += originalKeys.charAt(i);
            world = interactWithInputString(similarWorld);
            render(t);
            StdDraw.pause(100);
            i++;
        }
    }

    private void render(TERenderer t) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        String stringDate = dateFormat.format(date);
        t.renderFrame(world);
        StdDraw.setPenColor(Color.ORANGE);
        StdDraw.text(WIDTH, HEIGHT + 9, "Score: " + flowerScore);
        StdDraw.text(WIDTH, HEIGHT + 8, stringDate);
        StdDraw.text(6, HEIGHT + 9, "This is " + mouseTile());
        StdDraw.show();
    }

    private String mouseTile() {
        int x = (int) (StdDraw.mouseX() - 5);
        int y = (int) (StdDraw.mouseY() - 5);
        if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
            TETile tile = world[x][y];
            if (tile.equals(Tileset.NOTHING)) {
                return "nothing";
            } else if (tile.equals(Tileset.FLOWER)) {
                return "a flower";
            } else if (tile.equals(Tileset.FLOOR)) {
                return "a floor";
            } else if (tile.equals(Tileset.WALL)) {
                return "a wall";
            }
        }
        return "outside the world";
    }

    private void keyboardInputs(TERenderer t) {
        while (!qEntered) {
            if (nEntered || lEntered) {
                render(t);
                StdDraw.pause(20);
            }
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toLowerCase(StdDraw.nextKeyTyped());
                characterOptions(c, t);
            }
        }
    }

    private void characterOptions(char c, TERenderer t) {
        Font newFont = new Font("Monaco", Font.BOLD, (int) (WIDTH * 0.175));

        switch (c) {
            case 'n':
                nEntered = true;
                keysEntered += c;
                inputSeed();
                StdDraw.setFont(newFont);
                render(t);
                break;
            case ':':
                colonEntered = true;
                break;
            case 'q':
                if (!nEntered && !lEntered) {
                    System.exit(0);
                    break;
                }
                if (colonEntered) {
                    saveFile();
                    qEntered = true;
                }

                break;
            case 'l':
                lEntered = true;
                loadFile();
                StdDraw.setFont(newFont);
                render(t);
                break;
            case 'r':
                lEntered = false;
                StdDraw.setFont(newFont);
                loadFile();
                replay(t);
                break;
            case 'w':
                keysEntered += c;
                moveUp();
                render(t);
                break;
            case 's':
                keysEntered += c;
                moveDown();
                render(t);
                break;
            case 'a':
                keysEntered += c;
                moveLeft();
                render(t);
                break;
            case 'd':
                keysEntered += c;
                moveRight();
                render(t);
                break;
            default:
                break;
        }
    }

    private void saveFile() {
        File f = new File("save_data.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(keysEntered);
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    private void loadFile() {
        File f = new File("save_data.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                keysEntered = (String) os.readObject();
                world = interactWithInputString(keysEntered);
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }
    }

    private void inputSeed() {
        int midWidth = WIDTH / 2;
        int height = (int) (HEIGHT * 0.8);
        Font font = new Font("Monaco", Font.BOLD, (int) (WIDTH * 0.5));
        StdDraw.setFont(font);
        String string1 = "Please enter a random seed";
        String string2 = "Press S when you are done:";

        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.ORANGE);
        StdDraw.text(midWidth, height, string1);
        StdDraw.text(midWidth, height * 0.6, string2);
        StdDraw.show();

        StringBuilder seed = new StringBuilder();
        String digits = "";
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                keysEntered += c;
                if (Character.isDigit(c)) {
                    seed.append(c);
                    digits += c;
                    StdDraw.clear(Color.BLACK);
                    StdDraw.setPenColor(Color.ORANGE);
                    StdDraw.text(midWidth, height, string1);
                    StdDraw.text(midWidth, height * 0.8, digits);
                    StdDraw.text(midWidth, height * 0.6, string2);
                    StdDraw.show();
                }
                if (Character.toLowerCase(c) == 's') {
                    String stringSeed = seed.toString();
                    long intSeed = Long.parseLong(stringSeed);
                    random = new Random(intSeed);
                    generateWorld();

                    break;
                }
            }
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        world = new TETile[WIDTH][HEIGHT];
        positionWorld = new Position[WIDTH][HEIGHT];
        keysEntered = "";
        colonEntered = false;
        qEntered = false;

        int i = 0;
        char letter;
        while (i < input.length() && !qEntered) {
            letter = Character.toLowerCase(input.charAt(i));
            i += 1;
            switch (letter) {
                case 'n':
                    nEntered = true;
                    keysEntered += letter;
                    listRooms = new ArrayList<>();
                    long seed = findSeed(input);
                    random = new Random(seed);
                    connectedRooms = new WeightedQuickUnionUF(WIDTH * HEIGHT);
                    generateWorld();
                    while (i < input.length() && Character.isDigit(input.charAt(i))) {
                        i += 1;
                    }
                    i += 1; //to include the s that closes seed
                    break;
                case ':':
                    colonEntered = true;
                    break;
                case 'q':
                    if (!nEntered && !lEntered) {
                        System.exit(0);
                        break;
                    }
                    if (colonEntered) {
                        saveFile();
                        qEntered = true;
                    }
                    break;
                case 'l':
                    lEntered = true;
                    loadFile();
                    break;
                case 'w':
                    keysEntered += letter;

                    moveUp();
                    break;
                case 's':
                    keysEntered += letter;

                    moveDown();
                    break;
                case 'a':
                    keysEntered += letter;

                    moveLeft();
                    break;
                case 'd':
                    keysEntered += letter;

                    moveRight();
                    break;
                default:
                    break;

            }

        }
        return world;

    }

    private void moveUp() {
        int x = avatar.getX();
        int y = avatar.getY();
        TETile tileType = world[x][y + 1];
        if (tileType.equals(Tileset.FLOOR) || tileType.equals(Tileset.FLOWER)) {
            world[x][y + 1] = Tileset.AVATAR;
            positionWorld[x][y + 1].updateTileType(Tileset.AVATAR);
            world[x][y] = Tileset.FLOOR;
            positionWorld[x][y].updateTileType(Tileset.FLOOR);
            avatar.updateX(x);
            avatar.updateY(y + 1);
        }
        if (tileType.equals(Tileset.FLOWER)) {
            flowerScore += 1;
        }
    }

    private void moveDown() {
        int x = avatar.getX();
        int y = avatar.getY();
        TETile tileType = world[x][y - 1];
        if (tileType.equals(Tileset.FLOOR) || tileType.equals(Tileset.FLOWER)) {
            world[x][y - 1] = Tileset.AVATAR;
            positionWorld[x][y - 1].updateTileType(Tileset.AVATAR);
            world[x][y] = Tileset.FLOOR;
            positionWorld[x][y].updateTileType(Tileset.FLOOR);
            avatar.updateX(x);
            avatar.updateY(y - 1);
        }
        if (tileType.equals(Tileset.FLOWER)) {
            flowerScore += 1;
        }
    }

    private void moveLeft() {
        int x = avatar.getX();
        int y = avatar.getY();
        TETile tileType = world[x - 1][y];
        if (tileType.equals(Tileset.FLOOR) || tileType.equals(Tileset.FLOWER)) {
            world[x - 1][y] = Tileset.AVATAR;
            positionWorld[x - 1][y].updateTileType(Tileset.AVATAR);
            world[x][y] = Tileset.FLOOR;
            positionWorld[x][y].updateTileType(Tileset.FLOOR);
            avatar.updateX(x - 1);
            avatar.updateY(y);
        }
        if (tileType.equals(Tileset.FLOWER)) {
            flowerScore += 1;
        }
    }

    private void moveRight() {
        int x = avatar.getX();
        int y = avatar.getY();
        TETile tileType = world[x + 1][y];
        if (tileType.equals(Tileset.FLOOR) || tileType.equals(Tileset.FLOWER)) {
            world[x + 1][y] = Tileset.AVATAR;
            positionWorld[x + 1][y].updateTileType(Tileset.AVATAR);
            world[x][y] = Tileset.FLOOR;
            positionWorld[x][y].updateTileType(Tileset.FLOOR);
            avatar.updateX(x + 1);
            avatar.updateY(y);
        }
        if (tileType.equals(Tileset.FLOWER)) {
            flowerScore += 1;
        }
    }

    private long findSeed(String input) {
        int i = 1;
        long seed = 0;
        StringBuilder num = new StringBuilder();
        while (i < input.length() && Character.isDigit(input.charAt(i))) {
            num = num.append(input.charAt(i));
            i++;
        }
        String stringSeed = num.toString();
        seed = Long.parseLong(stringSeed);
        num = num.append(input.charAt(i));
        stringSeed = num.toString();
        keysEntered += stringSeed;

        return seed;
    }

    private void generateWorld() {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
                positionWorld[x][y] = new Position(x, y, calcposID(x, y), Tileset.NOTHING);

            }
        }

        fillwithRooms();
        makeHallways();
        createAvatar();
    }

    private void fillwithRooms() {
        int minRoomArea = (int) ((WIDTH * HEIGHT) * 0.025);
        int maxRoomArea = (int) ((WIDTH * HEIGHT) * 0.04);
        int totalRoomArea = WIDTH * HEIGHT;
        int maxnumRooms = totalRoomArea / minRoomArea;
        int minnumRooms = totalRoomArea / maxRoomArea;
        numRooms = random.nextInt(maxnumRooms - minnumRooms + 1) + minnumRooms;
        for (int i = 0; i < 2 * numRooms; i += 1) {
            fillSingleRoom(minRoomArea, maxRoomArea, i);
        }

    }

    private void fillSingleRoom(int minArea, int maxArea, int roomNum) {
        int area = 0;
        int x = 0;
        int y = 0;
        int width = 0;
        int height = 0;

        while (area < minArea || area > maxArea) {
            x = random.nextInt(WIDTH - 2);
            y = random.nextInt(HEIGHT - 2);
            width = random.nextInt(WIDTH - x - 2) + 3;
            height = random.nextInt(HEIGHT - y - 2) + 3;
            area = width * height;
        }

        Position pos = new Position(x, y, calcposID(x, y), Tileset.WALL);

        generateRoom(Tileset.FLOOR, Tileset.WALL, width, height, pos);
    }

    private void generateRoom(TETile floor, TETile wall, int w, int h, Position position) {
        putRoomintoWorld(floor, w, h, position);
        putWallaroundRoom(wall, w, h, position);
    }


    private void putRoomintoWorld(TETile floor, int w, int h, Position position) {
        int endX = position.getX() + w - 1;
        int endY = position.getY() + h - 1;

        for (int row = position.getY() + 1; row <= endY - 1; row += 1) {
            for (int col = position.getX() + 1; col <= endX - 1; col += 1) {
                world[col][row] = floor;
                positionWorld[col][row].updateTileType(floor);

            }
        }
    }

    private void putWallaroundRoom(TETile wall, int w, int h, Position position) {
        int startX = position.getX();
        int endX = startX + w - 1;
        int startY = position.getY();
        int endY = startY + h - 1;
        for (int col = startX; col <= endX; col += 1) {
            wallGeneration(col, startY, wall);
            wallGeneration(col, endY, wall);
        }
        for (int row = startY; row <= endY; row += 1) {
            wallGeneration(startX, row, wall);
            wallGeneration(endX, row, wall);
        }

    }

    private void wallGeneration(int x, int y, TETile wallType) {
        world[x][y] = wallType;
        positionWorld[x][y].updateTileType(wallType);
    }

    private void makeHallways() {
        createListofRooms();
        connectTilesinRoom();
        connectAllRooms();
    }

    private void createListofRooms() {
        List<Position> fringe = new ArrayList<>();
        fringe.add(positionWorld[0][0]);
        while (!fringe.isEmpty()) {
            List<Position> pointsinRoom = new ArrayList<>();
            Position pos = fringe.remove(0);
            if (!pos.getTraveresed()) {
                traverseOneSetTiles(pos, pointsinRoom, fringe);
                listRooms.add(pointsinRoom);
            }

        }
    }


    private void traverseOneSetTiles(Position pos, List<Position> pointsinRoom,
                                               List<Position> fringe) {
        pointsinRoom.add(pos);
        pos.updateTraveresed(true);

        if (pos.getX() != 0) {
            Position left = positionWorld[pos.getX() - 1][pos.getY()];
            if (!left.getTraveresed()) {
                helperTraverse(pos, left, pointsinRoom, fringe);
            }
        }

        if (pos.getX() != WIDTH - 1) {
            Position right = positionWorld[pos.getX() + 1][pos.getY()];
            if (!right.getTraveresed()) {
                helperTraverse(pos, right, pointsinRoom, fringe);

            }
        }

        if (pos.getY() != 0) {
            Position bot = positionWorld[pos.getX()][pos.getY() - 1];
            if (!bot.getTraveresed()) {
                helperTraverse(pos, bot, pointsinRoom, fringe);

            }
        }

        if (pos.getY() != HEIGHT - 1) {
            Position top = positionWorld[pos.getX()][pos.getY() + 1];
            if (!top.getTraveresed()) {
                helperTraverse(pos, top, pointsinRoom, fringe);

            }
        }
    }

    private void helperTraverse(Position curPos, Position newPos,
                                List<Position> pointsinRoom, List<Position> fringe) {
        if (newPos.gettileType().equals(curPos.gettileType())) {
            traverseOneSetTiles(newPos, pointsinRoom, fringe);
        } else {
            fringe.add(newPos);
        }
    }


    private void connectTilesinRoom() {
        for (List<Position> room: listRooms) {
            if (room.get(0).gettileType().equals(Tileset.FLOOR)) {
                int firstTileID = room.get(0).getID();
                for (Position tile: room) {
                    connectedRooms.union(tile.getID(), firstTileID);
                }
            }
        }
    }

    private void connectAllRooms() {
        List<Object> values = generateKDTree();
        List<Point> listMidPoints = (List<Point>) values.get(0);
        List<Position> listMidPositions = (List<Position>) values.get(1);
        Map<Position, Point> posPointMap = (Map<Position, Point>) values.get(2);
        WeightedQuickUnionUF connectedMidPoints = new WeightedQuickUnionUF(WIDTH * HEIGHT);
        for (Position midPosition: listMidPositions) {
            Point midPoint = posPointMap.get(midPosition);
            if (listMidPoints.size() > 1) {
                listMidPoints.remove(midPoint);
                KDTree kdt = new KDTree(listMidPoints);
                Point nearestmidPoint = kdt.nearest(midPosition.getX(), midPosition.getY());
                Position nearestPosition = pointToPosition(nearestmidPoint, listMidPositions);
                boolean connectedPositions = connectedMidPoints.connected(nearestPosition.getID(),
                        midPosition.getID());
                if (!connectedPositions) {
                    connectedMidPoints.union(nearestPosition.getID(), midPosition.getID());
                    buildHallway(nearestPosition, midPosition);
                }
            }
        }
    }

    private List<Object> generateKDTree() {
        List<Position> listMidPositions = new ArrayList<>();
        List<Point> listMidPoints = new ArrayList<>();
        Map<Position, Point> posPointMap = new HashMap<>();
        for (List<Position> roomTiles: listRooms) {
            if (roomTiles.get(0).gettileType().equals(Tileset.FLOOR)) {
                Room roomInstance = new Room(Tileset.FLOOR, roomTiles);
                int x = roomInstance.midpoint.getX();
                int y = roomInstance.midpoint.getY();
                world[x][y] = Tileset.FLOWER;
                roomInstance.midpoint.updateTileType(Tileset.FLOWER);
                Point midpoint = new Point(x, y);
                listMidPoints.add(midpoint);
                listMidPositions.add(roomInstance.midpoint);
                posPointMap.put(roomInstance.midpoint, midpoint);
            }
        }
        List<Object> rtr = new ArrayList<>();
        rtr.add(listMidPoints);
        rtr.add(listMidPositions);
        rtr.add(posPointMap);
        return rtr;
    }

    private Position pointToPosition(Point point, List<Position> listPositions) {
        for (Position pos: listPositions) {
            if (point.getX() == pos.getX() && point.getY() == pos.getY()) {
                return pos;
            }
        }
        return null;
    }

    private void buildHallway(Position pos1, Position pos2) {
        int pos1X = pos1.getX();
        int pos1Y = pos1.getY();
        int pos2X = pos2.getX();
        int pos2Y = pos2.getY();

        int minX = Math.min(pos1X, pos2X);
        int maxX = Math.max(pos1X, pos2X);
        int minY = Math.min(pos1Y, pos2Y);
        int maxY = Math.max(pos1Y, pos2Y);

        if (pos1X == minX) {
            if (pos1Y == minY) {
                straightHallway(minY, minX, maxX, true);
                straightHallway(maxX, minY, maxY, false);
            } else {
                straightHallway(minY, minX, maxX, true);
                straightHallway(minX, minY, maxY, false);
            }
        } else {
            if (pos1Y == minY) {
                straightHallway(minY, minX, maxX, true);
                straightHallway(minX, minY, maxY, false);
            } else {
                straightHallway(minY, minX, maxX, true);
                straightHallway(maxX, minY, maxY, false);
            }
        }

    }

    private void straightHallway(int same, int cor1, int cor2, boolean isX) {
        int gap = Math.max(cor1, cor2) - Math.min(cor2, cor1);
        int min = Math.min(cor2, cor1);
        for (int i = 0; i < gap; i += 1) {
            if (!isX) {
                world[same][min + i] = Tileset.FLOOR;
                positionWorld[same][min + i].updateTileType(Tileset.FLOOR);
                if (world[same][min + i + 1] == Tileset.NOTHING) {
                    world[same][min + i + 1] = Tileset.WALL;
                }

                if (world[same][min + i - 1] == Tileset.NOTHING) {
                    world[same][min + i - 1] = Tileset.WALL;
                }

                if (world[same - 1][min + i] == Tileset.NOTHING) {
                    world[same - 1][min + i] = Tileset.WALL;
                }

                if (world[same + 1][min + i] == Tileset.NOTHING) {
                    world[same + 1][min + i] = Tileset.WALL;
                }

                if (world[same + 1][min + i + 1] == Tileset.NOTHING) {
                    world[same + 1][min + i + 1] = Tileset.WALL;
                }

                if (world[same + 1][min + i - 1] == Tileset.NOTHING) {
                    world[same + 1][min + i - 1] = Tileset.WALL;
                }

                if (world[same - 1][min + i - 1] == Tileset.NOTHING) {
                    world[same - 1][min + i - 1] = Tileset.WALL;
                }

                if (world[same - 1][min + i + 1] == Tileset.NOTHING) {
                    world[same - 1][min + i + 1] = Tileset.WALL;
                }

            } else {
                world[min + i][same] = Tileset.FLOOR;
                positionWorld[min + i][same].updateTileType(Tileset.FLOOR);

                if (world[min + i + 1][same] == Tileset.NOTHING) {
                    world[min + i + 1][same] = Tileset.WALL;
                }

                if (world[min + i - 1][same] == Tileset.NOTHING) {
                    world[min + i - 1][same] = Tileset.WALL;
                }

                if (world[min + i][same - 1] == Tileset.NOTHING) {
                    world[min + i][same - 1] = Tileset.WALL;
                }

                if (world[min + i][same + 1] == Tileset.NOTHING) {
                    world[min + i][same + 1] = Tileset.WALL;
                }

                if (world[min + i + 1][same + 1] == Tileset.NOTHING) {
                    world[min + i + 1][same + 1] = Tileset.WALL;
                }

                if (world[min + i - 1][same + 1] == Tileset.NOTHING) {
                    world[min + i - 1][same + 1] = Tileset.WALL;
                }

                if (world[min + i - 1][same - 1] == Tileset.NOTHING) {
                    world[min + i - 1][same - 1] = Tileset.WALL;
                }

                if (world[min + i + 1][same - 1] == Tileset.NOTHING) {
                    world[min + i + 1][same - 1] = Tileset.WALL;
                }

            }

        }

    }

    private void createAvatar() {
        int x = random.nextInt(WIDTH);
        int y = random.nextInt(HEIGHT);
        while (!world[x][y].equals(Tileset.FLOOR)) {
            x = random.nextInt(WIDTH);
            y = random.nextInt(HEIGHT);
        }

        world[x][y] = Tileset.AVATAR;
        positionWorld[x][y].updateTileType(Tileset.AVATAR);
        avatar = positionWorld[x][y];
    }

    private class Room {
        private TETile floorType;
        private List<Position> tiles;
        private KDTree kdt;
        private Position midpoint;

        private Room(TETile floor, List<Position> listTiles) {
            floorType = floor;
            tiles = listTiles;
            midpoint = calculateMiddleTile();
        }

        private Position calculateMiddleTile() {
            putTilesinKDT();
            Point centroid = findCentroid();
            Point nearestPoint = kdt.nearest(centroid.getX(), centroid.getY());
            Position nearestTile = findNearestTile(nearestPoint);
            return nearestTile;

        }

        private void putTilesinKDT() {
            List<Point> points = new ArrayList<>();
            for (Position tile: tiles) {
                Point point = new Point(tile.getX(), tile.getY());
                points.add(point);
            }
            kdt = new KDTree(points);
        }

        private Point findCentroid() {
            double totalX = 0;
            double totalY = 0;
            for (Position tile: tiles) {
                totalX += tile.getX();
                totalY += tile.getY();
            }
            double centerX = totalX / tiles.size();
            double centerY = totalY / tiles.size();
            Point point = new Point(centerX, centerY);
            return point;
        }

        private Position findNearestTile(Point nearestPoint) {
            Position nearestTile = tiles.get(0);

            for (Position tile: tiles) {
                int tileX = tile.getX();
                int tileY = tile.getY();
                int nearestX = (int) nearestPoint.getX();
                int nearestY = (int) nearestPoint.getY();
                if (tileX == nearestX && tileY == nearestY) {
                    nearestTile = tile;
                }
            }

            return nearestTile;
        }

    }

    private int calcposID(int x, int y) {
        if (x == 0 && y == 0) {
            return 0;
        } else if (x == 0) {
            return y * WIDTH;
        } else if (y == 0) {
            return x;
        }

        int id = (y * WIDTH) + x;
        return id;
    }

    public static void main(String[] args) {

        TERenderer t = new TERenderer();
        t.initialize(WIDTH + 10, HEIGHT + 10, 5, 5);
        Engine test = new Engine();
        /*TETile[][] cmon = test.interactWithInputString("lwwwww");
        t.renderFrame(cmon);*/
        for (int i = 0; i < 100; i += 1) {
            TETile[][] cmon = test.interactWithInputString("n" + i + "s");
            t.renderFrame(cmon);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }


    }
}
