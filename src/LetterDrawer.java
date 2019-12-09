import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class LetterDrawer{
    // All 26 letters in caps.
    private Letter[] alphabetLetters = new Letter[30];
    // The word we're going to draw.
    private String word = "";
    // Our array of letters that our word has
    private Letter letters[];
    // Keeps track of which letter in the sequence we're on.
    private int currentLetter = 0;
    // Keeps track of which path in the letter we're on.
    private int currentPath = 0;
    // Our current path endpoints
    private double destinationX = 0;
    private double destinationY = 0;
    
    //Our current position
    public double x;
    public double y;

    //Out current velocity;
    public double xV = 0;
    public double yV = 0;

    // Font size
    public double fontSize;

    // Page shift
    public double pageShift;

    // Our current line number
    int lineNum = 0;

    //done running
    private boolean finished = false;

    // number of times we've finished drawing the word
    private int completions = 0;

    // Draw speed
    private double drawSpeed = 4;
    private int sWidth = 0;

    public LetterDrawer(String word, double fontSize, int screenWidth){
        this.fontSize = fontSize;
        this.word = word;
        letters = new Letter[word.length()];
        sWidth = screenWidth;
        pageShift = screenWidth - (fontSize*2)*word.length()+20;
        if(pageShift < 0){
            pageShift = 0;
        }
        pageShift /= 2;

        String fileName = "letters.csv";
        try{
            BufferedReader inFile = new BufferedReader(new FileReader(fileName));
            inFile.readLine();
            String line = inFile.readLine();
            char c = ' ';
            int index = -1;
            while(line != null){
                // Case 1: This line indicates our letter
                // this is when there are no commas.
                if(!line.contains(",")){
                    c = line.charAt(0);
                    index += 1;
                }
                // Case 2: it's the paths for a letter.
                else{
                    // Split the line into each path
                    String[] paths = line.split("-");

                    // Split each path into ints and add them to the Letter.
                    double[][] values = new double[paths.length][4];

                    for(int i=0; i<paths.length; i++){

                        String[] pV = paths[i].split(",");
                        values[i][0] = Double.parseDouble(pV[0])*fontSize;
                        values[i][1] = Double.parseDouble(pV[1])*fontSize;
                        values[i][2] = Double.parseDouble(pV[2])*fontSize;
                        values[i][3] = Double.parseDouble(pV[3])*fontSize;
                    }
                    
                    for(double[] path : values){
                        System.out.println("Path for "+c+": "+path[0]+", "+path[1]+", "+path[2]+", "+path[3]);
                    }
                    alphabetLetters[index] = new Letter(c, values);
                }
                line = inFile.readLine();
            }

            populateLetterArray();
        }
        catch(IOException e){
            System.out.println("The file "+fileName+" was not found! It needs to be in the same directory as LetterDrawer.class");
        }
    }

    public void populateLetterArray(){
        // Used to keep track of how far back things need to be shifted when they move to the next line
        double correction = 0;
        int lineNo = 0;
        // for each letter in our word string
        for(int i=0; i<word.length(); i++){
            boolean found = false;
            int searchCnt = 0;
            while(!found){
                //System.out.println("Is "+word.toCharArray()[i]+" == "+alphabetLetters[searchCnt].getChar());
                if(word.toCharArray()[i] == alphabetLetters[searchCnt].getChar()){
                    letters[i] = new Letter(alphabetLetters[searchCnt]);

                    double xShift = ((i)*fontSize*2)+pageShift;//+pageShift);//%(sWidth -fontSize*2);
                    xShift -= correction;
                    if((xShift+fontSize*2)>sWidth){
                        /*
                        correction += (xShift+fontSize*2)%sWidth+sWidth;
                        xShift = ((i)*fontSize*2);
                        xShift -= correction;
                        */
                        correction += xShift;
                        xShift = 0;
                        lineNo++;
                    }
                    System.out.println("XSHIFT: "+xShift);
                    double yShift = -fontSize*2.5*(int)(lineNo);
                    letters[i].shiftBy(xShift, yShift);
                    found = true;
                }
                searchCnt++;
            }
        }

        setDestination();
    }


    public Vector getNextPosition(){
        if(!finished){
        x += xV*drawSpeed;
        y += yV*drawSpeed;

        // If we've reached the next point ie. finished our path

        //if(Math.abs(x-destinationX)<1 && Math.abs(y-destinationY) < 1){
            if((x-destinationX)*(letters[currentLetter].paths[currentPath].startX-destinationX)<0
                    || (y-destinationY)*(letters[currentLetter].paths[currentPath].startY-destinationY)<0){
            currentPath++;
            // If we've reached end of letter.

            if(currentPath == letters[currentLetter].getTotPaths()){
                currentLetter++;
                currentPath = 0;
                completions++;
            }

            if(currentLetter == word.length()){
                //System.out.println("All done!");
                //finished = true;
                currentLetter = 0;
                currentPath = 0;
            }

            // set our new start and destinations
            setDestination();
        }
        return new Vector(x,y);
    }
    else{
        return new Vector(0, 0);
    }
    }

    private void setDestination(){
        x = letters[currentLetter].paths[currentPath].startX;
        destinationX = letters[currentLetter].paths[currentPath].endX;

        y = letters[currentLetter].paths[currentPath].startY;
        destinationY = letters[currentLetter].paths[currentPath].endY;

            xV = destinationX - x;
            yV = destinationY - y;
            double straightLineDistance = Math.sqrt(Math.pow(xV,2)+Math.pow(yV,2));

            // Normalize the velocity vector
            xV /= straightLineDistance;
            yV /= straightLineDistance;
            //xV *= 2;
            //yV *= 2;
    }

    public int getTotalCompletions(){
        return completions;
    }


}