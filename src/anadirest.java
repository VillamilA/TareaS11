import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class anadirest extends JFrame{
    private JTextField mat;
    private JTextField nom;
    private JTextField ape;
    private JTextField dir;
    private JTextField ed;
    private JTextField tlf;
    private JTextField cor;
    private JTextField nt1;
    private JTextField nt2;
    private JButton registrarButton;
    private JButton regresarButton;

public anadirest(){
    setTitle("Añadir estudiante");
    setSize(400,574);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);

    registrarButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String matricula = mat.getText();
            String nombre = nom.getText();
            String apellido = ape.getText();
            String direccion = dir.getText();
            String edad = ed.getText();
            String telefono = tlf.getText();
            String correo = cor.getText();
            String nota1= nt1.getText();
            String nota2= nt2.getText();

            if(matricula.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || direccion.isEmpty() || edad.isEmpty() ||
            telefono.isEmpty() || correo.isEmpty() || nota1.isEmpty() || nota2.isEmpty()){
                anadir(matricula,nombre,apellido,direccion,edad,telefono,correo,nota1,nota2);
            }
        }
    });
}

    private void anadir(String matricula, String nombre, String apellido, String direccion, String edad, String telefono, String correo, String nota1, String nota2) {
    String url = "jdbc:mysql://localhost:3306/curso";
    String username = "root";
    String password = "123456";

        try{
            Connection conexion = DriverManager.getConnection(url, username, password);
            String sql = "insert into estudiantes (matricula,nombre,apellido,direccion,edad,telefono,correo,nota1,nota2) values (?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = conexion.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(matricula));
            pst.setString(2, nombre);
            pst.setString(3, apellido);
            pst.setString(4, direccion);
            pst.setInt(5, Integer.parseInt(edad));
            pst.setString(6, telefono);
            pst.setString(7, correo);
            pst.setBigDecimal(8, new BigDecimal(nota1));
            pst.setBigDecimal(9, new BigDecimal(nota2));

            int rowsIns = pst.executeUpdate();
            if(rowsIns > 0){
                JOptionPane.showMessageDialog(null, "Estudiante registrado");
                pst.close();
                conexion.close();
            }
            else{
                JOptionPane.showMessageDialog(null, "Recuerda llenar todo los campos");
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
