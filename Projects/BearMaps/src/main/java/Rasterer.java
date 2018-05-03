import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    private double tile = MapServer.TILE_SIZE;
    private double rootUllat = MapServer.ROOT_ULLAT;
    private double rootUllon = MapServer.ROOT_ULLON;
    private double rootLrlat = MapServer.ROOT_LRLAT;
    private double rootLrlon = MapServer.ROOT_LRLON;

    private double rootLonDist = rootLrlon - rootUllon;
    private double rootLatDist = rootUllat - rootLrlat;

    public Rasterer() {
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);

        double ulln = params.get("ullon");
        double ullt = params.get("ullat");
        double lrln = params.get("lrlon");
        double lrlt = params.get("lrlat");
        double height = params.get("h");
        double width = params.get("w");

        Map<String, Object> results = new HashMap<>();
        if (!checkQuery(ulln, lrln, ullt, lrlt)) {
            return generateEmptyFalse();
        }


        results.put("query_success", true);
        double queryLonDPP = calcLonDPP(lrln, ulln, width);
        int depth = findDepth(queryLonDPP);
        results.put("depth", depth);

        double eachLonDist = rootLonDist / Math.pow(2, depth);
        double eachLatDist = rootLatDist / Math.pow(2, depth);

        if (ulln < rootUllon) {
            ulln = rootUllon;
        }

        if (lrln > rootLrlon) {
            lrln = rootLrlon;
        }

        if (ullt > rootUllat) {
            ullt = rootUllat;
        }

        if (lrlt < rootLrlat) {
            lrlt = rootLrlat;
        }

        int startX = (int) Math.floor((ulln - rootUllon) / eachLonDist);
        int startY = (int) Math.floor((rootUllat - ullt) / eachLatDist);

        double lonDist = lrln - ((eachLonDist * (double) (startX + 1)) + rootUllon);
        double latDist = (rootUllat - (eachLatDist * (double) (startY + 1))) - lrlt;

        int endX = (int) Math.ceil(lonDist / eachLonDist) + startX;
        int endY = (int) Math.ceil(latDist / eachLatDist) + startY;

        results.put("raster_ul_lon", (double) startX * eachLonDist + rootUllon);
        results.put("raster_ul_lat", rootUllat - (double) startY * eachLatDist);
        results.put("raster_lr_lon", (double) (endX + 1) * eachLonDist + rootUllon);
        results.put("raster_lr_lat", rootUllat - (double) (endY + 1) * eachLatDist);
        results.put("render_grid", generateImageTexts(startX, startY, endX, endY, depth));

        return results;
    }

    private boolean checkQuery(double ulln, double lrln, double ullt, double lrlt) {
        if (lrln < ulln) {
            System.out.println("false first");
            return false;
        }

        if (lrlt > ullt) {
            System.out.println("false second");
            return false;
        }

        boolean start = (ulln >= rootUllon) || (ullt <= rootUllat);
        boolean end = (lrln <= rootLrlon) || (lrlt >= rootLrlat);
        System.out.println(start + " " + end);
        return start && end;
    }

    private Map<String, Object> generateEmptyFalse() {
        Map<String, Object> results = new HashMap<>();
        results.put("query_success", false);
        results.put("render_grid", new String[1][1]);
        results.put("depth", 1);
        results.put("raster_ul_lon", 0.0);
        results.put("raster_ul_lat", 0.0);
        results.put("raster_lr_lon", 0.0);
        results.put("raster_lr_lat", 0.0);
        return results;
    }

    private double calcLonDPP(double lrlon, double ullon, double width) {
        return (lrlon - ullon) / width;
    }

    private int findDepth(double qLonDPP) {
        int depth = 0;
        double rootLonDistTemp = rootLrlon - rootUllon;
        for (int i = 0; i <= 7; i++) {
            double lonDPP = ((rootLonDistTemp) / Math.pow(2, depth)) / tile;
            if (lonDPP <= qLonDPP) {
                return depth;
            }
            depth++;
        }

        return 7;
    }

    private String[][] generateImageTexts(int sX, int sY, int eX, int eY, int d) {
        int xLength = eX - sX + 1;
        int yLength = eY - sY + 1;
        String[][] imgs = new String[yLength][xLength];

        for (int row = 0; row < yLength; row++) {
            for (int col = 0; col < xLength; col++) {
                int x = sX + col;
                int y = sY + row;
                String img = "d" + d + "_x" + x + "_y" + y + ".png";
                imgs[row][col] = img;
            }
        }

        return imgs;
    }

}
