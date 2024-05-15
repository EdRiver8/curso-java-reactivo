package sesion1;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {

    public static int duplicar(int x){
        return x * 2;
    }

    public static boolean esPar(int x){
        return x % 2 == 0;
    }

    public static <T, R, S> Function<T, S> componer(Function<T, R> f1, Function<R, S> f2){
        return f1.andThen(f2);
    }

    public static List<Integer> duplicarYFiltrarPares(List<Integer> numeros){
        Function<Integer, Integer> f1 = Main::duplicar;
        Predicate<Integer> p1 = Main::esPar;

//        Function<Integer, Integer> duplicarYFiltrar = componer(f1, p1);
//        duplicarYFiltrar.apply(1);
        return numeros.stream()
            .map(f1)
            .filter(p1)
            .collect(Collectors.toList());
    }


    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}