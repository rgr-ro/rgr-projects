public interface IChessboard {
    java.util.LinkedList<Position> bishops(Figure_Color color);
    //Return the bishops positions, of the selected color, on the Chessboard.

    java.util.LinkedList<Position> allFigures(Figure_Color color);
    //Get the positions of all figures, of the selected color, on the Chessboard.

    java.util.LinkedList<Position>	knights(Figure_Color color);
    //Return the knights positions, of the selected color, on the Chessboard.

    IChessboard clone();

    java.util.LinkedList<IChessboard> scenarios(Figure_Color color);
    //Generate all the possible scenarios (Chessboards), by moving the figure of the selected colour, starting from an existing board.

    IChessboard strategy(java.util.LinkedList<IChessboard> scenarios, Figure_Color color);
    //Implements the game strategy: return the best movement to try win the human.

    Figure[][] get();
    //return the complete Chessboard.

    Figure get(Position position);

    void initChessboard();
    //put the Figures to start the game.

    Position[] movement(IChessboard chessboardTarget);
    //Show the movement between the current chessboard to the target chessboard (chessboardTarget).

    java.util.LinkedList<Position> pawns(Figure_Color color);
    //Return the pawns positions, of the selected color, on the Chessboard.

    Position queen(Figure_Color color);
    //Return the Queen's position, of the selected color, on the Chessboard.

    boolean King(Figure_Color color);
    //Return true or false if the king is on the chess board or not.

    Position king(Figure_Color color);

    void set(Figure figure);
    //To introduce the Figure to the Chessboard; The Figures have their own positions; If the figure is null the position will be empty.

    void set(IChessboard chessboard);
    //Update the Chessboard.

    void setNull(Position position);
    //Delete one Figure from the chess board.

    int	size();
    //Total number of figures (whites and blacks) on the chessboard.

    int	size(Figure_Color color);
    //Total number of whites or blacks figures on the chessboard.

    int	sizeBishops(Figure_Color color);
    //Number of Bishops on the chessboard of the selected colour.

    int	sizeKnights(Figure_Color color);
    //Number of Knights on the chessboard of the selected colour.

    int	sizePawns(Figure_Color color);
    //Number of Pawns on the chessboard of the selected colour.

    int	sizeCastles(Figure_Color color);
    //Number of Castles on the chessboard of the selected colour.

    java.util.LinkedList<Position>	castles(Figure_Color color);
    //Return the Castles positions on the chessboard of the selected colour.

    java.lang.String toString();

    int	valor(Figure_Color color);
    //Chessboard value of the selected colour.
}

