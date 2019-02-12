package mx.tec.tc3039;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class EjemploRecursiveTask {

    public static class PiTask extends RecursiveTask<Double> {

        private static final long serialVersionUID = 1L;

        public static final int UMBRAL = 1_000;

        private long lo, hi;
        private double width;

        public PiTask(long lo, long hi, double width) {
            this.lo = lo;
            this.hi = hi;
            this.width = width;
        }

        @Override
        protected Double compute() {
            if (hi - lo < UMBRAL) {
                double mid, height, sum = 0.0;
                for (long i = lo; i <= hi; i++) {
                    mid = (i + 0.5) * width;
                    height = 4.0 / (1.0 + mid * mid);
                    sum += height;
                }
                return width * sum;
            } else {
                long mid = (hi + lo) >>> 1;
                var t1 = new PiTask(lo, mid, width);
                var t2 = new PiTask(mid + 1, hi, width);
                t1.fork();
                double r2 = t2.compute();
                double r1 = t1.join();
                return r1 + r2;
            }
        }
    }

    private static final long NUM_RECTS = 1_000_000_000;

    public static double sequentialPi() {
        double mid, height, width, area;
        double sum = 0.0;

        width = 1.0 / (double) NUM_RECTS;
        for (long i = 0; i < NUM_RECTS; i++) {
            mid = (i + 0.5) * width;
            height = 4.0 / (1.0 + mid * mid);
            sum += height;
        }
        area = width * sum;
        return area;
    }

    public static double parallelPi() {
        var pool = new ForkJoinPool();
        var t = new PiTask(0, NUM_RECTS - 1, 1.0 / NUM_RECTS);
        return pool.invoke(t);
    }

    public static void main(String[] args) {
        long inicio = System.nanoTime();
        double result = sequentialPi();
        long fin = System.nanoTime();
        double time1, time8;
        time1 = (fin - inicio) / 1.0e9;
        System.out.printf("Pi = %.10f T1 = %.2f%n", result, time1);
        inicio = System.nanoTime();
        result = parallelPi();
        fin = System.nanoTime();
        time8 = (fin - inicio) / 1.0e9;
        System.out.printf("Pi = %.10f T8 = %.2f%n", result, time8);
        System.out.printf("Speed Up = %.2f%n", time1 / time8);
    }
}
