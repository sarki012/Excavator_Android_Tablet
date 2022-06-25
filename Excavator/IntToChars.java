package com.esark.excavator;

public class IntToChars {
    String[] tripleString = new String[] {"0,", "0", "0"};
    public IntToChars(){
    }
    public String[] IntToCharsMethod(int integer){
        //int to char code here...
        if(integer < 0){
            tripleString[2] = "-";
        }
        else{
            tripleString[2] = "+";
        }
        integer = Math.abs(integer);
        switch((int)integer%10) {
            case 0 :
                tripleString[0] = "0";
                break;
            case 1 :
                tripleString[0] = "1";
                break;
            case 2 :
                tripleString[0] = "2";
                break;
            case 3 :
                tripleString[0] = "3";
                break;
            case 4 :
                tripleString[0] = "4";
                break;
            case 5 :
                tripleString[0] = "5";
                break;
            case 6 :
                tripleString[0] = "6";
                break;
            case 7 :
                tripleString[0] = "7";
                break;
            case 8 :
                tripleString[0] = "8";
                break;
            case 9 :
                tripleString[0] = "9";
                break;
            default :
                tripleString[0] = "-";
        }
        integer /= 10;
        switch((int)integer) {
            case 0 :
                tripleString[1] = "0";
                break;
            case 1 :
                tripleString[1] = "1";
                break;
            case 2 :
                tripleString[1] = "2";
                break;
            case 3 :
                tripleString[1] = "3";
                break;
            case 4 :
                tripleString[1] = "4";
                break;
            case 5 :
                tripleString[1] = "5";
                break;
            case 6 :
                tripleString[1] = "6";
                break;
            case 7 :
                tripleString[1] = "7";
                break;
            case 8 :
                tripleString[1] = "8";
                break;
            case 9 :
                tripleString[1] = "9";
                break;
            default :
                tripleString[1] = "-";
        }


        return tripleString;
    }
}

