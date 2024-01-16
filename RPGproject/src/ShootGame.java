//final game, the boss fight, by Weijie Y.
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;

public class ShootGame extends RPGPanel {

    int spawnRate = 7;
    int attackSpeed;
    private static final long serialVersionUID = 1L;
    //size of the screen
    public static int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static BufferedImage background;
    public static BufferedImage start;
    public static BufferedImage meteorite;
    public static BufferedImage bullet;
    public static BufferedImage hero0;
    public static BufferedImage gameover;
    public static BufferedImage boss0;
    {
        try {
            changeScale(WIDTH, HEIGHT);
            background = ImageIO.read(new File("Resources/background.png"));
            meteorite = ImageIO.read(new File("Resources/meteorite.png"));
            meteorite = resizeImage(meteorite,(meteorite.getWidth()/2 ),(meteorite.getHeight()/2 ));//size the meteorite
            bullet = ImageIO.read(new File("Resources/bullet.png"));
            hero0 = ImageIO.read(new File("Resources/hero0.png"));
            boss0 = ImageIO.read(new File("Resources/airplane.png"));
            boss0 = resizeImage(boss0, (250 ),(250 ));
        } catch (Exception e) {

        }

    }

    public Hero hero = new Hero();
    public Boss boss = new Boss();
    public Meteorite[] flyers = {};//array that store all enemy
    public Bullet[] bullets = {};//array that store all bullet

    //game state
    public static final int START = 0;
    public static final int RUNNING = 1;
    public static final int PAUSE = 2;
    public static final int GAME_OVER = 3;

    private static double xScale;
    private static double yScale;
    //initial state is start
    private int state = START;
    private boolean back = false;
    public int ableToPause = 0;
    public ShootGame(int id, RPG game, double xScale, double yScale){
        super(id,game, xScale,yScale);
        hero.setHp(game.getPower(0));
        attackSpeed = game.getPower(1);
        hero.doubleFire = game.getPower(2);
        hero.tripleFire = game.getPower(3);
        boss.setHP(game.getPower(4));
        ableToPause = game.getPower(5);
        action();
    }

