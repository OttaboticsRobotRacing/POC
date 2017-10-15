package org.usfirst.frc.team2609.robot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import jaci.pathfinder.modifiers.TankModifier;

import org.usfirst.frc.team2609.enums.TalonState;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;
import com.mindsensors.CANLight;

public class RobotMap {
	public static boolean disableHumanDrive = false;
	public static CANTalon driveTalonLeft1;
	public static CANTalon driveTalonLeft2;
	public static CANTalon driveTalonRight1;
	public static CANTalon driveTalonRight2;
	public static CANTalon ballIntake;
	public static CANTalon tsunamiMotor;
	public static CANTalon gearRoller;
	public static PowerDistributionPanel pdp;
	public static Compressor compressor;
	
    public static DoubleSolenoid shifter;
    public static DoubleSolenoid vulcanDeploy;
    public static DoubleSolenoid vulcanClaw;
    public static DoubleSolenoid autoClaw;
    public static DoubleSolenoid hodor;
    public static DoubleSolenoid gearPusher;
    
	public static CANTalon prototype;
	public static Encoder driveEncLeft;
	public static Encoder driveEncRight;
	public static AHRS ahrs;
	public static SerialPort serialport;
	public static Relay ringLED;
	public static DigitalInput dio9;
//	public static DigitalInput gearSensor;
	public static Ultrasonic ultra;
	
    public static CANLight frameLights;
    public static DriverStation ds;
    
    public static DigitalInput clawCloseSensor;
    public static DigitalInput clawMissSensor;
    public static DigitalInput clawOpenSensor;
    public static DigitalInput clawUpSensor;
    public static DigitalInput clawDownSensor;
    public static Joystick Dandyboy;
    
    public static AxisState axisState = AxisState.BALL;
    public static TalonState talonState = TalonState.ARCADE;
    
    public static TankModifier gearPath; 
    

	public static void init() {
		// DONT DEFINE THE OBJECT TYPE HERE!!1111! actually you cant define an object that is part of a spectrum!
		//ONE ex. Victor  driveVictorLeft1 = new Victor(0);
		try {
            ahrs = new AHRS(SPI.Port.kMXP);
            LiveWindow.addSensor("Drivetrain", "AHRS", ahrs);
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
		try {
			CameraServer.getInstance().startAutomaticCapture();
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating camera:  " + ex.getMessage(), true);
        }
    	//serialport = new SerialPort(9600, SerialPort.Port.kUSB);
    	//SerialPort serial = new SerialPort(115200, SerialPort.Port.kUSB);
		driveTalonRight1 = new CANTalon(3);
		driveTalonRight2 = new CANTalon(4);
		driveTalonLeft1 = new CANTalon(1);
		driveTalonLeft2 = new CANTalon(2);
		ballIntake = new CANTalon(5);
		tsunamiMotor = new CANTalon(6);
		gearRoller = new CANTalon(7);
		pdp = new PowerDistributionPanel();
		compressor = new Compressor(0);
		
//		driveTalonRight2.changeControlMode(TalonControlMode.Follower);
//		driveTalonLeft2.changeControlMode(TalonControlMode.Follower);
//		driveTalonRight2.set(3); // Follows talon 3 talon is the second best talon
//		driveTalonLeft2.set(1); // Follows talon 1 talon 3 is not the best talon
		
		driveTalonRight1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		driveTalonRight1.configEncoderCodesPerRev(51);//this is haram
//		driveTalonRight1.configEncoderCodesPerRev(611); // TODO: Enable if Motion profiling
		driveTalonRight1.setInverted(false);
		driveTalonRight1.reverseSensor(false);
		driveTalonRight1.reverseOutput(false);
		driveTalonRight2.setInverted(false);
		driveTalonRight2.reverseSensor(false);
		driveTalonRight2.reverseOutput(false);
		driveTalonLeft1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		driveTalonLeft1.configEncoderCodesPerRev(51);
		driveTalonLeft1.setInverted(true);
		driveTalonLeft1.reverseSensor(true);
		driveTalonLeft1.reverseOutput(false);
		driveTalonLeft2.setInverted(true);
		driveTalonLeft2.reverseSensor(true);
		driveTalonLeft2.reverseOutput(false);
//		driveTalonLeft1.configEncoderCodesPerRev(611); // TODO: Enable if Motion profiling
//		driveTalonLeft1.reverseSensor(false); // TODO: Enable if Motion profiling
		
		
		tsunamiMotor.changeControlMode(TalonControlMode.PercentVbus);

//		prototype = new CANTalon(5);
//		prototype.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);	
//		prototype.reverseSensor(false);
        shifter = new DoubleSolenoid(0, 1, 0);
        vulcanClaw = new DoubleSolenoid(0, 7, 6);
        vulcanDeploy = new DoubleSolenoid(0, 4, 5);
        autoClaw = new DoubleSolenoid(1,2,3);
        hodor = new DoubleSolenoid(0,2,3);
        
        gearPusher = new DoubleSolenoid(13,0,1);
        	
		ringLED = new Relay(0);
		ringLED.set(Relay.Value.kOff);
//		gearSensor = new DigitalInput(3);
		ultra = new Ultrasonic(8,9);
		ultra.setAutomaticMode(true);
		
		clawCloseSensor = new DigitalInput(0);
		clawMissSensor = new DigitalInput(4);
		clawOpenSensor = new DigitalInput(5);
		clawUpSensor = new DigitalInput(1);
		clawDownSensor = new DigitalInput(2);
		
        frameLights = new CANLight(12);
        ds = DriverStation.getInstance();
        Dandyboy = new Joystick(0);
	}
}
