package org.usfirst.frc.team2609.robot;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.usfirst.frc.team2609.robot.commands.Auto1;
import org.usfirst.frc.team2609.robot.commands.HockeyStick;
import org.usfirst.frc.team2609.robot.commands.Swivel;
import org.usfirst.frc.team2609.robot.commands.toggleClaw;
import org.usfirst.frc.team2609.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2609.robot.subsystems.Logger;
import org.usfirst.frc.team2609.robot.subsystems.Shifter;
import org.usfirst.frc.team2609.robot.subsystems.VulcanClaw;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import org.usfirst.frc.team2609.robot.commands.*;


public class Robot extends IterativeRobot {
	public static Drivetrain drivetrain;
	public static Shifter shifter;
	public static VulcanClaw vulcanclaw;
	public static OI oi;
//	public static VulcanGearGrab vulcangeargrab = new VulcanGearGrab();
	private Logger logger;
	boolean gearSensorOld;
    Command autonomousCommand;
    SendableChooser chooser;
    public static NetworkTable table;
    
    //public static double centerX = 0;
    
    public void robotInit() {
		RobotMap.init();// put this here when imports don't work / robots don't quit
		oi = new OI();

		SmartDashboard.putNumber("Drive P: ", 0.003);
    	SmartDashboard.putNumber("Drive I: ", 0.001);
    	SmartDashboard.putNumber("Drive D: ", 0.01);
    	SmartDashboard.putNumber("Drive Max: ", 0.8);
    	SmartDashboard.putNumber("Drive Eps: ", 1);
    	
		SmartDashboard.putNumber("Gyro P: ", 0.02);
    	SmartDashboard.putNumber("Gyro I: ", 0.000);
    	SmartDashboard.putNumber("Gyro D: ", 0.0);
    	SmartDashboard.putNumber("Gyro Max: ", 0.2);
		
        SmartDashboard.putNumber("turn P: ",0.03);
        SmartDashboard.putNumber("turn I: ",0.001);
        SmartDashboard.putNumber("turn D: ",0.0);
        SmartDashboard.putNumber("turn Max: ",0.25);
        SmartDashboard.putNumber("turn Eps: ",1);
        
        SmartDashboard.putNumber("camera P: ",0.01);
        SmartDashboard.putNumber("camera I: ",0.000);
        SmartDashboard.putNumber("camera D: ",0.0);
        SmartDashboard.putNumber("camera Max: ",0.3);
        SmartDashboard.putNumber("camera Eps: ",1);
        SmartDashboard.putNumber("camera Delay: ",0.01);
        
        SmartDashboard.putNumber("gyroCamera P: ",0.03);
        SmartDashboard.putNumber("gyroCamera I: ",0.001);
        SmartDashboard.putNumber("gyroCamera D: ",0.0);
        SmartDashboard.putNumber("gyroCamera Max: ",0.5);
        SmartDashboard.putNumber("gyroCamera Eps: ",1);
        boolean valueVision = false;
        SmartDashboard.putBoolean("vision", valueVision);
        SmartDashboard.putNumber("Launcher Speed", 0);

        shifter = new Shifter();
		drivetrain = new Drivetrain();
		vulcanclaw = new VulcanClaw();
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", new Auto1());
        chooser.addObject("Hockey Stick", new HockeyStick());
        chooser.addObject("Swivel", new Swivel());
        SmartDashboard.putData("Auto mode", chooser);
        this.logger = logger.getInstance();
//      chooser.addObject("My Auto", new MyAutoCommand());  
        table = NetworkTable.getTable("RaspberryPi");
        table.putNumber("display", 1); //1 will Enable display outputs on the raspberry pi, will crash if no monitor connected to it.
        RobotMap.vulcanClaw.set(DoubleSolenoid.Value.kReverse);
        RobotMap.vulcanDeploy.set(DoubleSolenoid.Value.kForward);
    }
	
    public void disabledInit(){
    }
	
	@SuppressWarnings("deprecation")
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		this.logger.close();
//        if (RobotMap.ds.getAlliance() == DriverStation.Alliance.Red) {
//        	RobotMap.frameLights.showRGB(255, 0, 0);
//        } else if (RobotMap.ds.getAlliance() == DriverStation.Alliance.Blue) {
//        	RobotMap.frameLights.showRGB(0, 0, 255);
//        } else if (RobotMap.ds.getAlliance() == DriverStation.Alliance.Invalid) {
//        	RobotMap.frameLights.showRGB(255, 200, 0); // yellow
//        }
		Double readyVulcanClaw = table.getNumber("readyVulcanClaw",2);
        if (readyVulcanClaw == 1) {
        	RobotMap.frameLights.showRGB(255, 200, 0); // yellow For the peanut gallery
			//RobotMap.frameLights.showRGB(0, 255, 0);//set led's to green when ready i think that yellow means go
		}
		else{
			RobotMap.frameLights.showRGB(156,39,176);//set led's to purple otherwise yes this is good
		}
		SmartDashboard.putBoolean("DIO9", RobotMap.dio9.get());
		SmartDashboard.putBoolean("gearSensor", RobotMap.gearSensor.get());
		SmartDashboard.putNumber("Gyro getAngle", RobotMap.ahrs.getAngle());
		SmartDashboard.putNumber("Gyro getYaw", RobotMap.ahrs.getYaw());
		SmartDashboard.putNumber("driveEncLeft.getDistance()", RobotMap.driveTalonLeft1.getPosition());
		SmartDashboard.putNumber("driveEncRight.getDistance()", RobotMap.driveTalonRight1.getPosition());

