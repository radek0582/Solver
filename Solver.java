package main;

public class Solver {
	static String process(String line) {
		String output = "";
		String arrayOfStrings[] = line.split(" ");
		int players = -1;

		if (arrayOfStrings[0].equals("five-card-draw")) {
			players = arrayOfStrings.length - 1;
		} else
			players = arrayOfStrings.length - 2;

		int currentPlayer = 1;
		int playersCardsValues[] = new int[players];
		int playerTableScores[][] = new int[players][2];
		int sortedPlayerTable[][] = new int[players][2];
		String playerCards[] = new String[players];

		for (int i = 0; i < players; i++) {
			if (arrayOfStrings[0].equals("five-card-draw"))
				playerCards[i] = arrayOfStrings[i + 1];
			else
				playerCards[i] = arrayOfStrings[i + 2];
		}

		if (arrayOfStrings[0].equals("texas-holdem") || arrayOfStrings[0].equals("five-card-draw")
				|| arrayOfStrings[0].equals("omaha-holdem")) {
			while (currentPlayer <= players) {
				System.out.println("pl: " + currentPlayer);

				if (arrayOfStrings[0].equals("texas-holdem"))
					playersCardsValues[currentPlayer - 1] = calculateCardsValue(arrayOfStrings[1],
							arrayOfStrings[currentPlayer + 1]);
				else if (arrayOfStrings[0].equals("omaha-holdem")) {
					playersCardsValues[currentPlayer - 1] = calculateCardsValue(arrayOfStrings[1],
							arrayOfStrings[currentPlayer + 1]);
				} else
					playersCardsValues[currentPlayer - 1] = calculateCardsValue(arrayOfStrings[currentPlayer], "");

				playerTableScores[currentPlayer - 1][0] = playersCardsValues[currentPlayer - 1];
				playerTableScores[currentPlayer - 1][1] = currentPlayer;
				currentPlayer++;

			}

			int score = -1000000000;
			int id = -1;

			for (int j = players - 1; j >= 0; j--) {
				for (int i = 0; i < players; i++) {
					if (playerTableScores[i][0] > score) {
						score = playerTableScores[i][0];
						id = i;
					}
				}
				sortedPlayerTable[j][1] = playerTableScores[id][1];
				sortedPlayerTable[j][0] = playerTableScores[id][0];
				playerTableScores[id][0] = -2000000000;
				score = -1000000000;
				id = -1;
			}

			int order[] = new int[players];

			for (int i = 0; i < players; i++) {
				order[i] = sortedPlayerTable[i][1];
			}

			int countPlayers = 0;

			while (countPlayers < players) {
				String highestCard = "";
				String sign = " ";
				highestCard = playerCards[sortedPlayerTable[countPlayers][1] - 1];

				if (countPlayers == players - 1) {
					sign = "";
				} else if (sortedPlayerTable[countPlayers][0] == sortedPlayerTable[countPlayers + 1][0]) {
					sign = "=";
				}

				output += highestCard + sign;
				countPlayers++;
			}
		} else
			throw new UnsupportedOperationException("Not implemented yet...");

		return output;
	}

