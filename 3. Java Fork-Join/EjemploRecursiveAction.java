package mx.tec.tc3039;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class EjemploRecursiveAction {
    
    public static class SquareAction extends RecursiveAction {

        private static final long serialVersionUID = 1L;

        private int lo, hi;
        private int[] array;
        public static final int UMBRAL = 1_000;
        
        public SquareAction(int lo, int hi, int[] array) {
            this.lo = lo;
            this.hi = hi;
            this.array = array;
        }

        @Override
        protected void compute() {
            if (hi -lo < UMBRAL) {
                for (int i = lo; i <= hi; i++) {
                    array[i] *= array[i];
                    
                    // Efectuar "busy waiting" para forzar
                    // más tiempo de ejecución. 
                    for (long k = 0; k < 1000; k++);
                }
            } else {
                int mid = (hi + lo) >>> 1;
                var a1 = new SquareAction(lo, mid, array);
                var a2 = new SquareAction(mid + 1, hi, array);
                invokeAll(a1, a2);
            }
        }
    }
    
    private static void parallelSquare(int[] array) {
        var pool = new ForkJoinPool();
        var a = new SquareAction(0, array.length - 1, array);
        pool.invoke(a);
    }
    
    private static final int ARRAY_SIZE = 100_000_000;
    
    public static void main(String[] args) {
        int[] array = new int[ARRAY_SIZE];
        Arrays.fill(array, 42);
        
        long inicio = System.nanoTime();
        parallelSquare(array);
        long fin = System.nanoTime();
        double time8;
        
        time8 = (fin - inicio) / 1.0e9;
        System.out.printf("T8 = %.2f%n", time8);
    }
}
