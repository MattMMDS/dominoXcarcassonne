package Model;


import java.awt.*;
import java.util.Random;

public class PieceCarcassonne extends Piece<ObjectCarcassonne>{

    private static PieceCarcassonne[] pieces;
    private static int[] piecesNombre;

    public PieceCarcassonne(ObjectCarcassonne[][] e , ObjectCarcassonne centre) {
        super(e);
        this.centre = centre;
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
    public boolean notPlayable(String posPieceSurPlateau, Piece pieceSurPlateau, Piece aPoser) {
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
    public static Piece<ObjectCarcassonne> drawPiece(Plateau plateau) {
        Random rand = new Random();
        boolean flag = true;
        int ind = 0;
        while (flag){
            ind = rand.nextInt(24);
            if (piecesNombre[ind]>0){
                piecesNombre[ind]--;
                flag = false; break;
            }
        }
        PieceCarcassonne res = pieces[ind].clone();
        res.original = res.setOriginal();
        return res;
    }

    private String setOriginal() {
        StringBuilder nomFic = new StringBuilder();
        try {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    ObjectCarcassonne tmp = (ObjectCarcassonne) vals[i][j];
                    Object tmp2 = vals[i][j];
                    if (tmp.typeTerrain.equals("ville")){
                        nomFic.append("v");
                    }else if (tmp.typeTerrain.equals("prairie")){
                        nomFic.append("p");
                    }else {
                        nomFic.append("c");
                    }
                }
            }
            ObjectCarcassonne tmp = centre;
            if (tmp.typeTerrain.equals("ville")){
                nomFic.append("v");
            }else if (tmp.typeTerrain.equals("prairie")){
                nomFic.append("p");
            }else {
                nomFic.append("c");
            }
        }catch (Exception ignored){}
        return nomFic.toString();
    }

    public PieceCarcassonne clone(){
        ObjectCarcassonne[][] newVals = new ObjectCarcassonne[4][3];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                newVals[i][j] = new ObjectCarcassonne(vals[i][j].typeTerrain);
            }
        }
        ObjectCarcassonne newCentre = new ObjectCarcassonne(centre.typeTerrain);
        return new PieceCarcassonne(newVals , newCentre);
    }

    public static PieceCarcassonne creatingFirst() {
        createPieces();
        return (PieceCarcassonne) drawPiece(null);
    }


    public static void createPieces(){
        pieces = new PieceCarcassonne[24];
        piecesNombre = new int[24];
        ObjectCarcassonne c = new ObjectCarcassonne("chemin");
        ObjectCarcassonne v = new ObjectCarcassonne("ville");
        ObjectCarcassonne p= new ObjectCarcassonne("prairie");
        ObjectCarcassonne[] prairie = new ObjectCarcassonne[]{p,p,p};
        ObjectCarcassonne[] ville = new ObjectCarcassonne[]{v,v,v};

        pieces[0] = new PieceCarcassonne(new ObjectCarcassonne[][]{prairie,prairie,{p ,c ,p } , {p, c , p}} , c);
        piecesNombre[0] = 9;
        pieces[1] = new PieceCarcassonne(new ObjectCarcassonne[][]{{p, v , p},{p , c , p},{p , c , p}, prairie}, c);
        piecesNombre[1] = 3;
        pieces[2] = new PieceCarcassonne(new ObjectCarcassonne[][]{ville,{p , c , p},{p , c , p}, ville}, v);
        piecesNombre[2] =2;
        pieces[3] = new PieceCarcassonne(new ObjectCarcassonne[][]{ville,ville,{p , c , p}, ville}, v);
        piecesNombre[3] = 1;
        pieces[4] = new PieceCarcassonne(new ObjectCarcassonne[][]{ville,ville,prairie, ville}, v);
        piecesNombre[4] = 1;
        pieces[5] = new PieceCarcassonne(new ObjectCarcassonne[][]{{v,v,p},{p , c , p},{p , c , p}, ville},v );
        piecesNombre[5] = 3;
        pieces[6] = new PieceCarcassonne(new ObjectCarcassonne[][]{ville,{p , c , p},{p , c , p}, {p , c , p}}, c);
        piecesNombre[6] = 3;
        pieces[7] = new PieceCarcassonne(new ObjectCarcassonne[][]{{p , c , p},prairie,{p , c , p}, prairie}, c);
        piecesNombre[7] = 8;
        pieces[8] = new PieceCarcassonne(new ObjectCarcassonne[][]{prairie,{p , c , p},{p , c , p}, {p , c , p}}, c);
        piecesNombre[8] = 4;
        pieces[9] = new PieceCarcassonne(new ObjectCarcassonne[][]{ville,prairie,prairie, prairie}, p);
        piecesNombre[9] = 5;
        pieces[10] = new PieceCarcassonne(new ObjectCarcassonne[][]{ville,ville,prairie, prairie}, p);
        piecesNombre[10] = 2;
        pieces[11] = new PieceCarcassonne(new ObjectCarcassonne[][]{ville,ville,{v ,p, p}, ville}, v);
        piecesNombre[11] = 3;
        pieces[12] = new PieceCarcassonne(new ObjectCarcassonne[][]{prairie,prairie,prairie, prairie}, v);
        piecesNombre[12] = 4;
        pieces[13] = new PieceCarcassonne(new ObjectCarcassonne[][]{prairie,prairie,{p , c , p}, prairie}, v);
        piecesNombre[13] = 2;
        pieces[14] = new PieceCarcassonne(new ObjectCarcassonne[][]{ville,prairie,prairie, ville}, v);
        piecesNombre[14] = 3;
        pieces[15] = new PieceCarcassonne(new ObjectCarcassonne[][]{{v,p,p},ville , {v, p, v}, ville}, v);
        piecesNombre[15] = 2;
        pieces[16] = new PieceCarcassonne(new ObjectCarcassonne[][]{ville,{p , c , p},prairie, {p , c , p}}, c);
        piecesNombre[16] = 4;
        pieces[17] = new PieceCarcassonne(new ObjectCarcassonne[][]{ville,prairie,{p , c , p}, {p , c , p}}, c);
        piecesNombre[17] = 3;
        pieces[18] = new PieceCarcassonne(new ObjectCarcassonne[][]{{v,p , p},ville,prairie, ville}, v);
        piecesNombre[18] = 1;
        pieces[19] = new PieceCarcassonne(new ObjectCarcassonne[][]{ville,ville,{v,c,p}, ville},v );
        piecesNombre[19] = 2;
        pieces[20] = new PieceCarcassonne(new ObjectCarcassonne[][]{ville,ville,ville, ville},v );
        piecesNombre[20] = 1;
        pieces[21] = new PieceCarcassonne(new ObjectCarcassonne[][]{{p,c,p},{p,c,p},{p,c,p}, {p,c,p}}, c );
        piecesNombre[21] = 1;
        pieces[22] = new PieceCarcassonne(new ObjectCarcassonne[][]{{v,v,p},prairie,prairie, ville}, v);
        piecesNombre[20] = 2;
        pieces[23] = new PieceCarcassonne(new ObjectCarcassonne[][]{prairie,ville,prairie, ville}, p);
        piecesNombre[20] = 3;
    }


}