	static int calculateCardsValue(String tableCards, String playerCards) {
		int value = 0;
		int fourOfAKind = -1;
		int threeOfAKind = -1;
		int pair1 = -1;
		int pair2 = -1;
		char flushColor = 0;
		int fullHouse = -1;
		int fullHouseHigherPair = -1;
		int highestCard = -1;
		int pairs = 0;
		int straight = -2;
		String allCards = tableCards + playerCards;
		int currentCard = 0;
		int amountOf_Figure[] = new int[13];
		char dominantColorChar = 0;
		int dominantColor[] = new int[4];
		int poker = -2;

		for (; currentCard < allCards.length(); currentCard++) {
			if (allCards.charAt(currentCard) == 'h') // 0 for 'h'earts, 1 for 'd'iamonds, 2 for 'c'lubc, 3 for 's'pades,
				dominantColor[0]++;
			else if (allCards.charAt(currentCard) == 'd')
				dominantColor[1]++;
			else if (allCards.charAt(currentCard) == 'c')
				dominantColor[2]++;
			else if (allCards.charAt(currentCard) == 's') {
				dominantColor[3]++;
			}
		}

		for (int color = 0; color < 4; color++) {
			if (dominantColor[color] > 4) {

				if (color == 0)
					flushColor = 'h';
				else if (color == 1)
					flushColor = 'd';
				else if (color == 2)
					flushColor = 'c';
				else if (color == 3)
					flushColor = 's';
			}
		}

		currentCard = 0;

		while (currentCard < allCards.length()) {
			if (allCards.charAt(currentCard) == '2')
				amountOf_Figure[0]++;
			else if (allCards.charAt(currentCard) == '3')
				amountOf_Figure[1]++;
			else if (allCards.charAt(currentCard) == '4')
				amountOf_Figure[2]++;
			else if (allCards.charAt(currentCard) == '5')
				amountOf_Figure[3]++;
			else if (allCards.charAt(currentCard) == '6')
				amountOf_Figure[4]++;
			else if (allCards.charAt(currentCard) == '7')
				amountOf_Figure[5]++;
			else if (allCards.charAt(currentCard) == '8')
				amountOf_Figure[6]++;
			else if (allCards.charAt(currentCard) == '9')
				amountOf_Figure[7]++;
			else if (allCards.charAt(currentCard) == 'T')
				amountOf_Figure[8]++;
			else if (allCards.charAt(currentCard) == 'J')
				amountOf_Figure[9]++;
			else if (allCards.charAt(currentCard) == 'Q')
				amountOf_Figure[10]++;
			else if (allCards.charAt(currentCard) == 'K')
				amountOf_Figure[11]++;
			else if (allCards.charAt(currentCard) == 'A')
				amountOf_Figure[12]++;

			currentCard++;
		}

		int variousCard = 0;

		for (int i = 0; i < 13; i++) { // count card other than before
			if (amountOf_Figure[i] > 0)
				variousCard++;
		}

		if (variousCard >= 5) // possible Straight or Poker
		{
			System.out.println("pok");

			if (amountOf_Figure[12] > 0 && amountOf_Figure[0] > 0 && amountOf_Figure[1] > 0 && amountOf_Figure[2] > 0
					&& amountOf_Figure[3] > 0)
				straight = -1;

			if (amountOf_Figure[0] > 0 && amountOf_Figure[1] > 0 && amountOf_Figure[2] > 0 && amountOf_Figure[3] > 0
					&& amountOf_Figure[4] > 0)
				straight = 0;

			if (amountOf_Figure[1] > 0 && amountOf_Figure[2] > 0 && amountOf_Figure[3] > 0 && amountOf_Figure[4] > 0
					&& amountOf_Figure[5] > 0)
				straight = 1;

			if (amountOf_Figure[2] > 0 && amountOf_Figure[3] > 0 && amountOf_Figure[4] > 0 && amountOf_Figure[5] > 0
					&& amountOf_Figure[6] > 0)
				straight = 2;

			if (amountOf_Figure[3] > 0 && amountOf_Figure[4] > 0 && amountOf_Figure[5] > 0 && amountOf_Figure[6] > 0
					&& amountOf_Figure[7] > 0)
				straight = 3;

			if (amountOf_Figure[4] > 0 && amountOf_Figure[5] > 0 && amountOf_Figure[6] > 0 && amountOf_Figure[7] > 0
					&& amountOf_Figure[8] > 0)
				straight = 4;

			if (amountOf_Figure[5] > 0 && amountOf_Figure[6] > 0 && amountOf_Figure[7] > 0 && amountOf_Figure[8] > 0
					&& amountOf_Figure[9] > 0)
				straight = 5;

			if (amountOf_Figure[6] > 0 && amountOf_Figure[7] > 0 && amountOf_Figure[8] > 0 && amountOf_Figure[9] > 0
					&& amountOf_Figure[10] > 0)
				straight = 6;

			if (amountOf_Figure[7] > 0 && amountOf_Figure[8] > 0 && amountOf_Figure[9] > 0 && amountOf_Figure[10] > 0
					&& amountOf_Figure[11] > 0)
				straight = 7;

			if (amountOf_Figure[8] > 0 && amountOf_Figure[9] > 0 && amountOf_Figure[10] > 0 && amountOf_Figure[11] > 0
					&& amountOf_Figure[12] > 0)
				straight = 8;
		}

		if (straight > -2) { // check if not Poker

			for (int i = 0; i < 4; i++) {
				if (dominantColor[i] > 4) {// possible Poker

					if (i == 0)
						dominantColorChar = 'h';
					else if (i == 1)
						dominantColorChar = 'd';
					else if (i == 2)
						dominantColorChar = 'c';
					else if (i == 3)
						dominantColorChar = 's';

					break;
				}
			}
		}

		if (dominantColorChar != 0) {
			poker = searchForPlayerPoker(allCards, dominantColorChar);
		}

		if (poker == -2 && straight == -2) {

			for (int i = 0; i < 13; i++) {
				if (amountOf_Figure[i] > 3) // Four of a kind
					fourOfAKind = i;
			}

			if (fourOfAKind == -1) {
				int higherThree = -1;

				for (int i = 0; i < 13; i++) {
					if (amountOf_Figure[i] > 2) { // Three of a kind
						if (higherThree < i)
							higherThree = i;
					}
				}

				if (higherThree >= 0) {
					int higherPair = checkFullHigherPair(allCards, amountOf_Figure, higherThree); // higher three to
					// exclude

					if (higherPair == -1)
						threeOfAKind = higherThree;
					else {
						fullHouse = higherThree;
						fullHouseHigherPair = higherPair;
					}
				}
			}

			if (fourOfAKind == -1) // no four of a kind
				if (threeOfAKind == -1) { // no three of a kind
					for (int i = 0; i < 13; i++) {
						if (amountOf_Figure[i] == 2)
							pairs++;
					}
				}

			if (fourOfAKind >= 0) {
				highestCard = checkForHighestCard(amountOf_Figure, fourOfAKind, -1, -1);

				value = 50000000 + fourOfAKind * 1000 + highestCard;
			}

			if (threeOfAKind >= 0) {
				highestCard = checkForHighestCard2(amountOf_Figure, -1);
				value = 1000000 + threeOfAKind * 400 + highestCard * 20;
				highestCard = checkForHighestCard2(amountOf_Figure, highestCard);
				value += highestCard;
			}

			if (fourOfAKind == -1 && threeOfAKind == -1 && pairs > 0) {

				if (pairs == 3 || pairs == 2) {
					pair1 = checkForHighestPair(amountOf_Figure, -1, -1);
					pair2 = checkForHighestPair(amountOf_Figure, pair1, -1);
					highestCard = checkForHighestCard(amountOf_Figure, pair1, pair2, -1);
					value = 500000 + pair1 * 10000 + pair2 * 100 + highestCard;
				}

				if (pairs == 1) {
					pair1 = checkForHighestPair(amountOf_Figure, -1, -1);
					int highestCard1 = checkForHighestCard(amountOf_Figure, pair1, -1, -1);
					value = -1000 + pair1 * 10000 + highestCard1 * 400;
					int highestCard2 = checkForHighestCard(amountOf_Figure, pair1, highestCard1, -1);
					value += highestCard2 * 20;
					int highestCard3 = checkForHighestCard(amountOf_Figure, pair1, highestCard1, highestCard2);
					value += highestCard3;
				}
			}

		} else {
			value = 100000000 + poker + 1;
		}

		if (poker == -2 && flushColor != 0 && fullHouse == -1) {
			String flushCards = new String();
			System.out.println("kolor");

			for (int i = 0; i < allCards.length(); i++) {
				if (flushColor == allCards.charAt(i))
					flushCards += allCards.charAt(i - 1);
			}

			int highestCard1 = calculateFlush(flushCards, amountOf_Figure, flushColor, -1, -1, -1, -1);
			value = 7000000 + highestCard1 * 15 * 15 * 15 * 15;
			int highestCard2 = calculateFlush(flushCards, amountOf_Figure, flushColor, highestCard1, -1, -1, -1);
			value += highestCard2 * 15 * 15 * 15;
			int highestCard3 = calculateFlush(flushCards, amountOf_Figure, flushColor, highestCard1, highestCard2, -1,
					-1);
			value += highestCard3 * 15 * 15;
			int highestCard4 = calculateFlush(flushCards, amountOf_Figure, flushColor, highestCard1, highestCard2,
					highestCard3, -1);
			value += highestCard4 * 15;
			int highestCard5 = calculateFlush(flushCards, amountOf_Figure, flushColor, highestCard1, highestCard2,
					highestCard3, highestCard4);
			value += highestCard5;
		}

		if (straight >= -1 && poker == -2 && flushColor == 0) {
			System.out.println("street");
			value = 5000000 + straight + 1;
		}

		if (fullHouse >= 0) {
			value = 10000000 + fullHouse * 1000 + fullHouseHigherPair;
		}

		if (poker == -2 && straight == -2 && pairs == 0 && fullHouse == -1 && threeOfAKind == -1 && fourOfAKind == -1
				&& flushColor == 0) {

			int highestCard1 = calculateCard(amountOf_Figure, -1, -1, -1, -1);
			value = -1000000 + highestCard1 * 15 * 15 * 15 * 15;
			int highestCard2 = calculateCard(amountOf_Figure, highestCard1, -1, -1, -1);
			value += highestCard2 * 15 * 15 * 15;
			int highestCard3 = calculateCard(amountOf_Figure, highestCard1, highestCard2, -1, -1);
			value += highestCard3 * 15 * 15;
			int highestCard4 = calculateCard(amountOf_Figure, highestCard1, highestCard2, highestCard3, -1);
			value += highestCard4 * 15;
			int highestCard5 = calculateCard(amountOf_Figure, highestCard1, highestCard2, highestCard3, highestCard4);
			value += highestCard5;
		}
		return value;
	}

