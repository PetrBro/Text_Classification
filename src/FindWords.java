import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import stemmer.porter.ru.StemmerPorterRU;


public class FindWords {

    String URL_topic;
    String URL_text;


    public FindWords(String URL_topic, String URL_text){
        this.URL_topic = URL_topic;
        this.URL_text = URL_text;
    }

    public FindWords(){
        this.URL_topic = "";
        this.URL_text = "";
    }

    public void Find_Same_Words(){
        StringBuilder words_in_topic_file = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(URL_topic))) {
            String line;

            while((line = br.readLine()) != null) {
                words_in_topic_file.append(line);
                words_in_topic_file.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder words_in_text_file = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(URL_topic))) {
            String line;

            while((line = br.readLine()) != null) {
                words_in_text_file.append(line);
                words_in_text_file.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        String targetChar = "пер";
        int index = words_in_topic_file.indexOf(String.valueOf(targetChar));
        System.out.println(index);
    }

    public static void main(String args[]){
        FindWords words = new FindWords("medical_topics.txt", "example.txt");
        words.Find_Same_Words();

        String word = "чрезвычайно";

        System.out.println(StemmerPorterRU.stem(word));
    }
}
