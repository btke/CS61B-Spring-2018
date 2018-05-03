package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {
    private int period;
    private int state;
    private int weird;

    public StrangeBitwiseGenerator(int p) {
        period = p;
        state = 0;
        weird = state & (state >>> 3) % period;
    }

    public double next() {
        if (state % period != 0) {
            double n = normalize(weird);
            state++;
            weird = state & (state >>> 3) % period;
            return n;
        }
        state = 0;
        weird = state & (state >>> 3) % period;
        double n = normalize(weird);
        state++;
        weird = state & (state >>> 3) % period;
        return n;
    }

    private double normalize(int state) {
        double num = (double) state / (period / 2);
        return num - 1;
    }

}
