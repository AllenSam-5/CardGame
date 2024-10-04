// Assignment Name: Card Game
// Name of Student: Allen Sam
// Due Date: 2020-11-06 (11:59 PM)
// Brief Program Description:
// This program will replicate a card game that determines a winner through the card combinations in the hands.
// Different card combinations give higher value than others and a winner is determined by the number of points.

class CardGame {

	// Global Variable List
	static final int DECKSIZE = 52;
	static final int HANDSIZE = 5;
	static String[] cards = new String[DECKSIZE];
	static String[] faces = { "Ace", "Deuce", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
			"Jackolantern", "Maleficent", "Dracula" };
	static String[] suits = { "PumpkinPatch", "WitchHut", "Graveyard", "Tomb" };

	static int[] usedCards = new int[DECKSIZE];
	static int numUsedCards = 0;
	static String pairFace;
	static String threeKindFace;

	// The main Method of the Program
	public static void main(String[] args) {

		// Variable List
		String[] hand1 = new String[HANDSIZE];
		String[] hand2 = new String[HANDSIZE];

		// Method List (Invoking, Assigning and Printing)
		createDeck();
		shuffleDeck();

		hand1 = dealHand();
		hand2 = dealHand();

		int player1Points = score(hand1);
		int player2Points = score(hand2);

		String result = winner(hand1, hand2, player1Points, player2Points);

		System.out.print("Player 1: ");
		printCards(hand1);
		System.out.print("\nType of Hand: " + typeHand(hand1));

		System.out.print("\nPlayer 2: ");
		printCards(hand2);
		System.out.print("\nType of Hand: " + typeHand(hand2));

		System.out.println("\nOUTCOME: " + result);

	}

	// The createDeck method for creating all 52 cards in the deck. This method will
	// create cards using the face and suit arrays. This method is then used to
	// create an ordered deck of cards by the main method.
	public static void createDeck() {

		int k = 0;

		for (int i = 0; i < suits.length; i++) {
			for (int j = 0; j < faces.length; j++) {
				cards[k] = faces[j] + " " + suits[i];
				k++;
			}
		}

	}

	// The shuffleDeck method for shuffling all 52 cards in the deck. This method
	// takes the deck created in the createDeck method and shuffles it using basic
	// swapping logic.
	public static void shuffleDeck() {

		String temp = "";

		for (int i = 0; i < cards.length; i++) {
			int num = (int) (51 * Math.random());
			temp = cards[i];
			cards[i] = cards[num];
			cards[num] = temp;
		}

	}

	// The dealCard method for dealing one of the 52 cards in the deck. This method
	// will randomly generate a card from the shuffled deck. Cards taken from the
	// deck are tracked and prevented from appearing again in the same run.
	public static String dealCard() {

		boolean found = false;
		int num;

		while (true) {
			found = false;
			num = (int) (cards.length * Math.random());

			for (int i = 0; i < numUsedCards; i++) {
				if (num == usedCards[i])
					found = true;
			}

			if (found == false)
				break;
		}

		String deal = cards[num];
		usedCards[numUsedCards] = num;
		numUsedCards++;

		return deal;

	}

	// The dealHand method for dealing a hand to a player. This method will create a
	// hand array and deal five cards from the shuffled deck. This method uses the
	// dealCard method in order to track usedCards and perform important functions.
	public static String[] dealHand() {

		String[] hand = new String[HANDSIZE];

		for (int i = 0; i < hand.length; i++)
			hand[i] = dealCard();

		return hand;

	}

	// The typeHand method for determining the combination in a player's hand. This
	// method is used for invoking the boolean methods for different card
	// combinations. If a boolean method is true and has the highest value among
	// other true boolean methods, the "Type of Hand" output result will be set to
	// that boolean method.
	public static String typeHand(String[] hand) {

		String typeOfHand = "";

		if (isRoyFlush(hand) == true)
			typeOfHand = "Royal Flush";
		else if (isStFlush(hand) == true)
			typeOfHand = "Straight Flush";
		else if (is4Kind(hand) == true)
			typeOfHand = "Four of a Kind";
		else if (isFullHouse(hand) == true)
			typeOfHand = "Full House";
		else if (isFlush(hand) == true)
			typeOfHand = "Flush";
		else if (isStraight(hand) == true)
			typeOfHand = "Straight";
		else if (is3Kind(hand) == true)
			typeOfHand = "Three of a Kind";
		else if (is2Pairs(hand) == true)
			typeOfHand = "Two Pairs";
		else if (isPair(hand, 0) == true)
			typeOfHand = "Pair";
		else
			typeOfHand = "High Card";

		return typeOfHand;

	}

