import java.awt.*;
import java.awt.event.InputEvent;

public class PoEBot extends Robot {
    public PoEBot() throws AWTException
    {
        super();
    }

    public void mouseMove(Location loc)
    {
        mouseMove(loc, 1000);
    }

    public void mouseMove(Location loc, int n)
    {
        Point currentLoc = MouseInfo.getPointerInfo().getLocation();
        int time = (int) (distance(currentLoc, loc) * 0.6);
        mouseGlide(currentLoc.x, currentLoc.y, loc.x, loc.y, time, n);
    }

    private double distance(Point one, Location two)
    {
        return Math.sqrt((two.x-one.x)*(two.x-one.x) + (two.y-one.y)*(two.y-one.y));
    }

    public void mouseGlide(int x1, int y1, int x2, int y2, int t, int n) {
        try {
            Robot r = new Robot();
            double dx = (x2 - x1) / ((double) n);
            double dy = (y2 - y1) / ((double) n);
            double dt = t / ((double) n);
            for (int step = 1; step <= n; step++) {
                Thread.sleep(1);
                r.mouseMove((int) (x1 + dx * step), (int) (y1 + dy * step));
            }
        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Color getPixelColor(Location loc)
    {
        if (loc != null)
            return this.getPixelColor(loc.x, loc.y);

        return null;
    }

    public void leftClick()
    {
        this.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        delay(30);
        this.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        delay(30);
    }

    public void rightClick()
    {
        this.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        delay(30);
        this.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        delay(30);
    }
}
