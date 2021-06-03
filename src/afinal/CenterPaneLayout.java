package afinal;

/*

    center pane layout class. used to create a layout with 5 panels:
    1) top panel
    2) left panel
    3) center panel (the largest panel - used to hold main display)
    4) right panel
    5) bottom panel

    these panels are built up from horizontal and vertical layouts
    main panel is a vertical layout, which holds the following
    1) horizontal layout (top panel
    2) horizontal layout - middle panel, which holds:
        a) left panel (Vertical layout)
        b) center panel (vertical layout)
        c) right panel (vertical layout)
    3) bottom panel (horizontal layout

    each panel can contain multiple widgets, but center panel should remain clear (for display)

    // use a builder to construct this

 */

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;


public class CenterPaneLayout {

    Layout layout;
    public TextView accountBalanceTextView;
    public TextView entryPostionTextView;
    final static int speedArray[] = {10000,5000, 1000, 500, 250, 100};
    public static int speedIndex = HistoryManager.getSpeed();;


//initialize JFrame and JPanel
    public static void showWindow(){
        JFrame jf = new JFrame("Bitcoin app");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setSize(600,500);
        MasterPanel master = new MasterPanel();
        jf.add(master);
        jf.setVisible(true);
        CryptoDataFetcher.init(master);
        master.initPanels();

        //Press on X listener
        jf.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                try {
                    HistoryManager.saveState();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

    }


    public CenterPaneLayout(JPanel panel){

        layout = new VerticalLayout(panel);

        // two main sections: top, middle, bottom
        Layout topSection = new HorizontalLayout(panel);
        topSection.setPreferredSize(110,15);
        Layout middleSection = new HorizontalLayout(panel);
        middleSection.setPreferredSize(100,80);



        // three parts of the middle layout
        Layout middleLeft = new VerticalLayout(panel);
        middleLeft.setPreferredSize(15,100);
        Layout middleMiddle = new HorizontalLayout(panel); // can be horizontal or vertical
        middleMiddle.setPreferredSize(70,100);
        Layout middleRight = new VerticalLayout(panel);
        middleRight.setPreferredSize(15,100);

        // set up the buttons/textview/sliders/etc for each layout
        accountBalanceTextView=new TextView(45,100,"" );
        Account.accountBalanceTextView=accountBalanceTextView;
        topSection.addUIElement(accountBalanceTextView);

        //Show entry position
        entryPostionTextView =new TextView(45,100,"");
        Account.accountEntryPoint= entryPostionTextView;
        topSection.addUIElement(entryPostionTextView);

        //Slow speed of the graph updating
        Button slowButton=new Button(100,25,"SLOW");
        slowButton.setPressedListener(new OnPressedListener() {
            @Override
            public void pressed() {
                if(speedIndex <=5&& speedIndex >0){
                    speedIndex--;
                }
                System.out.println(speedArray[speedIndex]);

            }
        });

        middleLeft.addUIElement(slowButton);

        //Move graph to the left
        Button leftButton=new Button(100,25,"LEFT");
        leftButton.setPressedListener(new OnPressedListener() {
            @Override
            public void pressed() {

                if (TimeSeriesDisplayWidget.start >=3 && TimeSeriesDisplayWidget.zoomPercent <1)
                    {
                       TimeSeriesDisplayWidget.moveLeftAndRight+=3;

                    }
                System.out.println( TimeSeriesDisplayWidget.start);

            }
        });
        middleLeft.addUIElement(leftButton);

        //Buy button
        Button buyButton=new Button(100,25,"BUY");
        buyButton.setPressedListener(new OnPressedListener() {
            @Override
            public void pressed() {
                if(!Account.inTrade()) {
                    Account.addBuy(1, CryptoDataFetcher.getData().get(CryptoDataFetcher.getData().size() - 1));
                    Account.updateEntrypoint();
                    System.out.println(Account.positionSize);
                }
            }
        });
        //Sell button
        Button sellButton=new Button(100,25,"SELL");
        sellButton.setPressedListener(new OnPressedListener() {
            @Override
            public void pressed() {
                Account.addSell(1,CryptoDataFetcher.getData().get(CryptoDataFetcher.getData().size()-1));
                Account.updateEntrypoint();
                System.out.println(Account.positionSize);
            }
        });

        middleLeft.addUIElement(buyButton);
        middleLeft.addUIElement(sellButton);

        middleMiddle.addUIElement(new TimeSeriesDisplayWidget(100,100));

        //Accelerate speed of the graph updating
        Button fastButton=new Button(100,25,"FAST");
        fastButton.setPressedListener(new OnPressedListener() {
            @Override
            public void pressed() {
                if(speedIndex >=0&& speedIndex <5){
                    speedIndex++;
                }
                System.out.println(speedArray[speedIndex]);

            }
        });

        middleRight.addUIElement(fastButton);

        //Move graph to the right
        Button rightButton=new Button(100,25,"RIGHT");
        rightButton.setPressedListener(new OnPressedListener() {
            @Override
            public void pressed() {

                if (TimeSeriesDisplayWidget.end < TimeSeriesDisplayWidget.data.size() - 1&&TimeSeriesDisplayWidget.zoomPercent >0.05){
                    TimeSeriesDisplayWidget.moveLeftAndRight-=3;
                }
                System.out.println(TimeSeriesDisplayWidget.start);
                System.out.println(TimeSeriesDisplayWidget.end);
            }
        });
        middleRight.addUIElement(rightButton);

        //Zoom in graph
        Button zoomPlusButton=new Button(100,25,"+");
        zoomPlusButton.setPressedListener(new OnPressedListener() {
            @Override
            public void pressed() {
                if (TimeSeriesDisplayWidget.zoomPercent >0.05){
                    TimeSeriesDisplayWidget.zoomPercent -=0.05;
                    TimeSeriesDisplayWidget.zoomPercentTracker -=0.05;
                }
                else {}
                System.out.println(TimeSeriesDisplayWidget.start);
            }
        });

        //Zoom out graph
        Button zoomMinusButton=new Button(100,25,"-");
        zoomMinusButton.setPressedListener(new OnPressedListener() {
            @Override
            public void pressed() {
                if(TimeSeriesDisplayWidget.zoomInBoundChecker <0){
                    TimeSeriesDisplayWidget.start=0;
                    TimeSeriesDisplayWidget.end=TimeSeriesDisplayWidget.data.size();
                }
                    if (TimeSeriesDisplayWidget.zoomPercent <1&&TimeSeriesDisplayWidget.start!=0){
                        TimeSeriesDisplayWidget.zoomPercent +=0.05;
                        TimeSeriesDisplayWidget.zoomPercentTracker +=0.05;
                    }

                else {}
                System.out.println(TimeSeriesDisplayWidget.start);
                System.out.println(TimeSeriesDisplayWidget.end);

            }

        });

        middleRight.addUIElement(zoomPlusButton);
        middleRight.addUIElement(zoomMinusButton);

        middleSection.addUIElement(middleLeft);
        middleSection.addUIElement(middleMiddle);
        middleSection.addUIElement(middleRight);



        layout.addUIElement(topSection);
        layout.addUIElement(middleSection);

    }

    public Layout getLayouts(){
        return layout;
    }

}
