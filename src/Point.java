public class Point {
    private int x;
    private int y;

    private boolean outside = false;

//    private boolean checked = false;
    public boolean getOutside() {
        return outside;
    }

    public void setOutside(boolean outside) {
        this.outside = outside;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
