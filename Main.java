import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    static BufferedReader read;
    static String[] parts;

    public static void main(String[] args) {
        try {
            RedBlackTreeMap<String, Integer> tree = new RedBlackTreeMap<>();
            String raw;
            String nameKey;
            int value;
            int i = 0;
            read = new BufferedReader(new FileReader("players_homeruns.csv"));
            while ((raw = read.readLine()) != null) {
                String[] parts = raw.split(",");
                // for (String i: parts) { //checking values in parts are there
                // System.out.println(i);
                // }
                nameKey = parts[i];
                value = Integer.parseInt(parts[i + 1]);
                tree.add(nameKey, value);
                //System.out.println(nameKey + ": " + value); // testing conversion of values
            }
            tree.printStructure();
            read.close();
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND");
            e.printStackTrace();
        } catch (IOException f) {
            System.out.println("I/O ERROR");
            f.printStackTrace();
        }
    }
}