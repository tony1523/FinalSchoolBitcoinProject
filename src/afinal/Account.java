package afinal;
import java.text.DecimalFormat;

public class Account {

    private static double accountBalance;
    public static double positionSize=HistoryManager.getPositionSize();
    private static double entryPosition;
    private static double startingBalance;
    public static TextView accountBalanceTextView;
    public static TextView accountEntryPoint;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public static void setBalance(double newBalance){
        startingBalance = newBalance;
    }

    public static boolean inTrade(){
        return positionSize!=0;
    }
    public static double getEntryPosition(){
        return entryPosition;
    }
    public static double getBalance(){
        return accountBalance;
    }

    public static void addBuy(double bitcoinAmount,double pos){
        entryPosition=pos;
        if (positionSize==0) {
            positionSize += bitcoinAmount;
        }

    }
    public static void addSell(double bitcoinAmount,double pos){
        entryPosition=pos;
        if(positionSize==1) {
            positionSize -= bitcoinAmount;
        }
    }

    public static void updateBalance(Double btcDatapoint){
        if(positionSize!=0&&HistoryManager.getEntryPosition()!=0){
            accountBalance = startingBalance + (btcDatapoint-HistoryManager.getEntryPosition());
            accountBalanceTextView.setText("Balance:"+ df2.format(accountBalance));
        }else if(positionSize!=0&&HistoryManager.getEntryPosition()==0){
            accountBalance = startingBalance + (btcDatapoint-entryPosition);
            accountBalanceTextView.setText("Balance:"+ df2.format(accountBalance));
        }
    }
    public static void updateEntrypoint(){
        if(!inTrade())
        {
            accountEntryPoint.setText("Not in trade");
        }else {
            accountEntryPoint.setText("Entry Price:"+ df2.format(getEntryPosition()));
        }


    }
}
