import java.awt.*;
import javax.swing.JPanel;
import java.util.concurrent.TimeUnit;

public class PaintPanel extends JPanel{

    private double x = 0;
    private double y = 20;
    private LetterDrawer ld;
    private int brushSize = 20;
    private int maxBrushSize = 20;
    private int fontSize = 60;
    private int screenW;

    private int counter = 0;
    
    int cnt = 0;

    public PaintPanel(String word, int screenWidth, int fontSize, int brushSize){
        super();
        maxBrushSize = brushSize;
        screenW = screenWidth;
        this.setSize(screenWidth,1000);
        this.setVisible(true);
        ld = new LetterDrawer(word,fontSize, screenWidth);
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        Vector temp = ld.getNextPosition();
        x = temp.x;
        y = fontSize*4-temp.y;

        if(cnt <= 4){
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, screenW, 1440);
        }
        counter = ld.getTotalCompletions()%4+1;
        setColorTo(g2d, counter);
        g2d.fillOval((int)x, (int)y, brushSize, brushSize);
        cnt++;
        repaint();
    }

    public void setColorTo(Graphics2D g, int count){
        switch(count){
            case(1): g.setColor(Color.MAGENTA); brushSize = (int)(Math.random()*maxBrushSize+1); break;

            case(2): g.setColor(Color.ORANGE); brushSize = (int)(Math.random()*maxBrushSize+1); break;//x-=2; y-=2; break;

            case(3): g.setColor(Color.RED); brushSize = (int)(Math.random()*maxBrushSize+1); break;//x-=4; y-=4;break;

            case(4): g.setColor(Color.GRAY); brushSize = (int)(Math.random()*maxBrushSize+1); break;//x-=6; y-=6;break;

            //case(5): g.setColor(Color.BLACK); brushSize = 32; x-=8; y-=8; break;
        }
    }

}