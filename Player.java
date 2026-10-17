import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Player {
    
    public Integer playerNumber;
    private ArrayList<Integer> hand;
    public static Integer cardDrawn;
    public static Integer cardDiscarded;
    public static FileWriter playerFile;
    public String fileName = "player" + playerNumber + ".txt";
    public Player(Integer playerNumber, ArrayList<Integer> hand) throws IOException {
        this.hand = hand;
        FileWriter playerFile = new FileWriter(fileName);
        playerFile.close();

    }
    Thread t1 = new Thread(new Runnable() {
        public void run() {
            while (!hand.equals(Arrays.asList(playerNumber, playerNumber, playerNumber, playerNumber))) {
                try {
                    if (CardDeck.deckNumber == playerNumber) {
                        CardDeck.drawCard(hand);
                        CardDeck.getDrawnCard(hand);
                        playerFile.write("player 1 draws a" + cardDrawn.toString() + "from deck" + playerNumber.toString() + "\n");
                    }
                  
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    Thread t2 = new Thread (new Runnable() {
        public void run() {
            while(!hand.equals(Arrays.asList(playerNumber, playerNumber, playerNumber, playerNumber))) {
                try {
                    if (CardDeck.deckNumber == playerNumber+1) {
                        for (int i = 0; i <hand.size(); i++) {
                            if (hand.get(i) != playerNumber) {
                                CardDeck.discardCard(hand.get(i), hand);
                                playerFile.write("player 1 discards a" + cardDiscarded.toString() + "to deck" + playerNumber.toString() + "\n");
                            }
                        }
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    });
    
    public void startThreads() {
        t1.start();
        t2.start();
    }


}
