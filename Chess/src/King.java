import java.util.LinkedList;

@SuppressWarnings("Duplicates")
public class King extends Figure {
    public King(Figure_Color color, Position position) {
        super(color, position);
    }

    @Override
    public Figure clone() {
        return new King(getColor(),getPosition());
    }

    @Override
    public char getRepresentation() {
        return 'K';
    }

    @Override
    int getValor() {
        return 6;
    }

    @Override
    public LinkedList<Position> movements(IChessboard chessboard) {
        LinkedList<Position> positions = new LinkedList<Position>(); //(Math.abs(f1-f2)<=1) && (Math.abs(c1-c2) <=1)
        for(int i = 0; i<chessboard.size(); i++)
            for(int j = 0; j<chessboard.size(); j++){
                if(getPosition().getX()-1 == i && getPosition().getY() == j){ //up
                    Position pos = new Position(i,j);
                    if(chessboard.get(pos) != null){
                        if(chessboard.get(pos).getColor() != getColor())
                            positions.add(pos);
                    }
                    else positions.add(pos);
                }
                if(getPosition().getX() == i && getPosition().getY()-1 == j){ //left
                    Position pos = new Position(i,j);
                    if(chessboard.get(pos) != null){
                        if(chessboard.get(pos).getColor() != getColor())
                            positions.add(pos);
                    }
                    else positions.add(pos);
                }
                if(getPosition().getX() == i && getPosition().getY()+1 == j){ //right
                    Position pos = new Position(i,j);
                    if(chessboard.get(pos) != null){
                        if(chessboard.get(pos).getColor() != getColor())
                            positions.add(pos);
                    }
                    else positions.add(pos);
                }
                if(getPosition().getX()+1 == i && getPosition().getY() == j){ //down
                    Position pos = new Position(i,j);
                    if(chessboard.get(pos) != null){
                        if(chessboard.get(pos).getColor() != getColor())
                            positions.add(pos);
                    }
                    else positions.add(pos);
                }
                if(getPosition().getX()-1 == i && getPosition().getY()-1 == j){//up left
                    Position pos = new Position(i,j);
                    if(chessboard.get(pos) != null){
                        if(chessboard.get(pos).getColor() != getColor())
                            positions.add(pos);
                    }
                    else positions.add(pos);
                }
                if(getPosition().getX()+1 == i && getPosition().getY()+1 == j){ //down right
                    Position pos = new Position(i,j);
                    if(chessboard.get(pos) != null){
                        if(chessboard.get(pos).getColor() != getColor())
                            positions.add(pos);
                    }
                    else positions.add(pos);
                }
                if(getPosition().getX()-1 == i && getPosition().getY()+1 == j){ //up right
                    Position pos = new Position(i,j);
                    if(chessboard.get(pos) != null){
                        if(chessboard.get(pos).getColor() != getColor())
                            positions.add(pos);
                    }
                    else positions.add(pos);
                }
                if(getPosition().getX()+1 == i && getPosition().getY()-1 == j){ //down left
                    Position pos = new Position(i,j);
                    if(chessboard.get(pos) != null){
                        if(chessboard.get(pos).getColor() != getColor())
                            positions.add(pos);
                    }
                    else positions.add(pos);
                }
            }
        return positions;
    }
}
