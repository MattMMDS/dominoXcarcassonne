package Model;

import Controleur.Controleur;

import java.util.ArrayList;


public class Jeu<E> {

    /* Le but de cette classe est de faire toute la relation entre toute les autres avec de quoi gérer un tour et une partie

     Description d'un tour
     * Affiche qui est le jouer qui joue et ses points
     * Affiche le plateau normal
     * Demande au joueur ce quil veux faire
     * On propose de piocher ou abandonner
        * S'il abandonne alors on continue avec ce joueur en moins sauf s'il ne reste qu'un joueur
        * S'il pioche le tout continue avec l'affichage de sa main
     * Ensuite on montre le plateau posable puis on demande s'il veut tourner ou poser
     *   S'il pose, on affiche le plateau avec sa piece posée et le resume du joueur ensuite on passe au suivant
     *   Si tourne re-affiche le plateau et la piece tourner
     */


    public Plateau<E> plateau;
    public ArrayList<Joueur> joueurs;
    public int indJoueurs;
    public int pioches;

    public Controleur controleur;

    
    public Jeu (Plateau<E> plateau, int i , int nbrPieces){
        joueurs = new ArrayList<>(i);
        pioches= nbrPieces;
        this.plateau = plateau;
    }

    public Jeu() {

    }

    public Jeu(int nbPiece , boolean isDomino) {
        joueurs = new ArrayList<>();
        pioches = nbPiece;
        plateau = new Plateau<>(isDomino);
    }

    @SuppressWarnings ( " unchecked " )
    public boolean tour(){
        if (pioches!= 0){
            controleur.afficheWhoPlay(joueurs.get(indJoueurs));
            controleur.affichage(plateau.pieces);
            if (joueurs.get(indJoueurs).whatToDo(controleur)){
                joueurs.get(indJoueurs).draw(plateau , this);
                plateau.prepPlateau();
                controleur.affichagePosage(plateau.pieces , plateau.piecesPosable);
                while (!joueurs.get(indJoueurs).playerAction(controleur , plateau)){}
                controleur.affichage(plateau.pieces);
                controleur.resumeJoueur(indJoueurs);
                indJoueurs=(indJoueurs+1)%joueurs.size();
            }else{
                joueurs.remove(indJoueurs);
                if (joueurs.size()==1){
                    return true;
                }else{
                    indJoueurs=(indJoueurs+1)%joueurs.size();
                }
            }
            return false;
        }
        return true;
    }

    @SuppressWarnings ( " unchecked " )
    public void play(boolean isDomino){
        if (isDomino){
            plateau = new Plateau<>((Piece<E>) Domino.creatingFirst());
        }else{
            plateau = new Plateau<>((Piece<E>) PieceCarcassonne.creatingFirst());
        }
        int nbJoueur = controleur.askHowManyPlayers();
        int nbBot = controleur.askHowManyBot();
        joueurs = new ArrayList<>(nbJoueur+nbBot);
        for (int i = 0; i < nbJoueur; i++) {
            joueurs.add(Joueur.createPlayer(controleur , i+1 , isDomino));
        }
        for (int i = 0; i < nbBot; i++) {
            joueurs.add(Joueur.createBot(controleur , i+1 , isDomino));
        }
        pioches = controleur.askHowManyTuiles();
        while (!tour()){}
        win();
    }

    private void win() {
        controleur.afficheClassement();
    }

    public int getJoueursPoints(int indJoueur) {return joueurs.get(indJoueur).points;}

    public String getJoueursName(int indJoueur) {return joueurs.get(indJoueur).name;}

    public void nextPlayer() {
        indJoueurs+=1;
        indJoueurs = indJoueurs%joueurs.size();
    }

    public Object[][] getWinners() {
        Object[][] res = new Object[joueurs.size()][2];
        joueurs.sort((joueur, t1) -> t1.points - joueur.points);
        for (int i = 0; i < res.length; i++) {
            res[i][0]=joueurs.get(i).name;
            res[i][1]=joueurs.get(i).points;
        }
        return res;
    }
}
