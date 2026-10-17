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
        
    }

    
    static synchronized void discardCard(Integer card, ArrayList<Integer> deck) throws IOException {
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
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        {
            try {
                StringBuilder builder = new StringBuilder();
                for (Integer crd : deck) {
                    builder.append(crd);
                    builder.append(",");
                }
                deckFile.write(builder.toString());
                deckFile.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    static synchronized void drawCard(ArrayList<Integer> deck) throws IOException {
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
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }
    
    static synchronized void getDiscardedCard(ArrayList<Integer> deck) throws IOException {
        cardDiscarded = deck.get(3);
    }
    
    static synchronized void getDrawnCard(ArrayList<Integer> deck) throws IOException {
        cardDrawn = deck.get(0);
    }

}
