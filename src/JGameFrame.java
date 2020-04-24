import java.awt.*;
import java.applet.*;
import javax.swing.*;

import java.awt.event.*;

public class JGameFrame extends JFrame 
{
	
	protected JPanel p;
	protected JMenuItem newItem,abortItem,exit,options;
	protected GameManager gm;
	protected JOptionsDialog d;
	JMenuBar menubar;
	protected int width,height;
	//protected OptionsDialog d;
	@SuppressWarnings("deprecation")
	public JGameFrame(Applet app,int width,int height) {
	super("Balagan in Paradise");
	gm = (GameManager)app;
	this.width = width;
	this.height = height;
	resize(width+13,height+65);
	setResizable(false);
	final JGameFrame gf=this;
	 try {
//	        UIManager.setLookAndFeel(new net.sourceforge.napkinlaf.NapkinLookAndFeel());
	    } catch (Exception unused) {
	        ; // Ignore exception because we can't do anything.  Will use default.
	    }

	//****************************
	JMenuBar menubar = new JMenuBar();
	// newItem,abortItem are MenuItems
	newItem = new JMenuItem("New Game");
	newItem.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
		gm.newGame();
		newItem.setEnabled(false);
		abortItem.setEnabled(true);
		
	}});
	JMenu m1 = new JMenu("Game");
	JMenu m2 = new JMenu("Options");
	m1.add(newItem);
	abortItem = new JMenuItem("Abort Game");
	abortItem.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			gm.gameOver();
		}
	});
	abortItem.setEnabled(false);
	m1.add(abortItem);
	exit=new JMenuItem("Exit");
	exit.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			setVisible(false);
			gm.stop();
			gm.destroy();
			gm.introsound.stop();
			dispose();	
		}
	});
	m1.add(exit);
	options=new JMenuItem("Change Options...");
	options.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent ae){
		d = new JOptionsDialog(gf,gm);
		d.setVisible(true);	}
	});
	m2.add(options);
	menubar.add(m1);
	menubar.add(m2);
	setJMenuBar(menubar);
	//*******************************
	setCursor(Frame.MOVE_CURSOR);
	p = new JPanel();
	p.setLayout(new FlowLayout(FlowLayout.CENTER));
	p.add(app);
	setLayout(new BorderLayout());
	add("Center",p);

	setVisible(true);
	//show();	depricated method
	
	}
	public void gameOver() {
		abortItem.setEnabled(false);
		newItem.setEnabled(true);
		}
		
			
		}
		