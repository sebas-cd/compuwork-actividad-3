// package compuwork.view;

// import compuwork.exception.DepartamentoException;
// import compuwork.model.Departamento;
// import compuwork.report.GestorRRHH;

// import javax.swing.*;
// import javax.swing.border.EmptyBorder;
// import javax.swing.table.DefaultTableModel;
// import java.awt.*;

// public class DepartamentosView extends BaseView {

//     private final GestorRRHH gestor;
//     private DefaultTableModel modelDepartamentos;
//     private JTable tablaDepartamentos;

//     public DepartamentosView(GestorRRHH gestor) {
//         this.gestor = gestor;
//         setLayout(new BorderLayout(12, 12));
//         setBorder(new EmptyBorder(16, 16, 16, 16));
//         add(crearFormulario(), BorderLayout.NORTH);
//         add(crearTabla(),      BorderLayout.CENTER);
//     }

//     private JPanel crearFormulario() {
//         JPanel contenedor = new JPanel(new BorderLayout(0, 10));
//         contenedor.setOpaque(false);

//         JPanel campos = new JPanel(new GridBagLayout());
//         campos.setOpaque(false);
//         GridBagConstraints g = new GridBagConstraints();
//         g.insets = new Insets(8, 8, 8, 8);
//         g.fill   = GridBagConstraints.HORIZONTAL;

//         JTextField txtId        = field("Ej: D001");
//         JTextField txtNombre    = field("Nombre del departamento");
//         JTextField txtDesc      = field("Descripción");
//         JTextField txtCapacidad = field("10");

//         g.gridx=0; g.gridy=0; g.weightx=0.2; campos.add(label("ID:"), g);
//         g.gridx=1; g.weightx=0.8; campos.add(txtId, g);
//         g.gridx=2; g.weightx=0.2; campos.add(label("Nombre:"), g);
//         g.gridx=3; g.weightx=0.8; campos.add(txtNombre, g);

//         g.gridx=0; g.gridy=1; g.weightx=0.2; campos.add(label("Descripción:"), g);
//         g.gridx=1; g.gridwidth=3; g.weightx=0.8; campos.add(txtDesc, g);
//         g.gridwidth=1;

//         g.gridx=0; g.gridy=2; g.weightx=0.2; campos.add(label("Capacidad máx:"), g);
//         g.gridx=1; g.weightx=0.3; campos.add(txtCapacidad, g);

//         JLabel msgDep = new JLabel(" ");
//         msgDep.setFont(MainGUI.FONT_NORMAL);

//         JButton btnRegistrar = boton("Registrar",            "add.png",    MainGUI.COLOR_ACENTO);
//         JButton btnEliminar  = boton("Eliminar seleccionado","delete.png", MainGUI.COLOR_ERROR);
//         JButton btnLimpiar   = boton("Limpiar",              "clear.png",  MainGUI.COLOR_PANEL2);

//         btnRegistrar.addActionListener(e -> {
//             try {
//                 String id   = txtId.getText().trim();
//                 String nom  = txtNombre.getText().trim();
//                 String desc = txtDesc.getText().trim();
//                 int    cap  = Integer.parseInt(txtCapacidad.getText().trim());

//                 if (id.isEmpty() || nom.isEmpty()) {
//                     mostrarMensaje(msgDep, "⚠  ID y nombre son obligatorios.", MainGUI.COLOR_ERROR); return;
//                 }

//                 gestor.registrarDepartamento(new Departamento(id, nom, desc, cap));
//                 actualizarTabla();
//                 limpiarCampos(txtId, txtNombre, txtDesc, txtCapacidad);
//                 mostrarMensaje(msgDep, "✔  Departamento registrado.", MainGUI.COLOR_EXITO);

//             } catch (NumberFormatException ex) {
//                 mostrarMensaje(msgDep, "⚠  La capacidad debe ser un número.", MainGUI.COLOR_ERROR);
//             }
//         });

//         btnEliminar.addActionListener(e -> {
//             int fila = tablaDepartamentos.getSelectedRow();
//             if (fila < 0) {
//                 mostrarMensaje(msgDep, "⚠  Selecciona un departamento.", MainGUI.COLOR_ERROR); return;
//             }
//             String id = (String) modelDepartamentos.getValueAt(fila, 0);
//             int ok = JOptionPane.showConfirmDialog(this,
//                 "¿Eliminar departamento con ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
//             if (ok == JOptionPane.YES_OPTION) {
//                 try {
//                     gestor.eliminarDepartamento(id);
//                     actualizarTabla();
//                     mostrarMensaje(msgDep, "✔  Departamento eliminado.", MainGUI.COLOR_EXITO);
//                 } catch (DepartamentoException ex) {
//                     mostrarMensaje(msgDep, "⚠  " + ex.getMessage(), MainGUI.COLOR_ERROR);
//                 }
//             }
//         });

