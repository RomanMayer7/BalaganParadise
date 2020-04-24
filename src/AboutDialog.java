import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class AboutDialog extends Dialog implements ActionListener {	
Label name;
Label author;
Button closeButton;

GameManager gm;
public void actionPerformed(ActionEvent ae){
	String str=ae.getActionCommand();
	if(str.equals("OK")){
		this.setVisible(false);
		this.dispose();
	}
		
	}
public AboutDialog(Frame parent,GameManager gm) {
	super(parent,"Balagan in Paradise 1.3 alpha",true);
	this.gm = gm;
	setSize(270,270);
	setLayout(new GridLayout(4,2,13,13));
	name=new Label("Balagan in Paradise 1.3 alpha",Label.LEFT);
	author=new Label("development by Roman Mayerson 2009,July",Label.LEFT);
	add(name);
	add(author);
closeButton=new Button("OK");
closeButton.addActionListener(this);
add(closeButton);
}
}