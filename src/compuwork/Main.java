// package compuwork;

// import compuwork.exception.DepartamentoException;
// import compuwork.exception.DepartamentoLlenoException;
// import compuwork.exception.EmpleadoDuplicadoException;
// import compuwork.exception.EmpleadoNoEncontradoException;
// import compuwork.exception.SalarioInvalidoException;
// import compuwork.model.*;
// import compuwork.report.GestorRRHH;

// import java.util.Scanner;

// public class Main {

//     static Scanner scanner = new Scanner(System.in);
//     static GestorRRHH gestor = new GestorRRHH();

//     public static void main(String[] args) {

//         int opcion = 0;

//         System.out.println("=========================================");
//         System.out.println("   BIENVENIDO AL SISTEMA COMPUWORK       ");
//         System.out.println("=========================================");

//         // Menu principal
//         do {
//             System.out.println("\n===== MENU PRINCIPAL =====");
//             System.out.println("1. Registrar empleado permanente");
//             System.out.println("2. Registrar empleado temporal");
//             System.out.println("3. Registrar departamento");
//             System.out.println("4. Asignar empleado a departamento");
//             System.out.println("5. Transferir empleado entre departamentos");
//             System.out.println("6. Listar empleados");
//             System.out.println("7. Listar departamentos");
//             System.out.println("8. Generar reporte de desempeno");
//             System.out.println("0. Salir");
//             System.out.print("Seleccione una opcion: ");

//             opcion = Integer.parseInt(scanner.nextLine());

//             switch (opcion) {
//                 case 1: registrarEmpleadoPermanente(); break;
//                 case 2: registrarEmpleadoTemporal();   break;
//                 case 3: registrarDepartamento();       break;
//                 case 4: asignarEmpleado();             break;
//                 case 5: transferirEmpleado();          break;
//                 case 6: gestor.listarEmpleados();      break;
//                 case 7: gestor.listarDepartamentos();  break;
//                 case 8: generarReporte();              break;
//                 case 0: System.out.println("\nHasta luego!"); break;
//                 default: System.out.println("Opcion invalida, intente de nuevo."); break;
//             }

//         } while (opcion != 0);

//         scanner.close();
//     }

//     // ── Registrar empleado permanente ────────────────────────────────────────
//     static void registrarEmpleadoPermanente() {
//         System.out.println("\n===== REGISTRAR EMPLEADO PERMANENTE =====");
//         try {
//             System.out.print("Ingrese el ID: ");
//             String id = scanner.nextLine();

//             System.out.print("Ingrese el nombre: ");
//             String nombre = scanner.nextLine();

//             System.out.print("Ingrese el apellido: ");
//             String apellido = scanner.nextLine();

//             System.out.print("Ingrese el email: ");
//             String email = scanner.nextLine();

//             System.out.print("Ingrese el salario base: ");
//             double salarioBase = Double.parseDouble(scanner.nextLine());

//             System.out.print("Ingrese la bonificacion anual: ");
//             double bonificacion = Double.parseDouble(scanner.nextLine());

//             System.out.print("Tiene seguro medico? (true/false): ");
//             boolean seguro = Boolean.parseBoolean(scanner.nextLine());

//             System.out.print("Ingrese los dias de vacaciones: ");
//             int vacaciones = Integer.parseInt(scanner.nextLine());

//             EmpleadoPermanente ep = new EmpleadoPermanente(
//                     id, nombre, apellido, email, salarioBase,
//                     bonificacion, seguro, vacaciones);

//             gestor.registrarEmpleado(ep);

//         } catch (SalarioInvalidoException e) {
//             System.out.println("[SalarioInvalidoException] " + e.getMessage());
//         } catch (EmpleadoDuplicadoException e) {
//             System.out.println("[EmpleadoDuplicadoException] " + e.getMessage());
//         } catch (NumberFormatException e) {
//             System.out.println("Error: ingrese un valor numerico valido.");
//         }
//     }

//     // ── Registrar empleado temporal ──────────────────────────────────────────
//     static void registrarEmpleadoTemporal() {
//         System.out.println("\n===== REGISTRAR EMPLEADO TEMPORAL =====");
//         try {
//             System.out.print("Ingrese el ID: ");
//             String id = scanner.nextLine();

//             System.out.print("Ingrese el nombre: ");
//             String nombre = scanner.nextLine();

//             System.out.print("Ingrese el apellido: ");
//             String apellido = scanner.nextLine();

//             System.out.print("Ingrese el email: ");
//             String email = scanner.nextLine();

//             System.out.print("Ingrese la fecha fin de contrato (YYYY-MM-DD): ");
//             String fechaFin = scanner.nextLine();

//             System.out.print("Ingrese la tarifa por hora: ");
//             double tarifa = Double.parseDouble(scanner.nextLine());

//             System.out.print("Ingrese las horas trabajadas: ");
//             int horas = Integer.parseInt(scanner.nextLine());

//             System.out.print("Ingrese la agencia contratante: ");
//             String agencia = scanner.nextLine();

//             EmpleadoTemporal et = new EmpleadoTemporal(
//                     id, nombre, apellido, email, 0,
//                     fechaFin, tarifa, horas, agencia);

//             gestor.registrarEmpleado(et);

