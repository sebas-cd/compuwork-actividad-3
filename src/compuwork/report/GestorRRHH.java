// package compuwork.report;

// import compuwork.exception.DepartamentoException;
// import compuwork.exception.DepartamentoLlenoException;
// import compuwork.exception.EmpleadoDuplicadoException;
// import compuwork.exception.EmpleadoNoEncontradoException;
// import compuwork.model.Departamento;
// import compuwork.model.Empleado;

// import java.util.ArrayList;

// public class GestorRRHH {

//     // Lista de empleados y departamentos registrados
//     private ArrayList<Empleado>     empleados;
//     private ArrayList<Departamento> departamentos;

//     // Constructor
//     public GestorRRHH() {
//         this.empleados     = new ArrayList<>();
//         this.departamentos = new ArrayList<>();
//     }

//     // ───────────────────────────────────────────────── Gestion de empleados ─────────────────────────────────────────────────

//     // Registra un nuevo empleado en el sistema
//     public void registrarEmpleado(Empleado empleado) throws EmpleadoDuplicadoException {
//         // Validar que no exista un empleado con el mismo ID
//         for (Empleado e : empleados) {
//             if (e.getId().equals(empleado.getId())) {
//                 throw new EmpleadoDuplicadoException(
//                     "Ya existe un empleado con el ID: " + empleado.getId());
//             }
//         }
//         empleados.add(empleado);
//         System.out.println("Empleado registrado: " + empleado.getNombreCompleto());
//     }

//     // Elimina un empleado del sistema por su ID
//     public void eliminarEmpleado(String id) throws EmpleadoNoEncontradoException {
//         Empleado empleado = buscarEmpleadoPorId(id);
//         empleados.remove(empleado);
//         System.out.println("Empleado eliminado: " + empleado.getNombreCompleto());
//     }

//     // Busca un empleado por su ID
//     public Empleado buscarEmpleadoPorId(String id) throws EmpleadoNoEncontradoException {
//         for (Empleado e : empleados) {
//             if (e.getId().equals(id)) {
//                 return e;
//             }
//         }
//         throw new EmpleadoNoEncontradoException(
//             "No se encontro empleado con ID: " + id);
//     }

//     // Muestra todos los empleados registrados
//     public void listarEmpleados() {
//         System.out.println("\n===== LISTA DE EMPLEADOS =====");
//         if (empleados.isEmpty()) {
//             System.out.println("No hay empleados registrados.");
//             return;
//         }
//         for (Empleado e : empleados) {
//             System.out.println(e);
//         }
//         System.out.println("==============================\n");
//     }

//     public ArrayList<Empleado> getEmpleados() { return empleados; }
//     public ArrayList<Departamento> getDepartamentos() { return departamentos; }

//    // ───────────────────────────────────────────── Gestion de departamentos ─────────────────────────────────────────────

//     // Registra un nuevo departamento en el sistema
//     public void registrarDepartamento(Departamento departamento) {
//         departamentos.add(departamento);
//         System.out.println("Departamento registrado: " + departamento.getNombre());
//     }

//     // Elimina un departamento del sistema por su ID
//     public void eliminarDepartamento(String id) throws DepartamentoException {
//         Departamento departamento = buscarDepartamentoPorId(id);
//         if (departamento.getTotalEmpleados() > 0) {
//             throw new DepartamentoException(
//                 "No se puede eliminar el departamento " + departamento.getNombre() +
//                 " porque tiene " + departamento.getTotalEmpleados() + " empleado(s) asignado(s).");
//         }
//         departamentos.remove(departamento);
//         System.out.println("Departamento eliminado: " + departamento.getNombre());
//     }

//     // Busca un departamento por su ID
//     public Departamento buscarDepartamentoPorId(String id) throws DepartamentoException {
//         for (Departamento d : departamentos) {
//             if (d.getId().equals(id)) {
//                 return d;
//             }
//         }
//         throw new DepartamentoException(
//             "No se encontro departamento con ID: " + id);
//     }

//     // Muestra todos los departamentos registrados
//     public void listarDepartamentos() {
//         System.out.println("\n===== LISTA DE DEPARTAMENTOS =====");
//         if (departamentos.isEmpty()) {
//             System.out.println("No hay departamentos registrados.");
//             return;
//         }
//         for (Departamento d : departamentos) {
//             System.out.println(d);
//             d.listarEmpleados();
//         }
//         System.out.println("==================================\n");
//     }

// // ──────────────────────────────────────────────Asignacion de empleados ──────────────────────────────────────────────

//     // Asigna un empleado a un departamento
//     public void asignarEmpleadoADepartamento(String idEmpleado, String idDepartamento)
//             throws EmpleadoNoEncontradoException, DepartamentoException, DepartamentoLlenoException {
//         Empleado     empleado     = buscarEmpleadoPorId(idEmpleado);
//         Departamento departamento = buscarDepartamentoPorId(idDepartamento);
//         departamento.agregarEmpleado(empleado);
//     }

