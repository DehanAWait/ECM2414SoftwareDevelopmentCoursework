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
    public boolean hasWon = false; 
    public String fileName = "player" + playerNumber + ".txt";
    public Player(Integer playerNumber, ArrayList<Integer> hand) throws IOException {
        this.hand = hand;
        playerFile = new FileWriter(fileName);
        playerFile.close();
        //Each player has a hand consisting of 4 cards, as well as a file to save all of their actions to.


    }
    Thread t1 = new Thread(new Runnable() {
        //Thread made for drawing cards from the deck to their left, saving the move to their player file.
        public void run() {
            while (!hand.equals(Arrays.asList(playerNumber, playerNumber, playerNumber, playerNumber))) {
                try {
                    if (CardDeck.deckNumber == playerNumber) {
                        CardDeck.drawCard(hand);
                        CardDeck.getDrawnCard(hand);
                        hand.add(CardDeck.cardDrawn);
                        playerFile.write("player 1 draws a" + cardDrawn.toString() + "from deck" + playerNumber.toString() + "\n");
                        //Saves move to player file detailing which card was drawn from which deck.
                        playerFile.close();
                    }
                  
                } catch (IOException e) {
                    System.out.println("An error has occurred.");
                    e.printStackTrace();
                }
            }
        }
    });

    Thread t2 = new Thread (new Runnable() {
        //Thread made for discarding cards and saving the move to the player file
        public void run() {
            while(!hand.equals(Arrays.asList(playerNumber, playerNumber, playerNumber, playerNumber))) {
                try {
                    if (CardDeck.deckNumber == playerNumber+1) {
                        for (int i = 0; i <hand.size(); i++) {
                            if (hand.get(i) != playerNumber) {
                                CardDeck.discardCard(hand.get(i), hand);
                                playerFile.write("player 1 discards a" + cardDiscarded.toString() + "to deck" + playerNumber.toString() + "\n");
                                //Saves the individual move to the player file.
                                playerFile.close();
                            }
                        }
                    }
                }
                catch (IOException e) {
                    System.out.println("An error has occurred.");
                    e.printStackTrace();
                }
            }
        }
    });
    
    public void startThreads() {
        t1.start();
        t2.start();
        //Starts the threads for drawing and discarding cards.
    }

}
