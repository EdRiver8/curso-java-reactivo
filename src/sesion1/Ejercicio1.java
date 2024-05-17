package sesion1;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Persona {
    String nombre;
    int edad;
    String genero;

    public Persona(String nombre, int edad, String genero) {
        this.nombre = nombre;
        this.edad = edad;
        this.genero = genero;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getGenero() {
        return genero;
    }

    @Override
    public String toString() {
        return nombre + " - " + edad + " - " + genero;
    }
}
public class Ejercicio1 {
    public static void main(String[] args) {
        List<Persona> personas = Arrays.asList(
                new Persona("Juan", 25, "Masculino"),
                new Persona("María", 30, "Femenino"),
                new Persona("Pedro", 40, "Masculino"),
                new Persona("Ana", 20, "Femenino")
        );

        // Filter: Filtrar personas mayores de 25 años.
        List<Persona> personasMayoresDe25 = personas.stream()
                .filter(persona -> persona.getEdad() > 25)
                .collect(Collectors.toList());

        // Map: Obtener una lista con los nombres de las personas.
        List<String> nombres = personas.stream()
                .map(Persona::getNombre)
                .collect(Collectors.toList());

        // Reduce: Obtener la suma de las edades de todas las personas.
        int sumaEdades = personas.stream()
                .map(Persona::getEdad)
                .reduce(0, Integer::sum);

        // Contar la cantidad de personas de cada género
        long cantidadMasculinos = personas.stream()
                .filter(persona -> persona.getGenero().equals("Masculino"))
                .count();

        long cantidadFemeninos = personas.stream()
                .filter(persona -> persona.getGenero().equals("Femenino"))
                .count();


        System.out.println("Personas mayores de 25 años: " + personasMayoresDe25);
        System.out.println("Nombres de las personas: " + nombres);
        System.out.println("Suma de las edades: " + sumaEdades);
        System.out.println("Cantidad de personas masculinas: " + cantidadMasculinos);
        System.out.println("Cantidad de personas femeninas: " + cantidadFemeninos);

        // Calcular el promedio de edades de las personas.
        double promedioEdades = personas.stream()
                .mapToInt(Persona::getEdad)
                .average()
                .orElse(0);

        System.out.println("Promedio de edades: " + promedioEdades);

        // Encontrar la persona de mayor edad.
        Persona personaMayorEdad = personas.stream()
                .max((p1, p2) -> p1.getEdad() - p2.getEdad())
                .orElse(null);

        System.out.println("Persona de mayor edad: " + personaMayorEdad);
    }
}