//     // Transfiere un empleado de un departamento a otro
//     public void transferirEmpleado(String idEmpleado,
//                                     String idDepartamentoOrigen,
//                                     String idDepartamentoDestino)
//             throws EmpleadoNoEncontradoException, DepartamentoException, DepartamentoLlenoException {
//         Empleado     empleado = buscarEmpleadoPorId(idEmpleado);
//         Departamento origen   = buscarDepartamentoPorId(idDepartamentoOrigen);
//         Departamento destino  = buscarDepartamentoPorId(idDepartamentoDestino);
//         origen.eliminarEmpleado(empleado);
//         destino.agregarEmpleado(empleado);
//         System.out.println("Empleado " + empleado.getNombreCompleto() +
//                            " transferido a " + destino.getNombre());
//     }
// }

package compuwork.report;

import compuwork.exception.DepartamentoException;
import compuwork.exception.DepartamentoLlenoException;
import compuwork.exception.EmpleadoDuplicadoException;
import compuwork.exception.EmpleadoNoEncontradoException;
import compuwork.exception.SalarioInvalidoException;
import compuwork.model.Departamento;
import compuwork.model.Empleado;

import java.util.ArrayList;

public class GestorRRHH {

    private ArrayList<Empleado>     empleados;
    private ArrayList<Departamento> departamentos;

    public GestorRRHH() {
        this.empleados     = new ArrayList<>();
        this.departamentos = new ArrayList<>();
    }

    // ── Gestion de empleados ──────────────────────────────────────────────────

    public void registrarEmpleado(Empleado empleado) throws EmpleadoDuplicadoException {
        for (Empleado e : empleados) {
            if (e.getId().equals(empleado.getId())) {
                throw new EmpleadoDuplicadoException(
                    "Ya existe un empleado con el ID: " + empleado.getId());
            }
        }
        empleados.add(empleado);
        System.out.println("Empleado registrado: " + empleado.getNombreCompleto());
    }

    public void eliminarEmpleado(String id) throws EmpleadoNoEncontradoException {
        Empleado empleado = buscarEmpleadoPorId(id);
        empleados.remove(empleado);
        System.out.println("Empleado eliminado: " + empleado.getNombreCompleto());
    }

    public void actualizarEmpleado(String id, String nuevoNombre,
                                   String nuevoApellido, String nuevoEmail)
            throws EmpleadoNoEncontradoException {
        Empleado empleado = buscarEmpleadoPorId(id);
        if (nuevoNombre   != null && !nuevoNombre.isEmpty())   empleado.setNombre(nuevoNombre);
        if (nuevoApellido != null && !nuevoApellido.isEmpty()) empleado.setApellido(nuevoApellido);
        if (nuevoEmail    != null && !nuevoEmail.isEmpty())    empleado.setEmail(nuevoEmail);
        System.out.println("Empleado actualizado: " + empleado.getNombreCompleto());
    }

    public Empleado buscarEmpleadoPorId(String id) throws EmpleadoNoEncontradoException {
        for (Empleado e : empleados) {
            if (e.getId().equals(id)) return e;
        }
        throw new EmpleadoNoEncontradoException("No se encontro empleado con ID: " + id);
    }

    public void listarEmpleados() {
        System.out.println("\n===== LISTA DE EMPLEADOS =====");
        if (empleados.isEmpty()) { System.out.println("No hay empleados registrados."); return; }
        for (Empleado e : empleados) System.out.println(e);
        System.out.println("==============================\n");
    }

    // ── Gestion de departamentos ──────────────────────────────────────────────

    public void registrarDepartamento(Departamento departamento) {
        departamentos.add(departamento);
        System.out.println("Departamento registrado: " + departamento.getNombre());
    }

    public void eliminarDepartamento(String id) throws DepartamentoException {
        Departamento departamento = buscarDepartamentoPorId(id);
        if (departamento.getTotalEmpleados() > 0) {
            throw new DepartamentoException(
                "No se puede eliminar el departamento " + departamento.getNombre() +
                " porque tiene " + departamento.getTotalEmpleados() + " empleado(s) asignado(s).");
        }
        departamentos.remove(departamento);
        System.out.println("Departamento eliminado: " + departamento.getNombre());
    }

