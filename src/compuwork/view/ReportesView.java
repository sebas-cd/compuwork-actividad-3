package compuwork.view;

import compuwork.exception.*;
import compuwork.model.*;
import compuwork.report.GestorRRHH;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ReportesView extends JPanel {

    private final GestorRRHH gestor;

    private final JTextField txtIdRep   = new JTextField();
    private final JTextField txtIdEmp   = new JTextField();
    private final JTextField txtPeriodo = new JTextField();
    private final JTextField txtIdDep   = new JTextField();

    private final ArrayList<JPanel> panelesMétrica = new ArrayList<>();
    private JPanel    panelListaMetricas;
    private JTextArea areaReporte;

    public ReportesView(GestorRRHH gestor) {
        this.gestor = gestor;
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelSuperior = new JPanel(new GridLayout(1, 2, 10, 0));
        panelSuperior.add(crearPanelIndividual());
        panelSuperior.add(crearPanelDepartamento());

        areaReporte = new JTextArea();
        areaReporte.setEditable(false);
        areaReporte.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaReporte.setBorder(BorderFactory.createTitledBorder("Resultado del reporte"));

        add(panelSuperior,                BorderLayout.NORTH);
        add(new JScrollPane(areaReporte), BorderLayout.CENTER);
    }

    private JPanel crearPanelIndividual() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Reporte de desempeno individual"));

        JPanel formPrincipal = new JPanel(new GridLayout(0, 2, 5, 5));
        formPrincipal.add(new JLabel("ID del reporte:"));   formPrincipal.add(txtIdRep);
        formPrincipal.add(new JLabel("ID del empleado:"));  formPrincipal.add(txtIdEmp);
        formPrincipal.add(new JLabel("Periodo evaluado:")); formPrincipal.add(txtPeriodo);

        // Encabezado de métricas
        panelListaMetricas = new JPanel();
        panelListaMetricas.setLayout(new BoxLayout(panelListaMetricas, BoxLayout.Y_AXIS));

        JPanel encabezado = new JPanel(new GridLayout(1, 4, 5, 0));
        encabezado.add(boldLabel("Nombre metrica"));
        encabezado.add(boldLabel("Puntuacion (0-10)"));
        encabezado.add(boldLabel("Peso (0.0 a 1.0)"));
        encabezado.add(boldLabel("Observacion"));
        encabezado.setMaximumSize(new Dimension(Integer.MAX_VALUE, 22));
        panelListaMetricas.add(encabezado);
        panelListaMetricas.add(Box.createVerticalStrut(3));

        JScrollPane scrollMetricas = new JScrollPane(panelListaMetricas);
        scrollMetricas.setBorder(BorderFactory.createTitledBorder("Metricas de evaluacion"));
        scrollMetricas.setPreferredSize(new Dimension(0, 140));

        JLabel  lblMsg        = new JLabel(" ");
        JButton btnAddMetrica = new JButton("+ Agregar metrica");
        JButton btnGenerar    = new JButton("Generar reporte", MainGUI.cargarIcono("reportes.png", 16, 16));
        JButton btnLimpiar    = new JButton("Limpiar",         MainGUI.cargarIcono("clear.png",    16, 16));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelBotones.add(btnAddMetrica);
        panelBotones.add(btnGenerar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(lblMsg);

        btnAddMetrica.addActionListener(e -> agregarFilaMetrica());

        btnGenerar.addActionListener(e -> {
            try {
                String idRep   = txtIdRep.getText().trim();
                String idEmp   = txtIdEmp.getText().trim();
                String periodo = txtPeriodo.getText().trim();

                if (idRep.isEmpty() || idEmp.isEmpty() || periodo.isEmpty()) {
                    lblMsg.setText("Complete los campos: ID Reporte, ID Empleado y Periodo."); return;
                }
                if (panelesMétrica.isEmpty()) {
                    lblMsg.setText("Agregue al menos una metrica de evaluacion."); return;
                }

                Empleado emp = gestor.buscarEmpleadoPorId(idEmp);
                ReporteDesempeno reporte = new ReporteDesempeno(
                    idRep, emp, java.time.LocalDate.now().toString(), periodo);

                for (JPanel fila : panelesMétrica) {
                    Component[] comps = fila.getComponents();
                    String nom  = ((JTextField) comps[0]).getText().trim();
                    String punt = ((JTextField) comps[1]).getText().trim();
                    String peso = ((JTextField) comps[2]).getText().trim();
                    String obs  = ((JTextField) comps[3]).getText().trim();
                    if (nom.isEmpty() || punt.isEmpty() || peso.isEmpty()) {
                        lblMsg.setText("Complete Nombre, Puntuacion y Peso en cada metrica."); return;
                    }
                    reporte.agregarMetrica(new MetricaDesempeno(
                        nom, Double.parseDouble(punt), Double.parseDouble(peso), obs));
                }

                areaReporte.setText(reporte.generarReporte());
                lblMsg.setText("Reporte generado correctamente.");

            } catch (EmpleadoNoEncontradoException ex) {
                lblMsg.setText("No se encontro el empleado con ese ID.");
            } catch (NumberFormatException ex) {
                lblMsg.setText("Error: Puntuacion y Peso deben ser numeros (ej: 8.5 y 0.3).");
            }
        });

        btnLimpiar.addActionListener(e -> {
            txtIdRep.setText(""); txtIdEmp.setText(""); txtPeriodo.setText("");
            panelListaMetricas.removeAll();
            panelesMétrica.clear();
            panelListaMetricas.add(encabezado);
            panelListaMetricas.add(Box.createVerticalStrut(3));
            panelListaMetricas.revalidate(); panelListaMetricas.repaint();
            areaReporte.setText("");
            lblMsg.setText(" ");
        });

        JPanel contenido = new JPanel(new BorderLayout(5, 5));
        contenido.add(formPrincipal,  BorderLayout.NORTH);
        contenido.add(scrollMetricas, BorderLayout.CENTER);
        contenido.add(panelBotones,   BorderLayout.SOUTH);

        panel.add(contenido, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelDepartamento() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Reporte de departamento"));

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        form.add(new JLabel("ID del departamento:")); form.add(txtIdDep);

        JLabel  lblMsg     = new JLabel(" ");
        JButton btnGenerar = new JButton("Generar reporte", MainGUI.cargarIcono("reportes.png", 16, 16));
        JButton btnLimpiar = new JButton("Limpiar",         MainGUI.cargarIcono("clear.png",    16, 16));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelBotones.add(btnGenerar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(lblMsg);

        btnGenerar.addActionListener(e -> {
            try {
                String idDep = txtIdDep.getText().trim();
                if (idDep.isEmpty()) { lblMsg.setText("Ingrese el ID del departamento."); return; }
                areaReporte.setText(gestor.generarReporteDepartamento(idDep));
                lblMsg.setText("Reporte generado correctamente.");
            } catch (DepartamentoException ex) {
                lblMsg.setText("No se encontro el departamento con ese ID.");
            }
        });

        btnLimpiar.addActionListener(e -> {
            txtIdDep.setText(""); areaReporte.setText(""); lblMsg.setText(" ");
        });

        panel.add(form,         BorderLayout.NORTH);
        panel.add(panelBotones, BorderLayout.SOUTH);
        return panel;
    }

    private void agregarFilaMetrica() {
        JPanel fila = new JPanel(new GridLayout(1, 4, 5, 0));
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        JTextField tNom  = new JTextField(); tNom.setToolTipText("Nombre de la metrica (ej: Puntualidad)");
        JTextField tPunt = new JTextField(); tPunt.setToolTipText("Puntuacion del 0 al 10 (ej: 8.5)");
        JTextField tPeso = new JTextField(); tPeso.setToolTipText("Peso de 0.0 a 1.0 (ej: 0.3)");
        JTextField tObs  = new JTextField(); tObs.setToolTipText("Observacion (opcional)");
        fila.add(tNom); fila.add(tPunt); fila.add(tPeso); fila.add(tObs);
        panelListaMetricas.add(fila);
        panelListaMetricas.add(Box.createVerticalStrut(3));
        panelListaMetricas.revalidate();
        panelesMétrica.add(fila);
    }

    private JLabel boldLabel(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(l.getFont().deriveFont(java.awt.Font.BOLD));
        return l;
    }
}