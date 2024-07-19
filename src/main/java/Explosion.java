public class Explosion {
    private Vector2 position;
    private int size, maxSize;

    public Explosion(Vector2 pos, int size){
        position = pos;
        maxSize = size;
    }

    public boolean update(){
        if(size + 8 >= maxSize) return true;
        size += 8;
        show();
        return false;
    }

    public void show(){
        Shapes.ellipse((int) position.getX(), (int) position.getY(), size, 6);
    }
}
