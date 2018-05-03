package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;

    public static int leftStart = 0;
    public static int hexagonSize;

    public static int calculateSpace(int N) {
        int hexLines = hexagonSize * 2;
        int totalLines = N * hexLines;
        return (HEIGHT - totalLines) / 2;
    }

    public static int calculatePush() {
        return hexagonSize - 1;
    }

    public static void changeLeftStart(){
        leftStart += calcMaxHexWidth() - hexagonSize + 1;
    }

    public static int calcMaxHexWidth(){
        int value = hexagonSize;
        for (int i = 0; i < hexagonSize - 1; i+=1){
            value += 2;
        }

        return value;
    }

    public static void drawColumnHex(TETile[][] world, int N, int size, TETile[] tile){
        int Ypush = size * 2;
        int startPosY = calculateSpace(N);
        for (int i = 0; i < N; i+=1){
            drawHex(world, leftStart, startPosY + Ypush * i, size, tile[i]);
        }

        changeLeftStart();
    }

    public static void drawHex(TETile[][] world, int startPosX, int startPosY, int size, TETile tile){
        drawHexTop(world, startPosX, startPosY, size, tile);
        drawHexBottom(world, startPosX, startPosY + size, size, tile);
    }

    public static void drawHexTop(TETile[][] world, int startPosX, int startPosY, int size, TETile tile){
        int startValueX = startPosX + calculatePush();
        int lineSize = size;

        for (int i = 0; i < size; i+=1){

            for (int k = 0; k < lineSize; k+=1){
                world[startValueX + k][startPosY] = tile;
            }

            startValueX -= 1;
            startPosY += 1;
            lineSize += 2;
        }
    }

    public static void drawHexBottom(TETile[][] world, int startPosX, int startPosY, int size, TETile tile){
        int startValueX = startPosX;
        int lineSize = calcMaxHexWidth();

        for (int i = 0; i < size; i+=1){

            for (int k = 0; k < lineSize; k+=1){
                world[startValueX + k][startPosY] = tile;
            }

            startValueX += 1;
            startPosY += 1;
            lineSize -= 2;
        }
    }

    public static void initialize(TETile[][] world){
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public static void main(String[] args){
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        hexagonSize = 3;

        TETile[][] tiles = new TETile[WIDTH][HEIGHT];
        initialize(tiles);

        //tiles[1][0] = Tileset.WALL;
        TETile[] skins = new TETile[]{Tileset.WALL, Tileset.WATER, Tileset.GRASS};
        drawColumnHex(tiles, 3, hexagonSize, skins);

        skins = new TETile[]{Tileset.SAND, Tileset.MOUNTAIN, Tileset.TREE, Tileset.FLOWER};
        drawColumnHex(tiles, 4, hexagonSize, skins);

        skins = new TETile[]{Tileset.FLOOR, Tileset.WATER, Tileset.WALL, Tileset.GRASS, Tileset.FLOOR};
        drawColumnHex(tiles, 5, hexagonSize, skins);

        skins = new TETile[]{Tileset.SAND, Tileset.MOUNTAIN, Tileset.TREE, Tileset.FLOWER};
        drawColumnHex(tiles, 4, hexagonSize, skins);

        skins = new TETile[]{Tileset.WALL, Tileset.WATER, Tileset.GRASS};
        drawColumnHex(tiles, 3, hexagonSize, skins);
        ter.renderFrame(tiles);
    }
}
