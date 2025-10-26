import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Keno {
    private Player player;
    private GameState currState;
    private List<Drawing> drawings;
    private double totalWinnings;
    private int currentDrawingIndex;
    private Random random;

    public Keno() {
        this.player = new Player();
        this.currState = GameState.WELCOME;
        this.drawings = new ArrayList<>();
        this.totalWinnings = 0.0;
        this.currentDrawingIndex = -1;
        this.random = new Random();
    }

    // Enum for game states
    public enum GameState {
        WELCOME,
        BETTING,
        DRAWING_IN_PROGRESS,
        DRAWING_COMPLETE,
        GAME_OVER
    }

    // Inner class for Drawing
    public class Drawing {
        private List<Integer> drawnNumbers;
        private List<Integer> playerNumbers;
        private int spotsPlayed;
        private double winnings;
        private int matches;

        public Drawing(List<Integer> playerNumbers, int spotsPlayed) {
            this.playerNumbers = new ArrayList<>(playerNumbers);
            this.spotsPlayed = spotsPlayed;
            this.drawnNumbers = new ArrayList<>();
            this.winnings = 0.0;
            this.matches = 0;
        }

        public void conductDrawing() {
            drawnNumbers.clear();
            // Draw 20 unique random numbers between 1-80
            while (drawnNumbers.size() < 20) {
                int num = random.nextInt(80) + 1; // Now using the instance random
                if (!drawnNumbers.contains(num)) {
                    drawnNumbers.add(num);
                }
            }
            Collections.sort(drawnNumbers);

            // Calculate matches
            calculateMatches();

            // Calculate winnings
            calculateWinnings();
        }

        private void calculateMatches() {
            matches = 0;
            for (int num : playerNumbers) {
                if (drawnNumbers.contains(num)) {
                    matches++;
                }
            }
        }

        private void calculateWinnings() {
            // Based on North Carolina Lottery Keno odds
            switch (spotsPlayed) {
                case 1:
                    if (matches == 1) {
                        winnings = 2.0; // $2 for matching 1 spot
                    }
                    break;
                case 4:
                    if (matches == 2) {
                        winnings = 1.0; // $1 for matching 2 spots
                    } else if (matches == 3) {
                        winnings = 5.0; // $5 for matching 3 spots
                    } else if (matches == 4) {
                        winnings = 75.0; // $75 for matching all 4 spots
                    }
                    break;
                case 8:
                    if (matches == 4) {
                        winnings = 2.0; // $2 for matching 4 spots
                    } else if (matches == 5) {
                        winnings = 12.0; // $12 for matching 5 spots
                    } else if (matches == 6) {
                        winnings = 50.0; // $50 for matching 6 spots
                    } else if (matches == 7) {
                        winnings = 750.0; // $750 for matching 7 spots
                    } else if (matches == 8) {
                        winnings = 10000.0; // $10,000 for matching all 8 spots
                    }
                    break;
                case 10:
                    if (matches == 0) {
                        winnings = 5.0; // $5 for matching 0 spots (special case)
                    } else if (matches == 5) {
                        winnings = 2.0; // $2 for matching 5 spots
                    } else if (matches == 6) {
                        winnings = 15.0; // $15 for matching 6 spots
                    } else if (matches == 7) {
                        winnings = 100.0; // $100 for matching 7 spots
                    } else if (matches == 8) {
                        winnings = 1000.0; // $1,000 for matching 8 spots
                    } else if (matches == 9) {
                        winnings = 5000.0; // $5,000 for matching 9 spots
                    } else if (matches == 10) {
                        winnings = 100000.0; // $100,000 for matching all 10 spots
                    }
                    break;
            }
        }

        // Getters
        public List<Integer> getDrawnNumbers() { return drawnNumbers; }
        public List<Integer> getPlayerNumbers() { return playerNumbers; }
        public int getSpotsPlayed() { return spotsPlayed; }
        public double getWinnings() { return winnings; }
        public int getMatches() { return matches; }
        public List<Integer> getMatchedNumbers() {
            List<Integer> matched = new ArrayList<>();
            for (int num : playerNumbers) {
                if (drawnNumbers.contains(num)) {
                    matched.add(num);
                }
            }
            return matched;
        }
    }

    // Inner class for Player
    public class Player {
        private List<Integer> selectedNumbers;
        private int spotsToPlay;
        private int drawingsToPlay;
        private int currentDrawingCount;

        public Player() {
            this.selectedNumbers = new ArrayList<>();
            this.spotsToPlay = 0;
            this.drawingsToPlay = 1;
            this.currentDrawingCount = 0;
        }

        public void reset() {
            selectedNumbers.clear();
            spotsToPlay = 0;
            drawingsToPlay = 1;
            currentDrawingCount = 0;
        }

        public void addNumber(int number) {
            if (!selectedNumbers.contains(number) && selectedNumbers.size() < spotsToPlay) {
                selectedNumbers.add(number);
            }
        }

        public void removeNumber(int number) {
            selectedNumbers.remove(Integer.valueOf(number));
        }

        public void clearNumbers() {
            selectedNumbers.clear();
        }

        public void generateQuickPick() {
            clearNumbers();
            List<Integer> allNumbers = new ArrayList<>();
            for (int i = 1; i <= 80; i++) {
                allNumbers.add(i);
            }
            Collections.shuffle(allNumbers, random); // Use the instance random
            for (int i = 0; i < spotsToPlay; i++) {
                selectedNumbers.add(allNumbers.get(i));
            }
            Collections.sort(selectedNumbers);
        }

        // Getters and Setters
        public List<Integer> getSelectedNumbers() { return selectedNumbers; }
        public int getSpotsToPlay() { return spotsToPlay; }
        public void setSpotsToPlay(int spots) { this.spotsToPlay = spots; }
        public int getDrawingsToPlay() { return drawingsToPlay; }
        public void setDrawingsToPlay(int drawings) { this.drawingsToPlay = drawings; }
        public int getCurrentDrawingCount() { return currentDrawingCount; }
        public void incrementDrawingCount() { this.currentDrawingCount++; }
        public boolean isBettingComplete() {
            return selectedNumbers.size() == spotsToPlay && spotsToPlay > 0;
        }
    }

    // Main Keno class methods
    public void startNewGame() {
        player.reset();
        drawings.clear();
        totalWinnings = 0.0;
        currentDrawingIndex = -1;
        currState = GameState.BETTING;
    }

    public void setupBet(int spots, int numDrawings, List<Integer> numbers) {
        player.setSpotsToPlay(spots);
        player.setDrawingsToPlay(numDrawings);
        player.clearNumbers();
        for (int num : numbers) {
            player.addNumber(num);
        }

        // Create drawings for all rounds
        drawings.clear();
        for (int i = 0; i < numDrawings; i++) {
            drawings.add(new Drawing(player.getSelectedNumbers(), spots));
        }
    }

    public void conductDrawing() {
        if (currentDrawingIndex < drawings.size() - 1) {
            currentDrawingIndex++;
            Drawing currentDrawing = drawings.get(currentDrawingIndex);
            currentDrawing.conductDrawing();
            totalWinnings += currentDrawing.getWinnings();
            player.incrementDrawingCount();

            if (currentDrawingIndex == drawings.size() - 1) {
                currState = GameState.GAME_OVER;
            } else {
                currState = GameState.DRAWING_COMPLETE;
            }
        }
    }

    public double calculateWinnings() {
        return totalWinnings;
    }

    public boolean hasMoreDrawings() {
        return currentDrawingIndex < drawings.size() - 1;
    }

    public Drawing getCurrentDrawing() {
        if (currentDrawingIndex >= 0 && currentDrawingIndex < drawings.size()) {
            return drawings.get(currentDrawingIndex);
        }
        return null;
    }

    public List<Drawing> getAllDrawings() {
        return new ArrayList<>(drawings);
    }

    // Getters
    public Player getPlayer() { return player; }
    public GameState getCurrentState() { return currState; }
    public double getTotalWinnings() { return totalWinnings; }
    public int getCurrentDrawingIndex() { return currentDrawingIndex; }
    public int getTotalDrawings() { return drawings.size(); }

    // Setters
    public void setGameState(GameState state) { this.currState = state; }
}
