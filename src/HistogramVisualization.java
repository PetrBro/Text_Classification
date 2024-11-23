import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HistogramVisualization extends JPanel {

    private Map<String, Integer> data;

    public HistogramVisualization(Map<String, Integer> data) {
        this.data = data;
        setPreferredSize(new Dimension(500, 300)); // Установка предпочтительного размера для компонента
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int maxHeight = getMaxHeight(); // Получаем максимальное значение из словаря
        int nonZeroCount = countNonZeroValues();
        int barWidth = (getWidth() - 40) / nonZeroCount; // Расчет ширины столбца с учетом только ненулевых значений

        // Рисуем сетку с подписями
        drawGrid(g, maxHeight);

        int index = 0;
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();

            // Пропускаем значения, равные 0
            if (value == 0) {
                continue;
            }

            // Нормируем высоту столбца по максимальному значению
            int height = (int) ((double) value / maxHeight * (getHeight() - 25)); // Максимальная высота - 40 для оси X и 30 для сетки
            int yPosition = getHeight() - height - 40; // Позиция Y для рисования столбца

            // Устанавливаем цвет и рисуем столбец
            g.setColor(Color.BLUE);
            g.fillRect(index * barWidth + 20, yPosition, barWidth - 5, height);

            // Выводим имя ключа под столбцом
            g.setColor(Color.BLACK);
            g.drawString(key, index * barWidth + 20 + (barWidth / 4), getHeight() - 20); // Положение текста под столбцом
            index++;
        }
    }

    private void drawGrid(Graphics g, int maxHeight) {
        g.setColor(Color.LIGHT_GRAY);
        int step = (getHeight() - 40) / 10; // Шаг для рисования сетки, учитываем место для оси X

        // Рисуем горизонтальные линии и их значения
        for (int i = 0; i <= 10; i++) {
            int y = getHeight() - 40 - (int) ((i / 10.0) * (getHeight() - 40)); // Обратный порядок для чисел
            if (y < 0) continue; // Не рисуем линии выше области видимости

            g.drawLine(20, y, getWidth() - 20, y); // Рисуем линию

            // Выводим числовые значения сбоку от графика
            int value = (int) ((maxHeight / 10.0) * i);
            g.drawString(String.valueOf(value), 5, y + 5); // Положение текста
        }

        // Рисуем ось X
        g.drawLine(20, getHeight() - 40, getWidth() - 20, getHeight() - 40); // Ось X
    }

    private int getMaxHeight() {
        return data.values().stream().max(Integer::compare).orElse(1); // Возвращаем максимальное значение
    }

    private int countNonZeroValues() {
        return (int) data.values().stream().filter(value -> value > 0).count(); // Считаем значения больше 0
    }

    // Метод для сохранения графика в виде JPG
    public void saveToJPG(String filePath) {
        int width = getWidth();

        int height = getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // Рисуем график в BufferedImage
        paintComponent(g2d);

        // Освобождаем ресурсы
        g2d.dispose();

        try {
            ImageIO.write(image, "jpg", new File(filePath)); // Сохраняем изображение
            System.out.println("График сохранен в: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        HashMap<String, Integer> data = new HashMap<>();
//        // Вы можете добавлять, обновлять или изменять значения в словаре
//        data.put("A", 5);
//        data.put("B", 0);
//        data.put("C", 15);
//        data.put("D", 0);
//        data.put("E", 12);
//        data.put("F", 3);
//        data.put("G", 7);
//        data.put("H", 10);
//
//        JFrame frame = new JFrame("Гистограмма с сеткой и подписями");
//        HistogramVisualization histogram = new HistogramVisualization(data);
//        frame.setLayout(new BorderLayout());
//        frame.add(histogram, BorderLayout.CENTER);
//
//        // Создаем кнопку для сохранения графика
//        JButton saveButton = new JButton("Сохранить график в JPG");
//        saveButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                histogram.saveToJPG("histogram.jpg");
//            }
//        });
//
//        // Добавляем кнопку снизу
//        frame.add(saveButton, BorderLayout.SOUTH);
//
//        frame.setSize(500, 400);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
    }
}
