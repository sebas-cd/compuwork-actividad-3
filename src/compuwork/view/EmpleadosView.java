package compuwork.view;

import compuwork.exception.*;
import compuwork.model.*;
import compuwork.report.GestorRRHH;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmpleadosView extends JPanel {

    private final GestorRRHH gestor;
    private final DefaultTableModel modelo;
    private final JTable tabla;

    // Campos Registrar
    private final JComboBox<String> tipoCombo = new JComboBox<>(new String[]{"Permanente", "Temporal"});
    private final JTextField txtId        = new JTextField();
    private final JTextField txtNombre    = new JTextField();
    private final JTextField txtApellido  = new JTextField();
    private final JTextField txtEmail     = new JTextField();

    // Permanente
    private final JLabel     lblSalario   = new JLabel("Salario base ($):");
    private final JTextField txtSalario   = new JTextField();
    private final JLabel     lblBonif     = new JLabel("Bonificacion anual ($):");
    private final JTextField txtBonif     = new JTextField();
    private final JLabel     lblVacas     = new JLabel("Dias de vacaciones:");
    private final JTextField txtVacas     = new JTextField();
    private final JCheckBox  chkSeguro    = new JCheckBox("Tiene seguro medico");

    // Temporal
    private final JLabel     lblFecha     = new JLabel("Fecha fin contrato:");
    private final JSpinner   spinnerFecha;  // selector de fecha
    private final JLabel     lblTarifa    = new JLabel("Tarifa por hora ($):");
    private final JTextField txtTarifa    = new JTextField();
    private final JLabel     lblHoras     = new JLabel("Horas trabajadas:");
    private final JTextField txtHoras     = new JTextField();
    private final JLabel     lblAgencia   = new JLabel("Agencia contratante:");
    private final JTextField txtAgencia   = new JTextField();

    // Campos Actualizar
    private final JTextField txtActId       = new JTextField();
    private final JTextField txtActNombre   = new JTextField();
    private final JTextField txtActApellido = new JTextField();
    private final JTextField txtActEmail    = new JTextField();

    private final JLabel lblMsg    = new JLabel(" ");
    private final JLabel lblMsgAct = new JLabel(" ");

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public EmpleadosView(GestorRRHH gestor) {
        this.gestor = gestor;

        // Configurar el spinner de fecha
        SpinnerDateModel modeloFecha = new SpinnerDateModel();
        spinnerFecha = new JSpinner(modeloFecha);
        JSpinner.DateEditor editorFecha = new JSpinner.DateEditor(spinnerFecha, "yyyy-MM-dd");
        spinnerFecha.setEditor(editorFecha);
        spinnerFecha.setValue(new Date()); // fecha inicial = hoy

        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelSuperior = new JPanel(new GridLayout(1, 2, 10, 0));
        panelSuperior.add(crearPanelRegistrar());
        panelSuperior.add(crearPanelActualizar());

        modelo = new DefaultTableModel(
            new String[]{"ID", "Nombre completo", "Tipo contrato", "Salario mensual"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) txtActId.setText((String) modelo.getValueAt(fila, 0));
        });

        add(panelSuperior,          BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        setVisibleTemporal(false);
        tipoCombo.addActionListener(e -> {
            boolean perm = tipoCombo.getSelectedItem().equals("Permanente");
            setVisiblePermanente(perm);
            setVisibleTemporal(!perm);
        });
    }

    private JPanel crearPanelRegistrar() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Registrar nuevo empleado"));

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        form.add(new JLabel("Tipo de empleado:")); form.add(tipoCombo);
        form.add(new JLabel("ID:"));               form.add(txtId);
        form.add(new JLabel("Nombre:"));           form.add(txtNombre);
        form.add(new JLabel("Apellido:"));         form.add(txtApellido);
        form.add(new JLabel("Email:"));            form.add(txtEmail);
        // Permanente
        form.add(lblSalario);  form.add(txtSalario);
        form.add(lblBonif);    form.add(txtBonif);
        form.add(lblVacas);    form.add(txtVacas);
        form.add(chkSeguro);   form.add(new JLabel());
        // Temporal
        form.add(lblFecha);    form.add(spinnerFecha); // selector de fecha
        form.add(lblTarifa);   form.add(txtTarifa);
        form.add(lblHoras);    form.add(txtHoras);
        form.add(lblAgencia);  form.add(txtAgencia);

        JButton btnRegistrar = new JButton("Registrar",             MainGUI.cargarIcono("add.png",    16, 16));
        JButton btnEliminar  = new JButton("Eliminar seleccionado", MainGUI.cargarIcono("delete.png", 16, 16));
        JButton btnLimpiar   = new JButton("Limpiar campos",        MainGUI.cargarIcono("clear.png",  16, 16));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(lblMsg);

        btnRegistrar.addActionListener(e -> registrar());
        btnEliminar.addActionListener(e  -> eliminar());
        btnLimpiar.addActionListener(e   -> limpiarRegistro());

        panel.add(form,         BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelActualizar() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Actualizar empleado (seleccione de la tabla)"));

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        form.add(new JLabel("ID del empleado:"));  form.add(txtActId);
        form.add(new JLabel("Nuevo nombre:"));     form.add(txtActNombre);
        form.add(new JLabel("Nuevo apellido:"));   form.add(txtActApellido);
        form.add(new JLabel("Nuevo email:"));      form.add(txtActEmail);

        JButton btnActualizar = new JButton("Actualizar", MainGUI.cargarIcono("add.png",   16, 16));
        JButton btnLimpiar    = new JButton("Limpiar",    MainGUI.cargarIcono("clear.png", 16, 16));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelBotones.add(btnActualizar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(lblMsgAct);

        btnActualizar.addActionListener(e -> actualizar());
        btnLimpiar.addActionListener(e    -> limpiarActualizar());

        panel.add(form,         BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        return panel;
    }

    // ── Acciones ──────────────────────────────────────────────────────────────

    private void registrar() {
        try {
            String id       = txtId.getText().trim();
            String nombre   = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String email    = txtEmail.getText().trim();

            if (id.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || email.isEmpty()) {
                lblMsg.setText("Complete los campos: ID, Nombre, Apellido y Email."); return;
            }

            if (tipoCombo.getSelectedItem().equals("Permanente")) {
                if (txtSalario.getText().trim().isEmpty() || txtBonif.getText().trim().isEmpty()
                        || txtVacas.getText().trim().isEmpty()) {
                    lblMsg.setText("Complete todos los campos del empleado permanente."); return;
                }
                double salario = Double.parseDouble(txtSalario.getText().trim());
                double bonif   = Double.parseDouble(txtBonif.getText().trim());
                int    vacas   = Integer.parseInt(txtVacas.getText().trim());
                gestor.registrarEmpleado(new EmpleadoPermanente(
                    id, nombre, apellido, email, salario, bonif, chkSeguro.isSelected(), vacas));
            } else {
                if (txtTarifa.getText().trim().isEmpty() || txtHoras.getText().trim().isEmpty()
                        || txtAgencia.getText().trim().isEmpty()) {
                    lblMsg.setText("Complete todos los campos del empleado temporal."); return;
                }
                // Obtener la fecha del spinner y formatearla como yyyy-MM-dd
                Date fechaSeleccionada = (Date) spinnerFecha.getValue();
                String fechaFin = sdf.format(fechaSeleccionada);

                double tarifa = Double.parseDouble(txtTarifa.getText().trim());
                int    horas  = Integer.parseInt(txtHoras.getText().trim());
                gestor.registrarEmpleado(new EmpleadoTemporal(
                    id, nombre, apellido, email, 0,
                    fechaFin, tarifa, horas, txtAgencia.getText().trim()));
            }

            actualizarTabla();
            limpiarRegistro();
            lblMsg.setText("Empleado registrado correctamente.");

        } catch (NumberFormatException ex) {
            lblMsg.setText("Error: ingrese solo numeros en los campos numericos.");
        } catch (SalarioInvalidoException | EmpleadoDuplicadoException ex) {
            lblMsg.setText(ex.getMessage());
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) { lblMsg.setText("Seleccione un empleado de la tabla para eliminar."); return; }
        String id     = (String) modelo.getValueAt(fila, 0);
        String nombre = (String) modelo.getValueAt(fila, 1);
        int ok = JOptionPane.showConfirmDialog(this,
            "¿Esta seguro que desea eliminar a " + nombre + " (ID: " + id + ")?",
            "Confirmar eliminacion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (ok == JOptionPane.YES_OPTION) {
            try {
                gestor.eliminarEmpleado(id);
                actualizarTabla();
                lblMsg.setText("Empleado eliminado correctamente.");
            } catch (EmpleadoNoEncontradoException ex) {
                lblMsg.setText(ex.getMessage());
            }
        }
    }

    private void actualizar() {
        try {
            String id = txtActId.getText().trim();
            if (id.isEmpty()) { lblMsgAct.setText("Seleccione un empleado de la tabla o ingrese un ID."); return; }
            if (txtActNombre.getText().trim().isEmpty() && txtActApellido.getText().trim().isEmpty()
                    && txtActEmail.getText().trim().isEmpty()) {
                lblMsgAct.setText("Ingrese al menos un campo para actualizar."); return;
            }
            gestor.actualizarEmpleado(id,
                txtActNombre.getText().trim(),
                txtActApellido.getText().trim(),
                txtActEmail.getText().trim());
            actualizarTabla();
            limpiarActualizar();
            lblMsgAct.setText("Empleado actualizado correctamente.");
        } catch (EmpleadoNoEncontradoException ex) {
            lblMsgAct.setText(ex.getMessage());
        }
    }

    private void limpiarRegistro() {
        txtId.setText(""); txtNombre.setText(""); txtApellido.setText(""); txtEmail.setText("");
        txtSalario.setText(""); txtBonif.setText(""); txtVacas.setText("");
        txtTarifa.setText(""); txtHoras.setText(""); txtAgencia.setText("");
        chkSeguro.setSelected(false);
        spinnerFecha.setValue(new Date()); // resetear al dia de hoy
        lblMsg.setText(" ");
    }

    private void limpiarActualizar() {
        txtActId.setText(""); txtActNombre.setText("");
        txtActApellido.setText(""); txtActEmail.setText("");
        lblMsgAct.setText(" ");
    }

    private void setVisiblePermanente(boolean v) {
        lblSalario.setVisible(v); txtSalario.setVisible(v);
        lblBonif.setVisible(v);   txtBonif.setVisible(v);
        lblVacas.setVisible(v);   txtVacas.setVisible(v);
        chkSeguro.setVisible(v);
    }

    private void setVisibleTemporal(boolean v) {
        lblFecha.setVisible(v);   spinnerFecha.setVisible(v);
        lblTarifa.setVisible(v);  txtTarifa.setVisible(v);
        lblHoras.setVisible(v);   txtHoras.setVisible(v);
        lblAgencia.setVisible(v); txtAgencia.setVisible(v);
    }

    public void actualizarTabla() {
        modelo.setRowCount(0);
        for (Empleado e : gestor.getEmpleados()) {
            modelo.addRow(new Object[]{
                e.getId(),
                e.getNombreCompleto(),
                e.getTipoContrato(),
                MainGUI.formatearNumero(e.calcularSalario())
            });
        }
    }
}