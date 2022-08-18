// Assignment 12: Arizona State University CSE205
//         Name: Michael Buerger
//    StudentID: 1214351462
//      Lecture: Tu Th 9:00 AM - 10:15 AM
//  Description: Defines a pane and methods for displaying animated (drawn point by point) sine waves on the pane.
//               Allows the variance of color, wavelength, and amplitude. Animations can be paused, resumed, and reset.

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.event.*;
import javafx.animation.*;
import javafx.util.Duration;

public class WaveDisplayPane extends Pane {

	// define instance variables
	private Timeline m_timeline;
	private int m_time;
	private int m_waveLength;
	private int m_waveAmplitude;
	private int m_paneWidth;
	private Color m_color;

	// constructor
	public WaveDisplayPane(int _paneWidth, Color _color) {
		// initialize instance variables
		m_waveAmplitude = 100;
		m_waveLength = 50;
		m_time = 0;
		m_paneWidth = _paneWidth;
		m_color = _color;

		super.setStyle("-fx-background-color: white; -fx-border-color: black;"); // set background color to white, border to black

		KeyFrame _kf = new KeyFrame(Duration.millis(500), new WaveHandler()); // instanciate keyframe object, reg. to WaveHandler
		m_timeline = new Timeline(_kf); // instanciate timeline animation object and register keyframe object with it
		m_timeline.setCycleCount(Timeline.INDEFINITE); // run indefinite # of cycles

		this.setRate(20); // set initial speed to 20
		this.resume(); // play animation
	}

	// plays animation
	public void resume() {
		m_timeline.play();
	}

	// pauses animation and resets time to 0
	public void suspend() {
		m_timeline.stop();
		m_time = 0;
	}

	// changes color of drawn circles (line color)
	public void changeColor(Color _color) {
		m_color = _color;
	}

	// clears all drawn circles from pane (resets pane)
	public void clearPane() {
		getChildren().clear(); // get children of pane and clear
	}

	// setters
	public void setWaveLength(int _waveLength) { m_waveLength = _waveLength; }
	public void setWaveAmplitude(int _waveAmplitude) { m_waveAmplitude = _waveAmplitude; }
	public void setRate(int _rate) { m_timeline.setRate(_rate); }

	// defines an event listener to draw a new point
	private class WaveHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent _event) {
			m_time++; // increment time

			// generate x and y of point on sine wave given based on time (and wavelength and amplitude)
			int _x = (m_waveLength * m_time) / 314;
			int _y = (int) (m_waveAmplitude * Math.sin((0.0174533) * m_time) + 115);

			// if x is in pane, draw circle, else suspend current animation (pause and reset time to 0)
			if (_x < m_paneWidth) {
				Circle _dot = new Circle(_x, _y, 2);
				_dot.setFill(m_color);
				getChildren().add(_dot); // get children of pane and add dot
			} else suspend();
		}
	}
}
