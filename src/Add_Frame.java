import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Add_Frame extends JFrame {

    private JPanel Add_panel;
    private JPanel LabelsPane;
    private JPanel TextFieldsPane;
    private JPanel ButtonsPane;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JButton InsertButton;
    private JButton QuitButton;

    public Add_Frame(){
        setTitle("Прием на работу");
        setSize(1280,720);
        setContentPane(Add_panel);



        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
