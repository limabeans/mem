import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class Mem extends JFrame
{

    LinkedList<String> linkedList = new LinkedList<String>();
    private JLabel label1;
    private JPanel panel1;
    private FlashText flashTextPanel;
    private JTextField field1;

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
	this.setLayout(new GridLayout(2,1));
	panel1 = new JPanel();
	panel1.setLayout(new GridLayout(1,2));
	this.add(panel1);
	label1 = new JLabel("List of letters to flash");
	panel1.add(label1);
	field1 = new JTextField();
	panel1.add(field1);
        flashTextPanel = new FlashText();
	this.getContentPane().add(flashTextPanel);

	FlashListener flashListener = new FlashListener();
	field1.addActionListener(flashListener);
	timer = new javax.swing.Timer(1000,flashListener);
	timer.setInitialDelay(0);
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
		    flashTextPanel.setLetter("done");
		    flashTextPanel.repaint();
		}
		else
		{
		    flashTextPanel.setLetter(linkedList.get(0));
		    flashTextPanel.repaint();
		    linkedList.remove(0);
		}
	    }
	}
    }

    

}
    


