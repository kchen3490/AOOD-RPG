//Final boss for boss fight, by Weijie Y
public class Boss extends FlyingObject{
    private static int maxHealth;
    public static int health = maxHealth;
    public static int moveSpeed = 10;
    public static boolean right = true;
    double boundary;
    public Boss(){
        image=ShootGame.boss0;
        boundary = ShootGame.WIDTH;
        x = ShootGame.WIDTH/2;
        y=20;
        width = image.getWidth();
        height = image.getHeight();
        health = maxHealth;
    }
    public void setHP(int num){
        maxHealth = num;
        resetHealth();
    }
    public int getHealth(){return health;}
    public void damage(){health-=1;}
    public void resetHealth(){health = maxHealth;}
    @Override
    public void step() {
        if(right){
            x += (int)(Math.random()*moveSpeed*ShootGame.getXScale());
            if(x +(double)width*1.5*ShootGame.getXScale()> ShootGame.WIDTH){
                right = false;
            }
        }else {
            x -= (int)(Math.random()*moveSpeed*ShootGame.getXScale());
            if(x < 0){
                right = true;
            }
        }
    }
    @Override
    public boolean outOfBounds() {
        return false;
    }
    public boolean bossCollision(FlyingObject f1,FlyingObject f2){
        int ax = f1.x;
        int ay = f1.y+f1.y/4;
        int aw = f1.width;
        int bx = f2.x+f2.width/6;
        int by = f2.y+f2.y/4;
        int bw = f2.width+f2.width/3;
        int ar = aw / 2, br = bw / 2;
        int acx = ax + ar, acy = ay + ar;
        int bcx = bx + br, bcy = by + br;
        double length = Math.sqrt(Math.pow(acx - bcx, 2) + Math.pow(acy - bcy, 2));
        return length < (ar + br);
    }
}

