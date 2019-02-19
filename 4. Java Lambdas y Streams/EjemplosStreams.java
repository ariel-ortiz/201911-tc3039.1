package mx.tec.tc3039;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EjemplosStreams {

    public static void main(String[] args) {
        IntStream intstream = IntStream.rangeClosed(1, 1000)
                .map(x -> x * x);
        /*
         * int resultado = intstream.reduce(0, (a, b) -> a + b);
         * System.out.println(resultado);
         */
        Stream<String> strstream = intstream
                .mapToObj(x -> Integer.toString(x));
        strstream.forEach(System.out::println);
    }
}
