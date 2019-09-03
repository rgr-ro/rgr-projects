import java.util.LinkedList;

public class Knight extends Figure {
    public Knight(Figure_Color color, Position posicion) {
        super(color, posicion);
    }

    @Override
    public Figure clone() {
        return new Knight(getColor(),getPosition());
    }

    @Override
    public char getRepresentation() {
        return 'L';
    }

    @Override
    int getValor() {
        return 3;
    }

    @Override
    public LinkedList<Position> movements(IChessboard chessboard) {
        LinkedList<Position> positions = new LinkedList<Position>();
        for(int i = 0; i<chessboard.size(); i++)
            for(int j = 0; j<chessboard.size(); j++)
                if((getPosition().getX() - i) * (getPosition().getX() - i) + (getPosition().getY() - j) * (getPosition().getY() - j) == 5){ //(f1-f2)*(f1-f2) +(c1-c2)*(c1-c2) == 5
                    Position pos = new Position(i,j);
                    if(chessboard.get(pos) != null){
                        if(chessboard.get(pos).getColor() != getColor())
                            positions.add(pos);
                    }
                    else positions.add(pos);
                }
        return positions;
    }
}
