

import java.awt.*;
import java.applet.*;
	class BitmapSprite extends Sprite {
		protected int locx;
		protected int locy;
		// image dimensions
		protected int width,height;
		protected Image image; // the bitmap
		protected Applet applet; // the parent applet
		public BitmapSprite(Image i,Applet a) {
		locx = 0;
		locy = 0;
		image = i;
		applet = a;
		if (image != null) {
		width = image.getWidth(a); // get size of background
		height = image.getHeight(a);
		}
		restore();
		}
		public BitmapSprite(int x,int y,Image i,Applet a) {
		locx = x;
		locy = y;
		image = i;
		applet = a;
		if (image != null) {
		width = image.getWidth(a); // get size of background
		height = image.getHeight(a);
		}
		restore();
		}
		public BitmapSprite(){
			//******************************
		}
		public void setSize(int w,int h) {
		width = w;
		height = h;
		}
		public void update() {
			
		}

		// do nothing
	
	public void paint(Graphics g) {
	if (visible) {
	g.drawImage(image,locx,locy,applet);
	}
	}}
	
