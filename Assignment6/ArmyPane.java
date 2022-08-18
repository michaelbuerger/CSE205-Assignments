/* 
Assignment #: 6
        Name: Michael Buerger
   StudentID: 1214351462
     Lecture: Tu Th 9:00 AM - 10:15 AM
 Description: The ArmyPane class uses a BorderPane to
			  assemble a list of Heros and display the
			  scores of those selected
*/

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ArmyPane extends BorderPane {
	
	// Contains a list of heroes
	ArrayList<Hero> heroList;
	
	// Variables containing army Damage, Strength, and Charisma
	int totalDamage;
	int totalStrength;
	int totalCharisma;

	Label armyInfoLabel; // displays army information
	VBox centerVBox; // contains checkboxes + heroes
	Button loadHeroesButton; // refreshes heroes and clears checkboxes

	/*
		Create army pane from hero list (border pane)
		Display army info on top, vbox displaying heroes with checkboxes in center, and a load heroes button on the bottom
		Set event handler for loadHeroesButton
     */
	public ArmyPane(ArrayList<Hero> heroList) {
		this.heroList = heroList;

		// Initialize the instance variables
		armyInfoLabel = new Label("Select heroes to add to your army"); // displays army information
		centerVBox = new VBox(); // contains checkboxes + heroes
		loadHeroesButton = new Button("Load Heroes/Clear Selection"); // refreshes heroes and clears checkboxes

		// Bind "Load Heroes/Clear Selection" Button to its handler
		loadHeroesButton.setOnAction(new LoadHeroesButtonHandler());
		
		// setup borderpane (parent class) layout
		super.setTop(armyInfoLabel);
		super.setCenter(centerVBox);
		super.setBottom(loadHeroesButton);
	}
	
	/*
		Clear out center vbox of all checkboxes (which contain hero info)
		Add respective checkboxes for latest list of heroes to vbox
     */
	private class LoadHeroesButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			
			// clear all checkboxes
			centerVBox.getChildren().clear();

			for(Hero h : heroList) // for each hero, create checkbox with hero.toString info, bind CheckBoxHandler, add to center vbox
			{
				CheckBox checkbox = new CheckBox(h.toString());
				checkbox.setOnAction(new CheckBoxHandler(h));
				centerVBox.getChildren().add(checkbox);
			}

		}
	}

	/*
		If checkbox gets checked, add scores to total scores then update label at top
		Otherwise, if checkbox gets unchecked, subtract scores from total scores then update label
     */
	private class CheckBoxHandler implements EventHandler<ActionEvent> {

		Hero hero;
		
		// When creating a new CheckBoxHandler, pass in a Hero object so it can be accessed later
		public CheckBoxHandler(Hero _hero) {
			this.hero = _hero;
		}

		@Override
		public void handle(ActionEvent event) {
			CheckBox checkbox = (CheckBox) event.getSource(); // get caller of handle and cast to checkbox
			
			// If the CheckBox was selected, add the current hero scores to totalStrength, 
			// 	totalCharisma, and totalDamge. Otherwise, subtract the current hero scores
			if(checkbox.isSelected())
			{
			 	totalStrength += hero.getStrength();
				totalCharisma += hero.getCharisma();
				totalDamage += hero.getDamage();
			} else
			{
				totalStrength -= hero.getStrength();
				totalCharisma -= hero.getCharisma();
				totalDamage -= hero.getDamage();
			}

			// update army info label at top with new total scores
			armyInfoLabel.setText("Total Damage: " + totalDamage + "\t\tTotal Strength: " + totalStrength + "\tTotal Charisma: " + totalCharisma);
		}
	}

}
