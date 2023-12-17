import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class UserRegistrationApp extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton loadButton;
    private JButton saveButton;
    private JTextArea userDataArea;

    private ArrayList<User> users;

    public UserRegistrationApp() {
        setTitle("Регистрация пользователя");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        registerButton = new JButton("Зарегистрировать");
        loadButton = new JButton("Загрузить из файла");
        saveButton = new JButton("Сохранить в файл");
        userDataArea = new JTextArea(10, 40);

        users = new ArrayList<>();

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Имя пользователя:"));
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("Пароль:"));
        inputPanel.add(passwordField);
        inputPanel.add(registerButton);
        inputPanel.add(new JPanel());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(userDataArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                users.add(new User(username, password));
                usernameField.setText("");
                passwordField.setText("");
                updateUserDataArea();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("users.dat"));
                    users = (ArrayList<User>) inputStream.readObject();
                    inputStream.close();
                    updateUserDataArea();
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("users.dat"));
                    outputStream.writeObject(users);
                    outputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void updateUserDataArea() {
        userDataArea.setText("");
        for (User user : users) {
            userDataArea.append("Имя пользователя: " + user.getUsername() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UserRegistrationApp app = new UserRegistrationApp();
                app.setVisible(true);
            }
        });
    }
}

class User implements Serializable {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
}