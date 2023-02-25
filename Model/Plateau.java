package Model;

import java.util.*;

//a mettre abstract
public class Plateau <E> {

    public Collection<Piece<E>> pieces;

    @SuppressWarnings(" unchecked ")
    public Plateau(boolean isDomino) {
        Piece tmp = Piece.creatingFirst(isDomino);
        pieces = new HashSet<>();
        this.pieces.add(tmp);
        piecesLibre = this.freePiece();
    }

//    Méthode qui fait le tour du plateau pour rendre toute les piece qui sont libres sous formes de liste

    @SuppressWarnings(" unchecked ")
    public HashSet<Piece<E>> freePiece(){
        HashSet piecesLibre= new HashSet<>();
        if (!pieces.isEmpty()){
            for (Piece e: pieces) {
                if (e != null){
                    if (e.asOneSideFree()){
                        piecesLibre.add(e);
                    }
                }
            }
        }
        return piecesLibre;
    }

    public HashSet<Object[]> borderCombinaison(HashSet<Piece <E>> bordersE){
        HashSet<Object[]> res= new HashSet<>();
        for (Piece<E> e:bordersE) {
            if (e.isUpFree){
                Object[] toAdd = new Object[4];
                System.arraycopy(e.vals[0], 0, toAdd, 0, 3);
                toAdd[3]=0;
                res.add(toAdd);
            }
            if (e.isRightFree){
                Object[] toAdd = new Object[4];
                System.arraycopy(e.vals[1], 0, toAdd, 0, 3);
                toAdd[3]=1;
                res.add(toAdd);
            }
            if (e.isDownFree){
                Object[] toAdd = new Object[4];
                System.arraycopy(e.vals[2], 0, toAdd, 0, 3);
                toAdd[3]=2;
                res.add(toAdd);
            }
            if (e.isLeftFree){
                Object[] toAdd = new Object[4];
                System.arraycopy(e.vals[3], 0, toAdd, 0, 3);
                toAdd[3]=3;
                res.add(toAdd);
            }
        }
        return res;
    }



    public Plateau(Piece<E> e){
        this.pieces = new HashSet<>();
        this.pieces.add(e);
    }

    public int getMaxX(){
        boolean v = this.pieces.stream().max(Comparator.comparing(Piece::getX)).isPresent();
        Piece x = (v) ? this.pieces.stream().max(Comparator.comparing(Piece::getX)).get() : new Piece() {
            @Override
            public boolean notPlayable(String orientation, Piece pieceSurPlateau, Piece aPoser) {
                return true;
            }
        };
        return x.getX();
    }

    public int getMinX(){
        boolean v = this.pieces.stream().min(Comparator.comparing(Piece::getX)).isPresent();
        Piece x = (v) ? this.pieces.stream().min(Comparator.comparing(Piece::getX)).get() : new Piece() {
            @Override
            public boolean notPlayable(String orientation, Piece pieceSurPlateau, Piece aPoser) {
                return true;
            }

        };
        return x.getX();
    }

    public int getMaxY(){
        boolean v = this.pieces.stream().max(Comparator.comparing(Piece::getY)).isPresent();
        Piece y = (v) ? this.pieces.stream().max(Comparator.comparing(Piece::getY)).get() : new Piece() {
            @Override
            public boolean notPlayable(String orientation, Piece pieceSurPlateau, Piece aPoser) {
                return true;
            }
        };
        return y.getY();
    }

    public int getMinY(){
        boolean v = this.pieces.stream().min(Comparator.comparing(Piece::getY)).isPresent();
        Piece y = (v) ? this.pieces.stream().min(Comparator.comparing(Piece::getY)).get() : new Piece() {
            @Override
            public boolean notPlayable(String orientation, Piece pieceSurPlateau, Piece aPoser) {
                return true;
            }
        };
        return y.getY();
    }

    public boolean placePiece (Piece piece , int x , int y , Joueur j){
        boolean res = piece.isPosable(x, y , this);
        if (res){
            place(piece , x , y , j);
        }
        return res ;
    }

    public boolean placePiece (PieceCarcassonne piece , int x , int y , Joueur j){
        boolean res = piece.isPosable(x, y , this);
        if (res){
            place(piece , x , y , j);
        }
        return res ;
    }

