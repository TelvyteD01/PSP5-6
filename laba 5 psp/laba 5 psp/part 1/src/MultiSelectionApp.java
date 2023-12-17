import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MultiSelectionApp extends JFrame {
    private JComboBox<String> itemComboBox;
    private JTextArea textField;
    private JButton addButton;
    private JButton clearButton;

    private ArrayList<String> selectedItems;

    public MultiSelectionApp() {
        setTitle("Множественный выбор элементов");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        itemComboBox = new JComboBox<>();
        itemComboBox.addItem("Элемент 1");
        itemComboBox.addItem("Элемент 2");
        itemComboBox.addItem("Элемент 3");

        textField = new JTextArea();
        addButton = new JButton("Добавить");
        clearButton = new JButton("Очистить");

        selectedItems = new ArrayList<>();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);

        add(itemComboBox, BorderLayout.NORTH);
        add(textField, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) itemComboBox.getSelectedItem();
                selectedItems.add(selectedItem);

                // Обновляем текстовое поле
                updateTextField();

                // Проверяем длину текста
                if (textField.getText().length() > 100) {
                    JOptionPane.showMessageDialog(MultiSelectionApp.this, "Превышено максимальное количество символов (100).", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    selectedItems.clear(); // Очищаем выбранные элементы
                    updateTextField();
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Очищаем выбранные элементы и текстовое поле
                selectedItems.clear();
                updateTextField();
            }
        });
    }

    private void updateTextField() {
        StringBuilder text = new StringBuilder();
        for (String selectedItem : selectedItems) {
            text.append(selectedItem).append(" ");
        }
        textField.setText(text.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MultiSelectionApp app = new MultiSelectionApp();
                app.setVisible(true);
            }
        });
    }
}
