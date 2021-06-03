package afinal;

import javax.swing.*;

/*
    Horizontal, extends Layout. the only thing it changes is how to recalculate the element bounds
    horizontal layout stacks elements (including other layouts) horizontally
 */

public class HorizontalLayout extends Layout {

    public HorizontalLayout(JPanel panel){
        super(panel);
    }

    // this function places elements horizontally within the bounds of the layout
    public void recalculateElementBounds(Layout parentLayout){


        int currentXOffset = 0;
        for(int i=0;i<elements.size();i++){
            int currentElementHeight = (int)((height*(elements.get(i).getPreferredSizeY()))/100);
            int currentElementWidth = (int)((width*(elements.get(i).getPreferredSizeX()))/100);
            elements.get(i).setUpperLeftCorner(
                    topLeftX+currentXOffset,topLeftY);
            currentXOffset += currentElementWidth;
            elements.get(i).setHeight(currentElementHeight);
            elements.get(i).setWidth(currentElementWidth);
            elements.get(i).recalculateElementBounds(this);
        }
    }


}
