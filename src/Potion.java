import bagel.Image;

public class Potion extends Object implements Affectable {
    private final static Image POTION= new Image("res/items/potion.png");
    private final static Image POTION_ICON =  new Image("res/items/potionIcon.png");
    private final static int HEAL = 25;

    /**
     * Constructor for the potion.
     * @param startX This is the X position of the potion
     * @param startY This is the Y position of the potion
     */
    public Potion(int startX, int startY){
        super(startX,startY,POTION);
    }

    /**
     * Implement the effect of healing of the potion on the targeted character
     * @param gainer This is the targeted character that we want to heal
     */
    @Override
    public void implementAffect(Character gainer) {
        gainer.healthPoints += HEAL;
        // This prevents heal over the max health of  the character.
        if (gainer.healthPoints > gainer.max_health) gainer.healthPoints = gainer.max_health;
        String newHealth = gainer.healthPoints + "/" + gainer.max_health;
        System.out.println("Sailor finds Potion.  Sailor’s current health:  " + newHealth);
        getPicked(POTION_ICON);
    }


}