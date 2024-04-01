import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Change_Frame extends JFrame {
    private JPanel Main_Pane;
    private JFormattedTextField tab_n;
    private JFormattedTextField dt_yvl;
    private JButton Save_Button;
    private JButton Close_Button;
    private JButton Del_Button;

    public Change_Frame(){
        setTitle("Форма изменения");
        setSize(800, 350);
        setContentPane(Main_Pane);
        setVisible(true);

        // Кнопка сохранения
        Save_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            DataUpdate();

            tab_n.setText("");
            dt_yvl.setText("");

            dispose();
            }
        });

        // Кнопка Удаления
        Del_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataDelete();

                tab_n.setText("");
                dt_yvl.setText("");
            }
        });

        // Кнопка закрытия
        Close_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setLocationRelativeTo(null);
    }

    private void DataUpdate() {
        // Данные для подключения к базе данных
        String url      = "jdbc:postgresql://localhost:5432/DB1";
        String user     = "postgres";
        String password = "23615797";

        // Данные для отправки
        String INSERT_DATA_SQL = "UPDATE l_sost SET dt_yvl = ? WHERE tab_n = ?";

        // Подключение к базе данных
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_DATA_SQL)) {

            preparedStatement.setDate(1, Date.valueOf(dt_yvl.getText()));
            preparedStatement.setString(2, tab_n.getText());

            System.out.println(preparedStatement);

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

    private void DataDelete() {
        // Данные для подключения к базе данных
        String url      = "jdbc:postgresql://localhost:5432/DB1";
        String user     = "postgres";
        String password = "23615797";

        // Данные для отправки
        String INSERT_DATA_SQL = "DELETE FROM l_sost WHERE tab_n = ?";

        // Подключение к базе данных
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_DATA_SQL)) {

            preparedStatement.setString(1, tab_n.getText());
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