//         btnLimpiar.addActionListener(e -> {
//             limpiarCampos(txtId, txtNombre, txtDesc, txtCapacidad);
//             msgDep.setText(" ");
//         });

//         JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
//         botones.setOpaque(false);
//         botones.add(btnRegistrar);
//         botones.add(btnEliminar);
//         botones.add(btnLimpiar);
//         botones.add(msgDep);

//         contenedor.add(campos,  BorderLayout.CENTER);
//         contenedor.add(botones, BorderLayout.SOUTH);
//         return contenedor;
//     }

//     private JScrollPane crearTabla() {
//         String[] cols = {"ID", "Nombre", "Descripción", "Empleados", "Capacidad", "Masa Salarial"};
//         modelDepartamentos = new DefaultTableModel(cols, 0) {
//             public boolean isCellEditable(int r, int c) { return false; }
//         };
//         tablaDepartamentos = tabla(modelDepartamentos);
//         return scrollTabla(tablaDepartamentos);
//     }

//     public void actualizarTabla() {
//         modelDepartamentos.setRowCount(0);
//         for (Departamento d : gestor.getDepartamentos()) {
//             modelDepartamentos.addRow(new Object[]{
//                 d.getId(),
//                 d.getNombre(),
//                 d.getDescripcion(),
//                 d.getTotalEmpleados(),
//                 d.getCapacidadMaxima(),
//                 String.format("$%.2f", d.calcularMasaSalarial())
//             });
//         }
//     }
// }

package compuwork.view;

