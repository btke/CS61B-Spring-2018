package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    private int period;
    private int state;

    public SawToothGenerator(int p) {
        period = p;
        state = 0;
    }

    public double next() {
        if (state % period != 0) {
            double n = normalize(state);
            state++;
            return n;
        }
        state = 0;
        double n = normalize(state);
        state++;
        return n;
    }

    private double normalize(int state) {
        double num = (double) state / (period / 2);
        return num - 1;
    }

}
