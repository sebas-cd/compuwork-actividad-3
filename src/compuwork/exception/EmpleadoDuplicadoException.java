package compuwork.exception;

// Excepcion que se lanza cuando se intenta registrar un empleado con ID repetido
public class EmpleadoDuplicadoException extends Exception {

    public EmpleadoDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
