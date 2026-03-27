package compuwork.model;

public class MetricaDesempeno {

    // Atributos
    private String nombre;
    private double puntuacion;
    private double peso;       
    private String observacion;

    // Constructor
    public MetricaDesempeno(String nombre, double puntuacion, double peso, String observacion) {
        this.nombre      = nombre;
        this.puntuacion  = puntuacion;
        this.peso        = peso;
        this.observacion = observacion;
    }

    // Calcula el aporte ponderado de esta metrica al puntaje final
    public double getPuntajePonderado() {
        return puntuacion * peso;
    }

    // Getters
    public String getNombre()      { return nombre; }
    public double getPuntuacion()  { return puntuacion; }
    public double getPeso()        { return peso; }
    public String getObservacion() { return observacion; }

    // Setters
    public void setNombre(String nombre)           { this.nombre = nombre; }
    public void setPuntuacion(double puntuacion)   { this.puntuacion = puntuacion; }
    public void setPeso(double peso)               { this.peso = peso; }
    public void setObservacion(String observacion) { this.observacion = observacion; }

    // Muestra la informacion de la metrica
    @Override
    public String toString() {
        return "Metrica: " + nombre +
               " | Puntuacion: " + puntuacion +
               " | Peso: " + peso +
               " | Ponderado: " + getPuntajePonderado() +
               " | Observacion: " + observacion;
    }
}