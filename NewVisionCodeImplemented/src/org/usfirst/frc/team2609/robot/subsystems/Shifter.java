package org.usfirst.frc.team2609.robot.subsystems;
import org.usfirst.frc.team2609.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shifter extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    DoubleSolenoid doubleSolenoid1 = RobotMap.shifter;
    
    public void toggle() {
    	if (doubleSolenoid1.get() == DoubleSolenoid.Value.kReverse){
    			doubleSolenoid1.set(DoubleSolenoid.Value.kForward);
    			System.out.println("high gear "+ doubleSolenoid1.get());
    	}
    	else if (doubleSolenoid1.get() == DoubleSolenoid.Value.kForward){
			doubleSolenoid1.set(DoubleSolenoid.Value.kReverse);
			System.out.println("low gear "+ doubleSolenoid1.get());
    	}
    	else {
    		doubleSolenoid1.set(DoubleSolenoid.Value.kReverse);
    		System.out.println("low gear "+ doubleSolenoid1.get());
    	}    	
    }
    public void high() {
    	doubleSolenoid1.set(DoubleSolenoid.Value.kReverse);
	}
    public void low() {
    	doubleSolenoid1.set(DoubleSolenoid.Value.kForward);
	}
    public void neutral() {
    	doubleSolenoid1.set(DoubleSolenoid.Value.kOff);
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