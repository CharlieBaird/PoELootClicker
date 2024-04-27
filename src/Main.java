import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Main {

    private static KeyListener keyListener;
    private static PoEBot robot;

    static int width = 1920;
    static int height = 1080;
    static ArrayList<Location> spiralPoints;

    static Color ITEM_PURPLE = new Color(61, 0, 77);
    static Color ITEM_PURPLE_HIGHLIGHTED = new Color(106, 59, 122);

    public static void main(String[] args)
    {
        try {
            robot = new PoEBot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

        keyListener = new KeyListener();
        keyListener.startListener();

//        mouseListener = new

        spiralPoints = getSpiralPoints(); // Center (0,0), 10 spiral loops
    }

    public static void CPressed() {
        long startTime = System.nanoTime();

        BufferedImage image = robot.createScreenCapture(new Rectangle(0,0,1920,1080));

        long endTime = System.nanoTime();
        printElapsed(startTime, endTime);

        Location foundLoc = null;

        for (Location spiralPoint : spiralPoints)
        {
            int rgb = image.getRGB(spiralPoint.x, spiralPoint.y);
            Color color = new Color(rgb);

            if (color.equals(ITEM_PURPLE) || color.equals(ITEM_PURPLE_HIGHLIGHTED)) {
                System.out.println("Found at " + spiralPoint.x + ", " + spiralPoint.y);
                foundLoc = spiralPoint;
                break;
            }
        }

        endTime = System.nanoTime();
//        printElapsed(startTime, endTime);

        if (foundLoc == null)
        {
            return;
        }

        robot.mouseMove(foundLoc, 40);

        Point currentLoc = MouseInfo.getPointerInfo().getLocation();
        Color color = robot.getPixelColor(currentLoc.x, currentLoc.y);;

        if (!color.equals(ITEM_PURPLE_HIGHLIGHTED)) {
            CPressed();
            return;
        }

        robot.leftClick();

    }

    public static ArrayList<Location> getSpiralPoints() {
        ArrayList<Location> points = new ArrayList<>();

        int X = 80;
        int Y = 46;

        int x=0, y=0, dx = 0, dy = -1;
        int t = Math.max(X,Y);
        int maxI = t*t;

        for (int i=0; i < maxI; i++){
            if ((-X/2 <= x) && (x <= X/2) && (-Y/2 <= y) && (y <= Y/2)) {

                int xLoc = x*23 + width/2;
                int yLoc = y*23 + height/2;
//                System.out.println(xLoc + "," + yLoc);
                points.add(new Location(xLoc, yLoc));
            }

            if( (x == y) || ((x < 0) && (x == -y)) || ((x > 0) && (x == 1-y))) {
                t=dx; dx=-dy; dy=t;
            }
            x+=dx; y+=dy;
        }

        return points;
    }

    private static void printElapsed(long startTime, long endTime) {
        long durationInMilliseconds = (endTime - startTime) / 1000000; // Convert nanoseconds to milliseconds
        System.out.println("Time taken: " + durationInMilliseconds + " milliseconds");

    }
}