    // Actualiza nombre, descripcion y capacidad de un departamento existente
    public void actualizarDepartamento(String id, String nuevoNombre,
                                       String nuevaDescripcion, int nuevaCapacidad)
            throws DepartamentoException {
        Departamento dep = buscarDepartamentoPorId(id);
        if (nuevoNombre      != null && !nuevoNombre.isEmpty())      dep.setNombre(nuevoNombre);
        if (nuevaDescripcion != null && !nuevaDescripcion.isEmpty()) dep.setDescripcion(nuevaDescripcion);
        if (nuevaCapacidad > 0) {
            if (nuevaCapacidad < dep.getTotalEmpleados()) {
                throw new DepartamentoException(
                    "La nueva capacidad (" + nuevaCapacidad + ") no puede ser menor " +
                    "al numero de empleados actuales (" + dep.getTotalEmpleados() + ").");
            }
            dep.setCapacidadMaxima(nuevaCapacidad);
        }
        System.out.println("Departamento actualizado: " + dep.getNombre());
    }

    public Departamento buscarDepartamentoPorId(String id) throws DepartamentoException {
        for (Departamento d : departamentos) {
            if (d.getId().equals(id)) return d;
        }
        throw new DepartamentoException("No se encontro departamento con ID: " + id);
    }

    public void listarDepartamentos() {
        System.out.println("\n===== LISTA DE DEPARTAMENTOS =====");
        if (departamentos.isEmpty()) { System.out.println("No hay departamentos registrados."); return; }
        for (Departamento d : departamentos) { System.out.println(d); d.listarEmpleados(); }
        System.out.println("==================================\n");
    }

    // ── Asignacion de empleados ───────────────────────────────────────────────

    public void asignarEmpleadoADepartamento(String idEmpleado, String idDepartamento)
            throws EmpleadoNoEncontradoException, DepartamentoException, DepartamentoLlenoException {
        Empleado     empleado     = buscarEmpleadoPorId(idEmpleado);
        Departamento departamento = buscarDepartamentoPorId(idDepartamento);
        departamento.agregarEmpleado(empleado);
    }

    // Elimina la asignacion de un empleado de su departamento actual
    public void desasignarEmpleado(String idEmpleado)
            throws EmpleadoNoEncontradoException, DepartamentoException {
        Empleado empleado = buscarEmpleadoPorId(idEmpleado);
        if (empleado.getDepartamento().equals("Sin asignar")) {
            throw new DepartamentoException(
                "El empleado " + empleado.getNombreCompleto() + " no esta asignado a ningun departamento.");
        }
        // Buscar el departamento al que pertenece y removerlo
        for (Departamento d : departamentos) {
            if (d.getNombre().equals(empleado.getDepartamento())) {
                d.eliminarEmpleado(empleado);
                return;
            }
        }
    }

    public void transferirEmpleado(String idEmpleado,
                                   String idDepartamentoOrigen,
                                   String idDepartamentoDestino)
            throws EmpleadoNoEncontradoException, DepartamentoException, DepartamentoLlenoException {
        Empleado     empleado = buscarEmpleadoPorId(idEmpleado);
        Departamento origen   = buscarDepartamentoPorId(idDepartamentoOrigen);
        Departamento destino  = buscarDepartamentoPorId(idDepartamentoDestino);
        origen.eliminarEmpleado(empleado);
        destino.agregarEmpleado(empleado);
        System.out.println("Empleado " + empleado.getNombreCompleto() +
                           " transferido a " + destino.getNombre());
    }

    // ── Reporte por departamento ──────────────────────────────────────────────

    public String generarReporteDepartamento(String idDepartamento) throws DepartamentoException {
        Departamento dep = buscarDepartamentoPorId(idDepartamento);
        String reporte = "";
        reporte += "===========================================\n";
        reporte += "     REPORTE DE DEPARTAMENTO - COMPUWORK  \n";
        reporte += "===========================================\n";
        reporte += "ID          : " + dep.getId() + "\n";
        reporte += "Departamento: " + dep.getNombre() + "\n";
        reporte += "Descripcion : " + dep.getDescripcion() + "\n";
        reporte += "Capacidad   : " + dep.getTotalEmpleados() + "/" + dep.getCapacidadMaxima() + "\n";
        reporte += "-------------------------------------------\n";
        reporte += "EMPLEADOS:\n";
        if (dep.getEmpleados().isEmpty()) {
            reporte += "  No hay empleados asignados.\n";
        } else {
            for (Empleado e : dep.getEmpleados()) {
                reporte += "  - " + e.getNombreCompleto() +
                           " | " + e.getTipoContrato() +
                           " | Salario: " + formatearNumero(e.calcularSalario()) + "\n";
            }
        }
        reporte += "-------------------------------------------\n";
        reporte += "Masa salarial total: " + formatearNumero(dep.calcularMasaSalarial()) + "\n";
        reporte += "===========================================\n";
        return reporte;
    }

    private String formatearNumero(double valor) {
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance(new java.util.Locale("es", "CO"));
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return "$" + nf.format(valor);
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public ArrayList<Empleado>     getEmpleados()     { return empleados; }
    public ArrayList<Departamento> getDepartamentos() { return departamentos; }
}