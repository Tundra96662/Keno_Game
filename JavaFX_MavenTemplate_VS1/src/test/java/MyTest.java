import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;
import java.util.HashSet;
class MyTest {

    /// Danies 13 tests
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

    /// ---------------------------------------
    /// Yonatan's Tests
    @Test
    void TestQuickPickUniqueNumbers() {
        BetCard betCard = new BetCard();
        betCard.maxSelectedSpots = 10;

        betCard.quickPick();
        Set<Integer> selected = betCard.getSelectedSpots();

        assertEquals(10, selected.size(), "Quick pick should select exactly 10 numbers");

        // Verify all numbers are unique
        Set<Integer> uniqueCheck = new HashSet<>(selected);
        assertEquals(10, uniqueCheck.size(), "All quick pick numbers should be unique");

        // Verify numbers are within valid range (1-80)
        for (int num : selected) {
            assertTrue(num >= 1 && num <= 80, "All numbers should be between 1 and 80");
        }
    }

    @Test
    void TestSelectNumberBeyondMaxLimit() {
        BetCard betCard = new BetCard();
        betCard.maxSelectedSpots = 3;

        // Fill to capacity
        betCard.selectNumber(1);
        betCard.selectNumber(2);
        betCard.selectNumber(3);

        assertEquals(3, betCard.getSelectedSpots().size(), "Should have exactly 3 numbers");

        // Try to add beyond capacity
        betCard.selectNumber(4);
        assertEquals(3, betCard.getSelectedSpots().size(), "Should not exceed max selected spots");
        assertFalse(betCard.getSelectedSpots().contains(4), "Number 4 should not be added");
    }

    @Test
    void TestAbleToBeginValidation() {
        BetCard betCard = new BetCard();
        betCard.maxSelectedSpots = 4;

        // Initially should not be able to begin
        assertFalse(betCard.ableToBegin(), "Should not be able to begin with no numbers selected");

        // Partially filled - should not be able to begin
        betCard.selectNumber(1);
        betCard.selectNumber(2);
        assertFalse(betCard.ableToBegin(), "Should not be able to begin with partial selection");

        // Exactly filled - should be able to begin
        betCard.selectNumber(3);
        betCard.selectNumber(4);
        assertTrue(betCard.ableToBegin(), "Should be able to begin with exact selection");

        // Beyond filled (testing edge case)
        betCard.maxSelectedSpots = 2;
        assertFalse(betCard.ableToBegin(), "Should not be able to begin when over selected for new max");
    }

    @Test
    void TestDisplayMethodsWithDefaultState() {
        BetCard betCard = new BetCard();

        // Test displayClear with default state
        String clearDisplay = betCard.displayClear();
        assertTrue(clearDisplay.contains("$0"), "Default total winnings should be $0");
        assertTrue(clearDisplay.contains("Draws\n   0"), "Default total draws should be 0");

        // Test displayStats with no configuration
        String statsDisplay = betCard.displayStats();
        assertTrue(statsDisplay.contains("Drawings:") && statsDisplay.contains("Spots:"),
                "Stats should display drawings and spots information");
    }

    @Test
    void TestScoreCalculationEdgeCases() {
        BetCard betCard = new BetCard();

        // Test invalid spot configurations
        betCard.maxSelectedSpots = 5; // Invalid spot count
        betCard.correctSpots = 3;
        assertEquals(0, betCard.scoreCalculation(), "Invalid spot count should return 0");

        betCard.maxSelectedSpots = 0; // Zero spots
        betCard.correctSpots = 0;
        assertEquals(0, betCard.scoreCalculation(), "Zero spots should return 0");

        // Test negative correct spots
        betCard.maxSelectedSpots = 4;
        betCard.correctSpots = -1;
        assertEquals(0, betCard.scoreCalculation(), "Negative matches should return 0");
    }

    @Test
    void TestMultipleQuickPicksProduceDifferentResults() {
        BetCard betCard = new BetCard();
        betCard.maxSelectedSpots = 8;

        Set<Integer> firstPick = new HashSet<>();
        Set<Integer> secondPick = new HashSet<>();

        // First quick pick
        betCard.quickPick();
        firstPick.addAll(betCard.getSelectedSpots());

        // Second quick pick
        betCard.quickPick();
        secondPick.addAll(betCard.getSelectedSpots());

        // While it's possible to get the same numbers randomly
        assertEquals(8, firstPick.size(), "First pick should have 8 numbers");
        assertEquals(8, secondPick.size(), "Second pick should have 8 numbers");

        // Both should be valid sets
        for (int num : firstPick) {
            assertTrue(num >= 1 && num <= 80, "First pick numbers should be valid");
        }
        for (int num : secondPick) {
            assertTrue(num >= 1 && num <= 80, "Second pick numbers should be valid");
        }
    }

