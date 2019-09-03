import java.util.Iterator;
import java.util.LinkedList;

public class Queen extends Figure {
    public Queen(Figure_Color color, Position position) {
        super(color, position);
    }

    @Override
    public Figure clone() {
        return new Queen(getColor(),getPosition());
    }

    @Override
    public char getRepresentation() {
        return 'Q';
    }

    @Override
    int getValor() {
        return 5;
    }

    @Override
    public LinkedList<Position> movements(IChessboard chessboard) {
        LinkedList<Position> positions = new LinkedList<Position>(); //(f1==f2) || (c1==c2) || (Math.abs(f1-f2) == Math.abs(c1-c2))
        Castle c = new Castle(getColor(),getPosition());
        LinkedList<Position> positionsCastle = c.movements(chessboard);
        Bishop al = new Bishop(getColor(),getPosition());
        LinkedList<Position> positionsBishop = al.movements(chessboard);
        Iterator it_Castle = positionsCastle.iterator();
        Iterator it_Bishop = positionsBishop.iterator();
        while (it_Castle.hasNext())
            positions.add((Position) it_Castle.next());
        while (it_Bishop.hasNext())
            positions.add((Position) it_Bishop.next());
        return positions;
    }
}