		SmartDashboard.putBoolean("RobotMap.clawCloseSensor.get()", RobotMap.clawCloseSensor.get());
		SmartDashboard.putBoolean("RobotMap.clawUpSensor.get()", RobotMap.clawUpSensor.get());
		SmartDashboard.putBoolean("RobotMap.clawDownSensor.get()", RobotMap.clawDownSensor.get());
	}
    public void autonomousInit() {
        Robot.drivetrain.gyroYawZero();
        Robot.drivetrain.resetDriveEncoders();
        autonomousCommand = (Command) chooser.getSelected();
        this.logger.openFile();
        if (autonomousCommand != null) autonomousCommand.start();
        
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("Gyro getYaw", RobotMap.ahrs.getYaw());
        //SmartDashboard.putNumber("driveEncLeft.getDistance()", RobotMap.driveEncLeft.getDistance());
		//SmartDashboard.putNumber("driveEncRight.getDistance()", RobotMap.driveEncRight.getDistance());
		//SmartDashboard.putNumber("driveEncLeft.getRate()", RobotMap.driveEncLeft.getRate());
		//SmartDashboard.putNumber("driveEncRight.getRate()", RobotMap.driveEncRight.getRate());
		SmartDashboard.putNumber("driveVictorLeft1.get()", RobotMap.driveTalonLeft1.get());
		SmartDashboard.putNumber("driveVictorRight1.get()", RobotMap.driveTalonRight1.get());
		
        this.logger.logAll(); // write to logs
        
		
    }

    public void teleopInit() {
        if (autonomousCommand != null) autonomousCommand.cancel();
        //EncReset(); todo
        Robot.drivetrain.resetDriveEncoders();
        Robot.drivetrain.gyroYawZero();
        RobotMap.frameLights.showRGB(255, 0, 0);//set led's to red for start of match
        this.logger.openFile();
        //RobotMap.serialport.reset();
		//RobotMap.serialport.writeString(":85");
		
    }

    @SuppressWarnings("deprecation")
	public void teleopPeriodic() {
    	SmartDashboard.putNumber("Gyro getYaw", RobotMap.ahrs.getYaw());
    	SmartDashboard.putNumber("driveEncLeft.getDistance()", RobotMap.driveTalonLeft1.getPosition());
		SmartDashboard.putNumber("driveEncRight.getDistance()", RobotMap.driveTalonRight1.getPosition());
		
		SmartDashboard.putBoolean("RobotMap.clawCloseSensor.get()", RobotMap.clawCloseSensor.get());
		SmartDashboard.putBoolean("RobotMap.clawUpSensor.get()", RobotMap.clawUpSensor.get());
		SmartDashboard.putBoolean("RobotMap.clawDownSensor.get()", RobotMap.clawDownSensor.get());
		
		Scheduler.getInstance().run();
		if (!gearSensorOld){
			if (RobotMap.gearSensor.get() && !RobotMap.clawDownSensor.get()){
//				vulcangeargrab.start();
				new VulcanGearGrab().start();
			}
		}
		gearSensorOld = RobotMap.gearSensor.get();
		Double readyVulcanClaw = table.getNumber("readyVulcanClaw", 0);
		if (readyVulcanClaw == 1) {
			RobotMap.frameLights.showRGB(255, 200, 0); // yellow For the peanut gallery
			//RobotMap.frameLights.showRGB(0, 255, 0);//set led's to green when ready i think that yellow means go
		}
		else{
			RobotMap.frameLights.showRGB(156,39,176);//set led's to red otherwise yes this is good
		}
        this.logger.logAll(); // write to logs
        Joystick driveStick = new Joystick(0);
		double deadZone = 0.15;
		double X = -driveStick.getRawAxis(0);
        double Y = driveStick.getRawAxis(1);
        if ((Math.abs(-driveStick.getRawAxis(0))<deadZone) && (Math.abs(-driveStick.getRawAxis(1))<deadZone)){
        	X = 0;
        	Y = 0;
        }
        /*if (Math.abs(-driveStick.getRawAxis(1))<deadZone){
        	Y = 0;
        }*/
        double leftOutput;
        double rightOutput;
        if (Y > 0) {
            if (X > 0.0) {
                leftOutput = Math.pow(Y, 1) - Math.pow(X, 1);
                rightOutput = Math.max(Math.pow(Y, 1), Math.pow(X, 1));
            } else {
                leftOutput = Math.max(Math.pow(Y, 1), -(Math.pow(X, 1)));
                rightOutput = Math.pow(Y, 1) + (Math.pow(X, 1));
            }
        } else{
            if (X > 0.0) {
                leftOutput = -Math.max(-(Math.pow(Y, 1)), Math.pow(X, 1));
                rightOutput = (Math.pow(Y, 1)) + Math.pow(X, 1);
            } else {
            	//this is also vvv imborktant
                leftOutput = (Math.pow(Y, 1)) - (Math.pow(X, 1));
                rightOutput = -Math.max(-(Math.pow(Y, 1)), -(Math.pow(X, 1)));
            }
            	

        }
        RobotMap.ballIntake.set(-driveStick.getRawAxis(3));
        	
//        
            RobotMap.driveTalonLeft1.set(-leftOutput);
            RobotMap.driveTalonRight1.set(rightOutput);
//            RobotMap.launcherVictor.set(SmartDashboard.getNumber("Launcher Speed", 0));
            
            
}
    
    public void testPeriodic() {
        LiveWindow.run();
    }
}
