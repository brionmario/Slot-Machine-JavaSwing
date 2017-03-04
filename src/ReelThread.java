import java.util.Random;

/**
 * Created by brionsilva on 18/12/2016.
 */
public class ReelThread implements Runnable{
    String reelName;
    int sleepTime;
    Random random = new Random();

    public ReelThread(String reelName){
        this.reelName=reelName;
        sleepTime = random.nextInt();
    }
    @Override
    public void run() {

    }
}
