
import java.awt.*;
import java.applet.*;
import java.applet.*;
public class UFOManager {
	static int width, height; // applet dimensions
	private UFO ufo[];
	private UFOgun ufo2[];
	int ufosKilled; // count ufos killed
	int level; // the level
	// (i.e. #ufos on screen)
	// kill 13 ufos until next level
	int startLevel;
	int maxLevel;
	static final int KILL_FOR_NEXT_LEVEL = 13;
	boolean playSound=true;
	static final int NUM_UFOS = 9;
	AudioClip expsound;
	AudioClip congratulis;
	AudioClip shotsound;
	public UFOManager(int startLevel,int maxLevel,int width,int height,
	Image ufoImages[],Image attackImages[],
	Image explodeImages[],Image ufo_gun[],Image ufo_gun_atck[],Applet a,AudioClip exp,AudioClip cong,AudioClip shotsound) {
	this.width = width;
	this.height = height;
	this.startLevel = startLevel;
	this.maxLevel = maxLevel;
	this.shotsound=shotsound;
	ufo = new UFO[NUM_UFOS];
	ufo2 = new UFOgun[NUM_UFOS];
	for (int i=0; i<ufo.length; i++) {
	ufo[i] = new UFO(ufoImages,attackImages,explodeImages,width,height,this,a);}
	for (int i=0; i<ufo2.length; i++) {
	ufo2[i] = new UFOgun(ufo_gun,ufo_gun_atck,explodeImages,width,height,this,a,shotsound);}
	expsound = exp;
	congratulis=cong;
	newGame();
	}
	// allow the UFO class to communicate with the gun
	public void initialize(GunManager gm) {
		UFO.initialize(gm);
		UFOgun.initialize(gm);
		}
	// set ufo at a random screen location
	private void initializePosition(Moveable m) {
		m.setPosition(UFO.getRand(width - 100)+50,
		UFO.getRand(height - 150)+10);
		
		}
	// accessor method, so the missile knows where
	// the targets are!
	public UFO[] getUFO() {
	return ufo;
	}
	public UFOgun[] getUFO2(){
		return ufo2;
	}
	// This method tells the UFO class where
	// the gun is (so the UFOs know if they’ve
	// collided with it)
	    public void newGame() {
		ufosKilled = 0;
		level = 2; // start with 2 ufos
		// on the screen
		for (int i=0; i<ufo.length; i++) {
		initializePosition(ufo[i]);
		initializePosition(ufo2[i]);
		if (i >= level) { // suspend ufos
		// above start level
		ufo[i].suspend();
		ufo2[i].suspend();
		}
		}
		
		}
	// tracks the number of ufos killed. If the
	// num_killed is divisible by KILL_FOR_NEXT_LEVEL
	// increment the level
	public void killed() {
	ufosKilled++;
	if (ufosKilled % KILL_FOR_NEXT_LEVEL == 0) {
	level = (level == NUM_UFOS) ? NUM_UFOS : level+1;
	}
	}

	public void setStartLevel(int start) {
		startLevel = start;
		}
	public void setSound(boolean s) {
		playSound = s;
		}
	public void playExplosion() {
		if (playSound && expsound != null) {
		expsound.play();
		if (ufosKilled%7==0){congratulis.play();}//if killing 7 ufos>congratulations
		}}
	
		
		
		// paint all ufos in a level
		public void paint(Graphics g) {
		for (int i=0; i<level; i++) {
		ufo[i].paint(g);
		ufo2[i].paint(g);
		}
		}
		// update all ufos in a level. Otherwise start
		// ufo if it's not on screen
		public void update() {
		for (int i=0; i<level; i++) {
		if (ufo[i].isActive()) {
		ufo[i].update();
		}
		else { // make new ufo
		initializePosition(ufo[i]);
		ufo[i].init();
		}
		}
		for (int i=0; i<level; i++) {
			if (ufo2[i].isActive()) {
			ufo2[i].update();
			}
			else { // make new ufo
			initializePosition(ufo2[i]);
			ufo2[i].init();
			}
			}
		}
		}
