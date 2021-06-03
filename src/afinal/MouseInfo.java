package afinal;

/*
small data class for holding info about a mouse press (action and xy coordinate)
added this class because for some reason MouseEvent from awt doesn't contain info
about the type of mouse action

this class is used to construct objects passed from MasterPanel to Button, to update
the button's color based on the mouse movement/click

 */

public final class MouseInfo {

    public final MouseActionEventType action;
    private final int x;
    private final int y;

    public MouseInfo(int x, int y, MouseActionEventType actionEventType){
        this.x = x;
        this.y = y;
        this.action = actionEventType;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
