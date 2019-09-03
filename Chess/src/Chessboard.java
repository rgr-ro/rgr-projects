import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

@SuppressWarnings("Duplicates")
public class Chessboard implements IChessboard {

    static final int SIZE = 8;
    private Figure[][] chessboard;
    private PropertyChangeSupport support;

    Chessboard() {
        support = new PropertyChangeSupport(this);
        this.chessboard = new Figure[SIZE][SIZE];
    }

    Chessboard(Figure[][] chessboard) {
        support = new PropertyChangeSupport(this);
        this.chessboard = new Figure[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                this.chessboard[i][j] = chessboard[i][j];

    }

    Chessboard(IChessboard chessboard) {
        support = new PropertyChangeSupport(this);
        this.chessboard = new Figure[SIZE][SIZE];
        set(chessboard);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    @Override
    public LinkedList<Position> bishops(Figure_Color color) {
        LinkedList<Position> positions = new LinkedList<Position>();
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (chessboard[i][j] != null)
                    if (chessboard[i][j].getRepresentation() == 'B' && chessboard[i][j].getColor() == color)
                        positions.add(chessboard[i][j].getPosition());
        return positions;
    }

    @Override
    public LinkedList<Position> allFigures(Figure_Color color) {
        LinkedList<Position> positions = new LinkedList<Position>();
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (chessboard[i][j] != null)
                    if (chessboard[i][j].getColor() == color)
                        positions.add(new Position(i, j));
        return positions;
    }

    @Override
    public LinkedList<Position> knights(Figure_Color color) {
        LinkedList<Position> positions = new LinkedList<Position>();
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (chessboard[i][j] != null)
                    if (chessboard[i][j].getRepresentation() == 'L' && chessboard[i][j].getColor() == color)
                        positions.add(chessboard[i][j].getPosition());
        return positions;
    }

    @Override
    public IChessboard clone() {
        Chessboard tab = new Chessboard();
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                tab.set(chessboard[i][j]);
        return tab;
    }

    @Override
    public LinkedList<IChessboard> scenarios(Figure_Color color) {
        LinkedList<IChessboard> scenariosTotal = new LinkedList<IChessboard>();
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (chessboard[i][j] != null)
                    if (chessboard[i][j].getColor() == color) {
                        Chessboard tabAux = new Chessboard(chessboard);
                        LinkedList<IChessboard> scenarios = chessboard[i][j].scenarios(tabAux);
                        Iterator it = scenarios.iterator();
                        while (it.hasNext()) {
                            scenariosTotal.add((Chessboard) it.next());
                        }
                    }
        return scenariosTotal;
    }

    @Override
    public IChessboard strategy(LinkedList<IChessboard> scenarios, Figure_Color color) {
        LinkedList<IChessboard> bestScenarios = new LinkedList<IChessboard>();
        Iterator it = scenarios.iterator();
        Chessboard bestChessboard = new Chessboard();
        Figure_Color colorContrary;
        if (color == Figure_Color.WHITE)            //strategy for the white ones
            colorContrary = Figure_Color.BLACK;
        else colorContrary = Figure_Color.WHITE;
        int sizeCu = size(colorContrary);          //size of current contrary figures chessboard

        while (it.hasNext()) {
            Chessboard chessboardAux = (Chessboard) it.next();
            if (chessboardAux.size(colorContrary) < sizeCu)
                bestScenarios.add(chessboardAux);
        }

        Iterator it_B = bestScenarios.iterator();

        if (!it_B.hasNext()) {        //choose a movement in which the figure is not eaten
            it = scenarios.iterator();
            sizeCu = size(color);
            boolean eaten;
            while (it.hasNext()) {
                Chessboard chessboardAux = (Chessboard) it.next();
                LinkedList<IChessboard> scenariosNextBoard = chessboardAux.scenarios(colorContrary);  // get all the scenarios of the contrary figures.
                Iterator it_esc = scenariosNextBoard.iterator();
                eaten = false;
                while (it_esc.hasNext()) {
                    Chessboard chessboardAux2 = (Chessboard) it_esc.next();
                    if (chessboardAux2.size(color) < sizeCu)
                        eaten = true;
                }
                if (!eaten)             // choose a scenario in which the figure is not eaten.
                    bestScenarios.add(chessboardAux);
            }
            it_B = bestScenarios.iterator();            //we choose a random chessboard
            if (it_B.hasNext()) {
                Random rx = new Random();
                int x = rx.nextInt(bestScenarios.size());
                int k = 0;
                while (it_B.hasNext() && k < x) {
                    it_B.next();
                    k++;
                }
                bestChessboard = (Chessboard) it_B.next();
            } else {
                it = scenarios.iterator();
                Random rx = new Random();
                int x = rx.nextInt(scenarios.size());
                int k = 0;
                while (it.hasNext() && k < x) {
                    it.next();
                    k++;
                }
                bestChessboard = (Chessboard) it.next();
            }
        } else {       //we have eaten a figure, we have to eaten the figure with the biggest heuristic value
            int min = Integer.MAX_VALUE;
            while (it_B.hasNext()) {
                Chessboard chessboardAux = (Chessboard) it_B.next();
                if (chessboardAux.valor(colorContrary) < min) {
                    bestChessboard = new Chessboard(chessboardAux);
                    min = chessboardAux.valor(colorContrary);
                }
            }
        }
        return bestChessboard;
    }

    @Override
    public Figure[][] get() {
        return chessboard;
    }

    @Override
    public Figure get(Position position) {
        return chessboard[position.getX()][position.getY()];
    }

    @Override
    public void initChessboard() {
        chessboard = new Figure[SIZE][SIZE];
        iniFigures();
    }

    private void iniFigures() {
        Pawn pWhite1 = new Pawn(Figure_Color.WHITE, new Position(6, 0));
        Pawn pWhite2 = new Pawn(Figure_Color.WHITE, new Position(6, 1));
        Pawn pWhite3 = new Pawn(Figure_Color.WHITE, new Position(6, 2));
        Pawn pWhite4 = new Pawn(Figure_Color.WHITE, new Position(6, 3));
        Pawn pWhite5 = new Pawn(Figure_Color.WHITE, new Position(6, 4));
        Pawn pWhite6 = new Pawn(Figure_Color.WHITE, new Position(6, 5));
        Pawn pWhite7 = new Pawn(Figure_Color.WHITE, new Position(6, 6));
        Pawn pWhite8 = new Pawn(Figure_Color.WHITE, new Position(6, 7));

        Castle cWhite1 = new Castle(Figure_Color.WHITE, new Position(7, 0));
        Castle cWhite2 = new Castle(Figure_Color.WHITE, new Position(7, 7));

        Knight lWhite1 = new Knight(Figure_Color.WHITE, new Position(7, 1));
        Knight lWhite2 = new Knight(Figure_Color.WHITE, new Position(7, 6));

        Bishop bWhite1 = new Bishop(Figure_Color.WHITE, new Position(7, 2));
        Bishop bWhite2 = new Bishop(Figure_Color.WHITE, new Position(7, 5));

        Queen qWhite = new Queen(Figure_Color.WHITE, new Position(7, 3));

        King kWhite = new King(Figure_Color.WHITE, new Position(7, 4));


        Pawn pBlack1 = new Pawn(Figure_Color.BLACK, new Position(1, 0));
        Pawn pBlack2 = new Pawn(Figure_Color.BLACK, new Position(1, 1));
        Pawn pBlack3 = new Pawn(Figure_Color.BLACK, new Position(1, 2));
        Pawn pBlack4 = new Pawn(Figure_Color.BLACK, new Position(1, 3));
        Pawn pBlack5 = new Pawn(Figure_Color.BLACK, new Position(1, 4));
        Pawn pBlack6 = new Pawn(Figure_Color.BLACK, new Position(1, 5));
        Pawn pBlack7 = new Pawn(Figure_Color.BLACK, new Position(1, 6));
        Pawn pBlack8 = new Pawn(Figure_Color.BLACK, new Position(1, 7));

        Castle cBlack1 = new Castle(Figure_Color.BLACK, new Position(0, 0));
        Castle cBlack2 = new Castle(Figure_Color.BLACK, new Position(0, 7));

        Knight lBlack1 = new Knight(Figure_Color.BLACK, new Position(0, 1));
        Knight lBlack2 = new Knight(Figure_Color.BLACK, new Position(0, 6));

        Bishop bBlack1 = new Bishop(Figure_Color.BLACK, new Position(0, 2));
        Bishop bBlack2 = new Bishop(Figure_Color.BLACK, new Position(0, 5));

        Queen qBlack = new Queen(Figure_Color.BLACK, new Position(0, 3));

        King kBlack = new King(Figure_Color.BLACK, new Position(0, 4));

        chessboard[6][0] = pWhite1;
        chessboard[6][1] = pWhite2;
        chessboard[6][2] = pWhite3;
        chessboard[6][3] = pWhite4;
        chessboard[6][4] = pWhite5;
        chessboard[6][5] = pWhite6;
        chessboard[6][6] = pWhite7;
        chessboard[6][7] = pWhite8;

        chessboard[1][0] = pBlack1;
        chessboard[1][1] = pBlack2;
        chessboard[1][2] = pBlack3;
        chessboard[1][3] = pBlack4;
        chessboard[1][4] = pBlack5;
        chessboard[1][5] = pBlack6;
        chessboard[1][6] = pBlack7;
        chessboard[1][7] = pBlack8;

        chessboard[0][0] = cBlack1;
        chessboard[0][7] = cBlack2;
        chessboard[7][0] = cWhite1;
        chessboard[7][7] = cWhite2;

        chessboard[7][1] = lWhite1;
        chessboard[7][6] = lWhite2;
        chessboard[0][1] = lBlack1;
        chessboard[0][6] = lBlack2;

        chessboard[7][2] = bWhite1;
        chessboard[7][5] = bWhite2;
        chessboard[0][2] = bBlack1;
        chessboard[0][5] = bBlack2;

        chessboard[7][3] = qWhite;
        chessboard[0][3] = qBlack;

        chessboard[7][4] = kWhite;
        chessboard[0][4] = kBlack;
    }

    @SuppressWarnings({"SingleStatementInBlock", "Duplicates"})
    @Override
    public Position[] movement(IChessboard chessboardTarget) {
        Position[] positionsArray = new Position[2];
        LinkedList<Position> positionsAfter;
        LinkedList<Position> positionsBefore;
        Iterator it_posAfter;
        Iterator it_posBefore;
        Position posOrigin = new Position(-1, -1);
        Position posMoved = new Position(-1, -1);
        boolean found = false;
        if (size(Figure_Color.WHITE) != chessboardTarget.size(Figure_Color.WHITE) || size(Figure_Color.BLACK) != chessboardTarget.size(Figure_Color.BLACK)) {//if there isn't the same number of figures as the current chessboard in the target one.
            if (size(Figure_Color.BLACK) > chessboardTarget.size(Figure_Color.BLACK)) { //if the figures who eat are white
                positionsAfter = chessboardTarget.allFigures(Figure_Color.WHITE);
                positionsBefore = allFigures(Figure_Color.WHITE);
                it_posAfter = positionsAfter.iterator();
                it_posBefore = positionsBefore.iterator();
            } else {//if the figures who eat are black
                positionsAfter = chessboardTarget.allFigures(Figure_Color.BLACK);
                positionsBefore = allFigures(Figure_Color.BLACK);
                it_posAfter = positionsAfter.iterator();
                it_posBefore = positionsBefore.iterator();
            }
            while (it_posBefore.hasNext() && !found) {
                if (it_posAfter.hasNext()) {
                    posOrigin = (Position) it_posBefore.next();
                    posMoved = (Position) it_posAfter.next();
                    if (posOrigin.getY() != posMoved.getY() || posOrigin.getX() != posMoved.getX())
                        found = true;
                }
            }
            positionsArray[0] = posOrigin;
            positionsArray[1] = posMoved;
        } else {
            positionsAfter = chessboardTarget.allFigures(Figure_Color.WHITE);
            positionsBefore = allFigures(Figure_Color.WHITE);
            it_posAfter = positionsAfter.iterator();
            it_posBefore = positionsBefore.iterator();
            while (it_posBefore.hasNext() && !found) {
                if (it_posAfter.hasNext()) {
                    posOrigin = (Position) it_posBefore.next();
                    posMoved = (Position) it_posAfter.next();
                    if ((posOrigin.getY() != posMoved.getY()) || (posOrigin.getX() != posMoved.getX()))
                        found = true;
                }
            }
            if (found) {
                positionsArray[0] = posOrigin;
                positionsArray[1] = posMoved;
            } else {
                positionsAfter = chessboardTarget.allFigures(Figure_Color.BLACK);
                positionsBefore = allFigures(Figure_Color.BLACK);
                it_posAfter = positionsAfter.iterator();
                it_posBefore = positionsBefore.iterator();
                while (it_posBefore.hasNext() && !found) {
                    if (it_posAfter.hasNext()) {
                        posOrigin = (Position) it_posBefore.next();
                        posMoved = (Position) it_posAfter.next();
                        if (posOrigin.getY() != posMoved.getY() || posOrigin.getX() != posMoved.getX())
                            found = true;
                    }
                }
                positionsArray[0] = posOrigin;
                positionsArray[1] = posMoved;
            }
        }
        return positionsArray;  //In [0] there is the origin position and in [1] there is the new position.
    }

    @Override
    public LinkedList<Position> pawns(Figure_Color color) {
        LinkedList<Position> positions = new LinkedList<Position>();
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (chessboard[i][j] != null)
                    if (chessboard[i][j].getRepresentation() == 'P' && chessboard[i][j].getColor() == color)
                        positions.add(chessboard[i][j].getPosition());
        return positions;
    }

    @Override
    public Position queen(Figure_Color color) {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (chessboard[i][j] != null)
                    if (chessboard[i][j].getRepresentation() == 'Q' && chessboard[i][j].getColor() == color)
                        return new Position(i, j);

        return null;
    }

    @Override
    public boolean King(Figure_Color color) {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (chessboard[i][j] != null)
                    if (chessboard[i][j].getRepresentation() == 'K' && chessboard[i][j].getColor() == color)
                        return true;
        return false;
    }

    @Override
    public Position king(Figure_Color color) {
        if (King(color)) {
            for (int i = 0; i < SIZE; i++)
                for (int j = 0; j < SIZE; j++)
                    if (chessboard[i][j] != null)
                        if (chessboard[i][j].getRepresentation() == 'K' && chessboard[i][j].getColor() == color)
                            return new Position(i, j);
        }
        return null;
    }

    @Override
    public void set(Figure figure) {
        if (figure.getPosition().getX() >= 0 && figure.getPosition().getX() < SIZE && figure.getPosition().getY() >= 0 && figure.getPosition().getY() < SIZE)
            chessboard[figure.getPosition().getX()][figure.getPosition().getY()] = figure;
    }

    @Override
    public void set(IChessboard chessboard) {
        support.firePropertyChange("chessboard", this.chessboard, chessboard);
        for (int i = 0; i < chessboard.size(); i++)
            for (int j = 0; j < chessboard.size(); j++)
                this.chessboard[i][j] = chessboard.get(new Position(i, j));
    }

    public void copy(IChessboard chessboard) {
        for (int i = 0; i < chessboard.size(); i++)
            for (int j = 0; j < chessboard.size(); j++)
                this.chessboard[i][j] = chessboard.get(new Position(i, j));
    }

    @Override
    public void setNull(Position position) {
        if (position.getX() >= 0 && position.getX() < SIZE && position.getY() >= 0 && position.getY() < SIZE)
            chessboard[position.getX()][position.getY()] = null;
    }

    @Override
    public int size() {
        return SIZE;
    }

    @Override
    public int size(Figure_Color color) {
        int cont = 0;
        for (int i = 0; i < chessboard.length; i++)
            for (int j = 0; j < chessboard.length; j++)
                if (chessboard[i][j] != null)
                    if (chessboard[i][j].getColor() == color)
                        cont++;
        return cont;
    }

    @Override
    public int sizeBishops(Figure_Color color) {
        int cont = 0;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (chessboard[i][j] != null)
                    if (chessboard[i][j].getRepresentation() == 'B' && chessboard[i][j].getColor() == color)
                        cont++;
        return cont;
    }

    @Override
    public int sizeKnights(Figure_Color color) {
        int cont = 0;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (chessboard[i][j] != null)
                    if (chessboard[i][j].getRepresentation() == 'L' && chessboard[i][j].getColor() == color)
                        cont++;
        return cont;
    }

    @Override
    public int sizePawns(Figure_Color color) {
        int cont = 0;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (chessboard[i][j] != null)
                    if (chessboard[i][j].getRepresentation() == 'P' && chessboard[i][j].getColor() == color)
                        cont++;
        return cont;
    }

    @Override
    public int sizeCastles(Figure_Color color) {
        int cont = 0;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (chessboard[i][j] != null)
                    if (chessboard[i][j].getRepresentation() == 'C' && chessboard[i][j].getColor() == color)
                        cont++;
        return cont;
    }

    @Override
    public LinkedList<Position> castles(Figure_Color color) {
        LinkedList<Position> positions = new LinkedList<Position>();
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (chessboard[i][j] != null)
                    if (chessboard[i][j].getRepresentation() == 'C' && chessboard[i][j].getColor() == color)
                        positions.add(chessboard[i][j].getPosition());
        return positions;
    }

    @Override
    public int valor(Figure_Color color) {
        int valor = 0;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (chessboard[i][j] != null)
                    if (chessboard[i][j].getColor() == color)
                        valor += chessboard[i][j].getValor();
        return valor;
    }

    public String toString() {
        String res = "";
        for (int i = 0; i < chessboard.length; i++) {
            res += "|";
            for (int j = 0; j < chessboard[i].length; j++) {
                if (chessboard[i][j] == null)
                    res += '0' + "  ";
                else if (chessboard[i][j].getColor() == Figure_Color.WHITE)
                    res += chessboard[i][j].getRepresentation() + "  ";
                else
                    res += chessboard[i][j].getRepresentation() + "· "; //Distinction for black figures
            }
            res += "|" + "\n";
        }
        res += "/-------\n";
        return res;
    }
}
