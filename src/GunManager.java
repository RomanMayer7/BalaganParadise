
import java.awt.*;
import java.applet.*;
import java.applet.*;

public class GunManager {
	private GunSprite gun; // your gun
	private int gun_width; // width of gun
	private int gun_height;
	private MissileSprite missile[]=new MissileSprite[3]; // missile
	static int width, height; // applet dimensions
	static GameManager game;
	private int min_x,max_x; // min and max x coords
	// for gun movement
	private int gun_min_x,gun_max_x;
	private int mis_min_x,mis_max_x;
	private int gun_y;
	private boolean displayHit;
	private int energy;
	private int maxEnergy;
	private int energyDec;
	static final int MISSILE_WIDTH = 3;
	static final int MISSILE_HEIGHT = 27;
	static final int MISSILE_SPEED = -27; // missile flies upward
	static final Color MISSILE_COLOR= Color.red;
	AudioClip shotsound;
	public GunManager(int maxEnergy,int energyDec,int width,int height,
			Image gunImage,AudioClip shotsound,Intersect target[],Intersect target2[],Applet a) {
			this.maxEnergy = maxEnergy;
			this.energyDec = energyDec;
	this.width = width;
	this.height = height;
	this.shotsound=shotsound;                      
	game=(GameManager)a;
	gun = new GunSprite(gunImage,a,this);
	gun_width = gunImage.getWidth(a)/2;
	gun_height = gunImage.getHeight(a);
	gun_y = height - gun_height;
	min_x = gun_width;
	max_x = width - gun_width;
	gun_min_x = 0;
	gun_max_x = width - 2*gun_width;
	mis_min_x = min_x-2;
	mis_max_x = max_x-2;
	gun.setPosition(width/2-gun_width,gun_y);
	for (int i=0;i<3;i++){
	missile[i] = new MissileSprite(MISSILE_WIDTH,MISSILE_HEIGHT,
	MISSILE_COLOR,MISSILE_SPEED,
	height-gun_height,
	0,target,target2,shotsound);}}
	public void newGame() {
		gun.setPosition(width/2-gun_width,gun_y);
		gun.restore();
		displayHit = false;
		energy = maxEnergy;
		}
	// move gun to the given x coordinate
	public void moveGun(int x) {
	if (x <= min_x) {
	gun.setPosition(gun_min_x,gun_y);
	}
	else if (x >= max_x) {
	gun.setPosition(gun_max_x,gun_y);
	}
	else {
	gun.setPosition(x-gun_width,gun_y);
	}
	}
	// fire missile from given x coordinate
	public void fireMissile(int x) {
	if (!missile[0].isActive()) { // if missile sprite
	// isn't active
	if (x <= min_x) {
	missile[0].init(mis_min_x);
	}
	else if (x >= max_x) {
	missile[0].init(mis_max_x);
	}
	else {
	missile[0].init(x-2); // initialize missile
	}
	}
	else if (!missile[1].isActive()) { // if missile sprite
		// isn't active
		if (x <= min_x) {
		missile[1].init(mis_min_x);
		}
		else if (x >= max_x) {
		missile[1].init(mis_max_x);
		}
		else {
		missile[1].init(x-2); // initialize missile
		}
		}
	else if (!missile[2].isActive()) { // if missile sprite
		// isn't active
		if (x <= min_x) {
		missile[2].init(mis_min_x);
		}
		else if (x >= max_x) {
		missile[2].init(mis_max_x);
		}
		else {
		missile[2].init(x-2); // initialize missile
		}
		}
	}
	// handles a hit from an alien
	public void handleHit() {
	displayHit = true; // set display hit flag
	energy -= energyDec; // update energy
	if (energy<= 0) { // game over if energy <= 0
	game.gameOver(); // notify game manager
	gun.suspend(); // turn off sprites
	for(int i=0;i<missile.length;i++){
	missile[i].suspend();
	}
	}
	}
	
	// update all the parameters associated with the
	// gun. In this case, only the missile needs to move
	// automatically. Also the gun manager checks if the
	// missile hits anything
	public void update() {
		for(int i=0;i<missile.length;i++){
	missile[i].update();
		}
	}
	// paint all sprites associated with gun
	// also paint status display for amount of energy left
	String energyString = "Energy";
	public void paint(Graphics g) {
		// if gun is hit, flash a red rectangle
		// instead of painting gun
		if (displayHit) {
		g.setColor(Color.red);
		g.fillRect(0,gun_y,width,gun_height);
		displayHit = false;
		}
		else {
		gun.paint(g);
		}
		for(int i=0;i<missile.length;i++){
		missile[i].paint(g);}
		// display energy left
		g.setColor(Color.red);
		g.drawString(energyString,3,13);
		g.fillRect(0,17,energy,10);
		}
	// accessor function for gun
	public GunSprite getGun() {
	return gun;
	}
	public int getGunY() {
	return gun_y;
	}
	public int getGunX(){
		return gun.locx;
	}
	public void setEnergyDec(int energyDec) {
		this.energyDec=energyDec;
	}
	}
	