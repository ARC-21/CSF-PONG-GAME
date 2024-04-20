import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

//InflatingCircle extends YOUR Circle class and implements Animatable

//Animatable requires a step() method, which is in this file, and a
//drawMe(Graphics g) method, which you should already have in Circle

public class Paddle extends Square implements Animatable
{
    private int dY;
    private int height;
    private int score;

    // constructors
    public Paddle()
    {
        super(10, 250, 250, Color.WHITE);
        dY = 0;
        height = 50;
        score = 0;
    }

    public Paddle(int sideValue, int xValue, int yValue, Color cValue)
    {
        super(sideValue, xValue, yValue, cValue);
        dY = 0;
        height = 70;
    }
    public int getDY()
    {
        return dY;
    }
    public int getHeight() { return height; }
    public int getScore() {return score;}
    public void setScore(int score) {this.score = score;}

    //modifiers
    public void setDY(int dYValue)
    {
        dY = dYValue;
    }

    public void drawMe(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(getX(), getY(), getSide(), height);
    }


    //instance methods
    public void step()  //Implement Animatable's required step()
    {
        if (getY() < 0) {
            setY(0);
        }
        if (getY() > (430)) {
            setY(430);
        }
        setY(getY() + dY);
    }
}