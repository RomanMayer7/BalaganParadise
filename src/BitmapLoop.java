
import java.awt.*;
import java.applet.*;

class BitmapLoop extends BitmapSprite implements Moveable{
	protected Image images[]; // sequence of bitmaps
	protected int currentImage; // the current bitmap
	protected boolean foreground; // are there foreground images?
	protected boolean background; // is there background image?
	// constructor. Assumes that background image is already
	// loaded. (use MediaTracker)
	public BitmapLoop(int x,int y,Image b,Image f[],Applet a) {
	super(x,y,b,a);
	if (image != null) { // if there's a background image
		background = true;
		}
		else {
		background = false;
		}
		images = f;
		currentImage = 0;
		if (images == null || images.length == 0) {
		foreground = false; // nothing in images[]
		}
		else {
		foreground = true;
		if (!background) { // if no background
		width = images[0].getWidth(a); // get size of images[0]
		height = images[0].getHeight(a);
		}
		}
		}
	public BitmapLoop(){
		//***************************
	}
		
		// cycle currentImage if sprite is active, and there
		// are foreground images
		public void update() {
		if (active && foreground) {
		currentImage = (currentImage + 1) % images.length;
		}
		updatePosition();
		}
		public void paint(Graphics g) {
		if (visible) {
		if (background) {
		g.drawImage(image,locx,locy,applet);
		}
		if (foreground) {
		g.drawImage(images[currentImage],locx,locy,applet);
		}
		}
		}
		// implement moveable interface
		public void setPosition(int x,int y) {
		locx = x;
		locy = y;
		}
		protected int vx;
		protected int vy;
		public void setVelocity(int x,int y) {
		vx = x;
		vy = y;
		}
		// update position according to velocity
		public void updatePosition() {
		locx += vx;
		locy += vy;
		}
		}