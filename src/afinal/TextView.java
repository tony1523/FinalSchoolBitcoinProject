package afinal;

/*
Button class. used to represent a button. user can click on the button.
right now the button has no functionality.
functionality could easily be added (and will be required in final project)

 */

import org.w3c.dom.Text;

import java.awt.*;

public class TextView extends Widget {

    private String text;

    public TextView(double preferredSizeX, double preferredSizeY,String text){
        super(preferredSizeX,preferredSizeY);
        this.text=text;
    }



    @Override
    public void drawSelf(Graphics2D g2) {

        g2.setColor(Color.GRAY);

        g2.fillRect(xPos+horizontalPadding, yPos+verticalPadding,
                width-horizontalPadding*2, height-verticalPadding*2);

        drawCenteredLabel(g2);
    }

public void setText(String newText){
    text=newText;
}
    private void drawCenteredLabel(Graphics2D g2){

        int stringWidth = g2.getFontMetrics().stringWidth(text);

        if(within==false)
            g2.setColor(Color.WHITE);
        else g2.setColor(Color.BLACK);

        g2.drawString(text,xPos+width/2 - stringWidth/2,yPos + height/2);
    }


}
