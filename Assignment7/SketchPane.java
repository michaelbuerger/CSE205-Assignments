/* 
Assignment #: 7
        Name: Michael Buerger
   StudentID: 1214351462
     Lecture: Tu Th 9:00 AM - 10:15 AM
 Description: Defines the sketch pane for the sketching application.
			  Sets up controls on the top and bottom of the application
			  and contains a canvas for drawing in the center. Uses GridPane
			  to hold HBoxes for controls and the Pane for drawing.
*/

import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;


public class SketchPane extends BorderPane {
	
	// Instance variables
	private ArrayList<Shape> shapeList, tempList;
	private Button undoButton, eraseButton;
	private Label fillColorLabel, strokeColorLabel, strokeWidthLabel;
	private ComboBox<String> fillColorCombo, strokeWidthCombo, strokeColorCombo;
	private ToggleGroup radioToggleGroup;
	private RadioButton radioButtonLine, radioButtonRectangle, radioButtonCircle;
	private Pane sketchCanvas;
	private Color[] colors;
	private String[] strokeWidth, colorLabels;
	private Color currentStrokeColor, currentFillColor;
	private int currentStrokeWidth;
	private Line line;
	private Circle circle;
	private Rectangle rectangle;
	private double x1, y1;
	
	/*
		Instanciate instance variables, setup controls and panes.
	*/
	public SketchPane() {
		// Colors, labels, and stroke widths that are available to the user
		colors = new Color[] {Color.BLACK, Color.GREY, Color.YELLOW, Color.GOLD, Color.ORANGE, Color.DARKRED, Color.PURPLE, Color.HOTPINK, Color.TEAL, Color.DEEPSKYBLUE, Color.LIME} ;
        colorLabels = new String[] {"black", "grey", "yellow", "gold", "orange", "dark red", "purple", "hot pink", "teal", "deep sky blue", "lime"};
        fillColorLabel = new Label("Fill Color:");
        strokeColorLabel = new Label("Stroke Color:");
        strokeWidthLabel = new Label("Stroke Width:");
        strokeWidth = new String[] {"1", "3", "5", "7", "9", "11", "13"};    

		// instanciate controls, with titles/internal labels where applicalbe
		shapeList = new ArrayList<Shape>();
		tempList = new ArrayList<Shape>();
		fillColorCombo = new ComboBox<String>();
		strokeWidthCombo = new ComboBox<String>();
		strokeColorCombo = new ComboBox<String>();
		radioButtonLine = new RadioButton("Line");
		radioButtonRectangle = new RadioButton("Rectangle");
		radioButtonCircle = new RadioButton("Circle");
		undoButton = new Button("Undo");
		eraseButton = new Button("Erase");
		
		// setup toggle group and add radio buttons, set line to default by seleccting it
		radioToggleGroup = new ToggleGroup();
		radioButtonLine.setSelected(true);
		radioButtonLine.setToggleGroup(radioToggleGroup);
		radioButtonRectangle.setToggleGroup(radioToggleGroup);
		radioButtonCircle.setToggleGroup(radioToggleGroup);
		
		// pass instances of ButtonHandler to undo and erase button
		undoButton.setOnAction(new ButtonHandler());
		eraseButton.setOnAction(new ButtonHandler());

		// add relevant element labels to combo boxes
		fillColorCombo.getItems().addAll(colorLabels);
		strokeWidthCombo.getItems().addAll(strokeWidth);
		strokeColorCombo.getItems().addAll(colorLabels);
		// set default: stroke and fill color black, stroke width 1
		fillColorCombo.setValue("black");
		strokeWidthCombo.setValue("1");
		strokeColorCombo.setValue("black");
		// pass instances of ColorHandler to combo boxes
		fillColorCombo.setOnAction(new ColorHandler());
		strokeWidthCombo.setOnAction(new WidthHandler());
		strokeColorCombo.setOnAction(new ColorHandler());

		// instanciate actual drawing area (Pane), set background color to
		sketchCanvas = new Pane();
		sketchCanvas.setStyle("-fx-background-color: white;");

		// instanciate top hbox with default spacing of 20, set minimum size
		// set alignment of horizontal elements to horizontally and vertically centered
		HBox topHBox = new HBox(20);
		topHBox.setMinSize(20, 40);
		topHBox.setAlignment(Pos.CENTER);
		topHBox.setStyle("-fx-background-color: lightgrey;");
		// add radio buttons, and erase and undo buttons to hbox
		topHBox.getChildren().addAll(fillColorLabel, fillColorCombo, strokeWidthLabel, strokeWidthCombo, strokeColorLabel, strokeColorCombo);

		// instanciate bottom hbox with default spacing of 20, set minimum size
		// set alignment of horizontal elements to horizontally and vertically centered
		HBox bottomHBox = new HBox(20);
		bottomHBox.setMinSize(20, 40);
		bottomHBox.setAlignment(Pos.CENTER);
		bottomHBox.setStyle("-fx-background-color: lightgrey;");
		// add radio buttons, and erase and undo buttons to hbox
		bottomHBox.getChildren().addAll(radioButtonLine, radioButtonRectangle, radioButtonCircle, undoButton, eraseButton);
    
		// setup borderpane (parent class) layout
		// set center first as this effectively pushes it behidn the top and bottom hboxes
		// because of this, drawn shapes will not overlap the controls
		super.setCenter(sketchCanvas); // sketch area in center
		super.setTop(topHBox); // color and stroke settings at top
		super.setBottom(bottomHBox); // shape type, undo, and erase buttons at bottom

		// register sketch pane with MouseHandler
		sketchCanvas.setOnMousePressed(new MouseHandler());
      	sketchCanvas.setOnMouseDragged(new MouseHandler());
      	sketchCanvas.setOnMouseReleased(new MouseHandler());

		// set remaining instance variables to defaults
		x1 = 0;
		y1 = 0;
		currentFillColor = Color.BLACK;
		currentStrokeColor = Color.BLACK;
		currentStrokeWidth = 1;
	}

