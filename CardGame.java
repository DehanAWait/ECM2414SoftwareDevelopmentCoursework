import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;

public class CardGame {
    public static void main(String[] args){

        //To get input
        Scanner scanner = new Scanner(System.in);
        int n=0;

        //Check input is valid and get number of players
        while (true) {
            System.out.println("Please Enter the number of Players:");
            if (scanner.hasNextInt()){
                n = scanner.nextInt();
                if (n>0) break;
            } else {
                scanner.next();
            }
            System.out.println("Invalid input. Please enter a positive integer.");
        }

        //Create random file and name it
        String generatedFilename = n + ".txt";
        generateRandomPackFile(n,generatedFilename);
        System.out.println("Automatically generated pack file: " + generatedFilename);





        //Creates a random pack
        private static void generateRandomPackFile(int n, String filename) {
            int totalRows = 8 * n;
            Random random = new Random();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                for (int i = 0; i < totalRows; i++) {
                    int randomValue = random.nextInt(1,13);
                    writer.write(String.valueOf(randomValue));
                    writer.newLine();
                } 
            } catch (IOException e) {
                    System.out.println("An error occured.");
                    e.printStackTrace();
                }
        }

    
        



    }
}
