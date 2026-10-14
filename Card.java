public class Card {
    private final int value;


    //Constructor representing a non-negative integer face value.
    public Card(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Card value cannot be negative.");
        }
        this.value = value;
    }

}