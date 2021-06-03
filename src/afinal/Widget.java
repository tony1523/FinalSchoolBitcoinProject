package afinal;

import java.awt.*;
/*
    Widget - abstract class extended by Button, Slider, and TextEdit (and possibly others.
    implements UIElement
    provides all the functionality needed for a typical user interface element, such as
    changing color when hovered by mouse or clicked, setting size and resizing automatically, etc.

    the widget is a 'primitive' in the sense that it cannot contain other widgets or layouts
    layouts can contain other layouts and widgets

    all the fields are protected right now. some of them could probably be made private
    (whichever fields aren't used directly in Slider, Button, or TextEdit could be made private)
 */

public abstract class Widget implements UIElement {

    protected int xPos, yPos;
    protected int width, height;
    protected boolean within = false;
    protected boolean pressed = false;
    protected final int verticalPadding = 5;
    protected final int horizontalPadding = 5;
    protected Color dynamicColor = Color.DARK_GRAY;
    protected double preferredSizeX;
    protected double preferredSizeY;

    public Widget(){}

    public Widget(double preferredSizeX, double preferredSizeY){
        this.preferredSizeX = preferredSizeX;
        this.preferredSizeY = preferredSizeY;
    }

    protected void respondReleased(boolean currentWithin){
        if(pressed) {
            pressed = false;
            dynamicColor = Color.GRAY;
        }
    }

    protected void respondPressed(boolean currentWithin){
        if(currentWithin && !pressed) {
            pressed = true;
            dynamicColor = Color.LIGHT_GRAY;
        }
    }

    protected void respondMoved(boolean currentWithin){
        if(!currentWithin && within){
            within = false;
            dynamicColor = Color.DARK_GRAY;
        }
        else if(currentWithin && !within){
            within = true;
            dynamicColor = Color.GRAY;
        }
    }

    public void setAction(MouseInfo e) {
        boolean currentWithin = mouseWithin(e);

        switch(e.action){
            case ACTION_MOVED -> respondMoved(currentWithin);
            case ACTION_PRESSED -> respondPressed(currentWithin);
            case ACTION_RELEASED -> respondReleased(currentWithin);
        }
    }

    @Override
    public void drawSelf(Graphics2D g2) { } //overridden by all subclasses

    @Override
    public void setUpperLeftCorner(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void recalculateElementBounds(Layout parentLayout) { }

    @Override
    public void sendKeyPress(char key) {

    }

    @Override
    public void setPreferredSize(double xPercentage, double yPercentage) {
        preferredSizeX = xPercentage;
        preferredSizeY = yPercentage;
    }

    @Override
    public double getPreferredSizeX() {
        return preferredSizeX;
    }

    @Override
    public double getPreferredSizeY() {
        return preferredSizeY;
    }


    @Override
    public boolean mouseWithin(MouseInfo e) {
        if(e.getX() > xPos && e.getX() <= xPos + width && e.getY() > yPos && e.getY() <= yPos + height)
            return true;
        else return false;
    }
}
