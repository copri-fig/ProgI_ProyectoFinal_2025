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
    private ButtonGroup grupoClase;
    private ButtonGroup grupoPreferencia;

    //Constructor
    public ReservasAerolineaForm() {
        avion = new Avion();


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
