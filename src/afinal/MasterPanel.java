package afinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.text.DecimalFormat;

/*
    MasterPanel is the entire JPanel
    used to listen for mouse and key evens
    also used to set initialize the layout (centerPaneLayout, or some other)
 */


public class MasterPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    private final ArrayList<Layout> layouts;
    private static DecimalFormat df2 = new DecimalFormat("#.##");



    public MasterPanel(){
        layouts = new ArrayList<>();
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    public void setFocusable(boolean b) {
        super.setFocusable(b);
    }
    private void checkForAction(MouseInfo e){
        for(Layout layout: layouts)
            layout.checkMouseAction(e);
        repaint();
    }

    private void checkReadyTextEdits(char key){
        for(Layout layout: layouts)
            layout.checkReadyTextEdits(key);
        repaint();
    }


    public void initPanels(){

            CenterPaneLayout centerPaneLayout = new CenterPaneLayout(this);
            Account.setBalance(HistoryManager.getAccountBalance());

            //Initialize account balance and entry position
            centerPaneLayout.accountBalanceTextView.setText("Balance: "+df2.format(HistoryManager.getAccountBalance()));

            if(HistoryManager.getPositionSize()==0)
            {
                centerPaneLayout.entryPostionTextView.setText("Not in trade");
            }else {
                centerPaneLayout.entryPostionTextView.setText("Entry Price: "+df2.format(HistoryManager.getEntryPosition()));
            }

            layouts.add(centerPaneLayout.getLayouts());
            repaint();
    }



    @Override
    public Dimension getPreferredSize(){
        return new Dimension(500,500);
    }

    private void updateLayoutDimensions(){
        for(Layout layout: layouts) {
            layout.setHeight(getHeight());
            layout.setWidth(getWidth());
            layout.recalculateElementBounds(layout);
        }
    }

    @Override
    public void paintComponent(Graphics g){
        updateLayoutDimensions();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(),getHeight());
        for(Layout layout: layouts)
            layout.drawSelf((Graphics2D) g);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

        checkForAction(new MouseInfo(e.getX(),e.getY(), MouseActionEventType.ACTION_PRESSED));

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        checkForAction(new MouseInfo(e.getX(), e.getY(), MouseActionEventType.ACTION_RELEASED));
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        checkForAction(new MouseInfo(e.getX(),e.getY(), MouseActionEventType.ACTION_DRAGGED));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        checkForAction(new MouseInfo(e.getX(),e.getY(), MouseActionEventType.ACTION_MOVED));
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }



    @Override
    public void keyPressed(KeyEvent e) {
        checkReadyTextEdits(e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
