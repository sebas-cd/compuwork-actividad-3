package compuwork.model;

import compuwork.exception.SalarioInvalidoException;

public class EmpleadoTemporal extends Empleado {

    // Atributos
    private String fechaFinContrato;
    private double tarifaPorHora;
    private int horasTrabajadas;
    private String agenciaContratante;

    // Constructor
    public EmpleadoTemporal(String id, String nombre, String apellido,
                             String email, double salarioBase,
                             String fechaFinContrato, double tarifaPorHora,
                             int horasTrabajadas, String agenciaContratante) throws SalarioInvalidoException {
        super(id, nombre, apellido, email, salarioBase);
        this.fechaFinContrato   = fechaFinContrato;
        this.tarifaPorHora      = tarifaPorHora;
        this.horasTrabajadas    = horasTrabajadas;
        this.agenciaContratante = agenciaContratante;
    }

    // Calcula el salario total basado en las horas trabajadas
    @Override
    public double calcularSalario() {
        return tarifaPorHora * horasTrabajadas;
    }

    // Retorna el tipo de contrato
    @Override
    public String getTipoContrato() {
        return "Temporal";
    }

    // Getters
    public String getFechaFinContrato()   { return fechaFinContrato; }
    public double getTarifaPorHora()      { return tarifaPorHora; }
    public int getHorasTrabajadas()       { return horasTrabajadas; }
    public String getAgenciaContratante() { return agenciaContratante; }

    // Setters
    public void setFechaFinContrato(String fechaFinContrato)     { this.fechaFinContrato = fechaFinContrato; }
    public void setTarifaPorHora(double tarifaPorHora)           { this.tarifaPorHora = tarifaPorHora; }
    public void setHorasTrabajadas(int horasTrabajadas)          { this.horasTrabajadas = horasTrabajadas; }
    public void setAgenciaContratante(String agenciaContratante) { this.agenciaContratante = agenciaContratante; }

    // Muestra la informacion del empleado temporal
    @Override
    public String toString() {
        return super.toString() +
               " | Fecha fin contrato: " + fechaFinContrato +
               " | Horas trabajadas: " + horasTrabajadas +
               " | Agencia: " + agenciaContratante;
    }
}