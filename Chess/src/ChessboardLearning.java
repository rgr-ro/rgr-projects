public class ChessboardLearning extends Chessboard implements IChessboardLearning{

    private State[][] tableroLearning;

    public ChessboardLearning(){
        super();
        initStates();
    }

    @Override
    public State getState(Position position) {
        return tableroLearning[position.getX()][position.getY()];
    }

    @Override
    public void initStates() {
        tableroLearning = new State[SIZE][SIZE];
        for(int i = 0; i<SIZE; i++)
            for(int j = 0; j<SIZE; j++)
                if(tableroLearning[i][j] != State.REAL_MOVEMENT)
                    tableroLearning[i][j] = State.EMPTY;
    }

    @Override
    public void setState(Position position, State state) {
        tableroLearning[position.getX()][position.getY()] = state;
    }
}
