import java.util.ArrayList;
import java.util.Collection;

public class EnemySpawner {

    Collection<Enemy> enemies = new ArrayList<>();

    public EnemySpawner(){



    }

    public EnemySpawner spawn(){
        if(Math.random() < 0.05) {
            enemies.add(new Enemy());
        }
        return this;
    }

    public void update(){
        Collection<Enemy> temp = new ArrayList<>();
        for(Enemy enemy : enemies){
            if(enemy.update().draw().isDead())
                temp.add(enemy);
        }
        enemies.remove(temp);
    }
}
