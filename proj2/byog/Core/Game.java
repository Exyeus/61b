package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;


public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private final Room[] roomList = new Room[30];
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     * Consider apply randomUtils to generate rectangular rooms of random vertices(2 Points)
     * The sum? Perhaps trying 20 times is enough. If filled, just let it be.
     * Also, method for connecting disconnected rooms is needed, and
     * its turning point and connecting point can be decided through random numbers.
     * Attention! What if rooms intersect? This is allowed!
     * Fixed Steps -- first edition
     *   1. Organize Room objects, containing 2 positions, and isIntersected
     *   2. Randomly generate 20 Rooms, store them in an array.
     *      Actually, the number should be determined randomly or by the actual size
     *      1. Fix the boundary;
     *      2. Go through the inner space: If wall exists, erase them with floor
     *          and switch its state of isIntersected
     *   3. Check the array for Rooms disconnected. If there exists, randomly choose a
     *      room and connect the disconnected. One by one, respectively
     * Be cautious to details!
     * There is no point in creating `validPositions
     */
    public static class Room {
        Position p1;
        Position p2;
        boolean isConnected = false;
        public Room(Position p1, Position p2) {
            this.p1 = p1;
            this.p2 = p2;
        }
    }

    /**
     * To turn a horizontal line in TETile[][] world, from current TETile to another
     * @param world A 2d TETile array
     * @param p1 A position, for one end of this line
     * @param p2 Another position
     * @param choice a type of TETile
     */
    public void fillHorizontalWith(TETile[][] world, Position p1, Position p2, TETile choice) {
        if (p1.y != p2.y) {
            throw new IllegalArgumentException("Horizontal lines of TETiles require positions of equal y's");
        }
        for (int xStart = p1.x; xStart <= p2.x; xStart++) {
            world[xStart][p1.y] = choice;
        }
    }

    /**
     * To turn a vertical line in TETile[][] world into a new type of TETile
     * @param world A TETile[][]
     * @param p1 Position of one end
     * @param p2 Position of another end
     * @param choice New type of TETile
     */
    public void fillVerticalWith(TETile[][] world, Position p1, Position p2, TETile choice) {
        if (p1.x != p2.x) {
            throw new IllegalArgumentException("Vertical lines of TETiles require positions of equal x's");
        }
        for (int yStart = p1.y; yStart <= p2.y; yStart++) {
            world[p1.x][yStart] = choice;
        }
    }

    /**
     * Randomly return a room object from Room List
     * Can be used for randomly connecting rooms
     * @param roomList An array for Room Object
     * @return A Room object randomly chosen from the roomList
     */
    private Room returnRandomRoom(Room[] roomList) {
        return roomList[0];
    }
    private boolean validPositions(Position p1, Position p2) {
        return true;
    }

    /**
     * To create rooms from offered 2 positions
     * @param p1 any position, standing for a vertex of this Room
     * @param p2 any position, standing for a vertex of this Room
     */
    private void createRoom(Position p1, Position p2) {

    }

    /**
     * To establish connection among disconnected rooms
     * Use this after all rooms have been randomly generated
     */
    private void connectRooms() {
        for (Room room : roomList) {
            if (!room.isConnected) {
                Room randomRoom = returnRandomRoom(roomList);
                // Randomly choose a room and connect current room to it.
                // Remember to nicely handle overlapped routine.
            }
        }

    }
    private void addRooms(int maxTrialTime) {

    }
    private void generateWorld(int seed) {

    }
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        TETile[][] finalWorldFrame = null;
        return finalWorldFrame;
    }
}
