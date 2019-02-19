package mx.tec.tc3039;

import java.util.function.Predicate;

@FunctionalInterface
interface MiInterfaz {
    String justDoIt(boolean b, int x);
}

public class EjemplosLambdas {

    public static void main(String[] args) {
        Runnable corredor = new Runnable() {
            
            @Override
            public void run() {
                System.out.println("Hola");
            }
        };
        
        corredor.run();
        
        Runnable corredor2 = () -> { System.out.println("Adios"); };
        
        corredor2.run();
        
        Predicate<String> p = s -> s.length() > 10;
        System.out.println(p.test("Alligators and crocodiles"));
        
        MiInterfaz mi = (bo, in) -> "xxxx";
        System.out.println(mi.justDoIt(true, 0));
    }
}
