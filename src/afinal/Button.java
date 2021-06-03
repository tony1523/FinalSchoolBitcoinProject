package afinal;

/*
Button class. used to represent a button. user can click on the button.
right now the button has no functionality.
functionality could easily be added (and will be required in final project)

 */

import java.awt.*;

public class Button extends Widget {

private  String buttonLabel="button";

private OnPressedListener listener;

    public Button(){}

    public Button(double preferredSizeX, double preferredSizeY){
        super(preferredSizeX, preferredSizeY);
    }

    public Button(double preferredSizeX, double preferredSizeY,String label){
        super(preferredSizeX, preferredSizeY);
        buttonLabel= label;
    }

    @Override
    protected void respondPressed(boolean currentWithin){
        if(currentWithin && !pressed) {
            pressed = true;
            dynamicColor = Color.PINK;
            listener.pressed();
        }
    }


    @Override
    public void drawSelf(Graphics2D g2) {

        g2.setColor(dynamicColor);

        g2.fillRect(xPos+horizontalPadding, yPos+verticalPadding,
                width-horizontalPadding*2, height-verticalPadding*2);

        drawCenteredLabel(g2);
    }

    private void drawCenteredLabel(Graphics2D g2){

        int stringWidth = g2.getFontMetrics().stringWidth(buttonLabel);

        if(within==false)
            g2.setColor(Color.WHITE);
        else g2.setColor(Color.BLACK);

        g2.drawString(buttonLabel,xPos+width/2 - stringWidth/2,yPos + height/2);
    }
public void setPressedListener(OnPressedListener newListener){
        listener = newListener;
}

}
