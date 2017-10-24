import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class TexasPoker {
	private static Card[] cards;
	private static ArrayList<Integer> usedCards;
	
	public static void main(String[] args) {
		cards = getInitialCards();
		
		playRandomGame();
	}
	
	public static void playGame(){
		usedCards = new ArrayList<Integer>();
		int[] hands = new int[2];
		int[] street = {-1,-1,-1,-1,-1};
		
		//Draw pocket cards
		hands[0] = setCard("H6");
		hands[1] = setCard("S13");
		
		//Draw Flop
		street[0] = setCard("D9");
		street[1] = setCard("C11");
		street[2] = setCard("C2");
		
		System.out.println("-----[ Flop ]-----");
		System.out.print("Hands' Cards: ");
		showCards(hands);
		System.out.print("Street Cards: ");
		showCards(street);

		Card[] best = getBestCombination(hands,street);
		System.out.print("Best Cards: ");
		showCards(best);
		calWinningRate(hands,street);
		System.out.println("");
		
		//Draw turn
		street[3] = setCard("H2");
		
		System.out.println("-----[ Turn ]-----");
		System.out.print("Hands' Cards: ");
		showCards(hands);
		System.out.print("Street Cards: ");
		showCards(street);

		best = getBestCombination(hands,street);
		System.out.print("Best Cards: ");
		showCards(best);
		calWinningRate(hands,street);
		System.out.println("");
		
		//Draw river
		street[4] = setCard("S6");
		
		System.out.println("-----[ River ]-----");
		System.out.print("Hands' Cards: ");
		showCards(hands);
		System.out.print("Street Cards: ");
		showCards(street);

		best = getBestCombination(hands,street);
		System.out.print("Best Cards: ");
		showCards(best);
		calWinningRate(hands,street);
		System.out.println("");
	}
	
	public static void playRandomGame(){
		usedCards = new ArrayList<Integer>();
		int[] hands = new int[2];
		int[] street = {-1,-1,-1,-1,-1};
		
		//Draw pocket cards
		hands[0] = drawCard();
		hands[1] = drawCard();
		
		//Draw Flop
		street[0] = drawCard();
		street[1] = drawCard();
		street[2] = drawCard();
		
		System.out.println("-----[ Flop ]-----");
		System.out.print("Hands' Cards: ");
		showCards(hands);
		System.out.print("Street Cards: ");
		showCards(street);

		Card[] best = getBestCombination(hands,street);
		System.out.print("Best Cards: ");
		showCards(best);
		calWinningRate(hands,street);
		System.out.println("");
		
		//Draw turn
		street[3] = drawCard();
		
		System.out.println("-----[ Turn ]-----");
		System.out.print("Hands' Cards: ");
		showCards(hands);
		System.out.print("Street Cards: ");
		showCards(street);

		best = getBestCombination(hands,street);
		System.out.print("Best Cards: ");
		showCards(best);
		calWinningRate(hands,street);
		System.out.println("");
		
		//Draw river
		street[4] = drawCard();
		
		//System.out.print("List size: " + usedCards.size() + "\n");
		//System.out.print("Combination size: " + nCr(7,5) + "\n");
		System.out.println("-----[ River ]-----");
		System.out.print("Hands' Cards: ");
		showCards(hands);
		System.out.print("Street Cards: ");
		showCards(street);

		best = getBestCombination(hands,street);
		System.out.print("Best Cards: ");
		showCards(best);
		calWinningRate(hands,street);
		System.out.println("");
	}

	public static Card[] getBestCombination(int[] hands, int[] street){
		Card[] bestCards = new Card[5];
		/*Card[] sCards = {cards[street[0]],cards[street[1]],cards[street[2]],cards[street[3]],cards[street[4]]};
		System.out.println("isStraightFlush => " + isStraightFlush(sCards));
		System.out.println("isFour => " + isFour(sCards));
		System.out.println("isFullHouse => " + isFullHouse(sCards));
		System.out.println("isFlush => " + isFlush(sCards));
		System.out.println("isStraight => " + isStraight(sCards));
		System.out.println("isThree => " + isThree(sCards));
		System.out.println("isTwoPair => " + isTwoPair(sCards));
		System.out.println("isPair => " + isPair(sCards));
		*/
		ArrayList<Card> c = new ArrayList<Card>();
		
		c.add(cards[hands[0]]);
		c.add(cards[hands[1]]);
		
		for(int i =0;i<street.length;i++){
			if (street[i] != -1){
				c.add(cards[street[i]]);
			}
		}
		Collections.sort(c);
		//System.out.print("All Cards: ");
		//showCards(c);
		
		if (c.size() <= 5)
			bestCards = c.toArray(bestCards);
		else{
			int[] combinationIndex = {0,1,2,3,5};
			for(int i =0;i<5;i++)
				bestCards[i] = c.get(i);
			int count = 1;
			while(combinationIndex[0] <= c.size()-5){
				//System.out.print(Arrays.toString(combinationIndex) + "=> ");
				Card[] tempCards = new Card[5];
				for(int i =0;i<5;i++)
					tempCards[i] = c.get(combinationIndex[i]);
				
				if ( compareCards(bestCards,tempCards) == 1)
					bestCards = tempCards;

				for(int i=1;i<combinationIndex.length;i++){
					if (combinationIndex[i] == i+c.size()-5){
						combinationIndex[i-1]++;
						combinationIndex[i] = combinationIndex[i-1];
						//System.out.print(i+" "+Arrays.toString(combinationIndex) + "=> ");
					}
				}
				//System.out.println(" loop " + count++);
				combinationIndex[4]++;
				
			}
		}
		
		return bestCards;
	}
	
	/*
	 * The meaning of return value
	 * -1 => c1 > c2
	 *  0 => c1 = c2
	 *  1 => c1 < c2
	 */
	public static int compareCards(Card[] c1, Card[] c2){
		boolean[] c1Table = new boolean[8];
		c1Table[0] = isStraightFlush(c1);
		c1Table[1] = isFour(c1);
		c1Table[2] = isFullHouse(c1);
		c1Table[3] = isFlush(c1);
		c1Table[4] = isStraight(c1);
		c1Table[5] = isThree(c1);
		c1Table[6] = isTwoPair(c1);
		c1Table[7] = isPair(c1);
		
		boolean[] c2Table = new boolean[8];
		c2Table[0] = isStraightFlush(c2);
		c2Table[1] = isFour(c2);
		c2Table[2] = isFullHouse(c2);
		c2Table[3] = isFlush(c2);
		c2Table[4] = isStraight(c2);
		c2Table[5] = isThree(c2);
		c2Table[6] = isTwoPair(c2);
		c2Table[7] = isPair(c2);
		
		/*System.out.print("c1 Cards: ");
		showCards(c1);
		System.out.print("c2 Cards: ");
		showCards(c2);
		System.out.println("c1 => "+Arrays.toString(c1Table));
		System.out.println("c2 => "+Arrays.toString(c2Table));*/
		
		int c1Rank = 8;
		int c2Rank = 8;
		
		for (int i = 0;i<c1Table.length;i++){
			if (c1Table[i] == true && c1Rank == 8)
				c1Rank = i;
			if (c2Table[i] == true && c2Rank == 8)
				c2Rank = i;
		}
		//System.out.println("c1 => "+c1Rank);
		//System.out.println("c2 => "+c2Rank);
		
		//0 is higher
		if (c1Rank < c2Rank)
			return -1;
		else if (c1Rank > c2Rank)
			return 1;
		else{
			//System.out.println("They are same rank.");
			switch(c1Rank){
				case 0:
				case 4:
					//2,3,4,5,A and 10,J,Q,K,A both cNum == A, so we use second last number
					if (c1[3].getNumber() < c2[3].getNumber())
						return 1;
					else if (c1[3].getNumber() > c2[3].getNumber())
						return -1;
					else
						return 0;
					
				case 1:
					int c1Four = c1[2].getNumber();
					int c2Four = c1[2].getNumber();
					
					if (c1Four > c2Four)
						return -1;
					else if (c1Four < c2Four)
						return 1;
					else{
						int c1Single = 0;
						int c2Single = 0;
						
						if(c1Four == c1[0].getNumber())
							c1Single = c1[4].getNumber();
						else
							c1Single = c1[0].getNumber();
						
						if(c2Four == c2[0].getNumber())
							c2Single = c2[4].getNumber();
						else
							c2Single = c2[0].getNumber();
						
						if (c1Single > c2Single)
							return -1;
						else if (c1Single < c2Single)
							return 1;
						else
							return 0;
					}
				case 2:
					int c1Set = c1[2].getNumber();
					int c2Set = c1[2].getNumber();
					
					if (c1Set > c2Set)
						return -1;
					else if (c1Set < c2Set)
						return 1;
					else{
						int c1Pair = 0;
						int c2Pair = 0;
						
						if(c1Set == c1[1].getNumber())
							c1Pair = c1[3].getNumber();
						else
							c1Pair = c1[1].getNumber();
						
						if(c2Set == c2[1].getNumber())
							c2Pair = c2[3].getNumber();
						else
							c2Pair = c2[1].getNumber();
						
						if (c1Pair > c2Pair)
							return -1;
						else if (c1Pair < c2Pair)
							return 1;
						else
							return 0;
					}
				case 3:
					for (int i=4;i>=0;i--){
						if (c1[i].getNumber() < c2[i].getNumber())
							return 1;
						else if (c1[i].getNumber() > c2[i].getNumber())
							return -1;
					}
					return 0;
				case 5:
					int c1Three = c1[2].getNumber();
					int c2Three = c1[2].getNumber();
					
					if (c1Three > c2Three)
						return -1;
					else if (c1Three < c2Three)
						return 1;
					else{
						for (int i=4;i>=0;i--){
							if (c1[i].getNumber() < c2[i].getNumber())
								return 1;
							else if (c1[i].getNumber() > c2[i].getNumber())
								return -1;
						}
						return 0;
					}
					
				case 6:
					//Assume it must two pair, not other
					int c1NumH = 0;
					int c2NumH = 0;
					int c1NumL = 0;
					int c2NumL = 0;
					for(int i=0;i<c1.length-1;i++){
						if (c1[i+1].getNumber() == c1[i].getNumber()){
							if (c1[i].getNumber() > c1NumH){
								c1NumL = c1NumH;
								c1NumH = c1[i].getNumber();
							}else{
								c1NumL = c1[i].getNumber();
							}
						}
						if (c2[i+1].getNumber() == c2[i].getNumber()){
							if (c2[i].getNumber() > c2NumH){
								c2NumL = c2NumH;
								c2NumH = c2[i].getNumber();
							}else{
								c2NumL = c2[i].getNumber();
							}
						}
					}
					//Compare which pair is bigger
					if (c1NumH < c2NumH)
						return 1;
					else if (c1NumH > c2NumH)
						return 1;
					else{
						//it still have one pair
						if (c1NumL < c2NumL)
							return 1;
						else if (c1NumL > c2NumL)
							return 1;
						else{
							for (int i=4;i>=0;i--){
								if (c1[i].getNumber() < c2[i].getNumber())
									return 1;
								else if (c1[i].getNumber() > c2[i].getNumber())
									return -1;
							}
							return 0;
						}
					}
				case 7:
					//Assume it must a pair
					int c1Num = 0;
					int c2Num = 0;
					for(int i=0;i<c1.length-1;i++){
						if (c1[i+1].getNumber() == c1[i].getNumber())
							c1Num = c1[i].getNumber();
						if (c2[i+1].getNumber() == c2[i].getNumber())
							c2Num = c2[i].getNumber();
					}
					//Compare which pair is bigger
					if (c1Num < c2Num)
						return 1;
					else if (c1Num > c2Num)
						return 1;
					else{
						for (int i=4;i>=0;i--){
							if (c1[i].getNumber() < c2[i].getNumber())
								return 1;
							else if (c1[i].getNumber() > c2[i].getNumber())
								return -1;
						}
						return 0;
					}
				default:
					//Since the array already sorted, the rightmost card must be the highest
					for (int i=4;i>=0;i--){
						if (c1[i].getNumber() < c2[i].getNumber())
							return 1;
						else if (c1[i].getNumber() > c2[i].getNumber())
							return -1;
					}
					return 0;
			}
		}
	}
	
	public static void calWinningRate(int[] hands, int[] street){
		boolean[] isUsed = new boolean[52];
		isUsed[hands[0]] = true;
		isUsed[hands[1]] = true;
		
		int cardCount = 2;
		
		for(int i =0;i<street.length;i++){
			if (street[i] != -1){
				isUsed[street[i]] = true;
				cardCount++;
			}
		}
		int[] remainCard = new int[52 - cardCount];
		int[] combineIndex = {0,1};
		int tempCount = 0;
		for(int i=0;i<isUsed.length;i++){
			if (isUsed[i] == false)
				remainCard[tempCount++] = i;
		}
		//System.out.println(Arrays.toString(remainCard));
		double winCount = 0.0;
		double lossCount = 0.0;
		double drawCount = 0.0;
		
		Card[] ownBest = getBestCombination(hands,street);
		
		while(combineIndex[0] <= remainCard.length-2){
			int[] opponent = new int[2];
			opponent[0] = remainCard[combineIndex[0]];
			opponent[1] = remainCard[combineIndex[1]];
			
			Card[] oppBest = getBestCombination(opponent,street);
			
			int result = compareCards(ownBest,oppBest);
			if (result == 1)
				lossCount++;
			else if (result == -1)
				winCount++;
			else
				drawCount++;
			
			for(int i=1;i<combineIndex.length;i++){
				if (combineIndex[i] == i+remainCard.length-2){
					combineIndex[i-1]++;
					combineIndex[i] = combineIndex[i-1];
					//System.out.print(i+" "+Arrays.toString(combinationIndex) + "=> ");
				}
			}
			//System.out.println(" loop " + count++);
			combineIndex[1]++;
		}
		
		double total = winCount + lossCount + drawCount;
		double winRate = winCount / total;
		double lossRate = lossCount / total;
		double drawRate = drawCount / total;
		
		System.out.println("Win Rate: " + winRate);
		System.out.println("Loss Rate: " + lossRate);
		System.out.println("Draw Rate: " + drawRate);
	}
	
	public static Card[] getInitialCards(){
		Card[] cards = new Card[52];
		int count = 0;
		for(int i=0;i<4;i++){
			for(int j=2;j<15;j++){
				switch(i){
					case 0: cards[count++] = new Card(Card.Suit.DIAMONDS,j);break;
					case 1: cards[count++] = new Card(Card.Suit.CLUBS,j);break;
					case 2: cards[count++] = new Card(Card.Suit.HEARTS,j);break;
					case 3: cards[count++] = new Card(Card.Suit.SPADES,j);break;
				}
			}
		}
		
		return cards;
	}
	
	public static int setCard(String str){
		char ch = str.charAt(0);
		int n = Integer.parseInt(str.substring(1));
		//System.out.println(ch+" "+n);
		//System.out.println(new Card(convertSuit(ch),n).toString());
		int num = getCardIndex(new Card(convertSuit(ch),n));

		return num;
	}
	
	public static int drawCard(){
		Random ran = new Random();
		boolean valid = true;
		int num = -1;
		
		do{
			num = ran.nextInt(52);
			for(int i=0;i<usedCards.size();i++){
				if (usedCards.get(i) == num)
					valid = false;
			}
		}while(!valid);
		
		//isUsed[num] = true;
		usedCards.add(num);
		
		return num;
	}
	
	public static int getCardIndex(Card card){
		for(int i = 0;i<cards.length;i++){
			if (cards[i].isEqual(card))
				return i;
		}
		return -1;
	}
	
	//checking ranking
	public static boolean isStraightFlush(Card[] c){
		if (isStraight(c) && isFlush(c))
			return true;
		return false;
	}
	
	public static boolean isFour(Card[] c){
		Arrays.sort(c);
		int count = 1;
		
		for(int i=0;i<c.length-1;i++){
			if (c[i+1].getNumber() == c[i].getNumber())
				count++;
			else
				count = 1;
			if (count >= 4)
				return true;
		}
		return false;
	}
	
	public static boolean isFullHouse(Card[] c){
		Arrays.sort(c);
		int numCount = 1;
		for(int i=0;i<c.length-1;i++){
			if (c[i+1].getNumber() - c[i].getNumber() != 0)
				numCount++;
		}
		
		if (numCount == 2 && !isFour(c))
			return true;
		return false;
	}
	
	public static boolean isFlush(Card[] c){
		Arrays.sort(c);
		for(int i=0;i<c.length-1;i++){
			if (c[i+1].getSuit() != c[i].getSuit())
				return false;
		}
		return true;
	}
	
	public static boolean isStraight(Card[] c){
		Arrays.sort(c);
		for(int i=0;i<c.length-1;i++){
			if (c[i+1].getNumber() == 14 && i == 3){
				if (c[i].getNumber() == 5)
					return true;
			}
			if (c[i+1].getNumber() - c[i].getNumber() != 1)
				return false;
		}
		return true;
	}
	
	public static boolean isThree(Card[] c){
		Arrays.sort(c);
		int count = 1;
		
		for(int i=0;i<c.length-1;i++){
			if (c[i+1].getNumber() == c[i].getNumber())
				count++;
			else
				count = 1;
			if (count >= 3)
				return true;
		}
		return false;
	}
	
	public static boolean isTwoPair(Card[] c){
		Arrays.sort(c);
		int pairCount = 0;
		
		for(int i=0;i<c.length-1;i++){
			if (c[i+1].getNumber() == c[i].getNumber()){
				pairCount++;
				i++;
				if (pairCount >= 2)
					return true;
			}
			
		}
		return false;
	}
	
	public static boolean isPair(Card[] c){
		Arrays.sort(c);
		for(int i=0;i<c.length-1;i++){
			if (c[i+1].getNumber() == c[i].getNumber())
				return true;
		}
		return false;
	}
	
	
	//Math ------
	public static BigDecimal nCr(int n,int r){
		int k = n - r;
		int index = k;
		if (r > k)
			index = r;
		BigDecimal denominator = factorial(new BigDecimal(r));
		if (r > k)
			denominator = factorial(new BigDecimal(k));
		BigDecimal molecular = new BigDecimal("1");
		for(int i=n;i > index;i--){
			molecular = molecular.multiply(new BigDecimal(i));
		}
		
		return molecular.divide(denominator);
	}
	
	public static BigDecimal factorial(BigDecimal n){
		if (n.equals(new BigDecimal("1")) || n.equals(new BigDecimal("0")))
			return new BigDecimal("1");
		return n.multiply(factorial(n.subtract(new BigDecimal("1"))));
	}
	//Math ------
	
	//Display ------
	public static void showCards(int[] cardIndex){
		for(int i = 0;i<cardIndex.length;i++)
			if (cardIndex[i] != -1)
				System.out.print(cards[cardIndex[i]].toString() + " ");
		System.out.println("");
	}
	
	public static void showCards(Card[] c){
		for(int i = 0;i<c.length;i++)
			System.out.print(c[i].toString() + " ");
		System.out.println("");
	}
	
	public static void showCards(ArrayList<Card> c1){
		Card[] c = new Card[c1.size()];
		c = c1.toArray(c);
		for(int i = 0;i<c.length;i++)
			System.out.print(c[i].toString() + " ");
		System.out.println("");
	}
	//Display ------
	
	public static Card.Suit convertSuit(char ch){
		if (ch == 'D')
			return Card.Suit.DIAMONDS;
		else if (ch == 'C')
			return Card.Suit.CLUBS;
		else if (ch == 'H')
			return Card.Suit.HEARTS;
		else if (ch == 'S')
			return Card.Suit.SPADES;
		else
			return Card.Suit.DIAMONDS;
		
	}
	
	
	
}
