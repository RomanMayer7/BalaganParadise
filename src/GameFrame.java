import java.awt.*;
import java.applet.*;
public class GameFrame extends Frame{
	protected Panel p;
	protected MenuItem newItem,abortItem,aboutItem;
	protected GameManager gm;
	protected OptionsDialog d;
	protected AboutDialog ad;
	MenuBar menubar;
	protected int width,height;
	//protected OptionsDialog d;
	@SuppressWarnings("deprecation")
	public GameFrame(Applet app,int width,int height) {
	super("Balagan in Paradise");
	gm = (GameManager)app;
	this.width = width;
	this.height = height;
	resize(width+13,height+65);
	setResizable(false);
	//****************************
	MenuBar menubar = new MenuBar();
	// newItem,abortItem are MenuItems
	newItem = new MenuItem("New Game");
	aboutItem=new MenuItem("About");
	Menu m1 = new Menu("Game");
	Menu m2 = new Menu("Options");
	m1.add(newItem);
	m1.add(aboutItem);
	abortItem = new MenuItem("Abort Game");
	abortItem.setEnabled(false);
	m1.add(abortItem);
	m1.add(new MenuItem("Exit"));
	m2.add(new MenuItem("Change Options..."));
	
	menubar.add(m1);
	menubar.add(m2);
	setMenuBar(menubar);
	//*******************************
	setCursor(Frame.MOVE_CURSOR);
	p = new Panel();
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
		// handle actions
		public boolean action(Event e,Object o) {
		if (e.target instanceof MenuItem) {
		String s = (String)o;
		if (e.target == newItem) {	
		gm.newGame();
		newItem.setEnabled(false);
		abortItem.setEnabled(true);
		}
		else if (e.target == abortItem) {
		gm.gameOver();
		
		}
		else if (s.equals("Exit")) {
		setVisible(false);
		gm.stop();
		gm.destroy();
		gm.introsound.stop();
		dispose();
		}
		else if (s.equals("Change Options...")) {
		d = new OptionsDialog(this,gm);
		d.setVisible(true);}
		else if (s.equals("About")){
			ad=new AboutDialog(this,gm);
			ad.setVisible(true);
		}
		return true;
		}
		else return false;
		}
		}


