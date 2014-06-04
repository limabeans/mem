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
    private TimerStats timerStats = new TimerStats();

    protected ArrayList<SolveTime> database1;

    private CommSolver solver;

    //GUI instances
    private JButton deleteLastTimeButton, exportSolveTimesButton, clearAllSolveTimesButton;
    private JTextField timerTextField, generatedScramble, forceEdgeTextField, forceCornerTextField, exportSolveTimesTextField;
    private JLabel forceEdgeLabel, forceCornerLabel, solveTimesLabel;
    private JTextArea scrambleAnalysisTextArea, solveTimesTextArea, solveStatsTextArea;
    private JPanel panel1,panel2,panel3,panel4,panel5,panel6;
    private JToggleButton timingMemoToggle;
    private JRadioButton randomRadioButton, parityRadioButton, noParityRadioButton;
    private JCheckBox edgeFlipsCheckBox, cornerTwistsCheckBox;
    private JComboBox<String> selectPuzzleComboBox;
    private String[] selectPuzzleArray = { "3x3 blindfolded", "3x3 speedsolve" };

    File exportSolveTimesFile;
    
    private javax.swing.Timer timer;

    //actual data

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
	database1 = new ArrayList<SolveTime>();

	panel1 = new JPanel(new BorderLayout());
	JPanel panel1_1 = new JPanel(new GridLayout(1,2));
	selectPuzzleComboBox = new JComboBox<String>(selectPuzzleArray);
	panel1_1.add(selectPuzzleComboBox);
	selectPuzzleComboBox.addActionListener(new selectPuzzleComboBoxListener());
	panel1.add(panel1_1,BorderLayout.NORTH);
	generatedScramble = new JTextField(scramble);
	generatedScramble.setEditable(false);
	generatedScramble.setHorizontalAlignment(JLabel.CENTER);
	generatedScramble.setFont(SCRAMBLE_FONT);
	panel1.add(generatedScramble,BorderLayout.CENTER);
	this.add(panel1);

	panel2 = new JPanel(new BorderLayout());
	scrambleAnalysisTextArea = new JTextArea("welcome to Angel Lim's bld timer!");
	scrambleAnalysisTextArea.setEditable(false);
	scrambleAnalysisTextArea.setFont(SCRAMBLE_FONT);
	panel2.add(scrambleAnalysisTextArea,BorderLayout.CENTER);
	this.add(panel2);

	panel3 = new JPanel(new BorderLayout());
	timerTextField = new JTextField("Ready",TIMER_FIELD_SIZE);
	timerTextField.setFont(TIMER_FONT);
	timerTextField.setHorizontalAlignment(JTextField.CENTER);
	timerTextField.setEditable(false);
	timerTextField.addFocusListener(new TimerFocusListener());
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
	timingMemoToggle.setEnabled(false);
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
	//panel5-left side
	JPanel panel5_1 = new JPanel(new BorderLayout());
	JPanel panel5_1top = new JPanel();
	solveTimesLabel = new JLabel("Solve Times");
	solveTimesLabel.setHorizontalAlignment(JLabel.CENTER);
	panel5_1top.add(solveTimesLabel);
	exportSolveTimesButton = new JButton("Export");
	panel5_1.add(panel5_1top, BorderLayout.NORTH);
	JPanel panel5_1bot = new JPanel(new GridLayout(2,1));
	JPanel panel5_1botdelete = new JPanel(new GridLayout(1,2));
	deleteLastTimeButton = new JButton("Delete Last Time");
	panel5_1botdelete.add(deleteLastTimeButton);
	clearAllSolveTimesButton = new JButton("Clear All Times");
	clearAllSolveTimesButton.addActionListener(new deleteAllSolveTimesButtonListener());
	clearAllSolveTimesButton.setForeground(Color.RED);
	panel5_1botdelete.add(clearAllSolveTimesButton);
	panel5_1bot.add(panel5_1botdelete);
	JPanel panel5_1botexport = new JPanel(new FlowLayout());
	exportSolveTimesTextField = new JTextField("/home/lima/Desktop/session.txt",19);
	exportSolveTimesButton = new JButton("Export");
	panel5_1botexport.add(exportSolveTimesTextField);
	panel5_1botexport.add(exportSolveTimesButton);
	exportSolveTimesButton.addActionListener( new ExportSolveTimesButtonListener() );
	panel5_1bot.add(panel5_1botexport);
	panel5_1.add(panel5_1bot,BorderLayout.SOUTH);
	
	//panel5-right side
	solveTimesTextArea = new JTextArea();
	solveTimesTextArea.setFont(SOLVES_TIMES_STATS_FONT);
	solveTimesTextArea.setEditable(false);
	solveTimesTextArea.setLineWrap(true);
	panel5_1.add(solveTimesTextArea, BorderLayout.CENTER);
	JScrollPane solveTimesScroll = new JScrollPane(solveTimesTextArea);
	solveTimesScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	panel5_1.add(solveTimesScroll, BorderLayout.CENTER);
	panel5.add(panel5_1);

	solveStatsTextArea = new JTextArea(timerStats.toString());
	solveStatsTextArea.setFont(SOLVES_TIMES_STATS_FONT);
	solveStatsTextArea.setEditable(false);
	panel5.add(solveStatsTextArea);
	JScrollPane solveStatsScroll = new JScrollPane(solveStatsTextArea);
	solveStatsScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	panel5.add(solveStatsScroll);
	this.add(panel5);

	panel6 = new JPanel(new GridLayout(1,2));
	JTextArea temp6 = new JTextArea("Key Bindings:\n SPACE: start/stop timer\n D: DNF\n");
	JTextArea settingstemp = new JTextArea("Settings");
	panel6.add(temp6);
	panel6.add(settingstemp);
	this.add(panel6);

	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Dimension screen = toolkit.getScreenSize();
	setSize((int)(screen.getWidth()*.9),(int)(screen.getHeight()*.9));
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setVisible(true);
	
	exportSolveTimesFile = new File(exportSolveTimesTextField.getText());
	timerTextField.requestFocusInWindow(); //so SPACE keybinding will work immediately

	//DISABLED FEATURES
	randomRadioButton.setEnabled(false);
	parityRadioButton.setEnabled(false);
	forceCornerTextField.setEnabled(false);
	forceEdgeTextField.setEnabled(false);
	forceCornerTextField.setEnabled(false);
	deleteLastTimeButton.setEnabled(false);
	forceCornerLabel.setEnabled(false);
	forceEdgeLabel.setEnabled(false);
	edgeFlipsCheckBox.setEnabled(false);
	cornerTwistsCheckBox.setEnabled(false);
	//	solveTimesTextArea.getInputMap().put(KeyStroke.getKeyStroke("D"), "doDAction");
	//	solveTimesTextArea.getActionMap().put("doDAction", dAction);
    }


    //INNER CLASSES
    class TimerFocusListener implements FocusListener
    {
	public void focusGained(FocusEvent e)
	{
	    timerTextField.setForeground(new Color(0,0,0));
	}
	public void focusLost(FocusEvent e)
	{
	    timerTextField.setForeground(new Color(204,204,204));
	}
    }
    class deleteAllSolveTimesButtonListener implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear all times?", "Confirmation", JOptionPane.YES_NO_OPTION);
	    if(confirm == JOptionPane.YES_OPTION)
	    {
		database1.clear();
		updateSolveTimesTextArea();
		updateSolveStatsTextArea();
		timerTextField.setText("Ready");
		timerTextField.requestFocusInWindow();
		scrambleAnalysisTextArea.setText("Ready!");
	    }
	 	    
	}
    }
    class selectPuzzleComboBoxListener implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    if(((String)selectPuzzleComboBox.getSelectedItem()).equals("3x3 speedsolve"))
	    {
		panel2.setVisible(false);
		panel4.setVisible(false);
	    }
	    if(((String)selectPuzzleComboBox.getSelectedItem()).equals("3x3 blindfolded"))
	    {
		panel2.setVisible(true);
		panel4.setVisible(true);
	    }
	    timerTextField.requestFocusInWindow();
	}
    }

    class ExportSolveTimesButtonListener implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    exportSolveTimesFile = new File(exportSolveTimesTextField.getText());
	    if(!exportSolveTimesFile.exists())
	    {
		export();
	    }
	    else
	    {
		int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to write over " + exportSolveTimesTextField.getText(), exportSolveTimesTextField.getText() + " exists!", JOptionPane.YES_NO_OPTION);
		if(confirm == JOptionPane.YES_OPTION)
		{
		    export();
		}
		else
		{
		    JOptionPane.showMessageDialog(null,"Did not export");
		}
	    }
	    timerTextField.requestFocusInWindow();
	}
	public void export()
	{
	    try(PrintWriter out = new PrintWriter(new FileWriter(exportSolveTimesFile))) {
		    out.print("Session Solve Times\n"); 
		    out.print(solveTimesTextArea.getText());
		    out.close();
		} catch (IOException IOE) { JOptionPane.showMessageDialog(null, "Exporting failure");} 
	    JOptionPane.showMessageDialog(null,"Exported");
	}
    }
    class DAction extends AbstractAction
    {
	public void actionPerformed(ActionEvent e)
	{
	    if(!timer.isRunning())
	    {
		timerTextField.setText("DNF");
		database1.get(database1.size()-1).setToDNF();
		updateSolveTimesTextArea();
		updateSolveStatsTextArea();
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
		timer.stop();
		updateTimer();//calcs solve time, sets it to solveTime, and edits timerTextField
		updateSolveTimesArrayList();
	        updateSolveStatsTextArea(); 
		updateScrambleAnalysisTextArea();//write to right

		updateSolveTimesTextArea(); //write to bottom-left 

	        prepNewScramble();

	    }
	}
    }
    public void updateSolveTimesArrayList()
    {
	SolveTime newSolveTime = new SolveTime(solveTime);
	database1.add(newSolveTime);
    }
    public void updateSolveTimesTextArea()
    {
	StringBuilder toThisTextArea = new StringBuilder();
	for(int x = 0; x < database1.size(); x++)
	{
	    toThisTextArea.append((x+1) + ". " + database1.get(x).toString() + "\n");
	}
	solveTimesTextArea.setText(toThisTextArea.toString());
    }
    public void prepNewScramble()
    {
	scramble = scrambler.genEasyScramble();
	generatedScramble.setText(scramble);
	solver.refresh(scramble);
    }
    public void updateSolveStatsTextArea()
    {
	solveStatsTextArea.setText(timerStats.toString());
    }
    public void updateScrambleAnalysisTextArea()
    {
	scrambleAnalysisTextArea.setText(solver.toString());
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


    public void updateTimer() //calcuates the solve time, sets it to solveTime, and edits timerTextField
    {
	timeInMillis = System.currentTimeMillis() - startTime;
	Date elapsed = new Date(timeInMillis);
	solveTime = SIMPLE_DATE_FORMAT.format(elapsed);
	timerTextField.setText(solveTime);
    }


    class TimerStats
    {
	private String bestMO3, bestAVG5;
	private ArrayList<SolveTime> databaseAVG5;
	public TimerStats()
	{
	    databaseAVG5 = new ArrayList<SolveTime>();
	    bestAVG5 = "99:59.59";
	    bestMO3 = "99:59.59";
	}
	public String toString()
	{
	    String formatted = "Best Time: " + getBestTime() + "\n" +
		"Worst Time: " + getWorstTime() + "\n" ;//+
		/*	"Best mo3: " + getBestMO3() + "\n" +
		"Best avg5: " + getBestAVG5() + "\n" +
		"Current mo3: " + getCurrentMO3() + "\n" +
		"Current avg5: " + getCurrentAVG5() + "\n" ;*/

	    return formatted;
	}
	public String getBestAVG5()
	{
	    if(database1.size()<5)
		return "DNF";
	    System.out.println("WEOIFUDSFOA");
	    Collections.sort(databaseAVG5);
	    return databaseAVG5.get(databaseAVG5.size()-1).getTime();
	}
	public String getCurrentAVG5()
	{
	    databaseAVG5.add(getCurrentAVGz(5));
	    System.out.println(databaseAVG5);
	    return databaseAVG5.get(databaseAVG5.size()-1).getTime();
	}
	public SolveTime getCurrentAVGz(int z)
	{
	    int numDNFs=0;
	    if(database1.size()<z)
		return new SolveTime("99:59.59",true);
	    ArrayList<SolveTime> solves = new ArrayList<SolveTime>();
	    for(int x = 1; x<=z ; x++)
	    {
		solves.add(database1.get(database1.size() -x));
		if(database1.get(database1.size() -x).getIsDNF())
		{
		    numDNFs++;
		}
		if(numDNFs==2)
		{
		    return new SolveTime("99:59.59",true);
		}
	    }
	    Collections.sort(solves);
	    long sumZ=0;
	    for(int i = 1; i <= solves.size()-2 ; i++)
	    {
		sumZ+= SolveTime.convertToMillis(solves.get(i).getTime());
	    }
	    return new SolveTime(SIMPLE_DATE_FORMAT.format((sumZ ) / (z-2)), false);
	}
	public String getBestMO3()
	{
	    if(database1.size()<3)
		return "DNS";
	    if(!getCurrentMO3().equals("DNF") &&
	       ((SolveTime.convertToMillis(bestMO3) - SolveTime.convertToMillis(getCurrentMO3())) > 0))
	    {
		bestMO3 = getCurrentMO3();
	    }
	    return bestMO3;
	}
	public String getCurrentMO3()
	{
	    if(database1.size()<3)
		return "DNS";
	    if(database1.get(database1.size()-1).getIsDNF() || 
	       database1.get(database1.size()-2).getIsDNF() ||
	       database1.get(database1.size()-3).getIsDNF())
		return "DNF";
	    
	    return SIMPLE_DATE_FORMAT.format((SolveTime.convertToMillis(database1.get(database1.size()-1).getTime())
					      +SolveTime.convertToMillis(database1.get(database1.size()-2).getTime())
					      +SolveTime.convertToMillis(database1.get(database1.size()-3).getTime())) / 3);
	}
	public String getBestTime()
	{
	    if(database1.size()==0)
		return "DNS";
	    if(database1.size()==1)
		return database1.get(0).toString();
	    else
	    {
		SolveTime best = database1.get(0);
		for(int x = 0; x < database1.size(); x++)
		{
		    if(database1.get(x).compareTo(best)>0)
			best = database1.get(x);
		}
		return best.getTime();
	    }
	}
	public String getWorstTime()
	{
	    if(database1.size()==0)
		return "DNS";
	    if(database1.size()==1)
		return database1.get(0).toString();
	    else
	    {
		SolveTime worst = database1.get(0);
		for(int x = 0; x < database1.size(); x++)
		{
		    if(database1.get(x).compareTo(worst)<0)
			worst = database1.get(x);
		}
		if(worst.getIsDNF())
		{
		    return "DNF";
		}
		return worst.getTime();
	    }
	}
    }



}
