import java.util.LinkedList;

public class Bishop extends Figure{

    public Bishop(Figure_Color color, Position position) {
        super(color, position);
    }

    @Override
    public Figure clone() {
        return new Bishop(getColor(),getPosition());
    }

    @Override
    public char getRepresentation() {
        return 'B';
    }

    @Override
    int getValor() {
        return 4;
    }

    @Override
    public LinkedList<Position> movements(IChessboard chessboard) {
        LinkedList<Position> positions = new LinkedList<Position>();
        boolean found1 = false;
        boolean found2 = false;
        boolean found3 = false;
        boolean found4 = false;
        for(int k = 1; k<chessboard.size(); k++){
            if(getPosition().getX()+k >=0 && getPosition().getX()+k<8 && getPosition().getY()+k>=0 && getPosition().getY()+k<8 && !found1){
                Position pos = new Position(getPosition().getX()+k,getPosition().getY()+k);
                if(chessboard.get(pos) != null){
                    found1=true;
                    if(chessboard.get(pos).getColor() != getColor())
                        positions.add(pos);
                }
                else positions.add(pos);
            }
            if(getPosition().getX()-k >=0 && getPosition().getX()-k<8 && getPosition().getY()+k>=0 && getPosition().getY()+k<8 && !found2){
                Position pos = new Position(getPosition().getX()-k,getPosition().getY()+k);
                if(chessboard.get(pos) != null){
                    found2=true;
                    if(chessboard.get(pos).getColor() != getColor()){
                        positions.add(pos);
                    }
                }
                else positions.add(pos);
            }
            if(getPosition().getX()+k >=0 && getPosition().getX()+k<8 && getPosition().getY()-k>=0 && getPosition().getY()-k<8 && !found3){
                Position pos = new Position(getPosition().getX()+k,getPosition().getY()-k);
                if(chessboard.get(pos) != null){
                    found3=true;
                    if(chessboard.get(pos).getColor() != getColor())
                        positions.add(pos);
                }
                else positions.add(pos);
            }
            if(getPosition().getX()-k >=0 && getPosition().getX()-k<8 && getPosition().getY()-k>=0 && getPosition().getY()-k<8 && !found4){
                Position pos = new Position(getPosition().getX()-k,getPosition().getY()-k);
                if(chessboard.get(pos) != null){
                    found4=true;
                    if(chessboard.get(pos).getColor() != getColor())
                        positions.add(pos);
                }
                else positions.add(pos);
            }
        }
        return positions;
    }
}
