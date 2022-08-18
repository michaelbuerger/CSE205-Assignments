// Assignment 12: Arizona State University CSE205
//         Name: Michael Buerger
//    StudentID: 1214351462
//      Lecture: Tu Th 9:00 AM - 10:15 AM
//  Description: Defines a pane with controls for the WaveDisplayPane. Allows user to stop and start animations,
//				 clear the display pane, and randomize parameters. It also allows the user to change the wave
//				 color, wavelength, amplitude, and speed.

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import javafx.scene.control.ColorPicker;

import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.util.Random;
import javafx.event.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class WaveControlPane extends Pane {

	// define instance variables
	private WaveDisplayPane m_wavePane; // pane to draw animation
	private int m_width, m_height;
	private Color m_color; // current selected color
	private ColorPicker m_picker; // color picker
	private Button m_startButton, m_stopButton, m_clearButton, m_surpriseButton; // buttons
	private Label m_speedLabel, m_widthLabel, m_heightLabel; // slider labels
	private Slider m_speedSlider, m_widthSlider, m_heightSlider; // sliders;

	// constructor to create all components, set their handler/listener,
	// and arrange them using layout panes.
	public WaveControlPane(int _h, int _w, Color _initialColor) {
		m_color = _initialColor;
		m_width = (int) (_h * 0.68);
		m_height = _w - 10;

		// creates a pane to display waves with the specified color
		m_wavePane = new WaveDisplayPane(m_width, m_color);
		m_wavePane.setMinSize(m_width, m_height);
		m_wavePane.setMaxSize(m_width, m_height);

		// create a color picker with the specified initial color
		m_picker = new ColorPicker(m_color);
		m_picker.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		// instanciate buttons
		m_startButton = new Button("Start");
		m_stopButton = new Button("Stop");
		m_clearButton = new Button("Clear");
		m_surpriseButton = new Button("Surprise!");

		// resize buttons to width of pane (resize to max width available)
		m_startButton.setMaxWidth(Double.MAX_VALUE);
		m_stopButton.setMaxWidth(Double.MAX_VALUE);
		m_clearButton.setMaxWidth(Double.MAX_VALUE);
		m_surpriseButton.setMaxWidth(Double.MAX_VALUE);

		VBox _buttonPane = new VBox(m_startButton, m_stopButton, m_clearButton, m_surpriseButton, m_picker); // add buttons + color picker to vertical button pane
		
		_buttonPane.setPrefSize(100, 100);
		_buttonPane.setAlignment(Pos.CENTER);

		// instanciate labels + sliders
		m_speedLabel = new Label("Speed");
		m_widthLabel = new Label("Width");
		m_heightLabel = new Label("Height");

		m_speedSlider = new Slider(5, 100, 20); // Slider(min, max, startvalue)
		m_speedSlider.setMajorTickUnit(10);
		m_speedSlider.setMinorTickCount(5);
		m_speedSlider.setOrientation(Orientation.VERTICAL);
		m_speedSlider.setShowTickLabels(true);
		m_speedSlider.setShowTickMarks(true);

		m_widthSlider = new Slider(5, 100, 50); // Slider(min, max, startvalue)
		m_widthSlider.setMajorTickUnit(10);
		m_widthSlider.setMinorTickCount(5);
		m_widthSlider.setOrientation(Orientation.VERTICAL);
		m_widthSlider.setShowTickLabels(true);
		m_widthSlider.setShowTickMarks(true);

		m_heightSlider = new Slider(5, 100, 100); // Slider(min, max, startvalue)
		m_heightSlider.setMajorTickUnit(10);
		m_heightSlider.setMinorTickCount(5);
		m_heightSlider.setOrientation(Orientation.VERTICAL);
		m_heightSlider.setShowTickLabels(true);
		m_heightSlider.setShowTickMarks(true);
	
		// add sliders + labels to respective vertical panes
		VBox _speedSliderPane = new VBox(m_speedLabel, m_speedSlider);
		VBox _waveLengthSliderPane = new VBox(m_widthLabel, m_widthSlider);
		VBox _waveAmplitudeSliderPane = new VBox(m_heightLabel, m_heightSlider);

		TilePane _sliderPane = new TilePane();
		_sliderPane.setPrefColumns(3);
		_sliderPane.setPadding(new Insets(5, 5, 5, 5));
		_sliderPane.setAlignment(Pos.CENTER);
		_sliderPane.getChildren().addAll(_speedSliderPane, _waveLengthSliderPane, _waveAmplitudeSliderPane);

		HBox _controls = new HBox(_buttonPane, _sliderPane);
		_controls.setAlignment(Pos.CENTER);

		BorderPane _controlsAndWaves = new BorderPane();
		_controlsAndWaves.setLeft(_controls);
		_controlsAndWaves.setCenter(m_wavePane);

		this.getChildren().add(_controlsAndWaves);

		// register event handlers to buttons, color picker, sliders
		m_startButton.setOnAction(new ButtonHandler());
		m_stopButton.setOnAction(new ButtonHandler());
		m_clearButton.setOnAction(new ButtonHandler());
		m_surpriseButton.setOnAction(new ButtonHandler());

		m_picker.setOnAction(new ColorHandler());

		m_speedSlider.valueProperty().addListener(new SpeedHandler());
		m_widthSlider.valueProperty().addListener(new WaveLengthHandler());
		m_heightSlider.valueProperty().addListener(new WaveAmplitudeHandler());
	}

	// handles button presses from the 4 different buttons
	private class ButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent _event) {
			if(_event.getSource() == m_startButton) // start button pressed
				m_wavePane.resume();
			if(_event.getSource() == m_stopButton) // stop button pressed
				m_wavePane.suspend();
			if(_event.getSource() == m_clearButton) // clear button pressed
				m_wavePane.clearPane();
			if(_event.getSource() == m_surpriseButton) { // surprise button pressed
				m_wavePane.suspend();

				/* Generate random paramaters */
				int _randAmplitude, _randWavelength, _randSpeed, _randR, _randG, _randB;
				Random _randomGen = new Random();

				_randAmplitude = _randomGen.nextInt(96) + 5; // gens random num 0-95 then shifts range to 5-100
				_randWavelength = _randomGen.nextInt(96) + 5;
				_randSpeed = _randomGen.nextInt(96) + 5;

				_randR = _randomGen.nextInt(256); // generate random int 0-255
				_randG = _randomGen.nextInt(256);
				_randB = _randomGen.nextInt(256);

				Color _randColor = Color.rgb(_randR, _randG, _randB);
				/*		*/

				// assign parameters to control pane
				m_speedSlider.setValue(_randSpeed);  
				m_widthSlider.setValue(_randWavelength); 
				m_heightSlider.setValue(_randAmplitude);
				m_picker.setValue(_randColor);

				// assign parameters to wave display pane and resume animation
				m_wavePane.setWaveLength(_randWavelength);
				m_wavePane.setWaveAmplitude(_randAmplitude);
				m_wavePane.setRate(_randSpeed);
				m_wavePane.changeColor(_randColor);

				m_wavePane.resume();
			}
		}
	}

	// color handler, updates wavepanes draw color when color picker is updated
	private class ColorHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent _event) {
			m_wavePane.changeColor(m_picker.getValue());
		}
	}

	// speed handler, updates wavepanes draw rate when speed slider is updated
	private class SpeedHandler implements ChangeListener<Number> {
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			m_wavePane.setRate((int) m_speedSlider.getValue());
		}
	}

	// wavelength handler, updates wavepanes wavelength when wavelength slider is updated
	private class WaveLengthHandler implements ChangeListener<Number> {
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			m_wavePane.suspend();
			m_wavePane.setWaveLength((int) m_widthSlider.getValue());
		}
	}

	// waveamplitude handler, updates wavepanes amplitude when amplitude slider is updated
	private class WaveAmplitudeHandler implements ChangeListener<Number> {
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			m_wavePane.suspend();
			m_wavePane.setWaveAmplitude((int) m_heightSlider.getValue());
		}
	}
}
