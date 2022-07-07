package com.esark.excavator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.esark.framework.AndroidGame;
import com.esark.framework.Game;
import com.esark.framework.Graphics;
import com.esark.framework.Input;
import com.esark.framework.MultiTouchHandler;
import com.esark.framework.Pixmap;
import com.esark.framework.Screen;

import static com.esark.excavator.Assets.excavatorTabletLandscapeBackground;
import static com.esark.excavator.Assets.redJoystick;
import static com.esark.framework.AndroidGame.landscape;

import java.lang.ref.WeakReference;
import java.util.List;

public class GameScreen extends Screen implements Input{
    Context context = null;
    int xTouch1 = 0;
    int yTouch1 = 0;

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
    int numAvg = 0;
    public static int stopSendingLeft = 0;
    public static int stopSendingRight = 0;
    public static int stopSendingBoom = 0;
    public static int stopSendingCurl = 0;
    public static int stopSendingOrbit = 0;
    public static int stopSendingStick = 0;
    public static int backgroundCount = 0;
    public static int joystickCount = 0;
    int count = 0;
    public Pixmap backgroundPixmap = null;
    public int bottomLeftPtr = -1;
    public int bottomRightPtr = -1;
    public int leftTrackPtr = -1;
    public int rightTrackPtr = -1;
    public int bottomLeftFlag = 0;
    public int bottomRightFlag = 0;
    public int leftTrackFlag = 0;
    public int rightTrackFlag = 0;
    public int xTouchBottomLeft = 850;
    public int yTouchBottomLeft = 1950;
    public int xTouchBottomRight = 3400;
    public int yTouchBottomRight = 1950;
    public int xPrevBottomLeft = 850;        //Adjust these/////////////////////////
    public int yPrevBottomLeft = 1950;
    public int xPrevBottomRight = 3400;
    public int yPrevBottomRight = 1950;
    public int yTrackLeft = 300;
    public int yTrackPrevLeft = 300;
    public int yTrackRight = 300;
    public int yTrackPrevRight = 300;
    public int touchUpCount = 0;
    public int renderCount = 1;
    public int innerCount = 0;


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
        backgroundPixmap = Assets.excavatorTabletLandscapeBackground;
        if (count == 0) {
            g.drawLandscapePixmap(excavatorTabletLandscapeBackground, 0, 0);
            g.drawJoystick(redJoystick, 850, 1950);          //Bottom Left Joystick
            g.drawJoystick(redJoystick, 3400, 1950);           //Bottom Right joystick
            g.drawJoystick(redJoystick, 410, 300);              //Left track
            g.drawJoystick(redJoystick, 3750, 300);             //Right track
        }
        int len = touchEvents.size();
        //Check to see if paused
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            mActivePointerId = event.pointer;
            //The pointer points to which finger or thumb. The first finger to touch is 0
            Log.d("ADebugTag", "mActivePointerId: " + mActivePointerId);
            if (event.type == TouchEvent.TOUCH_UP) {
                //  g.drawLandscapePixmap(backgroundPixmap, 0, 0);
/*
                g.drawJoystick(redJoystick, xPrevBottomLeft - 375, yPrevBottomLeft - 375);          //Bottom Left Joystick
                g.drawJoystick(redJoystick, xPrevBottomRight - 375, yPrevBottomRight - 375);           //Bottom Right joystick
                g.drawJoystick(redJoystick, 410, yTrackPrevLeft - 375);              //Left track
                g.drawJoystick(redJoystick, 3750, yTrackPrevRight - 375);             //Right track
                touchUpCount = 1;

 */
                touchUpCount = 1;
            }
                /*
                if (mActivePointerId == bottomLeftPtr) {
                    bottomLeftFlag = 0;
                }
                if (mActivePointerId == bottomRightPtr) {
                    bottomRightFlag = 0;
                }
                if (mActivePointerId == leftTrackPtr) {
                    leftTrackFlag = 0;
                }
                if (mActivePointerId == rightTrackPtr) {
                    rightTrackFlag = 0;
                }

            }
            if (bottomLeftFlag == 0) {
                g.drawJoystick(redJoystick, xPrevBottomLeft - 375, yPrevBottomLeft - 375);
            }
            if (bottomRightFlag == 0) {
                g.drawJoystick(redJoystick, xPrevBottomRight - 375, yPrevBottomRight - 375);
            }
            if (leftTrackFlag == 0) {
                g.drawJoystick(redJoystick, 410, yTrackPrevLeft - 375);
            }
            if (rightTrackFlag == 0) {
                g.drawJoystick(redJoystick, 3750, yTrackPrevRight - 375);
            }
*/



