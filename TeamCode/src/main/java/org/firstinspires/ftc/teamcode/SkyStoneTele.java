package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_WITHOUT_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static com.qualcomm.robotcore.util.Range.scale;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "SkyStoneTele" , group = "TOComp")

public class SkyStoneTele extends OpMode {

    static final double INCREMENT   = .3;     // amount to ramp motor each CYCLE_MS cycle
    static final int    CYCLE_MS    =   50;     // period of each cycle
    static final double MAX_FWD     =  1.0;     // Maximum FWD power applied to motor
    static final double MAX_REV     = 0;     // Maximum REV power applied to motor

    double  power   = 0;
    boolean rampUp  = true;

    DcMotor FLeft;
    DcMotor FRight;
    DcMotor BLeft;
    DcMotor BRight;

    DcMotor intakeMotor;

    @Override
    public void init() {

        FLeft = hardwareMap.dcMotor.get("FLeft");
        FRight = hardwareMap.dcMotor.get("FRight");
        BLeft = hardwareMap.dcMotor.get("BLeft");
        BRight = hardwareMap.dcMotor.get("BRight");

        intakeMotor = hardwareMap.dcMotor.get("intakeMotor");

        FLeft.setMode(RUN_WITHOUT_ENCODER);
        FRight.setMode(RUN_WITHOUT_ENCODER);
        BLeft.setMode(RUN_WITHOUT_ENCODER);
        BRight.setMode(RUN_WITHOUT_ENCODER);

        intakeMotor.setMode(RUN_WITHOUT_ENCODER);

        BLeft.setDirection(FORWARD);
        FLeft.setDirection(FORWARD);
        FRight.setDirection(REVERSE);
        BRight.setDirection(REVERSE);

        intakeMotor.setDirection(FORWARD);
    }

    @Override
    public void loop() {
        float speed = -gamepad1.right_stick_y;
        float direction = gamepad1.right_stick_x;
        float strafe = gamepad1.left_stick_x;

        float Magnitude = Math.abs(speed) + Math.abs(direction) + Math.abs(strafe);
        if (Magnitude < 1) {
            Magnitude = 1;
        }
        power -= INCREMENT;
//        if (gamepad1.a) {
////            if (power <= MAX_REV) {
////                power = MAX_REV;
////            }
//            FLeft.setPower(scale(speed + direction - strafe, -Magnitude, Magnitude, -.5, .5));
//            FRight.setPower(scale(speed - direction + strafe, -Magnitude, Magnitude, -.5, .5));
//            BLeft.setPower(scale(speed + direction + strafe, -Magnitude, Magnitude, -.5, .5));
//            BRight.setPower(scale(speed - direction - strafe, -Magnitude, Magnitude, -.5, .5));
//        }



        FLeft.setPower(scale(speed + direction - strafe, -Magnitude, Magnitude, -.5, .5));
        FRight.setPower(scale(speed - direction + strafe, -Magnitude, Magnitude, -.5, .5));
        BLeft.setPower(scale(speed + direction + strafe, -Magnitude, Magnitude, -.5, .5));
        BRight.setPower(scale(speed - direction - strafe, -Magnitude, Magnitude, -.5, .5));

        float forwardIntake = gamepad1.right_trigger;
        float reverseIntake = -gamepad1.left_trigger;

        if (forwardIntake > .6){
            forwardIntake = (float) .6;
        }

        if (reverseIntake > .6){
            reverseIntake = (float) .6;
        }

        if (gamepad1.right_trigger > .1){
            intakeMotor.setPower(forwardIntake);
        }
        else if (gamepad1.left_trigger > .1){
            intakeMotor.setPower(reverseIntake);
        }
        else {
            intakeMotor.setPower(0);
        }
        telemetry.addData("IntakeSpeed", intakeMotor.getPower());

    }
}
