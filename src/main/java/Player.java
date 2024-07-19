import static org.lwjgl.glfw.GLFW.*;

public class Player {



    private Vector2 acceleration = new Vector2(0, Main.GRAVITY);
    private Vector2 velocity = new Vector2();
    private Vector2 position = new Vector2(Main.getWidth() / 2f, Main.getHeight() * 0.75);
    private final Vector2 size = new Vector2(20, 20);

    public Player(){


    }

    public Player update(){
        if(Main.activeKeys.contains(GLFW_KEY_D))
            velocity = velocity.add(0.2, 0);
        if(Main.activeKeys.contains(GLFW_KEY_A))
            velocity = velocity.add(-0.2, 0);
        if(Main.activeKeys.contains(GLFW_KEY_SPACE))
            if(position.getY() + size.getY() >= Main.getHeight() - 1)
                velocity = velocity.add(0, -1.5);

        velocity = velocity.add(0, Main.GRAVITY * 1 / (position.getY() - Main.getHeight()));

        velocity = velocity.add(acceleration);

        if(position.getY() + size.getY() >= Main.getHeight() - 5)
            velocity = velocity.mul(0.97);
        else
            velocity = velocity.mul(0.99);

        position = position.add(velocity);

        if(position.getX() + size.getX() > Main.getWidth()) {
            position.setX(Main.getWidth() - size.getX());
            velocity = velocity.mul(-1, 1);
        }
        else if(position.getX() < 0) {
            position.setX(0);
            velocity = velocity.mul(-1, 1);
        }
        if(position.getY() + size.getX() > Main.getHeight()) {
            position.setY(Main.getHeight() - size.getX());
        }
        else if(position.getY() < 0) {
            position.setY(0);
        }


        return this;
    }

    public Player show(){
        Shapes.rect((int) position.getX(), (int) position.getY(), (int) size.getX(), (int) size.getY());

        return this;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }
}
