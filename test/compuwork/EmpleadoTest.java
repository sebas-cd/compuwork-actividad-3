package compuwork;

import compuwork.exception.SalarioInvalidoException;
import compuwork.model.EmpleadoPermanente;
import compuwork.model.EmpleadoTemporal;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class EmpleadoTest {

    private EmpleadoPermanente permanente;
    private EmpleadoTemporal   temporal;

    // Se ejecuta antes de cada prueba para inicializar los objetos
    @Before
    public void setUp() throws SalarioInvalidoException {
        permanente = new EmpleadoPermanente(
            "E001", "Ana", "Garcia", "ana@empresa.com",
            2000000, 1200000, true, 15);

        temporal = new EmpleadoTemporal(
            "E002", "Luis", "Ramirez", "luis@empresa.com",
            0, "2025-12-31", 50000, 160, "AgenciaTech");
    }

    // ── Pruebas EmpleadoPermanente ────────────────────────────────────────────

    @Test
    public void testEmpleadoPermanenteNombreCompleto() {
        assertEquals("Ana Garcia", permanente.getNombreCompleto());
    }

    @Test
    public void testEmpleadoPermanenteTipoContrato() {
        assertEquals("Permanente", permanente.getTipoContrato());
    }

    @Test
    public void testEmpleadoPermanenteCalcularSalario() {
        // Salario = salarioBase + (bonificacionAnual / 12)
        // = 2.000.000 + (1.200.000 / 12) = 2.000.000 + 100.000 = 2.100.000
        double esperado = 2000000 + (1200000.0 / 12);
        assertEquals(esperado, permanente.calcularSalario(), 0.01);
    }

    @Test
    public void testEmpleadoPermanenteDepartamentoInicial() {
        assertEquals("Sin asignar", permanente.getDepartamento());
    }

    @Test
    public void testEmpleadoPermanenteSeguroMedico() {
        assertTrue(permanente.isTieneSeguroMedico());
    }

    @Test
    public void testEmpleadoPermanenteDiasVacaciones() {
        assertEquals(15, permanente.getDiasVacaciones());
    }

    // ── Pruebas EmpleadoTemporal ──────────────────────────────────────────────

    @Test
    public void testEmpleadoTemporalNombreCompleto() {
        assertEquals("Luis Ramirez", temporal.getNombreCompleto());
    }

    @Test
    public void testEmpleadoTemporalTipoContrato() {
        assertEquals("Temporal", temporal.getTipoContrato());
    }

    @Test
    public void testEmpleadoTemporalCalcularSalario() {
        // Salario = tarifaPorHora * horasTrabajadas = 50.000 * 160 = 8.000.000
        double esperado = 50000 * 160;
        assertEquals(esperado, temporal.calcularSalario(), 0.01);
    }

    @Test
    public void testEmpleadoTemporalAgencia() {
        assertEquals("AgenciaTech", temporal.getAgenciaContratante());
    }

    @Test
    public void testEmpleadoTemporalFechaFin() {
        assertEquals("2025-12-31", temporal.getFechaFinContrato());
    }

    // ── Pruebas de validacion ─────────────────────────────────────────────────

    @Test(expected = SalarioInvalidoException.class)
    public void testSalarioNegativoLanzaExcepcion() throws SalarioInvalidoException {
        // Un salario negativo debe lanzar SalarioInvalidoException
        new EmpleadoPermanente("E003", "Pedro", "Lopez", "pedro@empresa.com",
            -500000, 0, false, 0);
    }

    @Test
    public void testActualizarNombre() {
        permanente.setNombre("Maria");
        assertEquals("Maria Garcia", permanente.getNombreCompleto());
    }

    @Test
    public void testActualizarDepartamento() {
        permanente.setDepartamento("Tecnologia");
        assertEquals("Tecnologia", permanente.getDepartamento());
    }
}