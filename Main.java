import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

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
            read = new BufferedReader(new FileReader("first_five.csv"));
            //read = new BufferedReader(new FileReader("first_ten.csv"));
            //read = new BufferedReader(new FileReader("players_homeruns.csv"));
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
            boolean stop = false;
            Scanner input = new Scanner(System.in);
            while(stop == false){// not working yet runtime error
                System.out.print("Choose name to search: ");
                String put = input.next();
                tree.find(put);
                //System.out.println(put.toString());
                System.out.println("continue y/n?: ");
                String cont = input.next();
                if(cont == "y"){
                }
                else{
                    stop = true;
                }
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND");
            e.printStackTrace();
        } catch (IOException f) {
            System.out.println("I/O ERROR");
            f.printStackTrace();
        } catch(NullPointerException np){
            System.out.println("Reached leaf NILs");
        }
    }
}