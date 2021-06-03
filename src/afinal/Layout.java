package afinal;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/*
    abstract Layout class. extended by both Vertical and Horizontal layouts.
    provides methods for adjusting dimensions of rectangular layout element on a JPanel
    provides methods for adding buttons/sub-layouts and automatically updating size
    updates size automatically based on size of JPanel
    allows the MasterPanel to treat all layouts in a uniform way
    (can have an array of 'Layouts' containing vertical, horizontal, and CenterPane layouts)

    using a combination of horizontal and vertical layouts, pretty much any UI can be composed
 */

public abstract class Layout implements UIElement {

    protected final JPanel panel;
    protected final ArrayList<UIElement> elements;
    protected int topLeftX, topLeftY;
    protected int width, height;
    protected double preferredSizeX;
    protected double preferredSizeY;


    protected Layout(JPanel panel) {
        this.panel = panel;
        elements = new ArrayList<>();
        setUpperLeftCorner(0, 0);
        setWidth(panel.getWidth());
        setHeight(panel.getHeight());
    }

    public void checkMouseAction(MouseInfo e){
        for(UIElement uiElement: elements)
            uiElement.setAction(e);
    }

    public void checkReadyTextEdits(char key){
        for(UIElement uiElement: elements)
            uiElement.sendKeyPress(key);
    }

    public void addUIElement(UIElement element){
        elements.add(element);
        recalculateElementBounds(this);
        panel.repaint();
    }

    public void recalculateElementBounds(Layout parentLayout){}

    public void drawSelf(Graphics2D g2){
        for(UIElement element: elements)
            element.drawSelf(g2);
    }

    @Override
    public void setUpperLeftCorner(int xPos, int yPos) {
        this.topLeftX = xPos;
        this.topLeftY = yPos;
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
    public void setAction(MouseInfo e){
        for(UIElement element: elements)
            element.setAction(e);
    }

    public void sendKeyPress(char key){
        for(UIElement element: elements)
            element.sendKeyPress(key);
    }

    public boolean mouseWithin(MouseInfo e){
        return topLeftX <= e.getX() && topLeftX + width > e.getX()
                && topLeftY <= e.getY() && topLeftY + height < e.getY();
    }

    @Override
    public double getPreferredSizeX(){
        return preferredSizeX;
    }

    @Override
    public double getPreferredSizeY(){
        return preferredSizeY;
    }

    @Override
    public void setPreferredSize(double percentX, double percentY){
        preferredSizeX = percentX;
        preferredSizeY = percentY;
        height = (int)((panel.getHeight()*preferredSizeY)/100);
        width = (int)((panel.getHeight()*preferredSizeX)/100);

    }
}
