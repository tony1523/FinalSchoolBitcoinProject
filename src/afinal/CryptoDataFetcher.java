package afinal;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CryptoDataFetcher {

    private static ArrayList<Double> data;
    private static ArrayList<Long> times;
    public static MasterPanel masterPanel;

    static int count=0;

    public CryptoDataFetcher(){

    }
    public static void saveDataPoints() throws IOException {
        try (var out = new BufferedWriter(new FileWriter("BtcData.csv", true))){


                   out.write(String.valueOf(data.get(times.size() - 1)));
                   out.write(',');
                   out.write(String.valueOf(times.get(times.size() - 1)));
                   out.write('\n');

        }
    }

    public static void init(MasterPanel panel){
        masterPanel=panel;
        data = new ArrayList<>();
        times = new ArrayList<>();
        startFetching();
    }



    private static void startFetching(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Document doc = Jsoup.connect("https://robinhood.com/crypto/BTC").get();
                        String str = doc.toString();
                        int index = str.indexOf(" <span class=\"_2oDUGMkaRlWbu-mRRWjtK5\">");
                        String subStr = str.substring(index, index + 100);
                        int dollarIndex = subStr.indexOf("$");
                        String parsed = subStr.substring(dollarIndex+1,dollarIndex+10);
                        Double btcDataPoint = Double.parseDouble(parsed.replace(",",""));
                        addDataPoint(btcDataPoint);
                        Account.updateBalance(btcDataPoint);

                        Thread.sleep(CenterPaneLayout.speedArray[CenterPaneLayout.speedIndex]);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
    //add old points
    static void addAllPoints(){
            data.addAll(TimeSeriesDisplayWidget.historicData);
            times.addAll(TimeSeriesDisplayWidget.historicTime);
    }

    private static void addDataPoint(Double newDataPoint) throws IOException {
        //To add all points just once

        while (count<1){

            addAllPoints();
            count++;
        }
        saveDataPoints();
        if(data.size() == 0){
            data.add(newDataPoint);
            times.add(System.currentTimeMillis());
            masterPanel.repaint();
        }
        else if(!newDataPoint.equals(data.get(data.size()-1))) {
            data.add(newDataPoint);
            times.add(System.currentTimeMillis());
            masterPanel.repaint();
        }

    }

    public static ArrayList<Double> getData(){
        return data;
    }

    public static ArrayList<Long> getTimes(){
        return times;
    }


}
