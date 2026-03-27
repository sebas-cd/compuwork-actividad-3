package compuwork.model;

import java.util.ArrayList;

public class ReporteDesempeno {

    // Atributos
    private String id;
    private Empleado empleado;
    private String fechaGeneracion;
    private String periodoEvaluacion;
    private ArrayList<MetricaDesempeno> metricas;
    private String comentarioGeneral;

    // Constructor
    public ReporteDesempeno(String id, Empleado empleado,
                             String fechaGeneracion, String periodoEvaluacion) {
        this.id               = id;
        this.empleado         = empleado;
        this.fechaGeneracion  = fechaGeneracion;
        this.periodoEvaluacion = periodoEvaluacion;
        this.metricas         = new ArrayList<>();
        this.comentarioGeneral = "";
    }

    // Agrega una metrica al reporte
    public void agregarMetrica(MetricaDesempeno metrica) {
        metricas.add(metrica);
    }

    // Calcula el puntaje global sumando todos los puntajes ponderados
    public double calcularPuntajeGlobal() {
        double total = 0;
        for (MetricaDesempeno m : metricas) {
            total += m.getPuntajePonderado();
        }
        return total;
    }

    // Determina el nivel de desempeño segun el puntaje global
    public String getNivelDesempeno() {
        double puntaje = calcularPuntajeGlobal();
        if (puntaje >= 8.5) return "Excelente";
        if (puntaje >= 7.0) return "Bueno";
        if (puntaje >= 5.0) return "Regular";
        return "Deficiente";
    }

    // Genera el reporte en formato texto
    public String generarReporte() {
        String reporte = "";
        reporte += "===========================================\n";
        reporte += "       REPORTE DE DESEMPENO - COMPUWORK   \n";
        reporte += "===========================================\n";
        reporte += "ID Reporte   : " + id + "\n";
        reporte += "Fecha        : " + fechaGeneracion + "\n";
        reporte += "Periodo      : " + periodoEvaluacion + "\n";
        reporte += "-------------------------------------------\n";
        reporte += "Empleado     : " + empleado.getNombreCompleto() + "\n";
        reporte += "Tipo contrato: " + empleado.getTipoContrato() + "\n";
        reporte += "Departamento : " + empleado.getDepartamento() + "\n";
        reporte += "-------------------------------------------\n";
        reporte += "METRICAS:\n";
        for (MetricaDesempeno m : metricas) {
            reporte += "  " + m.toString() + "\n";
        }
        reporte += "-------------------------------------------\n";
        reporte += "Puntaje global: " + calcularPuntajeGlobal() + "\n";
        reporte += "Nivel         : " + getNivelDesempeno() + "\n";
        if (!comentarioGeneral.isEmpty()) {
            reporte += "Comentario    : " + comentarioGeneral + "\n";
        }
        reporte += "===========================================\n";
        return reporte;
    }

    // Getters
    public String getId()                          { return id; }
    public Empleado getEmpleado()                  { return empleado; }
    public String getFechaGeneracion()             { return fechaGeneracion; }
    public String getPeriodoEvaluacion()           { return periodoEvaluacion; }
    public ArrayList<MetricaDesempeno> getMetricas(){ return metricas; }
    public String getComentarioGeneral()           { return comentarioGeneral; }

    // Setters
    public void setComentarioGeneral(String comentarioGeneral) {
        this.comentarioGeneral = comentarioGeneral;
    }
}