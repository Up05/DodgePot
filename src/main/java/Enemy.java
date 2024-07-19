import java.nio.file.Files;
import java.nio.file.Paths;

public class Enemy {

    Vector2 position, velocity;
    final int type;
    TextFast body;
    private boolean dead;

    public Enemy(){
        Player player = Main.instance.getPlayer();
        type = (int) Math.floor(Math.random() * 2);

        velocity = new Vector2(0, Math.random() * 4 + 4);
        double temp = Main.getHeight() / velocity.getY();
        double a = -player.getVelocity().getX();
        if(a * temp < 0)
            position = new Vector2(player.getPosition().getX() + Math.abs((a * temp) % Main.getWidth()), 0);
        else if(a * temp > Main.getWidth())
            position = new Vector2(player.getPosition().getX() + Main.getWidth() - (a * temp) % Main.getWidth(), 0);
        else
            position = new Vector2(player.getPosition().getX() + a * temp, 0); // b o d g e

        // but what if it's -2x Main.width, OH MY GOD, THE MATHEMATIX

        Shapes.point(position.mul(1, 0).add(0, player.getPosition().getY()));

        String out = "_error";
        try {
            out = new String(Files.readAllBytes(Paths.get(this.getClass().getResource("pot" + type + ".txt").toURI())));
        } catch(Exception e) {
            e.printStackTrace();
        }
        body = new TextFast(out, 0, 0).setCentered(true).setCenteredY(true).setSpaceBetweenChars(8);


    }

    public Enemy update(){
        position = position.add(velocity);
        if(position.getY() > Main.getHeight())
            dead = true;
        return this;
    }

    public Enemy draw(){
        body.setPosition(position);
        body.draw();
        return this;
    }

    public boolean isDead() {
        return dead;
    }
}
