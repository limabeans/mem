import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyEvent.*;
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

    //tool instances
    private javax.swing.Timer timer;
    private Scrambler scrambler = new Scrambler();
    private String scramble = scrambler.genFriendlyScramble();
    private SessionStats sessionStats = new SessionStats();

    //GUI instances
    private JButton deleteLastTimeButton, exportSolveTimesButton, clearAllSolveTimesButton, commentSubmitButton;
    private JTextField timerTextField, generatedScramble, exportSolveTimesTextField, forceEdgeCommTextField, forceCornerCommTextField, commentTextField;
    private JLabel scrambleOptionsLabel, paritySelectLabel, edgeFlipsSelectLabel, forceEdgeCommLabel, cornerTwistsSelectLabel, forceCornerCommLabel, commentLabel;
    private JTextArea scrambleAnalysisTextArea, solveTimesTextArea, sessionStatsTextArea;
    private JPanel panel1,panel2,panel3,panel4,panel5,panel6;
    private JToggleButton timingMemoToggleButton;
    //    private JRadioButton 
    //    private JCheckBox
    private JComboBox<String> selectPuzzleComboBox, cornerTwistsComboBox, edgeFlipsComboBox, parityComboBox;
    private String[] selectPuzzleArray = { "3x3 blindfolded", "3x3 speedsolve", "3x3 blindfolded memo practice" };
    private String[] selectYesNoRandomArray = { "No", "Yes", "Random" };
    private String[] onOffArray = {"On","Off"};


    //data instances
    protected ArrayList<ASolve> database1;
    private ASolve currentSolve;
    File exportSolveTimesFile;
    
    //variables that are constantly changing
    private long startTime;
    private boolean timeIsStarted;
    private String currentSolveTime;

    public static void main(String[] args)
    {
	GUI exe = new GUI();
    }

    public GUI()
    {
	super("mem -- my personal java prototype of (what will eventually become) limatime.");
	this.setLayout(new GridLayout(3,2));
	currentSolve = new ASolve(scramble);
	
	database1 = new ArrayList<ASolve>();

	panel1 = new JPanel(new BorderLayout());
	JPanel panel1_1 = new JPanel(new GridLayout(1,2));
	selectPuzzleComboBox = new JComboBox<String>(selectPuzzleArray);
	panel1_1.add(selectPuzzleComboBox);
	selectPuzzleComboBox.addActionListener(new selectPuzzleComboBoxListener());
	panel1.add(panel1_1,BorderLayout.NORTH);
	generatedScramble = new JTextField(scramble);
	generatedScramble.setEditable(false);
	generatedScramble.setFocusable(false);
	generatedScramble.setHorizontalAlignment(JLabel.CENTER);
	generatedScramble.setFont(SCRAMBLE_FONT);
	panel1.add(generatedScramble,BorderLayout.CENTER);
	this.add(panel1);

	panel2 = new JPanel(new BorderLayout());
	scrambleAnalysisTextArea = new JTextArea("welcome to Angel Lim's bld timer!");
	scrambleAnalysisTextArea.setEditable(false);
	scrambleAnalysisTextArea.setFocusable(false);
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

	//to shift focus to commentTextField with you hit enter
	//KeyStroke.getKeyStroke('L',KeyEvent.CTRL_DOWN_MASK), "doCommentAction"
	CommentAction commentAction = new CommentAction();
	//KeyEvent.VK_ENTER
	timerTextField.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "doCommentAction");
	timerTextField.getActionMap().put("doCommentAction", commentAction);


	panel4 = new JPanel(new BorderLayout());
	//panel4LeftSide
	JPanel panel4LeftSide = new JPanel(new GridLayout(7,1));
	JPanel panel4_1 = new JPanel();
	scrambleOptionsLabel = new JLabel("Scramble Options");
	scrambleOptionsLabel.setHorizontalAlignment(JLabel.CENTER);
	panel4_1.add(scrambleOptionsLabel);
	panel4LeftSide.add(panel4_1);
	JPanel panel4_2 = new JPanel(new GridLayout(1,2));
	paritySelectLabel = new JLabel("Parity:");
	paritySelectLabel.setHorizontalAlignment(JLabel.RIGHT);
	panel4_2.add(paritySelectLabel);
	parityComboBox = new JComboBox<String>(selectYesNoRandomArray);
	parityComboBox.addActionListener(new CustomScrambleListener());
	panel4_2.add(parityComboBox);
	panel4LeftSide.add(panel4_2);
	JPanel panel4_3 = new JPanel(new GridLayout(1,2));
	edgeFlipsSelectLabel = new JLabel("Edge Flips:");
	edgeFlipsSelectLabel.setHorizontalAlignment(JLabel.RIGHT);
	panel4_3.add(edgeFlipsSelectLabel);
        edgeFlipsComboBox = new JComboBox<String>(selectYesNoRandomArray);
	edgeFlipsComboBox.addActionListener(new CustomScrambleListener());
	panel4_3.add(edgeFlipsComboBox);
	panel4LeftSide.add(panel4_3);
	JPanel panel4_4 = new JPanel(new GridLayout(1,2));
	forceEdgeCommLabel = new JLabel("Force edge comm:");
	forceEdgeCommLabel.setHorizontalAlignment(JLabel.RIGHT);
	panel4_4.add(forceEdgeCommLabel);
        forceEdgeCommTextField = new JTextField();
	panel4_4.add(forceEdgeCommTextField);
	forceEdgeCommTextField.addActionListener(new CustomScrambleListener());
	panel4LeftSide.add(panel4_4);
	JPanel panel4_5 = new JPanel(new GridLayout(1,2));
	cornerTwistsSelectLabel = new JLabel("Corner Twists:");
	cornerTwistsSelectLabel.setHorizontalAlignment(JLabel.RIGHT);
	panel4_5.add(cornerTwistsSelectLabel);
	cornerTwistsComboBox = new JComboBox<String>(selectYesNoRandomArray);
	cornerTwistsComboBox.addActionListener(new CustomScrambleListener());
	panel4_5.add(cornerTwistsComboBox);
	panel4LeftSide.add(panel4_5);
	JPanel panel4_6 = new JPanel(new GridLayout(1,2));
	forceCornerCommLabel = new JLabel("Force corner comm:");
	forceCornerCommLabel.setHorizontalAlignment(JLabel.RIGHT);
	panel4_6.add(forceCornerCommLabel);
	forceCornerCommTextField = new JTextField();
	panel4_6.add(forceCornerCommTextField);
	forceCornerCommTextField.addActionListener(new CustomScrambleListener());
	panel4LeftSide.add(panel4_6);
	JPanel panel4_7 = new JPanel();
	timingMemoToggleButton = new JToggleButton("Timing memo: OFF");
	panel4_7.add(timingMemoToggleButton);
	panel4LeftSide.add(panel4_7);
	panel4.add(panel4LeftSide, BorderLayout.WEST);
	//panel4RightSide
	JPanel panel4RightSide = new JPanel(new GridLayout(2,1));
	JButton tempButton = new JButton("//add new feature here");
	JButton temp2 = new JButton("mem v0.5 --Angel Lim");
	panel4RightSide.add(tempButton);
	panel4RightSide.add(temp2);
	panel4.add(panel4RightSide, BorderLayout.CENTER);
	this.add(panel4);

	panel5 = new JPanel(new GridLayout(1,2));
	//panel5-left side
	JPanel panel5_1 = new JPanel(new BorderLayout());
	JPanel panel5_1top = new JPanel();
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
	JPanel panel5_1botcomment = new JPanel(new FlowLayout());
	commentLabel = new JLabel("Comment");
	panel5_1botcomment.add(commentLabel);
	commentTextField = new JTextField(12);
	commentTextField.addActionListener(new CommentListener());
	panel5_1botcomment.add(commentTextField);
	commentSubmitButton = new JButton("Submit");
	commentSubmitButton.addActionListener(new CommentListener());
	panel5_1botcomment.add(commentSubmitButton);
	panel5_1bot.add(panel5_1botcomment);

	panel5_1.add(panel5_1bot,BorderLayout.SOUTH);
	//panel5-right side
	solveTimesTextArea = new JTextArea();
	solveTimesTextArea.setFont(SOLVES_TIMES_STATS_FONT);
	solveTimesTextArea.setEditable(false);
	solveTimesTextArea.setLineWrap(true);
	solveTimesTextArea.setFocusable(false);
	panel5_1.add(solveTimesTextArea, BorderLayout.CENTER);
	JScrollPane solveTimesScroll = new JScrollPane(solveTimesTextArea);
	solveTimesScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	panel5_1.add(solveTimesScroll, BorderLayout.CENTER);
	panel5.add(panel5_1);
	//panel5_2
	JPanel panel5_2 = new JPanel(new BorderLayout());
	JPanel panel5_2top = new JPanel();
	panel5_2.add(panel5_2top,BorderLayout.NORTH);
	sessionStatsTextArea = new JTextArea(sessionStats.toString());
	sessionStatsTextArea.setFont(SOLVES_TIMES_STATS_FONT);
	sessionStatsTextArea.setEditable(false);
	sessionStatsTextArea.setFocusable(false);
	panel5_2.add(sessionStatsTextArea, BorderLayout.CENTER);
	JScrollPane sessionStatsScroll = new JScrollPane(sessionStatsTextArea);
	sessionStatsScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	panel5_2.add(sessionStatsScroll, BorderLayout.CENTER);

	JPanel panel5_2bot = new JPanel(new FlowLayout());
	exportSolveTimesTextField = new JTextField("/home/lima/Desktop/session.txt",19);
	exportSolveTimesButton = new JButton("Export");
	panel5_2bot.add(exportSolveTimesTextField);
	panel5_2bot.add(exportSolveTimesButton);
	exportSolveTimesButton.addActionListener( new ExportSolveTimesButtonListener() );
	panel5_2.add(panel5_2bot,BorderLayout.SOUTH);

	panel5.add(panel5_2);
	this.add(panel5);

	panel6 = new JPanel(new BorderLayout());

	this.add(panel6);

	//setting size/display appropriately
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Dimension screen = toolkit.getScreenSize();
	setSize((int)(screen.getWidth()*.9),(int)(screen.getHeight()*.9));
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setVisible(true);

	//housekeeping prep
	prepNewScramble();
	exportSolveTimesFile = new File(exportSolveTimesTextField.getText());
	timerTextField.requestFocusInWindow(); //so SPACE keybinding will work immediately

	//DISABLED FEATURES broken
	deleteLastTimeButton.setEnabled(false);
	timingMemoToggleButton.setEnabled(false);

	//STUFF AUTO-DISABLED AT START
	//commentLabel.setEnabled(false);
	//commentTextField.setEnabled(false);
	//commentSubmitButton.setEnabled(false);
    }

    //LISTENERS
    class CommentListener implements ActionListener
    {
	public void actionPerformed(ActionEvent event)
	{
	    database1.get(database1.size()-1).setComment(commentTextField.getText());
	    updateSolveTimesTextArea();
	    updateScrambleAnalysisTextArea();
	    timerTextField.requestFocusInWindow();
	}
    }

    class CustomScrambleListener implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    prepNewScramble();
	    timerTextField.requestFocusInWindow();
	}
    }
    class ClockListener implements ActionListener 
    {
	//this method is called repeatedly based on a certain delay, once start() is called.
	public void actionPerformed(ActionEvent e)
	{
	    updateTimer();
	}
    }
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
	    if(((String)selectPuzzleComboBox.getSelectedItem()).equals("3x3 blindfolded memo practice"))
	    {
		panel2.setVisible(false);
		panel4.setVisible(false);
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
    //KEYBINDING CLASSES
    class CommentAction extends AbstractAction //note, figure out how to implement with CONTROL.....
    {
	public void actionPerformed(ActionEvent e)
	{
	    commentTextField.requestFocusInWindow();
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
		currentSolve.setTime(database1.get(database1.size()-1).toString());
		updateSolveTimesTextArea();
		updateSolveStatsTextArea();
		updateScrambleAnalysisTextArea();
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
		updateTimer();//calcs solve time,updates currentSolve.setTime(), and edits timerTextField
		updateSolveTimesArrayList();
	        updateSolveStatsTextArea(); 
		updateScrambleAnalysisTextArea();//write to right
		updateSolveTimesTextArea(); //write to bottom-left 
		prepNewScramble();
	    }
	}
    }

    //findScramble-parity/flips/twists 4/2/1/0 no,yes,random,blank
    //27
    //FIND A CUSTOM SCRAMBLE
    public void prepNewScramble()
    {
	tempParityFix();
	scramble = scrambler.genFriendlyScramble();
	currentSolve = new ASolve(scramble);
	switch((String)parityComboBox.getSelectedItem()) 
	{
	case "No": //4 0 0
	    switch((String)edgeFlipsComboBox.getSelectedItem()) 
	    {
	    case "No": //4 4 0
		switch((String)cornerTwistsComboBox.getSelectedItem()) 
		{
		case "No": //4 4 4
		    while(currentSolve.hasFlippedEdges() || currentSolve.hasTwistedCorners())
			reloadSolverEngine();
		    break;
		case "Yes": //4 4 2
		    while(currentSolve.hasFlippedEdges() || !currentSolve.hasTwistedCorners())
			reloadSolverEngine();
		    break;
		case "Random": //4 4 1
		    while(currentSolve.hasFlippedEdges())
			reloadSolverEngine();
		    break;
		}
		break;
	    
	    case "Yes": //4 2 0
	        switch((String)cornerTwistsComboBox.getSelectedItem())
		{
		case "No": //4 2 4
		    while(!currentSolve.hasFlippedEdges() || currentSolve.hasTwistedCorners())
			reloadSolverEngine();
		    break;
		case "Yes": //4 2 2
		    while(!currentSolve.hasFlippedEdges() || !currentSolve.hasTwistedCorners())
			reloadSolverEngine();
		    break;
		case "Random": //4 2 1
		    while(!currentSolve.hasFlippedEdges())
			reloadSolverEngine();
		    break;
		}
		break;
	    case "Random": // 4 1 0
	        switch((String)cornerTwistsComboBox.getSelectedItem())
		{
		case "No": //4 1 4
		    while(currentSolve.hasTwistedCorners())
			reloadSolverEngine();
		    break;
		case "Yes": //4 1 2
		    while(!currentSolve.hasTwistedCorners())
			reloadSolverEngine();
		    break;
		case "Random": //4 1 1
		    //while(parity)
		    reloadSolverEngine();
		    break;
		}
		break;
	    }
	    break;

	default:
	    scrambleAnalysisTextArea.setText("Scramble may have parity, \n scramble analysis and options broken for now :(");
	    scramble = scrambler.genDangerousScramble();
	    tempParityBreak();
	    break;

	    /*	case "Yes": //2 0 0
	    break;
	case "Random": //1 0 0 
	    break;
	default:
	System.out.println("You really messed up");*/
	
	}

	if(forceEdgeCommTextField.getText().length()>0)
	{
	    forceEdgeCommInScramble();
	}
	if(forceCornerCommTextField.getText().length()>0)
	{
	    forceCornerCommInScramble();
	}
	generatedScramble.setText(scramble);
    }

    public void forceEdgeCommInScramble()
    {
	while(!currentSolve.hasGivenEdgeComm(forceEdgeCommTextField.getText()))
	{
	    prepNewScramble();
	}
	forceEdgeCommTextField.setText("");
    }
    public void forceCornerCommInScramble()
    {
	while(!currentSolve.hasGivenCornerComm(forceCornerCommTextField.getText()))
	{
	    prepNewScramble();
	}
	forceCornerCommTextField.setText("");
    }
    public void tempParityBreak()
    {
	edgeFlipsComboBox.setEnabled(false);
	cornerTwistsComboBox.setEnabled(false);
	forceEdgeCommTextField.setEnabled(false);
	forceCornerCommTextField.setEnabled(false);
    }
    public void tempParityFix()
    {
	edgeFlipsComboBox.setEnabled(true);
	cornerTwistsComboBox.setEnabled(true);
	forceEdgeCommTextField.setEnabled(true);
	forceCornerCommTextField.setEnabled(true);
    }


    //UPDATE METHODS
    public void reloadSolverEngine()
    {
	scramble = scrambler.genFriendlyScramble();
	currentSolve.refresh(scramble);
    }
    public void updateSolveTimesArrayList()
    {
	currentSolve.setTime(currentSolveTime);
	database1.add(currentSolve);
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

    public void updateSolveStatsTextArea()
    {
	sessionStatsTextArea.setText(sessionStats.toString());
    }
    public void updateScrambleAnalysisTextArea()
    {
	scrambleAnalysisTextArea.setText(database1.get((database1.size()-1)).getAnalysis());
    }
    public void updateTimer()
    {
	Date elapsed = new Date(System.currentTimeMillis() - startTime);
	currentSolveTime = SIMPLE_DATE_FORMAT.format(elapsed);
	currentSolve.setTime(currentSolveTime);
	timerTextField.setText(currentSolve.getTime());
    }

    //HELPER CLASSES

    class SessionStats
    {
	private String bestMO3, bestAVG5;
	private ArrayList<ASolve> databaseAVG5;
	public SessionStats()
	{
	    databaseAVG5 = new ArrayList<ASolve>();
	    bestAVG5 = "99:59.59";
	    bestMO3 = "99:59.59";
	}
	
	public String toString()
	{
	    return "BestTime\nWorstTime\nSuccessStreak\ndnfStreak\nbestmo3\ncurrentmo3\nbestavg5\ncurrentavg5\nbestavg12\ncurrentavg12";
	}
    }
        


}
