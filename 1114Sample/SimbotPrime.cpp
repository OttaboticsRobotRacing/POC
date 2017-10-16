#include "DoubleSolenoid.h"
#include "LogitechGamepad.h"
#include "SimLib.h"
#include "WPILib.h"

class SimbotPrime : public IterativeRobot
{
private:
	// Driver joystick
	Joystick *driveJoystick;

	// Operator gamepad
	LogitechGamepad *operatorPad;

	// Drive motors
	Victor *driveLeftFront;
	Victor *driveLeftBack;
	Victor *driveRightFront;
	Victor *driveRightBack;

	// Arm motors
	Victor *fourBar;
	Victor *elevator;

	// Window motor on arm gripper
	Relay *gripper;

	Compressor *compressor;

	// Andy-Mark two speed transmission
	DoubleSolenoid *shifter;
	Solenoid *shifterLow;
	Solenoid *shifterHigh;

	// Robot sensors
	Gyro* gyro;  
	AnalogChannel* elevatorPot; // Elevator
	Encoder* leftEncoder; // Left side drive
	Encoder* rightEncoder; // Right side drive

public:
	SimbotPrime(void)
	{
		driveJoystick = new Joystick(1); // USB port 1
		operatorPad = new LogitechGamepad(2);  // USB port 2

		driveLeftFront = new Victor(1); // PWM 1
		driveLeftBack = new Victor(2); // PWM 2
		driveRightFront = new Victor(3); // PWM 3
		driveRightBack = new Victor(4); // PWM 4

		elevator = new Victor(5); // PWM 5
		fourBar = new Victor(6); // PWM 6

		// Initialize all motors to neutral.
		// NOTE: if not initialized, they will blink even when enabled.
		driveLeftFront->Set(0);
		driveLeftBack->Set(0);
		driveRightFront->Set(0);
		driveRightBack->Set(0);
		elevator->Set(0);
		fourBar->Set(0);

		gripper = new Relay(1); // Relay 1

		// Pressure sensor in digital I/O 1, compressor on relay 2
		compressor = new Compressor(1, 2);
		compressor->Start();

		// Double solenoid in ports 1 and 2
		shifter = new DoubleSolenoid(1, 2);

		// Start in low gear.
		shifter->Set(DoubleSolenoid::kOnA);

		gyro = new Gyro(1); // Analog port 1
		gyro->SetSensitivity(0.0122); // ADXRS150

		elevatorPot = new AnalogChannel(2); // Analog port 2

		// A-channel on digital 11, B on digital 12
		leftEncoder = new Encoder(11, 12);
		// A-channel on digital 13, B on digital 14
		rightEncoder = new Encoder(13, 14);

		leftEncoder->Start();
		rightEncoder->Start();

		// Require the watchdog to be fed every 100 ms.
		GetWatchdog().SetExpiration(100);
	}

	/**
	 * Initializes all motors to neutral to begin teleop.
	 * NOTE: This is important to prevent motors from retaining values from
	 * autonomous.
	 */
	void TeleopInit(void)
	{
		driveLeftFront->Set(0);
		driveLeftBack->Set(0);
		driveRightFront->Set(0);
		driveRightBack->Set(0);

		elevator->Set(0);
		fourBar->Set(0);
	}

	/**
	 * Runs while the robot is disabled.
	 */
	void DisabledPeriodic(void)
	{
		GetWatchdog().Feed();
	}

	/**
	 * The main loop during teleop.
	 * Maps joystick and gamepad input to motor output. 
	 */
	void TeleopPeriodic(void)
	{
		GetWatchdog().Feed();

		// Retrieve and square the joystick values to desensitize them.
		float x = SimLib::SignSquare(driveJoystick->GetX());
		float y = SimLib::SignSquare(driveJoystick->GetY());

		// Calculate motor outputs for arcade drive.
		float left = SimLib::LimitOutput(y - x);
		float right = -1.0 * SimLib::LimitOutput(y + x);  // Right side is inverted

		driveLeftFront->Set(left);
		driveLeftBack->Set(left);
		driveRightFront->Set(right);
		driveRightBack->Set(right);

		// Set arm motors to squared gamepad axis inputs.
		fourBar->Set(SimLib::SignSquare(operatorPad->GetRightY()));
		elevator->Set(SimLib::SignSquare(operatorPad->GetLeftY()));

		// Open gripper.
		if(operatorPad->GetNumberedButton(6)) {
			gripper->Set(Relay::kForward);
		}
		// Close gripper.
		else if(operatorPad->GetNumberedButton(8)) {
			gripper->Set(Relay::kReverse);
		}
		// Don't move gripper.
		else 
		{
			gripper->Set(Relay::kOff);
		}

		// Set the shifter speed according to the buttons on the joystick base.
		if (driveJoystick->GetRawButton(7))
		{
			shifter->Set(DoubleSolenoid::kOnA);
		}
		else if (driveJoystick->GetRawButton(6))
		{
			shifter->Set(DoubleSolenoid::kOnB);
		}
	}
};

START_ROBOT_CLASS(SimbotPrime);
