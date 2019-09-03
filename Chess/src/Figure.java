import java.util.Iterator;
import java.util.LinkedList;

public abstract class Figure {

    private Figure_Color color;
    private Position position;

    public Figure(Figure_Color color, Position position){
        this.color = color;
        this.position = position;
    }

    public java.util.LinkedList<IChessboard> scenarios(IChessboard chessboard){   // Get the possible scenarios (Chessboards) by moving this Figure
        LinkedList<Position> positions = movements(chessboard);
        Iterator it_pos = positions.iterator();
        LinkedList<IChessboard> scenarios = new LinkedList<IChessboard>();
        Position posOrigin = new Position(getPosition().getX(),getPosition().getY());
        while(it_pos.hasNext()){
            Chessboard tabAux = new Chessboard(chessboard);
            tabAux.setNull(posOrigin);
            Position pos = (Position) it_pos.next();
            if(getRepresentation() == 'P'){
                Pawn pawn = new Pawn(getColor(),pos);
                tabAux.set(pawn);
            }
            else if(getRepresentation() == 'L'){
                Knight knight = new Knight(getColor(),pos);
                tabAux.set(knight);
            }
            else if(getRepresentation() == 'Q'){
                Queen gueen = new Queen(getColor(),pos);
                tabAux.set(gueen);
            }
            else if(getRepresentation() == 'C'){
                Castle castle = new Castle(getColor(),pos);
                tabAux.set(castle);
            }
            else if(getRepresentation() == 'K'){
                King king = new King(getColor(),pos);
                tabAux.set(king);
            }
            else{
                Bishop bishop = new Bishop(getColor(),pos);
                tabAux.set(bishop);
            }
            scenarios.add(tabAux);
        }
        return scenarios;
    }

    public Figure_Color getColor(){    //Devuelve el color de la Figure.
        return color;
    }

    public Position getPosition(){     //Indica la posición de la Figure, en el chessboard, en cualquier momento de la partida.
        return position;
    }

    void setPosition(Position position){
        this.position.setX(position.getX());
        this.position.setY(position.getY());
    }

    //ABSTRACT:

    public abstract Figure	clone();

    public abstract char getRepresentation();

    abstract int getValor();    // The heuristic value of the figure. The higher the value, the more important it is


    public abstract java.util.LinkedList<Position> movements(IChessboard chessboard);      //Calculate yhe possible movements for the current figure.


}
