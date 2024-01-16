// parent class for all the flying object for boss fight, By Weijie Y
import java.awt.image.BufferedImage;

public abstract class FlyingObject {
	protected int x;//x position of the object's top left
	protected int y;//y position of the object's top left
	protected int width;
	protected int height;
	protected BufferedImage image;//image used by the flying object
	public abstract void step();
	public abstract boolean outOfBounds();

	public static boolean bang(FlyingObject f1,FlyingObject f2){
		int f1x = f1.x+f1.width/2;
		int f1y = f1.y+f1.height/2;
		int f2x = f2.x+f2.width/2;
		int f2y = f2.y+f2.height/2;
		boolean H = (Math.abs(f1x-f2x))<(f1.width+f2.width)/2;
		boolean V = (Math.abs(f1y-f2y))<(f1.height+f2.height)/2;
		return H&V;
	}
}
