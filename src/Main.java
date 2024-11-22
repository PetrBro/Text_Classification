import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Main {

    protected static String MAIN_LINK = "src/Files_with_words_for_topics/";
    public static String LINK_TO_TEXT_FILE = "src/Files_with_words_for_topics/example.txt";
    public static Map<String, Map<String,Integer>> dictionary_for_statistic = new HashMap<String,Map<String,Integer>>();
    public static FindWords Find_Words_In_Topic;
    public static ArrayList<Float> Array_for_statistic;
    private static final Map<String,String> dictionary = new HashMap<String,String>();
    private static final String[] array_of_topics = new String[] {"medical_topics", "historical_topics", "network_topics", "cryptography_topics", "finance_topics", "programming_topics"};

    private static void FillDictionary(){
        for (String topic: array_of_topics) {
            dictionary.put(topic, MAIN_LINK + topic + ".txt");
        }
    }

    public static void Find_Words(String LINK_TO_TEXT_FILE) throws FileNotFoundException {
        FillDictionary();
        for (String topic : array_of_topics) {
            Find_Words_In_Topic = new FindWords(dictionary.get(topic), LINK_TO_TEXT_FILE);
            Map<String,Integer> array_for_find_topic_words = Find_Words_In_Topic.Find_Same_words();
            Array_for_statistic.add(Find_Words_In_Topic.Return_Statistic());

            dictionary_for_statistic.put(topic, array_for_find_topic_words);
        }
    }


    public static void main(String[] args) throws FileNotFoundException {
    }
}