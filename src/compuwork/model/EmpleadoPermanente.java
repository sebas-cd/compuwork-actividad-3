package compuwork.model;

import compuwork.exception.SalarioInvalidoException;

public class EmpleadoPermanente extends Empleado {

    // Atributos 
    private double bonificacionAnual;
    private boolean tieneSeguroMedico;
    private int diasVacaciones;

    // Constructor
    public EmpleadoPermanente(String id, String nombre, String apellido,
                               String email, double salarioBase,
                               double bonificacionAnual, boolean tieneSeguroMedico,
                               int diasVacaciones) throws SalarioInvalidoException {
        super(id, nombre, apellido, email, salarioBase);
        this.bonificacionAnual = bonificacionAnual;
        this.tieneSeguroMedico = tieneSeguroMedico;
        this.diasVacaciones    = diasVacaciones;
    }

    // Calcula el salario total sumando la bonificacion mensual
    @Override
    public double calcularSalario() {
        return getSalarioBase() + (bonificacionAnual / 12);
    }

    // Retorna el tipo de contrato
    @Override
    public String getTipoContrato() {
        return "Permanente";
    }

    // Getters
    public double getBonificacionAnual()  { return bonificacionAnual; }
    public boolean isTieneSeguroMedico()  { return tieneSeguroMedico; }
    public int getDiasVacaciones()        { return diasVacaciones; }

    // Setters
    public void setBonificacionAnual(double bonificacionAnual)   { this.bonificacionAnual = bonificacionAnual; }
    public void setTieneSeguroMedico(boolean tieneSeguroMedico)  { this.tieneSeguroMedico = tieneSeguroMedico; }
    public void setDiasVacaciones(int diasVacaciones)            { this.diasVacaciones = diasVacaciones; }

    // Muestra la informacion del empleado permanente
    @Override
    public String toString() {
        return super.toString() +
               " | Dias de vacaciones: " + diasVacaciones +
               " | Seguro medico: " + (tieneSeguroMedico ? "Si" : "No");
    }
}