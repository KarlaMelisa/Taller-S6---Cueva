import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ventana {
    private JPanel Main;
    private JTabbedPane tabbedPane1;
    private JButton btnRegistrar;
    private JComboBox cbNombre;
    private JSpinner spMes;
    private JTextField txtPrecio;          // no se usa estrictamente, pero se mantiene por compatibilidad
    private JSpinner spCantidad;
    private JList lstMes1;
    private JList lstMes2;
    private JList lstMes3;
    private JButton btnMostrar;
    private JTabbedPane tabbedPane2;
    private JComboBox cbSeleccion;
    private JTextField txtEditar;
    private JButton btnEditar;
    private JSpinner spBuscarId;
    private JButton btnBuscarId;
    private JTextArea txtBuscarId;
    private JComboBox cbBuscarNombre;
    private JButton btnBuscarNombre;
    private JTextArea txtBuscarNombre;

    Tienda tienda = new Tienda(new BusquedaLineal());

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ventana");
        frame.setContentPane(new Ventana().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Ventana() {
        // Productos fijos de la tienda (IDs 1, 2 y 3)
        tienda.agregar(new Producto(1, "Perro", 200.0));
        tienda.agregar(new Producto(2, "Gato", 150.0));
        tienda.agregar(new Producto(3, "Hamster", 300.5));

        // Configuración de spinners
        SpinnerNumberModel spmMes = new SpinnerNumberModel(1, 1, 3, 1);
        spMes.setModel(spmMes);

        SpinnerNumberModel spmCantidad = new SpinnerNumberModel(1, 1, 100, 1);
        spCantidad.setModel(spmCantidad);

        SpinnerNumberModel spmBuscarId = new SpinnerNumberModel(1, 1, 3, 1);
        spBuscarId.setModel(spmBuscarId);

        // REGISTRAR VENTA
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = cbNombre.getSelectedItem().toString();
                int mes = (int) spMes.getValue();       // 1, 2 o 3
                int cantidad = (int) spCantidad.getValue();
                int id;

                if (nombre.equals("Perro")) {
                    id = 1;
                } else if (nombre.equals("Gato")) {
                    id = 2;
                } else {
                    id = 3; // Pez
                }

                boolean ok = tienda.registrarVentaId(id, mes, cantidad);
                if (ok) {
                    Producto p = tienda.buscarPorId(id);
                    double total = p.getPrecio() * cantidad;
                    JOptionPane.showMessageDialog(null,
                            "Venta registrada.\nProducto: " + p.getNombre() +
                                    "\nCantidad: " + cantidad +
                                    "\nTotal: " + total);
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo registrar la venta.");
                }
            }
        });

        // MOSTRAR VENTAS POR MES EN LAS LISTAS
        btnMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultListModel<String> modelo1 = new DefaultListModel<>();
                DefaultListModel<String> modelo2 = new DefaultListModel<>();
                DefaultListModel<String> modelo3 = new DefaultListModel<>();

                for (Producto p : tienda.todos()) {
                    try {
                        int v1 = p.getVentasMes(0); // mes 1
                        int v2 = p.getVentasMes(1); // mes 2
                        int v3 = p.getVentasMes(2); // mes 3

                        if (v1 > 0) {
                            double total1 = v1 * p.getPrecio();
                            modelo1.addElement(p.getNombre()
                                    + " - Cantidad: " + v1
                                    + " - Total: " + total1);
                        }
                        if (v2 > 0) {
                            double total2 = v2 * p.getPrecio();
                            modelo2.addElement(p.getNombre()
                                    + " - Cantidad: " + v2
                                    + " - Total: " + total2);
                        }
                        if (v3 > 0) {
                            double total3 = v3 * p.getPrecio();
                            modelo3.addElement(p.getNombre()
                                    + " - Cantidad: " + v3
                                    + " - Total: " + total3);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }

                lstMes1.setModel(modelo1);
                lstMes2.setModel(modelo2);
                lstMes3.setModel(modelo3);
            }
        });

        // EDITAR PRECIO
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = cbSeleccion.getSelectedItem().toString();
                double nuevoPrecio;

                try {
                    nuevoPrecio = Double.parseDouble(txtEditar.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Ingrese un precio válido.");
                    return;
                }

                boolean ok;
                if (nombre.equals("Perro")) {
                    ok = tienda.actualizarPrecioId(1, nuevoPrecio);
                } else if (nombre.equals("Gato")) {
                    ok = tienda.actualizarPrecioId(2, nuevoPrecio);
                } else {
                    ok = tienda.actualizarPrecioId(3, nuevoPrecio);
                }

                if (ok) {
                    JOptionPane.showMessageDialog(null, "Precio actualizado.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo actualizar el precio.");
                }
            }
        });

        // BUSCAR POR ID
        btnBuscarId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = (int) spBuscarId.getValue();
                Producto p = tienda.buscarPorId(id);

                if (p == null) {
                    txtBuscarId.setText("No existe ningún producto con ID " + id);
                } else {
                    txtBuscarId.setText(generarResumenVentas(p));
                }
            }
        });

        // BUSCAR POR NOMBRE
        btnBuscarNombre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = cbBuscarNombre.getSelectedItem().toString();
                Producto p = tienda.buscarPorNombre(nombre);

                if (p == null) {
                    txtBuscarNombre.setText("No existe ningún producto con nombre: " + nombre);
                } else {
                    txtBuscarNombre.setText(generarResumenVentas(p));
                }
            }
        });
    }

    /**
     * Genera el resumen de todas las ventas del producto
     * (los 3 meses, cantidad y total por mes + total general).
     */
    private String generarResumenVentas(Producto p) {
        StringBuilder sb = new StringBuilder();

        sb.append("Producto: ").append(p.getNombre())
                .append(" (ID: ").append(p.getId()).append(")\n");
        sb.append("Precio unitario actual: ").append(p.getPrecio()).append("\n\n");

        double totalGeneral = 0;

        for (int mesIndex = 0; mesIndex < 3; mesIndex++) { // 0,1,2 => Mes 1,2,3
            int cantidad;
            try {
                cantidad = p.getVentasMes(mesIndex);
            } catch (Exception e) {
                sb.append("Error leyendo ventas del mes ")
                        .append(mesIndex + 1).append(": ").append(e.getMessage()).append("\n");
                continue;
            }

            if (cantidad > 0) {
                double total = cantidad * p.getPrecio();
                totalGeneral += total;
                sb.append("Mes ").append(mesIndex + 1).append(":\n");
                sb.append("  Cantidad vendida: ").append(cantidad).append("\n");
                sb.append("  Total: ").append(total).append("\n\n");
            }
        }

        sb.append("Total general vendido (3 meses): ").append(totalGeneral);

        return sb.toString();
    }
}
