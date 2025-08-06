package byog.Core;

import byog.TileEngine.TETile;

public class Room {

    public Position p1;
    public Position p2;
    TETile[][] belongWorld;
    boolean isConnected = false;
    private final int largerX;
    private final int smallerX;
    private final int largerY;
    private final int smallerY;

    public Room(Position p1, Position p2) {
        this.p1 = p1;
        this.p2 = p2;
        largerX = Math.max(p1.x, p2.x);
        largerY = Math.max(p1.y, p2.y);
        smallerX = Math.min(p1.x, p2.x);
        smallerY = Math.min(p1.y, p2.y);
    }

    /**
     * Connect current Room to another Room in this "world"
     * Using paths of width of 1
     * @param world TETile[][] 2d array
     * @param anotherRoom Another Room in this world
     */
    public void connectTo(TETile[][] world, Room anotherRoom) {
        // Automatically plan the path from one position to another position!

    }

    /**
     * Automatically find way from a Position to another Position
     * @param p1 a position
     * @param p2 another position
     */
    private void makeWay(Position p1, Position p2) {
        // The algorithm:
        // Going through the path possible, and ...
        // Very tricky part! Unable to make clear switch between filling or not filling!
        // Well, just break wall whenever meeting them!
        // There may be continuous meeting walls, and due to our limitation
        // on the size of rooms and the selection of middle point,
        // the confronted walls must not belong to the target wall!
        // ####
        //    @#######
        // #####     #
        //     #     #
        //     #######
        // What to do? destruct all the walls in the middle of our path!
        Position interPosition = new Position(p1.x, p2.y);

    }
    private Position returnMiddlePoint(Position p1, Position p2) {
        int aveX = Math.round((p1.x + p2.x) / 2);
        int aveY = Math.round((p2.x + p2.y) / 2);
        return new Position(aveX, aveY);
    }

    /**
     * Receive a Position object as parameter, and return a number standing for
     * the status of containing for the Position
     * 1 means the Position is inside the Room;
     * 0 means the Position is on the Room's edge
     * -1 means the Position is outside the Room
     * @param p A Position object
     * @return An int value stands for the situation
     */
    public int containPosition(Position p) {
        if (p.x < largerX
                && p.x > smallerX
                && p.y < largerY
                && p.y > smallerY) {
            return 1;
        } else if ((p.x == smallerX || p.x == largerX) && (p.y <= largerY && p.y >= smallerY)
                || (p.x >= smallerX && p.x <= largerX) && (p.y == smallerY || p.y == largerY)) {
            return 0;
        } else {
            return -1;
        }


    }

}
