//the player for the boss fight, by weijie Y
import java.util.Random;
public class Hero extends FlyingObject {
	public int doubleFire;
	public int tripleFire;
	private int life;
	private int score;
	public static int heroX;
	public static int heroY;
	public int getLife(){
		return life;
	}

	public int getScore(){
		return score;
	}
	public Hero(){
		image = ShootGame.hero0;
		width = image.getWidth();
		height = image.getHeight();
		x = 150;
		y = 450;
		life = 511;
		score = 0;
	}

	@Override
	public void step() {
		Random  r = new Random();
		if(r.nextInt(2)==0){
			image = ShootGame.hero0;
		}else{
			image = ShootGame.hero0;
		}
	}
	@Override
	public boolean outOfBounds() {
		return false;
	}
	//you move with the mouse
	public void move(int x,int y){
		this.x = x-width/2;
		this.y = y-height/2;
		heroX = this.x;
		heroY = this.y;
	}
	public void getScore_Award(FlyingObject f){
		if(f instanceof Meteorite){
			score += ((Meteorite) f).getScore();
		}
	}
//shoot bullet
	public Bullet[] shoot(){
		Bullet[] bullets = null;
		double xScale = ShootGame.getXScale();
		double yScale = ShootGame.getYScale();

		if(doubleFire==1){
			if(tripleFire==1){
				bullets = new Bullet[3];
				bullets[0] = new Bullet((int)
						(x/xScale/xScale)+width/6,(int)(y/yScale/yScale)+height/4);
				bullets[1] = new Bullet((int)
						(x/xScale/xScale)+(int)(width/xScale/1.2),
						(int)(y/yScale/yScale)+height/4);
				bullets[2] = new Bullet((int)
						(x/xScale/xScale)+width/2,(int)(y/yScale/yScale)-height/4);
			}else {
				bullets = new Bullet[2];
				bullets[0] = new Bullet((int) 
						(x / xScale / xScale) + width / 6, 
						(int) (y / yScale / yScale) + height / 4);
				bullets[1] = new Bullet((int) 
						(x / xScale / xScale) + (int) (width / xScale / 1.2),
						(int) (y / yScale / yScale) + height / 4);
			}
		} else{
			bullets = new Bullet[1];
			bullets[0] = new Bullet((int)(x/xScale/xScale)+width/2,
					(int)(y/yScale/yScale)-height/4);
		}
		return bullets;
	}
	public int inv;
	public void doInv(){
		inv -=1;
	}
	public void setInv(){
		inv = 200;
	}
	public void die(){
		life = life -1;
	}
	public void setHp(int num){
		life =num;
	}
	public boolean hit(FlyingObject f){
		boolean r = FlyingObject.bang(this, f);
		if(r){
			life--;
		}
		return r;
	}
}
