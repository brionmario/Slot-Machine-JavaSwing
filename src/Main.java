
/**
 *
 * @author Brion Mario, Apparecium Labs
 */


import javax.swing.*;
import java.awt.*;

public class Main {
	
	public static void main(String[]args){

		SlotMachineGUI object= new SlotMachineGUI();
		object.setTitle("SLOT MACHINE");
		object.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		object.setMinimumSize(new Dimension(800,700));
		object.setSize(1000,800);
		object.setLocationRelativeTo(null);

		object.pack();
		object.setVisible(true);

	}
       
	

}
