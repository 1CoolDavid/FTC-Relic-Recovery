package org.firstinspires.ftc.robotcontroller.internal;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class EmmaOpMode10414 extends OpMode {
   //Defines Utilities
   DcMotor leftMotor;
   DcMotor rightMotor;
   DcMotor rackMotor;
   DcMotor fslideMotor;
   DcMotor bslideMotor;
   Servo bleftClamp;
   Servo brightClamp;
   Servo leftClamp;
   Servo rightClamp;
   Servo grabber;
   DcMotor lifter;  
   private double w;
   @Override
   public void init() {
      leftMotor = hardwareMap.dcMotor.get("leftMotor");//Left wheel
      rightMotor = hardwareMap.dcMotor.get("rightMotor");//Right wheel
       bleftClamp = hardwareMap.servo.get("bleftClamp"); //bottom left clamp
       brightClamp = hardwareMap.servo.get("brightClamp"); //bottom right clamp
      rackMotor = hardwareMap.dcMotor.get("rackMotor"); //Rack motor
      fslideMotor = hardwareMap.dcMotor.get("fslideMotor"); //motor that makes arm extend
       bslideMotor = hardwareMap.dcMotor.get("bslideMotor");
      leftClamp = hardwareMap.servo.get("leftClamp");//clamp arm (left)
      rightClamp = hardwareMap.servo.get("rightClamp"); //clamp arm
     grabber = hardwareMap.servo.get("grabber"); //the left pincher
     lifter = hardwareMap.dcMotor.get("lifter"); //the right pincher
       leftClamp.setPosition(.23); //starting position
       rightClamp.setPosition(.6); //starting position
       bleftClamp.setPosition(.07); //starting position
       brightClamp.setPosition(.6); //starting position
     grabber.setPosition(.5); //starting position
     lifter.setPower(0); //starting position
       //double a = leftMotor.getCurrentPosition();
       //double b = rightMotor.getCurrentPosition();
      }

   public void init_loop() {
      //necessary for motors to allow autonomous and avoid crash
      leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);//runs measuring distance
      rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); //runs measuring distance
      rackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); //runs without measuring distance
      fslideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); //runs without measuring distance
      bslideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
   }

   @Override
   public void loop() {
      //
      leftMotor.setPower(gamepad1.left_stick_y * -1); //left motor moves with left stick
      rightMotor.setPower(gamepad1.right_stick_y * 1); //right motor moves with right stick
       telemetry.addData(" LeftMotor ", leftMotor.getCurrentPosition());
       telemetry.addData(" RightMotor ", rightMotor.getCurrentPosition());
       telemetry.addData("RackMotor", rackMotor.getCurrentPosition());
      //clamp servos
      if (gamepad1.a) {
          //clamps are synced -- NOLI TENERE NISI VIS NECARE ME
         //open
         leftClamp.setPosition(.01); //numbers represent degree of movement (0 being 0 degrees and 1 being 180 degrees)
         rightClamp.setPosition(.8);
         bleftClamp.setPosition(.27);
         brightClamp.setPosition(.39);
      } else if (gamepad1.b) {
         //close
         leftClamp.setPosition(.23);
         rightClamp.setPosition(.6);
         bleftClamp.setPosition(.07);
         brightClamp.setPosition(.6);
      } else {

      }

   if (gamepad1.dpad_up) {
         lifter.setPower(.4); //when dpad up is pressed move the servo 90 degrees
      }
      else if (gamepad1.dpad_down){
         lifter.setPower(-.4); //when dpad down is pressed move the servo back (reset)
      }
      else{
          lifter.setPower(0);
   }
      if (gamepad1.x){
          grabber.setPosition(0.5); //when dpad right is pressed move the servo 90 degrees
      }
      else if (gamepad1.y){
          grabber.setPosition(0); //when dpad left is pressed move the servo back (reset)
      }
      //clamp motor
      if (gamepad1.right_trigger > .5) {
         rackMotor.setPower(1); //go forward
      } else if (gamepad1.left_trigger > .5) {
         rackMotor.setPower(-1); //go backward
      } else { //stop moving if no triggers are pressed
         rackMotor.setPower(0);
      }
      //cascade slide motor

      if (gamepad1.right_bumper) {//extend
         fslideMotor.setPower(-.5); //move motor forward if right bumper is pressed
         bslideMotor.setPower(.4);
      } else if (gamepad1.left_bumper) {//retract
         fslideMotor.setPower(.3); //move motor back if left bumper is pressed
         bslideMotor.setPower(-.2);
      } else {
         fslideMotor.setPower(0); //if none of the previous buttons are pressed pause in track.
         bslideMotor.setPower(0);
      }
    //end of controls
   }
}