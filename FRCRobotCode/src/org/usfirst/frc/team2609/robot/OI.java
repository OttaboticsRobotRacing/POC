package org.usfirst.frc.team2609.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team2609.robot.commands.*;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.ClawOpenForce;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.VulcanBallMode;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.VulcanGearMode;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.VulcanGearScore;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.toggleClaw;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.toggleDeployClaw;
import org.usfirst.frc.team2609.robot.subsystems.DigitalSensor;

public class OI {
    public static Joystick driverStick;
    public static JoystickButton button1;
    public static JoystickButton button2;
    public static JoystickButton button3;
    public static JoystickButton button4;
    public static JoystickButton button5;
    public static JoystickButton button6;
    public static JoystickButton button7;
    public static JoystickButton button9;
    public static JoystickButton button10;
    //public static JoystickButton opbutton9;
    //public static JoystickButton opbutton10;
    public JoystickButton shift;
    public JoystickButton toggleDeployClaw;
    public JoystickButton toggleClaw;
    public JoystickButton toggleLED;
    public JoystickButton VulcanGearScore;
    public JoystickButton VulcanGearMode;
    public JoystickButton VulcanBallMode;
    public static JoystickButton vulcanGearScore;
    public JoystickButton BallDoorToggle;
    public JoystickButton ClawForceOpen;

    public static Joystick opStick;
    public JoystickButton TsunamiUp;
    public JoystickButton TsunamiDown;
    
    public static JoystickButton opButton1;
    public static JoystickButton opButton2;
    public static JoystickButton opButton3;
    public static JoystickButton opButton4;
    public static JoystickButton opButton5;
    public static JoystickButton opButton6;
    public static JoystickButton opButton7;
    public static JoystickButton opButton9;
    public static JoystickButton opButton10;
    
    public static DigitalSensor gearSensor;
    
    public OI() {
    	driverStick = new Joystick(0);
    	opStick = new Joystick(1);
        
        button1 = new JoystickButton(driverStick, 1);
        button1.whenPressed(new ShifterCommandHigh());
        
        shift = new JoystickButton(driverStick, 2);
        shift.whenPressed(new ShifterCommandLow());

//        button1 = new JoystickButton(driverStick, 1);
//        //button1.whenPressed(new GyroCameraTurn(.5));
//        //toggleLED = new JoystickButton(driverStick, 7);
////        toggleLED.whenReleased(new toggleLED());
//        toggleDeployClaw = new JoystickButton(driverStick, 10);
//        toggleDeployClaw.whenReleased(new toggleDeployClaw());
//        toggleClaw = new JoystickButton(driverStick, 9);
//        toggleClaw.whenReleased(new toggleClaw());
//        
        button3 = new JoystickButton(driverStick, 3);
//        button3.whenPressed(new CameraToggle());
//        
        vulcanGearScore = new JoystickButton(driverStick, 4);
        vulcanGearScore.whenPressed(new VulcanGearScore());
//        
//        xxx = new JoystickButton(driverStick, 5);
        
        toggleClaw = new JoystickButton(driverStick, 6);
        toggleClaw.whenReleased(new toggleClaw());
        
        TsunamiDown = new JoystickButton(driverStick, 5);
        
//        VulcanGearMode = new JoystickButton(driverStick, 8);
//    	VulcanGearMode.whenPressed(new VulcanGearMode());
//
//        VulcanBallMode = new JoystickButton(driverStick, 7);
//    	VulcanBallMode.whenPressed(new VulcanBallMode());
//    	
//        VulcanGearScore = new JoystickButton(driverStick, 6);
//    	VulcanGearScore.whenPressed(new VulcanGearScore());
        VulcanBallMode = new JoystickButton(driverStick, 7);
    	VulcanBallMode.whenPressed(new VulcanBallMode());
    	
        VulcanGearMode = new JoystickButton(driverStick, 8);
    	VulcanGearMode.whenPressed(new VulcanGearMode());

    	BallDoorToggle = new JoystickButton(driverStick, 10);
    	BallDoorToggle.whenPressed(new BallDoorToggle());
    	
    	ClawForceOpen = new JoystickButton(driverStick, 9);
    	ClawForceOpen.whileHeld(new ClawOpenForce());
    	
		opButton1 = new JoystickButton(opStick, 1);
		opButton1.whenPressed(new autoClawClose());
		opButton2 = new JoystickButton(opStick, 2);
		opButton2.whileHeld(new TsunamiControl(TsunamiDirection.DOWN,0.5));
//		opButton2.whileHeld(new SetLED(0,0,255));
        toggleLED = new JoystickButton(opStick, 3);
        toggleLED.whenPressed(new toggleLED());
		opButton4 = new JoystickButton(opStick, 4);
		opButton4.whileHeld(new TsunamiControl(TsunamiDirection.DOWN,1));
//		opButton4.whileHeld(new SetLED(0,0,255));
//		opButton5 = new JoystickButton(driverStick, 3);
//		opButton5.whenPressed(new CameraToggle());
		opButton9 = new JoystickButton(opStick, 9);
		// Enable analog scaling
		

        
        toggleDeployClaw = new JoystickButton(driverStick, 5);
        toggleDeployClaw.whenReleased(new toggleDeployClaw());
        
        gearSensor = new DigitalSensor(3);
        gearSensor.whenActive(new gearRollerSetSpeed(0));
        

    }
    public Joystick getdriverStick() {
        return driverStick;
    }
}