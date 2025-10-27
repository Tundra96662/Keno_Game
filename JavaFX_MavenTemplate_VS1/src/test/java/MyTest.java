import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;
import java.util.HashSet;
class MyTest {

	@Test
	void TenSpotGamePRIZE() {
		BetCard betCard = new BetCard();//fail("Not yet implemented");
		//betCard.selectedSpots = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		//betCard.winningSpots = {1,2,4,6,8,10}; //Doesnt need to be 20

		//Simulating the game by setting the amount of correctSpots that the card has
		betCard.maxSelectedSpots = 10;

		betCard.correctSpots = 0;
		assertEquals(5, betCard.scoreCalculation());
		
		betCard.correctSpots = 1;
		assertEquals(0, betCard.scoreCalculation());
		
		betCard.correctSpots = 2;
		assertEquals(0, betCard.scoreCalculation());
		
		betCard.correctSpots = 3;
		assertEquals(0, betCard.scoreCalculation());
		
		betCard.correctSpots = 4;
		assertEquals(0, betCard.scoreCalculation());
		
		betCard.correctSpots = 5;
		assertEquals(2, betCard.scoreCalculation());
		
		betCard.correctSpots = 8;
		assertEquals(450, betCard.scoreCalculation());
		
		betCard.correctSpots = 9;
		assertEquals(4250, betCard.scoreCalculation());
		
		betCard.correctSpots = 10;
		assertEquals(100000, betCard.scoreCalculation());
		

		
	}

	@Test
	void EightSpotGamePRIZE() {
		BetCard betCard = new BetCard();

		//Simulating the game by setting the amount of correctSpots that the card has
		betCard.maxSelectedSpots = 8;

		betCard.correctSpots = 0;
		assertEquals(0, betCard.scoreCalculation());
		
		betCard.correctSpots = 1;
		assertEquals(0, betCard.scoreCalculation());
		
		betCard.correctSpots = 2;
		assertEquals(0, betCard.scoreCalculation());
		
		betCard.correctSpots = 3;
		assertEquals(0, betCard.scoreCalculation());
		
		betCard.correctSpots = 4;
		assertEquals(2, betCard.scoreCalculation());
		
		betCard.correctSpots = 5;
		assertEquals(12, betCard.scoreCalculation());
		
		betCard.correctSpots = 6;
		assertEquals(50, betCard.scoreCalculation());
		
		betCard.correctSpots = 7;
		assertEquals(750, betCard.scoreCalculation());
		
		betCard.correctSpots = 8;
		assertEquals(10000, betCard.scoreCalculation());
		
	}

	@Test
	void FourSpotGamePRIZE() {
		BetCard betCard = new BetCard();

		//Simulating the game by setting the amount of correctSpots that the card has
		betCard.maxSelectedSpots = 4;

		betCard.correctSpots = 0;
		assertEquals(0, betCard.scoreCalculation());
		
		betCard.correctSpots = 1;
		assertEquals(0, betCard.scoreCalculation());
		
		betCard.correctSpots = 2;
		assertEquals(1, betCard.scoreCalculation());
		
		betCard.correctSpots = 3;
		assertEquals(5, betCard.scoreCalculation());
		
		betCard.correctSpots = 4;
		assertEquals(75, betCard.scoreCalculation());
	}

	@Test
	void OneSpotGamePRIZE() {
		BetCard betCard = new BetCard();

		//Simulating the game by setting the amount of correctSpots that the card has
		betCard.maxSelectedSpots = 1;

		betCard.correctSpots = 0;
		assertEquals(0, betCard.scoreCalculation());
		
		betCard.correctSpots = 1;
		assertEquals(2, betCard.scoreCalculation());
	}

	//resetting game for another go
	@Test
	void ResettingGame(){
		BetCard betCard = new BetCard();

		betCard.curDrawing = 4;
		betCard.selectedSpots.add(14);
		betCard.selectedSpots.add(18);
		betCard.selectedSpots.add(1);
		betCard.selectedSpots.add(4);
		betCard.selectedSpots.add(20);
		betCard.selectedSpots.add(24);
		
		//rest of info is irrelavent

		assertEquals(6, betCard.selectedSpots.size());
		assertEquals(4, betCard.curDrawing);

		betCard.resetGame();

		assertEquals(0, betCard.selectedSpots.size());
		assertEquals(1, betCard.curDrawing);

		//This is the only important information that needed to be resetted so the GUI can behave properly and present
		//correct information

	}
	
