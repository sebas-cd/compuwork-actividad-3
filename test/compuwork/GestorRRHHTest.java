package compuwork;

import compuwork.exception.*;
import compuwork.model.*;
import compuwork.report.GestorRRHH;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class GestorRRHHTest {

    private GestorRRHH         gestor;
    private EmpleadoPermanente empleado1;
    private EmpleadoTemporal   empleado2;
    private Departamento       dept1;
    private Departamento       dept2;

    @Before
    public void setUp() throws SalarioInvalidoException, EmpleadoDuplicadoException {
        gestor = new GestorRRHH();

        empleado1 = new EmpleadoPermanente(
            "E001", "Ana", "Garcia", "ana@empresa.com",
            2000000, 1200000, true, 15);

        empleado2 = new EmpleadoTemporal(
            "E002", "Luis", "Ramirez", "luis@empresa.com",
            0, "2025-12-31", 50000, 160, "AgenciaTech");

        dept1 = new Departamento("D001", "Tecnologia", "Dept TI", 5);
        dept2 = new Departamento("D002", "Contabilidad", "Dept Contable", 3);

        gestor.registrarEmpleado(empleado1);
        gestor.registrarEmpleado(empleado2);
        gestor.registrarDepartamento(dept1);
        gestor.registrarDepartamento(dept2);
    }

    // ── Pruebas registrar empleado ────────────────────────────────────────────

    @Test
    public void testRegistrarEmpleadoExitoso() {
        assertEquals(2, gestor.getEmpleados().size());
    }

    @Test(expected = EmpleadoDuplicadoException.class)
    public void testRegistrarEmpleadoDuplicadoLanzaExcepcion()
            throws EmpleadoDuplicadoException, SalarioInvalidoException {
        // Registrar un empleado con el mismo ID debe lanzar excepcion
        EmpleadoPermanente duplicado = new EmpleadoPermanente(
            "E001", "Otro", "Nombre", "otro@empresa.com",
            1000000, 0, false, 0);
        gestor.registrarEmpleado(duplicado);
    }

    // ── Pruebas eliminar empleado ─────────────────────────────────────────────

    @Test
    public void testEliminarEmpleadoExitoso() throws EmpleadoNoEncontradoException {
        gestor.eliminarEmpleado("E001");
        assertEquals(1, gestor.getEmpleados().size());
    }

    @Test(expected = EmpleadoNoEncontradoException.class)
    public void testEliminarEmpleadoNoExistenteLanzaExcepcion()
            throws EmpleadoNoEncontradoException {
        gestor.eliminarEmpleado("E999"); // ID que no existe
    }

    // ── Pruebas actualizar empleado ───────────────────────────────────────────

    @Test
    public void testActualizarEmpleadoNombre() throws EmpleadoNoEncontradoException {
        gestor.actualizarEmpleado("E001", "Maria", "", "");
        assertEquals("Maria Garcia", empleado1.getNombreCompleto());
    }

    @Test
    public void testActualizarEmpleadoEmail() throws EmpleadoNoEncontradoException {
        gestor.actualizarEmpleado("E001", "", "", "nuevo@empresa.com");
        assertEquals("nuevo@empresa.com", empleado1.getEmail());
    }

    @Test(expected = EmpleadoNoEncontradoException.class)
    public void testActualizarEmpleadoNoExistenteLanzaExcepcion()
            throws EmpleadoNoEncontradoException {
        gestor.actualizarEmpleado("E999", "Nombre", "", "");
    }

    // ── Pruebas buscar empleado ───────────────────────────────────────────────

    @Test
    public void testBuscarEmpleadoPorIdExitoso() throws EmpleadoNoEncontradoException {
        Empleado encontrado = gestor.buscarEmpleadoPorId("E001");
        assertEquals("Ana Garcia", encontrado.getNombreCompleto());
    }

    @Test(expected = EmpleadoNoEncontradoException.class)
    public void testBuscarEmpleadoNoExistenteLanzaExcepcion()
            throws EmpleadoNoEncontradoException {
        gestor.buscarEmpleadoPorId("E999");
    }

    // ── Pruebas registrar departamento ────────────────────────────────────────

    @Test
    public void testRegistrarDepartamentoExitoso() {
        assertEquals(2, gestor.getDepartamentos().size());
    }

    // ── Pruebas eliminar departamento ─────────────────────────────────────────

    @Test
    public void testEliminarDepartamentoVacioExitoso() throws DepartamentoException {
        gestor.eliminarDepartamento("D001");
        assertEquals(1, gestor.getDepartamentos().size());
    }

    @Test(expected = DepartamentoException.class)
    public void testEliminarDepartamentoConEmpleadosLanzaExcepcion()
            throws DepartamentoException, EmpleadoNoEncontradoException, DepartamentoLlenoException {
        // Asignar empleado y luego intentar eliminar el departamento
        gestor.asignarEmpleadoADepartamento("E001", "D001");
        gestor.eliminarDepartamento("D001"); // debe lanzar excepcion
    }

    // ── Pruebas actualizar departamento ───────────────────────────────────────

    @Test
    public void testActualizarDepartamentoNombre() throws DepartamentoException {
        gestor.actualizarDepartamento("D001", "Sistemas", "", 0);
        assertEquals("Sistemas", dept1.getNombre());
    }

    @Test(expected = DepartamentoException.class)
    public void testActualizarCapacidadMenorAEmpleadosLanzaExcepcion()
            throws DepartamentoException, EmpleadoNoEncontradoException, DepartamentoLlenoException {
        // Asignar 2 empleados y luego reducir capacidad a 1
        gestor.asignarEmpleadoADepartamento("E001", "D001");
        gestor.asignarEmpleadoADepartamento("E002", "D001");
        gestor.actualizarDepartamento("D001", "", "", 1); // debe lanzar excepcion
    }

    // ── Pruebas asignar empleado ──────────────────────────────────────────────

    @Test
    public void testAsignarEmpleadoExitoso()
            throws EmpleadoNoEncontradoException, DepartamentoException, DepartamentoLlenoException {
        gestor.asignarEmpleadoADepartamento("E001", "D001");
        assertEquals("Tecnologia", empleado1.getDepartamento());
    }

    @Test
    public void testAsignarEmpleadoActualizaTotalDepartamento()
            throws EmpleadoNoEncontradoException, DepartamentoException, DepartamentoLlenoException {
        gestor.asignarEmpleadoADepartamento("E001", "D001");
        assertEquals(1, dept1.getTotalEmpleados());
    }

    // ── Pruebas transferir empleado ───────────────────────────────────────────

    @Test
    public void testTransferirEmpleadoExitoso()
            throws EmpleadoNoEncontradoException, DepartamentoException, DepartamentoLlenoException {
        gestor.asignarEmpleadoADepartamento("E001", "D001");
        gestor.transferirEmpleado("E001", "D001", "D002");
        assertEquals("Contabilidad", empleado1.getDepartamento());
    }

    @Test
    public void testTransferirEmpleadoActualizaContadores()
            throws EmpleadoNoEncontradoException, DepartamentoException, DepartamentoLlenoException {
        gestor.asignarEmpleadoADepartamento("E001", "D001");
        gestor.transferirEmpleado("E001", "D001", "D002");
        assertEquals(0, dept1.getTotalEmpleados());
        assertEquals(1, dept2.getTotalEmpleados());
    }

    // ── Pruebas desasignar empleado ───────────────────────────────────────────

    @Test
    public void testDesasignarEmpleadoExitoso()
            throws EmpleadoNoEncontradoException, DepartamentoException, DepartamentoLlenoException {
        gestor.asignarEmpleadoADepartamento("E001", "D001");
        gestor.desasignarEmpleado("E001");
        assertEquals("Sin asignar", empleado1.getDepartamento());
    }

    @Test(expected = DepartamentoException.class)
    public void testDesasignarEmpleadoSinDepartamentoLanzaExcepcion()
            throws EmpleadoNoEncontradoException, DepartamentoException {
        // empleado1 no tiene departamento asignado
        gestor.desasignarEmpleado("E001");
    }

    // ── Pruebas reporte departamento ──────────────────────────────────────────

    @Test
    public void testGenerarReporteDepartamentoContieneNombre() throws DepartamentoException {
        String reporte = gestor.generarReporteDepartamento("D001");
        assertTrue(reporte.contains("Tecnologia"));
    }

    @Test
    public void testGenerarReporteDepartamentoConEmpleados()
            throws DepartamentoException, EmpleadoNoEncontradoException, DepartamentoLlenoException {
        gestor.asignarEmpleadoADepartamento("E001", "D001");
        String reporte = gestor.generarReporteDepartamento("D001");
        assertTrue(reporte.contains("Ana Garcia"));
    }

    @Test(expected = DepartamentoException.class)
    public void testGenerarReporteDepartamentoNoExistenteLanzaExcepcion()
            throws DepartamentoException {
        gestor.generarReporteDepartamento("D999");
    }
}