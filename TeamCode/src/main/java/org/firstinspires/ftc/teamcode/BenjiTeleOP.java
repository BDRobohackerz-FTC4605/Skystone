package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


/* Team: FTC NRG6762
 * School: Northwest Regional 7
 * Author: Aiden Maraia
 */
@TeleOp(name="Benji: TeleOP", group="Test")
//@Disabled
public class BenjiTeleOP extends OpMode{

//Declare OpMode members. Use the class created to define the robot's hardware
    HardwareRobot robot = new HardwareRobot();

//Telemetry boolean (used to turn on any valid telemetry at the right time)
    boolean joyPosTele = false;
    boolean joyPolarCoordTele = false;
    boolean wheelScalersTele = false;

//Code to run ONCE when the driver hits INIT
    @Override
    public void init() {

//Initialize the hardware variables. The init() method of the hardware class does all the work here
        robot.init(hardwareMap);

// Send telemetry message to signify robot waiting
        telemetry.addData("Say", "Hello Driver");
    }

//Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
    @Override
    public void init_loop() {
    }

//Code to run ONCE when the driver hits PLAY
    @Override
    public void start() {
    }

//Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {

//Create and set values to used variables
        double speed = 0, angle = 0, dchange = 0;
        double[] retValues;

//Instance of cartesianToPolar method used for changing the cartesian values into polar values.
        retValues = cartesianToPolar(gamepad1.right_stick_y, gamepad1.right_stick_x, gamepad1.left_stick_x);

//Set retValues array to shown values. retValues is used to return the variables used in multiple methods.
        speed = retValues[0];
        angle = retValues[1];
        dchange = retValues[2];

//Instance of polarToWheelSpeed method used for powering wheels
        polarToWheelSpeed(speed, angle, dchange);

//Update the Telemetry
        telemetry.update();
    }

//Code to run ONCE after the driver hits STOP
    @Override
    public void stop() {
    }

    public double[] cartesianToPolar(double y1, double x1, double x2) {

//Reset retValues to 0 for later use
        double[] retValues = new double []{0.0,0.0,0.0};
        double speed = 0.0, angle=0.0, dchange=0.0;

//Change joypad values into useful polar values
        speed = Math.sqrt((y1 * y1) + (x1 * x1));
        angle = Math.atan2(x1, -y1);
        dchange = -x2 / 3.33;

//Joypad input Telemetry
        if (joyPosTele) {
            telemetry.addData("X1: ",x1);
            telemetry.addData("Y1: ",y1);
            telemetry.addData("X2: ",x2);
        }

//Polar values Telemetry
        if (joyPolarCoordTele) {
            telemetry.addData("Speed: ", speed);
            telemetry.addData("Angle: ", angle);
            telemetry.addData("Rotational Change", dchange);
        }

//Add polar values to retValues array
        retValues[0] = speed;
        retValues[1] = angle;
        retValues[2] = dchange;
        return retValues;
    }

    public void polarToWheelSpeed(double speed, double angle, double dchange){

//Create Variables used only in this method
        double pos1, pos2, pos3, pos4, maxValue;

//Define unscaled voltage multipliers
        pos1 = speed*Math.sin(angle+(Math.PI/4))+dchange;
        pos2 = speed*Math.cos(angle+(Math.PI/4))-dchange;
        pos3 = speed*Math.cos(angle+(Math.PI/4))+dchange;
        pos4 = speed*Math.sin(angle+(Math.PI/4))-dchange;

//VOLTAGE MULTIPLIER SCALER

//Set maxValue to pos1 absolute
        maxValue = Math.abs(pos1);

//If pos2 absolute is greater than maxValue, then make maxValue equal to pos2 absolute
        if(Math.abs(pos2) > maxValue){maxValue = Math.abs(pos2);}

//If pos3 absolute is greater than maxValue, then make maxValue equal to pos3 absolute
        if(Math.abs(pos3) > maxValue){maxValue = Math.abs(pos3);}

//If pos4 absolute is greater than maxValue, then make maxValue equal to pos4 absolute
        if(Math.abs(pos4) > maxValue){maxValue = Math.abs(pos4);}

//Check if need to scale -- if not set to 1 to nullify scale
        if (maxValue <= 1){ maxValue = 1;}

//Power motors with scaled voltage multipliers
        robot.DrivePos1.setPower(pos1/maxValue);
        robot.DrivePos2.setPower(pos2/maxValue);
        robot.DrivePos3.setPower(pos3/maxValue);
        robot.DrivePos4.setPower(pos4/maxValue);

//Scaled Voltage Multiplier Telemetry
        if (wheelScalersTele) {
            telemetry.addData("Wheel 1 W/ Scale: ",pos1/maxValue);
            telemetry.addData("Wheel 2 W/ Scale: ",pos2/maxValue);
            telemetry.addData("Wheel 3 W/ Scale: ",pos3/maxValue);
            telemetry.addData("Wheel 4 W/ Scale: ",pos4/maxValue);
        }
    }
}
