package Model;


import java.awt.*;
import java.util.*;
import java.util.List;


public abstract class Piece<E>{
    /*Cette class sert à créer les objets avec les différentes choses en arguments.
    * Elle permet de centraliser beaucoup de fonction et de factoriser, et aussi de forcer l'écriture d'autre nécessaire
     */


     public E[][] vals;
    public boolean isUpFree;
    public boolean isRightFree;
    public boolean isDownFree;
    public boolean isLeftFree;

    public E centre;

    public int x;
    public int y;

    public Piece<E> up;
    public Piece<E> right;
    public Piece<E> down;
    public Piece<E> left;

    public String original;
    public int tourne = 0;


     public Piece(E[][] e){
         if (e.length == 4) {
             this.vals = e;
             isUpFree = true;
             isDownFree = true;
             isRightFree = true;
             isLeftFree = true;
         }
     }

    public Piece() {
         vals = null;
        isUpFree = true;
        isDownFree = true;
        isRightFree = true;
        isLeftFree = true;
    }

    public static <A> Piece<A> empty() {
        return new Piece<>() {
            @Override
            public int comptePoint(Joueur j, Piece<A> up, Piece<A> right, Piece<A> down, Piece<A> left) {
                return 0;
            }

            @Override
            public boolean notPlayable(String orientation, Piece pieceSurPlateau, Piece aPoser) {
                return true;
            }
        };
    }

    @SuppressWarnings ( " unchecked " )
    public static <A> Piece<A> wherePosable() {
        return new Piece<>() {
            {
                vals = (A[][]) new Object[4][3];
            }

            @Override
            public int comptePoint(Joueur j, Piece<A> up, Piece<A> right, Piece<A> down, Piece<A> left) {
                return 0;
            }

            @Override
            public boolean notPlayable(String orientation, Piece pieceSurPlateau, Piece aPoser) {
                return true;
            }
        };
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean asOneSideFree(){
        return this.isUpFree || this.isRightFree || this.isDownFree || this.isLeftFree;
    }

    public static Piece creatingFirst(boolean isDomino) {
         if (isDomino){
             return Domino.creatingFirst();
         }else{
             return PieceCarcassonne.creatingFirst();
         }
    }

    public boolean isPosable(int x, int y , Plateau p){
        return false;
    }

    public void prepPlateau(List<Piece> posable , Plateau<E> plateau){
        if (isDownFree){
            if (plateau.get(x, y - 1) == null){
                if (notContains(posable, x, y - 1)){
                    Piece p = wherePosable();
                    p.x=x;
                    p.y=y-1;
                    posable.add(p);
                }
            }else{
                if (plateau.pieces.contains(plateau.get(x, y - 1))){
                    posable.remove(plateau.get(x, y - 1));
                }
            }
        }
        if (isLeftFree){
            if (plateau.get(x - 1, y) == null){
                if (notContains(posable, x - 1, y)){
                    Piece p = wherePosable();
                    p.x=x-1;
                    p.y=y;
                    posable.add(p);
                }
            }else{
                if (plateau.pieces.contains(plateau.get(x - 1, y))){
                    posable.remove(plateau.get(x - 1, y));
                }
            }
        }
        if (isUpFree){
            if (plateau.get(x, y + 1) == null){
                if (notContains(posable, x, y + 1)){
                    Piece p = wherePosable();
                    p.x=x;
                    p.y=y+1;
                    posable.add(p);
                }
            }else{
                if (plateau.pieces.contains(plateau.get(x, y + 1))){
                    posable.remove(plateau.get(x, y + 1));
                }
            }
        }
        if (isRightFree){
            if (plateau.get(x + 1, y) == null){
                if (notContains(posable, x + 1, y)){
                    Piece p = wherePosable();
                    p.x=x+1;
                    p.y=y;
                    posable.add(p);
                }
            }else{
                if (plateau.pieces.contains(plateau.get(x + 1, y))){
                    posable.remove(plateau.get(x + 1, y));
                }
            }
        }
    }

    public static boolean notContains(List<Piece> set , int x , int y){
        for (Piece p:set) {
            if (p.x == x && p.y == y){
                return false;
            }
        }
        return true;
    }


    @SuppressWarnings ( " unchecked " )
    public void tournerUnePiece(){
        tourne= (tourne+1) % 4;
        Object[][] newPiece = new Object[4][3];
        for (int i = 0; i < 3; i++) {
            newPiece[0][i] = vals[0][i];
            newPiece[1][i] = vals[1][i];
            newPiece[2][i] = vals[2][i];
            newPiece[3][i] = vals[3][i];
        }

        Object[] tmp1 = newPiece[1];
        Object[] tmp2 = newPiece[2];
        Object[] tmp3 = newPiece[3];

        newPiece[1] = newPiece[0];
        newPiece[0] = tmp3;
        newPiece[2] = tmp1;
        newPiece[3] = tmp2;

        vals = (E[][]) newPiece;
    }

    public int comptePoint(Joueur j, Piece<E> up, Piece<E> right, Piece<E> down, Piece<E> left){
        int res = 0;
        if (down!=null){
            if (down.vals[0][0] instanceof Integer){
                for (int i = 0; i < 3; i++) {
                    res+=(int)vals[2][i];
                }
            }
        }
        if (up!=null){
            if (up.vals[0][0] instanceof Integer){
                for (int i = 0; i < 3; i++) {
                    res+=(int)vals[0][i];
                }
            }
        }
        if (right!=null){
            if (right.vals[0][0] instanceof Integer){
                for (int i = 0; i < 3; i++) {
                    res+=(int)vals[1][i];
                }
            }
        }
        if (left!=null){
            if (left.vals[0][0] instanceof Integer){
                for (int i = 0; i < 3; i++) {
                    res+=(int)vals[3][i];
                }
            }
        }
        return res;
    }

    public boolean equals(Piece obj) {
        if (obj.vals != null){
            if (vals!= null){
                for (int i = 0; i < obj.vals.length; i++) {
                    for (int j = 0; j < obj.vals[i].length; j++) {
                        if (vals[i][j]!= null){
                            if (! vals[i][j].equals(obj.vals[i][j])){
                                return false;
                            }
                        } else if (obj.vals[i][j] ==  null) {
                            if (obj.x == x && obj.y == y){
                                return true;
                            }
                        }
                    }
                }
            }else{
                return false;
            }
        } else if (vals == null) {
            return true;
        }
        return super.equals(obj);
    }

    public abstract boolean notPlayable(String orientation , Piece pieceSurPlateau , Piece aPoser);

    public String getOriginal(){
        return original;
    }

    public void posePion(int i, int j, Color color, Joueur joueur) {
        if (vals[i][j] instanceof ObjectCarcassonne){
            ObjectCarcassonne tmp = (ObjectCarcassonne) vals[i][j];
            if (i == 0){
                tmp.setPion(new Pion(joueur , color));

            } else if (i == 1){
                if (j == 0){
                    tmp = (ObjectCarcassonne) vals[3][2];
                    tmp.setPion(new Pion(joueur , color));
                } else if (j == 1) {
                    tmp = (ObjectCarcassonne) centre;
                    tmp.setPion(new Pion(joueur , color));
                }else {
                    tmp = (ObjectCarcassonne) vals[1][2];
                    tmp.setPion(new Pion(joueur , color));
                }
            }else{
                tmp = (ObjectCarcassonne) vals[i][2 - j];
                tmp.setPion(new Pion(joueur , color));
            }
        }
    }
}
