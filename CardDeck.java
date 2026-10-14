import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileWriter;

public class CardDeck {
    private ArrayList<Integer> deck;
    public Integer deckNumber;
    public String fileName = "deck" + deckNumber + ".txt";
    public CardDeck(ArrayList<Integer> deck, Integer deckNumber) {
        this.deck = deck;
        FileWriter deckFile = new FileWriter(fileName);
        
    }

    
    static void discardCard(Integer card, ArrayList<Integer> deck) {
        deck.set(0, card);
        deckFile.write(deck);
        deckFile.close();
    }
    
    static void drawCard(ArrayList<Integer> deck) {
        deck.remove(0);
        for(int i=0; 1<5; i++) {
            deck.set(i, deck(i+1));
        }
        deckFile.write(deck);
        deckFile.close();
    }
    

}
