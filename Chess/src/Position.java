public class Position {
    private int x;
    private int y;

    public Position(int x, int y){
        setX(x);
        setY(y);
    }

    public boolean	equals(Position p){
        return (x == p.getX() && y == p.getY());
    }

    public Position get(){
        return  this;
    }
    public int	getX(){
        return x;
    }

    public int	getY(){
        return y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void	setY(int y){
        this.y = y;
    }

    public String toString(){
        return "[X: "+x+", Y: "+y +"]";
    }

}
