package afinal;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;


public class TimeSeriesDisplayWidget extends Widget{

    public final int X_AXIS_OFFSET = 75;
    public final int Y_AXIS_OFFSET = 75;
    private final int MAX_VAL_PADDING = 0;
    private final int MIN_VAL_PADDING = 0;
    private final int NUMBER_OF_X_TICKS = 10;
    private final int NUMBER_OF_Y_TICKS = 10;

    //Contains all data points
    //Shows the complete graph
    static ArrayList <Double>data = new ArrayList<>();

    //Shows the graph with specific conditions(zoom, right,left)
    static ArrayList <Double> dataSpecificView = new ArrayList<>();

    //contains btc data points from before
    static ArrayList <Double>historicData = new ArrayList<>();
    static ArrayList <Long>historicTime = new ArrayList<>();


    //track where to start and end showing graph
    public static int start=0;
    public static int end=0;

    //Variable used to move left and right
    public static int moveLeftAndRight=0;


    public static double zoomPercent =HistoryManager.getZoom();

    //Variables used to make sure to zoom within the range
    public static double zoomPercentTracker =HistoryManager.getZoom()+0.05;
    public static int zoomInBoundChecker =0;

    public TimeSeriesDisplayWidget(double preferredSizeX, double preferredSizeY) {
        super(preferredSizeX, preferredSizeY);
        historicData.addAll(HistoryManager.getBitcoinData());
        historicTime.addAll(HistoryManager.getBitcoinTime());

    }

    private void drawAxis(Graphics g) {
        ArrayList<Long> milliTimes = CryptoDataFetcher.getTimes();
        int nXTicksToDraw = milliTimes.size() >= NUMBER_OF_X_TICKS ?
                NUMBER_OF_X_TICKS : milliTimes.size();

        int xTickDistance = (width - Y_AXIS_OFFSET) / nXTicksToDraw;
        int yTickDistance = (height - X_AXIS_OFFSET) / NUMBER_OF_Y_TICKS;

        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(0 + xPos, 0 + yPos, width - Y_AXIS_OFFSET, height - X_AXIS_OFFSET);

        ArrayList<String> datesToDraw = getXAxisDates(milliTimes, nXTicksToDraw);
        for (int i = 0; i < nXTicksToDraw; i++) {
            g.setColor(Color.LIGHT_GRAY);
            g.drawLine(xTickDistance * (i) + xPos, height - X_AXIS_OFFSET + yPos,
                    xTickDistance * (i) + xPos, height - X_AXIS_OFFSET + 5 + yPos);
            drawRotate((Graphics2D) g, xTickDistance * i+xPos, height - X_AXIS_OFFSET + 10 + yPos,
                    45, datesToDraw.get(i));
            g.setColor(Color.DARK_GRAY);
            g.drawLine(xTickDistance * (i) + xPos, height - X_AXIS_OFFSET + yPos,
                    xTickDistance * (i)+ xPos, 0 + yPos);
        }

        ArrayList<String> pricesToDraw = getYAxisValues(CryptoDataFetcher.getData());
        for(int i=1;i<=NUMBER_OF_Y_TICKS;i++) {
            g.setColor(Color.LIGHT_GRAY);
            g.drawLine(width - Y_AXIS_OFFSET + xPos, yTickDistance * i + yPos,
                    width - Y_AXIS_OFFSET + 5 + xPos, yTickDistance * i + yPos);
            g.drawString(pricesToDraw.get(NUMBER_OF_Y_TICKS - i),
                    width-Y_AXIS_OFFSET + 10 + xPos,yTickDistance * i + yPos);
            g.setColor(Color.DARK_GRAY);
            g.drawLine(width - Y_AXIS_OFFSET + xPos, yTickDistance * i + yPos,
                    0 + xPos, yTickDistance * i + yPos);
        }

    }

    private ArrayList<String> getYAxisValues(ArrayList<Double> dataPoints){
        ArrayList<String> values = new ArrayList<>();
        if(dataPoints.size() > 1){
            Double max = Collections.max(dataPoints);
            Double min = Collections.min(dataPoints);
            Double increment = (max - min)/NUMBER_OF_Y_TICKS;
            for(Double i = min;i<max;i+=increment)
                values.add(((Double)(Math.round(i*100.0)/100.0)).toString());
        }
        else{
            for(int i=0;i<NUMBER_OF_Y_TICKS;i++)
                values.add(dataPoints.get(0).toString());
        }
        return values;
    }

