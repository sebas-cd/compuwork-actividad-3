package compuwork;

import compuwork.exception.DepartamentoLlenoException;
import compuwork.exception.SalarioInvalidoException;
import compuwork.model.Departamento;
import compuwork.model.EmpleadoPermanente;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class DepartamentoTest {

    private Departamento      departamento;
    private EmpleadoPermanente empleado1;
    private EmpleadoPermanente empleado2;

    @Before
    public void setUp() throws SalarioInvalidoException {
        departamento = new Departamento("D001", "Tecnologia", "Dept de TI", 2);

        empleado1 = new EmpleadoPermanente(
            "E001", "Ana", "Garcia", "ana@empresa.com",
            2000000, 1200000, true, 15);

        empleado2 = new EmpleadoPermanente(
            "E002", "Carlos", "Perez", "carlos@empresa.com",
            1800000, 600000, false, 10);
    }

    // ── Pruebas basicas ───────────────────────────────────────────────────────

    @Test
    public void testDepartamentoNombre() {
        assertEquals("Tecnologia", departamento.getNombre());
    }

    @Test
    public void testDepartamentoCapacidadMaxima() {
        assertEquals(2, departamento.getCapacidadMaxima());
    }

    @Test
    public void testDepartamentoInicialmenteVacio() {
        assertEquals(0, departamento.getTotalEmpleados());
    }

    // ── Pruebas agregar empleado ──────────────────────────────────────────────

    @Test
    public void testAgregarEmpleadoExitoso() throws DepartamentoLlenoException {
        departamento.agregarEmpleado(empleado1);
        assertEquals(1, departamento.getTotalEmpleados());
    }

    @Test
    public void testAgregarEmpleadoAsignaDepartamento() throws DepartamentoLlenoException {
        departamento.agregarEmpleado(empleado1);
        assertEquals("Tecnologia", empleado1.getDepartamento());
    }

    @Test(expected = DepartamentoLlenoException.class)
    public void testDepartamentoLlenoLanzaExcepcion() throws DepartamentoLlenoException, SalarioInvalidoException {
        // Capacidad maxima es 2, agregar un tercero debe lanzar excepcion
        EmpleadoPermanente empleado3 = new EmpleadoPermanente(
            "E003", "Sofia", "Torres", "sofia@empresa.com",
            1500000, 0, false, 5);
        departamento.agregarEmpleado(empleado1);
        departamento.agregarEmpleado(empleado2);
        departamento.agregarEmpleado(empleado3); // debe lanzar excepcion
    }

    // ── Pruebas eliminar empleado ─────────────────────────────────────────────

    @Test
    public void testEliminarEmpleadoExitoso() throws DepartamentoLlenoException {
        departamento.agregarEmpleado(empleado1);
        departamento.eliminarEmpleado(empleado1);
        assertEquals(0, departamento.getTotalEmpleados());
    }

    @Test
    public void testEliminarEmpleadoResetDepartamento() throws DepartamentoLlenoException {
        departamento.agregarEmpleado(empleado1);
        departamento.eliminarEmpleado(empleado1);
        assertEquals("Sin asignar", empleado1.getDepartamento());
    }

    // ── Pruebas masa salarial ─────────────────────────────────────────────────

    @Test
    public void testCalcularMasaSalarialVacio() {
        assertEquals(0.0, departamento.calcularMasaSalarial(), 0.01);
    }

    @Test
    public void testCalcularMasaSalarialConEmpleados() throws DepartamentoLlenoException {
        departamento.agregarEmpleado(empleado1);
        departamento.agregarEmpleado(empleado2);
        double esperado = empleado1.calcularSalario() + empleado2.calcularSalario();
        assertEquals(esperado, departamento.calcularMasaSalarial(), 0.01);
    }

    // ── Pruebas actualizar departamento ──────────────────────────────────────

    @Test
    public void testActualizarNombreDepartamento() {
        departamento.setNombre("Sistemas");
        assertEquals("Sistemas", departamento.getNombre());
    }

    @Test
    public void testActualizarCapacidadMaxima() {
        departamento.setCapacidadMaxima(5);
        assertEquals(5, departamento.getCapacidadMaxima());
    }
}