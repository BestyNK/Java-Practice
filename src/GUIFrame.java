import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class GUIFrame extends JFrame {
    private JPanel MainPanel;
    private JButton Add_button;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton Change_button;
    private JButton fire_button;
    private DefaultTableModel tableModel;

    public GUIFrame(){
        setTitle("Таблица");
        setSize(1280,720);
        setContentPane(MainPanel);
        setVisible(true);

        // Создание модели таблицы
        tableModel = new DefaultTableModel();
        table.setModel(tableModel);

        // Загрузка данных из базы данных
        loadDataFromDatabase();

        // Кнопка добавления
        Add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



                Main.GUIFrame.setVisible(false);
                Main.Add_Frame.setVisible(true);
            }
        });

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void loadDataFromDatabase() {
        try {
            // Данные для подключения к базе данных
            String url      = "jdbc:postgresql://localhost:5432/DB1";
            String user     = "postgres";
            String password = "23615797";

            // Подключение к базе данных
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM l_sost");

            // Получение информации о столбцах
            /*
            int columnCount = rs.getMetaData().getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columnNames[i] = rs.getMetaData().getColumnName(i + 1);
            }
            tableModel.setColumnIdentifiers(columnNames);
            */

            tableModel.addColumn("Подр-е");
            tableModel.addColumn("Таб_№");
            tableModel.addColumn("Таб_№(8 зн)");
            tableModel.addColumn("ФИО");
            tableModel.addColumn("Дата изм-я");
            tableModel.addColumn("Дата увол-я");
            tableModel.addColumn("Категория");
            tableModel.addColumn("Разряд");
            tableModel.addColumn("Должность");
            tableModel.addColumn("Сетка");
            tableModel.addColumn("Оплата");

            int columnCount = rs.getMetaData().getColumnCount();
            // Заполнение данных
            while (rs.next()) {
                Object[] data = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    data[i] = rs.getObject(i + 1);
                }
                tableModel.addRow(data);
            }

            // Закрытие соединений
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            System.out.println("Connection failed...");

            System.out.println(ex);
        }
    }
}
