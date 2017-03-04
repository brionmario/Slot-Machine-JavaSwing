import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by brionsilva on 19/12/2016.
 */
public class PieChart extends JFrame {

    private static boolean isSaveBtnClicked;

    //I decided to use a pie chart to show off the statistics as it is more visual and clear to look at.
    // The Jfreechart library was an amazing option.
    public PieChart(String title, String chartTitle, int wins, int loses, int freeSpins, int totalSpins, int avgCredits) {
        super(title);

        JPanel contentPane = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        contentPane.setBorder(BorderFactory.createMatteBorder(15, 10, 10, 10, Color.WHITE));
        JButton btnSave = new JButton("Save to file");

        JLabel lblBannerTop = new JLabel();
        lblBannerTop.setFont(new Font("Helvetica", Font.BOLD, 18));
        lblBannerTop.setForeground(Color.DARK_GRAY);
        lblBannerTop.setText("FREE SPINS : " + freeSpins + "    " + "TOTAL SPINS : " + totalSpins+ "    " +
                "WINS : " + wins + "    " + "LOSES : " + loses);

        JLabel lblBannerBottom = new JLabel();
        lblBannerBottom.setFont(new Font("Helvetica", Font.BOLD, 18));
        lblBannerBottom.setForeground(Color.RED);
        lblBannerBottom.setText("AVERAGE : " + avgCredits);

        JLabel lblBannerBottom2= new JLabel();
        lblBannerBottom2.setFont(new Font("Helvetica", Font.BOLD, 12));
        lblBannerBottom2.setForeground(Color.DARK_GRAY);
        if(avgCredits>=0){
            lblBannerBottom2.setText("Your average is positive. You've been winning most of the time.");
        }else {
            lblBannerBottom2.setText("Your average is negative. You've been losing most of the time.");
        }


        PieDataset pieDataset = createDataset(wins, loses,freeSpins);
        JFreeChart chart = createChart(pieDataset, chartTitle);
        //to change colors
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Wins", Color.RED);
        plot.setSectionPaint("Losses", Color.BLACK);
        plot.setSectionPaint("Free Spins", Color.DARK_GRAY);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 400));

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weighty = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.CENTER;
        contentPane.add(lblBannerTop, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.CENTER;
        contentPane.add(chartPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.CENTER;
        contentPane.add(lblBannerBottom, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        contentPane.add(lblBannerBottom2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        contentPane.add(btnSave, gbc);

        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);

        //action for the button
        btnSave.addActionListener(new btnSaveHandler(wins,loses,freeSpins,totalSpins,avgCredits,lblBannerBottom2));
    }


    public PieDataset createDataset(int wins, int loses,int freespins) {
        DefaultPieDataset chartData = new DefaultPieDataset();
        chartData.setValue("Wins", wins);
        chartData.setValue("Losses", loses);
        chartData.setValue("Free Spins", freespins);
        return chartData;
    }

    public JFreeChart createChart(PieDataset dataset, String title) {
        JFreeChart jchart = ChartFactory.createPieChart(title, dataset, true, true, true);
        return jchart;
    }

    class btnSaveHandler implements ActionListener {
        int wins,loses,freeSpins,totalSpins,avgCredits;
        JLabel lblBannerBottom2;


        public btnSaveHandler(int wins, int loses, int freeSpins, int totalSpins, int avgCredits,JLabel lblBannerBottom2){
            this.wins=wins;
            this.loses=loses;
            this.freeSpins=freeSpins;
            this.totalSpins=totalSpins;
            this.avgCredits=avgCredits;
            this.lblBannerBottom2 = lblBannerBottom2;
        }
        public void actionPerformed(ActionEvent event) {
            if(isSaveBtnClicked==false) {
                FileWriting fileWriting = new FileWriting();
                fileWriting.fileWriting(wins, loses, freeSpins, totalSpins, avgCredits);
                lblBannerBottom2.setText("The data has been successfully saved to text file.");
                isSaveBtnClicked=true;
            }else if(isSaveBtnClicked==true){
                lblBannerBottom2.setText("The data has already been saved once.");
            }
        }
    }

}
