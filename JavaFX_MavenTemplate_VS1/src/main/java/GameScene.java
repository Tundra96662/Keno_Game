import javafx.scene.Scene;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import javafx.scene.text.Text;
import javafx.scene.control.TextField;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

import javafx.scene.control.ToolBar;

import javafx.scene.shape.Rectangle;

import javafx.scene.paint.Color;

import javafx.geometry.Pos;

public class GameScene {
	Scene scene;	
	public GameScene(){
		
		BetCard BETCARD = new BetCard();

		BorderPane root = new BorderPane();
		scene = new Scene(root, 700, 450);

		MenuItem menuButtonRules 			 = new MenuItem("Rules");
		MenuItem menuButtonProbability = new MenuItem("Probability");
		MenuItem menuButtonQuit				 = new MenuItem("Quit");
		MenuButton menuButton = new MenuButton("Menu", null, menuButtonRules, menuButtonProbability, menuButtonQuit);

		//NOTE UNCOMPLETE
		menuButtonRules.setOnAction(e -> {});
		menuButtonProbability.setOnAction(e -> {});
		menuButtonQuit.setOnAction(e -> {System.exit(0);});
		//
		///
		///
		//
		
		ToggleButton newLookButton = new ToggleButton("New Look");
		
		//
		///
		//

		ToolBar toolBar = new ToolBar(menuButton, newLookButton);

		root.setTop(toolBar);

		///
		//	Middle Section
		///



		//Status
		//
		
		Rectangle statsPane = new Rectangle(100,120);
		statsPane.setFill(Color.LIGHTGREY);
		
		statsPane.setArcWidth(15);
		statsPane.setArcHeight(15);

		Text statText = new Text(BETCARD.displayClear()); 
		StackPane holder = new StackPane();
		holder.getChildren().addAll(statsPane,statText);




		///NOTE: tHis LINE IS FROM SETUP 3
		Button beginButton = new Button("Begin Drawing");
		beginButton.setDisable(true);





		//SetDrawingGrid
		//
		ToggleButton[][]betCardButtons = new ToggleButton[8][10];

		int j = 0;
		GridPane betCard = new GridPane();		
		for(int y = 0; y < 8; y++){
			for(int x = 0; x < 10; x++){
				j++;
				
				String btnNm = Integer.toString(j);
				ToggleButton curButton = new ToggleButton(btnNm);
				curButton.setMaxWidth(100);
				curButton.setDisable(true);				
				betCardButtons[y][x] = curButton;

				final int jj = j;
				curButton.setOnAction(e -> { 
						
						BETCARD.selectNumber(jj);

						//Depending on what happens, We need to make sure that the toggle is correct
						curButton.setSelected(BETCARD.getSelectedSpots().contains(jj));

						statText.setText(BETCARD.displayStats());
						beginButton.setDisable(!BETCARD.ableToBegin());
				});

				betCard.add(curButton, x, y);
			}
		}

	
		Rectangle INVISIBLE = new Rectangle(75,100);
		INVISIBLE.setFill(Color.TRANSPARENT);

		HBox centerSection = new HBox();
		centerSection.getChildren().addAll(holder,betCard, INVISIBLE);
		centerSection.setAlignment(Pos.CENTER);
		centerSection.setSpacing(10);

		root.setCenter(centerSection);




		///
		//	Bottom Section
		///


		Rectangle bottomPane = new Rectangle(650,200);
		bottomPane.setFill(Color.LIGHTGREY);
		bottomPane.setArcWidth(15);
		bottomPane.setArcHeight(15);



		StackPane stackPane = new StackPane();

		
		///DRAWING TIME
		Text DT = new Text(BETCARD.displayDrawingStats());
		Button nextDrawingButton = new Button("Next Drawing");
		nextDrawingButton.setDisable(true);
		
		Button goAgainButton = new Button("Go again");
		goAgainButton.setDisable(true);
		

		Button exitProgramButton = new Button("Exit program");
		exitProgramButton.setDisable(true);
		exitProgramButton.setOnAction(e -> {System.exit(0);});

		VBox drawingContainer = new VBox();

		nextDrawingButton.setOnAction(e->{
		  BETCARD.curDrawing++;
			DT.setText(BETCARD.displayDrawingStats());

			drawingContainer.getChildren().clear();
			stackPane.getChildren().clear();
			
			if (( BETCARD.maxDrawing - BETCARD.curDrawing) >= 1){
				drawingContainer.getChildren().addAll(DT, nextDrawingButton);
				BETCARD.beginDrawing(betCardButtons,nextDrawingButton, statText);
			}
			else{
				drawingContainer.getChildren().addAll(DT, goAgainButton, exitProgramButton);
				BETCARD.beginDrawing(betCardButtons, goAgainButton, exitProgramButton, statText);
			}

			stackPane.getChildren().addAll(bottomPane, drawingContainer);

			//Same code as begin button
			for (int y = 0; y < 8; y++){
				for (int x = 0; x < 10; x++){
					betCardButtons[y][x].setDisable(true);
					betCardButtons[y][x].setStyle("");
				}
			}

		});
		

		///Setup T(H)REE

		Text SHText = new Text("Pick your numbers.\nor"); 
		//HBox SHBUTTONS = new HBox();
		Button quickPickButton = new Button("Quick Pick");
		//Button beginButton = new Button("Begin Drawing");
		
		quickPickButton.setOnAction(e->{

			BETCARD.quickPick();

			int v = 0;
			for (int y = 0; y < 8; y++){
				for (int x = 0; x < 10; x++){
					v++;
					final int vv = v; 
					betCardButtons[y][x].setSelected(BETCARD.getSelectedSpots().contains(vv));
					statText.setText(BETCARD.displayStats());
				}
			}

			beginButton.setDisable(false);

		});


		beginButton.setOnAction(e->{

			for (int y = 0; y < 8; y++){
				for (int x = 0; x < 10; x++){
					betCardButtons[y][x].setDisable(true);
					betCardButtons[y][x].setStyle("");
				}
			}
	

			DT.setText(BETCARD.displayDrawingStats());

			drawingContainer.getChildren().clear();
			stackPane.getChildren().clear();
			if (BETCARD.maxDrawing > 1){
				drawingContainer.getChildren().addAll(DT, nextDrawingButton);
				BETCARD.beginDrawing(betCardButtons,nextDrawingButton, statText);
			}
			else{
				drawingContainer.getChildren().addAll(DT, goAgainButton, exitProgramButton);
				BETCARD.beginDrawing(betCardButtons, goAgainButton, exitProgramButton, statText);
			}
			stackPane.getChildren().addAll(bottomPane, drawingContainer);

		});


		VBox setupThree = new VBox();
		setupThree.getChildren().addAll(SHText, quickPickButton, beginButton);



		///Setup TWO

		Text STText = new Text("How many drawings will you play?"); 
		HBox STBUTTONS = new HBox();
		
		Button[] STBUTTONSARRAY = new Button[4];
		for (int i = 1; i < 5; i++){
			final int actVal = i;
			Button curButton = new Button(Integer.toString(actVal));
			STBUTTONSARRAY[i-1] = curButton;
			STBUTTONS.getChildren().add(curButton);
			
			curButton.setOnAction(e->{
				//SET THE BETCARD AND THEN PUSH ONTO NEXT STEP
				BETCARD.setMaxDrawing(actVal);
				statText.setText(BETCARD.displayStats());
				stackPane.getChildren().clear();
				stackPane.getChildren().addAll(bottomPane,setupThree);

				for (int y = 0; y < 8; y++){
					for (int x = 0; x < 10; x++){
						betCardButtons[y][x].setDisable(false);
					}
				}

			});

		}

		VBox setupTwo = new VBox();
		setupTwo.getChildren().addAll(STText, STBUTTONS);




		///Setup ONE

		Text SOText = new Text("How many spots will you play?"); 
		HBox SOBUTTONS = new HBox();

		Button[] SOBUTTONSARRAY = new Button[4];
		int[] SOVALS = {1,4,8,10};
		for (int i = 0; i < 4; i++){
			final int actVal = SOVALS[i];
			Button curButton = new Button(Integer.toString(actVal));
			SOBUTTONSARRAY[i] = curButton;
			SOBUTTONS.getChildren().add(curButton);
			
			curButton.setOnAction(e->{
				//SET THE BETCARD AND THEN PUSH ONTO NEXT STEP
				BETCARD.setMaxSpots(actVal);
				stackPane.getChildren().clear();
				stackPane.getChildren().addAll(bottomPane,setupTwo);
			});

		}

		VBox setupOne = new VBox();
		setupOne.getChildren().addAll(SOText, SOBUTTONS);


		///THIS LINE IS TAKEN FROM DRAWING
		goAgainButton.setOnAction(e -> {
			BETCARD.clearSelectedSpots();
			for (int y = 0; y < 8; y++){
				for (int x = 0; x < 10; x++){
					betCardButtons[y][x].setStyle("");
					betCardButtons[y][x].setSelected(false);
				}
			}
			BETCARD.resetGame();
			statText.setText(BETCARD.displayClear());
			beginButton.setDisable(true);
			stackPane.getChildren().clear();
			stackPane.getChildren().addAll(bottomPane,setupOne);
		});

		stackPane.getChildren().addAll(bottomPane,setupOne);





		HBox BOTTOMBORDER = new HBox(stackPane);//bottomPane);
		BOTTOMBORDER.setAlignment(Pos.CENTER);
		root.setBottom(BOTTOMBORDER);


		///THIS IS TAKEN FROM THE VERY BEGINNING
		newLookButton.setOnAction(e -> {
			if (newLookButton.isSelected()){
				root.setStyle("-fx-background-color: #2E1F38");
				toolBar.setStyle("-fx-background-color: #675673");
				bottomPane.setFill(Color.web("#403C6C"));
				statsPane.setFill(Color.web("#403C6C"));

				newLookButton.setStyle("-fx-background-color: #8481AB; -fx-text-fill: white");
				menuButton.setStyle("-fx-background-color: #8481AB; -fx-text-fill: white");

				statText.setFill(Color.WHITE);
				DT.setFill(Color.WHITE);
				SOText.setFill(Color.WHITE);
				STText.setFill(Color.WHITE);
				SHText.setFill(Color.WHITE);

				for (int i = 0; i < SOBUTTONSARRAY.length; i++){
					SOBUTTONSARRAY[i].setStyle("-fx-background-color: #8481AB; -fx-text-fill: white");
					STBUTTONSARRAY[i].setStyle("-fx-background-color: #8481AB; -fx-text-fill: white");
				}

				quickPickButton.setStyle("-fx-background-color: #8481AB; -fx-text-fill: white");
				beginButton.setStyle("-fx-background-color: #8481AB; -fx-text-fill: white");
				nextDrawingButton.setStyle("-fx-background-color: #8481AB; -fx-text-fill: white");
				goAgainButton.setStyle("-fx-background-color: #8481AB; -fx-text-fill: white");
				exitProgramButton.setStyle("-fx-background-color: #8481AB; -fx-text-fill: white");

				//menuButtonRules.setStyle("-fx-background-color: #8481AB");
				//menuButtonProbability.setStyle("-fx-background-color: #8481AB");
				//menuButtonQuit.setStyle("-fx-background-color: #8481AB");
			}else{
				root.setStyle("");
				toolBar.setStyle("");
				bottomPane.setFill(Color.LIGHTGREY);
				statsPane.setFill(Color.LIGHTGREY);

				newLookButton.setStyle("");
				menuButton.setStyle("");
				
				statText.setFill(Color.BLACK);
				DT.setFill(Color.BLACK);
				SOText.setFill(Color.BLACK);
				STText.setFill(Color.BLACK);
				SHText.setFill(Color.BLACK);
			
				for (int i = 0; i < SOBUTTONSARRAY.length; i++){
					SOBUTTONSARRAY[i].setStyle("");
					STBUTTONSARRAY[i].setStyle("");
				}

				quickPickButton.setStyle("");
				beginButton.setStyle("");
				nextDrawingButton.setStyle("");
				goAgainButton.setStyle("");
				exitProgramButton.setStyle("");
			}

		});




	}
}
