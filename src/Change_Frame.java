import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Objects;

public class Change_Frame extends JFrame {
    private JPanel Main_Pane;
    private JFormattedTextField tab_n;
    private JFormattedTextField dt_yvl;
    private JFormattedTextField podr;
    private JFormattedTextField fio;
    private JFormattedTextField katg;
    private JFormattedTextField razr;
    private JFormattedTextField dolg;
    private JFormattedTextField setka;
    private JFormattedTextField oplat;
    private JButton Save_Button;
    private JButton Close_Button;
    private JButton Del_Button;

    public Change_Frame(){
        setTitle("Форма изменения");
        setSize(800, 500);
        setContentPane(Main_Pane);
        setVisible(true);

        tab_n.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkInput();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkInput();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkInput();
            }
        });

        // Кнопка сохранения
        Save_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            DataUpdate();

            tab_n.setText("");
            dt_yvl.setText("");
            podr.setText("");
            fio.setText("");
            katg.setText("");
            razr.setText("");
            dolg.setText("");
            setka.setText("");
            oplat.setText("");

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
                podr.setText("");
                fio.setText("");
                katg.setText("");
                razr.setText("");
                dolg.setText("");
                setka.setText("");
                oplat.setText("");
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

    private void checkInput(){
        if(tab_n.getText().length() == 6){
            getData();
        }
    }

    private void getData(){
        // Данные для подключения к базе данных
        String url      = "jdbc:postgresql://localhost:5432/DB1";
        String user     = "postgres";
        String password = "23615797";

        String SELECT_DATA_SQL = "SELECT * FROM l_sost WHERE tab_n = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_DATA_SQL)) {

            preparedStatement.setString(1, tab_n.getText());
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                podr.setText(rs.getString("podr"));
                dt_yvl.setText(rs.getString("dt_yvl"));
                fio.setText(rs.getString("fio"));
                katg.setText(rs.getString("katg"));
                razr.setText(rs.getString("razr"));
                dolg.setText(rs.getString("dolg"));
                setka.setText(rs.getString("setka"));
                oplat.setText(rs.getString("oplat"));
            }


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

    private void DataUpdate() {
        // Данные для подключения к базе данных
        String url      = "jdbc:postgresql://localhost:5432/DB1";
        String user     = "postgres";
        String password = "23615797";

        // Данные для отправки
        String INSERT_DATA_SQL = "UPDATE l_sost "
                                +"SET dt_yvl = ?, podr = ?, fio = ?, katg = ?, razr = ?, dolg = ?, setka = ?, oplat = ?  "
                                +"WHERE tab_n = ?";

        // Подключение к базе данных
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_DATA_SQL)) {
            if (!Objects.equals(dt_yvl.getText(), "")) {
                preparedStatement.setDate(1, Date.valueOf(dt_yvl.getText()));
            } else {
                preparedStatement.setNull(1, Types.DATE);
            }
            preparedStatement.setString(2, podr.getText());
            preparedStatement.setString(3, fio.getText());
            preparedStatement.setInt(4, Integer.parseInt(katg.getText()));
            preparedStatement.setInt(5, Integer.parseInt(razr.getText()));
            preparedStatement.setInt(6, Integer.parseInt(dolg.getText()));
            preparedStatement.setInt(7, Integer.parseInt(setka.getText()));
            preparedStatement.setInt(8, Integer.parseInt(oplat.getText()));
            preparedStatement.setString(9, tab_n.getText());

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
