import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.*;
import javax.swing.*;
public class JOptionsDialog extends JDialog{
	JLabel l[] = new JLabel[3];
	JTextField t[] = new JTextField[2];
	JButton b[] = new JButton[2];
	//JCheckboxGroup cg = new JCheckboxGroup();
	JCheckBox c[] = new JCheckBox[2];
	JPanel p = new JPanel();
	GameManager gm;
	public JOptionsDialog(Frame parent,GameManager gm) {
	super(parent,"Balagan in Ramat Gan alpha-Options",true);
	this.gm = gm;
	setLayout(new GridLayout(4,2,13,13));
	l[0] = new JLabel("Starting Level",JLabel.LEFT);
	l[1] = new JLabel("Energy Decrement",JLabel.LEFT);
	l[2] = new JLabel("Sound",JLabel.LEFT);
	t[0] = new JTextField(String.valueOf(gm.startLevel),3);
	t[1] = new JTextField(String.valueOf(gm.energyDec),3);
	c[0] = new JCheckBox("On",gm.sound);
	c[1] = new JCheckBox("Off",!gm.sound);
	p.setLayout(new FlowLayout(FlowLayout.CENTER,3,3));
	p.add(c[0]);
	p.add(c[1]);
	b[0] = new JButton("OK");
	b[0].addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			parseDialog();
			
		}
	});
	b[1] = new JButton("Cancel");
	b[1].addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			setVisible(false);
			dispose();
		}
	});
	add(l[0]);
	add(t[0]);
	add(l[1]);
	add(t[1]);
	add(l[2]);
	add(p);
	add(b[0]);
	add(b[1]);
	pack();
	}
	
	protected void parseDialog() {
	int start = -1,energy = -1;
	boolean sound;
	try {
	start = Integer.parseInt(t[0].getText());
	}
	catch (Exception exc) {
	}
	try {
	energy = Integer.parseInt(t[1].getText());
	}
	catch (Exception exc) {
	}
	sound = c[0].isSelected();
	gm.setOptions(start,energy,sound);

}}
