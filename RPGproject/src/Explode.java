//Explosion animation for boss fight, by Weijie Y
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Explode {
    int x,y;
    boolean small;
    boolean big;
    static Image images[] = new Image[11];
    static Image images2[] = new Image[11];//explosion exist 11 frame
    static Image images3[] = new Image[11];
    int count;
    public boolean live=true;
        static{
            for(int i=1;i<=6;i++){
                try {
                    images[i] = ImageIO.read(new File("Resources/explosion.png"));
                    images[i] = resizeImage(images[i],20,20);
                    images2[i] = ImageIO.read(new File("Resources/explosion.png"));
                    images2[i] = resizeImage(images[i],200,200);
                    images3[i] = ImageIO.read(new File("Resources/explosion.png"));
                    images3[i] = resizeImage(images[i],400,400);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                images[i].getWidth(null);
            }
        }

    public void draw(Graphics g) {
        if(!live) {
            return;
        }
        int max = 10;
        if(small){
            max = 2;
        }
        if(live){
            if(count<=max){//stop drawing the explosion when 11 frame has end
                if(small){
                    g.drawImage(images[count], (int)(x*ShootGame.getXScale()),
                            (int)(y-200/ShootGame.getYScale()/ShootGame.getYScale()), null);

                }else if(big){
                    g.drawImage(images3[count], x+25, y-250, null);
                }else{
                    g.drawImage(images2[count], x, y, null);
                }

                count++;
            }else{
                live=false;
            }
        }
    }
    public static Image resizeImage(Image originalImage, int targetWidth, int targetHeight) {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage newImage = new BufferedImage(resultingImage.getWidth(null),
                resultingImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(resultingImage, 0, 0, null);
        g.dispose();
        return newImage;
    }
    public Explode(int x, int y, boolean small, boolean big) {
        super();
        this.big = big;
        this.small = small;
        this.x = x-10;
        this.y = y+200;
    }
}
