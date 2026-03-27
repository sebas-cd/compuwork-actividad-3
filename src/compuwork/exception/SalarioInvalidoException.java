package compuwork.exception;

// Excepcion que se lanza cuando se ingresa un salario negativo o invalido
public class SalarioInvalidoException extends Exception {

    public SalarioInvalidoException(String mensaje) {
        super(mensaje);
    }
}