package mx.tec.tc3039;

import java.util.stream.IntStream;

public class StreamPi {

    private static final int numRects = 1_000_000_000;
    private static final double width = 1.0 / (double) numRects;

    private static double doit(int i) {
        double mid = (i + 0.5) * width;
        return 4.0 / (1.0 + mid * mid);
    }

    public static void main(String[] args) {
        long inicio = System.nanoTime();
        double suma = IntStream.range(0, numRects).parallel()
                .mapToDouble(StreamPi::doit).sum();
        double area = suma * width;
        long fin = System.nanoTime();

        System.out.printf("Resultado: %.15f, Tiempo: %.2f%n", area,
                (fin - inicio) / 1.0e9);
    }
}
