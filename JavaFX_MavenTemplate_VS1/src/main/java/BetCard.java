import java.util.Set;
import java.util.HashSet;
import java.util.Random;
import javafx.scene.control.ToggleButton;

import javafx.animation.PauseTransition;
import javafx.util.Duration;




public class BetCard{
	//Base card stuff
	int spots = 50;




	//Game settings
	int maxDrawing = -1; //1-2-3-4
	int curDrawing = 0;

	Set<Integer> winningSpots = new HashSet<>();


	//Player stuff
	Set<Integer> selectedSpots = new HashSet<>();
	int maxSelectedSpots = -1;



	///
	//		SETUP
	///

	void setMaxSpots(int spts){
		maxSelectedSpots = spts;
	}

	void setMaxDrawing(int drawing){
		maxDrawing = drawing;
	}












	public void beginDrawing(ToggleButton[][] grid){
		Random rand = new Random();
		
		winningSpots = new HashSet<>();

		for (int i = 0; i < 20; i++){
			PauseTransition pause = new PauseTransition(Duration.millis(250* i));
			pause.setOnFinished(e -> {

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


	//Unsure if we truly need this but ill add it since its on the doc
	
	boolean isValid(){
		return false;
	}

	int getSpots() { return spots; }

	int getDrawingCount(){ return maxDrawing; }
}
