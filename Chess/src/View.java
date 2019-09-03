import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

@SuppressWarnings("Duplicates")
public class View extends Canvas implements PropertyChangeListener {

    static int	BOX_SIZE = 60;
    static int	MARGIN = 10;
    private Font font = new Font("Arial Bold",Font.BOLD,30);

    private Graphics2D g2d;
    ChessboardLearning chessboard;

    public View(Chessboard model){
        chessboard = new ChessboardLearning();
        chessboard.set(model);
    }

    public void paint​(Graphics g) {
        g2d = (Graphics2D) g;
        g2d.setFont(font);
        for (int x=0;x<chessboard.size();x++)
            for (int y=0;y<chessboard.size();y++) {
                g2d.setColor(IChessboardLearning.getColor(new Position(x,y)).getColor​());
                g2d.fillRect(y*BOX_SIZE+MARGIN, x*BOX_SIZE+MARGIN, BOX_SIZE, BOX_SIZE);
                if(chessboard.getState(new Position(x,y)) == IChessboardLearning.State.DESTINANTION_MOVEMENT){
                    g2d.setColor(Color.GREEN);
                    g2d.fillOval(y*BOX_SIZE+2*MARGIN+3, x*BOX_SIZE+2*MARGIN+3, BOX_SIZE-25, BOX_SIZE-25);
                }
                else if(chessboard.getState(new Position(x,y)) == IChessboardLearning.State.ORIGIN_MOVEMENT){
                    g2d.setColor(Color.BLUE);
                    g2d.fillOval(y*BOX_SIZE+2*MARGIN+3, x*BOX_SIZE+2*MARGIN+3, BOX_SIZE-25, BOX_SIZE-25);
                }
                else if(chessboard.getState(new Position(x,y)) == IChessboardLearning.State.POSSIBLE_MOVEMENT){
                    g2d.setColor(Color.CYAN);
                    g2d.fillOval(y*BOX_SIZE+2*MARGIN+3, x*BOX_SIZE+2*MARGIN+3, BOX_SIZE-25, BOX_SIZE-25);
                }
                else if(chessboard.getState(new Position(x,y)) == IChessboardLearning.State.REAL_MOVEMENT){
                    g2d.setColor(Color.GREEN);
                    g2d.fillOval(y*BOX_SIZE+2*MARGIN+3, x*BOX_SIZE+2*MARGIN+3, BOX_SIZE-25, BOX_SIZE-25);
                }
                if(chessboard.get(new Position(x,y)) != null){
                    g2d.setColor(chessboard.get(new Position(x,y)).getColor().getColor​());
                    g2d.drawString(chessboard.get(new Position(x,y)).getRepresentation()+"",y*BOX_SIZE+3*MARGIN,x*BOX_SIZE+6*MARGIN);
                }
            }
    }

    public void propertyChange(PropertyChangeEvent evt) {  //To refresh the view when the chess board is updated
        chessboard = (ChessboardLearning) evt.getNewValue();
        repaint();
    }
}