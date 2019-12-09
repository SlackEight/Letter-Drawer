import javax.swing.JFrame;
import java.sql.Array;

public class Driver{
    public static void main(String[] args) {

        int brushSize = 20;
        int screenWidth = 1920;
        int fontSize = 100;
        int screenHeight = 1080;
        String word = "♡_SUP_RYAN_♡";

        JFrame f = new JFrame("Letter Drawer");
        PaintPanel p = new PaintPanel(word, screenWidth, fontSize, brushSize);
        f.setSize(screenWidth,screenHeight);
        f.add(p);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);

    }

}