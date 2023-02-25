package Model;

import Controleur.Controleur;


public class Joueur {
    public Piece current;
    public PieceCarcassonne currentCarc;
    public int points;
    public String name;
    boolean isDomino;

    /*Classe qui sert à modéliser un joueur qui va garder la main du joueur, ses points, son nom, et s'il joue au domino */

    public Joueur(String nom , boolean isDomino){
        name = nom;
        this.isDomino = isDomino;
    }

    public static Joueur createPlayer(Controleur controleur , int i , boolean b) {
        return new Joueur(controleur.askName(i) , b);
    }
    
    public static Joueur createBot(Controleur controleur , int i , boolean b) {
        if(controleur.askLevelBot()) return new OrdiPro("Bot " + i , b);
        else return new Ordi("Bot " + i , b);
    }

    public boolean whatToDo(Controleur c) {
        return c.askWhatToDo();
    }

    public void draw(Plateau plateau , Jeu jeu) {
        jeu.pioches-=1;
        if (isDomino){
            current = Domino.drawPiece(plateau);
        }else{
            currentCarc = (PieceCarcassonne) PieceCarcassonne.drawPiece(plateau);
        }
    }

    public boolean playerAction(Controleur controleur , Plateau plateau) {
        controleur.affichageMain(this.current);
        if (controleur.askToDump(this)){
            return true;
        }
        while (!controleur.askToTurn(true)){}
        int ind = controleur.askWheretoPlace(this);
        if (ind != -1){
            Piece where = (Piece) plateau.piecesPosable.get(ind);
            return plateau.placePiece(current , where.x , where.y , this);
        }
        return false;
    }

    public void dumpCurrent() {
        this.current = null;
    }

    public void addPoints(int comptePoint) {
        points+=comptePoint;
    }

}