    @SuppressWarnings(" unchecked ")
    public void place(Piece piece , int x , int y , Joueur j){
        //Attention ici les pieces sont nommées par rapport a la ou on veut placer la piece;
        Piece up =  get(x,y+1);
        Piece down =  get(x,y-1);
        Piece left =  get(x-1,y);
        Piece right =  get(x+1,y);
        boolean flag =false;
        if (up!=null){
            up.isDownFree = false ;
            piece.isUpFree = false;
            up.down = piece;
            piece.up= up;
            piece.x=up.x;
            piece.y=up.y-1;
            flag = true;

        }
        if (down!=null){
            down.isUpFree = false;
            piece.isDownFree = false ;
            piece.x=down.x;
            piece.y=down.y+1;
            down.up = piece;
            piece.down= down;
            flag = true;

        }
        if (right!=null){
            right.isLeftFree = false ;
            piece.isRightFree = false;
            piece.x=right.x-1;
            piece.y=right.y;
            piece.right = right;
            right.left = piece;
            flag = true;
        }
        if (left!=null){
            left.isRightFree = false ;
            piece.isLeftFree = false;
            piece.x=left.x+1;
            piece.y=left.y;
            piece.left = left;
            left.right = piece;
            flag = true;
        }
        if (flag){
            pieces.add(piece);
            piecesPosable.remove(piece);
            j.addPoints(piece.comptePoint(j , up, right, down, left));
        }
    }

    public boolean placePiece (Domino piece , int x , int y , Joueur j) {
        boolean res = piece.isPosable(x, y, this);
        if (res) {
            place(piece, x , y , j);
        }
        return res;
    }

    public Piece get(int x, int y){
        for (Piece p: pieces) {
            if (p.getX() == x && p.getY() == y){
                return p;
            }
        }
        return null;
    }


    public HashSet<Piece<E>> piecesLibre;
    public ArrayList<Piece<E>> piecesPosable = new ArrayList<>();

    @SuppressWarnings(" unchecked ")
    public void prepPlateau(){
        piecesLibre = freePiece();
        piecesPosable = new ArrayList<>();
        for (Piece p:piecesLibre) {
            p.prepPlateau(piecesPosable , this);
        }
    }

    public int getMinX(boolean ignored) {
        boolean v = this.pieces.stream().min(Comparator.comparing(Piece::getX)).isPresent();
        boolean v2 = piecesPosable.stream().min(Comparator.comparing(Piece::getX)).isPresent();
        Piece x = (v) ? this.pieces.stream().min(Comparator.comparing(Piece::getX)).get() : new Piece() {
            @Override
            public boolean notPlayable(String orientation, Piece pieceSurPlateau, Piece aPoser) {return true;}
        } ;
        Piece x2 = (v2) ? piecesPosable.stream().min(Comparator.comparing(Piece::getX)).get() : new Piece() {
            @Override
            public boolean notPlayable(String orientation, Piece pieceSurPlateau, Piece aPoser) {return true;}
        } ;
        return Math.min(x.getX() , x2.getX());
    }

    public int getMinY(boolean ignored) {
        boolean v = this.pieces.stream().min(Comparator.comparing(Piece::getY)).isPresent();
        boolean v2 = piecesPosable.stream().min(Comparator.comparing(Piece::getY)).isPresent();
        Piece y = (v) ? this.pieces.stream().min(Comparator.comparing(Piece::getY)).get() : new Piece() {
            @Override
            public boolean notPlayable(String orientation, Piece pieceSurPlateau, Piece aPoser) {return true;}
        } ;
        Piece y2 = (v2) ? piecesPosable.stream().min(Comparator.comparing(Piece::getY)).get() : new Piece() {
            @Override
            public boolean notPlayable(String orientation, Piece pieceSurPlateau, Piece aPoser) {return true;}
        } ;
        return Math.min(y.getY() , y2.getY());
    }

    public int getMaxY(boolean ignored) {
        boolean v = this.pieces.stream().max(Comparator.comparing(Piece::getY)).isPresent();
        boolean v2 = piecesPosable.stream().max(Comparator.comparing(Piece::getY)).isPresent();
        Piece y = (v) ? this.pieces.stream().max(Comparator.comparing(Piece::getY)).get() : new Piece() {
            @Override
            public boolean notPlayable(String orientation, Piece pieceSurPlateau, Piece aPoser) {return true;}
        } ;
        Piece y2 = (v2) ? piecesPosable.stream().max(Comparator.comparing(Piece::getY)).get() : new Piece() {
            @Override
            public boolean notPlayable(String orientation, Piece pieceSurPlateau, Piece aPoser) {return true;}
        } ;
        return Math.max(y.getY() , y2.getY());
    }

    public int getMaxX(boolean ignored) {
        boolean v = this.pieces.stream().max(Comparator.comparing(Piece::getX)).isPresent();
        boolean v2 = piecesPosable.stream().max(Comparator.comparing(Piece::getX)).isPresent();
        Piece X = (v) ? this.pieces.stream().max(Comparator.comparing(Piece::getX)).get() : new Piece() {
            @Override
            public boolean notPlayable(String orientation, Piece pieceSurPlateau, Piece aPoser) {return true;}
        } ;
        Piece X2 = (v2) ? piecesPosable.stream().max(Comparator.comparing(Piece::getX)).get() : new Piece() {
            @Override
            public boolean notPlayable(String orientation, Piece pieceSurPlateau, Piece aPoser) {return true;}
        } ;
        return Math.max(X.getX() , X2.getX());
    }

}
