package src;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Random;

public class ObstaclePlacer {
    public ArrayList<Obstacle> obstacles = new ArrayList<>();
    private float dx=-3;
    private float ddx= -0.001f;

    private Random random=new Random();
    private long lastObst;

    public void tick(){
        double rand = random.nextDouble();
        if(rand<0.01 && System.currentTimeMillis()-lastObst>500){
            obstacles.add(new Obstacle());
            lastObst= System.currentTimeMillis();
        }

        dx+=ddx;
        for(Obstacle obstacle: obstacles){
            obstacle.dx=dx;
            obstacle.tick();
            if(obstacle.x<-50){
                obstacles.remove(obstacle);
                break;
            }
        }
    }
    public void render(Graphics g, ImageObserver observer){
        for(Obstacle obstacle: obstacles){
            obstacle.render(g, observer);
        }
    }
    
}
