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

    private CommSolver solver;

    //GUI instances
    private JTextField timerTextField, generatedScramble, forceEdgeTextField, forceCornerTextField;
    private JLabel scrambleLabel, scrambleAnalysisLabel, forceEdgeLabel, forceCornerLabel;
    private JTextArea scrambleAnalysisTextArea, solveTimesTextArea;
    private JPanel panel1,panel2,panel3,panel4,panel5,panel6;
    private JToggleButton timingMemoToggle;
    private JRadioButton randomRadioButton, parityRadioButton, noParityRadioButton;
    private JCheckBox edgeFlipsCheckBox, cornerTwistsCheckBox;
    
    private javax.swing.Timer timer;

    //actual data
    private String solveTime;
    private String scrambleAnalysisString;

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

	appendLog(); //debuggin purposes
	solver = new CommSolver(scramble);

	panel1 = new JPanel();
	panel1.setLayout(new GridLayout(2,1));
	scrambleLabel = new JLabel("Scramble: ");
	scrambleLabel.setHorizontalAlignment(JLabel.CENTER);
	scrambleLabel.setFont(SCRAMBLE_FONT);
	//	panel1.add(scrambleLabel);
	generatedScramble = new JTextField(scramble);
	generatedScramble.setEditable(false);
	generatedScramble.setHorizontalAlignment(JLabel.CENTER);
	generatedScramble.setFont(SCRAMBLE_FONT);
	panel1.add(generatedScramble);
	this.add(panel1);

	panel2 = new JPanel();
	panel2.setLayout(new GridLayout(2,1));
	scrambleAnalysisLabel = new JLabel("Scramble Analysis");
	scrambleAnalysisLabel.setHorizontalAlignment(JLabel.CENTER);
	//	panel2.add(scrambleAnalysisLabel);
        scrambleAnalysisString = ""; //initialize to nothing
	scrambleAnalysisTextArea = new JTextArea(scrambleAnalysisString);
	panel2.add(scrambleAnalysisTextArea);
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
	panel4.setLayout(new GridLayout(5,1));
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
	forceEdgeLabel = new JLabel("force edge: ");
	forceEdgeTextField = new JTextField(5);
	panel4_4.add(forceEdgeLabel);
	panel4_4.add(forceEdgeTextField);
	JPanel panel4_5 = new JPanel();
	forceCornerLabel = new JLabel("force corner: ");
	forceCornerTextField = new JTextField(5);
	panel4_5.add(forceCornerLabel);
	panel4_5.add(forceCornerTextField);

	panel4.add(timingMemoToggle);
	panel4.add(panel4_2);	
	panel4.add(panel4_3);
	panel4.add(panel4_4);
	panel4.add(panel4_5);
	this.add(panel4);

	panel5 = new JPanel();
	solveTimesTextArea = new JTextArea("SCRAMBLE STATS");
	solveTimesTextArea.setEditable(false);
	solveTimesTextArea.setLineWrap(true);
	solveTimesTextArea.setRows(10);
	solveTimesTextArea.setColumns(20);
	panel5.add(solveTimesTextArea);
	JScrollPane solveTimesTextAreaScroll = new JScrollPane(solveTimesTextArea);
	solveTimesTextAreaScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	panel5.add(solveTimesTextAreaScroll);
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
		startTime = 0;
		timer.stop();
		updateSolveTimesTextArea(); //write to bottom
		scrambleAnalysisString = solver.toString();
		scrambleAnalysisTextArea.setText(scrambleAnalysisString); //write to right

		//generating and prepping new for new scramble
		scramble = scrambler.genEasyScramble();
		generatedScramble.setText(scramble);
		solver.refresh(scramble);
		scrambleAnalysisString = solver.toString();
		scrambleAnalysisTextArea.setText(scrambleAnalysisString);


	    }
	}
    }
    public void appendLog()
    {
	try(PrintWriter out = new PrintWriter(new FileWriter("log.txt",true))){
		out.print(String.format("%s\n",scramble));
	    } catch (IOException e){}
    }
    class ClockListener implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    updateTimer();
	}
    }

    public void updateSolveTimesTextArea()
    {
	scrambleSBuilder.append(solveTime + " ,");
	solveTimesTextArea.setText(scrambleSBuilder.toString());
    }
    public void updateTimer() //calcuates the solve time, sets it to solveTime, and edits timerTextField
    {
	Date elapsed = new Date(System.currentTimeMillis() - startTime);
	solveTime = SIMPLE_DATE_FORMAT.format(elapsed);
	timerTextField.setText(solveTime);
    }



}
