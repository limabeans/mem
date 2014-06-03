import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.text.*;
import java.io.*;
public class GUI extends JFrame 
{
    //constants
    private final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("m:ss.SSS");
    private final int TIMER_DELAY = 53;
    private final int TIMER_FIELD_SIZE = 6;
    private final Font TIMER_FONT = new Font("Arial",Font.BOLD,95);
    private final Font SCRAMBLE_FONT = new Font("Serif",Font.BOLD,16);
    private final Font SOLVES_TIMES_STATS_FONT = new Font("Georgia",Font.BOLD,14);

    private Scrambler scrambler = new Scrambler();
    private String scramble = scrambler.genEasyScramble();
    private StringBuilder scrambleSBuilder = new StringBuilder();
    private TimerStats timerStats = new TimerStats();

    private CommSolver solver;

    //GUI instances
    private JTextField timerTextField, generatedScramble, forceEdgeTextField, forceCornerTextField;
    private JLabel forceEdgeLabel, forceCornerLabel;
    private JTextArea scrambleAnalysisTextArea, solveTimesTextArea, solveStatsTextArea;
    private JPanel panel1,panel2,panel3,panel4,panel5,panel6;
    private JToggleButton timingMemoToggle;
    private JRadioButton randomRadioButton, parityRadioButton, noParityRadioButton;
    private JCheckBox edgeFlipsCheckBox, cornerTwistsCheckBox;
    
    private javax.swing.Timer timer;

    //actual data
    private String lastSolveTime;
    private long lastSolveTimeInMillis;
    private String scrambleAnalysisString;

    //variables that are constantly changing
    private long startTime;
    private String solveTime;
    private boolean timeIsStarted;
    private long timeInMillis;

    public static void main(String[] args)
    {
	GUI exe = new GUI();
    }

    public GUI()
    {
	super("Angel Lim's mem");
	this.setLayout(new GridLayout(3,2));

	appendLog(); //debugging purposes
	solver = new CommSolver(scramble);

	panel1 = new JPanel(new BorderLayout());
	generatedScramble = new JTextField(scramble);
	generatedScramble.setEditable(false);
	generatedScramble.setHorizontalAlignment(JLabel.CENTER);
	generatedScramble.setFont(SCRAMBLE_FONT);
	panel1.add(generatedScramble,BorderLayout.CENTER);
	this.add(panel1);

	panel2 = new JPanel(new BorderLayout());
        scrambleAnalysisString = "Welcome to Angel Lim's BLD timer!";
	scrambleAnalysisTextArea = new JTextArea(scrambleAnalysisString);
	scrambleAnalysisTextArea.setEditable(false);
	scrambleAnalysisTextArea.setFont(SCRAMBLE_FONT);
	panel2.add(scrambleAnalysisTextArea,BorderLayout.CENTER);
	this.add(panel2);

	panel3 = new JPanel(new BorderLayout());
	timerTextField = new JTextField("Ready",TIMER_FIELD_SIZE);
	timerTextField.setFont(TIMER_FONT);
	timerTextField.setHorizontalAlignment(JTextField.CENTER);
	timerTextField.setEditable(false);
	timer = new javax.swing.Timer(TIMER_DELAY, new ClockListener());
	panel3.add(timerTextField, BorderLayout.CENTER);
	this.add(panel3);

	//set timerTextField Key Binding to D for DNFing
	DAction dAction = new DAction();
	timerTextField.getInputMap().put(KeyStroke.getKeyStroke("D"), "doDAction");
	timerTextField.getActionMap().put("doDAction", dAction);

	//set timerTextField Key Binding to SPACE
	SpaceAction spaceAction = new SpaceAction();	
	timerTextField.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "doSpaceAction");
	timerTextField.getActionMap().put("doSpaceAction", spaceAction);

