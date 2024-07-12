import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class menuadmin extends JFrame {
    private JPanel menu;
    private JButton ingresarEstudianteButton;
    private JButton verLosRegistrosButton;
    private JButton consultaPorMatrículaButton;
    private JTextField consulta;
    private JScrollPane scroll;
    private JTable tablaestudiantes;

    public menuadmin() {
        setTitle("Menu Administrador");
        setSize(400, 574);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setLayout(null);
        setLocationRelativeTo(null);

        tablaestudiantes = new JTable();
        scroll = new JScrollPane(tablaestudiantes);
        scroll.setBounds(10, 100, 380, 400);  // Define las dimensiones y posición del JScrollPane
        menu.add(scroll);

        ingresarEstudianteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new anadirest().setVisible(true);
            }
        });

        consultaPorMatrículaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = consulta.getText();
                if (!codigo.isEmpty()) {
                    cargarestudiantescodigo(codigo);
                } else {
                    JOptionPane.showMessageDialog(null, "Ingrese un codigo correcto");
                }
            }
        });

        verLosRegistrosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarestudiantes();
            }
        });
    }

    private static DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        Vector<String> columnNames = new Vector<>();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        Vector<Vector<Object>> data = new Vector<>();
        while (resultSet.next()) {
            Vector<Object> vector = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(resultSet.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }

    private void cargarestudiantescodigo(String codigo) {
        String url = "jdbc:mysql://localhost:3306/curso";
        String username = "root";
        String password = "123456";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String query = "SELECT * FROM estudiantes WHERE matricula = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, codigo);
            ResultSet resultSet = preparedStatement.executeQuery();
            tablaestudiantes.setModel(buildTableModel(resultSet));

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarestudiantes() {
        String url = "jdbc:mysql://localhost:3306/curso";
        String username = "root";
        String password = "123456";
        try {
            Connection conexion = DriverManager.getConnection(url, username, password);
            String sql = "SELECT * FROM estudiantes";

            PreparedStatement pst = conexion.prepareStatement(sql);
            ResultSet resultset = pst.executeQuery();
            tablaestudiantes.setModel(buildTableModel(resultset));

            resultset.close();
            pst.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new menuadmin().setVisible(true);
            }
        });
    }
}
