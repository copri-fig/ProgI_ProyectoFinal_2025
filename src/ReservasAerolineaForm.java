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
