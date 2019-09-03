import java.awt.*;

public interface IChessboardLearning extends IChessboard{
    public static enum Box {
        BLANCA(Color.ORANGE.darker()), NEGRO(Color.ORANGE.darker().darker().darker());

        private Color color;

        Box(Color color) {
            this.color = color;
        }

        public java.awt.Color getColor​(){
            return this.color;
        }

        public static java.awt.Color getColor​(int valor){
            Box[] colores = Box.values();
            return colores[valor].getColor​();
        }

        public int	getValue​(){
            return this.ordinal();
        }
    }

    public static enum State {
        EMPTY,DESTINANTION_MOVEMENT,ORIGIN_MOVEMENT,POSSIBLE_MOVEMENT,REAL_MOVEMENT;

        public int	getValue​(){
            return this.ordinal();
        }
    }

    public static Box getColor(Position position){
        if(position.getX()>=0 && position.getX()<8 && position.getY()>=0 && position.getY()<8){
            if(position.getX()%2 == 0  && position.getY()%2 == 0)
                return Box.BLANCA;
            else if(position.getX()%2 == 0  && position.getY()%2 != 0)
                return Box.NEGRO;
            else if(position.getX()%2 != 0  && position.getY()%2 == 0)
                return Box.NEGRO;
            else if(position.getX()%2 != 0  && position.getY()%2 != 0)
                return Box.BLANCA;
        }
        return  null;
    }
    //Indicates if the box is white or black.

    public State getState(Position position);
    //Get the state of one position on the chess board.

    public void initStates();
    //Assign to all boxes the EMPTY State.

    public void setState(Position position, IChessboardLearning.State state);
    //Update the chess board.

}
