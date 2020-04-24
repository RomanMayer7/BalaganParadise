
import java.awt.*;
abstract class Sprite {
protected boolean visible; // is sprite visible
protected boolean active; // is sprite updateable
// abstract methods:
abstract void paint (Graphics g);
abstract void update();
// accessor methods:
public boolean isVisible() {
return visible;
}
public void setVisible(boolean b) {
	visible = b;
}
public boolean isActive() {
return active;
}
public void setActive(boolean b) {
active = b;
}
// suspend the sprite
public void suspend() {
setVisible(false);
setActive(false);
}
// restore the sprite
public void restore() {
setVisible(true);
setActive(true);
}}