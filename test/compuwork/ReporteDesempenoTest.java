package compuwork;

import compuwork.exception.SalarioInvalidoException;
import compuwork.model.*;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class ReporteDesempenoTest {

    private ReporteDesempeno   reporte;
    private EmpleadoPermanente empleado;

    @Before
    public void setUp() throws SalarioInvalidoException {
        empleado = new EmpleadoPermanente(
            "E001", "Ana", "Garcia", "ana@empresa.com",
            2000000, 1200000, true, 15);

        reporte = new ReporteDesempeno(
            "R001", empleado, "2025-01-01", "Q1-2025");
    }

    // ── Pruebas basicas ───────────────────────────────────────────────────────

    @Test
    public void testReporteIdCorrecto() {
        assertEquals("R001", reporte.getId());
    }

    @Test
    public void testReportePeriodoCorrecto() {
        assertEquals("Q1-2025", reporte.getPeriodoEvaluacion());
    }

    @Test
    public void testReporteEmpleadoCorrecto() {
        assertEquals("Ana Garcia", reporte.getEmpleado().getNombreCompleto());
    }

    @Test
    public void testReporteSinMetricasPuntajeEsCero() {
        assertEquals(0.0, reporte.calcularPuntajeGlobal(), 0.01);
    }

    // ── Pruebas metricas ──────────────────────────────────────────────────────

    @Test
    public void testAgregarMetricaExitoso() {
        reporte.agregarMetrica(new MetricaDesempeno("Puntualidad", 9.0, 0.3, "Excelente"));
        assertEquals(1, reporte.getMetricas().size());
    }

    @Test
    public void testCalcularPuntajeGlobalUnaMetrica() {
        // Puntaje ponderado = puntuacion * peso = 9.0 * 0.3 = 2.7
        reporte.agregarMetrica(new MetricaDesempeno("Puntualidad", 9.0, 0.3, "Excelente"));
        assertEquals(2.7, reporte.calcularPuntajeGlobal(), 0.01);
    }

    @Test
    public void testCalcularPuntajeGlobalVariasMetricas() {
        // 9.0 * 0.3 = 2.7
        // 8.0 * 0.4 = 3.2
        // 7.0 * 0.3 = 2.1
        // Total = 8.0
        reporte.agregarMetrica(new MetricaDesempeno("Puntualidad",  9.0, 0.3, ""));
        reporte.agregarMetrica(new MetricaDesempeno("Productividad",8.0, 0.4, ""));
        reporte.agregarMetrica(new MetricaDesempeno("Calidad",      7.0, 0.3, ""));
        assertEquals(8.0, reporte.calcularPuntajeGlobal(), 0.01);
    }

    // ── Pruebas nivel de desempeño ────────────────────────────────────────────

    @Test
    public void testNivelDesempenoExcelente() {
        // Puntaje >= 8.5 = Excelente
        reporte.agregarMetrica(new MetricaDesempeno("Rendimiento", 9.0, 1.0, ""));
        assertEquals("Excelente", reporte.getNivelDesempeno());
    }

    @Test
    public void testNivelDesempenoBueno() {
        // Puntaje >= 7.0 = Bueno
        reporte.agregarMetrica(new MetricaDesempeno("Rendimiento", 7.5, 1.0, ""));
        assertEquals("Bueno", reporte.getNivelDesempeno());
    }

    @Test
    public void testNivelDesempenoRegular() {
        // Puntaje >= 5.0 = Regular
        reporte.agregarMetrica(new MetricaDesempeno("Rendimiento", 6.0, 1.0, ""));
        assertEquals("Regular", reporte.getNivelDesempeno());
    }

    @Test
    public void testNivelDesempenoDeficiente() {
        // Puntaje < 5.0 = Deficiente
        reporte.agregarMetrica(new MetricaDesempeno("Rendimiento", 3.0, 1.0, ""));
        assertEquals("Deficiente", reporte.getNivelDesempeno());
    }

    // ── Pruebas generar reporte ───────────────────────────────────────────────

    @Test
    public void testGenerarReporteContieneNombreEmpleado() {
        reporte.agregarMetrica(new MetricaDesempeno("Puntualidad", 9.0, 1.0, ""));
        String resultado = reporte.generarReporte();
        assertTrue(resultado.contains("Ana Garcia"));
    }

    @Test
    public void testGenerarReporteContienePeriodo() {
        reporte.agregarMetrica(new MetricaDesempeno("Puntualidad", 9.0, 1.0, ""));
        String resultado = reporte.generarReporte();
        assertTrue(resultado.contains("Q1-2025"));
    }

    @Test
    public void testGenerarReporteContieneNivel() {
        reporte.agregarMetrica(new MetricaDesempeno("Rendimiento", 9.0, 1.0, ""));
        String resultado = reporte.generarReporte();
        assertTrue(resultado.contains("Excelente"));
    }
}