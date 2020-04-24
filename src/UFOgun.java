import java.applet.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class UFOgun extends UFO {
	private int energyDec;
	private int mis_min_x,mis_max_x;
	static final int MISSILE_WIDTH = 3;
	static final int MISSILE_HEIGHT = 27;
	static final int MISSILE_SPEED = 27; // missile flies downward(in player direction)
	static final Color MISSILE_COLOR= Color.green;
	
	
	// probability of state transitions
	static final double STANDBY_EXIT = .95;
	static final double ATTACK_EXIT = .99;
	static final double RETREAT_EXIT = .95;
	static final double LAND_EXIT = .95;
	static final double FLIP_X = 0.9;
	static final int RETREAT_Y = 17;
	int targetchange=0;
	
	int UFOgun_height;
	MissileSprite missile;
	AudioClip shotsound;
	int gunSpriteCoord;//the x coordinate of the players ship
	//UFO state Image arrays
	protected Image ufo[];
	protected Image attack[];
	protected Image explode[];
	int explosion_counter;
	UFOManager um;
	int max_x, max_y; // max coords of this UFO
	private int min_x=0; // min  coords
	static Intersect target; // refers to the gun
	static int gun_y; // the y-coord of gun
	static GameManager game; // ptr to game manager
    public UFOgun (Image ufoImages[],Image attackImages[],
			Image explodeImages[],int max_x,int max_y,UFOManager um,
			Applet a,AudioClip shotsound){
        super( ufoImages,attackImages,
    			 explodeImages,max_x, max_y, um,
    			a);
        
    	this.max_x = max_x;
    	this.max_y = max_y;
    	mis_min_x = min_x-2;
    	mis_max_x = max_x-2;
    	this.um=um;
    	game = (GameManager)a;
    	
    	ufo = ufoImages;
    	attack = attackImages;
    	explode = explodeImages;
    	this.shotsound=shotsound;
    	currentImage = getRand(5); // start at random image
    	startStandby();
    	UFOgun_height = attack[0].getHeight(a);
  
    

	target=new UFO();
	gunSpriteCoord=0;
	missile = new MissileSprite(MISSILE_WIDTH,MISSILE_HEIGHT,
			MISSILE_COLOR,MISSILE_SPEED,
			locy,
			600,target,shotsound);
	
    }

//fire missile from given x coordinate
    public void fireMissile(int x) {
    	if (!missile.isActive()) { // if missile sprite
    	// isn't active
    	if (x <= min_x) {
    	missile.init(mis_min_x);
    	}
    	else if (x >= max_x) {
    	missile.init(mis_max_x);
    	}
    	else {
    	missile.init(x-2); // initialize missile
    	}
    	}
    	}
    
    public void update() {
    	//System.out.println("missle active:"+missile.isActive());
    	//System.out.println("the UFOgun locx and locy"+locx+" "+locy);
       	//System.out.println("missle locx and locy:"+missile.locx+" "+missile.locy);
       	//System.out.println("the ufo's target is:"+target);
       	//System.out.println("the missle target is:"+missile.onetarget);
       	//System.out.println("The missle constructor used in UFOgun is:"+missile.Constructor);
       	
    	
       	
    	//missile.update();
    	 if(targetchange==0){    //while GunManager initialized already- we have the target
    	 target=game.gm.getGun();
    	 missile.changeoneTarget(target);//switch to real target
    	 gunSpriteCoord=game.gm.getGun().locx;}
    	 targetchange=1;
    	 if (targetchange==1){
    		if (missile.visible){ missile.update();}
    	 }
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
    	missile.update();
    	}
protected void startAttack() {
	if(locx <= gunSpriteCoord)
	{
	 vx=8;
	 vy=getRand(5)+7;
	 if((missile.active==false)&&(game.StartMsg<=40)){
	 		fireMissile(locx);//fires missile in the players direction	} 
	}
	
 	}
	else {
	 vx=-8;
	 vy=getRand(5)+7;
	 if((missile.active==false)&&(game.StartMsg<=40)){
 		fireMissile(locx);//fires missile in the players direction	
 	}
	}
	images = attack; // change to attack animation loop
	state = ATTACK;

}
public void paint(Graphics g){
super.paint(g);	
missile.paint(g);	
}

}