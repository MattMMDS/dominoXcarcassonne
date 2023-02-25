package Controleur;

import Model.*;
import View.MainPage;
import View.PopUpPlayer;
import View.TermAffiche;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Controleur<E> {

    public Jeu<E> jeu;
    public TermAffiche affichageTerminal;
    public MainPage affichageGraphique;
    public boolean isTerm;

    public Controleur (){
//        new MainPage();
    }

    @SuppressWarnings(" unchecked ")
    public void affichage (Collection<Piece<E>> pieces){
        if (!pieces.isEmpty()){
            Map truc = pieces.stream().collect(Collectors.groupingBy(Piece::getY));
            Map<Integer, List<Piece<E>>> treeMap = new TreeMap<>(Comparator.reverseOrder());
            treeMap.putAll(truc);
            int size = Math.max(Math.max(Math.abs(jeu.plateau.getMinX()), Math.abs(jeu.plateau.getMinY())), (Math.max(jeu.plateau.getMaxY(), jeu.plateau.getMaxX())));
            size = size * 2 +1;
            if (!isTerm){
                affichageGraphique.afficheListePiece(size , treeMap);
            }
            treeMap.forEach((y, ligneByligne)-> {
                prepareListeForAffiche(ligneByligne);
                if (isTerm){
                    affichageTerminal.afficheListePiece(ligneByligne);
                }
            });
        }
    }

    @SuppressWarnings(" unchecked ")
    public void affichage (boolean isDomino){
        if (!jeu.plateau.pieces.isEmpty()){
            Map truc = jeu.plateau.pieces.stream().collect(Collectors.groupingBy(Piece::getY));
            int size = Math.max(Math.max(Math.abs(jeu.plateau.getMinX()), Math.abs(jeu.plateau.getMinY())), (Math.max(jeu.plateau.getMaxY(), jeu.plateau.getMaxX())));
            size = size * 2 +1;
            if (isDomino){
                new TermAffiche().afficheListePiece(jeu.plateau.pieces);
                Map<Integer, List<Piece<E>>> treeMap = new TreeMap<>(Comparator.reverseOrder());
                treeMap.putAll(truc);
                if (!isTerm){
                    affichageGraphique.afficheListePiece(size , treeMap);
                }
            }else{
                Map<Integer, List<PieceCarcassonne>> treeMap = new TreeMap<>(Comparator.reverseOrder());
                treeMap.putAll(truc);
                if (!isTerm){
                    affichageGraphique.afficheListePiece(size , treeMap, false);
                }
            }
        }
    }

    @SuppressWarnings(" unchecked ")
    public void affichagePosage (Collection<Piece<E>> pieces , List<Piece<E>> posable){
        if (!pieces.isEmpty() && !posable.isEmpty()){
            Collection<Piece<E>> test = new ArrayList<>();
            test.addAll(pieces);
            test.addAll(posable);
            Map truc = test.stream().collect(Collectors.groupingBy(Piece::getY));
            Map<Integer, List<Piece<E>>> treeMap = new TreeMap<>(Comparator.reverseOrder());
            jeu.plateau.piecesPosable.sort(new ComparatorY().thenComparing(new Controleur.ComparatorX()));
            treeMap.putAll(truc);
            if (!isTerm){
                int size = Math.max(Math.max(Math.abs(jeu.plateau.getMinX(true)), Math.abs(jeu.plateau.getMinY(true))), (Math.max(jeu.plateau.getMaxY(true), jeu.plateau.getMaxX(true))));
                size = size*2 + 1;
                affichageGraphique.afficheListePiecePosable( size , treeMap , jeu.plateau.piecesPosable);
            }
            treeMap.forEach((y, ligneByligne)-> {
                prepareListeForAffichePosable(ligneByligne);
                if (isTerm){
                    affichageTerminal.afficheListePiecePosable(ligneByligne , jeu.plateau.piecesPosable);
                }
            });
        }
    }

    @SuppressWarnings(" unchecked ")
    public void affichagePosage (boolean isDomino){
        jeu.plateau.prepPlateau();
        if (!jeu.plateau.pieces.isEmpty() && !jeu.plateau.piecesPosable.isEmpty()){
            if (isDomino){
                Collection<Piece<E>> coll = new ArrayList<>();
                coll.addAll(jeu.plateau.pieces);
                coll.addAll( jeu.plateau.piecesPosable);
                Map truc = coll.stream().collect(Collectors.groupingBy(Piece::getY));
                Map<Integer, List<Piece<E>>> treeMap = new TreeMap<>(Comparator.reverseOrder());
                jeu.plateau.piecesPosable.sort(new ComparatorY().thenComparing(new Controleur.ComparatorX()));
                treeMap.putAll(truc);
                int size = Math.max(Math.max(Math.abs(jeu.plateau.getMinX(true)), Math.abs(jeu.plateau.getMinY(true))), (Math.max(jeu.plateau.getMaxY(true), jeu.plateau.getMaxX(true))));
                size = size*2 + 1;
                affichageGraphique.afficheListePiecePosable( size , treeMap , jeu.plateau.piecesPosable);
            }else {
                Collection<Piece<E>> coll = new ArrayList<>();
                coll.addAll(jeu.plateau.pieces);
                coll.addAll(jeu.plateau.piecesPosable);
                Map truc = coll.stream().collect(Collectors.groupingBy(Piece::getY));
                Map<Integer, List<PieceCarcassonne>> treeMap = new TreeMap<>(Comparator.reverseOrder());
                jeu.plateau.piecesPosable.sort(new ComparatorY().thenComparing(new Controleur.ComparatorX()));
                treeMap.putAll(truc);
                int size = Math.max(Math.max(Math.abs(jeu.plateau.getMinX(true)), Math.abs(jeu.plateau.getMinY(true))), (Math.max(jeu.plateau.getMaxY(true), jeu.plateau.getMaxX(true))));
                size = size*2 + 1;
                affichageGraphique.afficheListePiecePosable( size , treeMap , jeu.plateau.piecesPosable , false);
            }
        }
    }

    private void prepareListeForAffiche(List<Piece<E>> ligneByligne) {
        int minX = jeu.plateau.getMinX();
        ligneByligne.sort(Comparator.comparing(Piece::getX));
        int originSize=ligneByligne.size();
        if (ligneByligne.get(0)!=null && ligneByligne.get(0).getX()!= jeu.plateau.getMinX()){
            int diffdbt = ligneByligne.get(0).getX() - minX;
            for (int j = 0; j < diffdbt; j++) {
                ligneByligne.add(j , Piece.empty());
            }
        }
        if (originSize != 1){
            int limite = ligneByligne.size();
            for (int i = 0; i < limite - 1; i++) {
                if (ligneByligne.get(i).equals(Piece.empty())){
                    continue;
                }
                int diff = Math.abs(ligneByligne.get(i).getX() - ligneByligne.get(i+1).getX()) - 1;
                if(ligneByligne.get(i).getX()+1 !=ligneByligne.get(i+1).getX()){
                    if (diff != -1){
                        for (int j = 0; j < diff; j++) {
                            ligneByligne.add(i+j+1 , Piece.empty());
                            limite = ligneByligne.size();
                        }
                    }
                }
            }
        }
    }

    private void prepareListeForAffichePosable(List<Piece<E>> ligneByligne) {
        //Mettre des null dans les trous
        int minX = jeu.plateau.getMinX(true);
        ligneByligne.sort(Comparator.comparing(Piece::getX));
        int originSize=ligneByligne.size();
        if (ligneByligne.get(0)!=null && ligneByligne.get(0).getX()!= jeu.plateau.getMinX(true)){
            int diffdbt = ligneByligne.get(0).getX() - minX;
            for (int j = 0; j < diffdbt; j++) {
                ligneByligne.add(j , Piece.empty());
            }
        }
        if (originSize != 1){
            int limite = ligneByligne.size();
            for (int i = 0; i < limite - 1; i++) {
                if (ligneByligne.get(i).equals(Piece.empty())){
                    continue;
                }
                int diff = Math.abs(ligneByligne.get(i).getX() - ligneByligne.get(i+1).getX()) - 1;
                if(ligneByligne.get(i).getX()+1 !=ligneByligne.get(i+1).getX()){
                    if (diff != -1){
                        for (int j = 0; j < diff; j++) {
                            ligneByligne.add(i+j+1 , Piece.empty());
                            limite = ligneByligne.size();
                        }
                    }
                }
            }
        }
    }

    public int askWheretoPlace(Joueur joueur){
        String tmp = affichageTerminal.askWhereToPlay(joueur.name);
        if (tmp.length()==2){
            int i0 = tmp.charAt(0);
            if ((i0>= 65 && i0<=90) ||(i0>= 97 && i0<=122)){
                int i1 = tmp.charAt(1);
                if (i1>= 48 && i1<=57){
                    int ind = ((i0>=97) ? (i0-97) : (i0-65))*10  + i1-48 ;
                    if (jeu.plateau.piecesPosable.size()> ind){
                            return ind;
                    }
                }
            }
        }
        return -1;
    }

    public void affichageMain(Piece piece) {
        affichageTerminal.afficheMain(piece);
    }

    public void resumeJoueur(int indJoueurs) {
        affichageTerminal.affichePointJoueurs(jeu.getJoueursName(indJoueurs) , jeu.getJoueursPoints(indJoueurs));
    }

    public boolean askWhatToDo() {
        String rep = (affichageTerminal.askWhereWhatToDo()).toLowerCase(Locale.ROOT);
        switch (rep) {
            case "abandonner":
            case "give up":
                case"a" :
                return false;
            case "draw":
            case "d":
            case "piocher" :
            case "p": return true;
            default: return askWhatToDo();
        }
    }

    public Piece askToDraw(){
        jeu.joueurs.get(jeu.indJoueurs).draw(jeu.plateau , jeu);
        return jeu.joueurs.get(jeu.indJoueurs).current;
    }

    public Piece<ObjectCarcassonne> askToDraw(boolean ignored){
        jeu.joueurs.get(jeu.indJoueurs).draw(jeu.plateau , jeu);
        Piece<ObjectCarcassonne> tmp  = jeu.joueurs.get(jeu.indJoueurs).currentCarc;
        return jeu.joueurs.get(jeu.indJoueurs).currentCarc;
    }

    public void afficheClassement() {
        jeu.joueurs.sort(Comparator.comparingInt(joueur -> joueur.points));
        affichageTerminal.afficheClassement(jeu.joueurs);
    }

    public boolean askToTurn(boolean ignore) {
        if (isTerm){
            String turn = affichageTerminal.askToTurn();
            switch (turn){
                case "R":
                case "RIGHT":
                case "DROITE":
                case "D":
                    jeu.joueurs.get(jeu.indJoueurs).current.tournerUnePiece();
                    affichagePosage(jeu.plateau.pieces , jeu.plateau.piecesPosable);
                    affichageMain(jeu.joueurs.get(jeu.indJoueurs).current);
                    return askToTurn(true);
                case "V":
                case "VALID":
                case "VALIDER":
                case "STOP":
                case "S":
                    return true;
            }
            return false;
        }else{
            jeu.joueurs.get(jeu.indJoueurs).current.tournerUnePiece();
            return true;
        }
    }

    public Piece askToTurn() {
            jeu.joueurs.get(jeu.indJoueurs).current.tournerUnePiece();
            return jeu.joueurs.get(jeu.indJoueurs).current;
    }

    public Piece<ObjectCarcassonne> askToTurnCarc(boolean ignored) {
        jeu.joueurs.get(jeu.indJoueurs).currentCarc.tournerUnePiece();
        return jeu.joueurs.get(jeu.indJoueurs).currentCarc;
    }

    public boolean askToDump(Joueur joueur) {
        String tmp = affichageTerminal.askToDump();
        switch (tmp){
            case "o":
            case "oui":
            case "y":
            case "yes":
                joueur.dumpCurrent();
                return true;
            case "n":
            case "non":
            case "no":
                return false;
        }
        return false;
    }

    public void askToDump() {
        jeu.joueurs.get(jeu.indJoueurs).dumpCurrent();
    }

    public int askHowManyPlayers() {
        String tmp = affichageTerminal.askPlayers();
        int res = 0;
        try {
            res = Integer.parseInt(tmp);
        }catch (Exception ignored){}
        if (res <=0){
            return askHowManyPlayers();
        }else{
            return res;
        }
    }

    public int askHowManyBot() {
        String tmp = affichageTerminal.askBots();
        int res = 0;
        try {
            res = Integer.parseInt(tmp);
        }catch (Exception ignored){}
        if (res <=0){
            return askHowManyBot();
        }else{
            return res;
        }
    }

    public String askName(int i) {
        return affichageTerminal.askName(i);
    }

    public int askHowManyTuiles() {
        String tmp = affichageTerminal.askTuiles();
        int res = 0;
        try {
            res = Integer.parseInt(tmp);
        }catch (Exception ignored){}
        if (res <=0){
            return askHowManyPlayers();
        }else{
            return res;
        }
    }

    public void toNextPLayer(){
        jeu.nextPlayer();
    }

    public boolean askLevelBot(){
        String s = affichageTerminal.askLevel();
        if (s.equals("pro")) return true;
        else if (s.equals("easy")) return false;
        else return askLevelBot();
    }

    public void afficheWhoPlay(Joueur joueur) {
        affichageTerminal.afficheActualPlayer(joueur.name , joueur.points);
    }

    @SuppressWarnings(" unchecked ")
    public void createPlayer(boolean isDomino, String[] name , boolean[] bots , Color[] color) {
        if (affichageGraphique.joueurs == null){
            affichageGraphique.joueurs = new ArrayList<>(name.length + bots.length);
        }
        for (int i = 0; i < name.length; i++) {
            jeu.joueurs.add(new Joueur(name[i], isDomino));
            if (!isTerm) {
                affichageGraphique.joueurs.add(new PopUpPlayer<E>(this, name[i], 0, isDomino, affichageGraphique));
            }else{
                affichageGraphique.joueurs.add(new PopUpPlayer<E>(this, name[i], 0, isDomino, affichageGraphique  , color[i]));
            }
        }
        for (int i = 0; i < bots.length; i++) {
            if (bots[i]){
                jeu.joueurs.add(new OrdiPro("Ordi "+(i+1),isDomino));
            }else{
                jeu.joueurs.add(new Ordi("Ordi "+(i+1),isDomino));
            }
            if (isDomino){
                affichageGraphique.joueurs.add(new PopUpPlayer<E>(this, "Ordi "+(i+1), 0, true, affichageGraphique , true , null));
            }else {
                affichageGraphique.joueurs.add(new PopUpPlayer<E>(this, "Ordi "+(i+1), 0, false, affichageGraphique , true , color[i+ name.length]));
            }
        }
    }

    public void newGame(int nbPiece , boolean isTerm, boolean isDomino) {
        if (isTerm){
            this.isTerm = true;
        }else {
            jeu = new Jeu<>(nbPiece , isDomino);
            jeu.controleur = this;
        }
    }

    public void launch(boolean isTerm){
        affichageTerminal = new TermAffiche();
        if (!isTerm){
            affichageGraphique = new MainPage<E>(this);
            affichageGraphique.controleur = this;
        }else{
            this.isTerm = true;
            Jeu<E> jeu1 = new Jeu<>();
            jeu = jeu1;
            jeu.controleur = this;
            jeu.play(true);
        }
    }

    public int getPointActualPlayer() {
        return jeu.joueurs.get(jeu.indJoueurs).points;
    }

    public Joueur getActualPlayer() {
        return jeu.joueurs.get(jeu.indJoueurs);
    }

    public boolean poseTo(int x, int y, boolean isDomino) {
        if (isDomino){
            return jeu.plateau.placePiece((Domino) getActualPlayer().current , x , y , getActualPlayer());
        }else {
            return jeu.plateau.placePiece(getActualPlayer().currentCarc , x , y , getActualPlayer());
        }
    }

    public void giveUp() {
        jeu.joueurs.remove(jeu.indJoueurs);
    }

    public int getActualPlayerInd() {
        return jeu.indJoueurs;
    }

    public boolean isNotEndGame() {
        return jeu.joueurs.size() != 1 && jeu.pioches > 0;
    }

    public Object[][] getWinners() {
        return jeu.getWinners();
    }

    public int getPioches() {
        return jeu.pioches;
    }

    public void playOrdi() {
        getActualPlayer().draw(jeu.plateau , jeu);
        getActualPlayer().playerAction(this , jeu.plateau);
    }

    public void posePion(Piece<ObjectCarcassonne> e, int i, int j , Color color) {
        e.posePion(i , j ,color , getActualPlayer());
    }

    public boolean pionOnPiece(Piece<ObjectCarcassonne> e) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (e.vals[i][j] instanceof ObjectCarcassonne){
                    ObjectCarcassonne tmp =e.vals[i][j];
                    if (tmp.pion != null) return true;
                }
            }
        }
        if (e.centre instanceof ObjectCarcassonne) {
            ObjectCarcassonne tmp = e.centre;
            return tmp.pion != null;
        }
        return false;
    }

    public int[] pionPos(Piece<ObjectCarcassonne> e) {
        int[] pos = new int[2];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (e.vals[i][j].pion != null){
                    if (i == 0){
                        pos[0] = i ;
                        pos[1] = j ;
                    }else if (i == 2){
                        pos[0] = 2 ;
                        pos[1] = 2 -j ;
                    }else{
                        pos[0] = 2 ;
                        pos[1] = 3 - i ;
                    }
                }
            }
        }
        if (e.centre.pion != null) {
            pos[0] = 1;
            pos[1] = 1;
        }
        return pos;
    }

    public Color pionColor(Piece<ObjectCarcassonne> e) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (e.vals[i][j].pion != null) return e.vals[i][j].pion.color;
            }
        }
        return e.centre.pion.color;
    }

    public static class ComparatorX implements Comparator<Piece>{
        @Override
        public int compare(Piece piece, Piece t1) {
            return piece.x - t1.x;
        }

    }
    public static class ComparatorY implements Comparator<Piece>{
        public int compare(Piece piece, Piece t1) {
            return t1.y - piece.y;
        }

    }

    public int getHowMuchTurn(Piece piece){
        return piece.tourne;
    }

}
