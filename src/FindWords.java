import stemmer.porter.ru.StemmerPorterRU;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class FindWords {

    String URL_topic;
    String URL_text;
    ArrayList<String> words_in_topic_file;
    StringBuilder words_in_text_file;


    public FindWords(String URL_topic, String URL_text){
        this.URL_topic = URL_topic;
        this.URL_text = URL_text;
    }


    private void Find_Same_Words_Download_Files() throws FileNotFoundException {

        if (URL_topic.isEmpty()){
            throw new FileNotFoundException("Путь до файла с тематическими словами не указан!");
        }

        else if (URL_text.isEmpty()){
            throw new FileNotFoundException("Путь до файла с текстом не указан!");
        }
        else {

            ArrayList<String> words_in_topic_file = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(URL_topic))) {
                String topic_word;

                while ((topic_word = br.readLine()) != null) {
                    String[] topic_words = topic_word.split(" ");
                    if (topic_words.length >= 2){
                        for (int i = 0; i < topic_words.length; i++){
                            topic_words[i] = StemmerPorterRU.stem(topic_words[i].toLowerCase());
                        }
                        String topic_words_stemming = String.join(" ", topic_words);
                        words_in_topic_file.add(topic_words_stemming);
                    }
                    else {
                        topic_word = StemmerPorterRU.stem(topic_word);
                        words_in_topic_file.add(topic_word.toLowerCase());
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


            StringBuilder words_in_text_file = new StringBuilder();

            try (BufferedReader br = new BufferedReader(new FileReader(URL_text))) {
                String line;

                while ((line = br.readLine()) != null) {
                    String[] words = line.split(" ");
                    for (int i =0; i < words.length; i++) {
                        words[i] = words[i].replaceAll("[\\pP\\s]", "").toLowerCase();
                        if (words[i].length() > 3) {
                            words[i] = StemmerPorterRU.stem(words[i]);
                            words_in_text_file.append(words[i]);
                            words_in_text_file.append(" ");
                        }
                    }
                    words_in_text_file.append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            this.words_in_text_file = words_in_text_file;
            this.words_in_topic_file = words_in_topic_file;
        }
    }


    public ArrayList<String> Find_Same_words() throws FileNotFoundException {
        Find_Same_Words_Download_Files();

        System.out.println(words_in_topic_file);

        ArrayList<String> Array_of_topic_words = new ArrayList<>();

        for (int i=0; i < words_in_topic_file.size(); i++){
            String word = words_in_topic_file.get(i);

            if (words_in_text_file.indexOf(String.valueOf(word)) != -1) {
                Array_of_topic_words.add(word);
            }
        }
        System.out.println(words_in_text_file);

        return Array_of_topic_words;
    }

    public static void main(String[] args) throws FileNotFoundException {
        FindWords words_medical_topic = new FindWords("src/Files_with_words_for_topics/medical_topics.txt", "src/Files_with_words_for_topics/example.txt");
        ArrayList<String> array_medical_topic = words_medical_topic.Find_Same_words();
        System.out.println(array_medical_topic.size());
//        FindWords words_historical_topic = new FindWords("src/Files_with_words_for_topics/historical_topics.txt", "src/Files_with_words_for_topics/example.txt");
//        ArrayList<String> array_historical_topic = words_historical_topic.Find_Same_words();
//        System.out.println(array_historical_topic);
//        System.out.println(array_medical_topic);
    }
}
