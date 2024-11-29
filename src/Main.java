import edu.stanford.nlp.util.ArrayUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

public class Main extends JFrame_Windows {

    protected static String MAIN_LINK = "src/Files_with_words_for_topics/";
    public static Map<String, Map<String,Integer>> dictionary_for_statistic = new HashMap<>();
    public static FindWords Find_Words_In_Topic;
    private static final Map<String,String> dictionary = new HashMap<>();
    private static final String[] array_of_topics = new String[] {"medical_topics", "historical_topics", " network_topics", "cryptography_topics", "finance_topics", "programming_topics"};
    private static final String[] array_of_topics_russian = new String[] {"Медицина", "История", "Сети", "Криптография", "Финансы", "Программирование"};


    private static void FillDictionary(){
        for (String topic: array_of_topics) {
            dictionary.put(topic, MAIN_LINK + topic + ".txt");
        }
    }

    public static void Find_Words(String LINK_TO_TEXT_FILE) throws FileNotFoundException {
        FillDictionary();

        for (int i=0; i < array_of_topics.length; i++) {
            Find_Words_In_Topic = new FindWords(dictionary.get(array_of_topics[i]), LINK_TO_TEXT_FILE);
            Map<String,Integer> array_for_find_topic_words = Find_Words_In_Topic.Find_Same_words();
            tableModel.addRow(new String[]{array_of_topics_russian[i], Find_Words_In_Topic.Return_Statistic()});
            dictionary_for_statistic.put(array_of_topics[i], array_for_find_topic_words);
        }
    }

    @Override
    public void initListeners() {
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Link_to_file = textfield1.getText();
                Link_to_save_files = textfield2.getText();
                System.out.println(Link_to_file);
                try {
                    new BufferedReader(new FileReader(Link_to_file));
                    Find_Words(Link_to_file);

                    button_3.setVisible(true);
                    button_2.setVisible(true);
                    comboBox.setVisible(true);

                    try(FileWriter writer = new FileWriter(Link_to_save_files + '/' + "Statistic.txt", false)){

                        for (String elem : array_of_topics){
                            Map<String,Integer> Dict_for_topic_words = dictionary_for_statistic.get(elem);
                            writer.append(elem);
                            writer.append('\n');
                            writer.append('\n');
                            for (String key : Dict_for_topic_words.keySet()){
                                writer.append(key);
                                writer.append(" ");
                                String count_words = Integer.toString(Dict_for_topic_words.get(key));
                                writer.append(count_words);
                                writer.append('\n');
                                writer.append('\n');
                            }
                        }
                    }
                    catch(IOException ex){
                        System.out.println(ex.getMessage());
                    }
                }
                catch (FileNotFoundException g){
                    JOptionPane.showMessageDialog(null, "Файл не найден!","Message", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        button_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String Choose_topic = (String) comboBox.getSelectedItem();
                    int index_for_topic = ArrayUtils.indexOf(array_of_topics, Choose_topic);

                    if(dictionary_for_statistic.get(Choose_topic) != null) {
                        JFrame frame = new JFrame("Гистограмма для темы: " + array_of_topics_russian[index_for_topic]);
                        HistogramVisualization histogram = new HistogramVisualization(dictionary_for_statistic.get(Choose_topic), frame.getHeight());
                        frame.setLayout(new BorderLayout());

                        // Создаем кнопку для сохранения графика
                        JButton saveButton = new JButton("Сохранить график в JPG");
                        saveButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                histogram.saveToJPG(Link_to_save_files + "/" + "histogram.jpg");
                            }
                        });

                        // Добавляем компонент на фрейм
                        frame.add(histogram, BorderLayout.CENTER);
                        frame.add(saveButton, BorderLayout.SOUTH); // Кнопка снизу

                        frame.setSize(500, 400); // Увеличиваем размер окна
                        frame.setVisible(true);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "В тексте не найдены слова, относящиеся к данной тематике!","Message", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch (Exception g){
                    g.printStackTrace();
                }
            }
        });
        button_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame_2 = new JFrame("Таблица результатов");
                frame_2.setSize(250, 135);
                frame_2.add(table);
                frame_2.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.setVisible(true);
    }
}