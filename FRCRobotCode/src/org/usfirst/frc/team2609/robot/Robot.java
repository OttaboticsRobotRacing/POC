package org.usfirst.frc.team2609.robot;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

import org.usfirst.frc.team2609.robot.subsystems.AutoClaw;
import org.usfirst.frc.team2609.robot.subsystems.BallIntake;
import org.usfirst.frc.team2609.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2609.robot.subsystems.LEDuino;
import org.usfirst.frc.team2609.robot.subsystems.LedControl;
import org.usfirst.frc.team2609.robot.subsystems.Logger;
import org.usfirst.frc.team2609.robot.subsystems.R03;
import org.usfirst.frc.team2609.robot.subsystems.Shifter;
import org.usfirst.frc.team2609.robot.subsystems.Tsunami;
import org.usfirst.frc.team2609.robot.subsystems.VulcanClaw;

import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import org.usfirst.frc.team2609.enums.TalonState;
import org.usfirst.frc.team2609.enums.VulcanDeployState;
import org.usfirst.frc.team2609.robot.commands.autons.*;
import org.usfirst.frc.team2609.robot.commands.vulcanClaw.VulcanGearGrab;

public class Robot extends IterativeRobot {

	public static LedControl LedControl;
	public static Drivetrain drivetrain;
	public static Shifter shifter;
	public static VulcanClaw vulcanclaw;
	public static OI oi;
	public static Tsunami tsunami;
	public static BallIntake ballIntake;
	public static AutoClaw autoClaw;
	public static R03 r03;
	public static LEDuino leduino;
//	public static VulcanGearGrab vulcangeargrab = new VulcanGearGrab();
	private Logger logger;
	boolean gearSensorOld;
    Command autonomousCommand;
    SendableChooser chooser;
    public static NetworkTable table;
	double autonDistance1 = 0;
	double autonDistance2 = 0;
	double autonDistance3 = 0;
	double autonAngle1 = 0;
	double autonAngle2 = 0;
	double autonAngle3 = 0;
	
	int sensorCounter = 0;
//	double autonHeading1 = 0;
//	double autonHeading2 = 0;
//	double autonHeading3 = 0;
    //public static double centerX = 0;
	public static double angleToDriveInitial = 0;
	public static double distanceToDriveInitial = 0;
    
