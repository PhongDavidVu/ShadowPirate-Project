import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.ArrayList;

public class Character {
    protected double x;
    protected double y;

    protected int healthPoints;
    protected int max_health;
    protected int damagePoints;

    protected Image currentLeft;
    protected Image currentRight;
    protected State currentState = new State();
    protected Image currentImage;
    protected Image projectile_Image;

    protected boolean justBeingDamaged = false;

    public static int heightTop;
    public static int heightBottom;
    public static int widthLeft;
    public static int widthRight;

    protected final int FPS = 60;

    private final int ORANGE_BOUNDARY = 65;
    private final int RED_BOUNDARY = 35;
    protected int FONT_SIZE = 30;
    protected Font FONT = new Font("res/wheaton.otf", FONT_SIZE);
    private final DrawOptions COLOUR = new DrawOptions();
    private final Colour GREEN = new Colour(0, 0.8, 0.2);
    private final Colour ORANGE = new Colour(0.9, 0.6, 0);
    private final Colour RED = new Colour(1, 0, 0);


    /**
     * Construction for any Character class or its subclasses
     * @param x initial X position of Character
     * @param y intial Y position of Character
     */
    public Character(int x, int y) {
        this.x = x;
        this.y = y;
        COLOUR.setBlendColour(GREEN);
    }

    /**
     * Setting the Left Boundary and Top Boundary
     * @param readHeightTop: The y coordinate of topLeft read from CSV
     * @param readWidthLeft The x coordinate of topLeft read from CSV
     */
    public static void setTopLeft(int readHeightTop, int readWidthLeft) {
        heightTop = readHeightTop;
        widthLeft = readWidthLeft;
    }
    /**
     * Setting the Right Boundary and Bottom Boundary
     * @param readHeightBottom: The y coordinate of BottomRight read from CSV
     * @param readWidthRight The x coordinate of BottomRight read from CSV
     */
    public static void setBottomRight(int readHeightBottom, int readWidthRight) {
        heightBottom = readHeightBottom;
        widthRight = readWidthRight;
    }

    /**
     * Rendering percentage as healt hpoint with the given location
     * @param health_x This is the x coordinate of position we want to render the health
     * @param health_y This is the y coordinate of position we want to render the health
     */
    public void renderHealthPoints(double health_x, double health_y) {
        double percentageHP = ((double) healthPoints / max_health) * 100;
        if (percentageHP <= RED_BOUNDARY) {
            COLOUR.setBlendColour(RED);
        } else if (percentageHP <= ORANGE_BOUNDARY) {
            COLOUR.setBlendColour(ORANGE);
        }
        FONT.drawString(Math.round(percentageHP) + "%", health_x, health_y, COLOUR);
    }

    /**
     * Changing the state of he character
     * @param changeState The state that wanted to be change to
     * @param leftStateImage The new Left Image when character in its new stage
     * @param rightStateImage The new Right  Image when character in its new stage
     */
    public void changeState(String changeState, Image leftStateImage, Image rightStateImage) {
        currentState.setState(changeState);
        if (currentImage.equals(currentLeft)) currentImage = leftStateImage;
        else currentImage= rightStateImage;
        currentLeft = leftStateImage;
        currentRight = rightStateImage;

    }

    /**
     * Taking in the list of object that has been read and check for collisions
     * @param objects List of read objects, this includes items as well
     * @return True if collisions is found, false otherwise
     */
    public boolean checkCollisions(ArrayList<Object> objects) {
        Rectangle characterBox = getBoundingBox();
        for (Object object: objects){
            // Only rendering and accounting object that has not been picked. (This could imply to bomb as well)
            if (!object.picked) {
                Rectangle blockBox = object.getBoundingBox();
                if (characterBox.intersects(blockBox)) {
                    // implement effect of item if the object is an items
                    if (object instanceof Affectable && this instanceof Sailor) {
                        ((Affectable) object).implementAffect(this);
                    }
                    // trigger explosion in bomb if object is an bomb
                    if (object instanceof Bomb && this instanceof Sailor) {
                        ((Bomb) object).explode(this);
                    }
                    // Winning condition for sailor achieve if collide with treasure (level 1)
                    if (object instanceof Treasure && this instanceof Sailor){
                        ((Sailor) this).win_level1= true;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check if the Sailor have collide with enemies, if sailor is in attack state, implement damage
     * @param pirates List of read enemies, including blackbeard
     */
    public void checkCollisionsEnemy(ArrayList<Pirate> pirates) {
        Rectangle characterBox = getBoundingBox();
        int i = 0;
        for(Pirate pirate: pirates) {
            if (!pirate.isDead()) {
                Rectangle enemiesBlock = pirate.getBoundingBox();
                if (characterBox.intersects(enemiesBlock)) {
                    // Implement damage if sailor is in attack state
                    if (currentState.attack) this.implementDamage(pirate);
                }
            }
            i++;
        }
    }

    /**
     * Implement Damage to the target
     * @param target This is the target to be damaged.
     */
    public void implementDamage(Character target) {
            if (!target.justBeingDamaged) {
                target.justBeingDamaged = true;
                target.healthPoints -= this.damagePoints;
                // Print out the log state of the game
                logPrint(this,target);

            }
    }

    /**
     * Printing Log whenever some Character implement damage to another
     * @param inflicter The source of damage
     * @param target The target of the damage
     */
    public void logPrint(Character inflicter, Character target) {
        String inflicterName = getThisCharacter(inflicter);
        String inflictDamage = " inflicts " + inflicter.damagePoints + " damage points on ";
        String targetName = getThisCharacter(target);
        String healthLeft = targetName + "'s current health: " + target.healthPoints + "/" + target.max_health;
        System.out.println(inflicterName + inflictDamage + targetName + ".  " + healthLeft);
    }

    /**
     * Return the String name of the given character, use for log printing
     * @param character What character we want to get the name for.
     * @return the string name of the parameter Character
     */
    public String getThisCharacter(Character character) {
        if (character instanceof Sailor) return "Sailor";
        else if (character instanceof Blackbeard) return "Blackbeard";
        else return "Pirate";
    }

    /**
     * Get Bouding box with top left at x,y of a character
     * @return the Bounding Box of the character
     */
    public Rectangle getBoundingBox(){
        Rectangle characterBox = currentImage.getBoundingBoxAt(new Point(x, y));
        characterBox.moveTo(new Point(x,y));
        return characterBox;
    }

    /**
     * Get The centre point coordinates of the character
     * @return The Point containing the centre coordinate of the Character
     */
    public Point getCentre (){
        Rectangle characterBox = getBoundingBox();
        return characterBox.centre();
    }

    /**
     * Check if the Character is inside the pre-set bound
     * @return true if it is OUT of bound, False otherwise
     */
    public boolean isOutOfBound() {
        if ((y > heightBottom) || (y < heightTop) ||
                (x < widthLeft) || (x > widthRight)) {
            return true;
        }
        return false;
    }

    /**
     * Main movement function for all character
     * @param xMove This is how much we want to the x coordinator of Character to be changed
     * @param yMoveThis is how much we want to the y coordinator of Character to be changed
     */
    public void move(double xMove, double yMove){
        x += xMove;
        y += yMove;
    }

    /**
     * Given the counter, return the millisecond coresponding
     * @param counter The Counter that increment every Update of character
     * @return millisecond corresponding to how many count that counter has counted
     */
    public int millisecond (int counter) {return counter *1000/FPS;}

    /**
     * Function to check whether character is dead or not
     * @return True if the character is dead
     */
    public boolean isDead() {
        return healthPoints <= 0;
    }

}