
import java.awt.*;
import java.applet.*;

public class UFO extends BitmapLoop implements Intersect {
	byte state;
	// UFO states
	static final byte STANDBY = 0;
	static final byte ATTACK = 1;
	static final byte RETREAT = 2;
	static final byte LAND = 3;
	static final byte EXPLODE = 4;
	// probability of state transitions
	static final double STANDBY_EXIT = .95;
	static final double ATTACK_EXIT = .95;
	static final double RETREAT_EXIT = .95;
	static final double LAND_EXIT = .95;
	static final double FLIP_X = 0.9;
	static final int RETREAT_Y = 17;
	//UFO state Image arrays
	protected Image ufo[];
	protected Image attack[];
	protected Image explode[];
	int explosion_counter;
	UFOManager um;
	int max_x, max_y; // max coords of this UFO
	static Intersect target; // refers to the gun
	static int gun_y; // the y-coord of gun
	static GameManager game; // ptr to game manager
	public UFO(Image ufoImages[],Image attackImages[],
			Image explodeImages[],int max_x,int max_y,UFOManager um,
	Applet a) {
	super(0,0,null,ufoImages,a);
	this.max_x = max_x;
	this.max_y = max_y;
	this.um=um;
	game = (GameManager)a;
	ufo = ufoImages;
	attack = attackImages;
	explode = explodeImages;
	currentImage = getRand(5); // start at random image
	startStandby();
	}
	public UFO(){
		//*****************************
	}
	// finish initializing info about the player's gun
	static public void initialize(GunManager gm) {
	target = gm.getGun(); // refers to gun sprite
	gun_y = gm.getGunY(); // get gun y-coordinate
	}
	// implement Intersect interface:
	public boolean intersect(int x1,int y1,int x2,int y2) {
	return visible && (x2 >= locx) && (locx+width >= x1)
	&& (y2 >= locy) && (locy+height >= y1);
	}
	// this is called if a missile hits the alien
	public void hit() {
	// alien is invulnerable when it's attacking
	// but it gets "pushed back"
	if (state == ATTACK) {
	locy -= 37;
	}
	// otherwise explode!
	else if (state != EXPLODE) {
	startExplode(); // start explode state
	um.killed(); // tell UFOManager
	game.incrementScore(); // add to score
	// another UFO's dead
	}
	}
	// set state and images loop
	public void init() {
		startStandby();
		images = ufo;
		restore();
		}
	// this implements the state machine
	public void update() {
	// if alien hits target
	if ((locy + height >= gun_y) &&
	target.intersect(locx,locy,locx+width,locy+height)) {
	target.hit();
	suspend();
	return;
	}
	// otherwise, update alien state
	double r1 = Math.random(); // pick random nums
	double r2 = Math.random();
	switch (state) {
	case STANDBY:
	if (r1 > STANDBY_EXIT) {
	if (r2 > 0.5) {
	startAttack();
	}
	else {
	startLand();
	}
	}
	// else change the direction by flipping
	// the x-velocity. Net result: ufo moves
	// from side to side. And if the ufo gets close to
	// the left or right side of screen, it always changes
	// direction.
	else if ((locx < width) || (locx > max_x - width) ||
	(r2 > FLIP_X)) {
	vx = -vx;
	}
	break;
	case ATTACK:
	// retreat if the alien flies too close to
	// the ground
	if ((r1 > ATTACK_EXIT) || (locy > gun_y - 17)) {
	startRetreat();
	}
	// flip x-direction if it gets too close to edges
	else if ((locx < width) || (locx > max_x - width) ||
	(r2 > FLIP_X)) {
	vx = -vx;
	}
	break;
	case RETREAT:
	if (r1 > RETREAT_EXIT) {
	if (r2 > 0.5) {
	startAttack();
	}
	else {
	startStandby();
	}
	}
	// stop retreat if ufo goes too close
	// to top of screen
	else if (locy < RETREAT_Y) {
	startStandby();
	}
	break;
	case LAND:
		if (r1 > LAND_EXIT) {
		startStandby();
		}
		else if (locy >= max_y-height) {
		landingRoutine();
		}
		break;
	case EXPLODE:
		explosion_counter++; // bump counter
		// suspend once animation
		// is finished
		if (explosion_counter == explode.length) {
			suspend();
		}
		break;
		}
	
	super.update(); // BitmapLoop update draws the
	// appropriate image
	}
	protected void landingRoutine() {
		game.alienLanded(); // tell game manager that
		// the UFO's landed
		suspend();
		}
		protected void startStandby() {
		vx = getRand(8)-4 ;
		vy = 0;
		state = STANDBY;
		}
		// start attack state
		protected void startAttack() {
		vx = getRand(10)-5;
		vy = getRand(5)+7;
		images = attack; // change to attack animation loop
		state = ATTACK;
		}
		// start retreating state
		protected void startRetreat() {
		vx = 0;
		vy = -getRand(3) - 2;
		images = ufo; // change to usual animation loop
		state = RETREAT;
		}
		protected void startLand() {
		vx = 0;
		vy = getRand(3) + 2;
		state = LAND;
		}
		// start explosion state
		protected void startExplode() {
		images = explode; // set bitmap to explosion sequence
		currentImage = 0; // start at beginning of animation
		explosion_counter = 0; // count the number of frames
		um.playExplosion(); // play explosion sound:
		// (um is reference to the
		// UFOManager class)
		state = EXPLODE;
		}
		static public int getRand(int x) {
		return (int)(x * Math.random());
		}
		}