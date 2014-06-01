import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class Mem extends JFrame
{

    LinkedList<String> linkedList = new LinkedList<String>();
    private JLabel label1;
    private JPanel panel1, panel2;
    private FlashText flashTextPanel;
    private JTextField field1;
    private JRadioButton inOrderRadio, inRandomRadio;

    private javax.swing.Timer timer;

    public static void main(String[] args)
    {
	Mem exe = new Mem();

	exe.setSize(800,500);
	exe.setVisible(true);
	exe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public Mem()
    {
	super("BLD Memory Trainer");
	this.setLayout(new GridLayout(3,1));
	//panel1 for inputting the string of letters
	panel1 = new JPanel();
	panel1.setLayout(new GridLayout(1,2));
	label1 = new JLabel("List of letters to flash");
	panel1.add(label1);
	field1 = new JTextField();
	panel1.add(field1);
	this.add(panel1);


	//panel2 for radio buttons
	panel2 = new JPanel();
	panel2.setLayout(new FlowLayout());
	inOrderRadio = new JRadioButton("In Order",true);
	inRandomRadio = new JRadioButton("Randomized",false);
	panel2.add(inOrderRadio);
	panel2.add(inRandomRadio);
	this.add(panel2);

	RadioButtonListener radioButtonListener = new RadioButtonListener();
	inOrderRadio.addActionListener(radioButtonListener);
	inRandomRadio.addActionListener(radioButtonListener);

	//flashTextPanel for flashing the letters
        flashTextPanel = new FlashText();
	this.getContentPane().add(flashTextPanel);

	FlashListener flashListener = new FlashListener();
	field1.addActionListener(flashListener);
	timer = new javax.swing.Timer(1000,flashListener);
	timer.setInitialDelay(0);
    }

    class RadioButtonListener implements ActionListener
    {
	public void actionPerformed (ActionEvent event)
	{
	    //to ensure only one JRadioButton is on at a time
	    if(event.getSource() == inOrderRadio)
	    {
		inRandomRadio.setSelected(false);
	    }
	    if(event.getSource() == inRandomRadio)
	    {
		inOrderRadio.setSelected(false);
	    }
	     
	}
    }
    class FlashListener implements ActionListener
    {
	public void actionPerformed (ActionEvent event)
	{
	    if (event.getSource() == field1)
	    {

		for(int x=0;x<field1.getText().length();x++)
		{
		    linkedList.add(field1.getText().substring(x,x+1));
		}
		timer.start();
	    }
	    if(event.getSource() == timer)
	    {
		if(linkedList.size()==0)
		{
		    flashTextPanel.setText("done");
		    flashTextPanel.repaint();
		}
		else
		{
		    flashTextPanel.setText(linkedList.get(0));
		    flashTextPanel.repaint();
		    linkedList.remove(0);
		}
	    }
	}
    }

    

}
    