	private static int calculateCard(int amountOfFigure[], int excl1, int excl2, int excl3, int excl4) {
		int value = 0;

		for (int i = 0; i < amountOfFigure.length; i++) {
			if (amountOfFigure[i] == 1 && i != excl1 && i != excl2 && i != excl3 && i != excl4) {
				value = i;

			}
		}
		return value;
	}

	private static int calculateFlush(String flushCards, int amountOffigure[], char flushColor, int excl1, int excl2,
			int excl3, int excl4) {
		int flushValue = -1;
		excl1 -= 1;
		excl2 -= 1;
		excl3 -= 1;
		excl4 -= 1;

//		System.out.println(amountOffigure[0] + " " + checkFlushCard(flushCards, '2'));

		for (int i = amountOffigure.length - 1; i >= 0; i--) {
			if (i == 12 && amountOffigure[12] > 0 && checkFlushCard(flushCards, 'A') == true && i != excl1 && i != excl2
					&& i != excl3 && i != excl4) {
				flushValue = 13;
				break;
			}

			if (i == 11 && amountOffigure[11] > 0 && checkFlushCard(flushCards, 'K') == true && i != excl1 && i != excl2
					&& i != excl3 && i != excl4) {
				flushValue = 12;
				break;
			}

			if (i == 10 && amountOffigure[10] > 0 && checkFlushCard(flushCards, 'Q') == true && i != excl1 && i != excl2
					&& i != excl3 && i != excl4) {
				flushValue = 11;
				break;
			}

			if (i == 9 && amountOffigure[9] > 0 && checkFlushCard(flushCards, 'J') == true && i != excl1 && i != excl2
					&& i != excl3 && i != excl4) {
				flushValue = 10;
				break;
			}

			if (i == 8 && amountOffigure[8] > 0 && checkFlushCard(flushCards, 'T') == true && i != excl1 && i != excl2
					&& i != excl3 && i != excl4) {
				flushValue = 9;
				break;
			}

			if (i == 7 && amountOffigure[7] > 0 && checkFlushCard(flushCards, '9') == true && i != excl1 && i != excl2
					&& i != excl3 && i != excl4) {
				flushValue = 8;
				break;
			}

			if (i == 6 && amountOffigure[6] > 0 && checkFlushCard(flushCards, '8') == true && i != excl1 && i != excl2
					&& i != excl3 && i != excl4) {
				flushValue = 7;
				break;
			}

			if (i == 5 && amountOffigure[5] > 0 && checkFlushCard(flushCards, '7') == true && i != excl1 && i != excl2
					&& i != excl3 && i != excl4) {
				flushValue = 6;
				break;
			}

			if (i == 4 && amountOffigure[4] > 0 && checkFlushCard(flushCards, '6') == true && i != excl1 && i != excl2
					&& i != excl3 && i != excl4) {
				flushValue = 5;
				break;
			}

			if (i == 3 && amountOffigure[3] > 0 && checkFlushCard(flushCards, '5') == true && i != excl1 && i != excl2
					&& i != excl3 && i != excl4) {
				flushValue = 4;
				break;
			}

			if (i == 2 && amountOffigure[2] > 0 && checkFlushCard(flushCards, '4') == true && i != excl1 && i != excl2
					&& i != excl3 && i != excl4) {
				flushValue = 3;
				break;
			}

			if (i == 1 && amountOffigure[1] > 0 && checkFlushCard(flushCards, '3') == true && i != excl1 && i != excl2
					&& i != excl3 && i != excl4) {
				flushValue = 2;
				break;
			}

			if (i == 0 && amountOffigure[0] > 0 && checkFlushCard(flushCards, '2') == true && i != excl1 && i != excl2
					&& i != excl3 && i != excl4) {
				flushValue = 1;
				break;
			}
		}
		return flushValue;
	}

