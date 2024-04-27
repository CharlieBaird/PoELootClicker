public class Location {
    public int x;
    public int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Location Add(Location that)
    {
        return new Location(this.x + that.x, this.y + that.y);
    }

    @Override
    public String toString()
    {
        return "[" + x + " " + y + "]";
    }
}