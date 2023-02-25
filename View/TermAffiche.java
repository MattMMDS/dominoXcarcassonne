package View;

import Model.Joueur;
import Model.Piece;

import java.util.*;

public class TermAffiche {

    public <E> void afficheListePiece(Collection<Piece<E>> list){
        for (int i = 0; i < 6; i++) {
            afficheListePieceLine(list , i);
        }
    }

    public <E> void afficheListePiecePosable(Collection<Piece<E>> list , ArrayList<Piece<E>> listeWherePosable){
        for (int i = 0; i < 6; i++) {
            afficheListePieceLinePos(list , i , listeWherePosable);
        }
    }

    private <E> void afficheListePieceLine(Collection<Piece<E>> coll , int line) {
        List<Piece<E>> list = Model.ToList.toList(coll.stream());
        int ind;
        switch (line){
            case 0 :
            case 4 :
                for (Piece e: coll) {
                    if (e.equals(Piece.empty())){
                        System.out.print("       ");
                    }else{
                        System.out.print(' ');
                        if (line == 0){
                            ind = 0;
                            for (Object i:e.vals[ind]) {
                                System.out.print(i+" ");
                            }
                        } else {
                            ind = 2;
                            for (int i = e.vals[ind].length-1; i >= 0 ; i-=1) {
                                System.out.print(e.vals[ind][i]+" ");
                            }
                        }
                    }
                    if (list.indexOf(e) == list.size()-1){
                        System.out.println();
                    }
                }
                break;
            case 1 :
            case 2 :
            case 3 :
                for (Piece e: list) {
                    if (e.equals(Piece.empty())){
                        System.out.print("       ");
                    }else{
                        if (line ==1){
                            System.out.print(e.vals[3][2]+"     " + e.vals[1][0]);
                        } else if (line == 2) {
                            System.out.print(e.vals[3][1]+"     " + e.vals[1][1]);
                        } else{
                            System.out.print(e.vals[3][0]+"     " + e.vals[1][2]);
                        }
                    }
                    if (list.indexOf(e) == list.size()-1){
                        System.out.println();
                    }
                }
                break;
            default:
        }
    }

    private <E> void afficheListePieceLinePos(Collection<Piece<E>> coll , int line , List<Piece<E>> listeWherePosable) {
        List<Piece<E>> list = Model.ToList.toList(coll.stream());
        int ind;
        switch (line){
            case 0 :
            case 4 :
                for (Piece e: list) {
                    if (e.equals(Piece.empty())){
                        System.out.print("       ");
                    }else if (listeWherePosable.contains(e)) {
                        System.out.print("       ");
                    }else {
                        System.out.print(' ');
                        if (line == 0){
                            ind = 0;
                            for (Object i:e.vals[ind]) {
                                System.out.print(i+" ");
                            }
                        } else {
                            ind = 2;
                            for (int i = e.vals[ind].length-1; i >= 0 ; i-=1) {
                                System.out.print(e.vals[ind][i]+" ");
                            }
                        }
                    }
                    if (list.indexOf(e) == list.size()-1){
                        System.out.println();
                    }
                }
                break;
            case 1 :
            case 2 :
            case 3 :
                for (Piece e: list) {
                    if (e.equals(Piece.empty())){
                        System.out.print("       ");
                    }else if (listeWherePosable.contains(e)) {
                        if (line == 2){
                            System.out.print(posableInd(listeWherePosable.indexOf(e)) );
                        }else{
                            System.out.print("       ");
                        }
                    }else {
                        if (line ==1){
                            System.out.print(e.vals[3][2]+"     " + e.vals[1][0]);
                        } else if (line == 2) {
                            System.out.print(e.vals[3][1]+"     " + e.vals[1][1]);
                        } else {
                            System.out.print(e.vals[3][0]+"     " + e.vals[1][2]);
                        }
                    }
                    if (list.indexOf(e) == list.size()-1){
                        System.out.println();
                    }
                }
                break;
            default:
        }
    }

