package afinal;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class HistoryManager {

    //Method to save state on close in csv
    public static void saveState() throws IOException {
        try (var out = new BufferedWriter(new FileWriter("HistoryManager.csv", false))){
            out.write("Speed,Zoom,Balance,EntryPosition,postion value");
            out.write('\n');
            out.write(String.valueOf(CenterPaneLayout.speedIndex));
            out.write(',');
            out.write(String.valueOf(TimeSeriesDisplayWidget.zoomPercent));
            out.write(',');
            out.write(String.valueOf(Account.getBalance()));
            out.write(',');
            out.write(String.valueOf(Account.getEntryPosition()));
            out.write(',');
            out.write(String.valueOf(Account.positionSize));

        }
    }
    //Methods to get get data from CSV file
    public static ArrayList<Double> getBitcoinData(){
        ArrayList<Double> btcData = DataLoader.getData(Paths.get(
                "BtcData.csv"));
        return btcData;
    }
    public static ArrayList<Long> getBitcoinTime(){
        ArrayList<Long> timeData = DataLoader.getTime(Paths.get(
                "BtcData.csv"));
        return timeData;
    }
    public static int getSpeed(){
        int speedData = DataLoader.getSpeed(Paths.get(
                "HistoryManager.csv"));
        return speedData;
    }
    public static double getZoom(){
        double zoomData = DataLoader.getZoom(Paths.get(
                "HistoryManager.csv"));
        return zoomData;
    }
    public static double getEntryPosition(){
        double entryData = DataLoader.getEntryPosition(Paths.get(
                "HistoryManager.csv"));
        return entryData;
    }
    public static double getPositionSize(){
        double posData = DataLoader.getPositionSize(Paths.get(
                "HistoryManager.csv"));
        return posData;
    }
    public static double getAccountBalance(){
        double balData = DataLoader.getBal(Paths.get(
                "HistoryManager.csv"));
        return balData;
    }

}