	private static boolean checkFlushCard(String flushCards, char c) {
		boolean flushCard = false;

		for (int i = 0; i < flushCards.length(); i++) {
			if (flushCards.charAt(i) == c)
				return true;
		}
		return false;
	}

	private static int checkForHighestPair(int[] amountOf_Figure, int excluded1, int excluded2) {
		int pair = -1;

		for (int i = 0; i < 13; i++) {
			if (amountOf_Figure[i] == 2 && i != excluded1 && i != excluded2)
				pair = i;
		}
		return pair;
	}

	private static int checkForHighestCard(int[] amountOfFigure, int excluded1, int excluded2, int excluded3) {
		int highestCard = -1;

		for (int i = 0; i < 13; i++) {
			if (amountOfFigure[i] > 0 && excluded1 != i && excluded2 != i && excluded3 != i)
				if (i > highestCard)
					highestCard = i;
		}
		return highestCard;
	}

	private static int checkForHighestCard2(int[] amountOfFigure, int excludedCard) {
		int highestCard = -1;

		for (int i = 0; i < 13; i++) {
			if (amountOfFigure[i] < 3 && i != excludedCard)
				if (i > highestCard)
					highestCard = i;
		}

		return highestCard;
	}

	static int checkFullHigherPair(String allCards, int[] amountOfFigure, int higherThree) {
		int higherPair = -1;

		for (int i = 0; i < 13; i++) {
			if (amountOfFigure[i] >= 2 && i != higherThree)
				if (i > higherPair)
					higherPair = i;
		}

		return higherPair;
	}

