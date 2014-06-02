import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.text.*;
public class GUI extends JFrame implements KeyListener
{
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm.ss.SSS");

    private JToggleButton toggleTimer;
    private JTextField timerTextField;

    private javax.swing.Timer timer;

    private long startTime;
    private boolean started;

    public static void main(String[] args)
    {
	GUI exe = new GUI();
    }
    public GUI()
    {
	super("limatimer");
	started = false;
	JPanel mainPanel = new JPanel();
	//mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.PAGE_AXIS));



	toggleTimer = new JToggleButton("Start");
	toggleTimer.addItemListener(new ToggleButtonListener());
	//mainPanel.add(toggleTimer);
	
	timerTextField = new JTextField("TIMER");
	timerTextField.setEditable(false);
	mainPanel.add(timerTextField);
	
	timer = new javax.swing.Timer(53, new ClockListener());

	this.add(mainPanel);
	addKeyListener(this);
	this.setFocusable(true);
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Dimension screen = toolkit.getScreenSize();
	setSize((int)(screen.getWidth()*.8),(int)(screen.getHeight()*.8));
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setVisible(true);
    }
    public void keyTyped(KeyEvent e)
    {
	if(e.getKeyCode() == KeyEvent.VK_SPACE)
	    System.out.println("space typed");
    }
    public void keyPressed(KeyEvent e)
    {
	if(e.getKeyCode() == KeyEvent.VK_SPACE)
	{
	    System.out.println("space pressed");
	    if(started == false)
	    {
		started =true;
		startTime = System.currentTimeMillis();
		timer.start();
	    }
	    else if(started == true)
	    {
		started=false;
		updateTimer();
		startTime = 0;
		timer.stop();
	    }
	}
    }
    public void keyReleased(KeyEvent e)
    {
	if(e.getKeyCode() == KeyEvent.VK_SPACE)
	    System.out.println("space released");
    }
    class ClockListener implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    updateTimer();
	}
    }

    public void updateTimer()
    {
	Date elapsed = new Date(System.currentTimeMillis() - startTime);
	timerTextField.setText(simpleDateFormat.format(elapsed));
    }

    class ToggleButtonListener implements ItemListener
    {
	public void itemStateChanged(ItemEvent e)
	{
	    if(toggleTimer.isSelected())
	    {
		toggleTimer.setText("Stop");
		startTime = System.currentTimeMillis();
		timer.start();
	    }
	    else
	    {
		updateTimer();
		toggleTimer.setText("Start");
		startTime = 0;
		timer.stop();
	    }
	}
    }


}
