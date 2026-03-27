package compuwork.model;

import compuwork.exception.SalarioInvalidoException;

public abstract class Empleado {

    // Atributos privados
    private String id;
    private String nombre;
    private String apellido;
    private String email;
    private double salarioBase;
    private String departamento;

    // Constructor
    public Empleado(String id, String nombre, String apellido,
                    String email, double salarioBase) throws SalarioInvalidoException {
        // Validar que el salario no sea negativo
        if (salarioBase < 0) {
            throw new SalarioInvalidoException(
                "El salario no puede ser negativo. Valor ingresado: " + salarioBase);
        }
        this.id           = id;
        this.nombre       = nombre;
        this.apellido     = apellido;
        this.email        = email;
        this.salarioBase  = salarioBase;
        this.departamento = "Sin asignar";
    }

    // Metodos abstractos
    public abstract double calcularSalario();
    public abstract String getTipoContrato();

    // Getters
    public String getId()             { return id; }
    public String getNombre()         { return nombre; }
    public String getApellido()       { return apellido; }
    public String getEmail()          { return email; }
    public double getSalarioBase()    { return salarioBase; }
    public String getDepartamento()   { return departamento; }
    public String getNombreCompleto() { return nombre + " " + apellido; }

    // Setters
    public void setNombre(String nombre)             { this.nombre = nombre; }
    public void setApellido(String apellido)          { this.apellido = apellido; }
    public void setEmail(String email)               { this.email = email; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    // Setter de salario con validacion
    public void setSalarioBase(double salarioBase) throws SalarioInvalidoException {
        if (salarioBase < 0) {
            throw new SalarioInvalidoException(
                "El salario no puede ser negativo. Valor ingresado: " + salarioBase);
        }
        this.salarioBase = salarioBase;
    }

    // Muestra la informacion del empleado
    @Override
    public String toString() {
        return "ID: " + id + " | Nombre: " + getNombreCompleto() +
               " | Tipo: " + getTipoContrato() +
               " | Departamento: " + departamento +
               " | Salario: $" + calcularSalario();
    }
}