package Model;

import java.util.List;
import java.util.Random;

public class Domino extends Piece<Integer>{
    /* Cette classe gèrera donc ce qui est spécifique au domino et pas au piece ni à Carcassonne*/
    public Domino (Integer[][] vals){
        super(vals);
    }

    public Domino() {
        super();
    }

    @Override
    public String toString() {
        return "Model.Domino{" +
                "isUpFree=" + isUpFree +
                ", isRightFree=" + isRightFree +
                ", isDownFree=" + isDownFree +
                ", isLeftFree=" + isLeftFree+
                ", x = "+x+", y = "+y+'}';
    }

    public static Domino creatingFirst() {
         Integer[][] temp = new Integer[4][3];
        Random rand = new Random();
        int e = rand.nextInt(4);
        for (int i = 0; i < temp.length; i++) {
            if (i == e){
                temp[i][0] = rand.nextInt(10);
                temp[i][1] = rand.nextInt(10);
                temp[i][2] = temp[i][0];
            }else {
                for (int j = 0; j < temp[i].length; j++) {
                    temp[i][j] = rand.nextInt(10);
                }
            }
        }
        Domino d = new Domino();
        d.up = null;
        d.down = null;
        d.left = null;
        d.right = null;
        d.isLeftFree = true;
        d.isUpFree = true;
        d.isRightFree = true;
        d.isDownFree = true;
        d.x=0;
        d.y=0;
        d.vals = temp;
        return d;
    }

    @Override
    public boolean isPosable(int x, int y, Plateau plateau) {
        if (!plateau.pieces.contains(this)){
            Piece up = plateau.get(x,y+1);
            Piece down = plateau.get(x,y-1);
            Piece left = plateau.get(x-1,y);
            Piece right = plateau.get(x+1,y);
            if (up!=null){
                if (this.notPlayable("up", up, this)) {
                    return false;
                }
            }
            if (down!=null){
                if (this.notPlayable("down", down, this)) {
                    return false;
                }
            }
            if (right!=null){
                if (this.notPlayable("right", right, this)) {
                    return false;
                }
              }
            if (left!=null){
                return !this.notPlayable("left", left, this);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean notPlayable(String posPieceSurPlateau , Piece pieceSurPlateau , Piece aPoser){
        int indAPoser = -1;
        int indPlateau = -1;
        switch (posPieceSurPlateau){
            case "up":
                indAPoser = 0;
                indPlateau = 2;
                break;
            case "right":
                indAPoser = 1;
                indPlateau = 3;
                break;
            case "down":
                indAPoser = 2;
                indPlateau = 0;
                break;
            case "left":
                indAPoser = 3;
                indPlateau = 1;
                break;
        }
        if (pieceSurPlateau!= null && aPoser.vals != null && pieceSurPlateau.vals!= null){
            if (indAPoser != -1){
                for (int i = 0; i < aPoser.vals[indAPoser].length; i++) {
                    if (!aPoser.vals[indAPoser][2-i].equals(pieceSurPlateau.vals[indPlateau][i])){
                        return true;
                    }
                }
            }else{
                return true;
            }
                return false;
        }
        return true;
    }


    @SuppressWarnings ( " unchecked " )
    public static Piece<Integer> drawPiece(Plateau plateau) {
        Random random = new Random();
        plateau.piecesLibre = plateau.freePiece();
        List list = ToList.toList(plateau.borderCombinaison(plateau.piecesLibre).stream());
        int border = random.nextInt(100);
        if (border <=9){
            border = 0;
        } else if (border <= 44) {
            border = 1;
        }else if (border <= 70) {
            border = 2;
        }else if (border <= 88) {
            border = 3;
        }else {
            border = 4;
        }
        Integer[][] tmpRes = new Integer[4][3];
        for (int i = 0; i < border; i++) {
            int randIndex = random.nextInt(list.size());
            int index = random.nextInt(4);
            if (list.get(randIndex) instanceof Integer[]){
                Integer[] tmp = (Integer[]) list.get(randIndex);
                tmp = invertTabToVals(tmp);
                tmpRes[index] = tmp;
            }else {
                Object[] o = (Object[]) list.get(randIndex);
                Integer i0 = (Integer) o[0];
                Integer i1 = (Integer) o[1];
                Integer i2 = (Integer) o[2];
                Integer i3 = (Integer) o[3];
                Integer[] tmp = new Integer[]{i0 , i1 , i2 , i3 };
                tmp = invertTabToVals(tmp);
                tmpRes[index] = tmp;
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < tmpRes[i].length; j++) {
                if (tmpRes[i][j] == null){
                    tmpRes[i][j] = random.nextInt(10);
                }
            }
        }
        return new Domino(tmpRes);
    }

    private static Integer[] invertTabToVals(Integer[] t){
        return new Integer[]{t[2] , t[1] , t[0]};
    }

}