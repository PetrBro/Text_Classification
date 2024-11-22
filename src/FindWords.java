import stemmer.porter.ru.StemmerPorterRU;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FindWords {

    String URL_topic;
    String URL_text;
    Map<String,Integer> words_in_topic_file = new HashMap<String,Integer>();;
    StringBuilder text_file = new StringBuilder();
    Integer Count_topic_words;


    public FindWords(String URL_topic, String URL_text){
        this.URL_topic = URL_topic;
        this.URL_text = URL_text;
    }

    private String Stemming_word_or_words(String topic_word){
        String[] topic_words = topic_word.split(" ");
        if (topic_words.length >= 2){
            for (int i = 0; i < topic_words.length; i++){
                topic_words[i] = StemmerPorterRU.stem(topic_words[i].toLowerCase());
            }
            String topic_words_stemming = String.join(" ", topic_words);
            return topic_words_stemming;
        }
        else {
            topic_word = StemmerPorterRU.stem(topic_word);
            return topic_word;
        }
    }

    private void Find_Same_Words_Download_Files() throws FileNotFoundException {

        if (URL_topic.isEmpty()){
            throw new FileNotFoundException("Путь до файла с тематическими словами не указан!");
        }

        else if (URL_text.isEmpty()){
            throw new FileNotFoundException("Путь до файла с текстом не указан!");
        }
        else {
            try (BufferedReader br = new BufferedReader(new FileReader(URL_topic))) {
                String topic_word;

                while ((topic_word = br.readLine()) != null) {
                    this.words_in_topic_file.put(topic_word.toLowerCase(), 0);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            try (BufferedReader br = new BufferedReader(new FileReader(URL_text))) {
                String line;

                while ((line = br.readLine()) != null) {
                    String[] words = line.split(" ");
                    for (int i =0; i < words.length; i++) {
                        words[i] = words[i].replaceAll("[\\pP\\s]", "").toLowerCase();
                        if (words[i].length() > 3) {
                            words[i] = StemmerPorterRU.stem(words[i]);
                            this.text_file.append(words[i]);
                            this.text_file.append(" ");
                        }
                    }
                    this.text_file.append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int FindByString(String seq, StringBuilder text) {
        List<Integer> indices = new ArrayList<Integer>();
        int strIdx = 0;

        while ( strIdx < text.length() ) {
            int idx = text.indexOf( seq, strIdx );
            if ( idx == -1 )
                break;
            indices.add( idx );
            strIdx = idx + seq.length();
        }

        return indices.size();
    }

    public Map<String,Integer> Find_Same_words() throws FileNotFoundException {
        Find_Same_Words_Download_Files();

        for (String word : this.words_in_topic_file.keySet()) {
            String stemming_word = Stemming_word_or_words(word);
            int count_words = FindByString(stemming_word, this.text_file);

            int count_words_in_text = this.words_in_topic_file.get(word);
            count_words_in_text += count_words;
            this.Count_topic_words += count_words;
            this.words_in_topic_file.put(word, count_words_in_text);
        }
        return this.words_in_topic_file;
    }

    public float Return_Statistic(){
        return Count_topic_words/(float)text_file.length();
    }

//    public static void main(String[] args) throws FileNotFoundException {
//
//    }
}
