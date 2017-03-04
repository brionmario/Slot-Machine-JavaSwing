
/**
 * @author Brion Mario, Apparecium Labs
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;

public class SlotMachineGUI extends JFrame{
    //creating three reel instances
    Reel r1 = new Reel();
    Reel r2 = new Reel();
    Reel r3 = new Reel();

    //creating an arraylist to store the spinned symbols
    ArrayList<Symbol> spinned;

    private JButton btnSpin, btnBetOne, btnBetMax, btnReset, btnAddCoin, btnStats;
    public JLabel lblReel1, lblReel2, lblReel3, lblcreditArea, lblBetArea, lblMsg1;
    private ImageIcon iconReel1, iconReel2, iconReel3;
    private ImageIcon iconPicDisplay, iconLogo; //ui jackpot,logo
    public JLabel lblPicDisplay, lblLogo;//ui jackpot,logo
    Container containerPane;
    private int r1SymbolValue, r2SymbolValue, r3SymbolValue;
    JSeparator separator;

    //maximum bet
    final static int MAX_BET = 3;
    //initial credit count
    final static int INITIAL_CREDITS = 10;
    //inital credit count
    int credit = INITIAL_CREDITS; //credit = 10
    //initial bet
    int bet = 0;

    //boolean variable for the label click
    private static boolean isLabelClicked;

    //variables to store stats
    int wins;
    int loses;
    int freeSpins;
    int totalSpins;
    int creditsWon;
    int creditsBet;
    int avgCredits;


    //Credits string
    String creditString = "Credits : " + "£";
    String betString = "Current Bet : " + "£";

    GridBagConstraints gridBagConstraints = new GridBagConstraints();

    public SlotMachineGUI(){

        containerPane = getContentPane();
        containerPane.setLayout(new GridBagLayout());
        containerPane.setBackground(Color.DARK_GRAY);

        gridBagConstraints.insets = new Insets(20, 5, 5, 5);
        gridBagConstraints.weighty=2;
        gridBagConstraints.weighty=2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;

        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 1;

        addLabel();
        addButton();

    }

    private void addButton() {


        btnSpin = new JButton("Spin");
        btnSpin.addActionListener(new SpinHandler());
        btnBetOne = new JButton("Bet One");
        btnBetOne.addActionListener(new BetOne());
        btnBetMax = new JButton("Bet Max");
        btnBetMax.addActionListener(new BetMax());
        btnReset = new JButton("Reset");
        btnReset.addActionListener(new Reset());
        btnAddCoin = new JButton("Add Coin");
        btnAddCoin.addActionListener(new CreditHandler());
        btnStats = new JButton("Statistic");
        btnStats.addActionListener(new Statistic());


        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        containerPane.add(btnBetOne, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        containerPane.add(btnSpin, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        containerPane.add(btnBetMax, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        containerPane.add(btnReset, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        containerPane.add(btnAddCoin, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        containerPane.add(btnStats, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        containerPane.add(lblcreditArea, gridBagConstraints);


        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        containerPane.add(lblBetArea, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth=9;
        JSeparator sep1 = new JSeparator(SwingConstants.HORIZONTAL);
        containerPane.add(sep1,gridBagConstraints);


        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = GridBagConstraints.CENTER;
        containerPane.add(lblMsg1, gridBagConstraints);

        //Welcome message
        lblMsg1.setText(" ");
    }


    private void addLabel() {
        iconReel1 = new ImageIcon(r1.symbols.get(0).getImage());
        iconReel2 = new ImageIcon(r2.symbols.get(0).getImage());
        iconReel3 = new ImageIcon(r3.symbols.get(0).getImage());


        lblReel1 = new JLabel(iconReel1);
        //lblReel1.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
        lblReel2 = new JLabel(iconReel2);
        //lblReel2.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
        lblReel3 = new JLabel(iconReel3);
        //lblReel3.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));

        iconPicDisplay = new ImageIcon("images/starsopening.png");
        lblPicDisplay = new JLabel(iconPicDisplay);

        lblcreditArea = new JLabel(creditString + Integer.toString(INITIAL_CREDITS));
        //lblcreditArea.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.LIGHT_GRAY));
        lblcreditArea.setHorizontalAlignment(SwingConstants.CENTER);
        lblcreditArea.setForeground(Color.white);
        lblcreditArea.setSize(300, 200);
        lblcreditArea.setFont(new Font("Helvetica", Font.BOLD, 22));


        lblBetArea = new JLabel(betString + Integer.toString(bet));
        //lblBetArea.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.LIGHT_GRAY));
        lblBetArea.setHorizontalAlignment(SwingConstants.CENTER);
        lblBetArea.setForeground(Color.white);
        lblBetArea.setSize(300, 200);
        lblBetArea.setFont(new Font("Helvetica", Font.BOLD, 22));

        lblMsg1 = new JLabel();
        lblMsg1.setForeground(Color.RED);
        lblMsg1.setFont(new Font("Helvetica", Font.BOLD, 16));

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        containerPane.add(lblPicDisplay, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        containerPane.add(lblReel1, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        containerPane.add(lblReel2, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        containerPane.add(lblReel3, gridBagConstraints);

    }

    class SpinHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {

            if(bet>0){
                if (credit>=0) {
                    isLabelClicked=false;
                    //play spin sound
                    SlotMachineGUI.playSound("spin");

                    if(credit==0 && bet==1){

                        lblPicDisplay.setIcon(new ImageIcon("images/finalspin.png"));
                    }

                    //storing the last three symbols
                    spinned = new ArrayList<>();

                    r1.Spin(r1.symbols);
                    r2.Spin(r2.symbols);
                    r3.Spin(r3.symbols);

                    //creates three threads for each reel
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            start(r1,20,iconReel1,lblReel1);
                        }
                    });

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            start(r2,15,iconReel2,lblReel2);
                        }
                    });

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            start(r3,10,iconReel3,lblReel3);
                        }
                    });

                    //increase the spin count bty one (for stats)
                    totalSpins++;

                } else {
                    lblPicDisplay.setIcon(new ImageIcon("images/insertcoin.png"));
                    lblMsg1.setText("You Have No more Credits to play");
                }
            }else {
                lblMsg1.setText("Please place a bet");
            }
        }
    }

    class CreditHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            credit++; //coin = 1 credit
            lblcreditArea.setText(creditString + Integer.toString(credit));
        }

    }

    class BetMax implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (bet > 0) {
                lblMsg1.setText("You can't bet more than "+ MAX_BET+" credits");
            } else {
                if (credit >= MAX_BET) {

                    credit -= MAX_BET;
                    bet += MAX_BET;
                    lblcreditArea.setText(creditString + Integer.toString(credit));
                    lblBetArea.setText(betString + Integer.toString(bet));

                    //increase the bet credits by MAX_BET (3 in this case) (for stats)
                    creditsBet += MAX_BET;
                } else {
                    lblPicDisplay.setIcon(new ImageIcon("images/insertcoin.png"));
                    lblMsg1.setText("You don't have enough credits to place a maximum bet");
                }

            }
        }
    }

    class BetOne implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (bet >= MAX_BET) {
                lblMsg1.setText("You can't bet more than " + MAX_BET + " credits");
            } else {
                if (credit >= 1) {
                    credit--;
                    bet++;
                    lblcreditArea.setText(creditString + Integer.toString(credit));
                    lblBetArea.setText(betString + Integer.toString(bet));

                    //increase the bet credits by one (for stats)
                    creditsBet++;
                }else {
                    lblPicDisplay.setIcon(new ImageIcon("images/insertcoin.png"));
                    lblMsg1.setText("Sorry! You don't have any credits left to place a bet.");
                }
            }
        }
    }

    class Statistic implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            //only works when the user has played the game atleast once
            if(freeSpins >0 || totalSpins >0) {
                avgCredits = (int) averageCalculator();
                PieChart pieChart = new PieChart("Statistics", "",wins,loses,freeSpins, totalSpins,avgCredits);
                pieChart.setMinimumSize(new Dimension(800,600));
                pieChart.pack();
                pieChart.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                pieChart.setVisible(true);
            }else {
                lblMsg1.setText("Currently there's no statistics to show. Play the game and try again!");
            }
        }
    }

    class Reset implements ActionListener {
        public void actionPerformed(ActionEvent event) {

            credit = credit + bet; //restoring the credits

            //reduce the bet credits(for stats)
            creditsBet = creditsBet - bet;

            bet = 0; //initialising back to zero
            lblcreditArea.setText(creditString + Integer.toString(credit));
            lblBetArea.setText(betString + Integer.toString(bet));

        }
    }

    public void setReelValue(Symbol symbol1,Symbol symbol2,Symbol symbol3) {

        r1SymbolValue = (symbol1.getValue());
        r2SymbolValue = (symbol2.getValue());
        r3SymbolValue = (symbol3.getValue());

    }


    public void matchCheck(Symbol symbol1, Symbol symbol2, Symbol symbol3) {

        int won;

        //If all three similar
        if (Symbol.matchCheck(symbol1, symbol2, symbol3) == 3) {
            SlotMachineGUI.playSound("win");
            lblPicDisplay.setIcon(new ImageIcon("images/jackpot.png"));

            lblMsg1.setText("Congrats! You matched all three reels. You Won!");
            won = r1SymbolValue * bet;
            credit = credit + won;
            lblcreditArea.setText(creditString + Integer.toString(credit));
            bet = 0;

            //increase the credits won (for stats)
            creditsWon = creditsWon + won;
            //increasing the winning count by one (for stats)
            wins++;

            //if two are similar
        } else if (Symbol.matchCheck(symbol1, symbol2, symbol3) == 2) {
            lblPicDisplay.setIcon(new ImageIcon("images/freespin.png"));
            lblMsg1.setText("You matched two reels. You get a free spin ");
            lblcreditArea.setText(creditString + Integer.toString(credit));

            //increase the freespin count(for stats)
            freeSpins++;

            //nothing's similar
        } else if (Symbol.matchCheck(symbol1, symbol2, symbol3) == 0) {
            lblPicDisplay.setIcon(new ImageIcon("images/spinagain.png"));
            SlotMachineGUI.playSound("lose");
            lblMsg1.setText("Sorry! None of the symbols matched. You lost!");
            bet = 0;

            //increasing the lost count by one (for stats)
            loses++;
        } else {
            lblMsg1.setText("Something went wrong");
            bet = 0;
        }

    }

    public double averageCalculator(){
        double num = creditsWon - creditsBet;
        double den = totalSpins-freeSpins;
        double average = num/den;
        return average;
    }


    //threads for the reels
    private void start(Reel r,int sleepTime,ImageIcon icon,JLabel label) {

        SwingWorker<Boolean, Symbol> worker = new SwingWorker<Boolean, Symbol>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                int n = 0;
                if(isLabelClicked ==false){
                    do{

                        Random Dice = new Random();
                        n = Dice.nextInt(5);

                        publish(r.symbols.get(n));

                        try{
                            Thread.sleep(sleepTime);
                        }catch(Exception e){
                            System.out.println("Oops!");

                        }

                        event e = new event();
                        label.addMouseListener(e);



                    }while(isLabelClicked != true);

                }
                //if the reel is clicked
                if(isLabelClicked=true){
                    System.out.println("The value of the Symbol " + r.symbols.get(n).getValue()+" - "+ "The index of the Symbol "+ n);
                    System.out.println(r.symbols.toString());
                    //assigning the symbols to an arraylist
                    spinned.add(r.symbols.get(n));
                    //setting the spinnned symbol values
                    setReelValue(spinned.get(0),spinned.get(1),spinned.get(2));
                    //checking for any similarities
                    matchCheck(spinned.get(0), spinned.get(1), spinned.get(2));
                    lblBetArea.setText(betString + Integer.toString(bet));
                }
                return true;
            }
            // Can safely update the GUI from this method.
            protected void done() {

                boolean status;
                try {
                    // Retrieve the return value of doInBackground.
                    status = get();
                    System.out.println("\nNewly assigned "+ spinned.toString());
                    isLabelClicked =false;

                    System.out.println("\n-------------------STATS-------------");
                    System.out.println("Wins - " + wins);
                    System.out.println("Loses - " + loses);
                    System.out.println("FreeSpins - " + freeSpins);
                    System.out.println("TotalSpins - " + totalSpins);
                    System.out.println("Won Credits - " + creditsWon);
                    System.out.println("Bet Credits - " + creditsBet);
                    System.out.println("Average Credits - " + avgCredits);

                } catch (InterruptedException e) {
                    // This is thrown if the thread's interrupted.
                } catch (ExecutionException e) {
                    // This is thrown if we throw an exception
                    // from doInBackground.
                }
            }
            @Override
            // Can safely update the GUI from this method.
            protected void process(java.util.List<Symbol> chunks) {
                // Here we receive the values that we publish()
                Symbol mostRecentValue = chunks.get(chunks.size()-1);

                try{
                    //setting the image in the reel
                    label.setIcon(new ImageIcon(mostRecentValue.getImage()));


                }catch(Exception e){}
                //System.out.println();
            }


        };

        worker.execute();


    }
    //sound thread
    private static synchronized void playSound(final String sound) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResource("sounds/"+sound+".wav"));
                    clip.open(inputStream);
                    clip.start();
                } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public class event implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            isLabelClicked = true;
        }

        public void mousePressed(MouseEvent e) {
            isLabelClicked = true;
        }

        public void mouseReleased(MouseEvent e) { }

        public void mouseEntered(MouseEvent e) { }

        public void mouseExited(MouseEvent e) { }
    }


}
