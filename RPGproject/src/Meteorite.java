//the projectile for boss fight, by Weijie Y
import java.util.Random;
public class Meteorite extends FlyingObject {
	private int speed=7;//moving speed
	public int hp=7;
	public void hit(){
		hp-=1;
	}
	public int getHP(){
		return hp;
	}
	public Meteorite(){
		image=ShootGame.meteorite;
		width=image.getWidth();
		height=image.getHeight();
		Random rand=new Random();
		x= rand.nextInt(ShootGame.WIDTH-width);
		y=-this.height;
	}
	//score after kill a enemy
	public int getScore(){
		return 5;
	}
	//move
	public void step() {
		y+=speed;
	}
	@Override
	public boolean outOfBounds() {
		return this.y>=ShootGame.HEIGHT;
	}
}
