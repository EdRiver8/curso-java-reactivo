package sesion2;

import java.util.List;

// interfaces, todas las interfaces son publicas, son la firma de una clase
// para esta se genera un parametro de tipo generico que puede ser sustituido
// en la clase que implemente la interfaz, como por ejemplo Integer, String, etc.
interface Operaciones<S>{
    int sum(S entrada);
}

// esta clase define el tipo de dato que se va a utilizar en la interfaz, el cual es Integer
class Calculadora implements Operaciones<Integer>{

    @Override
    public int sum(Integer entrada) {
        return entrada + 1;
    }
}

// patron Repository, el generico T, representa la entidad que se va a operar en la base de datos
interface Repository<T, I>{
    void save(T t);
    void delete(I id);
    void update(T t);
    List<T> findAll();
    T findById(I id);
}

class Persona{
    private String nombre;
    private String apellido;
    private int edad;
}

class PersonaRepository implements Repository<Persona, Integer>{

    @Override
    public void save(Persona t) {
        System.out.println("Guardando persona");
    }

    @Override
    public void delete(Integer id) {
        System.out.println("Eliminando persona");
    }

    @Override
    public void update(Persona t) {
        System.out.println("Actualizando persona");
    }

    @Override
    public List<Persona> findAll() {
        System.out.println("Buscando todas las personas");
        return null;
    }

    @Override
    public Persona findById(Integer id) {
        System.out.println("Buscando persona por id");
        return null;
    }
}



public class Main {


    public static void main(String[] args) {
        System.out.println("Hello world!");




    }
}
