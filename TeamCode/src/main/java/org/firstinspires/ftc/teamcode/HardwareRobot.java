package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note: All names are lower case and some have single spaces between words.
 *
 * Motor channel: Drive Motor 1: "drive1"
 * Motor channel: Drive Motor 2: "drive2"
 * Motor channel: Drive Motor 3: "drive3"
 * Motor channel: Drive Motor 4: "drive4"
 */
public class HardwareRobot {

    /* Public OpMode members. */
    public DcMotor DrivePos1 = null;
    public DcMotor DrivePos2 = null;
    public DcMotor DrivePos3 = null;
    public DcMotor DrivePos4 = null;

    /* Local OpMode members. */
    HardwareMap hwMapRobot = null;

    /* Constructor */
    public HardwareRobot(){
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {

// Save reference to Hardware map
        hwMapRobot = ahwMap;

// Define and Initialize Motors
        DrivePos1 = hwMapRobot.dcMotor.get("drive1");
        DrivePos2 = hwMapRobot.dcMotor.get("drive2");
        DrivePos3 = hwMapRobot.dcMotor.get("drive3");
        DrivePos4 = hwMapRobot.dcMotor.get("drive4");

//Define Motor Directions
        DrivePos1.setDirection(DcMotor.Direction.REVERSE);
        DrivePos2.setDirection(DcMotor.Direction.FORWARD);
        DrivePos3.setDirection(DcMotor.Direction.REVERSE);
        DrivePos4.setDirection(DcMotor.Direction.FORWARD);

// Set all motors to zero power
        DrivePos1.setPower(0);
        DrivePos2.setPower(0);
        DrivePos3.setPower(0);
        DrivePos4.setPower(0);

// Set all motors to run with encoders.
// May want to use RUN_WITHOUT_ENCODERS if encoders aren't installed.
        DrivePos1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        DrivePos2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        DrivePos3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        DrivePos4.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
}
