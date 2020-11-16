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
            int value;

            read = new BufferedReader(new FileReader("players_homeruns.csv"));
            while ((raw = read.readLine()) != null) {
                String[] parts = raw.split(",");
                //for (String i: parts) { checking values in parts are there
                //    System.out.println(i);
                //}
            }
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