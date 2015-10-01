package alexwilton.cs5041.p1;

import com.phidgets.*;
import com.phidgets.event.*;

public class Controller implements AttachListener,
					ErrorListener,
					SensorChangeListener {

	private InterfaceKitPhidget ik;
	private int initX, initY;
	private SpaceShip spaceShip;

	public Controller(App app) {
		spaceShip = app.getSpaceShip();
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
			int xVal = ik.getSensorValue(0);
			int yVal = ik.getSensorValue(1);
			System.out.println("x: "+ xVal + ", y: " + yVal);
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
		updateSpaceShipDirection();
	}

	@Override
	public void error(ErrorEvent errorEvent) {
		System.out.println("Error: " + errorEvent.getException().getDescription());
	}
}

