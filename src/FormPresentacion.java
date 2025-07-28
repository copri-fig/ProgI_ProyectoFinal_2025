import javax.swing.*;
import java.awt.*;

public class FormPresentacion extends JFrame {
    private JPanel panelPrincipal;
    private JLabel lblUTP;
    private JLabel lblFisc;
    private JButton btnContinuar;

    public FormPresentacion() {
        setTitle("Presentación");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,700);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        setVisible(true);

        ImageIcon iconLogo = new ImageIcon(getClass().getResource("/assets/logoUTP.jpg"));
        Image iconEscalaLogo = iconLogo.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        lblUTP.setIcon(new ImageIcon(iconEscalaLogo));
        ImageIcon iconFisc = new ImageIcon(getClass().getResource("/assets/logoFISC.jpg"));
        Image iconEscalaFisc = iconFisc.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        lblFisc.setIcon(new ImageIcon(iconEscalaFisc));

        btnContinuar.addActionListener(e ->{
            new ReservasAerolineaForm().setVisible(true);
            dispose();
        });
    }

    public static void main(String[] args) {
        new FormPresentacion();
    }
}