            if (event.type == TouchEvent.TOUCH_DRAGGED || event.type == TouchEvent.TOUCH_DOWN) {
                touchUpCount = 0;
                count = 1;
                xTouch1 = event.x;          //Get the x and y coordinates of the first touch
                yTouch1 = event.y;


                if (event.x > 2000 && event.x < 3000 && event.y < 1500) {
                    //Back Button Code Here
                    backgroundPixmap.dispose();
                    // System.gc();
                    Intent intent2 = new Intent(context.getApplicationContext(), Excavator.class);
                    context.startActivity(intent2);
                    return;
                }

                //In the region of the stick and spin circle
                if (xTouch1 < 2500 & yTouch1 > 1300) {
                    xTouchBottomLeft = xTouch1;
                    yTouchBottomLeft = yTouch1;
                    xPrevBottomLeft = xTouchBottomLeft;
                    yPrevBottomLeft = yTouchBottomLeft;
                    bottomLeftFlag = 1;
                    switch (mActivePointerId) {
                        case 0:
                            bottomLeftPtr = 0;
                            break;
                        case 1:
                            bottomLeftPtr = 1;
                            break;
                        case 2:
                            bottomLeftPtr = 2;
                            break;
                        case 3:
                            bottomLeftPtr = 3;
                            break;
                    }
                }
                //In the region of the boom and curl circle
                else if (xTouch1 >= 2500 & yTouch1 > 1300) {
                    xTouchBottomRight = xTouch1;
                    yTouchBottomRight = yTouch1;
                    xPrevBottomRight = xTouchBottomRight;
                    yPrevBottomRight = yTouchBottomRight;
                    bottomRightFlag = 1;
                    switch (mActivePointerId) {
                        case 0:
                            bottomRightPtr = 0;
                            break;
                        case 1:
                            bottomRightPtr = 1;
                            break;
                        case 2:
                            bottomRightPtr = 2;
                            break;
                        case 3:
                            bottomRightPtr = 3;
                            break;
                    }
                }
                //In the region of the left track slider
                else if (xTouch1 < 2000 & yTouch1 <= 1300) {
                    yTrackLeft = yTouch1;
                    yTrackPrevLeft = yTrackLeft;
                    leftTrackFlag = 1;
                    switch (mActivePointerId) {
                        case 0:
                            leftTrackPtr = 0;
                            break;
                        case 1:
                            leftTrackPtr = 1;
                            break;
                        case 2:
                            leftTrackPtr = 2;
                            break;
                        case 3:
                            leftTrackPtr = 3;
                            break;
                    }
                }
                //In the region of the right track slider
                else if (xTouch1 >= 4000 & yTouch1 <= 1300) {
                    yTrackRight = yTouch1;
                    yTrackPrevRight = yTrackRight;
                    rightTrackFlag = 1;
                    switch (mActivePointerId) {
                        case 0:
                            rightTrackPtr = 0;
                            break;
                        case 1:
                            rightTrackPtr = 1;
                            break;
                        case 2:
                            rightTrackPtr = 2;
                            break;
                        case 3:
                            rightTrackPtr = 3;
                            break;
                    }
                }
            }
            if(renderCount == 10){
                if(touchUpCount == 0){
                    g.drawLandscapePixmap(excavatorTabletLandscapeBackground, 0, 0);
                }
                g.drawJoystick(redJoystick, xTouchBottomLeft - 375, yTouchBottomLeft - 375);
                g.drawJoystick(redJoystick, xTouchBottomRight - 375, yTouchBottomRight - 375);
                g.drawJoystick(redJoystick, 410, yTrackLeft - 375);
                g.drawJoystick(redJoystick, 3750, yTrackRight - 375);
                renderCount = 0;
            }
            renderCount++;
            /*
            switch (renderCount){
                case 1: g.drawJoystick(redJoystick, xPrevBottomLeft - 375, yPrevBottomLeft - 375);
                    break;
                case 2: g.drawJoystick(redJoystick, xPrevBottomRight - 375, yPrevBottomRight - 375);
                    break;
                case 3: g.drawJoystick(redJoystick, 410, yTrackPrevLeft - 375);
                    break;
                case 4: g.drawJoystick(redJoystick, 3750, yTrackPrevRight - 375);
                    renderCount = 0;
                    break;
            }


             */
            /*
            switch (renderCount) {
                case 1:
                    if(touchUpCount == 0 & innerCount == 10){
                        g.drawLandscapePixmap(excavatorTabletLandscapeBackground, 0, 0);
                        innerCount = 0;
                    }
                    innerCount++;
                    break;
                case 2:
                    g.drawJoystick(redJoystick, xTouchBottomLeft - 375, yTouchBottomLeft - 375);
                    break;
                case 3:
                    g.drawJoystick(redJoystick, xTouchBottomRight - 375, yTouchBottomRight - 375);
                    break;
                case 4:
                    g.drawJoystick(redJoystick, 410, yTrackLeft - 375);
                    break;
                case 5:
                    g.drawJoystick(redJoystick, 3750, yTrackRight - 375);
                    renderCount = 0;
                    break;
            }
            renderCount++;

             */
        }

    }

            /*
        pixmap = Assets.excavatorTabletLandscapeBackground;
        //If count == 0 it's the first time and we draw the background and the four joysticks
        if (count == 0) {
            //If landscape == 1 the phone is in landscape orientation
            if (landscape == 1) {
                g.drawLandscapePixmap(pixmap, 0, 0);
            } else {
                g.drawPortraitPixmap(Assets.excavatorPortraitBackground, 0, 0);
            }
            g.drawJoystick(redJoystick, 850, 1950);          //Bottom Left Joystick
            g.drawJoystick(redJoystick, 3400, 1950);           //Bottom Right joystick
            g.drawJoystick(redJoystick,410, 300);              //Left track
            g.drawJoystick(redJoystick, 3750, 300);             //Right track

        }
     //   MultiTouchHandler myMultiTouch = new MultiTouchHandler(View view, 5000, 3500)
        int len = touchEvents.size();
        //Check to see if paused
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {

                if(event.x < 2500 & event.y > 1300) {
                    stopSendingLeft = 1;
                }
                else if(event.x >= 350 & event.y > 1300){
                    stopSendingRight = 1;
                }
                g.drawJoystick(redJoystick, xPrevLeft, yPrevLeft);          //Bottom Left Joystick
                g.drawJoystick(redJoystick, xPrevRight, yPrevRight);           //Bottom Right joystick
                g.drawJoystick(redJoystick,410, yTrackPrevLeft);              //Left track
                g.drawJoystick(redJoystick, 3750, yTrackPrevRight);             //Right track


            }


            if (event.type == TouchEvent.TOUCH_DRAGGED || event.type == TouchEvent.TOUCH_DOWN) {
                count = 1;

                if (event.x < 2500 & event.y > 1300) {
                    stopSendingLeft = 0;
                } else if (event.x >= 2500 & event.y > 1300) {
                    stopSendingRight = 0;
                }
                if (landscape == 1) {
                    g.drawLandscapePixmap(pixmap, 0, 0);
                } else {
                    g.drawPortraitPixmap(Assets.excavatorPortraitBackground, 0, 0);
                }
                // Save the ID of this pointer
                if (event.x > 2000 && event.x < 3000 && event.y < 1500) {
                    //Back Button Code Here
                    pixmap.dispose();
                   // System.gc();
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
                    if (xTouch1 < 2500 & yTouch1 > 1300) {
                        xTouchLeft = xTouch1;
                        yTouchLeft = yTouch1;
                        xPrevLeft = xTouchLeft;
                        yPrevLeft = yTouchLeft;
                    }
                    //In the region of the boom and curl circle
                    else if (xTouch1 >= 2500 & yTouch1 > 1300) {
                        xTouchRight = xTouch1;
                        yTouchRight = yTouch1;
                        xPrevRight = xTouchRight;
                        yPrevRight = yTouchRight;
                    }
                    //In the region of the left track slider
                    else if (xTouch1 < 2000 & yTouch1 <= 1300) {
                        yTrackLeft = yTouch1;
                        yTrackPrevLeft = yTrackLeft;
                    }
                    //In the region of the right track slider
                    else if (xTouch1 >= 4000 & yTouch1 <= 1300) {
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
                    if (xTouch2 < 2500 & yTouch2 > 1300) {
                        xTouchLeft = xTouch2;
                        yTouchLeft = yTouch2;
                        xPrevLeft = xTouchLeft;
                        yPrevLeft = yTouchLeft;
                    }
                    //In the region of the boom and curl circle
                    if (xTouch2 >= 2500 & yTouch2 > 1300) {
                        xTouchRight = xTouch2;
                        yTouchRight = yTouch2;
                        xPrevRight = xTouchRight;
                        yPrevRight = yTouchRight;
                    }
                    //In the region of the left track slider
                    if (xTouch2 < 2000 & yTouch2 <= 1300) {
                        yTrackLeft = yTouch2;
                        yTrackPrevLeft = yTrackLeft;
                    }
                    //In the region of the right track slider
                    if (xTouch2 >= 4000 & yTouch2 <= 1300) {
                        yTrackRight = yTouch2;
                        yTrackPrevRight = yTrackRight;
                    }
                    Log.d("ADebugTag", "xTouch2: " + xTouch2);
                    Log.d("ADebugTag", "yTouch2: " + yTouch2);
                }


             //   if (landscape == 1) {               //The phone must be in landscape orientation
                    g.drawCircle(event.x, event.y, 200);
                    //g.drawJoystick(redJoystick, xTouchRight, yTouchRight);
                 //   g.drawJoystick(redJoystick, xTouchLeft, yTouchLeft);
                   // g.drawJoystick(redJoystick, 410, yTrackLeft);
                    //g.drawJoystick(redJoystick, 3750, yTrackRight);

               // }

            }

             */

    @Override
    public void present ( float deltaTime){
        Graphics g = game.getGraphics();
    }

    @Override
    public void pause () {

    }

    @Override
    public void resume () {

    }

    @Override
    public void dispose () {
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
                    /*
                    numAvg = 10;
                    xR = xTouchRight - 3400;
                    yR = 1950 - yTouchRight;
                    if (((int)Math.sqrt(Math.abs((xR*xR + yR*yR)))) > 570) {
                        //Inverse tangent to find the angle
                        angleR = Math.atan2((double) yR, (double) xR);
                        //cos for x
                        scaledXR = (int) (570 * Math.cos(angleR));
                        //sin for y
                        scaledYR = (int) (570 * Math.sin(angleR));
                        //Save the previous values in case the user lifts a thumb
                        xPrevRight = 3400 + scaledXR;
                        yPrevRight = 1950 - scaledYR;
                        //Draw the joystick maxed out
                        g.drawJoystick(redJoystick, (3400 + scaledXR), (1950 - scaledYR));
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
                    } else if((((int)Math.sqrt(Math.abs((xR*xR + yR*yR))) <= 570))) {
                        //The thumb is within the circle. Draw the joystick at the thumb press
                        g.drawJoystick(redJoystick, xTouchRight, yTouchRight);

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
              */