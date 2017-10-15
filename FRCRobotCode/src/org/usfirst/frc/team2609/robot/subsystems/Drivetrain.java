package org.usfirst.frc.team2609.robot.subsystems;
import org.usfirst.frc.team2609.robot.RobotMap;
import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivetrain extends Subsystem {
	private static double drivePIDOutputLeft = 0;
	private static double drivePIDOutputRight = 0;
	private static double steerPIDOutput = 0;
	private static double throttle = 0;
	private static double turnValue = 0;
	private static double leftMtr = 0;
	private static double rightMtr = 0;
	private static double turningGain = 0;  // 0 is no change, larger has greater effect
	private static double deadZone = 0.15; //Deadband for the controller
	double[] defaultval = new double[0];
	double angleReverse = 0;
	
    public void arcadeDriveLow(){
		double X = RobotMap.Dandyboy.getRawAxis(0);
        double Y = -RobotMap.Dandyboy.getRawAxis(1);
        double scaling = SmartDashboard.getNumber("High gear arcade scaling factor", 0.8);
        double left = 0;
        double right = 0;
        // left = y+x
        // right = y-x
        if(Math.abs(X)<deadZone && Math.abs(Y)<deadZone){
            left=0;
            right =0;
        }
        else{
            left=Y+X;
            right = Y-X;
        }

    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonLeft2.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonRight2.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonLeft1.setVoltageRampRate(10000);
    	RobotMap.driveTalonRight1.setVoltageRampRate(10000);
    	RobotMap.driveTalonLeft2.setVoltageRampRate(10000);
    	RobotMap.driveTalonRight2.setVoltageRampRate(10000);
        RobotMap.driveTalonLeft1.set(left*.5);
        RobotMap.driveTalonLeft2.set(left*.5);
        RobotMap.driveTalonRight1.set(right*.5);
        RobotMap.driveTalonRight2.set(right*.5);
        
    }
    
    public void arcadeDriveHigh(){
		double X = RobotMap.Dandyboy.getRawAxis(0);
        double Y = -RobotMap.Dandyboy.getRawAxis(1);
        double scaling = SmartDashboard.getNumber("High gear arcade scaling factor", 0.6);
        double left = 0;
        double right = 0;
        // left = y+x
        // right = y-x
        if(Math.abs(X)<deadZone && Math.abs(Y)<deadZone){
            left=0;
            right =0;
        }
        else{
            left=Y+(X*(1-Math.abs(scaling*Y)));
            right = Y-(X*(1-Math.abs(scaling*Y)));
        }


    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonLeft2.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonRight2.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonLeft1.setVoltageRampRate(10000);
    	RobotMap.driveTalonRight1.setVoltageRampRate(10000);
    	RobotMap.driveTalonLeft2.setVoltageRampRate(10000);
    	RobotMap.driveTalonRight2.setVoltageRampRate(10000);
        RobotMap.driveTalonLeft1.set(left);
        RobotMap.driveTalonLeft2.set(left);
        RobotMap.driveTalonRight1.set(right);
        RobotMap.driveTalonRight2.set(right);
        
    }
    
    public void driveTank(double left, double right){
    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonLeft1.setVoltageRampRate(10000);
    	RobotMap.driveTalonRight1.setVoltageRampRate(10000);
		RobotMap.driveTalonRight1.set(right);
		RobotMap.driveTalonLeft1.set(left);
    }
    /*public void encoderDriveStraight(double driveTarget, double drivePower, double driveHeading){
		double encAvg = ((RobotMap.driveEncLeft.getDistance()+(RobotMap.driveEncRight.getDistance()*-1))*0.5);
    } */   
    public void stopDrive(){
		RobotMap.driveTalonRight1.set(0);
		RobotMap.driveTalonLeft1.set(0);
    }
    public void disableDrive(){
		RobotMap.driveTalonRight1.disable();
		RobotMap.driveTalonLeft1.disable();
    }
    public void driveStraightLeft(double encLeft, double encRight, double steerInput, SimPID encPIDLeft, SimPID encPIDRight, SimPID steerPID){
    	steerPIDOutput = -steerPID.calcPID(steerInput);
    	drivePIDOutputLeft = encPIDLeft.calcPID(encLeft);
    	drivePIDOutputRight = encPIDLeft.calcPID(encLeft);
    	System.out.println("drivePIDOutput " + drivePIDOutputLeft + drivePIDOutputRight + " steerPIDOutput " + steerPIDOutput);
    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonLeft1.setVoltageRampRate(24);
    	RobotMap.driveTalonLeft2.setVoltageRampRate(24);
    	RobotMap.driveTalonRight1.setVoltageRampRate(24);
    	RobotMap.driveTalonRight2.setVoltageRampRate(24);
    	RobotMap.driveTalonLeft1.set(drivePIDOutputLeft-steerPIDOutput);
    	RobotMap.driveTalonLeft2.set(drivePIDOutputLeft-steerPIDOutput);
        RobotMap.driveTalonRight1.set(drivePIDOutputLeft+steerPIDOutput);
        RobotMap.driveTalonRight2.set(drivePIDOutputLeft+steerPIDOutput);
    }
    public void driveStraightRight(double encLeft, double encRight, double steerInput, SimPID encPIDLeft, SimPID encPIDRight, SimPID steerPID){
    	steerPIDOutput = -steerPID.calcPID(steerInput);
    	drivePIDOutputLeft = encPIDLeft.calcPID(encRight);
    	drivePIDOutputRight = encPIDLeft.calcPID(encRight);
    	System.out.println("drivePIDOutput " + drivePIDOutputLeft + drivePIDOutputRight + " steerPIDOutput " + steerPIDOutput);
    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonLeft1.setVoltageRampRate(24);
    	RobotMap.driveTalonLeft2.setVoltageRampRate(24);
    	RobotMap.driveTalonRight1.setVoltageRampRate(24);
    	RobotMap.driveTalonRight2.setVoltageRampRate(24);
    	RobotMap.driveTalonLeft1.set(drivePIDOutputRight-steerPIDOutput);
    	RobotMap.driveTalonLeft2.set(drivePIDOutputRight-steerPIDOutput);
        RobotMap.driveTalonRight1.set(drivePIDOutputRight+steerPIDOutput);
        RobotMap.driveTalonRight2.set(drivePIDOutputRight+steerPIDOutput);
    }
    public void driveStraightReverseLeft(double encLeft, double encRight, double steerInput, SimPID encPIDLeft, SimPID encPIDRight, SimPID steerPID){
    	steerPIDOutput = -steerPID.calcPID(steerInput);
    	drivePIDOutputLeft = encPIDRight.calcPID(encLeft);
    	drivePIDOutputRight = encPIDRight.calcPID(encLeft);
    	System.out.println("drivePIDOutput " + drivePIDOutputLeft + drivePIDOutputRight + " steerPIDOutput " + steerPIDOutput);
    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonLeft1.setVoltageRampRate(24);
    	RobotMap.driveTalonLeft2.setVoltageRampRate(24);
    	RobotMap.driveTalonRight1.setVoltageRampRate(24);
    	RobotMap.driveTalonRight2.setVoltageRampRate(24);
    	RobotMap.driveTalonLeft1.set(drivePIDOutputLeft+steerPIDOutput);
    	RobotMap.driveTalonLeft2.set(drivePIDOutputLeft+steerPIDOutput);
        RobotMap.driveTalonRight1.set(drivePIDOutputLeft-steerPIDOutput);
        RobotMap.driveTalonRight2.set(drivePIDOutputLeft-steerPIDOutput);
    }

    public void driveStraightReverseRight(double encLeft, double encRight, double steerInput, SimPID encPIDLeft, SimPID encPIDRight, SimPID steerPID){
    	steerPIDOutput = -steerPID.calcPID(steerInput);
    	drivePIDOutputLeft = encPIDRight.calcPID(encRight);
    	drivePIDOutputRight = encPIDRight.calcPID(encRight);
    	System.out.println("drivePIDOutput " + drivePIDOutputLeft + drivePIDOutputRight + " steerPIDOutput " + steerPIDOutput);
    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonLeft1.setVoltageRampRate(24);
    	RobotMap.driveTalonLeft2.setVoltageRampRate(24);
    	RobotMap.driveTalonRight1.setVoltageRampRate(24);
    	RobotMap.driveTalonRight2.setVoltageRampRate(24);
    	RobotMap.driveTalonLeft1.set(drivePIDOutputRight+steerPIDOutput);
    	RobotMap.driveTalonLeft2.set(drivePIDOutputRight+steerPIDOutput);
        RobotMap.driveTalonRight1.set(drivePIDOutputRight-steerPIDOutput);
        RobotMap.driveTalonRight2.set(drivePIDOutputRight-steerPIDOutput);
    }
    public void driveStraightHuman(double steerInput, SimPID steerPID){
    	steerPIDOutput = -steerPID.calcPID(steerInput);
    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonLeft1.setVoltageRampRate(10000);
    	RobotMap.driveTalonRight1.setVoltageRampRate(10000);
    	RobotMap.driveTalonLeft1.set(-RobotMap.Dandyboy.getRawAxis(1)-steerPIDOutput);
        RobotMap.driveTalonRight1.set(-RobotMap.Dandyboy.getRawAxis(1)+steerPIDOutput);
    }
    
    public void driveLeft(double encLeft, SimPID encPIDLeft){
    	drivePIDOutputLeft = encPIDLeft.calcPID(encLeft);
    	System.out.println("drivePIDOutput " + drivePIDOutputLeft + drivePIDOutputRight + " steerPIDOutput " + steerPIDOutput);
    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonLeft1.setVoltageRampRate(24);
    	RobotMap.driveTalonLeft1.set(drivePIDOutputLeft);
    }
    
    public void driveRight(double encRight, SimPID encPIDRight){
    	drivePIDOutputLeft = encPIDRight.calcPID(encRight);
    	System.out.println("drivePIDOutput " + drivePIDOutputLeft + drivePIDOutputRight + " steerPIDOutput " + steerPIDOutput);
    	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonRight1.setVoltageRampRate(24);
    	RobotMap.driveTalonRight1.set(drivePIDOutputRight);
    }
    
    public void resetDriveEncoders(){
    	RobotMap.driveTalonLeft1.setEncPosition(0);
    	RobotMap.driveTalonRight1.setEncPosition(0);
    }
    
    public void gyroCameraTurn(SimPID rightPID, SimPID leftPID)
    {
    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonLeft1.setVoltageRampRate(24);
    	RobotMap.driveTalonRight1.setVoltageRampRate(24);
    	double rightValue = rightPID.calcPID(RobotMap.ahrs.getYaw());
    	double leftValue = leftPID.calcPID(RobotMap.ahrs.getYaw());
    	driveTank(leftValue, -rightValue);
    }
    
    public void gyroTurn(SimPID rightPID, SimPID leftPID)
    {
    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonLeft1.setVoltageRampRate(24);
    	RobotMap.driveTalonLeft2.setVoltageRampRate(24);
    	RobotMap.driveTalonRight1.setVoltageRampRate(24);
    	RobotMap.driveTalonRight2.setVoltageRampRate(24);
    	double leftValue = leftPID.calcPID(RobotMap.ahrs.getYaw());
    	double rightValue = -rightPID.calcPID(RobotMap.ahrs.getYaw());
    	RobotMap.driveTalonLeft1.set(leftValue);
    	RobotMap.driveTalonLeft2.set(leftValue);
        RobotMap.driveTalonRight1.set(rightValue);
        RobotMap.driveTalonRight2.set(rightValue);
    }
    
    public void gyroTurnReverse(SimPID rightPID, SimPID leftPID)
    {
    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.driveTalonLeft1.setVoltageRampRate(24);
    	RobotMap.driveTalonLeft2.setVoltageRampRate(24);
    	RobotMap.driveTalonRight1.setVoltageRampRate(24);
    	RobotMap.driveTalonRight2.setVoltageRampRate(24);
    	if(RobotMap.ahrs.getYaw()<0){
    		angleReverse = -(RobotMap.ahrs.getYaw() + 180);
    	}
    	else{
    		angleReverse = -(RobotMap.ahrs.getYaw() - 180);
    	}
    	
    	double leftValue = -leftPID.calcPID(angleReverse);
    	double rightValue = rightPID.calcPID(angleReverse);
    	RobotMap.driveTalonLeft1.set(leftValue);
    	RobotMap.driveTalonLeft2.set(leftValue);
        RobotMap.driveTalonRight1.set(rightValue);
        RobotMap.driveTalonRight2.set(rightValue);
    }
    public void gyroYawZero(){
    	RobotMap.ahrs.zeroYaw();
    }
    public void handleTalonState(){
    	switch(RobotMap.talonState){
    	case ARCADE:
//    		RobotMap.driveTalonRight1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
//    		RobotMap.driveTalonRight1.configEncoderCodesPerRev(51);//this is haram
//    		RobotMap.driveTalonRight1.setInverted(false);
//    		RobotMap.driveTalonRight1.reverseSensor(false);
//    		RobotMap.driveTalonRight1.reverseOutput(false);
//    		RobotMap.driveTalonRight2.setInverted(false);
//    		RobotMap.driveTalonRight2.reverseSensor(false);
//    		RobotMap.driveTalonRight2.reverseOutput(false);
//    		RobotMap.driveTalonLeft1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
//    		RobotMap.driveTalonLeft1.configEncoderCodesPerRev(51);
//    		RobotMap.driveTalonLeft1.setInverted(true);
//    		RobotMap.driveTalonLeft1.reverseSensor(true);
//    		RobotMap.driveTalonLeft1.reverseOutput(false);
//    		RobotMap.driveTalonLeft2.setInverted(true);
//    		RobotMap.driveTalonLeft2.reverseSensor(true);
//    		RobotMap.driveTalonLeft2.reverseOutput(false);
    		RobotMap.driveTalonLeft1.setVoltageRampRate(10000);
        	RobotMap.driveTalonRight1.setVoltageRampRate(10000);
    		// TODO: Set the magic voltage ramp rate
    		break;
    	case SIMPID:
//    		RobotMap.driveTalonRight1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
//    		RobotMap.driveTalonRight1.configEncoderCodesPerRev(51);//this is haram
//    		RobotMap.driveTalonRight1.setInverted(false);
//    		RobotMap.driveTalonRight1.reverseSensor(false);
//    		RobotMap.driveTalonRight1.reverseOutput(false);
//    		RobotMap.driveTalonLeft1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
//    		RobotMap.driveTalonLeft1.configEncoderCodesPerRev(51);
//    		RobotMap.driveTalonLeft1.setInverted(true);
//    		RobotMap.driveTalonLeft1.reverseSensor(true);
//    		RobotMap.driveTalonLeft1.reverseOutput(false);
    		RobotMap.driveTalonLeft1.setVoltageRampRate(24);
        	RobotMap.driveTalonRight1.setVoltageRampRate(24);
    		// TODO: Set the magic voltage ramp rate
    		break;
    	case MOTION_PROFILE:
    		RobotMap.driveTalonRight1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    		RobotMap.driveTalonRight1.configEncoderCodesPerRev(611);
    		RobotMap.driveTalonRight1.setInverted(false);
    		RobotMap.driveTalonRight1.reverseSensor(false);
    		RobotMap.driveTalonRight1.reverseOutput(false);
    		RobotMap.driveTalonLeft1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    		RobotMap.driveTalonLeft1.configEncoderCodesPerRev(611);
    		RobotMap.driveTalonLeft1.setInverted(true);
    		RobotMap.driveTalonLeft1.reverseSensor(false);
    		RobotMap.driveTalonLeft1.reverseOutput(false);
    		RobotMap.driveTalonLeft1.setVoltageRampRate(10000);
        	RobotMap.driveTalonRight1.setVoltageRampRate(10000);
    		// TODO: Set the magic voltage ramp rate
    		break;
    	default:
    		System.out.println("Unexpected Talon state: " + RobotMap.talonState);
    	}
    }

//    public void humanDriveLow(){
//    throttle = -RobotMap.Dandyboy.getRawAxis(1); 
//    turnValue = RobotMap.Dandyboy.getRawAxis(0);
//    deadZone = 0.15;
//    turningGain = 0;
//    if ((Math.abs(throttle)<deadZone) && (Math.abs(turnValue)<deadZone)){
//    	throttle = 0;
//    	turnValue = 0;
//    }
//	leftMtr = throttle + turnValue;
//	rightMtr = throttle - turnValue;
//
//	if (leftMtr > 1.0) {
//        leftMtr = leftMtr + (-((leftMtr - 1.0) * turningGain)*(rightMtr));
//    } else if (leftMtr < -1.0) {
//    	leftMtr = leftMtr + (-((leftMtr + 1.0) * turningGain)*(rightMtr));
//    }
//	
//	if (rightMtr > 1.0) {
//		rightMtr = rightMtr + (-((rightMtr - 1.0) * turningGain)*(leftMtr));
//    } else if (rightMtr < -1.0) {
//    	rightMtr = rightMtr + (-((rightMtr + 1.0) * turningGain)*(leftMtr));
//    }
//	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.PercentVbus);
//	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.PercentVbus);
//	RobotMap.driveTalonLeft1.setVoltageRampRate(10000);
//	RobotMap.driveTalonRight1.setVoltageRampRate(10000);
//    RobotMap.driveTalonLeft1.set(leftMtr);
//    RobotMap.driveTalonRight1.set(rightMtr);
//    }
// 
//    public void humanDriveHigh(){
//        throttle = -RobotMap.Dandyboy.getRawAxis(1); 
//        turnValue = RobotMap.Dandyboy.getRawAxis(0)/4;
//        deadZone = 0.15;
//        turningGain = 0;
//        if ((Math.abs(throttle)<deadZone) && (Math.abs(turnValue)<deadZone)){
//        	throttle = 0;
//        	turnValue = 0;
//        }
//        // left = y+x
//        // right = y-x
//    	leftMtr = throttle + turnValue;
//    	rightMtr = throttle - turnValue;
//
//    	if (leftMtr > 1.0) {
//            leftMtr = leftMtr + (-((leftMtr - 1.0) * turningGain)*(rightMtr));
//        } else if (leftMtr < -1.0) {
//        	leftMtr = leftMtr + (-((leftMtr + 1.0) * turningGain)*(rightMtr));
//        }
//    	
//    	if (rightMtr > 1.0) {
//    		rightMtr = rightMtr + (-((rightMtr - 1.0) * turningGain)*(leftMtr));
//        } else if (rightMtr < -1.0) {
//        	rightMtr = rightMtr + (-((rightMtr + 1.0) * turningGain)*(leftMtr));
//        }
//    	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.PercentVbus);
//    	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.PercentVbus);
//    	RobotMap.driveTalonLeft1.setVoltageRampRate(10000);
//    	RobotMap.driveTalonRight1.setVoltageRampRate(10000);
//        RobotMap.driveTalonLeft1.set(leftMtr);
//        RobotMap.driveTalonRight1.set(rightMtr);
//        }
//    
//    public void humanDriveII(){
//		double deadZone = 0.15;
//		double X = -RobotMap.Dandyboy.getRawAxis(0);
//        double Y = RobotMap.Dandyboy.getRawAxis(1);
//        if ((Math.abs(RobotMap.Dandyboy.getRawAxis(0))<deadZone) && (Math.abs(RobotMap.Dandyboy.getRawAxis(1))<deadZone)){
//        	X = 0;
//        	Y = 0;
//        }
//        /*if (Math.abs(-driveStick.getRawAxis(1))<deadZone){
//        	Y = 0;
//        }*/
//        double leftOutput;
//        double rightOutput;
//        if (X>0){
//        	X = Math.pow(X, 2); //Square X input
//        }
//        else{
//        	X = Math.pow(X, 2)*-1;
//        }
//        if (Y > 0) {
//            if (X > 0.0) {
//                leftOutput = Math.pow(Y, 1) - Math.pow(X, 1);
//                rightOutput = Math.max(Math.pow(Y, 1), Math.pow(X, 1));
//            } else {
//                leftOutput = Math.max(Math.pow(Y, 1), -(Math.pow(X, 1)));
//                rightOutput = Math.pow(Y, 1) + (Math.pow(X, 1));
//            }
//        } else{
//            if (X > 0.0) {
//                leftOutput = -Math.max(-(Math.pow(Y, 1)), Math.pow(X, 1));
//                rightOutput = (Math.pow(Y, 1)) + Math.pow(X, 1);
//            } else {
//            	//this is also vvv imborktant
//                leftOutput = (Math.pow(Y, 1)) - (Math.pow(X, 1));
//                rightOutput = -Math.max(-(Math.pow(Y, 1)), -(Math.pow(X, 1)));
//            }
//            
//        	RobotMap.driveTalonLeft1.changeControlMode(TalonControlMode.PercentVbus);
//        	RobotMap.driveTalonRight1.changeControlMode(TalonControlMode.PercentVbus);
//        	RobotMap.driveTalonLeft1.setVoltageRampRate(10000);
//        	RobotMap.driveTalonRight1.setVoltageRampRate(10000);
////        	RobotMap.driveTalonLeft1.set(-leftOutput);
////        	RobotMap.driveTalonRight1.set(rightOutput);
//        }
//    }
    public void initDefaultCommand() {
    }
}

