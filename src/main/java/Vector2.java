import org.lwjgl.opengl.GL11;

public class Vector2 {
    private double x, y;

    public Vector2(){
    }
    public Vector2(double x, double y){
        this.x = x; this.y = y;
    }
    public double getX(){return x;}
    public double getY(){return y;}
    public Vector2 setX(double x){this.x = x; return this;}
    public Vector2 setY(double y){this.y = y; return this;}


    public Vector2 mul(double a){
        return new Vector2(x * a, y * a);
    }
    public Vector2 mul(double _x, double _y){
        return new Vector2(x * _x, y * _y);
    }

    public Vector2 sub(Vector2 vec){
        return new Vector2(x - vec.getX(), y - vec.getY());
    }

    public Vector2 sub(double _x, double _y){
        return new Vector2(x - _x, y - _y);
    }
    public Vector2 add(Vector2 vec){
        return new Vector2(x + vec.getX(), y + vec.getY());
    }

    public Vector2 add(double _x, double _y){
        return new Vector2(x + _x, y + _y);
    }
    public Vector2 getNormalized(){
        final double dist = Math.sqrt(x * x + y * y);
        return new Vector2(x / dist, y / dist);
    }

    public static double distance(Vector2 vec1, Vector2 vec2){
        return Math.sqrt(Math.pow(vec2.getX() - vec1.getX(), 2) + Math.pow(vec2.getY() - vec1.getY(), 2));

    }

    public void vertex(){
        GL11.glVertex2d(x, y);
    }

    public boolean isOutsideOf(double x1, double y1, double x2, double y2){
        if(x < x1 || y < y1 || x > x2 || y > y2 ) return true;
        return false;

    }

    public boolean hasPointRectCollided(Vector2 rectPos, Vector2 rectSize){
        double  px = x, py = y,
                rx = rectPos.getX(), ry = rectPos.getY(),
                rw = rectSize.getX(), rh = rectSize.getY();

        return px > rx && py > ry &&
                px < rx + rw &&
                py < ry + rh;
    }

    public Vector2 lerp(Vector2 vec2, float by){
        return new Vector2(
                x * (1 - by) + vec2.getX() * by,
                y * (1 - by) + vec2.getY() * by
        );
    }

    @Override
    public String toString() {
        return "Vector2(" + x + ", " + y + ')';
    }
}
