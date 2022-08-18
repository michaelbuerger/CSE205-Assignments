/* 
Assignment #: 6
        Name: Michael Buerger
   StudentID: 1214351462
     Lecture: Tu Th 9:00 AM - 10:15 AM
 Description: The HeroPane class creates an HBox to handle input of
 			  new heroes on one side and the display of heroes on the other
			  It handles exceptions to hero input and operates based on a passed
			  list of Hero objects which may be used outside of the class
*/

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.lang.Math;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class HeroPane extends HBox {
	// contains a list of heroes
	ArrayList<Hero> heroList;

	// Save current Hero Type
	String selectedHeroType;

	// Main layout components
	TextArea rightTextArea;
	VBox leftVBox;
	ComboBox<String> heroTypeComboBox;
	ImageView imageView;

	// Hero input components
	GridPane inputPane;
	Label nameLabel, strengthLabel, charismaLabel, damageLabel;
	TextField nameField, strengthField, charismaField, damageField;
	Button randomButton;
	
	// Add hero button and display label
	Button addHeroButton;
	Label infoLabel;
	
	// Define window size
	public static final int WINSIZE_X = 950, WINSIZE_Y = 600;

	/*
		Create HeroPane (extends hbox) with reference to list of heroes (if any heroes are contained initially they will be included)
		Setup vbox on left for creating heroes and text area on right for displaying heroes
		Sets up input fields and event handlers for buttons
     */
	public HeroPane(ArrayList<Hero> heroList) {

		// Assign the heroList passed into this constructor
		this.heroList = heroList;

		// Initialize main layout components
		this.leftVBox = new VBox();
		this.rightTextArea = new TextArea();
		rightTextArea.setEditable(false); // make right text area non-editable as it is just a display
		
		// Setting up ComboBox
		String[] heroType = { "Mage", "Fighter", "Unicorn", "Zombie" };
		heroTypeComboBox = new ComboBox<String>();
		heroTypeComboBox.setValue("Hero Type");
		heroTypeComboBox.getItems().addAll(heroType);
		heroTypeComboBox.setOnAction(new HeroTypeComboBoxHandler());
		leftVBox.getChildren().add(heroTypeComboBox);

		// Initialize hero input components
		inputPane = new GridPane();

		nameLabel = new Label("Name");
		strengthLabel = new Label("Strength");
		charismaLabel = new Label("Charisma");
		damageLabel = new Label("Damage");

		nameField = new TextField();
		strengthField = new TextField();
		charismaField = new TextField();
		damageField = new TextField();
		damageField.setEditable(false); // damageField should not be editable directly by user, instead is set by random button

		randomButton = new Button("Random");

		// Initialize add hero button and label
		addHeroButton = new Button("Add New Hero!!!");
		infoLabel = new Label();
		infoLabel.setTextFill(Color.RED);
		
		// Organize Labels, TextFields, and Button onto the GridPane
		inputPane.add(nameLabel, 0, 0);
		inputPane.add(strengthLabel, 0, 1);
		inputPane.add(charismaLabel, 0, 2);
		inputPane.add(damageLabel, 0, 3);

		inputPane.add(nameField, 1, 0);
		inputPane.add(strengthField, 1, 1);
		inputPane.add(charismaField, 1, 2);
		inputPane.add(damageField, 1, 3);

		inputPane.add(randomButton, 2, 3);

		// Bind buttons to their handlers (RandomButtonHandler and AddNewHeroButtonHandler)
		randomButton.setOnAction(new RandomButtonHandler()); // add button handler (calls handle method on press)
		addHeroButton.setOnAction(new AddNewHeroButtonHandler()); // add button handler (calls handle method on press)
		
		// Completed: Add GridPane, “Add Hero” Button, and red Label to leftVBox
		leftVBox.getChildren().addAll(inputPane, addHeroButton, infoLabel);

		// VBox layout alignment
		inputPane.setHgap(20);
		leftVBox.setPadding(new Insets(40, 50, 0, 50));
		leftVBox.setSpacing(40);
		leftVBox.setAlignment(Pos.TOP_CENTER);
		leftVBox.setPrefWidth(WINSIZE_X / 2);

		// Setting up ImageView
		imageView = new ImageView();
		imageView.setPreserveRatio(true);
		imageView.setFitWidth(100);
		leftVBox.getChildren().add(imageView);
		FileInputStream input;
		try {
			input = new FileInputStream("unicorn.png");
			Image image = new Image(input);
			imageView.setImage(image);
		} catch (FileNotFoundException e) {
			imageView.setImage(null);
		}
		
		// Add main components to "Add Hero" tab
		this.getChildren().addAll(leftVBox, rightTextArea);
	}
	
	// Generate random damage value (50 <= damage <= 100)
	/*
		On button push, generate random damage value between (inclusive) 50 and 100 and set damage field to it
		If damage field has been set already, display message to user
     */
	private class RandomButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			// Completed: 2. Write "Random" Button Handler
			if(damageField.getText().isEmpty()) // if text field is empty, set text field and damage value to random 50 --> 100
			{
				int dmg = (int) Math.floor(Math.random() * 51) + 50; // generates random int 50 --> 100
				damageField.setText(Integer.toString(dmg)); // set damage field to randomly generated value
			}
			else // if text field has already been set, set red label to indicating message
			{
				infoLabel.setText("Damage is already generated");
			}
		}
	}


	/*
		Check for valid input THEN add new hero to hero list
		If input is invalid in any way (not right format, field empty, etc),
		an exception is thrown and a message is displayed to the user and no hero is added
     */
	private class AddNewHeroButtonHandler implements EventHandler<ActionEvent> {

		// This method will be called once we click the button
		public void handle(ActionEvent event) {

			// Create 4 String variables and assign them to the values retrieved from TextFields using .getText()
			String name = nameField.getText(); // expected format: name
			String strengthStr = strengthField.getText(); // expected format: int
			String charismaStr = charismaField.getText(); // expected format: int
			String damageStr = damageField.getText(); // expected format: int

			try {
				if (selectedHeroType == null) {
					throw new Exception("Hero type is not yet selected");
				}

				if(name.isEmpty() || strengthStr.isEmpty() || charismaStr.isEmpty() || damageStr.isEmpty()) // check for empty strings
				{
					throw new Exception("At least one of the text fields is empty"); // throw exception if any field is empty
				}

				for(Hero h : heroList) // check for duplicate heroes
				{
					if(h.getName().equals(name))
						throw new Exception("Hero existed!"); // throw exception if hero is duplicate
				}

				// parse text field data to integers, parseInt throws NumberFormatException if non-integer is found
				int strength = Integer.parseInt(strengthStr);
				int charisma = Integer.parseInt(charismaStr);
				int damage = Integer.parseInt(damageStr);
				if(strength < 0 || charisma < 0) // check to make sure strength and charisma are both positive numbers
				{
					throw new Exception("Both Strength and Charisma must be positive numbers");
				}
				if(strength + charisma > 100) // make sure sum of strength and charisma is less than 100
				{
					throw new Exception("The sum of strength and charisma must be less or equal to 100");
				}
				heroList.add(new Hero(name, selectedHeroType, strength, charisma, damage)); // add hero to list
				infoLabel.setText("Hero added successfully"); // display successful addition of hero to user
				// clear text fields
				nameField.setText("");
				strengthField.setText("");
				charismaField.setText("");
				damageField.setText("");

				updateTextArea(); // update text area to right of vbox, to display new information
			} catch (NumberFormatException exception) {
				infoLabel.setText("At least one of the text fields is in the incorrect format"); // display number format exception to user

			} catch (Exception exception) {
				infoLabel.setText(exception.getMessage()); // display exception message to user in red label
			}

		}
	}

	/*
		Combine all hero information and set right text area to this information to display it
     */
	private void updateTextArea() {
		String heroInfo = "";
		for(Hero h : heroList)
		{
			heroInfo += h.toString() + "\n";
		}
		rightTextArea.setText(heroInfo);
	}
	
	
	/*
		Loads and sets image to respective hero images when hero type combo box updates
     */
	private class HeroTypeComboBoxHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			selectedHeroType = heroTypeComboBox.getSelectionModel().getSelectedItem();
			FileInputStream input;
			try {
				input = new FileInputStream(selectedHeroType.toLowerCase() + ".png");
				Image image = new Image(input);
				imageView.setImage(image);
			} catch (FileNotFoundException e) {
				imageView.setImage(null);
			}

		}
	}


}