	// The isPair method for checking the contents of a player's hand. This method
	// will check for a pair in a player's hand. A pair must be two cards that have
	// the same face value. If these requirements are met, the boolean method will
	// return true.
	public static boolean isPair(String[] hand, int forFullHouse) {

		boolean isPair = false;

		for (int i = 0; i < hand.length; i++) {
			for (int j = i + 1; j < hand.length; j++) {
				String[] card1 = hand[i].split(" ");
				String[] card2 = hand[j].split(" ");
				if (card1[0].equals(card2[0])) {
					if (forFullHouse == 0)
						isPair = true;
					else {
						pairFace = card1[0];
						if (!threeKindFace.equals(pairFace)) {
							isPair = true;
							break;
						}
					}
				}
			}
			if (isPair == true)
				break;
		}

		return isPair;

	}

	// The is2Pairs method for checking the contents of a player's hand. This method
	// will check for two pairs in a player's hand. The cards in a pair must have
	// the same face value. If these requirements are met, the boolean method will
	// return true.
	public static boolean is2Pairs(String[] hand) {

		boolean is2Pairs = false;
		int count = 0;
		int selectedi = -1;
		int selectedj = -1;

		for (int i = 0; i < hand.length; i++) {
			for (int j = i + 1; j < hand.length; j++) {
				if (i == selectedi || i == selectedj || j == selectedi || j == selectedj)
					continue;
				String[] card1 = hand[i].split(" ");
				String[] card2 = hand[j].split(" ");
				if (card1[0].equals(card2[0])) {
					count++;
					selectedi = i;
					selectedj = j;

					if (count == 2) {
						is2Pairs = true;
						break;
					}
				}

			}
			if (is2Pairs == true)
				break;
		}

		return is2Pairs;

	}

	// The is3Kind method for checking the contents of a player's hand. This method
	// will check for three different cards in a player's hand that all have the
	// same face value. If these requirements are met, the boolean method will
	// return true.
	public static boolean is3Kind(String[] hand) {

		boolean is3Kind = false;
		String[] handFaces = new String[hand.length];

		for (int i = 0; i < hand.length; i++) {
			String[] card = hand[i].split(" ");
			handFaces[i] = card[0];
		}

		for (int i = 0; i < handFaces.length; i++) {
			int count = countString(handFaces, handFaces[i]);
			if (count == 3) {
				is3Kind = true;
				threeKindFace = handFaces[i];
				break;
			}
		}

		return is3Kind;

	}

	// The is4Kind method for checking the contents of a player's hand. This method
	// will check for four different cards in a player's hand that all have the same
	// face value. If these requirements are met, the boolean method will return
	// true.
	public static boolean is4Kind(String[] hand) {

		boolean is4Kind = false;
		String[] handFaces = new String[hand.length];

		for (int i = 0; i < hand.length; i++) {
			String[] card = hand[i].split(" ");
			handFaces[i] = card[0];
		}

		for (int i = 0; i < handFaces.length; i++) {
			int count = countString(handFaces, handFaces[i]);
			if (count == 4) {
				is4Kind = true;
				break;
			}
		}

		return is4Kind;

	}

	// The isFlush method for checking the contents of a player's hand. This method
	// will check if the cards in a player's hand are all of the same suit. If these
	// requirements are met, the boolean method will return true.
	public static boolean isFlush(String[] hand) {

		boolean isFlush = false;
		String[] handSuits = new String[hand.length];

		for (int i = 0; i < hand.length; i++) {
			String[] card = hand[i].split(" ");
			handSuits[i] = card[1];
		}

		for (int i = 0; i < handSuits.length; i++) {
			int count = countString(handSuits, handSuits[i]);
			if (count == 5)
				isFlush = true;
		}

		return isFlush;

	}

	// The countString method for counting the number of repetitive faces or suits
	// in a
	// player's hand.
	public static int countString(String[] strings, String string) {

		int count = 0;

		for (int i = 0; i < strings.length; i++) {
			if (string.equals(strings[i]))
				count++;
		}

		return count;

	}

	// The isStraight method for checking the contents of a player's hand. This
	// method will check if a player's hand contains different face values that are
	// consequent to one another. If these requirements are met, the boolean method
	// will return true.
	public static boolean isStraight(String[] hand) {

		boolean isStraight = true;
		int[] positions = new int[hand.length];

		positions = findPositions(hand);

		bubbleSort(positions);

		if (positions[0] == 0 && positions[1] == 9 && positions[2] == 10 && positions[3] == 11 && positions[4] == 12)
			isStraight = true;
		else {
			for (int i = 0; i < positions.length - 1; i++) {
				if (positions[i + 1] - positions[i] != 1)
					isStraight = false;
			}
		}

		return isStraight;

	}