	/*
		Handles mouse events for drawing: mouse click, mouse drag, mouse released
	*/
	private class MouseHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			// if rectangle is being drawn
			if (radioButtonRectangle.isSelected()) {
				//Mouse is pressed
				if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
					x1 = event.getX();
					y1 = event.getY();
					rectangle = new Rectangle();
					rectangle.setX(x1);
					rectangle.setY(y1);
					shapeList.add(rectangle);
					rectangle.setFill(Color.WHITE);
					rectangle.setStroke(Color.BLACK);	
					sketchCanvas.getChildren().add(rectangle);
				}
				//Mouse is dragged
				else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
					if (event.getX() - x1 <0) 
						rectangle.setX(event.getX());
					if (event.getY() - y1 <0) 
						rectangle.setY(event.getY());
					rectangle.setWidth(Math.abs(event.getX() - x1));
					rectangle.setHeight(Math.abs(event.getY() - y1));

				}
				//Mouse is released
				else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
					rectangle.setFill(currentFillColor);
					rectangle.setStroke(currentStrokeColor);
					rectangle.setStrokeWidth(currentStrokeWidth);
				}
			}
			// if circle is being drawn
			if(radioButtonCircle.isSelected()) {
				// Mouse is pressed
				if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
					x1 = event.getX();
					y1 = event.getY();
					circle = new Circle();
					circle.setCenterX(x1);
					circle.setCenterY(y1);
					shapeList.add(circle);
					circle.setFill(Color.WHITE);
					circle.setStroke(Color.BLACK);	
					sketchCanvas.getChildren().add(circle);
				}
				// Mouse is dragged
				else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
					// set circle radius to distance between circle center and current mouse position
					circle.setRadius(getDistance(x1, y1, event.getX(), event.getY()));
				}
				// Mouse is released
				else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
					circle.setFill(currentFillColor);
					circle.setStroke(currentStrokeColor);
					circle.setStrokeWidth(currentStrokeWidth);
				}
			}
			// if line is being drawn
			if(radioButtonLine.isSelected()) {
				// Mouse is pressed
				if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
					x1 = event.getX();
					y1 = event.getY();
					line = new Line();
					// set start and end of line to same point
					// so line will appear as a point unless dragged
					line.setStartX(x1);
					line.setStartY(y1);
					line.setEndX(x1);
					line.setEndY(y1);
					shapeList.add(line);
					line.setStroke(Color.BLACK);	
					sketchCanvas.getChildren().add(line);
				}
				// Mouse is dragged
				else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
					line.setEndX(event.getX());
					line.setEndY(event.getY());
				}
				// Mouse is released
				else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
					line.setStroke(currentStrokeColor);
					line.setStrokeWidth(currentStrokeWidth);
				}
			}
		}
	}
		
	/*
		Handles button actions for erasing and undoing drawings
		Creates a backup of canvas when erasing and when undoing
		with an empty canvas this backup is restored
	*/
	private class ButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			// if undo button clicked
			if(event.getSource() == undoButton) {
				if(!shapeList.isEmpty()) { // shapeList isn't empty
					Shape lastShape = shapeList.get(shapeList.size() - 1); // get last shape

					// remove last shape from sketch canvas and then from shape list
					sketchCanvas.getChildren().remove(lastShape);
					shapeList.remove(lastShape);
				} else { // shapesList is empty, undo possible erasure
					// restore "backup", which may be empty
					shapeList = new ArrayList<Shape>(tempList);
					sketchCanvas.getChildren().addAll(shapeList);
				}
			}

			// if erase button clicked
			if(event.getSource() == eraseButton) {
				// setup temp list to allow undo of erasure
				if(!shapeList.isEmpty()) {
					// "backup" shapeList
					tempList = new ArrayList<Shape>(shapeList);

					// clear shapeList and canvas
					shapeList.clear();
					sketchCanvas.getChildren().clear();
				}
			}
		}
	}

	/*
		Handles combo boxes regarding stroke and fill color
		When they update their applicable instance variables are updated
	*/
	private class ColorHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			// set current stroke color based on selection
			currentStrokeColor = colors[strokeColorCombo.getSelectionModel().getSelectedIndex()];
			// set current fill color based on selection
			currentFillColor = colors[fillColorCombo.getSelectionModel().getSelectedIndex()];
		}
	}
	
	/*
		Handles combo box regarding stroke width
		When it updates its instance variable is updated
	*/
	private class WidthHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			// set current stroke width based on selected stroke width
			currentStrokeWidth = Integer.parseInt(strokeWidthCombo.getValue());
		}
	}
	
		
	// Get the Euclidean distance between (x1,y1) and (x2,y2)
    private double getDistance(double x1, double y1, double x2, double y2)  {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

}