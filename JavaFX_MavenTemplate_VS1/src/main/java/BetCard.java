import java.util.Set;
import java.util.HashSet;
import java.util.Random;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Button;

import javafx.scene.text.Text;

import javafx.animation.PauseTransition;
import javafx.util.Duration;




public class BetCard{
	//Base card stuff
	int spots = 50;




	//Game settings
	int maxDrawing = -1; //1-2-3-4
	int curDrawing = 1;

	Set<Integer> winningSpots = new HashSet<>();


	//Player stuff
	Set<Integer> selectedSpots = new HashSet<>();
	int maxSelectedSpots = -1; //1-4-8-10
	int correctSpots = 0;

	int totalReward = 0;
	int totalDrawsPlayed = 0;
	///
	//		SETUP
	///

	void setMaxSpots(int spts){
		maxSelectedSpots = spts;
	}

	public void setMaxDrawing(int drawing){
		maxDrawing = drawing;
	}

	public void resetGame(){
		curDrawing = 1;
		selectedSpots = new HashSet<>();
	}

	public int scoreCalculation(){
		int result = 0;
		switch(maxSelectedSpots){
			case 1:
				if (correctSpots == 1) return 11;
			break;

			case 4:
				switch(correctSpots){
					case 2:
						return 1;
					case 3:
						return 5;
					case 4:
						return 75;
				}
			break;

			case 8:
				switch(correctSpots){
					case 4:
						return 2;
					case 5:
						return 12;
					case 6:
						return 50;
					case 7:
						return 750;
					case 8:
						return 10000;
				}
			break;

			case 10:
				switch(correctSpots){
					case 0:
						return 5;
					case 5:
						return 2;
					case 6:
						return 15;
					case 7:
						return 40;
					case 8:
						return 450;
					case 9:
						return 4250;
					case 10:
						return 100000;
				}
			break;
		}

		return 0;
	}








	public void beginDrawing(ToggleButton[][] grid, Button nextDrawingButton, Button exitButton, Text score){
		Random rand = new Random();
		correctSpots = 0;
		totalDrawsPlayed++;
		winningSpots = new HashSet<>();
		nextDrawingButton.setDisable(true);
		exitButton.setDisable(true);
		score.setText(displayCurrentWinning());
	


		for (int i = 0; i < 22; i++){
			PauseTransition pause = new PauseTransition(Duration.millis(100* i));
			
			final int ii = i;

			pause.setOnFinished(e -> {
				

				if (ii == 20) {
					nextDrawingButton.setDisable(false);
					exitButton.setDisable(false);
					return;
				}

				if (ii == 21){
					totalReward += scoreCalculation();
					score.setText(displayCurrentWinning());
					return;
				}

				int randomSpot = rand.nextInt(80)+1;
				while (winningSpots.contains(randomSpot)){
					randomSpot = rand.nextInt(80)+1;

				}
				winningSpots.add(randomSpot);

				
				int v = 0;
				for (int y = 0; y < 8; y++){
					for (int x = 0; x < 10; x++){
						v++;
						final int vv = v; 
						if (vv == randomSpot){
							if (selectedSpots.contains(vv)){
								grid[y][x].setStyle("-fx-background-color: green;");
								correctSpots++;
								score.setText(displayCurrentWinning());
							}else{
								grid[y][x].setStyle("-fx-background-color: red;");
							}
						}


					}
				}

			});
			pause.play();
		}

	}

	public void beginDrawing(ToggleButton[][] grid, Button nextDrawingButton, Text score){
		Random rand = new Random();
		correctSpots = 0;
		totalDrawsPlayed++;
		winningSpots = new HashSet<>();
		nextDrawingButton.setDisable(true);
		score.setText(displayCurrentWinning());


		for (int i = 0; i < 22; i++){
			PauseTransition pause = new PauseTransition(Duration.millis(100* i));
			
			final int ii = i;

			pause.setOnFinished(e -> {
				

				if (ii == 20) {
					nextDrawingButton.setDisable(false);
					return;
				}

				if (ii == 21){
					totalReward += scoreCalculation();
					score.setText(displayCurrentWinning());
					return;
				}

				int randomSpot = rand.nextInt(80)+1;
				while (winningSpots.contains(randomSpot)){
					randomSpot = rand.nextInt(80)+1;

				}
				winningSpots.add(randomSpot);

				
				int v = 0;
				for (int y = 0; y < 8; y++){
					for (int x = 0; x < 10; x++){
						v++;
						final int vv = v; 
						if (vv == randomSpot){
							if (selectedSpots.contains(vv)){
								grid[y][x].setStyle("-fx-background-color: green;");
								correctSpots++;
								score.setText(displayCurrentWinning());
							}else{
								grid[y][x].setStyle("-fx-background-color: red;");
							}
						}


					}
				}

			});
			pause.play();
		}

	}

	public String displayCurrentWinning(){
		return "Ttl. Winnings\n  $" + Integer.toString(totalReward) + "\n Winning\n  $" + Integer.toString(scoreCalculation());//\n!!!\nDrawing Reward\n"  + Integer.toString(scoreCalculation());
	}

	public void quickPick(){
		Random rand = new Random();
	
		clearSelectedSpots();
		for (int i = 0; i < maxSelectedSpots; i++){
			int randomSpot = rand.nextInt(80)+1;
			while (selectedSpots.contains(randomSpot)){
				randomSpot = rand.nextInt(80)+1;

			}
			selectedSpots.add(randomSpot);
		}



	}

	public void selectNumber(int target){
		if (selectedSpots.contains(target)){
			selectedSpots.remove(target);
		} else {
			if (selectedSpots.size() < maxSelectedSpots){
				selectedSpots.add(target);

			}
		}
	}


	public Set<Integer> getSelectedSpots(){
		return selectedSpots;
	}

	void clearSelectedSpots(){
		selectedSpots = new HashSet<>();
	}




	//VISUALIZATION
	public String displayStats(){
		return "Drawings: \n" + Integer.toString(maxDrawing) + "\nSpots: \n" + Integer.toString(selectedSpots.size()) + " / " + Integer.toString(maxSelectedSpots);
	}

	public String displayDrawingStats(){
		return "Drawing: " + Integer.toString(curDrawing) + " / " + Integer.toString(maxDrawing);
	}

	public String displayClear(){
		return "Ttl. Winnings\n  $" + totalReward + "\nTtl. Draws\n   " + totalDrawsPlayed;
	}

	public boolean ableToBegin(){
		if (selectedSpots.size() == maxSelectedSpots){
			return true;
		}

		return false;
	}


	//Unsure if we truly need this but ill add it since its on the doc
	
	boolean isValid(){
		return false;
	}

	int getSpots() { return spots; }

	int getDrawingCount(){ return maxDrawing; }
}
