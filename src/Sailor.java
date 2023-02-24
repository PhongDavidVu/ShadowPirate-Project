import bagel.*;


import java.util.ArrayList;

public class Sailor extends Character {
    private static Image LEFT_IMAGE = new Image("res/sailor/sailorLeft.png");
    private static Image RIGHT_IMAGE = new Image("res/sailor/sailorRight.png");
    private static Image HIT_LEFT = new Image("res/sailor/sailorHitLeft.png");
    private static Image HIT_RIGHT = new Image("res/sailor/sailorHitRight.png");


    private final static int MOVE_SIZE = 1 ;
    private final static int MAX_HEALTH_POINTS = 100;
    private final static int SAILOR_DAMAGE = 15 ;

    private final static double HEALTH_X = 10;
    private final static double HEALTH_Y = 25;

    private static final int WIN_X = 990;
    private static final int WIN_Y = 630;
    protected boolean win_level1= false;

    private final static int ATTACK_DURATION = 1000;
    private final static int COOLDOWN = 1500;
    private int counter=1;

    private double oldX;
    private double oldY;

    /**
     * Constructor method, super the initial position for Superclass Character
     * @param startX starting X position of the sailor
     * @param startY starting Y postion of the sailor
     */
    public Sailor(int startX, int startY) {
        super(startX,startY);

        healthPoints = MAX_HEALTH_POINTS;
        max_health = MAX_HEALTH_POINTS;

        currentLeft = LEFT_IMAGE;
        currentRight = RIGHT_IMAGE;
        currentImage= RIGHT_IMAGE;

        damagePoints=SAILOR_DAMAGE;
        // starting state of sailor will always be idle state
        currentState.idleState(true);
    }

    /**
     * update the movement and action for Sailor
     * @param input Taking input for sailor movement and action
     * @param objects Taking in obstacle and available stationary objects
     * @param enemies Taking in the list of Enemies to implement action when needed
     */
    public void update(Input input, ArrayList<Object> objects,  ArrayList<Pirate> enemies){
        counter++;

        // changing back to idle state after the attack duration has finished
        if (currentState.attack && millisecond(counter) == ATTACK_DURATION ) {
            changeState("IDLE", LEFT_IMAGE, RIGHT_IMAGE);
            currentState.attackState(false);
            counter=1;
        }
        if (input.isDown(Keys.UP)){
            setOldPoints();
            move(0, -MOVE_SIZE);
        }else if (input.isDown(Keys.DOWN)){
            setOldPoints();
            move(0, MOVE_SIZE);
        }else if (input.isDown(Keys.LEFT)){
            setOldPoints();
            move(-MOVE_SIZE,0);
            currentImage = currentLeft;
        }else if (input.isDown(Keys.RIGHT)){
            setOldPoints();
            move(MOVE_SIZE,0);
            currentImage = currentRight;
        }
        // Only able to attack when cooldown time has passed

        if (input.wasPressed(Keys.S) && millisecond(counter) >= COOLDOWN) {
            changeState("ATTACK", HIT_LEFT, HIT_RIGHT);
            counter=1;
        }

        currentImage.drawFromTopLeft(x, y);
        if (checkCollisions(objects)) moveBack();
        checkCollisionsEnemy(enemies);
        if (isOutOfBound()) moveBack();
        renderHealthPoints( HEALTH_X, HEALTH_Y);
    }

    /**
     * Method that stores the old coordinates of the sailor
     */
    private void setOldPoints(){
        oldX = x;
        oldY = y;
    }

    /**
     * Method that moves the sailor back to its previous position
     */
    private void moveBack(){
        x = oldX;
        y = oldY;
    }

    /**
     * Method that moves the sailor back to its previous position
     * if level 0 we:
     * @return True when the location of the sailor is at WIN_X, WIN_Y
     * if level 1 we:
     * @return True when the win_level1 are set to true
     */
    public boolean hasWon(){
        if (win_level1) return true;

        return (x >= WIN_X) && (y >= WIN_Y);
    }



}