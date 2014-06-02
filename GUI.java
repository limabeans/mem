import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.text.*;
import java.io.*;
public class GUI extends JFrame implements KeyListener
{
    //constants
    private final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("mm:ss.SSS");
    private final int TIMER_DELAY = 53;
    private final int TIMER_FIELD_SIZE = 6;
    private final Font TIMER_FONT = new Font("Arial",Font.BOLD,70);
    private final Font SCRAMBLE_FONT = new Font("Serif",Font.BOLD,15);

    private Scrambler scrambler = new Scrambler();
    private String scramble = scrambler.genEasyScramble();
    private StringBuilder scrambleSBuilder = new StringBuilder();

    private CornerTracer cornerTracer;
    private EdgeTracer edgeTracer;

    //GUI instances
    private JTextField timerTextField, generatedScramble;
    private JLabel scrambleLabel;
    private JTextArea scrambleInfo, scrambleTimes;
    private JPanel panel1,panel2,panel3,panel4,panel5,panel6,panel7,panel8;
    private javax.swing.Timer timer;

    //actual data
    private String currentTime;
    private String previousTime;
    private String scrambleInfoString;

    //variables that are constantly changing
    private long startTime;
    private boolean started;

    public static void main(String[] args)
	    {
	GUI exe = new GUI();
    }
    public GUI()
    {
	super("Angel Lim's mem");
	started = false;
	this.setLayout(new GridLayout(3,2));
	
	try(PrintWriter out = new PrintWriter(new FileWriter("log.txt",true))){
		out.print(String.format("%s\n",scramble));
	    } catch (IOException e){}

	cornerTracer = new CornerTracer(scramble);
	edgeTracer = new EdgeTracer(scramble);
	cornerTracer.traceCorners(); edgeTracer.traceEdges();


	panel1 = new JPanel();
	scrambleLabel = new JLabel("Scramble: ");
	scrambleLabel.setFont(SCRAMBLE_FONT);
	panel1.add(scrambleLabel);
	generatedScramble = new JTextField(scramble);
	generatedScramble.setEditable(false);
	generatedScramble.setFont(SCRAMBLE_FONT);
	panel1.add(generatedScramble);
	this.add(panel1);

	panel2 = new JPanel();
        scrambleInfoString = String.format(cornerTracer.toString()+"\n"+edgeTracer.toString());
	scrambleInfo = new JTextArea(scrambleInfoString);
	panel2.add(scrambleInfo);
	this.add(panel2);

	panel3 = new JPanel();	
	timerTextField = new JTextField("Ready",TIMER_FIELD_SIZE);
	timerTextField.setFont(TIMER_FONT);
	timerTextField.setHorizontalAlignment(JTextField.CENTER);
	timerTextField.setEditable(false);
	timer = new javax.swing.Timer(TIMER_DELAY, new ClockListener());
	panel3.add(timerTextField);
	this.add(panel3);
	

	panel4 = new JPanel();
	JLabel temp4 = new JLabel("this is where the options will go");
	panel4.add(temp4);
	this.add(panel4);

	panel5 = new JPanel();
	scrambleTimes = new JTextArea("SCRAMBLE STATS");
	scrambleTimes.setEditable(false);
	scrambleTimes.setLineWrap(true);
	scrambleTimes.setRows(10);
	scrambleTimes.setColumns(20);
	panel5.add(scrambleTimes);
	JScrollPane scrambleTimesScroll = new JScrollPane(scrambleTimes);
	scrambleTimesScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	panel5.add(scrambleTimesScroll);
	this.add(panel5);

	panel6 = new JPanel();
	JLabel temp6 = new JLabel("this is where graph will go");
	panel6.add(temp6);
	this.add(panel6);

	addKeyListener(this);
	this.setFocusable(true);
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Dimension screen = toolkit.getScreenSize();
	setSize((int)(screen.getWidth()*.8),(int)(screen.getHeight()*.8));
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setVisible(true);
    }
    public void keyTyped(KeyEvent e) {} public void keyReleased(KeyEvent e) {}
    public void keyPressed(KeyEvent e)
    {
	if(e.getKeyCode() == KeyEvent.VK_SPACE)
	{

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
		updateScrambleTimes();
		scrambleInfo.setText(scrambleInfoString);
		
		scramble = scrambler.genEasyScramble();
		generatedScramble.setText(scramble);
		updateTracers(scramble);
		scrambleInfo.setText(scrambleInfoString);
		startTime = 0;
		timer.stop();
	    }
	}
    }

    class ClockListener implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    updateTimer();
	}
    }
    public void updateTracers(String newScramble)
    {
	cornerTracer = new CornerTracer(newScramble);
	edgeTracer = new EdgeTracer(newScramble);
	cornerTracer.traceCorners(); edgeTracer.traceEdges();
	scrambleInfoString = String.format(cornerTracer.toString()+"\n"+edgeTracer.toString());

    }
    public void updateScrambleTimes()
    {
	scrambleSBuilder.append(currentTime + " ,");
	scrambleTimes.setText(scrambleSBuilder.toString());
    }
    public void updateTimer()
    {
	Date elapsed = new Date(System.currentTimeMillis() - startTime);
	currentTime = SIMPLE_DATE_FORMAT.format(elapsed);
	timerTextField.setText(currentTime);
    }



}
