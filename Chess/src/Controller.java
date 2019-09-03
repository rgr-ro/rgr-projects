import java.util.Iterator;
import java.util.LinkedList;

public class Controller extends java.awt.event.MouseAdapter {

    private ChessboardLearning chessboardLearning;
    private Position positionPressed;

    Controller(ChessboardLearning chessboard){
        this.chessboardLearning = chessboard;
        positionPressed = new Position(-1,-1);
    }

    public void mousePressed(java.awt.event.MouseEvent e){
        System.out.println("Pressed box: X= " +(e.getY()-View.MARGIN)/View.BOX_SIZE + ", Y= " + (e.getX()-View.MARGIN)/View.BOX_SIZE);
        if(chessboardLearning.get(new Position((e.getY()-View.MARGIN)/View.BOX_SIZE,(e.getX()-View.MARGIN)/View.BOX_SIZE)) != null){
            positionPressed = new Position((e.getY()-View.MARGIN)/View.BOX_SIZE,(e.getX()-View.MARGIN)/View.BOX_SIZE);
            LinkedList<Position> positions = chessboardLearning.get(positionPressed).movements(chessboardLearning);
            Iterator it = positions.iterator();
            while(it.hasNext()){
                chessboardLearning.setState((Position) it.next(), IChessboardLearning.State.POSSIBLE_MOVEMENT);
            }
            chessboardLearning.setState(positionPressed, IChessboardLearning.State.ORIGIN_MOVEMENT);
            chessboardLearning.set(chessboardLearning);
        }
    }
    //Select the figure to move and calculate its possible allowed movements.

    public void mouseReleased(java.awt.event.MouseEvent e){
        System.out.println("Released box: X= " +(e.getY()-View.MARGIN)/View.BOX_SIZE + ", Y= " + (e.getX()-View.MARGIN)/View.BOX_SIZE);
        chessboardLearning.initStates();    //To reset the view
        Position positionReleased = new Position((e.getY()-View.MARGIN)/View.BOX_SIZE,(e.getX()-View.MARGIN)/View.BOX_SIZE);
        if(!(positionReleased.getX() == positionPressed.getX() && positionReleased.getY() == positionPressed.getY())){
            System.out.println("Figure origin: X= "+positionPressed.getX()+", Y= "+positionPressed.getY());
            System.out.println("Figure move to position: X= "+positionReleased.getX()+", Y= "+positionReleased.getY());
            Figure fig = chessboardLearning.get(new Position(positionPressed.getX(),positionPressed.getY())).clone();  //catch the current figure
            LinkedList<Position> possiblePositions = fig.movements(chessboardLearning);     //calculate its possible movements
            Iterator it = possiblePositions.iterator();
            boolean found = false;
            while(it.hasNext() && !found){                      //check if the figure has moved to a valid position.
                Position pos = (Position)it.next();
                if(positionReleased.getY() == pos.getY() && positionReleased.getX() == pos.getX())
                    found = true;
            }
            if(found){                  //if the figure has moved correctly
                System.out.println("The figure was moved successfully");
                ChessboardLearning chessboardAux = new ChessboardLearning();
                chessboardAux.copy(chessboardLearning);
                fig.setPosition(positionReleased);
                chessboardAux.set(fig);
                chessboardAux.setNull(positionPressed);
                chessboardAux.setState(positionReleased, IChessboardLearning.State.REAL_MOVEMENT);
                chessboardLearning.set(chessboardAux);    //Refresh the view
                System.out.println("You have move:\n"+chessboardLearning);
                if(!gameOver(chessboardLearning)){
                    chessboardAux.set(aiMove(fig.getColor()));
                    chessboardLearning.set(chessboardAux);    //Refresh the view
                    System.out.println("The AI has moved:\n"+chessboardLearning);
                    if(gameOver(chessboardLearning)){
                        if(chessboardLearning.King(Figure_Color.WHITE))
                            System.out.println("\n\nWHITES HAVE WON");
                        else
                            System.out.println("\n\nBLACKS HAVE WON");
                        chessboardLearning.initChessboard();
                        chessboardLearning.set(chessboardLearning);
                    }
                }
                else{
                    if(chessboardLearning.King(Figure_Color.WHITE))
                        System.out.println("\n\nWHITES HAVE WON");
                    else
                        System.out.println("\n\nBLACKS HAVE WON");
                    chessboardLearning.initChessboard();
                    chessboardLearning.set(chessboardLearning);
                }
            }
        }
    }

    public IChessboard aiMove(Figure_Color color){
        Figure_Color colorAux;
        if(color == Figure_Color.BLACK)
            colorAux = Figure_Color.WHITE;
        else
            colorAux = Figure_Color.BLACK;
        return chessboardLearning.strategy(chessboardLearning.scenarios(colorAux),colorAux);
    }

    public boolean gameOver(ChessboardLearning chessboardLearning){
        return !(chessboardLearning.King(Figure_Color.WHITE) && chessboardLearning.King(Figure_Color.BLACK));
    }

}