    public void robotInit() {
		RobotMap.init();// put this here when imports don't work / robots don't quit
		oi = new OI();

		SmartDashboard.putNumber("Drive P: ", 0.02); //low gear 0.02, 0.0005 for 30" to 100"
    	SmartDashboard.putNumber("Drive I: ", 0.0005);
    	SmartDashboard.putNumber("Drive D: ", 0.0);
    	SmartDashboard.putNumber("Drive Max: ", 0.8);
    	SmartDashboard.putNumber("Drive Eps: ", 1.0);
    	SmartDashboard.putNumber("Drive DR: ", 1.0);
    	SmartDashboard.putNumber("Drive DC: ", 5);
    	
		SmartDashboard.putNumber("Gyro P: ", 0.05);
    	SmartDashboard.putNumber("Gyro I: ", 0.000);
    	SmartDashboard.putNumber("Gyro D: ", 0.0);
    	SmartDashboard.putNumber("Gyro Max: ", 0.2);
//    	SmartDashboard.putNumber("Gyro Eps: ", 0.2); If needed later

		SmartDashboard.putNumber("Curve P: ", 0.01);
    	SmartDashboard.putNumber("Curve I: ", 0.000);
    	SmartDashboard.putNumber("Curve D: ", 0.0);
    	SmartDashboard.putNumber("Curve Max: ", 0.2);
//    	SmartDashboard.putNumber("Curve Eps: ", 0.2); If needed later
    	
        SmartDashboard.putNumber("turn P: ",0.01);
        SmartDashboard.putNumber("turn I: ",0.0015);
        SmartDashboard.putNumber("turn D: ",0.0);
        SmartDashboard.putNumber("turn Max: ",1.0);
        SmartDashboard.putNumber("turn Eps: ",1.0);
        SmartDashboard.putNumber("turn DR: ",1.0);
        SmartDashboard.putNumber("turn DC: ",5);

        SmartDashboard.putNumber("LED Colour: ", 1);
        SmartDashboard.putBoolean("LED Flash: ", false);
        
        boolean valueVision = false;
        SmartDashboard.putBoolean("vision", valueVision);
    	SmartDashboard.putNumber("climber speed", 1);

		SmartDashboard.putBoolean("Disable claw", true);
        shifter = new Shifter();
		drivetrain = new Drivetrain();
		vulcanclaw = new VulcanClaw();
		LedControl = new LedControl();
		ballIntake = new BallIntake();
		autoClaw = new AutoClaw();
        chooser = new SendableChooser();
        leduino = new LEDuino(3);
        r03 = new R03();
        chooser.addDefault("Default Auto - Dont move", new Auto1());
        chooser.addObject("Straight Peg Red", new StraightPegRed());
        chooser.addObject("Straight Peg Blue", new StraightPegBlue());
        chooser.addObject("Left peg Red", new LeftPRed());
        chooser.addObject("Right peg Red", new RightPRed());
        chooser.addObject("Left peg Blue", new LeftPBlue());
        chooser.addObject("Right peg Blue", new RightPBlue());
        chooser.addObject("double Dragon Blue", new doubleDragonBlue());
        chooser.addObject("double Dragon Red", new doubleDragonRed());
        chooser.addObject("Right peg Red Reverse", new RightPRedReverse());
        chooser.addObject("Left peg Red Reverse", new LeftPRedReverse());
        chooser.addObject("Right peg Blue Reverse", new RightPBlueReverse());
        chooser.addObject("Left peg Blue Reverse", new LeftPBlueReverse());
        chooser.addObject("Straight Peg Red Reverse", new StraightPegRedReverse());
        chooser.addObject("Straight Peg Blue Reverse", new StraightPegBlueReverse());
        SmartDashboard.putData("Auto mode", chooser);
        this.logger = Logger.getInstance(); // Changed from logger.getInstance to Logger.getInstance at Eclipse insistence
        table = NetworkTable.getTable("RaspberryPi");
        table.putNumber("display", 0); //1 will Enable display outputs on the raspberry pi, will crash if no monitor connected to it.
		Robot.table.putNumber("rPiCam", 1.0); // Default to field cam
        RobotMap.shifter.set(DoubleSolenoid.Value.kForward); //Low gear
//        RobotMap.vulcanClaw.set(DoubleSolenoid.Value.kReverse); //Closed Claw
//        RobotMap.vulcanDeploy.set(DoubleSolenoid.Value.kReverse); //Claw up
    }
	
