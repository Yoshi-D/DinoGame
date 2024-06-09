package src;
public class Dino extends GameObject{
    private boolean jumping = false;
    public Dino(){
        super(50,Game.GROUND_HEIGHT);
        sprite=loadSprite("assets/dino.png");
    }
    @Override
    public void tick(){
        if (jumping && y+dy>Game.GROUND_HEIGHT){
            jumping = false;
            y=Game.GROUND_HEIGHT;
            dy=0;
        }
        else if(jumping){
            dy+=0.3;
        }
        y+=dy;
    }
    public void jumpAction(){
        if(!jumping){
            jumping =true;
            dy=-8;
        }
    }
}