 @Test
	void TotalReward(){

		BetCard betCard = new BetCard();

		//Simulating the game by setting the amount of correctSpots that the card has
		betCard.maxSelectedSpots = 4;


		//Simulating going through each of the 20 spots and setting the rising total payoff

		betCard.correctSpots = 0;
		int f = betCard.scoreCalculation();
		
		betCard.correctSpots = 1;
		f += betCard.scoreCalculation();
		
		betCard.correctSpots = 2;
		f += betCard.scoreCalculation();
		
		betCard.correctSpots = 3;
		f += betCard.scoreCalculation();
		
		betCard.correctSpots = 4;
		f += betCard.scoreCalculation();

		assertEquals(81,f);
	}

	@Test
	void TestQP2(){

		BetCard betCard = new BetCard();
		betCard.maxSelectedSpots = 2;

		assertEquals(0, betCard.selectedSpots.size());

		betCard.quickPick();

		assertEquals(2, betCard.selectedSpots.size());
	}


	@Test
	void TestQP8(){

		BetCard betCard = new BetCard();
		betCard.maxSelectedSpots = 8;

		assertEquals(0, betCard.selectedSpots.size());

		betCard.quickPick();

		assertEquals(8, betCard.selectedSpots.size());
	}
	@Test
	void TestRESETQPMIX(){

		BetCard betCard = new BetCard();
		betCard.maxSelectedSpots = 2;

		assertEquals(0, betCard.selectedSpots.size());

		betCard.quickPick();

		assertEquals(2, betCard.selectedSpots.size());
		
		betCard.maxSelectedSpots = 8;

		betCard.quickPick();

		assertEquals(8, betCard.selectedSpots.size());
	}
	//selectnumber
	@Test
	void TestSelectNumber(){
		BetCard betCard = new BetCard();
		betCard.maxSelectedSpots = 4;
		betCard.selectNumber(1);
		betCard.selectNumber(2);
		betCard.selectNumber(3);
		betCard.selectNumber(4);

		Set<Integer> test = new HashSet();
		test.add(1);
		test.add(2);
		test.add(3);
		test.add(4);
		
		assertEquals(test, betCard.getSelectedSpots());
	}
	
	
	
	//deselectnumber
	@Test
	void TestDeselectNumber(){
		BetCard betCard = new BetCard();
		betCard.maxSelectedSpots = 4;
		betCard.selectNumber(1);
		betCard.selectNumber(2);
		betCard.selectNumber(3);
		betCard.selectNumber(4);

		Set<Integer> test = new HashSet();
		test.add(1);
		test.add(2);
		test.add(3);
		test.add(4);
		
		assertEquals(test, betCard.getSelectedSpots());

		betCard.selectNumber(2);
		betCard.selectNumber(3);

		test.remove(2);
		test.remove(3);

		assertEquals(test, betCard.getSelectedSpots());
	}
	
  @Test
	void TesGETSelectSPOTS(){
		BetCard betCard = new BetCard();
		betCard.maxSelectedSpots = 4;
		betCard.selectNumber(1);
		betCard.selectNumber(2);
		betCard.selectNumber(3);
		betCard.selectNumber(4);

		Set<Integer> test = new HashSet();
		test.add(1);
		test.add(2);
		test.add(3);
		test.add(4);
		
		assertEquals(test, betCard.getSelectedSpots());

		betCard.selectNumber(2);
		test.remove(2);

		assertEquals(test, betCard.getSelectedSpots());
		
		betCard.selectNumber(1);
		test.remove(1);

		assertEquals(test, betCard.getSelectedSpots());
		
		betCard.selectNumber(4);
		test.remove(4);

		assertEquals(test, betCard.getSelectedSpots());
		
		betCard.selectNumber(3);
		test.remove(3);

		assertEquals(test, betCard.getSelectedSpots());

		betCard.selectNumber(3);
		test.add(3);

		assertEquals(test, betCard.getSelectedSpots());


	}
	//clearSelectedSpots
	@Test
	void TestCLEARSelectSPOTS(){
		BetCard betCard = new BetCard();
		betCard.maxSelectedSpots = 4;
		betCard.selectNumber(1);
		betCard.selectNumber(2);
		betCard.selectNumber(3);
		betCard.selectNumber(4);

		Set<Integer> test = new HashSet();
		test.add(1);
		test.add(2);
		test.add(3);
		test.add(4);
		
		assertEquals(test, betCard.getSelectedSpots());
		
		test = new HashSet();

		betCard.clearSelectedSpots();

		assertEquals(test, betCard.getSelectedSpots());
	}









































}
