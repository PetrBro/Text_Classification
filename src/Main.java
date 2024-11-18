import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
        StringBuilder result = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader("medical_topics.txt"))) {
            String line;

            while((line = br.readLine()) != null) {
                result.append(line);
                result.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(result.toString());;
    }
}