package afinal;

import javax.swing.*;

/*
VerticalLayout, extends Layout. the only thing it changes is hwo to recalculate the element bounds
 */

public class VerticalLayout extends Layout {

    public VerticalLayout(JPanel panel){
        super(panel);
    }

    // this function places elements vertically within the bounds of the layout
    public void recalculateElementBounds(Layout parentLayout){

        height = parentLayout.height;
        int currentYOffset = 0;

        for(int i=0;i<elements.size();i++){
            int currentElementHeight = (int)((height*(elements.get(i).getPreferredSizeY()))/100);
            int currentElementWidth = (int)((width*(elements.get(i).getPreferredSizeX()))/100);
            elements.get(i).setUpperLeftCorner(
                    topLeftX,currentYOffset + topLeftY);
            currentYOffset += currentElementHeight;
            elements.get(i).setHeight(currentElementHeight);
            elements.get(i).setWidth(currentElementWidth);
            elements.get(i).recalculateElementBounds(this);
        }
    }

}
