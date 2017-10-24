public class Card implements Comparable<Object>{
	public enum Suit{
		HEARTS,DIAMONDS,CLUBS,SPADES;
	}
	private Suit suit;
	private int number;
	
	public Card(Suit suit,int number){
		this.suit = suit;
		this.number = number;
	}
	
	public Suit getSuit(){
		return suit;
	}
	
	public boolean isEqual(Card other){
		if (this.suit == other.getSuit() && this.number == other.number)
			return true;
		else
			return false;
	}
	
	public int getNumber(){
		return number;
	}
	
	public String pokerRank(int num){
		if (num == 14)
			return "A";
		else if (num == 11)
			return "J";
		else if (num == 12)
			return "Q";
		else if (num == 13)
			return "K";
		else 
			return ""+num;
		
	}
	
	public String suitImg(Suit s){
		String ANSI_RESET = "\u001B[0m";
		String ANSI_BLACK = "\u001B[30m";
		String ANSI_RED = "\u001B[31m";
		String ANSI_WHITE_BACKGROUND = "\u001B[47m";
		String result = ANSI_WHITE_BACKGROUND;
		
		if (s == Suit.DIAMONDS)
			result = ANSI_RED + "♦";
		else if (s == Suit.CLUBS)
			result = ANSI_BLACK + "♣";
		else if (s == Suit.HEARTS)
			result = ANSI_RED + "❤";
		else 
			result = ANSI_BLACK + "♠";
		
		result += ANSI_RESET;
		
		return result;
	}
	
	public String suitConvert(Suit s){
		String result;
		if (s == Suit.DIAMONDS)
			result = "♦";
		else if (s == Suit.CLUBS)
			result = "♣";
		else if (s == Suit.HEARTS)
			result = "❤";
		else 
			result = "♠";

		return result;
	}
	
	@Override
    public int compareTo(Object compareCard) {
        return this.number - ((Card)compareCard).getNumber();
    }
	
	public String toString(){
		return suitConvert(suit) + "" + pokerRank(number);
	}
}


