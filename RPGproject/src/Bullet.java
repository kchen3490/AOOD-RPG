//bullet for boss fight, by Weijie Y
public class Bullet extends FlyingObject{
	private int speed=10;
	public Bullet(int x,int y){
		image=ShootGame.bullet;
		width=image.getWidth();
		height=image.getHeight();
		this.x=(int)(x*ShootGame.getXScale());
		this.y=(int)(y*ShootGame.getYScale());
	}
	@Override
	public void step() {
		y-=speed;
		
	}
	@Override
	public boolean outOfBounds() {
		return this.y<=-this.height;
	}

}
