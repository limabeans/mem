import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class FlashText extends JPanel
{
    private Random rand = new Random();
    private String text = "default";

    public void setText(String newText)
    {
	text = newText;
    }
    public void paintComponent(Graphics g)
    {
	super.paintComponent(g);
	g.setColor(Color.BLACK);
	Font font = new Font("Arial",Font.BOLD,100);
	g.setFont(font);
	g.drawString(text,100,100);

    }
}