    public void afficheMain (Piece p){
        System.out.println("Vous avez la piece suivante");
        affichePiece(p);
    }

    public void affichePiece(Piece p) {
        int ind ;
        for (int line = 0; line < 5; line++) {
            switch (line){
                case 0 :
                case 4 :
                    if (p.equals(Piece.empty())){
                        System.out.print("       ");
                    }else{
                        System.out.print(' ');
                        if (line == 0){
                            ind = 0;
                            for (Object i:p.vals[ind]) {
                                System.out.print(i+" ");
                            }
                        } else {
                            ind = 2;
                            for (int i = p.vals[ind].length-1; i >= 0 ; i-=1) {
                                System.out.print(p.vals[ind][i]+" ");
                            }
                        }
                    }
                    System.out.println();
                    break;
                case 1 :
                case 2 :
                case 3 :
                    if (p.equals(Piece.empty())){
                            System.out.print("       ");
                        }else{
                            if (line ==1){
                                System.out.print(p.vals[3][2]+"     " + p.vals[1][0]);
                            } else if (line == 2) {
                                System.out.print(p.vals[3][1]+"     " + p.vals[1][1]);
                            } else {
                                System.out.print(p.vals[3][0]+"     " + p.vals[1][2]);
                            }
                        }
                    System.out.println();
                    break;
                default:
                    return;
            }

        }
    }


    private String posableInd(int i){
        int lettre = i/10 + 65;
        char c = (char) lettre;
        return "  " + c+ (i%10) + "   ";
    }

    public Scanner sc = new Scanner(System.in);

    public void affichePointJoueurs(String playerName , int joueursPoints) {
        System.out.println(playerName + " à " + joueursPoints + " points.");
    }

    public String askWhereToPlay(String name){
        System.out.println(name + ", à quelle place voulez vous le placer ?");
        return sc.nextLine();
    }

    public String askWhereWhatToDo() {
        System.out.println("Que voulez vous faire ? Piocher ? Ou abandonner ?");
        return sc.nextLine();
    }

    public void afficheClassement(ArrayList<Joueur> joueurs) {
        if (joueurs.size() == 1){
            System.out.println(joueurs.get(0).name + " a gagnez avec " + joueurs.get(0).points + " et plus aucun adversaire.");
        }else{
            System.out.println("Classement / Nom du joueur / Nombre de points");
            for (Joueur j:joueurs) {
                System.out.println((joueurs.indexOf(j)+1) + " / " + j.name + " / " + j.points);
            }
        }
    }

    public String askToTurn() {
        System.out.println("Pivoter la piece ? Vers la droite (D) Valider ou ne pas tourner(V/S)");
        return sc.nextLine().toUpperCase();
    }

    public String askToDump() {
        System.out.println("Jeter la piece et passer son tour ? (o/n)");
        return sc.nextLine().toLowerCase();
    }

    public String askPlayers() {
        System.out.println("A combien de joueurs voulez vous jouer ?");
        return sc.nextLine().toLowerCase();
    }

    public String askBots() {
        System.out.println("Avec combien d'ordinateur voulez vous jouer ?");
        return sc.nextLine().toLowerCase();
    }

    public String askLevel() {
        System.out.print("Veuillez entrer 'pro' pour mettre le joueur en difficulté professionnelle ou 'easy' pour une difficultée normale: ");
        return sc.nextLine().toLowerCase();
    }

    public String askName(int i) {
        switch (i){
            case 1:
                System.out.print("Premier");
                break;
            case 2:
                System.out.print("Deuxième");
                break;
            default:
                System.out.print(i + "ème");
                break;
        }
        System.out.print(" joueur entrez votre nom :");
        return sc.nextLine().toLowerCase();
    }

    public String askTuiles() {
        System.out.println("A combien de tuiles voulez vous jouer ?");
        return sc.nextLine().toLowerCase();
    }

    public void afficheActualPlayer(String name, int points) {
        System.out.print("Au tour de "+ name + " il a "+ points);
        if (points> 1){
            System.out.println(" points");
        }else{
            System.out.println(" point");
        }
    }
}