    @Override
    public int getScreenID() {
        return 7;
    }
    public static int currentHeight;
    public static int currentWidth;
    //update images
    public void runImage() {
        try {
            Rectangle r = this.getBounds();
            currentHeight = r.height;
            currentWidth= r.width;
            changeScale(currentWidth, currentHeight);

            background = ImageIO.read(new File("Resources/background.png"));
            background = resizeImage(background, (background.getWidth() ),(background.getHeight() ));
            meteorite = ImageIO.read(new File("Resources/meteorite.png"));
            meteorite = resizeImage(meteorite,(meteorite.getWidth()/2 ),(meteorite.getHeight()/2 ));//size the meteorite
            bullet = ImageIO.read(new File("Resources/bullet.png"));
            bullet = resizeImage(bullet,(bullet.getWidth() ),(bullet.getHeight() ));
            hero0 = ImageIO.read(new File("Resources/hero0.png"));
            hero0 = resizeImage(hero0,(hero0.getWidth() ),(hero0.getHeight() ));
            boss0 = ImageIO.read(new File("Resources/airplane.png"));
            boss0 = resizeImage(boss0, (boss0.getWidth()*8 ),(boss0.getHeight()*8 ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeScale(int width, int height) {
        xScale = (double) width / WIDTH;
        yScale = (double) height / HEIGHT;
    }

    public static double getXScale() {
        return xScale;
    }

    public static double getYScale() {
        return yScale;
    }

    //things happening during the gaME
    public void action() {
        runImage();
        //create a timer
        Timer timer = new Timer();
        //listen to the mouse
        MouseAdapter l = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (state == RUNNING) {

                    int x = e.getX();
                    int y = e.getY();
                    hero.move(x, y);
                }
            }
            @Override
            public void mouseExited(MouseEvent arg0) {
                if (state == RUNNING&&ableToPause ==1) {
                    state = PAUSE;
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if (state == PAUSE) {
                    state = RUNNING;
                }
                back = true;
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                if (state == START) {
                    state = RUNNING;
                } else if (state == PAUSE) {
                    state = RUNNING;
                } else if (state == GAME_OVER) {
                    boss.resetHealth();
                    state = START;
                    flyers = new Meteorite[0];
                    bullets = new Bullet[0];
                    hero = new Hero();
                    timer.cancel();
                    try {
                        game.changeScreen(0);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                }
            }
        };
        this.addMouseMotionListener(l);//allow mouse to move
        this.addMouseListener(l);//allow mouse to click
        timer.schedule(new TimerTask() {
            private int runTimes = 0;

            @Override
            public void run() {
                if (back) {
                    runImage();
                    back = false;
                }
                //other then repeating features, other feature only work when running
                if (state == RUNNING) {
                    runTimes++;
                    //run nextOne method once per 10 frame

                    if (runTimes % (100 / spawnRate) == 0) {
                        nextOne();
                    }
                    //move every object
                    for (int i = 0; i < flyers.length; i++) {
                        flyers[i].step();
                    }
                    //shoot a bullet every 20 frame

                    if (runTimes % (100 / attackSpeed) == 0) {
                        shoot();
                    }
                    //move all the bullet
                    for (int i = 0; i < bullets.length; i++) {

                        bullets[i].step();
                    }
                    hero.step();
                    boss.step();
                    //check collision of bullet and the enemy
                    bang();
                    //check collision of enemy and you
                    hit();
                    //destroy the object when it's out of bounce
                    outOfBounds();
                }

                repaint();
            }
        }, 10, 10);
    }

    //resize image
    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage newImage = new BufferedImage(resultingImage.getWidth(null),
                resultingImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(resultingImage, 0, 0, null);
        g.dispose();
        return newImage;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(background, -15, 0, WIDTH + 10, HEIGHT, null);
        paintHero(g);
        paintBoss(g);
        paintFlyers(g);
        paintBullets(g);
        paintData(g);
        for (int i = 0; i < explode.length; i++) {
            Explode e = explode[i];
            try {
                e.draw(g);
            } catch (Exception ex) {
            }
        }
        //draw game state depend on the game state
        if (state == START) {
            g.drawImage(start, 0, 0, WIDTH, HEIGHT, null);
            Font font = new Font(Font.SANS_SERIF, Font.BOLD, 55);
            g.setFont(font);
            g.drawString("Click to Start", (int)(WIDTH/2.4f), 2 * (HEIGHT / 5) + 50);
            g.drawString("Move your mouse to control",(int)(WIDTH/3.5f), 2 * (HEIGHT / 4) + 50);
            g.drawString("destroy the meteorite to gain score",(int)(WIDTH/4.5f), 2 * (HEIGHT / 4) + 150);
            g.drawString("and destroy the boss to win the game",(int)(WIDTH/5f), 2 * (HEIGHT / 4) + 250);

        } else if (state == PAUSE) {
            g.drawImage(gameover, 0, 0, WIDTH, HEIGHT, null);
            Font font = new Font(Font.SANS_SERIF, Font.BOLD, 55);
            g.setFont(font);
            g.drawString("Pause", (int)(WIDTH/2.4f), 2 * (HEIGHT / 5) + 50);
        } else if (state == GAME_OVER) {
            g.drawImage(gameover, 0, 0, WIDTH, HEIGHT, null);
            Font font = new Font(Font.SANS_SERIF, Font.BOLD, 55);
            g.setFont(font);
            g.drawString("Game over", (int)(WIDTH/2.4f), 2 * (HEIGHT / 5) + 50);
            g.drawString("Click to exit",(int)(WIDTH/2.45f),2 * (HEIGHT / 5) + 100);
        }
    }
    //draw you
    public void paintHero(Graphics g) {
        g.drawImage(hero.image, hero.x, hero.y, null);
    }
    //draw boss
    public void paintBoss(Graphics g) {
        g.drawImage(boss0, boss.x, boss.y, null);

        double maxHP = game.getPower(4);
        double currentHP = boss.getHealth();
        g.drawRect(0,0,getWidth(),20);
        g.setColor(Color.red);
        g.fillRect(0,0,(int)(getWidth()*(currentHP/maxHP)),20);
        g.setColor(Color.black);

    }
    //draw all the enemy
    public void paintFlyers(Graphics g) {
        for (int i = 0; i < flyers.length; i++) {
            g.drawImage(flyers[i].image, (int)(flyers[i].x*getXScale()), (int)(flyers[i].y*getYScale()), null);
        }
    }
    //draw all the bullets
    public void paintBullets(Graphics g) {
        for (int i = 0; i < bullets.length; i++) {
            g.drawImage(bullets[i].image, (int)(bullets[i].x*getXScale()), (int)(bullets[i].y*getYScale()), null);
        }
    }
    //draw health and score
    public void paintData(Graphics g) {
        int x = 20;
        int y = 50;
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 30);
        g.setFont(font);
        g.drawString("SCORE:" + hero.getScore(), x, y);
        y += 50;
        g.drawString("LIFE:" + hero.getLife(), x, y);
        y+= 50;
        g.drawString("Boss HP:"+boss.getHealth(),x,y);
    }
    //randomly generate an enemy
    public void nextOne() {
        Meteorite f = null;
        f = new Meteorite();
        flyers = Arrays.copyOf(flyers, flyers.length + 1);
        flyers[flyers.length - 1] = f;
    }
    public void shoot() {
        Bullet[] newBullets = hero.shoot();
        //return the new array for bullets
        bullets = Arrays.copyOf(bullets, bullets.length + newBullets.length);
        System.arraycopy(newBullets, 0, bullets,
                bullets.length - newBullets.length,
                newBullets.length);
    }
    Explode explode[] = {};
    public void bulletHit(int i){
        explode = Arrays.copyOf(explode, explode.length + 1);
        explode[explode.length - 1] = new Explode(bullets[i].x, bullets[i].y,true,false);
        bullets[i] = bullets[bullets.length - 1];
        bullets = Arrays.copyOf(bullets, bullets.length - 1);
    }
    public void bang() {
        hero.doInv();
        if(boss.bossCollision(hero,boss)){
            if(hero.inv < 0){
                hero.die();
                hero.setInv();
            }
        }
        for (int i = 0; i < bullets.length; i++) {
            for (int j = 0; j < flyers.length; j++) {
                try{
                if(boss.bossCollision(bullets[i],boss)){
                    bulletHit(i);
                    boss.damage();
                    if(boss.getHealth() <= 0){
                        explode = Arrays.copyOf(explode, explode.length + 1);
                        explode[explode.length - 1] = new Explode(boss.x, boss.y,false,true);
                        boss0 = resizeImage(boss0,1,1);
                        state = GAME_OVER;
                    }
                } else if (FlyingObject.bang(bullets[i], flyers[j])) {//if collision happened
                    flyers[j].hit();//reduce hp if the meteorite has been hit
                    //delete the bullet from bullet array
                    bulletHit(i);
                    if ((flyers[j].getHP() == 0)) {
                        hero.getScore_Award(flyers[j]);
                        //delete the enemy from enemy array
                        explode = Arrays.copyOf(explode, explode.length + 1);
                        explode[explode.length - 1] = new Explode(flyers[j].x, flyers[j].y,false,false);
                        flyers[j] = flyers[flyers.length - 1];
                        flyers = Arrays.copyOf(flyers, flyers.length - 1);
                        i--;
                    }
                    break;
                }
            }catch (Exception ignored){}
            }
        }
    }
    public void outOfBounds() {
        //check if the object is out of bounce
        Meteorite[] Flives = new Meteorite[flyers.length];
        int index = 0;
        for (int i = 0; i < flyers.length; i++) {
            if (!flyers[i].outOfBounds()) {
                Flives[index] = flyers[i];
                index++;
            }
        }
        flyers = Arrays.copyOf(Flives, index);
        index = 0;
        //check if bullet is out of bounce
        Bullet[] Bullets = new Bullet[bullets.length];
        for (int i = 0; i < bullets.length; i++) {
            if (!bullets[i].outOfBounds()) {
                Bullets[index] = bullets[i];
                index++;
            }
        }
        bullets = Arrays.copyOf(Bullets, index);
    }
    public void hit() {
        Meteorite[] lives = new Meteorite[flyers.length];
        //record the enemy that's alive
        int index = 0;
        for (int i = 0; i < flyers.length; i++) {
            if (!(hero.hit(flyers[i]))) {
                lives[index] = flyers[i];
                index++;
            }
        }
        if (hero.getLife() <= 0) {
            state = GAME_OVER;
        }
        flyers = Arrays.copyOf(lives, index);
    }
}
