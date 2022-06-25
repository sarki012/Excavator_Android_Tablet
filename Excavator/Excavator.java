package com.esark.excavator;

import com.esark.framework.Screen;
import com.esark.framework.AndroidGame;
import com.esark.excavator.LoadingScreen;


public class Excavator extends AndroidGame {
    /*
    AndroidGame is an abstract class. This means it doesn't have to implement all methods of Game,
    as long as one of the classes extending AndroidGame does this. getStartScreen() does this.
     */
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }
}