    private ArrayList<String> getXAxisDates(ArrayList<Long> milliTimes, int nXTicksToDraw){
        ArrayList<String> dates = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, HH:mm");
        if(milliTimes.size() <= nXTicksToDraw) {
            for(Long l : milliTimes)
                dates.add(dateFormat.format(l));
        }
        else{
            long startTime = milliTimes.get(0);
            long endTime = milliTimes.get(milliTimes.size()-1);
            long increment = (endTime - startTime) / NUMBER_OF_X_TICKS;
            for(long i=startTime;i<endTime;i+=increment)
                dates.add(dateFormat.format(i));
        }
        return dates;
    }

    private void drawRotate(Graphics2D g2d, double x, double y, int angle, String text)
    {
        g2d.translate((float)x,(float)y);
        g2d.rotate(Math.toRadians(angle));
        g2d.drawString(text,0,0);
        g2d.rotate(-Math.toRadians(angle));
        g2d.translate(-(float)x,-(float)y);
    }

    private void drawDirection(Graphics g){
        ArrayList<Double> data = CryptoDataFetcher.getData();
        g.setColor(Color.WHITE);
        if(data.size() > 1){
            if(data.get(data.size() - 1) > data.get(data.size() - 2)){
                g.drawString("direction: ",0 + xPos,10 + yPos);
                g.setColor(Color.GREEN);
                g.drawString("UP",50 + xPos,10 + yPos);
            }else{
                g.drawString("direction: ",0 + xPos,10 + yPos);
                g.setColor(Color.RED);
                g.drawString("DOWN",50 + xPos,10 + yPos);
            }
        }

    }

    private void drawCurrentPrice(Graphics g){
        g.setColor(Color.LIGHT_GRAY);
        Double currentPrice = CryptoDataFetcher.getData().get(CryptoDataFetcher.getData().size()-1);
        g.drawString("current: " + currentPrice,0 + xPos,40 + yPos);
    }

    @Override
    public void drawSelf(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.fillRect(xPos + horizontalPadding, yPos + verticalPadding,
                width - horizontalPadding * 2, height - verticalPadding * 2);

        g2.setColor(Color.BLACK);
        g2.fillRect(0 + xPos,0 + yPos, width, height);

        data=CryptoDataFetcher.getData();

        end=data.size()-1-moveLeftAndRight;
        start= (int) (data.size()-(zoomPercent *data.size()))-moveLeftAndRight;

        zoomInBoundChecker =(int) (data.size()-(zoomPercentTracker *data.size()))-moveLeftAndRight;;


        if (data.size()>15&&end<data.size()){
            dataSpecificView.clear();
            dataSpecificView.addAll(data.subList(start,end));
        }
        if(data.size() > 0){
            drawAxis(g2);
            drawDirection(g2);
            drawCurrentPrice(g2);
        }
        //Show data from main Array
        if(data.size() > 1&&data.size()<16){
            double max = Collections.max(data) + MAX_VAL_PADDING;
            double min = Collections.min(data) - MIN_VAL_PADDING;

            double xStep = ((double) (width - Y_AXIS_OFFSET)) / ((double)data.size() - 1);
            double yStep = ((double) height - X_AXIS_OFFSET) / (max - min);

            for(int i=0;i<data.size()-1;i++) {

                g2.setColor(Color.LIGHT_GRAY);
                g2.drawLine((int) (xStep*i)+ xPos, (int) (yStep*(max - data.get(i))) + yPos,
                        (int)(xStep*(i+1))+ xPos, (int)(yStep*(max - data.get(i+1))) + yPos);
            }

            //Show data from temporary Array
        }if(data.size()>=16) {
            double max = Collections.max(dataSpecificView) + MAX_VAL_PADDING;
            double min = Collections.min(dataSpecificView) - MIN_VAL_PADDING;

            double xStep = ((double) (width - Y_AXIS_OFFSET)) / ((double) dataSpecificView.size() - 1);
            double yStep = ((double) height - X_AXIS_OFFSET) / (max - min);

            for(int i = 0; i< dataSpecificView.size()-1; i++) {

                g2.setColor(Color.LIGHT_GRAY);
                g2.drawLine((int) (xStep*i)+ xPos, (int) (yStep*(max - dataSpecificView.get(i))) + yPos,
                        (int)(xStep*(i+1))+ xPos, (int)(yStep*(max - dataSpecificView.get(i+1))) + yPos);
            }
        }

    }

}