    public void disabledInit(){
		shifter.high();
		SmartDashboard.putNumber("LED Colour: ", 4);
        Robot.drivetrain.resetDriveEncoders();
		
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		//LedControl.setLed(255, 255, 0);
		this.logger.close();
		Robot.LedControl.setLed();
//        if (RobotMap.ds.getAlliance() == DriverStation.Alliance.Red) {
//        	RobotMap.frameLights.showRGB(255, 0, 0);
//        } else if (RobotMap.ds.getAlliance() == DriverStation.Alliance.Blue) {
//        	RobotMap.frameLights.showRGB(0, 0, 255);
//        } else if (RobotMap.ds.getAlliance() == DriverStation.Alliance.Invalid) {
//        	RobotMap.frameLights.showRGB(255, 200, 0); // yellow
//        }
		Double readyVulcanClaw = table.getNumber("readyVulcanClaw",2);
        if (readyVulcanClaw == 1) {
        	//RobotMap.frameLights.showRGB(255, 200, 0); // yellow For the peanut gallery
			//RobotMap.frameLights.showRGB(0, 255, 0);//set led's to green when ready i think that yellow means go
		}
		else{
			//RobotMap.frameLights.showRGB(156,39,176);//set led's to purple otherwise yes this is good
		}
        table.putNumber("gyro.getYaw", RobotMap.ahrs.getYaw());
        
		SmartDashboard.putBoolean("gearSensor", OI.gearSensor.get());
		SmartDashboard.putNumber("Gyro getYaw", RobotMap.ahrs.getYaw());
    	SmartDashboard.putNumber("driveEncLeft.getDistance()", RobotMap.driveTalonLeft1.getPosition());
    	SmartDashboard.putNumber("driveEncRight.getDistance()", RobotMap.driveTalonRight1.getPosition());
    	SmartDashboard.putNumber("driveEncLeft.getEncPosition()", RobotMap.driveTalonLeft1.getEncPosition());
    	SmartDashboard.putNumber("driveEncRight.getEncPosition()", RobotMap.driveTalonRight1.getEncPosition());
    	SmartDashboard.putNumber("ultra.getRangeInches()", RobotMap.ultra.getRangeInches());
		
		SmartDashboard.putBoolean("RobotMap.clawCloseSensor.get()", RobotMap.clawCloseSensor.get());
		SmartDashboard.putBoolean("RobotMap.clawUpSensor.get()", RobotMap.clawUpSensor.get());
		SmartDashboard.putBoolean("RobotMap.clawDownSensor.get()", RobotMap.clawDownSensor.get());
		SmartDashboard.putBoolean("little miss claw", RobotMap.clawMissSensor.get());
		SmartDashboard.putBoolean("CompToggle", true);
		
		
		SmartDashboard.putBoolean("avalanche limit Fwd", RobotMap.tsunamiMotor.isFwdLimitSwitchClosed());
		SmartDashboard.putBoolean("avalanche limit Rev", RobotMap.tsunamiMotor.isRevLimitSwitchClosed());
		SmartDashboard.putBoolean("Gear launcher disable", false);
		SmartDashboard.putBoolean("Disable claw", true);
		SmartDashboard.putNumber("High gear arcade scaling factor", 0.6);
//		if(OI.opButton5.get()){
//			new CameraToggle().start();
//		}
//		if(OI.button3.get()){
//	    	double currentState = Robot.table.getNumber("rPiCam", 0.0);
//	    	if(currentState == 1){
//	    		Robot.table.putNumber("rPiCam", 0.0);
//	    	}else{
//	    		Robot.table.putNumber("rPiCam", 1.0);
//	    	}
//		}
	}
    public void autonomousInit() {
        Robot.drivetrain.gyroYawZero();
        Robot.drivetrain.resetDriveEncoders();
        RobotMap.shifter.set(DoubleSolenoid.Value.kForward); //Low gear
        RobotMap.autoClaw.set(DoubleSolenoid.Value.kForward); //close auto claw
		SmartDashboard.putBoolean("CompToggle", false);

    	RobotMap.hodor.set(DoubleSolenoid.Value.kForward);
        RobotMap.vulcanClaw.set(DoubleSolenoid.Value.kReverse); //Closed Claw
        RobotMap.vulcanDeploy.set(DoubleSolenoid.Value.kReverse); //Claw up
        autonomousCommand = (Command) chooser.getSelected();
        this.logger.openFile();

		shifter.low();
        if (autonomousCommand != null) autonomousCommand.start();
 

    	
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        drivetrain.handleTalonState();
        SmartDashboard.putNumber("Gyro getYaw", RobotMap.ahrs.getYaw());
    	SmartDashboard.putNumber("driveEncLeft.getDistance()", RobotMap.driveTalonLeft1.getPosition());
    	SmartDashboard.putNumber("driveEncRight.getDistance()", RobotMap.driveTalonRight1.getPosition());
    	SmartDashboard.putNumber("driveEncLeft.getOutputVoltage()", RobotMap.driveTalonLeft1.getOutputVoltage());
    	SmartDashboard.putNumber("driveEncRight.getOutputVoltage()", RobotMap.driveTalonRight1.getOutputVoltage());

        table.putNumber("gyro.getYaw", RobotMap.ahrs.getYaw());
    	//RobotMap.ringLED.set(Relay.Value.kReverse);
        this.logger.logAll(); // write to logs
        //SmartDashboard.putNumber("driveEncLeft.getDistance()", RobotMap.driveEncLeft.getDistance());
		//SmartDashboard.putNumber("driveEncRight.getDistance()", RobotMap.driveEncRight.getDistance());
		//SmartDashboard.putNumber("driveEncLeft.getRate()", RobotMap.driveEncLeft.getRate());
		//SmartDashboard.putNumber("driveEncRight.getRate()", RobotMap.driveEncRight.getRate());
		SmartDashboard.putNumber("driveTalonLeft1.getEncPosition()", RobotMap.driveTalonLeft1.getEncPosition());
		SmartDashboard.putNumber("driveTalonRight1.getEncPosition()", RobotMap.driveTalonRight1.getEncPosition());
		SmartDashboard.putNumber("driveVictorLeft1.get()", RobotMap.driveTalonLeft1.get());
		SmartDashboard.putNumber("driveVictorRight1.get()", RobotMap.driveTalonRight1.get());

        if ((OI.gearSensor.get() && Robot.vulcanclaw.getDeployState() == VulcanDeployState.DOWN) && sensorCounter > 5 && !OI.button3.get()){
			new VulcanGearGrab().start();
			sensorCounter = 0;
		}else if ((OI.gearSensor.get()&& Robot.vulcanclaw.getDeployState() == VulcanDeployState.DOWN) && sensorCounter <=5){
			sensorCounter++;
		}else if(!(OI.gearSensor.get()&& Robot.vulcanclaw.getDeployState() == VulcanDeployState.DOWN)){
			sensorCounter = 0;
		}

		RobotMap.compressor.setClosedLoopControl(SmartDashboard.getBoolean("CompToggle", true));

//        this.logger.logAll(); // write to logs
        
		
    }

