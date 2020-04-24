import java.applet.*;
import java.awt.*;
class OptionsDialog extends Dialog {
Label l[] = new Label[3];
TextField t[] = new TextField[2];
Button b[] = new Button[2];
CheckboxGroup cg = new CheckboxGroup();
Checkbox c[] = new Checkbox[2];
Panel p = new Panel();
GameManager gm;
public OptionsDialog(Frame parent,GameManager gm) {
super(parent,"Balagan in Ramat Gan alpha-Options",true);
this.gm = gm;
setLayout(new GridLayout(4,2,13,13));
l[0] = new Label("Starting Level",Label.LEFT);
l[1] = new Label("Energy Decrement",Label.LEFT);
l[2] = new Label("Sound",Label.LEFT);
t[0] = new TextField(String.valueOf(gm.startLevel),3);
t[1] = new TextField(String.valueOf(gm.energyDec),3);
c[0] = new Checkbox("On",cg,gm.sound);
c[1] = new Checkbox("Off",cg,!gm.sound);
p.setLayout(new FlowLayout(FlowLayout.CENTER,3,3));
p.add(c[0]);
p.add(c[1]);
b[0] = new Button("OK");
b[1] = new Button("Cancel");
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
//handle actions
public boolean action(Event e,Object o) {
if (e.target instanceof Button) {
String str = (String)o;
// if user presses OK
if (str.equals(b[0].getLabel())) {
parseDialog();
}
// else user pressed cancel, so
// don't do anything
setVisible(false);
dispose();
return true;
}
else return false;
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
sound = c[0].getState();
gm.setOptions(start,energy,sound);
}
}