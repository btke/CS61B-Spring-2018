import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {
    private Picture picture;
    private int width;
    private int height;


    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
        height = picture.height();
        width = picture.width();

    }

    // current picture
    public Picture picture()  {
        return new Picture(this.picture);
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    private double calculateEnergiesX(int x, int y) {
        int leftX = changeX(x, -1);
        int rightX = changeX(x, 1);

        Color left = picture.get(leftX, y);
        Color right = picture.get(rightX, y);

        double rx = Math.abs(left.getRed() - right.getRed());
        double bx = Math.abs(left.getBlue() - right.getBlue());
        double gx = Math.abs(left.getGreen() - right.getGreen());

        return (rx * rx) + (bx * bx) + (gx * gx);

    }

    private double calculateEnergiesY(int x, int y) {
        int upperY = changeY(y, -1);
        int lowerY = changeY(y, 1);

        Color up = picture.get(x, upperY);
        Color down = picture.get(x, lowerY);

        double ry = Math.abs(up.getRed() - down.getRed());
        double by = Math.abs(up.getBlue() - down.getBlue());
        double gy = Math.abs(up.getGreen() - down.getGreen());

        return (ry * ry) + (by * by) + (gy * gy);
    }



    private int changeX(int x, int diff) {
        if (x + diff == width) {
            return 0;
        } else if (x + diff < 0) {
            return width - 1;
        }

        return x + diff;
    }

    private int changeY(int y, int diff) {
        if (y + diff == height) {
            return 0;
        } else if (y + diff < 0) {
            return height - 1;
        }

        return y + diff;
    }


    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= width) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        if (y < 0 || y >= height) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        return calculateEnergiesX(x, y) + calculateEnergiesY(x, y);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        transpose();
        int[] seam = findVerticalSeam();
        transpose();
        return seam;
    }

    private void transpose() {
        Picture temp = new Picture(height, width);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                temp.set(row, col, picture.get(col, row));
            }
        }

        picture = temp;
        int t = width;
        width = height;
        height = t;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] seam = new int[height];
        double totalEnergy = Double.MAX_VALUE;

        for (int col = 0; col < width; col++) {
            int y = 0;
            int x = col;
            int[] temp = new int[height];
            double tempEnergy = energy(x, y);
            temp[y] = x;
            y++;

            double topE = 0.0, leftE = 0.0, rightE = 0.0;


            while (y < height) {
                int top = x;
                int left = x - 1;
                int right = x + 1;

                topE = energy(top, y);
                if (left >= 0) {
                    leftE = energy(left, y);
                } else {
                    leftE = Double.MAX_VALUE;
                }

                if (right < width) {
                    rightE = energy(right, y);
                } else {
                    rightE = Double.MAX_VALUE;
                }

                if (topE <= leftE && topE <= rightE) {
                    tempEnergy += topE;
                    temp[y] = top;
                    x = top;
                } else if (leftE <= topE && leftE <= rightE) {
                    tempEnergy += leftE;
                    temp[y] = left;
                    x = left;
                } else {
                    tempEnergy += rightE;
                    temp[y] = right;
                    x = right;
                }

                y++;
            }

            if (tempEnergy <= totalEnergy) {
                totalEnergy = tempEnergy;
                seam = temp;
            }
        }

        return seam;
    }

    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {
        if (checkSeam(seam)) {
            this.picture = new Picture(SeamRemover.removeHorizontalSeam(this.picture, seam));
            height--;
        } else {
            throw new IllegalArgumentException();
        }
    }

    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {
        if (checkSeam(seam)) {
            this.picture = new Picture(SeamRemover.removeVerticalSeam(this.picture, seam));
            width--;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private boolean checkSeam(int[] seam) {
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                return false;
            }
        }

        return true;
    }
}
