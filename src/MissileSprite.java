
import java.awt.*;
import java.applet.*;

class MissileSprite extends RectSprite {
	protected int vy; // velocity in y coordinate
	protected int start_y; // starting y coord
	protected int stop_y; // stop at y coord
	Intersect target[];
	Intersect target2[];
	Intersect onetarget;
	AudioClip missleShotsound;
	int Constructor;//constructor ID
	public MissileSprite(int w,int h,Color c,int vy,
	int start_y,int stop_y,
	Intersect target[],AudioClip missleShotsound) {
	super(w,h,c);
	setFill(true); // fill rect sprite
	this.vy = vy; // initialize speed
	this.start_y = start_y; // initialize starting point
	this.stop_y = stop_y; // initialize stopping point
	this.target = target; // initialize targets
	this.missleShotsound=missleShotsound;//initializing shot sound
	Constructor=1;
	suspend();
	}
	//*********************Constructor for one target(player's ship)
	public MissileSprite(int w,int h,Color c,int vy,
			int start_y,int stop_y,
			Intersect target,AudioClip missleShotsound) {
			super(w,h,c);
			setFill(true); // fill rect sprite
			this.vy = vy; // initialize speed
			this.start_y = start_y; // initialize starting point
			this.stop_y = stop_y; // initialize stopping point
			this.onetarget = target; // initialize targets
			this.missleShotsound=missleShotsound;//initializing shot sound
			suspend();
			Constructor=2;
			}
	//******************************** Constructor for two target array's
	public MissileSprite(int w,int h,Color c,int vy,
			int start_y,int stop_y,
			Intersect target[],Intersect target2[],AudioClip missleShotsound) {
			super(w,h,c);
			setFill(true); // fill rect sprite
			this.vy = vy; // initialize speed
			this.start_y = start_y; // initialize starting point
			this.stop_y = stop_y; // initialize stopping point
			this.target = target; // initialize targets
			this.target2=target2;//initialize second target's array
			this.missleShotsound=missleShotsound;//initializing shot sound
			Constructor=3;
			suspend();
			}
	
	// start the missile at the given x coordinate
	public void init(int x) {
	missleShotsound.play();
	locx = x;
	locy = start_y;
	restore();
	
	}
	public void changeoneTarget(Intersect target){
	onetarget=target;	
	}
	public void update() {
	if ((active)&&((target!=null)||(onetarget!=null))) {
	// move missile
	locy += vy;
	if(vy<0){
	if (locy < stop_y) {
		suspend();}
		}
	if(vy>0){
		if (locy > stop_y) {
			suspend();}
			}
	
		// else if missile hits target, suspend it
		else {
	    if (Constructor==1){
		for (int i=0; i<target.length; i++) {
		if (target[i].intersect(locx,locy,
		locx+width,locy+height)) {
		target[i].hit(); // tell target it's been hit
		suspend();
		break;
		}
		}
		}
	    else if(Constructor==2){
	    	if (onetarget.intersect(locx,locy,
	    			locx+width,locy+height)) {
	    		System.out.println("WALLAK");
	    			onetarget.hit(); // tell target it's been hit
	    			suspend();
	    	}
	    }
	    else  if (Constructor==3){
			for (int i=0; i<target.length; i++) {
				if (target[i].intersect(locx,locy,
				locx+width,locy+height)) {
				target[i].hit(); // tell target it's been hit
				suspend();}
				if (target2[i].intersect(locx,locy,
						locx+width,locy+height)) {
						target2[i].hit(); // tell target it's been hit
				suspend();
				break;
				}
				}
				}
		}
		}
		}
	}