import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.EventListener;

public class Add_Frame extends JFrame {

    private JPanel Add_panel;
    private JPanel LabelsPane;
    private JPanel TextFieldsPane;
    private JPanel ButtonsPane;
    private JButton InsertButton;
    private JButton QuitButton;
    private JFormattedTextField podr;
    private JFormattedTextField tab_n;
    private JFormattedTextField tab_n8;
    private JFormattedTextField fio;
    private JFormattedTextField dt_izm;
    private JFormattedTextField katg;
    private JFormattedTextField razr;
    private JFormattedTextField dolg;
    private JFormattedTextField setka;
    private JFormattedTextField oplat;

    public Add_Frame(){
        setTitle("Прием на работу");
        setSize(1280,720);
        setContentPane(Add_panel);
        setVisible(true);

        // Кнопка "Сохранить"
        InsertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DataInsertion();

                podr.setText("");
                tab_n.setText("");
                tab_n8.setText("");
                fio.setText("");
                dt_izm.setText("");
                katg.setText("");
                razr.setText("");
                dolg.setText("");
                setka.setText("");
                oplat.setText("");
            }
        });

        // Кнопка "Вернуться"
        QuitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
                Main.GUIFrame.setVisible(true);
            }
        });


        setLocationRelativeTo(null);
    }

    // Функция соединения и отправки в базу данных
    private void DataInsertion() {
        // Данные для подключения к базе данных
        String url      = "jdbc:postgresql://localhost:5432/DB1";
        String user     = "postgres";
        String password = "23615797";

        // Данные для отправки
        String INSERT_DATA_SQL = "INSERT INTO l_sost(podr, tab_n, tab_n8, fio, dt_izm, katg, razr, dolg, setka, oplat)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Подключение к базе данных
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_DATA_SQL)) {

            preparedStatement.setString(1, podr.getText());
            preparedStatement.setString(2, tab_n.getText());
            preparedStatement.setString(3, tab_n8.getText());
            preparedStatement.setString(4, fio.getText());
            preparedStatement.setDate(5, Date.valueOf(dt_izm.getText()));
            preparedStatement.setInt(6, Integer.parseInt(katg.getText()));
            preparedStatement.setInt(7, Integer.parseInt(razr.getText()));
            preparedStatement.setInt(8, Integer.parseInt(dolg.getText()));
            preparedStatement.setInt(9, Integer.parseInt(setka.getText()));
            preparedStatement.setInt(10, Integer.parseInt(oplat.getText()));

            preparedStatement.executeUpdate();


            // Диалоговое окно
            JDialog d = new JDialog(Main.Add_Frame,"Сообщение");
            JLabel l = new JLabel("Успешно!");
            l.setHorizontalAlignment(SwingConstants.CENTER);
            d.add(l);

            d.setSize(100, 100);
            d.setLocationRelativeTo(null);
            d.setVisible(true);

        } catch (SQLException ex) {
            System.out.println("Connection failed...");

            System.out.println(ex);
        }

    }
}
