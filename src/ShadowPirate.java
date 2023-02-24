import bagel.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *  Skeleton Code for SWEN20003 Project 2, Semester 1, 2022
 *
 * @author Tuan Phong Vu, 1266265
 */
public class ShadowPirate extends AbstractGame {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "ShadowPirate";
    
    private final Image BACKGROUND_IMAGE_0 = new Image("res/background0.png");
    private final Image BACKGROUND_IMAGE_1 = new Image("res/background1.png");
    private Image backgroundImage = BACKGROUND_IMAGE_0;

    private final static String LEVEL0_FILE = "res/level0.csv";
    private final static String LEVEL1_FILE = "res/level1.csv";
    private static String levelFile = LEVEL0_FILE;

    private final static String START_MESSAGE = "PRESS SPACE TO START";
    private final static String ATTACK_INSTRUCTION = "PRESS S TO ATTACK";
    private final static String INSTRUCTION_MESSAGE_0= "USE ARROW KEYS TO FIND LADDER";
    private final static String INSTRUCTION_MESSAGE_1 = "FIND THE TREASURE";
    private static String instructionMessage = INSTRUCTION_MESSAGE_0;

    private final static int LEVEL_TRANSITION_TIME = 3000;
    private final static String END_MESSAGE = "GAME OVER";
    private final static String WIN_MESSAGE_0 = "LEVEL COMPLETE!";
    private final static String WIN_MESSAGE_1 = "CONGRATULATION!";
    private static String winMessage = WIN_MESSAGE_0;



    private final static int INSTRUCTION_OFFSET = 70;
    private final static int FONT_SIZE = 55;
    private final static int FONT_Y_POS = 402;
    private final Font FONT = new Font("res/wheaton.otf", FONT_SIZE);


    private static ArrayList<Object> objects = new ArrayList<>();
    private static ArrayList<Pirate> enemies = new ArrayList<>();

    private Sailor sailor;
    public static boolean gameOn;
    private boolean gameEnd;
    private boolean gameWin;
    private boolean level1 = false;

    private int counter=1;
    private int millisecond;

    public ShadowPirate() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        initializeLevel(levelFile);

    }

    /**
     * Entry point for program
     */
    public static void main(String[] args) {
        ShadowPirate game = new ShadowPirate();
        game.run();
    }
    private void initializeLevel(String csvFileName) {
        readCSV(csvFileName);
        gameWin = false;
        gameEnd = false;
        gameOn = false;
    }
    private void updateLevel1 () {
        backgroundImage = BACKGROUND_IMAGE_1;
        levelFile = LEVEL1_FILE;
        instructionMessage = INSTRUCTION_MESSAGE_1;
        winMessage = WIN_MESSAGE_1;
        level1 = true;
        sailor.healthPoints = sailor.max_health;
        objects.clear();
        enemies.clear();
        initializeLevel(levelFile);
    }



    /**
     * Method taking in a CSV file, read the file and create objects accordingly
     * @param fileName is the file to be read
     */
    private void readCSV(String fileName) {
        
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line;
            if ((line = reader.readLine()) != null) {
                String[] sections = line.split(",");
                if (sections[0].equals("Sailor")) {
                    sailor = new Sailor(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                }
            }
            while ((line = reader.readLine()) != null) {
                String[] sections = line.split(",");
                if (sections[0].equals("Block")) {
                    if (level1) objects.add(new Bomb(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                    else objects.add(new Block(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));

                }
                if (sections[0].equals("Potion")) {
                    objects.add(new Potion(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));

                }
                if (sections[0].equals("Sword")) {
                    objects.add(new Sword(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));

                }
                if (sections[0].equals("Elixir")) {
                    objects.add(new Elixir(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));

                }
                if (sections[0].equals("Treasure")) {
                    objects.add(new Treasure(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                }
                if (sections[0].equals("TopLeft")) {
                    Character.setTopLeft(Integer.parseInt(sections[2]), Integer.parseInt(sections[1]));
                }
                if (sections[0].equals("BottomRight")) {
                    Character.setBottomRight(Integer.parseInt(sections[2]), Integer.parseInt(sections[1]));
                }
                if (sections[0].equals("Pirate")) {
                    enemies.add(new Pirate(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                }
                if (sections[0].equals("Blackbeard") ) {
                   enemies.add(new Blackbeard(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Performs a state update.
     * Pressing escape key allows game to exit.
     * Auto Generate Level 1 after Level 0 is completed
     * @param input Taking in keyboard input.
     */
    @Override
    public void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        if (!gameOn) {
            drawStartScreen(input);
        }

        if (gameEnd) {
            gameEnd = true;
            drawEndScreen(END_MESSAGE);
        }

        if (gameWin) {
            drawEndScreen(winMessage);
            // Start counting when it reaches 3000 millisecond (3second) to change to level1
            counter++;
            millisecond = counter *1000/ sailor.FPS;
            if (millisecond> LEVEL_TRANSITION_TIME && !level1) updateLevel1();
        }

        // when game is running
        if (gameOn && !gameEnd && !gameWin) {
            backgroundImage.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
            for (Object object: objects) object.update();
            for (Pirate enemy: enemies) {
                if (!enemy.isDead())  enemy.update(objects,sailor);
            }

            sailor.update(input, objects, enemies);

            if (sailor.isDead()) {
                gameEnd = true;
            }

            if (sailor.hasWon()) {
                gameWin = true;
            }
        }
    }

    /**
     * Method used to draw the start screen instructions
     * @param input This is mainly to taking in whether a SPACE key has been pressed so it could start the game
     */
    private void drawStartScreen(Input input) {
        FONT.drawString(START_MESSAGE, (Window.getWidth() / 2.0 - (FONT.getWidth(START_MESSAGE) / 2.0)),
                FONT_Y_POS);
        FONT.drawString(instructionMessage, (Window.getWidth() / 2.0 - (FONT.getWidth(instructionMessage) / 2.0)),
                (FONT_Y_POS + INSTRUCTION_OFFSET));
        FONT.drawString(ATTACK_INSTRUCTION, (Window.getWidth() / 2.0 - (FONT.getWidth(ATTACK_INSTRUCTION) / 2.0)),
                (FONT_Y_POS + 2*INSTRUCTION_OFFSET));
        if (input.wasPressed(Keys.SPACE)) {
            gameOn = true;
        }
    }

    /**
     * Method used to draw end screen messages
     * @param message This is the message that we want to draw when the game end (either lose or win)
     */
    private void drawEndScreen(String message) {
        FONT.drawString(message, (Window.getWidth() / 2.0 - (FONT.getWidth(message) / 2.0)), FONT_Y_POS);
    }


}