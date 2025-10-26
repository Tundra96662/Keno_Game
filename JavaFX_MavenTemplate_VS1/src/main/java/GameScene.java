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
		
		Button newLookButton = new Button("New Look");
		newLookButton.setOnAction(e -> {});
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

		Text statText = new Text(BETCARD.displayStats()); 
		StackPane holder = new StackPane();
		holder.getChildren().addAll(statsPane,statText);



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
		


		///Setup T(H)REE

		Text SHText = new Text("Pick your numbers.\nor"); 
		//HBox SHBUTTONS = new HBox();
		Button quickPickButton = new Button("Quick Pick");
		Button beginButton = new Button("Begin Drawing");
		
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


		});


		beginButton.setOnAction(e->{
			BETCARD.beginDrawing(betCardButtons);

			for (int y = 0; y < 8; y++){
				for (int x = 0; x < 10; x++){
					betCardButtons[y][x].setDisable(true);
					betCardButtons[y][x].setStyle("");
				}
			}
		});


		VBox setupThree = new VBox();
		setupThree.getChildren().addAll(SHText, quickPickButton, beginButton);



		///Setup TWO

		Text STText = new Text("How many drawings will you play?"); 
		HBox STBUTTONS = new HBox();

		for (int i = 1; i < 5; i++){
			final int actVal = i;
			Button curButton = new Button(Integer.toString(actVal));
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

		int[] SOVALS = {1,4,8,10};
		for (int i = 0; i < 4; i++){
			final int actVal = SOVALS[i];
			Button curButton = new Button(Integer.toString(actVal));
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



		stackPane.getChildren().addAll(bottomPane,setupOne);





		HBox BOTTOMBORDER = new HBox(stackPane);//bottomPane);
		BOTTOMBORDER.setAlignment(Pos.CENTER);
		root.setBottom(BOTTOMBORDER);







	}
}
