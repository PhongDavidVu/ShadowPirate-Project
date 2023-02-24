import bagel.Image;

public class Treasure extends Object {
    private final static Image TREASURE= new Image("res/treasure.png");

    /**
     * Constructor for the Treasure Object.
     * @param startX This is the X position of the treasure.
     * @param startY This is the Y position of the treasure.
     */
    public Treasure(int startX, int startY){
        super(startX,startY,TREASURE);
    }



}