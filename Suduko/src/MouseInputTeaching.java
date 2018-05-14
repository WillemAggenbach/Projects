import java.awt.Color;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdIn;

public class MouseInputTeaching
{
  public static void main(String[] args)
  {
    StdDraw.text(0.5,0.5,"( 0.00, 0.00)");
    
    int fadeColor = 255;
    // loop forever
    while(true)
    {
      // slowly change fadeColor back to white
      if(fadeColor < 255){
        fadeColor = fadeColor + 5;
      }
      // make sure colour value is valid
      if(fadeColor > 255){
        fadeColor = 255;
      }
      
      // clear whole screen back to white
      StdDraw.clear();
      // get location of current mouse position
      double x = StdDraw.mouseX();
      double y = StdDraw.mouseY();
      // draw text for current mouse location
      String location = "("+String.format("%5.2f", x)+","+String.format("%5.2f", y)+")";
      StdDraw.text(0.5,0.5,location);
      
      // if a mouse click was detected
      if(StdDraw.mousePressed())
      {
        // set fadeColor back to black
        fadeColor = 0;
        // resets the value returned by mouseReleased() back to false
        StdDraw.mousePressed();
      }
      
      // get location of last mouse click
      x = StdDraw.mouseX();
      y = StdDraw.mouseY();
      // draw text for location of last mouse click
      location = "("+String.format("%5.2f", x)+","+String.format("%5.2f", y)+")";
      StdDraw.setPenColor(new Color(fadeColor,fadeColor,fadeColor)); // might seem odd
      StdDraw.text(0.5,0.25,"Mouse click detected at "+location);
      StdDraw.setPenColor(); // reset pen colour back to black
      
      // show drawing for 20 milliseconds before going further
      StdDraw.show(20); // warning: using show() like this changes StdDraw to animation mode
    }
    
  }
}