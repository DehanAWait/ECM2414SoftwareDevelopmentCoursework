import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class CardDeck {
    private ArrayList<Integer> deck;
    public static FileWriter deckFile;
    public static Integer cardDrawn;
    public static Integer cardDiscarded;
    public static Integer deckNumber;
    public String fileName = "deck" + deckNumber + ".txt";
    public CardDeck(ArrayList<Integer> deck, Integer deckNumber) throws IOException {
        this.deck = deck;
        FileWriter deckFile = new FileWriter(fileName);
        deckFile.close();
        //Each deck is created with a file to save all updates to the deck.
        //Each deck has an identifying number to distinguish them during the game.
    }

    
    static synchronized void discardCard(Integer card, ArrayList<Integer> deck) throws IOException {
        //Adds a card discarded from player hand to bottom of the deck
        deck.add(card);
        {
            try {
                StringBuilder builder = new StringBuilder();
                for (Integer crd : deck) {
                    builder.append(crd);
                    builder.append(",");
                }
                deckFile.write(builder.toString());
                deckFile.close();
                //updated state of deck added to the deck's individual file.
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    static synchronized void drawCard(ArrayList<Integer> deck) throws IOException {
        //Removes the top card from the deck and moves position of the other cards one place up in the deck
        cardDrawn = deck.get(0);
        //Card drawn from the deck is saved to a variable to be added to player's hand in Player class
        deck.remove(0);
        for(int i=0; i<5; i++) {
            deck.set(i, deck.get(i+1));
        }
        {
            try {
                StringBuilder builder = new StringBuilder();
                for (Integer card : deck) {
                    builder.append(card);
                    builder.append(",");
                }
                deckFile.write(builder.toString());
                deckFile.close();
                //updated state of deck added to the deck's individual file.
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }
    
    static synchronized void getDiscardedCard(ArrayList<Integer> deck) throws IOException {
        //Obtains value of a card discarded to a deck to be added to a player's file in the Player claass.
        cardDiscarded = deck.get(3);
    }

    static synchronized void getDrawnCard(ArrayList<Integer> deck) throws IOException {
        //Used to obtain value of card drawn from a deck to be added to a player's file in Player class.
        cardDrawn = deck.get(0);
    }

}
