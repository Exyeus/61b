package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static final long SEED = 114514;
    private static final Random RANDOM = new Random(SEED);

    private static boolean correctBoundary(Position p, int size, TETile[][] world) {
        return true;
    }
    public static void addHexagon(TETile[][] world,
                           Position p, int size, TETile t) {
        /**
         * world: the 2D array to which we will draw those hexagons
         * p: objects containing x and y.
         *    It should be the column 0 of line 0
         * size: the size of a hexagon. Therefore the leftmost will be
         *    x - size + 1, the lowermost will be y +- (2 * size - 1)
         *    the widest line will be size + 2 * (size - 1) = 3 * size - 2
         */
        if (!correctBoundary(p, size, world)) {
            System.out.println("Improper position and size!");
        }
        for (int increment = 0; increment < size; increment++) {
            drawLine(world, new Position(p.x - increment, p.y + increment),
                    size + 2 * increment, t);
        }
        for (int increment = 0; increment < size; increment++) {
            drawLine(world, new Position(p.x - size + 1 + increment,
                    p.y + size + increment),
                    3 * size - 2 - 2 * increment, t);
        }
    }
    private static void drawLine(TETile[][] world,
                                 Position p, int width, TETile t) {
        for (int i = p.x; i < p.x + width; i++) {
            world[i][p.y] = t;
        }
    }
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(3);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.NOTHING;
            default: return Tileset.NOTHING;
        }
    }
    public static void fillWithRandomTiles(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = randomTile();
            }
        }
    }
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][]  world = new TETile[WIDTH][HEIGHT];
        fillWithRandomTiles(world);
        addHexagon(world, new Position(3, 4), 4, Tileset.GRASS);

        ter.renderFrame(world);
    }
}
