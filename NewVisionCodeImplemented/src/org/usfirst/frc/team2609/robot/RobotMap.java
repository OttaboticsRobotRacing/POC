package org.usfirst.frc.team2609.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;
import com.mindsensors.CANLight;

public class RobotMap {
	public static CANTalon driveTalonLeft1;
	public static CANTalon driveTalonLeft2;
	public static CANTalon driveTalonRight1;
	public static CANTalon driveTalonRight2;
	public static CANTalon ballIntake;
	
    public static DoubleSolenoid shifter;
    public static DoubleSolenoid vulcanDeploy;
    public static DoubleSolenoid vulcanClaw;
    
	public static CANTalon prototype;
	public static Encoder driveEncLeft;
	public static Encoder driveEncRight;
	public static AHRS ahrs;
	public static SerialPort serialport;
	public static Relay ringLED;
	public static DigitalInput dio9;
	public static DigitalInput gearSensor;
	
    public static CANLight frameLights;
    public static DriverStation ds;
    
    public static DigitalInput clawCloseSensor;
    public static DigitalInput clawUpSensor;
    public static DigitalInput clawDownSensor;

	public static void init() {
		// DONT DEFINE THE OBJECT TYPE HERE!!1111! actually you cant define an object that is part of a spectrum!
		//ONE ex. Victor  driveVictorLeft1 = new Victor(0);
		try {
            ahrs = new AHRS(SPI.Port.kMXP);
            LiveWindow.addSensor("Drivetrain", "AHRS", ahrs);
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
    	//serialport = new SerialPort(9600, SerialPort.Port.kUSB);
    	//SerialPort serial = new SerialPort(115200, SerialPort.Port.kUSB);
		driveTalonRight1 = new CANTalon(1);
		driveTalonRight2 = new CANTalon(2);
		driveTalonLeft1 = new CANTalon(3);
		driveTalonLeft2 = new CANTalon(4);
		ballIntake = new CANTalon(5);
		
		driveTalonRight2.changeControlMode(TalonControlMode.Follower);
		driveTalonLeft2.changeControlMode(TalonControlMode.Follower);
		driveTalonRight2.set(1); // Follows talon 1 talon is the second best talon
		driveTalonLeft2.set(3); // Follows talon 3 talon 3 is not the best talon
		
		driveTalonRight1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		driveTalonRight1.configEncoderCodesPerRev(250);
		driveTalonRight1.reverseSensor(false);
		driveTalonRight1.reverseOutput(false);
		driveTalonLeft1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		driveTalonLeft1.configEncoderCodesPerRev(250);
		driveTalonLeft1.reverseSensor(false);
		driveTalonLeft1.reverseOutput(false);


//		prototype = new CANTalon(5);
//		prototype.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);	
//		prototype.reverseSensor(false);
        shifter = new DoubleSolenoid(0, 1, 0);
        vulcanClaw = new DoubleSolenoid(0, 7, 6);
        vulcanDeploy = new DoubleSolenoid(0, 4, 5);
		
		ringLED = new Relay(0);
		ringLED.setDirection(Relay.Direction.kForward);
		dio9 = new DigitalInput(9);
		gearSensor = new DigitalInput(3);
		
		clawCloseSensor = new DigitalInput(0);
		clawUpSensor = new DigitalInput(1);
		clawDownSensor = new DigitalInput(2);
		
        frameLights = new CANLight(7);
        ds = DriverStation.getInstance();
	}
}