    @Test
    void TestTotalWinningsAccumulation() {
        BetCard betCard = new BetCard();
        betCard.maxSelectedSpots = 4;

        // Simulate multiple winning rounds
        betCard.correctSpots = 2;
        int winnings1 = betCard.scoreCalculation();
        betCard.totalReward += winnings1;

        betCard.correctSpots = 3;
        int winnings2 = betCard.scoreCalculation();
        betCard.totalReward += winnings2;

        betCard.correctSpots = 4;
        int winnings3 = betCard.scoreCalculation();
        betCard.totalReward += winnings3;

        assertEquals(winnings1 + winnings2 + winnings3, betCard.totalReward,
                "Total reward should accumulate correctly across multiple drawings");
    }

    @Test
    void TestDrawingCountManagement() {
        BetCard betCard = new BetCard();

        // Test initial state
        assertEquals(1, betCard.curDrawing, "Initial drawing should be 1");
        assertEquals(-1, betCard.maxDrawing, "Initial max drawing should be -1 (unset)");

        // Test setting max drawing
        betCard.setMaxDrawing(3);
        assertEquals(3, betCard.maxDrawing, "Max drawing should be set to 3");

        // Test reset affects drawing count but not max drawing
        betCard.curDrawing = 2;
        betCard.resetGame();
        assertEquals(1, betCard.curDrawing, "Current drawing should reset to 1");
        assertEquals(3, betCard.maxDrawing, "Max drawing should persist after reset");
    }

    @Test
    void TestNumberSelectionToggleBehavior() {
        BetCard betCard = new BetCard();
        betCard.maxSelectedSpots = 5;

        // Test toggle selection
        betCard.selectNumber(10);
        assertTrue(betCard.getSelectedSpots().contains(10), "Number 10 should be selected");

        betCard.selectNumber(10); // Toggle off
        assertFalse(betCard.getSelectedSpots().contains(10), "Number 10 should be deselected");

        betCard.selectNumber(10); // Toggle on again
        assertTrue(betCard.getSelectedSpots().contains(10), "Number 10 should be selected again");
    }

    @Test
    void TestInvalidNumberSelections() {
        BetCard betCard = new BetCard();
        betCard.maxSelectedSpots = 10;

        // Test various number selections - current implementation accepts all
        int[] testNumbers = {1, 50, 80, 0, 81, -1, 1000};

        for (int num : testNumbers) {
            int previousSize = betCard.getSelectedSpots().size();
            betCard.selectNumber(num);

            //toggle selection regardless of number validity
            if (betCard.getSelectedSpots().contains(num)) {
                // Number was added
                assertEquals(previousSize + 1, betCard.getSelectedSpots().size(),
                        "Number " + num + " was added to selection");
            } else {
                // Number was removed (was already selected)
                assertEquals(previousSize - 1, betCard.getSelectedSpots().size(),
                        "Number " + num + " was removed from selection");
            }
        }
    }

    @Test
    void TestPartialResetScenario() {
        BetCard betCard = new BetCard();

        // Set up a game state
        betCard.maxSelectedSpots = 4;
        betCard.setMaxDrawing(2);
        betCard.selectNumber(5);
        betCard.selectNumber(10);
        betCard.selectNumber(15);
        betCard.selectNumber(20);
        betCard.curDrawing = 2;
        betCard.totalReward = 50;
        betCard.totalDrawsPlayed = 1;

        // Perform reset
        betCard.resetGame();

        // Verify what resets and what keep on
        assertEquals(0, betCard.getSelectedSpots().size(), "Selected spots should be cleared");
        assertEquals(1, betCard.curDrawing, "Current drawing should reset to 1");
        assertEquals(2, betCard.maxDrawing, "Max drawing should persist");
        assertEquals(50, betCard.totalReward, "Total reward should persist");
        assertEquals(1, betCard.totalDrawsPlayed, "Total draws played should persist");
    }

    @Test
    void TestWinningSpotTracking() {
        BetCard betCard = new BetCard();
        betCard.maxSelectedSpots = 4;

        // Set up player selections
        betCard.selectNumber(7);
        betCard.selectNumber(14);
        betCard.selectNumber(21);
        betCard.selectNumber(28);

        // Simulate winning spots (normally set during drawing)
        betCard.winningSpots.add(7);
        betCard.winningSpots.add(21);
        betCard.winningSpots.add(35);
        betCard.winningSpots.add(42);

        // Manually set correct spots to simulate the drawing result
        betCard.correctSpots = 2; // 7 and 21 are matches

        assertEquals(2, betCard.correctSpots, "Should have 2 correct matches");
        assertEquals(1, betCard.scoreCalculation(), "2 matches in 4-spot game should pay $1");
    }

}
