package org.usfirst.frc.team2609.robot.subsystems;
import org.usfirst.frc.team2609.enums.BallDoorState;
import org.usfirst.frc.team2609.robot.Robot;
import org.usfirst.frc.team2609.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class AutoClaw extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    DoubleSolenoid doubleSolenoid1 = RobotMap.autoClaw;
    
    public void toggle() {
    	BallDoorState currentState = this.getState();
    	switch(currentState){
    	case OPEN:
    		Robot.r03.StayInsideR03(BallDoorState.CLOSED);
//    		doubleSolenoid1.set(this.setDoor(BallDoorState.CLOSED));
    		return;
    	case CLOSED:
    		Robot.r03.StayInsideR03(BallDoorState.OPEN);
//    		doubleSolenoid1.set(this.setDoor(BallDoorState.OPEN));
    		return;
    	case NEUTRAL:
    		Robot.r03.StayInsideR03(BallDoorState.CLOSED);
    		break;
    	default:
    		System.out.println("Unknown BallDoor state!");
    		break;
    	}
//    	if (doubleSolenoid1.get() == DoubleSolenoid.Value.kReverse){
//    		doubleSolenoid1.set(DoubleSolenoid.Value.kForward);
//    	}
//    	else if (doubleSolenoid1.get() == DoubleSolenoid.Value.kForward){
//			doubleSolenoid1.set(DoubleSolenoid.Value.kReverse);
//    	}
//    	else {
//    		doubleSolenoid1.set(DoubleSolenoid.Value.kReverse);
//    	}    	
//    	I will keep this in until the state machine is tested
    }
    public void open() {
    	Robot.r03.StayInsideR03(BallDoorState.OPEN);
	}
    public void close() {
    	Robot.r03.StayInsideR03(BallDoorState.CLOSED);
	}
    public void neutral() {
    	Robot.r03.StayInsideR03(BallDoorState.NEUTRAL);
	}
    public BallDoorState getState(){
    	switch(doubleSolenoid1.get()){
    	case kForward:
    		return BallDoorState.OPEN;
    	case kReverse:
    		return BallDoorState.CLOSED;
    	case kOff:
    		return BallDoorState.NEUTRAL;
    	default:
    		return BallDoorState.OPEN;
    	}
    }
    public DoubleSolenoid.Value setDoor(BallDoorState desiredState){
    	switch(desiredState){
    	case OPEN:
    		return DoubleSolenoid.Value.kReverse;
    	case CLOSED:
    		return DoubleSolenoid.Value.kForward;
    	case NEUTRAL:
    		return DoubleSolenoid.Value.kOff;
    	default:
    		return DoubleSolenoid.Value.kOff;
    	}
    		
    }
    public void forceSetDoor(DoubleSolenoid.Value intendedValue){
    	doubleSolenoid1.set(intendedValue);
    }
}

/*
public void toggle() {
	if (Solenoid1.get() == false){
			Solenoid1.set(true);
			System.out.println("high gear "+ Solenoid1.get());
	}
	else if (Solenoid1.get() == true){
		Solenoid1.set(false);
		System.out.println("low gear "+ Solenoid1.get());
	}
	else {
		Solenoid1.set(Solenoid1.Value.kReverse);
		System.out.println("low gear "+ Solenoid1.get());
	}    	
}
public void high() {
	Solenoid1.set(true);
}
public void low() {
	Solenoid1.set(false);
}
public void neutral() {
	Solenoid1.set(false);
}*/