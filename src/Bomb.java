import bagel.Image;


public class Bomb extends Object {
    private final static Image BOMB = new Image("res/bomb.png");
    private final static Image BOMB_EXPLODE= new Image("res/explosion.png");
    private final static int DAMAGE_POINT = 10;
    public final int EXPLODE_DURATION = 500;
    public boolean exploded = false;

    /**
     * Constructor for the Bomb which super from Object class
     * @param startX The X coordinate of the bomb that will be drawn
     * @param startY The Y coordinate of the bomb that will be drawm
     */
    public Bomb(int startX, int startY){
        super(startX,startY, BOMB);
    }

    /**
     * This is the explosion function of the bomb which will explode, implement damage to the target then change image
     * @param target  This is the target that affect by the explosion
     */
    public void explode(Character target){
        if (exploded) return;
        this.objectImage = BOMB_EXPLODE;
        exploded = true;
        target.healthPoints -= DAMAGE_POINT;
        String healthLeft = target.healthPoints + "/"+ target.max_health;
        System.out.println("Bomb inflicts 10 damage points on Sailor.  Sailor's current health: " + healthLeft);
    }


}