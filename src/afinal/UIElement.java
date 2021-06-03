package afinal;

import java.awt.*;
/*

    UIElement interface, implemented by layout and button.
    allows layouts to treat elements (buttons, textView, etc.) and sub-layouts as the same type
    this enables functions like 'addUIElement' in 'Layout',
    which can add either a button, or another layout
    both the layouts and the buttons implement this interface, so they can adjust their dimensions and
    draw themselves

    for an interface, this actually has quite a few methods. maybe it should be broken up
 */


public interface UIElement {

    void drawSelf(Graphics2D g2);
    void setUpperLeftCorner(int xPos, int yPos);
    void setWidth(int width);
    void setHeight(int height);
    void setAction(MouseInfo e);
    void recalculateElementBounds(Layout parentLayout);
    void sendKeyPress(char key);
    void setPreferredSize(double percentageX, double percentageY);
    double getPreferredSizeX();
    double getPreferredSizeY();
    boolean mouseWithin(MouseInfo e);

}
