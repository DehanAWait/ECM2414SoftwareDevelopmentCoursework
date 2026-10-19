import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CardGame {
    public static void main(String[] args){

        //To get input
        Scanner scanner = new Scanner(System.in);
        int n = 0;

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

       //Prompt for pack location
       List<Cards> packCards = null;
       while (packCards == null) {
        System.out.println("Please enter location of pack to load:");
        String packFilename = scanner.nextLine().trim();
        if (packFilename.isEmpty()) {
            packFilename = generatedFilename;
        }
       }

       packCards = validatePack(packFilename, n);
        if (packCards == null) {
            System.out.println("Invalid pack file. Please try again.");
        }


        System.out.println("Pack loaded successfully with " + packCards.size() + " cards.");

        ArrayList<ArrayList<Card>> playerHands = new ArrayList<>();
        for (int i=0; i <n; i++) {
            playerHands.add(new ArrayList<>());
        }

        ArrayList<ArrayList<Card>> deckContents = new ArrayList<>();
        for (int i =0; i < n; i++) {
            deckContents.add(new ArrayList<>());
        }

        int cardIndex = 0;
        //Distribute 4 cards to each player in round-robin way
        for (int round = 0; round < 4; round++) {
            for (int p=0; p<n; p++){
                playerHands.get(p).add(packCards.get(cardIndex++));
            }
        }


        //Distribute remaining card to decks
        int deckIndex = 0;
        while (cardIndex < packCards.size()) {
            int targetDeck = deckIndex % n;
            deckContents.get(targetDeck).add(packCards.get(cardIndex++));
            deckIndex++;
        }

        //Initialise CardDeck and Player objects in ring topology



        //Immediate win check at the start of the game
        boolean immediateWin = false;
        for (int i=0; i <n;i++) {
            if (players.get(i).hasWon()) {
            System.out.println("player " + (i +1) + " wins");
            immediateWin = true;
            break;
            }
        }

        if (immediateWin) {
            for (CardDeck deck : decks) {
               //??????
            }
            scanner.close();
            return;
        }

        //Start player threads if no immediate win
        for (Thread t : threads) {
            t.start();
        }

        //Wait for one player to win
        try {
            for (Thread t: threads) {
                t.join();
            }
        } catch(Exception e) {
            System.out.println("Win error");
        }

        //Write final deck output files once a player has won the game
        for (CardDeck deck :decks) {
            deck.writeFinalContents();
        }

        System.out.println("Game finished. Output files generated.");
        scanner.close();
    }

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

    //Validates that a pack is valid
    private static List<Card> validatePack(String filename, int n) {
        int eRows = 8 * n;

        try {

            //Read all the lines from the file
            List<String> lines = Files.readAllLines(Paths.get(filename));

            //Validate that the row count matches
            if (lines.size() != expectedRows) {
                return null;
            }

            List<Card> cards = new ArrayList<>();
            for (String line: lines) {
                int value = Integer.parseInt(line.trim());

                if (value < 0) {
                    return null;
                }
                cards.add(new Card(value));
            }

            return cards;
        } catch (IOException | NumberFormatException e) {
            return null;
        }
    }



    
}
