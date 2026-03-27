package compuwork.exception;

// Excepcion que se lanza cuando el departamento alcanza su capacidad maxima
public class DepartamentoLlenoException extends Exception {

    public DepartamentoLlenoException(String mensaje) {
        super(mensaje);
    }
}
