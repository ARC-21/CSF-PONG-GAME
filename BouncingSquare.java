import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

//InflatingCircle extends YOUR Circle class and implements Animatable

//Animatable requires a step() method, which you will write below, and a
//drawMe(Graphics g) method, which you should already have written in Circle

public class BouncingSquare extends Square implements Animatable
{
    private int dX; //largest value we want r to be
    private int dY;
    // constructors
    public BouncingSquare() {
        super(10, 250, 262, Color.WHITE);
        dX = 1;
        dY = 2;
    }

    /*
       Add a 7-arg constructor that lets us specify an inflating circle of any size, at any
       x-position, y-position, of any color, with custom values for dR, minR, and maxR.
       */
    public BouncingSquare(int s, int x, int y, Color c, int dX, int dY) {
        super(s, x, y, c);
        this.dX = dX;
        this.dY = dY;
    }


    //accessors
    public int getDX()
    {
        return dX;
    }

    //modifiers
    public void setDX(int dXValue)
    {
        dX = dXValue;
    }
    public void setDY(int dYValue) { dY = dYValue; }
    public int getDY() { return dY; }
    //instance methods
    public void step()  //Implement Animatable's required step()
    {
        //Check to see if our circle is too small
        //If so, make sure dR is positive (radius is increasing)
        if (getX() >= 500-getSide())
        {
            dX *= -1;
        }
        if (getY() >= 500-getSide()) {
            dY *= -1;
        }
        //Check to see if our circle is too big
        //If so, make sure dR is negative (radius is decreasing)
        if (getX() <= 0)
        {
            dX *= -1;
        }
        if (getY() <= 0) {
            dY *= -1;
        }
        setX(getX() + dX);  //Change the radius a bit - either out or in - for each animation step
        setY(getY() + dY);
    }
    public int leftCollide(Paddle p) {
        if (getX() <= p.getX() + p.getSide()) {
            if (getY() + getSide() > p.getY() && p.getY() + p.getHeight() > getY()) {
                // collisions: 0-14, 15-28, 29-42, 43-56, 57-70
                // speeds: 5, 3, 1, 2, 4
                if (getY() - p.getY() <= 7) {
                    if (getDY() < 0) {
                        setDY(-3);
                    }
                    else {
                        setDY(3);
                    }
                }
                else if (getY() - p.getY() <= 14) {
                    if (getDY() < 0) {
                        setDY(-2);
                    }
                    else {
                        setDY(2);
                    }
                }
                else if (getY() - p.getY() <= 56) {
                    if (getDY() < 0) {
                        setDY(-1);
                    }
                    else {
                        setDY(1);
                    }
                }
                else if (getY() - p.getY() <= 63) {
                    if (getDY() < 0) {
                        setDY(-2);
                    }
                    else {
                        setDY(2);
                    }
                }
                else {
                    if (getDY() < 0) {
                        setDY(-3);
                    }
                    else {
                        setDY(3);
                    }
                }
                return 2;
            }
            return 1;

        }
        return 0;
    }
    public int rightCollide(Paddle p) {
        if (getX()+getSide() > p.getX()) {
            if (getY()+getSide() > p.getY() && p.getY() + p.getHeight() > getY()) {
                if (p.getY() - getY() <= 14) {
                    if (getDY() < 0) {
                        setDY(-3);
                    }
                    else {
                        setDY(3);
                    }
                }
                else if (p.getY() + p.getHeight() - getY() <= 28) {
                    if (getDY() < 0) {
                        setDY(-2);
                    }
                    else {
                        setDY(2);
                    }
                }
                else if (p.getY() + p.getHeight() - getY() <= 42) {
                    if (getDY() < 0) {
                        setDY(-1);
                    }
                    else {
                        setDY(1);
                    }
                }
                else if (p.getY() + p.getHeight() - getY() <= 56) {
                    if (getDY() < 0) {
                        setDY(-2);
                    }
                    else {
                        setDY(2);
                    }
                }
                else {
                    if (getDY() < 0) {
                        setDY(-3);
                    }
                    else {
                        setDY(3);
                    }
                }
                return 2;
            }
            return 1;
        }
        return 0;
    }
}