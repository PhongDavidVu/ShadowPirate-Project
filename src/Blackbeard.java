import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.ArrayList;


public class Blackbeard extends Pirate{
    private final static Image LEFT_IMAGE = new Image("res/blackbeard/blackbeardLeft.png");
    private final static Image RIGHT_IMAGE = new Image("res/blackbeard/blackbeardRight.png");
    private final static Image HIT_LEFT = new Image("res/blackbeard/blackbeardHitLeft.png");
    private final static Image HIT_RIGHT = new Image("res/blackbeard/blackbeardHitRight.png");

    private final static int MAX_HEALTH_POINTS = 90;

    private final static int BLACKBEARD_DAMAGE = 20;
    private final static double PROJECTILE_SPEED = 0.8 ;
    private final static Image PROJECTILE_IMAGE= new Image("res/blackbeard/blackbeardProjectile.png");
    private final static   int ATTACK_RANGE = 400;


    /**
     * Using the constructor for different variation of Pirate in Pirate Class construct a blackbeard
     * @param startX This is the starting X position of Blackbeard
     * @param startY This is the starting Y position of Blackbeard
     */
    public Blackbeard(int startX, int startY) {
        // Implement all the different aspect between Pirate and Blackbeard
        super(startX,startY,LEFT_IMAGE,RIGHT_IMAGE,HIT_LEFT,HIT_RIGHT);

        healthPoints = MAX_HEALTH_POINTS;
        max_health = MAX_HEALTH_POINTS;

        attackRange = ATTACK_RANGE;
        damagePoints = BLACKBEARD_DAMAGE;
        projectileImage = PROJECTILE_IMAGE;
        projectileSpeed = PROJECTILE_SPEED;

    }

    /**
     * This implement the update of the Blackbeard and fully taken from Pirate class
     * @param objects This is the read list of object obstacle that pirate could not pass
     * @param sailor This is the Main Sailor of the game that control by input
     */
    public void update(ArrayList<Object> objects, Sailor sailor) {
        super.update(objects,sailor);

    }


}
