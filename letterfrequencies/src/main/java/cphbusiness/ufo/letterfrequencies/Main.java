package cphbusiness.ufo.letterfrequencies;

import java.io.*;
import java.sql.Array;
import java.util.*;

import static java.util.stream.Collectors.toMap;

/**
 * Frequency analysis Inspired by
 * https://en.wikipedia.org/wiki/Frequency_analysis
 *
 * @author kasper
 */
public class Main {


    public static void main(String[] args) throws FileNotFoundException, IOException {

        ArrayList<Integer> time = new ArrayList<Integer>();
        for (int i = 0; i < 200 ; i++) {
            long startTime = System.currentTimeMillis();

            String fileName = "C:\\Users\\rasmu\\Desktop\\soft\\2.semester\\Ufo\\letterfrequencies\\src\\main\\resources\\FoundationSeries.txt";
            Reader reader = new FileReader(fileName);
            Map<Integer, Long> freq = new HashMap<>();

            tallyCharsOptimizedWithBufferedReader(reader, freq);
            print_tally(freq);

            long endTime = System.currentTimeMillis();
            int tim = (int) (endTime - startTime);
            time.add(tim);
        }
        System.out.println(time);
        int sum = 0;
        for (int i = 0; i < time.size() ; i++) {
            sum = sum + time.get(i);
        }
        System.out.println(sum/time.size());
    }

    private static void tallyCharsOptimizedWithBufferedReader(Reader reader, Map<Integer, Long> freq){
        int[] charCodeArray = new int[128];

        try (BufferedReader buffer = new BufferedReader(reader)) {
            int b;
            while ((b = buffer.read()) != -1) {
                charCodeArray[b]++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < charCodeArray.length; i++) {
            freq.put(i, (long) charCodeArray[i]);
        }
    }

   // private static void tallyChars(Reader reader, Map<Integer, Long> freq) throws IOException {
   //     int b;
   //     while ((b = reader.read()) != -1) {
   //         Arrays.sort(buffer, 0, b);
   //         try {
   //             freq.put(b, freq.get(b) + 1);
   //         } catch (NullPointerException np) {
   //             freq.put(b, 1L);
   //         };
   //     }
   // }

    private static void print_tally(Map<Integer, Long> freq) {
        int dist = 'a' - 'A';
        Map<Character, Long> upperAndlower = new LinkedHashMap();
        for (Character c = 'A'; c <= 'Z'; c++) {
            upperAndlower.put(c, freq.getOrDefault(c, 0L) + freq.getOrDefault(c + dist, 0L));
        }
        Map<Character, Long> sorted = upperAndlower
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        for (Character c : upperAndlower.keySet()) {
            System.out.println("" + c + ": " + upperAndlower.get(c));;
        }
    }
}
