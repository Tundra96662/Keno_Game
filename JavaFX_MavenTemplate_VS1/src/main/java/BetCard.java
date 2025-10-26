import java.util.Set;
import java.util.HashSet;

public class BetCard{
	//Base card stuff
	int spots = 50;




	//Game settings
	int maxDrawing = -1; //1-2-3-4
	int curDrawing = -1;



	//Player stuff
	Set<Integer> selectedSpots = new HashSet<>();


	void quickPick(){
	}

	void selectNumber(int target){
	}

	Set<Integer> getSelectedSpots(){
		return selectedSpots;
	}

	void clearSelectedSpots(){
		selectedSpots = new HashSet<>();
	}


	//Unsure if we truly need this but ill add it since its on the doc
	
	boolean isValid(){
		return false;
	}

	int getSpots() { return spots; }

	int getDrawingCount(){ return maxDrawing; }
}
