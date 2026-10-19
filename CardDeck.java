import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CardDeck {
    @SuppressWarnings("unused")
    private ArrayList<Card> deck;
    public FileWriter deckFile;
    public Card cardDrawn;
    public Card cardDiscarded;
    public Integer deckNumber;
    private String fileName;


    public CardDeck(ArrayList<Card> deck, Integer deckNumber) throws IOException {
        this.deck = deck;
        this.deckNumber = deckNumber;
        this.fileName = "deck" + deckNumber + "_output.txt";

        this.deckFile = new FileWriter(fileName);
        this.deckFile.close();
        //Each deck is created with a file to save all updates to the deck.
        //Each deck has an identifying number to distinguish them during the game.
    }

    
    public synchronized void discardCard(Card card) {
        //Adds a card discarded from player hand to bottom of the deck
        deck.add(card);
            try {
                StringBuilder builder = new StringBuilder("deck" + deckNumber + " contents:");
                for (Card crd : deck) {
                    builder.append(" ").append(crd.getValue());
                }
                deckFile = new FileWriter(fileName, false);
                deckFile.write(builder.toString() + "\n");
                deckFile.close();
                //updated state of deck added to the deck's individual file.
            }
            catch (IOException e) {
                e.printStackTrace();
            }
    }
    
    
    public synchronized Card drawCard() {
        if (deck.isEmpty()) return null;
        //Removes the top card from the deck and moves position of the other cards one place up in the deck
        cardDrawn = deck.get(0);
        //Card drawn from the deck is saved to a variable to be added to player's hand in Player class
        deck.remove(0);

        try {
            StringBuilder builder = new StringBuilder("deck " + deckNumber + " contents:");
            for (Card card : deck) {
                builder.append(card);
                builder.append(",");
            }
            deckFile = new FileWriter(fileName, false);
            deckFile.write(builder.toString() + "\n");
            deckFile.close();
                //updated state of deck added to the deck's individual file.
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return cardDrawn;
    }

    public void OutputDeckFile() {
        File newFile = new File(fileName);
        try (Scanner myReader = new Scanner(newFile)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    public synchronized Card getDiscardedCard() {
        //Obtains value of a card discarded to a deck to be added to a player's file in the Player claass.
        cardDiscarded = deck.get(deck.size() - 1);
        return cardDiscarded;
    }

    public synchronized Card getDrawnCard(){
        //Used to obtain value of card drawn from a deck to be added to a player's file in Player class.
        return cardDrawn;
    }

}
