import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Player implements Runnable {
    
    public Integer playerNumber;
    private ArrayList<Card> hand;
    public Card cardDrawn;
    public Card cardDiscarded;
    public FileWriter playerFile;
    public boolean hasWon = false; 

    public String fileName;

    private CardDeck leftDeck;
    private CardDeck rightDeck;

    public Player(Integer playerNumber, ArrayList<Card> hand, CardDeck leftDeck, CardDeck rightDeck) throws IOException {
        this.playerNumber = playerNumber;
        this.hand = hand;
        this.leftDeck = leftDeck;
        this.rightDeck = rightDeck;
        this.fileName = "player" +playerNumber + "_output.txt";

        playerFile = new FileWriter(fileName, false);
        StringBuilder sb = new StringBuilder("player " + playerNumber + " initial hand");
        for (Card c : hand) {
            sb.append(" ").append(c.getValue());
        }
        playerFile.write(sb.toString() + "\n");
        playerFile.close();
        //Each player has a hand consisting of 4 cards, as well as a file to save all of their actions to.
    }

    public boolean hasWon() {
        //Method to check win condition
        if (hand.size() < 4) return false;
        int firstVal = hand.get(0).getValue();
        for (Card c : hand) {
            if (c.getValue() != firstVal) {
                return false;
            }
        }
        return true;
    }


    public Thread t1 = new Thread(new Runnable() {
        //Thread made for drawing cards from the deck to their left, saving the move to their player file.
        public void run() {
            while (!hasWon() && !Thread.currentThread().isInterrupted()) {
                try {
                    cardDrawn = leftDeck.drawCard();
                    if (cardDrawn != null) {
                        synchronized (hand) {
                            hand.add(cardDrawn);
                        }

                        playerFile = new FileWriter(fileName, true);
                        playerFile.write("player "+ playerNumber + " draws a " +cardDrawn.getValue() + " from deck " + leftDeck.deckNumber + "\n");
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

    public Thread t2 = new Thread (new Runnable() {
        //Thread made for discarding cards and saving the move to the player file
        public void run() {
            while(!hasWon() && !Thread.currentThread().isInterrupted()) {
                try {
                    synchronized (hand) {
                        if (hasWon()) break;
                        for (int i = 0; i <hand.size(); i++) {
                            if (hand.get(i).getValue() != playerNumber) {
                                cardDiscarded = hand.remove(i);
                                rightDeck.discardCard(cardDiscarded);

                                playerFile = new FileWriter(fileName, true);
                                playerFile.write("player " + playerNumber + " discards a " + cardDiscarded.getValue() + " to deck " + rightDeck.deckNumber + "\n");

                                StringBuilder handStr = new StringBuilder("player " + playerNumber + " current hand is");
                                for (Card c :hand) {
                                    handStr.append(" ").append(c.getValue());
                                }
                                playerFile.write(handStr.toString() + "\n");
                                //Saves the individual move to the player file.
                                playerFile.close();
                                break;
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

    @Override
    public void run() {
        startThreads();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