	static int searchForPlayerPoker(String allCards, char color) {
		String playerOneColorCards = "";
		int poker = -2;

		for (int i = 0; i < allCards.length(); i++) {
			if (allCards.charAt(i) == color)
				playerOneColorCards += allCards.charAt(i - 1);
		}

		int cardsForPoker[] = new int[13];

		for (int i = 0; i < playerOneColorCards.length(); i++) {
			if (playerOneColorCards.charAt(i) == '2')
				cardsForPoker[0]++;
			else if (playerOneColorCards.charAt(i) == '3')
				cardsForPoker[1]++;
			else if (playerOneColorCards.charAt(i) == '4')
				cardsForPoker[2]++;
			else if (playerOneColorCards.charAt(i) == '5')
				cardsForPoker[3]++;
			else if (playerOneColorCards.charAt(i) == '6')
				cardsForPoker[4]++;
			else if (playerOneColorCards.charAt(i) == '7')
				cardsForPoker[5]++;
			else if (playerOneColorCards.charAt(i) == '8')
				cardsForPoker[6]++;
			else if (playerOneColorCards.charAt(i) == '9')
				cardsForPoker[7]++;
			else if (playerOneColorCards.charAt(i) == 'T')
				cardsForPoker[8]++;
			else if (playerOneColorCards.charAt(i) == 'J')
				cardsForPoker[9]++;
			else if (playerOneColorCards.charAt(i) == 'Q')
				cardsForPoker[10]++;
			else if (playerOneColorCards.charAt(i) == 'K')
				cardsForPoker[11]++;
			else if (playerOneColorCards.charAt(i) == 'A')
				cardsForPoker[12]++;

		}
		if (cardsForPoker[12] > 0 && cardsForPoker[0] > 0 && cardsForPoker[1] > 0 && cardsForPoker[2] > 0
				&& cardsForPoker[3] > 0)
			poker = -1;

		if (cardsForPoker[0] > 0 && cardsForPoker[1] > 0 && cardsForPoker[2] > 0 && cardsForPoker[3] > 0
				&& cardsForPoker[4] > 0)
			poker = 0;

		if (cardsForPoker[1] > 0 && cardsForPoker[2] > 0 && cardsForPoker[3] > 0 && cardsForPoker[4] > 0
				&& cardsForPoker[5] > 0)
			poker = 1;

		if (cardsForPoker[2] > 0 && cardsForPoker[3] > 0 && cardsForPoker[4] > 0 && cardsForPoker[5] > 0
				&& cardsForPoker[6] > 0)
			poker = 2;

		if (cardsForPoker[3] > 0 && cardsForPoker[4] > 0 && cardsForPoker[5] > 0 && cardsForPoker[6] > 0
				&& cardsForPoker[7] > 0)
			poker = 3;

		if (cardsForPoker[4] > 0 && cardsForPoker[5] > 0 && cardsForPoker[6] > 0 && cardsForPoker[7] > 0
				&& cardsForPoker[8] > 0)
			poker = 4;

		if (cardsForPoker[5] > 0 && cardsForPoker[6] > 0 && cardsForPoker[7] > 0 && cardsForPoker[8] > 0
				&& cardsForPoker[9] > 0)
			poker = 5;

		if (cardsForPoker[6] > 0 && cardsForPoker[7] > 0 && cardsForPoker[8] > 0 && cardsForPoker[9] > 0
				&& cardsForPoker[10] > 0)
			poker = 6;

		if (cardsForPoker[7] > 0 && cardsForPoker[8] > 0 && cardsForPoker[9] > 0 && cardsForPoker[10] > 0
				&& cardsForPoker[11] > 0)
			poker = 7;

		if (cardsForPoker[8] > 0 && cardsForPoker[9] > 0 && cardsForPoker[10] > 0 && cardsForPoker[11] > 0
				&& cardsForPoker[12] > 0)
			poker = 8;

		return poker;
	}
}
