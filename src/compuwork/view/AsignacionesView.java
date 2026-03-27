package compuwork.view;

import compuwork.exception.*;
import compuwork.model.Empleado;
import compuwork.report.GestorRRHH;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AsignacionesView extends JPanel {

    private final GestorRRHH       gestor;
    private final DepartamentosView vDepartamentos;

    // Asignar
    private final JTextField txtIdEmpAsig  = new JTextField();
    private final JTextField txtIdDepAsig  = new JTextField();

    // Transferir
    private final JTextField txtIdEmpTrans = new JTextField();
    private final JTextField txtOrigen     = new JTextField();
    private final JTextField txtDestino    = new JTextField();

    // Desasignar
    private final JTextField txtIdEmpDes   = new JTextField();

    // Tabla de asignaciones actuales
    private final DefaultTableModel modeloAsig;
    private final JTable tablaAsig;

    public AsignacionesView(GestorRRHH gestor, DepartamentosView vDepartamentos) {
        this.gestor         = gestor;
        this.vDepartamentos = vDepartamentos;
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel superior con las 3 acciones
        JPanel panelAcciones = new JPanel(new GridLayout(1, 3, 8, 0));
        panelAcciones.add(crearPanelAsignar());
        panelAcciones.add(crearPanelTransferir());
        panelAcciones.add(crearPanelDesasignar());

        // Tabla que muestra asignaciones actuales (empleado -> departamento)
        modeloAsig = new DefaultTableModel(
            new String[]{"ID Empleado", "Nombre completo", "Tipo contrato", "Departamento asignado"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaAsig = new JTable(modeloAsig);
        tablaAsig.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Al seleccionar fila cargar ID en campo desasignar
        tablaAsig.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaAsig.getSelectedRow();
            if (fila >= 0) txtIdEmpDes.setText((String) modeloAsig.getValueAt(fila, 0));
        });

        JScrollPane scrollTabla = new JScrollPane(tablaAsig);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Asignaciones actuales"));

        add(panelAcciones, BorderLayout.NORTH);
        add(scrollTabla,   BorderLayout.CENTER);

        actualizarTablaAsignaciones();
    }

    private JPanel crearPanelAsignar() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Asignar empleado"));

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        form.add(new JLabel("ID del empleado:"));     form.add(txtIdEmpAsig);
        form.add(new JLabel("ID del departamento:")); form.add(txtIdDepAsig);

        JLabel  lblMsg     = new JLabel(" ");
        JButton btnAsignar = new JButton("Asignar",  MainGUI.cargarIcono("assign.png", 16, 16));
        JButton btnLimpiar = new JButton("Limpiar",  MainGUI.cargarIcono("clear.png",  16, 16));

        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelBtn.add(btnAsignar);
        panelBtn.add(btnLimpiar);
        panelBtn.add(lblMsg);

        btnAsignar.addActionListener(e -> {
            try {
                String idEmp = txtIdEmpAsig.getText().trim();
                String idDep = txtIdDepAsig.getText().trim();
                if (idEmp.isEmpty() || idDep.isEmpty()) {
                    lblMsg.setText("Ingrese ambos IDs."); return;
                }
                gestor.asignarEmpleadoADepartamento(idEmp, idDep);
                txtIdEmpAsig.setText(""); txtIdDepAsig.setText("");
                lblMsg.setText("Asignado correctamente.");
                actualizarTablaAsignaciones();
                vDepartamentos.actualizarTabla();
            } catch (EmpleadoNoEncontradoException | DepartamentoException | DepartamentoLlenoException ex) {
                lblMsg.setText(ex.getMessage());
            }
        });

        btnLimpiar.addActionListener(e -> {
            txtIdEmpAsig.setText(""); txtIdDepAsig.setText(""); lblMsg.setText(" ");
        });

        panel.add(form,     BorderLayout.CENTER);
        panel.add(panelBtn, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelTransferir() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Transferir empleado"));

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        form.add(new JLabel("ID del empleado:"));         form.add(txtIdEmpTrans);
        form.add(new JLabel("ID departamento origen:"));  form.add(txtOrigen);
        form.add(new JLabel("ID departamento destino:")); form.add(txtDestino);

        JLabel  lblMsg        = new JLabel(" ");
        JButton btnTransferir = new JButton("Transferir", MainGUI.cargarIcono("transfer.png", 16, 16));
        JButton btnLimpiar    = new JButton("Limpiar",    MainGUI.cargarIcono("clear.png",    16, 16));

        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelBtn.add(btnTransferir);
        panelBtn.add(btnLimpiar);
        panelBtn.add(lblMsg);

        btnTransferir.addActionListener(e -> {
            try {
                String idEmp  = txtIdEmpTrans.getText().trim();
                String origen = txtOrigen.getText().trim();
                String dest   = txtDestino.getText().trim();
                if (idEmp.isEmpty() || origen.isEmpty() || dest.isEmpty()) {
                    lblMsg.setText("Complete todos los campos."); return;
                }
                gestor.transferirEmpleado(idEmp, origen, dest);
                txtIdEmpTrans.setText(""); txtOrigen.setText(""); txtDestino.setText("");
                lblMsg.setText("Transferido correctamente.");
                actualizarTablaAsignaciones();
                vDepartamentos.actualizarTabla();
            } catch (EmpleadoNoEncontradoException | DepartamentoException | DepartamentoLlenoException ex) {
                lblMsg.setText(ex.getMessage());
            }
        });

        btnLimpiar.addActionListener(e -> {
            txtIdEmpTrans.setText(""); txtOrigen.setText(""); txtDestino.setText(""); lblMsg.setText(" ");
        });

        panel.add(form,     BorderLayout.CENTER);
        panel.add(panelBtn, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelDesasignar() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Desasignar empleado (seleccione de la tabla)"));

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        form.add(new JLabel("ID del empleado:")); form.add(txtIdEmpDes);

        JLabel  lblMsg       = new JLabel(" ");
        JButton btnDesasignar = new JButton("Desasignar", MainGUI.cargarIcono("delete.png", 16, 16));
        JButton btnLimpiar    = new JButton("Limpiar",    MainGUI.cargarIcono("clear.png",  16, 16));

        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelBtn.add(btnDesasignar);
        panelBtn.add(btnLimpiar);
        panelBtn.add(lblMsg);

        btnDesasignar.addActionListener(e -> {
            try {
                String idEmp = txtIdEmpDes.getText().trim();
                if (idEmp.isEmpty()) {
                    lblMsg.setText("Seleccione un empleado de la tabla o ingrese un ID."); return;
                }
                Empleado emp = gestor.buscarEmpleadoPorId(idEmp);
                int ok = JOptionPane.showConfirmDialog(this,
                    "¿Esta seguro que desea desasignar a " + emp.getNombreCompleto() +
                    " del departamento \"" + emp.getDepartamento() + "\"?",
                    "Confirmar desasignacion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (ok == JOptionPane.YES_OPTION) {
                    gestor.desasignarEmpleado(idEmp);
                    txtIdEmpDes.setText("");
                    lblMsg.setText("Empleado desasignado correctamente.");
                    actualizarTablaAsignaciones();
                    vDepartamentos.actualizarTabla();
                }
            } catch (EmpleadoNoEncontradoException | DepartamentoException ex) {
                lblMsg.setText(ex.getMessage());
            }
        });

        btnLimpiar.addActionListener(e -> { txtIdEmpDes.setText(""); lblMsg.setText(" "); });

        panel.add(form,     BorderLayout.CENTER);
        panel.add(panelBtn, BorderLayout.SOUTH);
        return panel;
    }

    // Muestra solo los empleados que tienen departamento asignado
    public void actualizarTablaAsignaciones() {
        modeloAsig.setRowCount(0);
        for (Empleado e : gestor.getEmpleados()) {
            if (!e.getDepartamento().equals("Sin asignar")) {
                modeloAsig.addRow(new Object[]{
                    e.getId(),
                    e.getNombreCompleto(),
                    e.getTipoContrato(),
                    e.getDepartamento()
                });
            }
        }
    }
}