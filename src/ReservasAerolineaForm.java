import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ReservasAerolineaForm extends JFrame {
    private JPanel panelPrincipal;
    private JPanel panelDatos;
    private JTextField txtNombre;
    private JTextField txtCedula;
    private JRadioButton rbEconomico;
    private JRadioButton rbEjecutivo;
    private JRadioButton rbVentana;
    private JRadioButton rbPasillo;
    private JCheckBox chkAutomatico;
    private JRadioButton rbCentro;
    private JPanel panelAsientos;
    private JPanel panelEconomico;
    private JPanel panelEjecutivo;
    private JPanel panelControles;
    private JButton btnReservar;
    private JButton btnLimpiar;
    private JButton btnMostrarBoleto;
    private JTextArea txtAreaInfo;

    //Variables adicionales
    private JButton[][] botonesEjecutivo;
    private JButton[][] botonesEconomico;
    private Avion avion;
    private Asiento asientoSeleccionado;
    private ButtonGroup grupoClase;
    private ButtonGroup grupoPreferencia;

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
                //botonesEjecutivo[fila][col].addActionListener(e -> onAsientoSelected(false, f, c));

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
                //botonesEconomico[fila][col].addActionListener(e -> onAsientoSelected(true, f, c));

                panelEconomico.add(botonesEconomico[fila][col]);
            }
        }
        panelEconomico.revalidate();
        panelEconomico.repaint();


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
