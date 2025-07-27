import javax.swing.*;
import java.awt.*;

public class ReservasAerolineaForm extends JFrame {
    private JPanel panelPrincipal;
    private JPanel panelDatos;
    private JTextField txtNombre;
    private JTextField txtCedula;
    private JRadioButton rbEconomico;
    private JRadioButton rbEjecutivo;
    private JRadioButton rbVentana;
    private JRadioButton rbPasillo;
    private JRadioButton rbCentro;
    private JPanel panelAsientos;
    private JPanel panelEconomico;
    private JPanel panelEjecutivo;
    private JPanel panelControles;
    private JButton btnReservar;
    private JButton btnLimpiar;
    private JButton btnMostrarBoleto;
    private JTextArea txtAreaInfo;
    private JPanel panelModo;
    private JButton btnModoAsignacion;

    //Variables adicionales
    private JButton[][] botonesEjecutivo;
    private JButton[][] botonesEconomico;
    private Avion avion;
    private Asiento asientoSeleccionado;
    private ButtonGroup grupoClase;
    private ButtonGroup grupoPreferencia;
    private boolean modoAutomatico = true;

    //Constructor
    public ReservasAerolineaForm() {
        avion = new Avion();
        asientoSeleccionado = null;

        //Configuración inicial del area de Información
        txtAreaInfo.setText("=== SISTEMA DE RESERVAS AEROLÍNEA LÍNEA ===\n\n" +
                "1. Ingrese los datos del pasajero\n" +
                "2. Seleccione clase y preferencias\n" +
                "3. Elija asignación automática o manual\n" +
                "4. Presione 'Reservar' para confirmar\n\n" +
                "LEYENDA DE COLORES:\n" +
                "Verde: Disponible\n" +
                "Rojo: Ocupado\n" +
                "Amarillo: Seleccionado"
        );

        txtNombre.requestFocus();

        //Configuración general
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setupButtonGroups();
        crearAsientosEjecutivo();
        crearAsientosEconomico();
        actualizarVistaBotones();

        //Procesadores de eventos
        //Acciones para cambiar la clase del boleto
        rbEconomico.addActionListener(e -> cambioClase());
        rbEjecutivo.addActionListener(e -> cambioClase());

        //Boton para cambiar el modo de reserva
        btnModoAsignacion.addActionListener(e -> {
            modoAutomatico = !modoAutomatico;
            actualizarAparienciaBotonModo();
        });

        //Boton para reservar boleto
        btnReservar.addActionListener(e -> {
            // Validar datos
        if (!validarDatos()) return;
        
        String nombre = txtNombre.getText().trim();
        String cedula = txtCedula.getText().trim();
        Pasajero pasajero = new Pasajero(nombre, cedula);
        
        Asiento asientoReservar = null;
        
        
        if (modoAutomatico) {  
            // Asignación automática
            boolean esEconomico = rbEconomico.isSelected();
            String preferencia = getPreferenciaSeleccionada();
            
            asientoReservar = avion.asignarAutomaticamente(esEconomico, preferencia);
            
            if (asientoReservar == null) {
                JOptionPane.showMessageDialog(this,
                    "No hay asientos disponibles en la clase seleccionada.",
                    "Sin Disponibilidad",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
             } else {
            if (asientoSeleccionado == null) {
                JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un asiento del mapa.",
                    "Selección Requerida",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            asientoReservar = asientoSeleccionado;
        }
        
        // Realizar reserva
        if (avion.reservarAsiento(asientoReservar, pasajero)) {
            String clase = rbEconomico.isSelected() ? "Económica" : "Ejecutiva";
            String boleto = asientoReservar.getBoleto(clase);
            
            txtAreaInfo.setText("=== RESERVA EXITOSA ===\n\n" + boleto);
            updateAsientosVisualization();
            limpiarFormulario();
            
            JOptionPane.showMessageDialog(this,
                "Reserva realizada exitosamente!",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        } });
        //Boton para limpiar el formulario
        btnLimpiar.addActionListener(e -> {limpiarFormulario();});

        //Boton que imprime el ultimo boleto registrado
        btnMostrarBoleto.addActionListener(e -> {
            if (txtAreaInfo.getText().contains("RESERVA EXITOSA")) {
                JOptionPane.showMessageDialog(this, txtAreaInfo.getText(), "Último Boleto", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No hay boletos para mostrar.", "Sin Información", JOptionPane.INFORMATION_MESSAGE);
            }
        });

    }

    //Configura grupo de botones
    private void setupButtonGroups() {
        //grupo para las clases
        grupoClase = new ButtonGroup();
        grupoClase.add(rbEconomico);
        grupoClase.add(rbEjecutivo);

        //grupo para la preferencia
        grupoPreferencia = new ButtonGroup();
        grupoPreferencia.add(rbVentana);
        grupoPreferencia.add(rbPasillo);
        grupoPreferencia.add(rbCentro);
    }

    //Metodo para cambiar la apariencia del botón del modo segun el tipo
    private void actualizarAparienciaBotonModo() {
        if (modoAutomatico) {
            // Modo Automático - Verde
            btnModoAsignacion.setText("MODO AUTOMATICO");
            btnModoAsignacion.setBackground(new Color(76, 175, 80));
            btnModoAsignacion.setForeground(Color.WHITE);
            btnModoAsignacion.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(56, 142, 60), 2),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
        } else {
            // Modo Manual - Azul
            btnModoAsignacion.setText("MODO MANUAL");
            btnModoAsignacion.setBackground(new Color(33, 150, 243));
            btnModoAsignacion.setForeground(Color.WHITE);
            btnModoAsignacion.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(25, 118, 210), 2),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
        }
        actualizarVistaBotones();
    }

    //Metodo para crear los botones de los asientos en clase ejecutiva
    private void crearAsientosEjecutivo() {
        panelEjecutivo.removeAll();

        panelEjecutivo.setLayout(new GridLayout(4, 6, 2, 2));
        botonesEjecutivo = new JButton[3][4];

        // Headers de columnas
        panelEjecutivo.add(new JLabel("", SwingConstants.CENTER));
        panelEjecutivo.add(new JLabel("A", SwingConstants.CENTER));
        panelEjecutivo.add(new JLabel("B", SwingConstants.CENTER));
        panelEjecutivo.add(new JLabel("|", SwingConstants.CENTER));
        panelEjecutivo.add(new JLabel("C", SwingConstants.CENTER));
        panelEjecutivo.add(new JLabel("D", SwingConstants.CENTER));

        // Crear botones de asientos
        for (int fila = 0; fila < 3; fila++) {
            // Header de fila
            panelEjecutivo.add(new JLabel(String.valueOf(fila + 1), SwingConstants.CENTER));

            for (int col = 0; col < 4; col++) {
                botonesEjecutivo[fila][col] = new JButton();
                botonesEjecutivo[fila][col].setPreferredSize(new Dimension(50, 40));
                botonesEjecutivo[fila][col].setFont(new Font("SansSerif", Font.BOLD, 10));

                // Listener para selección de asiento
                final int f = fila, c = col;
                botonesEjecutivo[fila][col].addActionListener(e -> verificarAsiento(false, f, c));

                panelEjecutivo.add(botonesEjecutivo[fila][col]);

                // Espacio para corredor después de columna B
                if (col == 1) {
                    panelEjecutivo.add(new JLabel("|", SwingConstants.CENTER));
                }
            }
        }

        // Actualizar la interfaz
        panelEjecutivo.revalidate();
        panelEjecutivo.repaint();
    }

    //Metodo para crear los botones de los asientos de la clase económica
    private void crearAsientosEconomico() {
        panelEconomico.removeAll();

        panelEconomico.setLayout(new GridLayout(7, 9, 2, 2));
        botonesEconomico = new JButton[6][6];

        // Headers de columnas
        panelEconomico.add(new JLabel("", SwingConstants.CENTER));
        panelEconomico.add(new JLabel("A", SwingConstants.CENTER));
        panelEconomico.add(new JLabel("B", SwingConstants.CENTER));
        panelEconomico.add(new JLabel("C", SwingConstants.CENTER));
        panelEconomico.add(new JLabel("|", SwingConstants.CENTER));
        panelEconomico.add(new JLabel("D", SwingConstants.CENTER));
        panelEconomico.add(new JLabel("E", SwingConstants.CENTER));
        panelEconomico.add(new JLabel("F", SwingConstants.CENTER));

        // Crear botones de asientos
        for (int fila = 0; fila < 6; fila++) {
            // Header de fila
            panelEconomico.add(new JLabel(String.valueOf(fila + 1), SwingConstants.CENTER));

            for (int col = 0; col < 6; col++) {
                if (col == 3) {
                    panelEconomico.add(new JLabel("|", SwingConstants.CENTER));
                }

                botonesEconomico[fila][col] = new JButton();
                botonesEconomico[fila][col].setPreferredSize(new Dimension(40, 35));
                botonesEconomico[fila][col].setFont(new Font("SansSerif", Font.BOLD, 9));

                // Listener para selección de asiento
                final int f = fila, c = col;
                botonesEconomico[fila][col].addActionListener(e -> verificarAsiento(true, f, c));

                panelEconomico.add(botonesEconomico[fila][col]);
            }
        }
        panelEconomico.revalidate();
        panelEconomico.repaint();
    }

    private void actualizarAparienciaBotones(JButton button, Asiento asiento) {
        if (asiento.estaOcupado()) {
            button.setBackground(Color.RED);
            button.setForeground(Color.WHITE);
            button.setText("X");
            button.setEnabled(false);
        } else if (asiento == asientoSeleccionado) {
            button.setBackground(Color.YELLOW);
            button.setForeground(Color.BLACK);
            button.setEnabled(true);
        } else {
            // Color según ubicación
            switch (asiento.getTipoUbicacion()) {
                case "ventana":
                    button.setBackground(new Color(135, 206, 235));
                    button.setText("V");
                    break;
                case "pasillo":
                    button.setBackground(new Color(250, 101, 7));
                    button.setText("P");
                    break;
                case "centro":
                    button.setBackground(new Color(255, 182, 193));
                    button.setText("C");
                    break;
            }
            button.setForeground(Color.BLACK);
            button.setEnabled(!modoAutomatico || asiento == asientoSeleccionado);
        }
    }

    private void actualizarBotones(JButton[][] botones, Asiento[][] asientos) {
        if (botones == null) return;

        for (int i = 0; i < botones.length; i++) {
            for (int j = 0; j < botones[i].length; j++) {
                if (botones[i][j] != null) {
                    Asiento asiento = asientos[i][j];
                    actualizarAparienciaBotones(botones[i][j], asiento);
                }
            }
        }
    }

    private void actualizarVistaBotones() {
        actualizarBotones(botonesEjecutivo, avion.getEjecutivo());
        actualizarBotones(botonesEconomico, avion.getEconomico());
    }

    private void verificarAsiento(boolean esEconomico, int fila, int columna) {
        if (modoAutomatico) {
            return; // Asignación automática activada
        }

        // Verificar que coincida con la clase seleccionada
        if (esEconomico != rbEconomico.isSelected()) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un asiento de la clase elegida: " +
                            (rbEconomico.isSelected() ? "Económica" : "Ejecutiva"),
                    "Clase Incorrecta",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener asiento
        Asiento[][] seccion = esEconomico ? avion.getEconomico() : avion.getEjecutivo();
        Asiento asiento = seccion[fila][columna];

        if (asiento.estaOcupado()) {
            JOptionPane.showMessageDialog(this,
                    "Este asiento ya está ocupado.",
                    "Asiento No Disponible",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Actualizar selección
        asientoSeleccionado = null;
        asientoSeleccionado = asiento;
        actualizarVistaBotones();

        // Mostrar información
        txtAreaInfo.setText(
                "=== ASIENTO SELECCIONADO ===\n\n" +
                        "Posición: Fila " + (fila + 1) + ", Columna " + (columna + 1) + "\n" +
                        "Ubicación: " + asiento.getTipoUbicacion() + "\n" +
                        "Clase: " + (esEconomico ? "Económica" : "Ejecutiva") + "\n\n" +
                        "Presione 'Reservar' para confirmar o seleccione otro asiento."
        );
    }

    //Metodo que valida si es economico y habilita la opción de asiento centro
    private void cambioClase() {
        // Habilitar/deshabilitar "Centro" según la clase
        boolean esEconomico = rbEconomico.isSelected();
        rbCentro.setEnabled(esEconomico);

        if (!esEconomico && rbCentro.isSelected()) {
            rbVentana.setSelected(true);
        }

        actualizarVistaBotones();
        asientoSeleccionado = null;
    }

    //Metodo que valida si la selección es manual o automática
    private void validarAsignacion() {
        boolean esAutomatico = modoAutomatico;
        //enableManualSelection(!esAutomatico);

        if (esAutomatico) {
            asientoSeleccionado = null;
        }

        actualizarVistaBotones();
    }

    private String getPreferenciaSeleccionada() {
        if (rbVentana.isSelected()) return "ventana";
        if (rbPasillo.isSelected()) return "pasillo";
        if (rbCentro.isSelected()) return "centro";
        return "ventana";
    }

    //Metodo para limpiar el formulario
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtCedula.setText("");
        rbEconomico.setSelected(true);
        rbVentana.setSelected(true);
        asientoSeleccionado = null;
        cambioClase();
        validarAsignacion();
        txtNombre.requestFocus();

        // Si no está en modo automático, cambiarlo
        if (!modoAutomatico) {
            modoAutomatico = true;
            actualizarAparienciaBotonModo();
        }
    }


    //Main
    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        new ReservasAerolineaForm().setVisible(true);
    }
}
