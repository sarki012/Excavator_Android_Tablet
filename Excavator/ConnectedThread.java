package com.esark.excavator;


import android.bluetooth.BluetoothSocket;
import android.os.Handler;

import android.os.SystemClock;
import android.util.Log;

import com.esark.framework.AndroidGame;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.esark.excavator.GameScreen.c;
import static com.esark.excavator.GameScreen.b;
import static com.esark.excavator.GameScreen.delay;
import static com.esark.excavator.GameScreen.l;
import static com.esark.excavator.GameScreen.o;
import static com.esark.excavator.GameScreen.r;
import static com.esark.excavator.GameScreen.s;
import static com.esark.excavator.GameScreen.stopSendingBoom;
import static com.esark.excavator.GameScreen.stopSendingCurl;
import static com.esark.excavator.GameScreen.stopSendingLeft;
import static com.esark.excavator.GameScreen.stopSendingLeftTrack;
import static com.esark.excavator.GameScreen.stopSendingOrbit;
import static com.esark.excavator.GameScreen.stopSendingRight;
import static com.esark.excavator.GameScreen.stopSendingRightTrack;
import static com.esark.excavator.GameScreen.stopSendingStick;
//import static com.esark.excavator.GameScreen.stopSending;
//import static com.esark.excavator.GameScreen.stopSendingBoom;
//import static com.esark.excavator.GameScreen.stopSendingCurl;


public class ConnectedThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private final Handler mHandler;
   // public IntToChars mIntToChars;
    public String[] returnArray = new String[] {"0", "0", "0", "0"};
    IntToChars mIntToChars = new IntToChars();

    public ConnectedThread(BluetoothSocket socket, Handler handler) {
        mmSocket = socket;
        mHandler = handler;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    @Override
    public void run() {
        int bytes; // bytes returned from read()
        // Keep listening to the InputStream until an exception occurs
        while (true) {
            try {
                // Read from the InputStream
                bytes = mmInStream.available();
                byte[] buffer = new byte[60];
                if (bytes != 0) {
                    //SystemClock.sleep(100); //pause and wait for rest of data. Adjust this depending on your sending speed. Originally 100
                    bytes = mmInStream.read(buffer, 0, 50); // record how many bytes we actually read
                    mHandler.obtainMessage(AndroidGame.MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget(); // Send the obtained bytes to the UI activity

                }
            } catch (IOException e) {
                e.printStackTrace();

                break;
            }

            //Try delays between the characters to make it not jump SystemClock.sleep
            /////////////////Bucket Curl//////////////////////
            returnArray = mIntToChars.IntToCharsMethod(c);      //Send an integer and get three chars in the returnArray as a return value
            write("c");               //c for curl. Number of pixels in the x-direction
            write(returnArray[3]);          //d3 (+/-)
            write(returnArray[2]);          //d2 (Left digit)
            write(returnArray[1]);          //d1 (middle digit)
            write(returnArray[0]);          //d0 (right digit)
            SystemClock.sleep(10);
            ////////////////Boom//////////////////////////////
            returnArray = mIntToChars.IntToCharsMethod(b);
            //Send an integer and get three chars in the returnArray as a return value
            write("b");               //b for boom. Number of pixels in the y-direction
            write(returnArray[3]);          //d3 (+/-)
            write(returnArray[2]);          //d2 (Left digit)
            write(returnArray[1]);          //d1 (middle digit)
            write(returnArray[0]);          //d0 (right digit)
            SystemClock.sleep(10);

            if(stopSendingLeft == 1){
                write("#");
            }
            else {
                ///////////Rotate//////////////////////////
                returnArray = mIntToChars.IntToCharsMethod(o);      //Send an integer and get three chars in the returnArray as a return value
                write("o");               //o for rotate. Number of pixels in the x-direction
                write(returnArray[3]);          //d3 (+/-)
                write(returnArray[2]);          //d2 (Left digit)
                write(returnArray[1]);          //d1 (middle digit)
                write(returnArray[0]);          //d0 (right digit)
                SystemClock.sleep(10);
            }

            ////////////////Boom//////////////////////////////
            returnArray = mIntToChars.IntToCharsMethod(s);
            //Send an integer and get three chars in the returnArray as a return value
            write("s");               //s for stick. Number of pixels in the y-direction
            write(returnArray[3]);          //d3 (+/-)
            write(returnArray[2]);          //d2 (Left digit)
            write(returnArray[1]);          //d1 (middle digit)
            write(returnArray[0]);          //d0 (right digit)
            SystemClock.sleep(10);
            
            if(stopSendingLeftTrack == 1){
                write("$");
            }
            else {
                //Left Track
                returnArray = mIntToChars.IntToCharsMethod(l);      //Send an integer and get three chars in the returnArray as a return value
                write("l");               //l for left track. Number of pixels in the y-direction
                write(returnArray[3]);          //d3 (+/-)
                write(returnArray[2]);          //d2 (Left digit)
                write(returnArray[1]);          //d1 (middle digit)
                write(returnArray[0]);          //d0 (right digit)
                SystemClock.sleep(10);
            }
            if(stopSendingRightTrack == 1){
                write("@");
            }
            else {
                //Right Track
                returnArray = mIntToChars.IntToCharsMethod(r);      //Send an integer and get three chars in the returnArray as a return value
                write("r");               //r for right track. Number of pixels in the y-direction
                write(returnArray[3]);          //d3 (+/-)
                write(returnArray[2]);          //d2 (Left digit)
                write(returnArray[1]);          //d1 (middle digit)
                write(returnArray[0]);          //d0 (right digit)
                SystemClock.sleep(10);
            }

            returnArray = mIntToChars.IntToCharsMethod(delay);      //Send an integer and get three chars in the returnArray as a return value
            write("d");               //r for right track. Number of pixels in the y-direction
            write(returnArray[3]);          //d3 (+/-)
            write(returnArray[2]);          //d2 (Left digit)
            write(returnArray[1]);          //d1 (middle digit)
            write(returnArray[0]);          //d0 (right digit)
            SystemClock.sleep(10);
        }
    }

    /* Call this from the main activity to send data to the remote device
    * So I'm now sending a dummy command to the device every 1 second (kind of "keep-alive" command) and now Android is happy because it's not a one-way communication anymore
    *  */
    public void write(String input) {
        byte[] bytes = input.getBytes();           //converts entered String into bytes
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) { }
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }
}