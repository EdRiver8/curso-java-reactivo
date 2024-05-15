package sesion3;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        // para trabajar flujos, se requieren listados, arreglos o objetos tipo lista
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        // los predicados son funciones que retornan un valor booleano, evaluan una condición y retorna true o false
        // en este caso, se evalua si el número es par
        Predicate<Integer> isEven = number -> number % 2 == 0;
        var evenNumbers = filter(numbers, isEven); // se filtran los números pares
        System.out.println("Números pares: " + evenNumbers);

        List<String> sayayins = Arrays.asList("Goku", "Vegeta", "Gohan", "Trunks", "Goten", "Bardock", "Raditz");
        Predicate<String> isGoku = name -> name.equals("Goku");
        var goku = filter(sayayins, isGoku); // se filtra el nombre de Goku
        System.out.println("Goku: " + goku);

        Predicate<String> isG = name -> name.startsWith("G");
        var namesWithG = filter(sayayins, isG); // se filtran los nombres que empiezan con G
        System.out.println("Nombres que empiezan con G: " + namesWithG);

        Predicate<String> isLengthGreaterThan5 = name -> name.length() > 5;
        var namesWithLengthGreaterThan5 = filter(sayayins, isLengthGreaterThan5); // se filtran los nombres con más de 5 letras
        System.out.println("Nombres con más de 5 letras: " + namesWithLengthGreaterThan5);

        Predicate<String> isLengthLessThan5 = name -> name.length() < 5;
        var namesWithLengthLessThan5 = filter(sayayins, isLengthLessThan5); // se filtran los nombres con menos de 5 letras
        System.out.println("Nombres con menos de 5 letras: " + namesWithLengthLessThan5);

        Predicate<String> isLengthEqualTo5 = name -> name.length() == 5;
        var namesWithLengthEqualTo5 = filter(sayayins, isLengthEqualTo5); // se filtran los nombres con 5 letras
        System.out.println("Nombres con 5 letras: " + namesWithLengthEqualTo5);

        // las funciones son funciones que reciben un parámetro y retornan un valor de cualquier tipo
        // en este caso, se convierten los nombres a mayúsculas
//        Function<String, String> toUpperCase = name -> name.toUpperCase();
        Function<String, String> toUpperCase = String::toUpperCase;// referencia a un método, aplica cuando la función recibe un solo parámetro y la entrada y salida son del mismo tipo
        var upperCaseNames = map(sayayins, toUpperCase); // se convierten los nombres a mayúsculas
        System.out.println("Nombres en mayúsculas: " + upperCaseNames);

        // los consumidores son funciones que reciben un parámetro y no retornan nada (void) y se utilizan para
        // realizar acciones, en este caso, se imprime el nombre
//        Consumer<String> printName = name -> System.out.println(name);// referencia a un método, aplica cuando la función recibe un solo parámetro y no retorna nada
        Consumer<String> printName = System.out::println;

        // el supplier (proveedor) es una función que no recibe parámetros y retorna un valor
        // en este caso, se crea un proveedor que retorna un número aleatorio
        Supplier<Integer> random = () -> new Random().nextInt(100);
        System.out.println("Número aleatorio: " + random.get());

        // se crea un flujo de datos con los nombres de los sayayins
        // el map se utiliza para transformar los datos, en este caso, se transforman los nombres a mayúsculas
        var map = Stream.of("Goku", "Vegeta", "Gohan", "Trunks", "Goten", "Bardock", "Raditz")
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) {
                        return s.toUpperCase();
                    }
                }).collect(Collectors.toList());

        // se imprime el flujo de datos, se imprime cada nombre en mayúsculas
        map.forEach(new Consumer<String>(){
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });

        // se crea un flujo de datos con los nombres de los sayayins
        // el flatmap se utiliza para trabajar con flujos de datos anidados, en este caso, se crea un flujo de datos
        // con los nombres de los sayayins y se imprime cada nombre según la cantidad de números
        var flatmap = Stream.of(1, 2, 3)
                .flatMap(new Function<Integer, Stream<?>>() {
                    @Override
                    public Stream<?> apply(Integer integer) {
                        return Stream.of("Gogeta", "Vegito", "Gotenks");
                    }
                }).collect(Collectors.toList());

        // se imprime el flujo de datos, se imprime cada nombre de sayayin segun la cantidad de números
        flatmap.forEach(new Consumer<Object>(){
            @Override
            public void accept(Object o) {
                System.out.println(o);
            }
        });
    }

    // se crea una funcion que recibe una lista y un predicado, y retorna una lista, esto se hace para poder
    // reutilizar el código y no tener que escribir el mismo código varias veces, por ello recibe
    // cualquier tipo de lista y cualquier tipo de predicado
    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        return list.stream().filter(predicate).toList();
    }

    public static <T, R> List<R> map(List<T> list, Function<T, R> function) {
        return list.stream().map(function).toList();
    }
}

