public class Card {
    private final int value;


    //Constructor representing a non-negative integer face value.
    public Card(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Card value cannot be negative.");
        }
        this.value = value;
    }

    //Retrieves the value of the card
    public int getValue() {
        return value;
    }

    //Converts value into readable string
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    /*
    //Compares two cards values
    @Override
    public boolean equals(Object obj) {
        //Error prevention for if the object is empty
        if (this == obj) {
        return true;
        }
        Card card = (Card) obj;
        return value == card.value;
    } 
    */

    //Ensures Card objects behave correctly
    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    } 

}