	// The isStFlush method for checking the contents of a player's hand. This
	// method will check whether a player's hand contains different face values that
	// are consequent to one another, and whether they are all of the same suit. If
	// these requirements are met, the boolean method will return true.
	public static boolean isStFlush(String[] hand) {

		boolean isStFlush = false;

		if (isStraight(hand) == true) {
			if (isFlush(hand) == true)
				isStFlush = true;
		}

		return isStFlush;

	}

	// The isFullHouse method for checking the contents of a player's hand. This
	// method will check if the player has a pair of cards, and a three of a kind
	// with a different face value than the pair. If these requirements are met, the
	// boolean method will return true.
	public static boolean isFullHouse(String[] hand) {

		boolean isFullHouse = false;
		pairFace = "";
		threeKindFace = "";

		if (is3Kind(hand) == true && isPair(hand, 1) == true) {
			if (!pairFace.equals(threeKindFace))
				isFullHouse = true;
		}

		return isFullHouse;

	}

	// The isRoyFlush method for checking the contents of a player's hand. This
	// method will check whether the player's hand contains all the cards from Ten
	// to Ace and whether they are all the same suit. If these requirements are met,
	// the boolean method will return true.
	public static boolean isRoyFlush(String[] hand) {

		boolean isRoyFlush = false;
		int[] positions = new int[hand.length];

		positions = findPositions(hand);

		bubbleSort(positions);

		if (positions[0] == 0 && positions[1] == 9 && positions[2] == 10 && positions[3] == 11 && positions[4] == 12) {
			if (isFlush(hand) == true)
				isRoyFlush = true;
		}

		return isRoyFlush;

	}

	// The winner method for determining the winning player (includes high card in
	// TIE conditions). This method uses the logic in the score method to determine
	// a winner out of the two players. In the case of a tie, the logic in this
	// method will determine a winner by looking at highest cards. A true tie can be
	// achieved with equal pairs and equal high cards.
	public static String winner(String[] hand1, String[] hand2, int p1, int p2) {

		String result = "";

		if (p1 > p2)
			result = "Player 1 Wins!";
		else if (p2 > p1)
			result = "Player 2 Wins!";
		else {
			int[] positions1 = new int[HANDSIZE];
			positions1 = findPositions(hand1);
			bubbleSort(positions1);
			int[] positions2 = new int[HANDSIZE];
			positions2 = findPositions(hand2);
			bubbleSort(positions2);
			for (int i = HANDSIZE - 1; i >= 0; i--) {
				if (positions1[i] > positions2[i]) {
					result = "Player 1 Wins!";
					break;
				} else if (positions1[i] < positions2[i]) {
					result = "Player 2 Wins!";
					break;
				} else
					continue;
			}

		}

		if (result.equals(""))
			result = "Tie";

		return result;

	}

	// The score method for determining a player's points in terms of hand type.
	// Using the different boolean methods, this method will generate an integer
	// score for the player depending on the type of hand he/she has.
	public static int score(String[] hand) {

		int points;

		if (isRoyFlush(hand) == true)
			points = 10;
		else if (isStFlush(hand) == true)
			points = 9;
		else if (is4Kind(hand) == true)
			points = 8;
		else if (isFullHouse(hand) == true)
			points = 7;
		else if (isFlush(hand) == true)
			points = 6;
		else if (isStraight(hand) == true)
			points = 5;
		else if (is3Kind(hand) == true)
			points = 4;
		else if (is2Pairs(hand) == true)
			points = 3;
		else if (isPair(hand, 0) == true)
			points = 2;
		else
			points = 1;

		return points;

	}

	// The findPositions method for finding index positions for the cards in a hand.
	// This method is used for finding orders and trends in a player's hand to
	// determine the possibilities of certain card combinations (ex. Straight)
	public static int[] findPositions(String[] hand) {

		int[] positions = new int[HANDSIZE];

		for (int i = 0; i < hand.length; i++) {
			String[] card = hand[i].split(" ");
			String face = card[0];
			for (int j = 0; j < faces.length; j++) {
				if (face.equals(faces[j]))
					positions[i] = j;
			}
		}

		return positions;

	}

	// The bubbleSort method for sorting position indices -
	// https://www.geeksforgeeks.org/bubble-sort/
	public static void bubbleSort(int[] positions) {

		int n = positions.length;

		for (int i = 0; i < n - 1; i++)
			for (int j = 0; j < n - i - 1; j++)
				if (positions[j] > positions[j + 1]) {
					int temp = positions[j];
					positions[j] = positions[j + 1];
					positions[j + 1] = temp;
				}

	}

	// The printCards method for printing all 52 cards in the deck. This method will
	// use the cards array passed through the parameters to print each element of
	// the array.
	public static void printCards(String[] cards) {

		for (int i = 0; i < cards.length; i++) {
			if (i == cards.length - 1)
				System.out.print(cards[i]);
			else
				System.out.print(cards[i] + ", ");
		}

	}

}