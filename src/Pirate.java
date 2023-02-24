import bagel.*;
import java.util.Random;
import java.util.ArrayList;
import bagel.util.Point;
import bagel.util.Rectangle;
public class Pirate extends Character {
    protected  Image LEFT_IMAGE = new Image("res/pirate/pirateLeft.png");
    protected  Image RIGHT_IMAGE = new Image("res/pirate/pirateRight.png");

    protected Image HIT_LEFT = new Image("res/pirate/pirateHitLeft.png");
    protected Image HIT_RIGHT = new Image("res/pirate/pirateHitRight.png");

    protected int maxHealth = 45;

    private final static double UPPER_SPEED = 0.7;
    private final static double LOWER_SPEED = 0.2;

    protected int PIRATE_DAMAGE = 10;

    protected double projectileSpeed = 0.4;
    private Rectangle attackBox;
    protected int attackRange = 200;
    protected Image projectileImage= new Image("res/pirate/pirateProjectile.png");
    protected  ArrayList<Projectile> projectilesList = new ArrayList<>();

    private Random rand = new Random();
    private double speed = rand.nextDouble(UPPER_SPEED-LOWER_SPEED)+LOWER_SPEED;
    private double speedX;
    private double speedY;

    private final static int OFF_SET = 6;
    private double health_x;
    private double health_y;

    private int counter=1;
    private int counterProjectile=1;
    private final static int INVINCIBLE_DURATION =1500 ;
    private final static int COOLDOWN = 3000;

    /**
     * Constructor method, super the initial position for Superclass Character to create default Pirate.
     * @param startX Initial X position of the pirate
     * @param startY Initial Y position of the pirate
     */
    public Pirate(int startX, int startY) {
        super(startX, startY);
        healthPoints = maxHealth;
        max_health = maxHealth;

        currentLeft=LEFT_IMAGE;
        currentRight=RIGHT_IMAGE;
        currentImage= RIGHT_IMAGE;

        projectile_Image = projectileImage;
        damagePoints = PIRATE_DAMAGE;

        FONT_SIZE = 15;
        FONT = new Font("res/wheaton.otf", FONT_SIZE);
        randomDirection();
    }

    /**
     * Constructor but this time for different variation of Pirate (this can extend to be more than just blackbeard)
     * @param startX Initial X position of the pirate
     *      * @param startY Initial Y position of the pirate
     *      * @param leftImage new Left Image represent different type pirate
     *      * @param rightImage new Right Image represent different type pirate
     *      * @param hitLeft new Left State Image represent different type pirate
     *      * @param hitRight  new Right State Image represent different type pirate
     */
    public Pirate(int startX, int startY, Image leftImage, Image rightImage, Image hitLeft, Image hitRight) {
        super(startX, startY);
        LEFT_IMAGE = leftImage;
        RIGHT_IMAGE = rightImage;
        currentImage = RIGHT_IMAGE;
        currentLeft = LEFT_IMAGE;
        currentRight = RIGHT_IMAGE;

        HIT_LEFT = hitLeft;
        HIT_RIGHT= hitRight;

        FONT_SIZE = 15;
        FONT = new Font("res/wheaton.otf", FONT_SIZE);
        randomDirection();
    }


    /**
     * update the pirate movement and action
     * @param objects This is the read list of object obstacle that pirate could not pass
     * @param sailor This is the Main Sailor of the game that control by input
     */
    public void update(ArrayList<Object> objects, Sailor sailor) {
        counter++;
        counterProjectile++;
        health_x = x;
        health_y = y - OFF_SET;


        Point centre = getCentre();
        centre = new Point(centre.x -attackRange/2, centre.y-attackRange/2);
        // Attack Box with center at the centre of the Pirate and attackRange being width and height of the box
        attackBox = new Rectangle(centre, attackRange,attackRange);

        move(speedX,speedY);

        // Change back to normal state after invincible time has passed
        if (currentState.invincible && millisecond(counter) > INVINCIBLE_DURATION){
            changeState("READY", LEFT_IMAGE, RIGHT_IMAGE);
            currentState.invincibleState(false );
            justBeingDamaged=false;
            counter=1;
        }
        // Shoot projectile to the Sailor direction after exactly 3000 millisecond (3second) has passed
        if (sailor.getBoundingBox().intersects(attackBox)&& millisecond(counterProjectile)>COOLDOWN) {
            projectilesList.add(new Projectile(projectileImage, getCentre(), sailor.getCentre()));
            counterProjectile=1;
        }

        for (Projectile projectile: projectilesList) {
            shootProjectile(projectile,sailor);
        }

        // Change to invincible state if just being hit and not already invincible
        if (!currentState.invincible && justBeingDamaged) {
            changeState("INVINCIBLE", HIT_LEFT, HIT_RIGHT);
            counter=1;
        }

        currentImage.drawFromTopLeft(x, y);
        // check collision and switch moving direction if collided is detected.
        if (checkCollisions(objects) || (isOutOfBound())) {
            if (speedX != 0) {
                speedX = -speedX;
                if (currentImage.equals(currentLeft)) currentImage = currentRight;
                else currentImage = currentLeft;
            }
            else speedY = -speedY;
        }
        renderHealthPoints(health_x,health_y);
    }


    private void randomDirection() {
        int directionIndicator = rand.nextInt(4) ;
        if (directionIndicator==0) {
            speedX = speed;
            speedY = 0;
        }
        else if (directionIndicator==1) {
            speedX = -speed;
            currentImage = LEFT_IMAGE;
            speedY = 0;
        }
        else if (directionIndicator==2) {
            speedX = 0;
            speedY = speed;
        }
        else if (directionIndicator==3) {
            speedX = 0;
            speedY = -speed;
        }
    }


    public void shootProjectile(Projectile projectile, Character target) {
        Rectangle targetBox = target.currentImage.getBoundingBoxAt(target.getCentre());
        if (!projectile.justHit) projectile.move(this.projectileSpeed,target);
        if (targetBox.intersects(new Point(projectile.x,projectile.y))) {
            if (!projectile.justHit) {
                target.healthPoints -= damagePoints;
                logPrint(this,target);
            }
            projectile.justHit = true;
        }

    }

}