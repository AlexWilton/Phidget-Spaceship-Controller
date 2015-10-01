package alexwilton.cs5041.p1;/*
  * CS5041 HCI Practice
 * Miguel Nacenta 2015-02
 * mans@st-andrews.ac.uk
 */

import com.phidgets.*;

public class TutorialExample0 {
    // Global variables
	InterfaceKitPhidget ik;
	AdvancedServoPhidget s;
	boolean initialized = false;
	
	
	public static void main(String[] args) throws PhidgetException {

		TutorialExample0 myTutorial = new TutorialExample0();
		myTutorial.initPhidgets();
		// myTutorial.readValues();		
	}
	        
	private void initPhidgets() throws PhidgetException {

		ik = new InterfaceKitPhidget();		
		ik.openAny();
		System.out.println("waiting for InterfaceKit attachment... (0)");
		ik.waitForAttachment();
		System.out.println("InterfaceKit attached... (0)");
		System.exit(0);
	}
	
	private void readValues() {
		try {
			while (true) {
				int sensorValue = ik.getSensorValue(0);
				System.out.println(sensorValue);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	

	// Constructor
	public TutorialExample0() {		
	}
	
	
}