	panel4 = new JPanel(new GridLayout(5,1));
	timingMemoToggle = new JToggleButton("Timing memo: OFF");
	JPanel panel4_2 = new JPanel();
	randomRadioButton = new JRadioButton("random");
	parityRadioButton = new JRadioButton("with parity");
	noParityRadioButton = new JRadioButton("without parity",true);
	panel4_2.add(randomRadioButton);
	panel4_2.add(parityRadioButton);
	panel4_2.add(noParityRadioButton);
	JPanel panel4_3 = new JPanel();
	edgeFlipsCheckBox = new JCheckBox("edge flips");
	cornerTwistsCheckBox = new JCheckBox("corner twists");
	panel4_3.add(edgeFlipsCheckBox);
	panel4_3.add(cornerTwistsCheckBox);
	JPanel panel4_4 = new JPanel();
	forceEdgeLabel = new JLabel("force edge comm: ");
	forceEdgeTextField = new JTextField(5);
	panel4_4.add(forceEdgeLabel);
	panel4_4.add(forceEdgeTextField);
	forceCornerLabel = new JLabel("force corner comm: ");
	forceCornerTextField = new JTextField(5);
	panel4_4.add(forceCornerLabel);
	panel4_4.add(forceCornerTextField);
	JPanel panel4_5 = new JPanel();
	panel4.add(timingMemoToggle);
	panel4.add(panel4_2);	
	panel4.add(panel4_3);
	panel4.add(panel4_4);
	panel4.add(panel4_5);
	this.add(panel4);

	panel5 = new JPanel(new GridLayout(1,2));
	solveTimesTextArea = new JTextArea("SOLVE TIMES");
	solveTimesTextArea.setFont(SOLVES_TIMES_STATS_FONT);
	solveTimesTextArea.setEditable(false);
	solveTimesTextArea.setLineWrap(true);
	panel5.add(solveTimesTextArea);
	JScrollPane solveTimesScroll = new JScrollPane(solveTimesTextArea);
	solveTimesScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	panel5.add(solveTimesScroll);

	solveStatsTextArea = new JTextArea("NOT WORKING\n" + timerStats.toString());
	solveStatsTextArea.setFont(SOLVES_TIMES_STATS_FONT);
	panel5.add(solveStatsTextArea);
	JScrollPane solveStatsScroll = new JScrollPane(solveStatsTextArea);
	solveStatsScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	panel5.add(solveStatsScroll);
	this.add(panel5);

	panel6 = new JPanel(new GridLayout(1,2));
	JTextArea temp6 = new JTextArea("for exporting text files");
	JTextArea settingstemp = new JTextArea("Settings");
	panel6.add(temp6);
	panel6.add(settingstemp);
	this.add(panel6);

	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Dimension screen = toolkit.getScreenSize();
	setSize((int)(screen.getWidth()*.9),(int)(screen.getHeight()*.9));
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setVisible(true);
	timerTextField.requestFocusInWindow(); //so SPACE keybinding will work immediately
    }
    class DAction extends AbstractAction
    {
	public void actionPerformed(ActionEvent e)
	{
	    if(!timer.isRunning())
	    {
		timerTextField.setText("DNF");
	    }
	}
    }
    class SpaceAction extends AbstractAction
    {
	public void actionPerformed(ActionEvent e)
	{
	    if(!timeIsStarted)
	    {

		timeIsStarted = true;
		startTime = System.currentTimeMillis();
		timer.start();
	    }
	    else if(timeIsStarted)
	    {
		timeIsStarted = false;
		updateTimer();
		startTime = 0;
		timer.stop();
		lastSolveTime = solveTime;
		//INSERT CODE: for timeStatsTextArea
		updateSolveTimesTextArea(); //write to bottom
		scrambleAnalysisString = solver.toString();
		scrambleAnalysisTextArea.setText(scrambleAnalysisString); //write to right

		//generating and prepping new for new scramble
		scramble = scrambler.genEasyScramble();
		generatedScramble.setText(scramble);
		solver.refresh(scramble);
	    }
	}
    }
    //temp method for debugging purposes.
    public void appendLog()
    {
	try(PrintWriter out = new PrintWriter(new FileWriter("log.txt",true))){
		out.print(String.format("%s\n",scramble));
	    } catch (IOException e){}
    }

    class ClockListener implements ActionListener 
    {
	//this method is called repeatedly based on a certain delay, once start() is called.
	public void actionPerformed(ActionEvent e)
	{
	    updateTimer();
	}
    }

    public void updateSolveTimesTextArea()
    {
	scrambleSBuilder.append(lastSolveTime + ", ");
	solveTimesTextArea.setText(scrambleSBuilder.toString());
    }
    public void updateTimer() //calcuates the solve time, sets it to solveTime, and edits timerTextField
    {
	timeInMillis = System.currentTimeMillis() - startTime;
	Date elapsed = new Date(timeInMillis);
	solveTime = SIMPLE_DATE_FORMAT.format(elapsed);
	timerTextField.setText(solveTime);
    }



}
