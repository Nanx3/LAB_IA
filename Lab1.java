import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.objectdetection.*;

public class Lab1 implements FeatureListener {

	public static int MAX_DETECT = 25;
	public static boolean IS_HEART = false;
	public static DifferentialPilot pilot;
	
	// Draw R letter;
	public static void drawR() {
		pilot = new DifferentialPilot(2.25f, 4.8f, Motor.A, Motor.C); //units: inches;
        pilot.travel(40);
        pilot.rotate(-120);
        pilot.steer(-50, -220);
        pilot.rotate(140);
        pilot.travel(25); 
	}
	
	// Draw heart shape.
	public static void drawHeart() {
		pilot = new DifferentialPilot(2.25f, 4.8f, Motor.A, Motor.C); //units: inches;
    	pilot.travel(25);
    	pilot.steer(-50, -250);
    	pilot.rotate(140);
    	pilot.steer(-50, -250);
    	pilot.travel(25);	
	}
	
	public static void main(String[] args) throws Exception {

		
		Lab1 listener = new Lab1();
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S4);
		RangeFeatureDetector fd = new RangeFeatureDetector(us, MAX_DETECT, 500);
		fd.addListener(listener);
		
		while (true) {
			if (IS_HEART) {
				drawHeart();
			} else {
				drawR();
			}
		}
	    	
		//Button.ENTER.waitForPressAndRelease();
	}
	
	public void featureDetected(Feature feature, FeatureDetector detector) {
		int range = (int)feature.getRangeReading().getRange();
		Sound.playTone(1200 - (range * 10), 100);
		System.out.println("Range:" + range);
		pilot.setTravelSpeed(0);
		pilot.setRotateSpeed(0);
		IS_HEART = !IS_HEART;
	}
}