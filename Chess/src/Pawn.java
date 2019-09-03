import java.util.LinkedList;

public class Pawn extends  Figure{
    public Pawn(Figure_Color color, Position position) {
        super(color, position);
    }

    @Override
    public Figure clone() {
        return new Pawn(getColor(),getPosition());
    }

    @Override
    public char getRepresentation() {
        return 'P';
    }

    @Override
    int getValor() {
        return 1;
    }

    @Override
    public LinkedList<Position> movements(IChessboard chessboard) {
        LinkedList<Position> positions = new LinkedList<Position>();
        for(int i = 0; i<chessboard.size(); i++)
            for(int j = 0; j<chessboard.size(); j++)
                if(getColor() == Figure_Color.WHITE  && getPosition().getX()-1 == i && getPosition().getY() == j){
                    Position pos = new Position(i,j);
                    if(chessboard.get(pos) == null){
                        positions.add(pos);
                    }
                }
                else if(getColor() == Figure_Color.BLACK  && getPosition().getX()+1 == i && getPosition().getY() == j){
                    Position pos = new Position(i,j);
                    if(chessboard.get(pos) == null){
                        positions.add(pos);
                    }
                }
                else if(getColor() == Figure_Color.WHITE && getPosition().getX() == 6 && getPosition().getX()-2 == i && getPosition().getY() == j){
                    Position pos = new Position(getPosition().getX()-1,getPosition().getY());
                    if(chessboard.get(pos) == null){
                        pos = new Position(i,j);
                        if(chessboard.get(pos) == null){
                            positions.add(pos);
                        }
                    }
                }
                else if(getColor() == Figure_Color.BLACK && getPosition().getX() == 1 && getPosition().getX()+2 == i && getPosition().getY() == j){
                    Position pos = new Position(getPosition().getX()+1,getPosition().getY());
                    if(chessboard.get(pos) == null){
                        pos = new Position(i,j);
                        if(chessboard.get(pos) == null){
                            positions.add(pos);
                        }
                    }
                }
                else if(getColor() == Figure_Color.WHITE  && (getPosition().getX()-1 == i && (getPosition().getY()-1 == j || getPosition().getY()+1 == j))){  //It is eating a figure
                    Position pos = new Position(i,j);
                    if(chessboard.get(pos) != null){
                        if(chessboard.get(pos).getColor() != getColor())
                            positions.add(pos);
                    }
                }
                else if(getColor() == Figure_Color.BLACK  && (getPosition().getX()+1 == i && (getPosition().getY()-1 == j || getPosition().getY()+1 == j))){   //It is eating a figure
                    Position pos = new Position(i,j);
                    if(chessboard.get(pos) != null){
                        if(chessboard.get(pos).getColor() != getColor())
                            positions.add(pos);
                    }
                }
        return positions;
    }
}
