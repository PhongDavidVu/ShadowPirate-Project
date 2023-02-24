import bagel.Image;


public class Sword extends Object implements Affectable {
    private final static Image SWORD= new Image("res/items/sword.png");
    private final static Image SWORD_ICON =  new Image("res/items/swordIcon.png");
    private final static int DAMAGE_INCREASE = 15;

    /**
     * Constructor for the sword.
     * @param startX This is the X position of the sword.
     * @param startY This is the Y position of the sword.
     */
    public Sword(int startX, int startY){
        super(startX,startY,SWORD);
    }

    /**
     * Implement the bonus attack damage to gainer
     * @param gainer the character that will be benefit from the item
     */
    @Override
    public void implementAffect(Character gainer) {
        gainer.damagePoints += DAMAGE_INCREASE;
        System.out.println("Sailor finds Sword.  Sailor’s damage points increase to "+ gainer.damagePoints);
        getPicked(SWORD_ICON);
    }


}