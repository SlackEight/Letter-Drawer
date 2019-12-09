public class Letter{
    
    private char letter;
    private int totalPaths;
    public Path[] paths;
    
    public Letter(char c, double[][] n){
        letter = c;
        if((n[0].length % 4) != 0){
            System.out.println("Invalid number of values. This letter has a corrupted path. Letter: "+c);
            return;
        }
        paths = new Path[n.length];

        for(int i=0; i<paths.length; i++){
            paths[i] = new Path(n[i][0], n[i][1], n[i][2], n[i][3]);
        }

        totalPaths = paths.length;
        
    }

    public Letter(Letter copy){
        letter = copy.letter;
        paths = copy.copy();
        totalPaths = copy.totalPaths;
    }

    public int getTotPaths(){
        return totalPaths;
    }

    public char getChar(){
        return letter;
    }

    public void shiftBy(double xShift, double yShift){
            for(Path path : paths){
                path.startX += xShift;
                path.endX += xShift;
                path.startY += yShift;
                path.endY += yShift;

            }
    }

    public class Path{
        public double startX;
        public double startY;

        public double endX;
        public double endY;

        public Path(double x, double y, double ex, double ey){
            startX = x;
            startY = y;
            endX = ex;
            endY = ey;
        }

        public Path(double[] values){
            startX = values[0];
            startY = values[1];
            endX = values[2];
            endY = values[3];
        }

    }
    public Path[] copy(){
            char c = letter;
            int tP = totalPaths;
            Path[] copiedPaths = new Path[totalPaths];
            for(int i=0; i<totalPaths; i++){
                copiedPaths[i] = new Path(paths[i].startX, paths[i].startY, paths[i].endX, paths[i].endY);
            }
            return copiedPaths;
        }

    
}