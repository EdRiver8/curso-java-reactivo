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

interface InterfaceA {
    void metodoA();
}

interface InterfaceB {
    void metodoB();
}

class ClaseAB implements InterfaceA, InterfaceB {

    @Override
    public void metodoA() {
        System.out.println("llamando al metodo de la clase A");
    }

    @Override
    public void metodoB() {
        System.out.println("llamando al metodo de la clase B");
    }
}

// T es un parametro de tipo generico que extiende de la interfaz InterfaceA, por ello ya cuenta con el metodoA
class Adaptador<T extends InterfaceA> implements InterfaceB {

    private T t;

    public Adaptador(T t) {
        this.t = t;
    }

    @Override
    public void metodoB() {
        t.metodoA(); // t es de tipo T, el cual extiende de InterfaceA y puede llamar al metodoA
    }
}

interface CarroElectrico {
    void cargarBateria();
}

interface CarroGasolina {
    void cargarGasolina();
}

class CarroElectricoImpl implements CarroElectrico {

    @Override
    public void cargarBateria() {
        System.out.println("Cargando bateria para carro electrico");
    }
}

class CarroGasolinaImpl implements CarroGasolina {

    @Override
    public void cargarGasolina() {
        System.out.println("Cargando gasolina para carro de combustible");
    }
}

// esta clase implementa la interfaz CarroGasolina, pero internamente utiliza la interfaz CarroElectrico
class CarroElectricoAdapter implements CarroGasolina {

    private CarroElectrico carroElectrico;

    public CarroElectricoAdapter(CarroElectrico carroElectrico) {
        this.carroElectrico = carroElectrico;
    }

    @Override
    public void cargarGasolina() {
        carroElectrico.cargarBateria();
    }
}



public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        // implementar calculadora
        Calculadora calculadora = new Calculadora();
        System.out.println(calculadora.sum(1));

        // implementar repositorio
        PersonaRepository personaRepository = new PersonaRepository();
        personaRepository.save(new Persona());

        // implementar clase que implementa dos interfaces
        ClaseAB claseAB = new ClaseAB();

        Adaptador<ClaseAB> adaptador = new Adaptador<>(claseAB);
        adaptador.metodoB();// el adaptador tiene el metodoB, pero al ser de tipo T, puede llamar al metodoA

        // implementar adaptador carro electrico
        // se crea una instancia de la interfaz CarroElectrico y esta se pasa como parametro al adaptador
        // ya que si la clase de implementacion cambia en el futuro, no se afecta el adaptador
        CarroElectrico carroElectrico = new CarroElectricoImpl();
        // las interfaces son compatibles, por lo que se puede utilizar el adaptador
        // para cargar la bateria del carro electrico
        // para esto se crea una instancia del carro electrico y se pasa como parametro al adaptador
        CarroElectricoAdapter carroElectricoAdapter = new CarroElectricoAdapter(carroElectrico);
        carroElectricoAdapter.cargarGasolina();
    }
}