//         } catch (SalarioInvalidoException e) {
//             System.out.println("[SalarioInvalidoException] " + e.getMessage());
//         } catch (EmpleadoDuplicadoException e) {
//             System.out.println("[EmpleadoDuplicadoException] " + e.getMessage());
//         } catch (NumberFormatException e) {
//             System.out.println("Error: ingrese un valor numerico valido.");
//         }
//     }

//     // ── Registrar departamento ───────────────────────────────────────────────
//     static void registrarDepartamento() {
//         System.out.println("\n===== REGISTRAR DEPARTAMENTO =====");
//         try {
//             System.out.print("Ingrese el ID: ");
//             String id = scanner.nextLine();

//             System.out.print("Ingrese el nombre: ");
//             String nombre = scanner.nextLine();

//             System.out.print("Ingrese la descripcion: ");
//             String descripcion = scanner.nextLine();

//             System.out.print("Ingrese la capacidad maxima: ");
//             int capacidad = Integer.parseInt(scanner.nextLine());

//             Departamento d = new Departamento(id, nombre, descripcion, capacidad);
//             gestor.registrarDepartamento(d);

//         } catch (NumberFormatException e) {
//             System.out.println("Error: ingrese un valor numerico valido.");
//         }
//     }

//     // ── Asignar empleado a departamento ──────────────────────────────────────
//     static void asignarEmpleado() {
//         System.out.println("\n===== ASIGNAR EMPLEADO A DEPARTAMENTO =====");
//         try {
//             System.out.print("Ingrese el ID del empleado: ");
//             String idEmpleado = scanner.nextLine();

//             System.out.print("Ingrese el ID del departamento: ");
//             String idDepartamento = scanner.nextLine();

//             gestor.asignarEmpleadoADepartamento(idEmpleado, idDepartamento);

//         } catch (EmpleadoNoEncontradoException e) {
//             System.out.println("[EmpleadoNoEncontradoException] " + e.getMessage());
//         } catch (DepartamentoException e) {
//             System.out.println("[DepartamentoException] " + e.getMessage());
//         } catch (DepartamentoLlenoException e) {
//             System.out.println("[DepartamentoLlenoException] " + e.getMessage());
//         }
//     }

//     // ── Transferir empleado ──────────────────────────────────────────────────
//     static void transferirEmpleado() {
//         System.out.println("\n===== TRANSFERIR EMPLEADO =====");
//         try {
//             System.out.print("Ingrese el ID del empleado: ");
//             String idEmpleado = scanner.nextLine();

//             System.out.print("Ingrese el ID del departamento origen: ");
//             String idOrigen = scanner.nextLine();

//             System.out.print("Ingrese el ID del departamento destino: ");
//             String idDestino = scanner.nextLine();

//             gestor.transferirEmpleado(idEmpleado, idOrigen, idDestino);

//         } catch (EmpleadoNoEncontradoException e) {
//             System.out.println("[EmpleadoNoEncontradoException] " + e.getMessage());
//         } catch (DepartamentoException e) {
//             System.out.println("[DepartamentoException] " + e.getMessage());
//         } catch (DepartamentoLlenoException e) {
//             System.out.println("[DepartamentoLlenoException] " + e.getMessage());
//         }
//     }

//     // ── Generar reporte de desempeno ─────────────────────────────────────────
//     static void generarReporte() {
//         System.out.println("\n===== GENERAR REPORTE DE DESEMPENO =====");
//         try {
//             System.out.print("Ingrese el ID del reporte: ");
//             String idReporte = scanner.nextLine();

//             System.out.print("Ingrese el ID del empleado: ");
//             String idEmpleado = scanner.nextLine();

//             Empleado empleado = gestor.buscarEmpleadoPorId(idEmpleado);

//             System.out.print("Ingrese el periodo de evaluacion: ");
//             String periodo = scanner.nextLine();

//             ReporteDesempeno reporte = new ReporteDesempeno(
//                     idReporte, empleado, "2025-01-01", periodo);

//             // Agregar metricas
//             do {
//                 System.out.print("Nombre de la metrica (o 'fin' para terminar): ");
//                 String nombreMetrica = scanner.nextLine();
//                 if (nombreMetrica.equalsIgnoreCase("fin")) break;

//                 System.out.print("Puntuacion (0-10): ");
//                 double puntuacion = Double.parseDouble(scanner.nextLine());

//                 System.out.print("Peso (0-1): ");
//                 double peso = Double.parseDouble(scanner.nextLine());

//                 System.out.print("Observacion: ");
//                 String observacion = scanner.nextLine();

//                 reporte.agregarMetrica(new MetricaDesempeno(
//                         nombreMetrica, puntuacion, peso, observacion));

//             } while (true);

//             System.out.println(reporte.generarReporte());

//         } catch (EmpleadoNoEncontradoException e) {
//             System.out.println("[EmpleadoNoEncontradoException] " + e.getMessage());
//         } catch (NumberFormatException e) {
//             System.out.println("Error: ingrese un valor numerico valido.");
//         }
//     }
// }

// package compuwork;

// import compuwork.view.MainGUI;
// import javax.swing.SwingUtilities;

// public class Main {
//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(() -> {
//             MainGUI gui = new MainGUI();
//             gui.setVisible(true);
//         });
//     }
// }

package compuwork;

import compuwork.view.MainGUI;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI().setVisible(true));
    }
}