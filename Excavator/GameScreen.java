package com.esark.excavator;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;

import com.esark.framework.Game;
import com.esark.framework.Graphics;
import com.esark.framework.Input;
import com.esark.framework.Pixmap;
import com.esark.framework.Screen;
import static com.esark.framework.AndroidGame.landscape;

import java.util.List;

public class GameScreen extends Screen implements Input{
    Context context = null;
    int xTouch1 = 0;
    int yTouch1 = 0;
    int xTouchLeft = 150;
    int yTouchLeft = 275;
    int xTouchRight = 560;
    int yTouchRight = 275;
    int xR = 0;
    int yR = 0;
    int xL = 0;
    int yL = 0;
    int yLTrack = 0;
    int yRTrack = 0;
    int scaledXR = 0;
    int scaledYR = 0;
    int scaledXL = 0;
    int scaledYL = 0;
    double angleR = 0;
    double angleL = 0;
    public static int c = 0;
    public static int b = 0;
    public static int o = 0;
    public static int s = 0;
    public static int l = 0;
    public static int r = 0;
    int[] tempCArr = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] tempBArr = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int tempC = 0;
    int tempB = 0;
    int i = 0;
    int h = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    int tempO = 0;
    int tempS = 0;
    int[] tempOArr = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] tempSArr = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int leftCount = 0;
    int rightCount = 0;
    int xTouch2 = 0;
    int yTouch2 = 0;
    int xPrevLeft = 2000;        //Adjust these/////////////////////////
    int yPrevLeft = 4200;
    int xPrevRight = 8000;
    int yPrevRight = 4200;
    int yTrackLeft = 1000;
    int yTrackPrevLeft = 1000;
    int yTrackRight = 1000;
    int yTrackPrevRight = 1000;
    int numAvg = 0;
    public static int stopSendingLeft = 0;
    public static int stopSendingRight = 0;
    public static int stopSendingBoom = 0;
    public static int stopSendingCurl = 0;
    public static int stopSendingOrbit = 0;
    public static int stopSendingStick = 0;
    int count = 0;
    public Pixmap pixmap = null;

    private static final int INVALID_POINTER_ID = -1;
    // The ‘active pointer’ is the one currently moving our object.
    private int mActivePointerId = INVALID_POINTER_ID;


    //Constructor
    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime, Context context) {
        //framework.input
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        updateRunning(touchEvents, deltaTime, context);
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime, Context context) {
        //updateRunning() contains controller code of our MVC scheme
        Graphics g = game.getGraphics();
        pixmap = Assets.excavatorTabletLandscapeBackground;
        //If count == 0 it's the first time and we draw the background and the four joysticks
        if (count == 0) {
            //If landscape == 1 the phone is in landscape orientation
            if (landscape == 1) {
                g.drawLandscapePixmap(pixmap, 0, 0);
            } else {
                g.drawPortraitPixmap(Assets.excavatorPortraitBackground, 0, 0);
            }
            g.drawCircle(2000, 4200, 400);         //Left joystick
            g.drawCircle(8000, 4200, 400);         //Right Joystick
            g.drawCircle(1400, 1000, 400);         //Left Track
            g.drawCircle(8300, 1000, 400);         //Right Track
            g.drawBlackLine(2000, 4200, 2500, 4700, 0);     //Left Joystick Lever
            g.drawBlackLine(8000, 4200, 7500, 4700, 0);     //Right Joystick Lever
            g.drawBlackLine(1000, 900, 2500, 1400, 0);     //Left Track Lever
            g.drawBlackLine(8000, 1150, 7450, 1400, 0);     //Right Track Lever
        }
        int len = touchEvents.size();
        //Check to see if paused
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if(event.x < 350) {
                    stopSendingLeft = 1;
                }
                else if(event.x >= 350){
                    stopSendingRight = 1;
                }
                g.drawCircle(xPrevLeft, yPrevLeft, 400);            //Left joystick
                g.drawBlackLine(2500, 4700, xPrevLeft, yPrevLeft, 0);      //Left joystick lever
                g.drawCircle(xPrevRight, yPrevRight, 400);           //Right joystick
                g.drawBlackLine(8000, 4700, xPrevRight, yPrevRight, 0);        //Right joystick lever
                g.drawCircle(1400, yTrackPrevLeft, 400);              //Left track
                g.drawBlackLine(2500, 1400, xPrevRight, yPrevRight, 0);        //Left track lever
                g.drawCircle(8000, yTrackPrevRight, 400);             //Right track
                g.drawBlackLine(7450, 1400, xPrevRight, yPrevRight, 0);        //Right track lever
            }


            if (event.type == TouchEvent.TOUCH_DRAGGED || event.type == TouchEvent.TOUCH_DOWN) {
                count = 1;
                if(event.x < 5000 && event.y > 3000) {
                    stopSendingLeft = 0;
                }
                else if(event.x >= 5000 && event.y > 3000){
                    stopSendingRight = 0;
                }
                if (landscape == 1) {
                    g.drawLandscapePixmap(pixmap, 0, 0);
                } else {
                    g.drawPortraitPixmap(Assets.excavatorPortraitBackground, 0, 0);
                }
                // Save the ID of this pointer
                if (event.x > 3500 && event.x < 4500 && event.y < 1500) {
                    //Back Button Code Here
                    pixmap.dispose();
                    Intent intent2 = new Intent(context.getApplicationContext(), Excavator.class);
                    context.startActivity(intent2);
                    return;
                }
                mActivePointerId = event.pointer;

                //The pointer points to which finger or thumb. The first finger to touch is 0
                if (mActivePointerId == 0) {
                    Log.d("ADebugTag", "mActivePointerId: " + mActivePointerId);
                    xTouch1 = event.x;          //Get the x and y coordinates of the first touch
                    yTouch1 = event.y;
                    //In the region of the stick and spin circle
                    if (xTouch1 < 200 & yTouch1 > 100) {
                        xTouchLeft = xTouch1;
                        yTouchLeft = yTouch1;
                        xPrevLeft = xTouchLeft;
                        yPrevLeft = yTouchLeft;
                    }
                    //In the region of the boom and curl circle
                    else if (xTouch1 >= 500 & yTouch1 > 100) {
                        xTouchRight = xTouch1;
                        yTouchRight = yTouch1;
                        xPrevRight = xTouchRight;
                        yPrevRight = yTouchRight;
                    }
                    //In the region of the left track slider
                    else if (xTouch1 > 200 & xTouch1 < 350 & yTouch1 < 210) {
                        yTrackLeft = yTouch1;
                        yTrackPrevLeft = yTrackLeft;
                    }
                    //In the region of the right track slider
                    else if (xTouch1 >= 350 & yTouch1 < 210 & xTouch1 < 450) {
                        yTrackRight = yTouch1;
                        yTrackPrevRight = yTrackRight;
                    }
                    Log.d("ADebugTag", "xTouch1: " + xTouch1);
                    Log.d("ADebugTag", "yTouch1: " + yTouch1);
                }
                mActivePointerId = event.pointer;
                //The pointer points to which finger or thumb. In this case, the other finger or thumb
                if (mActivePointerId == 1) {
                    Log.d("ADebugTag", "mActivePointerId: " + mActivePointerId);
                    xTouch2 = event.x;          //Get the x and y coordinates of the second touch
                    yTouch2 = event.y;
                    //In the region of the stick and spin circle
                    if (xTouch2 < 200 & yTouch2 > 100) {
                        xTouchLeft = xTouch2;
                        yTouchLeft = yTouch2;
                        xPrevLeft = xTouchLeft;
                        yPrevLeft = yTouchLeft;
                    }
                    //In the region of the boom and curl circle
                    if (xTouch2 >= 500 & yTouch2 > 100) {
                        xTouchRight = xTouch2;
                        yTouchRight = yTouch2;
                        xPrevRight = xTouchRight;
                        yPrevRight = yTouchRight;
                    }
                    //In the region of the left track slider
                    if (xTouch2 > 250 & xTouch2 < 350 & yTouch2 < 210) {
                        yTrackLeft = yTouch2;
                        yTrackPrevLeft = yTrackLeft;
                    }
                    //In the region of the right track slider
                    if (xTouch2 >= 350 & yTouch2 < 210 & xTouch2 < 400) {
                        yTrackRight = yTouch2;
                        yTrackPrevRight = yTrackRight;
                    }
                    Log.d("ADebugTag", "xTouch2: " + xTouch2);
                    Log.d("ADebugTag", "yTouch2: " + yTouch2);
                }
                if (landscape == 1) {               //The phone must be in landscape orientation
                    numAvg = 10;
                    xR = xTouchRight - 560;
                    yR = 275 - yTouchRight;
                    if (((int)Math.sqrt(Math.abs((xR*xR + yR*yR)))) > 85) {
                        //Inverse tangent to find the angle
                        angleR = Math.atan2((double) yR, (double) xR);
                        //cos for x
                        scaledXR = (int) (85 * Math.cos(angleR));
                        //sin for y
                        scaledYR = (int) (85 * Math.sin(angleR));
                        //Save the previous values in case the user lifts a thumb
                        xPrevRight = 560 + scaledXR;
                        yPrevRight = 275 - scaledYR;
                        //Draw the joystick maxed out
                        g.drawCircle((560 + scaledXR), (275 - scaledYR), 45);
                        g.drawLine(560, 275, (560 + scaledXR), (275 - scaledYR), 0);
                        //Do a numAvg moving average of the x and y coordinates of the thumb presses
                        //Shift all of the values in the temp arrays one value to the left
                        for (h = 1; h < numAvg; h++) {
                            tempCArr[h - 1] = tempCArr[h];
                            tempBArr[h - 1] = tempBArr[h];
                        }
                        //Pop the new x and y coordinates onto the stacks
                        tempCArr[numAvg - 1] = scaledXR;
                        tempBArr[numAvg - 1] = scaledYR;
                        //Once there are numAvg values in the stack rightCount = numAvg
                        if (rightCount < numAvg) {
                            rightCount++;
                        }
                        if (rightCount == numAvg) {
                            //Loop to total up the numAvg values in each array
                            for (j = 0; j < numAvg; j++) {
                                tempC += tempCArr[j];
                                tempB += tempBArr[j];
                            }
                            //The value to be sent out over Bluetooth is c. Take the average
                            c = (int) (tempC / numAvg);
                            //Make a dead zone along the y-axis. Otherwise both motors would always be spinning at the same time
                            if (c > -40 && c < 40) {
                                stopSendingCurl = 1;
                            } else {
                                stopSendingCurl = 0;
                            }
                            b = (int) (tempB / numAvg);
                            //Make a dead zone along the x-axis. Otherwise both motors would always be spinning at the same time
                            if (b > -40 && b < 40) {
                                stopSendingBoom = 1;
                            } else {
                                stopSendingBoom = 0;
                            }

                            tempC = 0;
                            tempB = 0;
                        }
                    } else if((((int)Math.sqrt(Math.abs((xR*xR + yR*yR))) <= 85))) {
                        //The thumb is within the circle. Draw the joystick at the thumb press
                        g.drawCircle(xTouchRight, yTouchRight, 45);
                        g.drawLine(560, 275, xTouchRight, yTouchRight, 0);

                        //Do a numAvg moving average of the x and y coordinates of the thumb presses
                        //Shift all of the values in the temp arrays one value to the left

                        for (k = 1; k < numAvg; k++) {
                            tempCArr[k - 1] = tempCArr[k];
                            tempBArr[k - 1] = tempBArr[k];
                        }




                        //Pop the new x and y coordinates onto the stacks
                        tempCArr[numAvg - 1] = xR;
                        tempBArr[numAvg - 1] = yR;
                        //Once there are numAvg values in the stack rightCount = numAvg
                        if (rightCount < numAvg) {
                            rightCount++;
                        }
                        if (rightCount == numAvg) {
                            //Loop to total up the numAvg values in each array
                            for (m = 0; m < numAvg; m++) {
                                tempC += tempCArr[m];
                                tempB += tempBArr[m];
                            }
                            //The value to be sent out over Bluetooth is c. Take the average
                            c = (int) (tempC / numAvg);
                            //Make a dead zone along the y-axis. Otherwise both motors would always be spinning at the same time
                            if (c > -40 && c < 40) {
                                stopSendingCurl = 1;
                            } else {
                                stopSendingCurl = 0;
                            }
                            b = (int) (tempB / numAvg);
                            //Make a dead zone along the x-axis. Otherwise both motors would always be spinning at the same time
                            if (b > -40 && b < 40) {
                                stopSendingBoom = 1;
                            } else {
                                stopSendingBoom = 0;
                            }

                            tempC = 0;
                            tempB = 0;
                        }

                    }
                    xL = xTouchLeft - 150;
                    yL = 275 - yTouchLeft;
                    if (((int)Math.sqrt(Math.abs((xL*xL + yL*yL)))) > 85) {
                        //Inverse tangent to find the angle
                        angleL = Math.atan2((double) yL, (double) xL);
                        //cos for x
                        scaledXL = (int) (85 * Math.cos(angleL));
                        //sin for y
                        scaledYL = (int) (85 * Math.sin(angleL));
                        //Save the previous values in case the user lifts a thumb
                        xPrevLeft = 140 + scaledXL;
                        yPrevLeft = 275 - scaledYL;
                        //Draw the joystick maxed out
                        g.drawCircle((140 + scaledXL), (275 - scaledYL), 45);
                        g.drawLine(140, 275, (140 + scaledXL), (275 - scaledYL), 0);
                        //Do a numAvg moving average of the x and y coordinates of the thumb presses
                        //Shift all of the values in the temp arrays one value to the left
                        for (k = 1; k < numAvg; k++) {
                            tempOArr[k - 1] = tempOArr[k];          //O for orbit
                            tempSArr[k - 1] = tempSArr[k];          //S for stick
                        }
                        //Pop the new x and y coordinates onto the stacks
                        tempOArr[numAvg - 1] = scaledXL;
                        tempSArr[numAvg - 1] = scaledYL;
                        //Once there are numAvg values in the stack rightCount = numAvg
                        if (leftCount < numAvg) {
                            leftCount++;
                        }
                        if (leftCount == numAvg) {
                            //Loop to total up the numAvg values in each array
                            for (m = 0; m < numAvg; m++) {
                                tempO += tempOArr[m];
                                tempS += tempSArr[m];
                            }
                            //o for orbit. Take the average
                            o = (int) (tempO / numAvg);
                            //Make a dead zone along the y-axis. Otherwise both motors would always be spinning at the same time
                            if (o > -40 && o < 40) {
                                stopSendingOrbit = 1;
                            } else {
                                stopSendingOrbit = 0;
                            }
                            //s for stick. Take the average
                            s = (int) (tempS / numAvg);
                            //Make a dead zone along the x-axis. Otherwise both motors would always be spinning at the same time
                            if (s > -40 && s < 40) {
                                stopSendingStick = 1;
                            } else {
                                stopSendingStick = 0;
                            }

                            tempO = 0;
                            tempS = 0;
                        }
                    } else if((((int)Math.sqrt(Math.abs((xL*xL + yL*yL))) <= 85))) {
                        //The thumb is within the circle. Draw the joystick at the thumb press
                        g.drawCircle((140 + xL), (275 - yL), 45);
                        g.drawLine(140, 275, (140 + xL), (275 - yL), 0);


                        //Do a numAvg moving average of the x and y coordinates of the thumb presses
                        //Shift all of the values in the temp arrays one value to the left
                        for (h = 1; h < numAvg; h++) {
                            tempOArr[h - 1] = tempOArr[h];
                            tempSArr[h - 1] = tempSArr[h];
                        }

                        //Pop the new x and y coordinates onto the stacks
                        tempOArr[numAvg - 1] = xL;
                        tempSArr[numAvg - 1] = yL;
                        //Once there are numAvg values in the stack rightCount = numAvg
                        if (leftCount < numAvg) {
                            leftCount++;
                        }


                        if (leftCount == numAvg) {
                            //Loop to total up the numAvg values in each array
                            for (j = 0; j < numAvg; j++) {
                                tempO += tempOArr[j];
                                tempS += tempSArr[j];
                            }
                            //o for orbit. Take the average
                            o = (int) (tempO / numAvg);
                            //Make a dead zone along the y-axis. Otherwise both motors would always be spinning at the same time
                            if (o > -40 && o < 40) {
                                stopSendingOrbit = 1;
                            } else {
                                stopSendingOrbit = 0;
                            }
                            //s for stick
                            s = (int) (tempS / numAvg);
                            //Make a dead zone along the x-axis. Otherwise both motors would always be spinning at the same time
                            if (s > -40 && s < 40) {
                                stopSendingStick = 1;
                            } else {
                                stopSendingStick = 0;
                            }

                            tempO = 0;
                            tempS = 0;
                        }


                    }
                    g.drawCircle(290, yTrackLeft, 45);
                    g.drawCircle(425, yTrackRight, 45);
                    l = 110 - yTrackLeft;
                    r = 110 - yTrackRight;
                }
            }
        }
    }
    @Override
    public void present ( float deltaTime){
        Graphics g = game.getGraphics();
    }

    @Override
    public void pause () {
        pixmap.dispose();
    }

    @Override
    public void resume () {

    }

    @Override
    public void dispose () {
        pixmap.dispose();
    }

    @Override
    public boolean isTouchDown(int pointer) {
        return false;
    }

    @Override
    public int getTouchX(int pointer) {
        return 0;
    }

    @Override
    public int getTouchY(int pointer) {
        return 0;
    }

    @Override
    public float getAccelX() {
        return 0;
    }

    @Override
    public float getAccelY() {
        return 0;
    }

    @Override
    public float getAccelZ() {
        return 0;
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        return null;
    }
}
