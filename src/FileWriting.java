import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by brionsilva on 19/12/2016.
 */
public class FileWriting {

    //Method to write to a file
    public void fileWriting(int wins, int loses, int freeSpins, int totalSpins, int avgCredits) {
        try {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());

            File dir = new File("stats");//creating a folder called stats to store the text files
            dir.mkdirs();

            String fileName = timeStamp + ".txt";

            File file = new File(dir,fileName);

            //Creates the text file if doesn't exist
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            BufferedWriter bw = new BufferedWriter(fw);


            bw.write("\t\t\tWESTMINSTER SLOT MACHINE STATISTICS\n\n");
            bw.newLine();
            bw.write("\t\t\tDate and Time - " + timeStamp);
            bw.newLine();
            bw.write("\n\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -\n");
            bw.newLine();
            bw.write("Total Number of Wins : " + wins+"\n\n");
            bw.newLine();
            bw.write("Total Number of loses : " + loses+"\n\n");
            bw.newLine();
            bw.write("Total Number of free spins : " + freeSpins+"\n\n");
            bw.newLine();
            bw.write("Total Number of total spins : " + totalSpins+"\n\n");
            bw.newLine();
            bw.write("Average Credits : " + avgCredits+"\n\n");
            bw.newLine();

            bw.close();

            System.out.println("\nAll the data was successfully saved to a text file.\n");

        } catch (IOException e) {
            System.out.println("Sorry! The program could not locate the text file");

        }
    }
}
