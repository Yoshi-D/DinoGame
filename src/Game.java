package src;
import java.awt.*;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements KeyListener{
    public static final int GAME_WIDTH=800;
    public static final int GAME_HEIGHT=400;
    public static final int GROUND_HEIGHT=250;
    
    private boolean running = false;

    private Dino dino;
    private ObstaclePlacer obstaclePlacer;

    private enum State{
        NOT_PLAYING,
        PLAYING,
        GAME_OVER
    }
    private State state= State.NOT_PLAYING;
    public Game(){
        dino=new Dino();
        obstaclePlacer = new ObstaclePlacer();
        addKeyListener(this);
    }
    public static void main(String[] args) {
        new GameWindow(GAME_WIDTH, GAME_HEIGHT, "Dino Game", new Game());
    }
    public void start() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 100.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
    
        running = true;
        state=State.PLAYING;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
    
            while (delta >= 1) {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;
    
                    // this prints the tick and frame rate to the console
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("fps: " + frames + " ticks: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }
    public void tick(){
        if(state==State.PLAYING){
        dino.tick();
        obstaclePlacer.tick();
        collisionCheck();
    }
    }
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if (bs==null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        g.setColor(Color.black);
        g.drawLine(0, GROUND_HEIGHT, GAME_WIDTH, GROUND_HEIGHT);
        dino.render(g,this);
        obstaclePlacer.render(g,this);
        g.dispose();
        bs.show();
    }
    public void collisionCheck(){
        for(Obstacle obstacle:obstaclePlacer.obstacles){
            if(dino.getHitBox().intersects(obstacle.getHitBox())){
                state=State.GAME_OVER;
                return;
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int key=e.getKeyCode();
        
        if (key==KeyEvent.VK_SPACE){
            if (state==State.PLAYING){
            dino.jumpAction();  
            }
            else if (state==State.GAME_OVER){
                dino=new Dino();
                obstaclePlacer= new ObstaclePlacer();
                state=State.PLAYING;
            }

        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}