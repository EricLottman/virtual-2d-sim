package org.firstinspires.ftc.teamcode.myopmodes.subsystems;

import static java.lang.Math.*;
import org.firstinspires.ftc.teamcode.myopmodes.subsystems.constants;

import java.util.ArrayList;

public class ease_commands {
    private static constants c = new constants();
    // literally just anything to make life easier
    // if imported static you can just use it in your code
    // just call import static org.firstinspires.ftc.teamcode.subsystems.ease_commands.*;

    // math based functions
    public static double sumAll(double[] i) {
        double o = 0;
        for ( double x : i) {
            o+=x;
        }
        return o;
    }
    public static double sumAll(ArrayList<Double> i) {
        double o = 0;
        for (double x : i) {
            o+=x;
        }
        return o;
    }
    public static int sumAll(int[] i) {
        int o = 0;
        for ( double x : i) {
            o+=x;
        }
        return o;
    }
    public static int minAll(int[] input) {
        int o = 0;
        int i = 0;
        for (double x : input) {
            o = min(input[i], input[i+1]);
            if ((i+1) == input.length) {
                break;
            }
            i++;
        }
        return o;
    }
    public static double minAll(double[] input) {
        double o = 0;
        int i = 0;
        for (double x : input) {
            o = min(input[i], input[i+1]);
            if ((i+1) == input.length) {
                break;
            }
            i++;
        }
        return o;
    }
    public static int maxAll(int[] input) {
        int o = 0;
        int i = 0;
        for (double x : input) {
            o = max(input[i], input[i+1]);
            if ((i+1) == input.length) {
                break;
            }
            i++;
        }
        return o;
    }
    public static double maxAll(double[] input) {
        double o = 0;
        int i = 0;
        for (double x : input) {
            o = max(input[i], input[i+1]);
            if ((i+1) == input.length) {
                break;
            }
            i++;
        }
        return o;
    }

    // array based functions
    // findInArr assumes there is only once entry with that value
    public double findInArr(double flag, double[] arr) {
        int i = 0;
        for (double x : arr) {
            if (x == flag) {
                break;
            }
            i++;
        }
        return i;
    }
    public static int findInArr(int flag, int[] arr) {
        int i = 0;
        for (int x : arr) {
            if (x == flag) {
                break;
            }
            i++;
        }
        return i;
    }
    public int findInArr(String flag, String[] arr) {
        int i = 0;
        for (String x : arr) {
            if (x.equals("flag")) {
                break;
            }
            i++;
        }
        return i;
    }

    public static double toIN(double i, String u) {
        return u.equals("in")?i:u.equals("cm")?i/2.54:u.equals("mm")?i/25.4:0;
    }
    public static int inTT_dt(double inches){
        return (int)round(c.conversion_factor_dt*inches);
    }
    public static int inTT_linear_slide(double inches){
        return (int)round(c.conversion_factor_linear_slide*inches);
    }

    public static String colorID(int[] i) {
        // make colors with matching indices to their values
//        String[] colors = {"black","very_light_red","light_red","red","dark_red","very_dark_red",
//                "very_light_blue", "light_blue","blue","dark_blue","very_dark_blue","very_light_green",
//                "light_green","green","dark_green","very_dark_green","white"};
//        int[][] color_values = {{0,0,0},{255,102,102},{255,51,51},{255,0,0},{204,0,0},
//                {153,0,0},{51,204,255},{51,153,255},{0,0,255},{0,0,204},{0,0,153},{102,255,102},
//                {0,255,51},{0,204,0},{0,153,0},{0,102,0},{255,255,255}};
        String[] colors = {"violet","indigo","blue","green","yellow","orange","red"};
        int[][] color_values = {{148, 0, 211},{75, 0, 130},{0, 0, 255},{0, 255, 0},{255, 255, 0},{255, 127, 0},{255, 0 , 0}};

        // new array to store total difference of r, g, and b values to our known colors
        int[] similarity = new int[colors.length];

        // loop through color values
        int iterator = 0;
        for (int[] arraydata : color_values) {
            int temp,iter;
            temp=iter=0;
            // loop through each piece of data
            for (int data : arraydata) {
                // compare our data to color_values and save the total difference of all r, g, and b
                temp+=abs(data - i[iter]);
                iter++;
            }
            // assign the total difference to the matching index
            similarity[iterator] = temp;
            iterator++;
        }

        // get the smallest number in similarity then find its index
        int outputIndex = findInArr(minAll(similarity), similarity);

        // return the name of the color equal to the index of the lowest color difference
        return colors[outputIndex];
    }
}
