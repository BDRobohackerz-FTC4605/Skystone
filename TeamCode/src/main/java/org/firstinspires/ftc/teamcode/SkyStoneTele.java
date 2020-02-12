package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_WITHOUT_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static com.qualcomm.robotcore.util.Range.scale;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "SkyStoneTele" , group = "TOComp")

public class SkyStoneTele extends OpMode {

    ImportantStuff robot = new ImportantStuff();

    static final double INCREMENT   = .3;     // amount to ramp motor each CYCLE_MS cycle
    static final int    CYCLE_MS    =   50;     // period of each cycle
    static final double MAX_FWD     =  1.0;     // Maximum FWD power applied to motor
    static final double MAX_REV     = 0;     // Maximum REV power applied to motor

    double  power   = 0;
    boolean rampUp  = true;

//    DcMotor liftUp;
//    DcMotor liftOut;

    @Override
    public void init() {

        robot.init(hardwareMap, telemetry);

//        liftUp = hardwareMap.dcMotor.get("liftUp");
//        liftOut = hardwareMap.dcMotor.get("liftOut");

//        liftUp.setMode(RUN_WITHOUT_ENCODER);
//        liftOut.setMode(RUN_WITHOUT_ENCODER);

//        liftUp.setDirection(FORWARD);
//        liftOut.setDirection(FORWARD);
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



        robot.robotStuff.FLeft.setPower(scale(speed + direction - strafe, -Magnitude, Magnitude, -.5, .5));
        robot.robotStuff.FRight.setPower(scale(speed - direction + strafe, -Magnitude, Magnitude, -.5, .5));
        robot.robotStuff.BLeft.setPower(scale(speed + direction + strafe, -Magnitude, Magnitude, -.5, .5));
        robot.robotStuff.BRight.setPower(scale(speed - direction - strafe, -Magnitude, Magnitude, -.5, .5));

        float forwardIntake = gamepad1.right_trigger;
        float reverseIntake = -gamepad1.left_trigger;

        if (forwardIntake > .5){
            forwardIntake = (float) .5;
        }

        if (reverseIntake < -.5){
            reverseIntake = (float) -.5;
        }

        if (gamepad1.right_trigger > .1){
            robot.robotStuff.intakeMotor.setPower(forwardIntake);
        }
        else if (gamepad1.left_trigger > .1){
            robot.robotStuff.intakeMotor.setPower(reverseIntake);
        }
        else {
            robot.robotStuff.intakeMotor.setPower(0);
        }
        telemetry.addData("IntakeSpeed", robot.robotStuff.intakeMotor.getPower());

        if (gamepad1.left_bumper && robot.robotStuff.leftHook.getPosition()==robot.robotStuff.SERVO_LATCH_UP){
            robot.robotStuff.leftHook.setPosition(robot.robotStuff.SERVO_LATCH_DOWN);
            robot.robotStuff.rightHook.setPosition(robot.robotStuff.SERVO_LATCH_DOWN);
        }
        else if (gamepad1.left_bumper && robot.robotStuff.leftHook.getPosition()==robot.robotStuff.SERVO_LATCH_DOWN){
            robot.robotStuff.leftHook.setPosition(robot.robotStuff.SERVO_LATCH_UP);
            robot.robotStuff.rightHook.setPosition(robot.robotStuff.SERVO_LATCH_UP);
        }

//        if (gamepad1.a) {
//            liftOut.setPower(1);
//        }
//        else if (gamepad1.b){
//            liftOut.setPower(-1);
//        }
//        else {
//            liftOut.setPower(0);
//        }
//
//        if (gamepad1.y){
//            liftUp.setPower(1);
//        }
//        else if (gamepad1.x){
//            liftUp.setPower(-1);
//        }
//        else {
//            liftUp.setPower(0);
//        }
    }
}
