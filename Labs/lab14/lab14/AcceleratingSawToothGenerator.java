package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private int state;
    private double factor;

    public AcceleratingSawToothGenerator(int p, double f) {
        period = p;
        state = 0;
        factor = f;
    }

    public double next() {
        if (state < period) {
            double n = normalize(state);
            state++;
            return n;
        }
        state = 0;
        double n = normalize(state);
        state++;
        period = (int) Math.floor(period * factor);
        return n;
    }

    private double normalize(int state) {
        double num = (double) state / (period / 2);
        return num - 1;
    }
}
