import com.mysql.cj.protocol.Resultset;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class loginadmin extends JFrame{
    private JPanel login;
    private JTextField cedu;
    private JPasswordField clav;
    private JButton ingresarButton;

    public loginadmin() {
        super("Login");
        setSize(400,500);
        setContentPane(login);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //CIERRA LA VENTANA

        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = cedu.getText();
                String contrasena = new String(clav.getPassword());

                if (validarlogin(cedula, contrasena)) {
                    JOptionPane.showMessageDialog(null, "Iniciaste Sesión de manera exitosa :D ");
                    new menuadmin().setVisible(true);
                    dispose();
                }
            }
        });
    }


    public boolean validarlogin(String cedula, String contrasena) {
        boolean validar = false;
        String url = "jdbc:mysql://localhost:3306/curso";
        String username = "root";
        String password = "123456";
        try {
            Connection conexion = DriverManager.getConnection(url, username, password);
            String sql = "select * from usuarioadministrador where cedula =? and clave =?";
            PreparedStatement pst = conexion.prepareStatement(sql);
            pst.setString(1, cedula);
            pst.setString(2, contrasena);
            ResultSet resultset = pst.executeQuery();
            if (resultset.next()) {
                validar = true;
            }
            resultset.close();
            pst.close();
            conexion.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return validar;
    }

public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
            new loginadmin().setVisible(true);
        }
    });
}
}