    public void teleopInit() {
        if (autonomousCommand != null) autonomousCommand.cancel();
        Robot.drivetrain.resetDriveEncoders();
        Robot.drivetrain.gyroYawZero();
//        RobotMap.frameLights.showRGB(255, 0, 0);//set led's to red for start of match
//        this.logger.openFile();
        //RobotMap.serialport.reset();
		//RobotMap.serialport.writeString(":85");
		SmartDashboard.putBoolean("CompToggle", true);
    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonLeft1.setVoltageRampRate(10000);
    	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonRight1.setVoltageRampRate(10000);
    	
//        RobotMap.vulcanClaw.set(DoubleSolenoid.Value.kReverse);
        RobotMap.vulcanDeploy.set(DoubleSolenoid.Value.kReverse);
		shifter.high();
		RobotMap.gearPusher.set(DoubleSolenoid.Value.kForward);
		RobotMap.hodor.set(DoubleSolenoid.Value.kReverse); //open auto claw

        RobotMap.driveTalonLeft1.setEncPosition(0);
        RobotMap.driveTalonRight1.setEncPosition(0);
        
        RobotMap.talonState = TalonState.ARCADE;
        
        LedControl.trackLED(true);
    }

	public void teleopPeriodic() {
		Scheduler.getInstance().run();
        drivetrain.handleTalonState();

		SmartDashboard.putBoolean("gearSensor", OI.gearSensor.get());
    	SmartDashboard.putNumber("Gyro getYaw", RobotMap.ahrs.getYaw());
    	SmartDashboard.putNumber("driveEncLeft.getDistance()", RobotMap.driveTalonLeft1.getPosition());
    	SmartDashboard.putNumber("driveEncRight.getDistance()", RobotMap.driveTalonRight1.getPosition());
    	SmartDashboard.putNumber("driveEncLeft.getOutputVoltage()", RobotMap.driveTalonLeft1.getOutputVoltage());
    	SmartDashboard.putNumber("driveEncRight.getOutputVoltage()", RobotMap.driveTalonRight1.getOutputVoltage());
    	SmartDashboard.putNumber("driveEncLeft.getEncPosition()", RobotMap.driveTalonLeft1.getEncPosition());
    	SmartDashboard.putNumber("driveEncRight.getEncPosition()", RobotMap.driveTalonRight1.getEncPosition());
		SmartDashboard.putBoolean("RobotMap.clawCloseSensor.get()", RobotMap.clawCloseSensor.get());
		SmartDashboard.putBoolean("RobotMap.clawUpSensor.get()", RobotMap.clawUpSensor.get());
		SmartDashboard.putBoolean("RobotMap.clawDownSensor.get()", RobotMap.clawDownSensor.get());
//		SmartDashboard.putBoolean("ClawClosedSolenoid", RobotMap.vulcanClaw.get());
    	SmartDashboard.putNumber("driveTalonLeft1.getOutputCurrent", RobotMap.driveTalonLeft1.getOutputCurrent());
    	SmartDashboard.putNumber("driveTalonLeft2.getOutputCurrent", RobotMap.driveTalonLeft2.getOutputCurrent());
    	SmartDashboard.putNumber("driveTalonRight1.getOutputCurrent", RobotMap.driveTalonRight1.getOutputCurrent());
    	SmartDashboard.putNumber("driveTalonRight2.getOutputCurrent", RobotMap.driveTalonRight2.getOutputCurrent());
    	SmartDashboard.putNumber("RobotMap.tsunamiMotor.getOutputCurrent()", RobotMap.tsunamiMotor.getOutputCurrent());
    	SmartDashboard.putNumber("RobotMap.tsunamiMotor.getBusVoltage()", RobotMap.tsunamiMotor.getOutputVoltage());
    	SmartDashboard.putNumber("RobotMap.tsunamiMotor.getOutputCurrent()", RobotMap.tsunamiMotor.getOutputCurrent());
//    	SmartDashboard.putNumber("CurrentChannel11", RobotMap.pdp.getCurrent(1));
    	
        table.putNumber("gyro.getYaw", RobotMap.ahrs.getYaw());
        
        if (RobotMap.vulcanClaw.get() == DoubleSolenoid.Value.kForward){
        	SmartDashboard.putBoolean("ClawClosedSolenoid", true);
        }
        else{
        	SmartDashboard.putBoolean("ClawClosedSolenoid", false);
        }
        
        LedControl.trackLED(true);
		
//    	SmartDashboard.putNumber("RobotMap.tsunamiMotor.getBusVoltage()", RobotMap.ballIntake.getOutputVoltage());
//    	SmartDashboard.putNumber("RobotMap.tsunamiMotor.getOutputCurrent()", RobotMap.ballIntake.getOutputCurrent());
//		
//
//		SmartDashboard.putNumber("driveTalonLeft1.getEncPosition()", RobotMap.driveTalonLeft1.getEncPosition());
//		SmartDashboard.putNumber("driveTalonRight1.getEncPosition()", RobotMap.driveTalonRight1.getEncPosition());
    	
//        if ((OI.gearSensor.get() && !RobotMap.clawDownSensor.get()) && sensorCounter > 5 && !OI.button3.get()){
//			new VulcanGearGrab().start();
//			sensorCounter = 0;
//		}else if ((OI.gearSensor.get()&& !RobotMap.clawDownSensor.get()) && sensorCounter <=5){
//			sensorCounter++;
//		}else if(!(OI.gearSensor.get()&& !RobotMap.clawDownSensor.get())){
//			sensorCounter = 0;
//		}
        if ((VulcanClaw.getGearSensor() && Robot.vulcanclaw.getDeployState() == VulcanDeployState.DOWN) && sensorCounter > 5 && !OI.button3.get()){
			new VulcanGearGrab().start();
			sensorCounter = 0;
		}else if ((VulcanClaw.getGearSensor()&& Robot.vulcanclaw.getDeployState() == VulcanDeployState.DOWN) && sensorCounter <=5){
			sensorCounter++;
		}else if(!(VulcanClaw.getGearSensor()&& Robot.vulcanclaw.getDeployState() == VulcanDeployState.DOWN)){
			sensorCounter = 0;
		}
		RobotMap.compressor.setClosedLoopControl(SmartDashboard.getBoolean("CompToggle", true));
		Robot.LedControl.setLed();
		
		if (OI.gearSensor.get()){
			SmartDashboard.putNumber("LED Colour: ", 2);
//			LedControl.setLed(0,255,0);
		}
		else{
			SmartDashboard.putNumber("LED Colour: ", 1);
//			LedControl.setLed(255,0,0);
		}
		gearSensorOld = OI.gearSensor.get();
		
        RobotMap.ballIntake.set(-RobotMap.Dandyboy.getRawAxis(3));

		if(OI.button3.get() ){
			vulcanclaw.gearRollerSetSpeed(0.0); // stop rollers
		}
		else if (!RobotMap.clawDownSensor.get() && Robot.vulcanclaw.getDeployState() == VulcanDeployState.DOWN){
			// Claw down and open
			vulcanclaw.gearRollerSetSpeed(0.8); // start rollers
		}
		else if(!RobotMap.clawUpSensor.get() || Robot.vulcanclaw.getDeployState() == VulcanDeployState.UP){
			// Claw up for whatever reason
			vulcanclaw.gearRollerSetSpeed(0.0); // stop rollers
		}
		
//		vulcanclaw.gearRollerSetSpeed(OI.opStick.getRawAxis(1));
		
//    	if(!SmartDashboard.getBoolean("Gear launcher disable", false) && !RobotMap.clawUpSensor.get() && RobotMap.clawCloseSensor.get() && RobotMap.clawMissSensor.get()){
//    		RobotMap.gearPusher.set(DoubleSolenoid.Value.kForward);
//    	}
//    	else if(!SmartDashboard.getBoolean("Gear launcher disable", false) && RobotMap.clawUpSensor.get() || !RobotMap.clawCloseSensor.get() || !RobotMap.clawMissSensor.get()){
//    		RobotMap.gearPusher.set(DoubleSolenoid.Value.kReverse);
//    	}
//    	else{
//    		RobotMap.gearPusher.set(DoubleSolenoid.Value.kOff);
//    	}
		
		
//        if(OI.driverStick.getPOV() == 180){
//        	RobotMap.tsunamiMotor.set(-SmartDashboard.getNumber("climber speed", 0));
//        }
//        else if (OI.driverStick.getPOV() == 0){
//        	RobotMap.tsunamiMotor.set(SmartDashboard.getNumber("climber speed", 0));
//        }
//        else{
//        	RobotMap.tsunamiMotor.set(0);
//        }
    	//RobotMap.ringLED.set(Relay.Value.kReverse);
		
		RobotMap.tsunamiMotor.set(Math.abs(OI.driverStick.getRawAxis(3))*.5);
        	//RobotMap.ballIntake.set(-driveStick.getRawAxis(3));
        
        
        
    	RobotMap.tsunamiMotor.changeControlMode(TalonControlMode.PercentVbus);
//        if(OI.driverStick.getPOV() == 180){
//        	RobotMap.tsunamiMotor.set(-SmartDashboard.getNumber("climber speed", 0));
//        }
//        else if (OI.driverStick.getPOV() == 0){
//        	RobotMap.tsunamiMotor.set(SmartDashboard.getNumber("climber speed", 0));
//        }
//        else{
//        	RobotMap.tsunamiMotor.set(0);
//        }
        
//        System.out.println("Left: " + RobotMap.driveTalonLeft1.getEncPosition());
//        System.out.println("Right: " + RobotMap.driveTalonRight1.getEncPosition());
//        System.out.println("LeftOutput: " + leftOutput);
//        System.out.println("RightOutput: " + rightOutput);
    	if(RobotMap.shifter.get() == DoubleSolenoid.Value.kForward){
    		drivetrain.arcadeDriveLow(); // low gear arcade
    	}else{
    		drivetrain.arcadeDriveHigh();
    	}
//    	drivetrain.humanDriveLow();
    	
    	

//        
//            RobotMap.launcherVictor.set(SmartDashboard.getNumber("Launcher Speed", 0));
            
            
}
    
    public void testPeriodic() {
        LiveWindow.run();
    }
    public static void eStopMP(){
    	RobotMap.talonState = TalonState.ARCADE;
    }
}