import compuwork.exception.DepartamentoException;
import compuwork.model.Departamento;
import compuwork.report.GestorRRHH;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DepartamentosView extends JPanel {

    private final GestorRRHH gestor;
    private final DefaultTableModel modelo;
    private final JTable tabla;

    // Campos Registrar
    private final JTextField txtId        = new JTextField();
    private final JTextField txtNombre    = new JTextField();
    private final JTextField txtDesc      = new JTextField();
    private final JTextField txtCapacidad = new JTextField();
    private final JLabel     lblMsg       = new JLabel(" ");

    // Campos Actualizar
    private final JTextField txtActId        = new JTextField();
    private final JTextField txtActNombre    = new JTextField();
    private final JTextField txtActDesc      = new JTextField();
    private final JTextField txtActCapacidad = new JTextField();
    private final JLabel     lblMsgAct       = new JLabel(" ");

    public DepartamentosView(GestorRRHH gestor) {
        this.gestor = gestor;
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelSuperior = new JPanel(new GridLayout(1, 2, 10, 0));
        panelSuperior.add(crearPanelRegistrar());
        panelSuperior.add(crearPanelActualizar());

        modelo = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Descripcion", "Empleados actuales", "Capacidad max", "Masa salarial"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Al seleccionar fila cargar ID en campo actualizar
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) txtActId.setText((String) modelo.getValueAt(fila, 0));
        });

        add(panelSuperior,          BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    private JPanel crearPanelRegistrar() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Registrar departamento"));

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        form.add(new JLabel("ID:"));                               form.add(txtId);
        form.add(new JLabel("Nombre:"));                          form.add(txtNombre);
        form.add(new JLabel("Descripcion:"));                     form.add(txtDesc);
        form.add(new JLabel("Capacidad maxima (num empleados):")); form.add(txtCapacidad);

        JButton btnRegistrar = new JButton("Registrar",             MainGUI.cargarIcono("add.png",    16, 16));
        JButton btnEliminar  = new JButton("Eliminar seleccionado", MainGUI.cargarIcono("delete.png", 16, 16));
        JButton btnLimpiar   = new JButton("Limpiar campos",        MainGUI.cargarIcono("clear.png",  16, 16));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(lblMsg);

        btnRegistrar.addActionListener(e -> {
            try {
                String id   = txtId.getText().trim();
                String nom  = txtNombre.getText().trim();
                String desc = txtDesc.getText().trim();
                if (id.isEmpty() || nom.isEmpty() || txtCapacidad.getText().trim().isEmpty()) {
                    lblMsg.setText("ID, Nombre y Capacidad son obligatorios."); return;
                }
                int cap = Integer.parseInt(txtCapacidad.getText().trim());
                if (cap <= 0) { lblMsg.setText("La capacidad debe ser mayor a 0."); return; }
                gestor.registrarDepartamento(new Departamento(id, nom, desc, cap));
                actualizarTabla();
                limpiarRegistro();
                lblMsg.setText("Departamento registrado correctamente.");
            } catch (NumberFormatException ex) {
                lblMsg.setText("Error: la capacidad debe ser un numero entero.");
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) { lblMsg.setText("Seleccione un departamento de la tabla para eliminar."); return; }
            String id     = (String) modelo.getValueAt(fila, 0);
            String nombre = (String) modelo.getValueAt(fila, 1);
            int ok = JOptionPane.showConfirmDialog(this,
                "¿Esta seguro que desea eliminar el departamento \"" + nombre + "\" (ID: " + id + ")?\n"
                + "Solo se puede eliminar si no tiene empleados asignados.",
                "Confirmar eliminacion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (ok == JOptionPane.YES_OPTION) {
                try {
                    gestor.eliminarDepartamento(id);
                    actualizarTabla();
                    lblMsg.setText("Departamento eliminado correctamente.");
                } catch (DepartamentoException ex) {
                    lblMsg.setText(ex.getMessage());
                }
            }
        });

        btnLimpiar.addActionListener(e -> limpiarRegistro());

        panel.add(form,         BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelActualizar() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Actualizar departamento (seleccione de la tabla)"));

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        form.add(new JLabel("ID del departamento:"));      form.add(txtActId);
        form.add(new JLabel("Nuevo nombre:"));             form.add(txtActNombre);
        form.add(new JLabel("Nueva descripcion:"));        form.add(txtActDesc);
        form.add(new JLabel("Nueva capacidad maxima:"));   form.add(txtActCapacidad);

        JButton btnActualizar = new JButton("Actualizar", MainGUI.cargarIcono("add.png",   16, 16));
        JButton btnLimpiar    = new JButton("Limpiar",    MainGUI.cargarIcono("clear.png", 16, 16));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelBotones.add(btnActualizar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(lblMsgAct);

        btnActualizar.addActionListener(e -> {
            try {
                String id = txtActId.getText().trim();
                if (id.isEmpty()) {
                    lblMsgAct.setText("Seleccione un departamento de la tabla o ingrese un ID."); return;
                }
                if (txtActNombre.getText().trim().isEmpty() && txtActDesc.getText().trim().isEmpty()
                        && txtActCapacidad.getText().trim().isEmpty()) {
                    lblMsgAct.setText("Ingrese al menos un campo para actualizar."); return;
                }
                int nuevaCap = 0;
                if (!txtActCapacidad.getText().trim().isEmpty()) {
                    nuevaCap = Integer.parseInt(txtActCapacidad.getText().trim());
                }
                gestor.actualizarDepartamento(id,
                    txtActNombre.getText().trim(),
                    txtActDesc.getText().trim(),
                    nuevaCap);
                actualizarTabla();
                limpiarActualizar();
                lblMsgAct.setText("Departamento actualizado correctamente.");
            } catch (NumberFormatException ex) {
                lblMsgAct.setText("Error: la capacidad debe ser un numero entero.");
            } catch (DepartamentoException ex) {
                lblMsgAct.setText(ex.getMessage());
            }
        });

        btnLimpiar.addActionListener(e -> limpiarActualizar());

        panel.add(form,         BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        return panel;
    }

    private void limpiarRegistro() {
        txtId.setText(""); txtNombre.setText(""); txtDesc.setText(""); txtCapacidad.setText("");
        lblMsg.setText(" ");
    }

    private void limpiarActualizar() {
        txtActId.setText(""); txtActNombre.setText(""); txtActDesc.setText(""); txtActCapacidad.setText("");
        lblMsgAct.setText(" ");
    }

    public void actualizarTabla() {
        modelo.setRowCount(0);
        for (Departamento d : gestor.getDepartamentos()) {
            modelo.addRow(new Object[]{
                d.getId(),
                d.getNombre(),
                d.getDescripcion(),
                d.getTotalEmpleados(),
                d.getCapacidadMaxima(),
                MainGUI.formatearNumero(d.calcularMasaSalarial())
            });
        }
    }
}