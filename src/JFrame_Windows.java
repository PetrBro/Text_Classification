import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
public class JFrame_Windows extends JFrame {

    public static JTextField textfield1;
    public static JLabel label_1, label_2, label_3, label_4;
    public static JButton button_1;


    public JFrame_Windows() {
        super("Text Classification");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel container = new JPanel(new FlowLayout());
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));

        JPanel buttonsPanel_2 = new JPanel(new FlowLayout());


        label_1 = new JLabel("<html>Приложение для определения тематики текста</html>");
        label_2 = new JLabel("<html>Приложение способно классифицировать текст по следующим тематикам:</html>");
        label_3 = new JLabel("<html>медицинским, историческим, программирование, сети, криптография, финансы</html>");
        label_4 = new JLabel("Укажите ссылку на текст:");
        textfield1 = new JTextField("",20);
        button_1 = new JButton("Определить тематику текста");

        label_1.setHorizontalAlignment(JLabel.CENTER);
        label_1.setFont(new Font("Arial", Font.PLAIN, 20));
        label_2.setHorizontalAlignment(JLabel.CENTER);
        label_3.setHorizontalAlignment(JLabel.CENTER);
        button_1.setHorizontalAlignment(JLabel.CENTER);
        buttonsPanel.add(label_1);
        buttonsPanel.add(new JLabel(" "));
        buttonsPanel.add(label_2);
        buttonsPanel.add(label_3);
        buttonsPanel.add(new JLabel(" "));
        buttonsPanel.add(label_4);
        buttonsPanel.add(textfield1);
        buttonsPanel_2.add(button_1);

//        add(buttonsPanel_1, BorderLayout.LINE_START);
        container.add(buttonsPanel);
        container.add(buttonsPanel_2);
        add(container);
    }
    public static void main(String[] args) {
        JFrame_Windows app = new JFrame_Windows();
        app.setVisible(true);
        app.pack(); /* Эта команда подбирает оптимальный размер в зависимости от содержимого окна  */
    }
}