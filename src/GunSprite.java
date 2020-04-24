

import java.awt.Image;
import java.applet.*;

class GunSprite extends BitmapSprite
implements Moveable,Intersect {
	protected GunManager gm; // pointer to manager class
	public GunSprite(Image i, Applet a,GunManager gm) {
	super(i,a);
	this.gm = gm;
	}
// the following methods implement Moveable:
public void setPosition(int x,int y) {
locx = x;
locy = y;
}
public void setVelocity(int x,int y) {
}
public void updatePosition() {
}
// the following methods implement Intersect:
//compare bounding boxes
public boolean intersect(int x1,int y1,int x2,int y2) {
return visible && (x2 >= locx) && (locx+width >= x1)
&& (y2 >= locy) && (locy+height >= y1);
}



// echo to stdout
public void hit() {
System.out.println("HIT!");
gm.handleHit(); // notify manager of hit
}
}