import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;


public class GoodMatch {

    public String charOccurrenceInString(String sentence) {
        ArrayList<Integer> Occurrences = new ArrayList<Integer>();
        String newSentence = (sentence.replaceAll(" ", "")).toLowerCase();

        // Creating a LinkedHashMap containing char
        // as a key and occurrences as a value
        Map<Character, Integer> charCountMap = new LinkedHashMap<>(); // Here we use a LinkedHashMap to maintain the insertion order as HashMap doesn't maintain the
                                                                      // order. Converting given string to char array

        char[] strArray = newSentence.toCharArray();

        // checking each char of strArray
        for (char c : strArray) {
            if (charCountMap.containsKey(c)) {

                // If char is present in charCountMap,
                // incrementing it's count by 1
                charCountMap.put(c, charCountMap.get(c) + 1);
            } else {

                // If char is not present in charCountMap,
                // putting this char to charCountMap with 1 as it's value
                charCountMap.put(c, 1);
            }
        }

        // Adding the charCountMap value onto the Occurrences arraylist.
        for (Map.Entry entry : charCountMap.entrySet()) {
            Occurrences.add((int) (entry.getValue()));
        }
        return Occurrences.stream().map(Object::toString).collect(Collectors.joining(""));
    }

    public static String reduceString(String long_num) {
        ArrayList<Long> arrL = new ArrayList<Long>();
        long vl = 0;
        while (long_num.length() > 1) {
            long n = Long.parseLong(long_num);
            long lastn = n % 10;
            // Remove last digit from number
            // till only one digit is left
            long firstn = n;
            while (firstn >= 10) {
                firstn /= 10;
            }

            arrL.add(firstn + lastn);
            if (n != lastn) {
                String newL = long_num.substring(1, long_num.length() - 1);
                long_num = newL;
            }
            vl = lastn;
        }

        if (long_num.length() == 1) {
            arrL.add(Long.parseLong(long_num));
        }
        long_num = arrL.stream().map(Object::toString).collect(Collectors.joining(""));

        return long_num;
    }

    public static void printResult(String result, String sentence) {

        for (int i = 0; i < result.length() - 1; i++) {
            result = GoodMatch.reduceString(result);
        }
        int benchmark = 80;
        int percentage = Integer.parseInt(GoodMatch.reduceString(result));
        if (percentage > benchmark && percentage <= 100) {
            System.out.println(sentence + " " + percentage + "%, good match.");
        } else if (percentage > benchmark) {
            int first_digit = percentage;
            while (first_digit >= 10) {
                first_digit /= 10;
            }
            System.out.printf("%s %d%s", sentence, ((percentage % 10) + first_digit),
                    Character.toString((String.valueOf(percentage)).charAt(1)));
            System.out.print("%.\n");
        } else {
            System.out.println(sentence + " " + percentage + "%.");
        }

    }

    public static ArrayList<String> namesFromCSV(String filename) {
        ArrayList<String> Males = new ArrayList<>();
        ArrayList<String> Females = new ArrayList<>();
        ArrayList<String> newMales = new ArrayList<>();
        ArrayList<String> newFemales = new ArrayList<>();
        ArrayList<String> Sentences = new ArrayList<>();
        String fileName = filename;
        
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            String line = null;    
            while ((line = br.readLine()) != null){
            //Process the line
                // Add male names to the Males arraylist.
                if(line.contains(", m")){
                    Males.add(line.replace(", m", ""));
                }
                // Add female names to the Females arraylist.
                if(line.contains(", f")){
                    Females.add(line.replace(", f", ""));
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // Remove any duplicates from the Males arraylist.
        for(String mname : Males){
            if(!newMales.contains(mname)){
                newMales.add(mname);
            }
        }
        // Remove any duplicates from the Females arraylist.
        for(String fname : Females){
            if(!newFemales.contains(fname)){
                newFemales.add(fname);
            }
        }
                
        Pattern p = Pattern.compile("^[a-zA-Z]*$"); // Compile the regex as we'll use it multiple times.
        // Match every male to each and every female.
        for(int i = 0; i < newMales.size(); i++){
            for(int j = 0; j < newFemales.size(); j++){
                // 
                if(p.matcher(newMales.get(i)).find() == true && p.matcher(newFemales.get(j)).find() == true){
                    String sentence = newMales.get(i) + " matches " + newFemales.get(j);
                    Sentences.add(sentence);
                }
                else{
                    System.out.println("Only enter alphabetic characters, please try again.\n");        
                }
                
            }
        }
        
        return Sentences;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String csvFile = scan.next();
        GoodMatch obj = new GoodMatch();
        ArrayList<String> newArrL = GoodMatch.namesFromCSV(csvFile);

        for(int i = 0; i < newArrL.size(); i++){
            String nstring = GoodMatch.reduceString(obj.charOccurrenceInString(newArrL.get(i)));
            // Print the result
            try {
                System.out.println();
                GoodMatch.printResult(nstring, newArrL.get(i));
            } catch (OutOfMemoryError e) {
                System.out.println(e);
            }
        }
        

    }
}
