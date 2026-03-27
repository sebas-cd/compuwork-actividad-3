package compuwork.view;

import compuwork.report.GestorRRHH;

import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {

    private final GestorRRHH gestor = new GestorRRHH();

    public MainGUI() {
        setTitle("CompuWork - Sistema de RRHH");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);

        ImageIcon icon = cargarIcono("logo.png", 32, 32);
        if (icon != null) setIconImage(icon.getImage());

        // Crear vistas
        EmpleadosView     vEmpleados     = new EmpleadosView(gestor);
        DepartamentosView vDepartamentos = new DepartamentosView(gestor);

        // Pasar referencia de DepartamentosView a AsignacionesView
        // para que se actualice en tiempo real al asignar o transferir
        AsignacionesView vAsignaciones = new AsignacionesView(gestor, vDepartamentos);
        ReportesView     vReportes     = new ReportesView(gestor);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Empleados",     cargarIcono("empleados.png",     16, 16), vEmpleados);
        tabs.addTab("Departamentos", cargarIcono("departamentos.png", 16, 16), vDepartamentos);
        tabs.addTab("Asignaciones",  cargarIcono("asignaciones.png",  16, 16), vAsignaciones);
        tabs.addTab("Reportes",      cargarIcono("reportes.png",      16, 16), vReportes);

        add(tabs);
    }

    public static ImageIcon cargarIcono(String nombre, int w, int h) {
        try {
            java.net.URL url = MainGUI.class.getClassLoader()
                .getResource("compuwork/resources/images/" + nombre);
            if (url == null) return null;
            Image img = new ImageIcon(url).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            return null;
        }
    }

    // Formato de número con puntos de miles y coma decimal: 1.000.000,00
    public static String formatearNumero(double valor) {
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance(new java.util.Locale("es", "CO"));
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return "$" + nf.format(valor);
    }
}