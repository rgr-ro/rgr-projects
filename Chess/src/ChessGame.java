import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChessGame extends java.awt.Frame {
    ChessboardLearning chessboardLearning;
    View view;
    Controller controller;
    ChessGame(){
        chessboardLearning = new ChessboardLearning();
        chessboardLearning.initChessboard();
        view = new View(chessboardLearning);
        chessboardLearning.addPropertyChangeListener(view);
        controller = new Controller(chessboardLearning);
        view.addMouseListener(controller);
        this.add(view);
        addWindowListener(new WindowAdapter()
                          {public void windowClosing(WindowEvent e)
                          {dispose(); System.exit(0);}
                          }
        );
        setBounds(500,50,8 *View.BOX_SIZE+43,8 *View.BOX_SIZE+73);
        setBackground(Color.BLACK);
        setTitle("The Chess Game");
        Toolkit pantalla = Toolkit.getDefaultToolkit();
        Image icono = pantalla.getImage("AjedrezIco.png");
        setIconImage(icono);
        setVisible(true);
        chessboardLearning.set(chessboardLearning);
        System.out.println(chessboardLearning);
    }


    public static void main(java.lang.String[] args){
        new ChessGame();
    }

}
