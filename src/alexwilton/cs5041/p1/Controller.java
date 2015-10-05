package alexwilton.cs5041.p1;

import com.phidgets.*;
import com.phidgets.event.*;

public class Controller implements AttachListener,
					ErrorListener,
					SensorChangeListener {

	private InterfaceKitPhidget ik;
	private int initX, initY;
	private SpaceShip spaceShip;

	private final int SENSOR_ID_MINISTICK_X = 0;
	private final int SENSOR_ID_MINISTICK_Y = 1;
	private final int SENSOR_ID_LIGHT = 3;
	private final int SENSOR_ID_ROTATION = 7;

	private final int LED_GREEN_OUTPUT_ID = 0;
	private final int LED_YELLOW_OUTPUT_ID = 2;
	private final int LED_RED_OUTPUT_ID = 4;

	public Controller(GameModel gameModel) {
		spaceShip = gameModel.getSpaceShip();
		initialize();
	}
	
	public void initialize(){
		try {
			ik = new InterfaceKitPhidget();
			ik.addAttachListener(this);
			ik.addErrorListener(this);
			ik.addSensorChangeListener(this);
			ik.openAny();
			System.out.println("Please attach Ministick Sensor and Rotational Sensor Phidge InterfaceKit");
			ik.waitForAttachment();

			/* Record initial x and y to use as the reference centre point */
			initX = ik.getSensorValue(0);
			initY = ik.getSensorValue(1);
		}catch (PhidgetException e){System.out.println("Phidget Error: " + e.getDescription());}
	}

	/**
	 * Update SpaceShip Acceleration using distance from centre point on ministick sensor
	 */
	public void updateSpaceShipAcceleration(){
		try{
			int xVal = ik.getSensorValue(0);
			int yVal = ik.getSensorValue(1);
			double changeInX = xVal - initX;
			double changeInY = initY - yVal;
			double scalor = 0.0002;
			spaceShip.setAcceleration(new PVector(changeInX * scalor, changeInY * scalor));
		}catch(Exception e){}
	}

	/**
	 * Update SpaceShip direction based on ministick sensor
	 */
	public void updateSpaceShipDirection(){
		try{
			int xVal = ik.getSensorValue(SENSOR_ID_MINISTICK_X);
			int yVal = ik.getSensorValue(SENSOR_ID_MINISTICK_Y);
			if(Math.abs(xVal - initX) < 30 && Math.abs(yVal - initY) < 30) return; //ignore very small movements

			double newDirectionAngle;
			if(yVal > initY)
				newDirectionAngle = Math.atan2(yVal - initY, xVal - initX);
			else
				newDirectionAngle = Math.PI + Math.atan2(-(yVal - initY), -(xVal - initX));
			spaceShip.setDirection(newDirectionAngle);
		}catch (Exception e){}
	}


	@Override
	public void attached(AttachEvent ae) {
		String deviceName = "";
		try {
			deviceName = ae.getSource().getDeviceName();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println(deviceName + " Phidge Attached!");
	}

	@Override
	public void sensorChanged(SensorChangeEvent sce) {
		if(sce.getIndex() == SENSOR_ID_LIGHT) checkForLaserFire();
	}

	@Override
	public void error(ErrorEvent errorEvent) {
		System.out.println("Error: " + errorEvent.getException().getDescription());
	}

	public void checkForLaserFire() {
		try {
			int lightSensorVal = ik.getSensorValue(SENSOR_ID_LIGHT);
			if(lightSensorVal < 25) spaceShip.attemptToFireLaser();
		}catch (PhidgetException e){ System.out.println("Error checking light sensor value");}
	}

	public void updateLEDsWithLaserState() {
		try {
			switch (spaceShip.getLaserState()) {
				case READY: //show green LED only
					ik.setOutputState(LED_GREEN_OUTPUT_ID, true);
					ik.setOutputState(LED_YELLOW_OUTPUT_ID, false);
					ik.setOutputState(LED_RED_OUTPUT_ID, false);
					break;
				case FIRING: //show red+yellow LED
					ik.setOutputState(LED_GREEN_OUTPUT_ID, false);
					ik.setOutputState(LED_YELLOW_OUTPUT_ID, true);
					ik.setOutputState(LED_RED_OUTPUT_ID, true);
					break;
				case COOLING_DOWN: //show yellow LED only
					ik.setOutputState(LED_GREEN_OUTPUT_ID, false);
					ik.setOutputState(LED_YELLOW_OUTPUT_ID, true);
					ik.setOutputState(LED_RED_OUTPUT_ID, false);
					break;
			}
		}catch (PhidgetException e){ System.out.println("Failed to set LED output values");}
	}
}

