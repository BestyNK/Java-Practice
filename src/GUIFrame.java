import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
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
    private JButton refresh_button;
    private DefaultTableModel tableModel;

    public GUIFrame(){
        setTitle("Таблица");
        setSize(1280,720);
        setContentPane(MainPanel);
        setVisible(true);

        // Создание модели таблицы
        tableModel = new DefaultTableModel();
        table.setModel(tableModel);
        table.setDefaultEditor(Object.class, null);

        //Выравнивание текста в таблице по центру
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)table.getDefaultRenderer(String.class);
        renderer.setHorizontalAlignment(SwingConstants.CENTER);


        // Загрузка данных из базы данных
        loadDataFromDatabase();

        // Кнопка добавления
        Add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.Add_Frame = new Add_Frame();
            }
        });

        // Кнопка изменения
        Change_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.Change_Frame = new Change_Frame();
            }
        });

        // Кнопка обновления
        refresh_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            RefreshDataTable();
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

    private void RefreshDataTable() {
        try {
            // Данные для подключения к базе данных
            String url      = "jdbc:postgresql://localhost:5432/DB1";
            String user     = "postgres";
            String password = "23615797";

            // Подключение к базе данных
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM l_sost");

            tableModel.setRowCount(0);

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
