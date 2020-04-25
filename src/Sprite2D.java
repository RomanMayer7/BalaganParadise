import java.awt.*;

abstract class Sprite2D extends Sprite
{
  protected int locx;
  protected int locy;
  Color color;
  boolean fill;
  public boolean getFill()
  {
    return fill;
  }
  public void setFill(boolean b)
  {
    fill = b;
  }
  public void setColor(Color c) 
  {
   color = c;
  }
  public Color getColor()
  {
   return color;
  }
}