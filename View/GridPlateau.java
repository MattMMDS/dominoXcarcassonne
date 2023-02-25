package View;

import Controleur.Controleur;
import Model.ObjectCarcassonne;
import Model.Piece;
import Model.PieceCarcassonne;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GridPlateau<E> extends JPanel {

    public JPanel plateau = new JPanel();
    public MainPage<E> mainPage;

    private int size;
    private DomButton[][] boutonsDom;
    private DomButton.CarcassonneButton[][] boutonsCarcassonne;

    @SuppressWarnings(" unchecked ")
    public GridPlateau(int size, Map<Integer, List<Piece<E>>> treeMap , int resteDom , boolean isDomino , MainPage m) {
        setLayout(new BorderLayout());
        mainPage = m;
        if (size< 4){
            size = 5;
        }
        this.size = size;
        plateau.setLayout(new GridLayout(size, size));
        if (isDomino){
            boutonsDom = new DomButton[size][size];
        }else {
            boutonsCarcassonne = new DomButton.CarcassonneButton[size][size];
        }
        int relativeX = (size/2) ;
        int relativeY = (size/2) ;
        int finalSize = size;
        if (isDomino){
            treeMap.forEach((y , ligne) -> {
                for (Piece e: ligne) {
                    boutonsDom[relativeY - y][relativeX + e.getX()] = new DomButton(e , finalSize);
                }
            });
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (boutonsDom[i][j] == null) {
                        boutonsDom[i][j] = new DomButton();
                        boutonsDom[i][j].setEnabled(false);
                    }
                    plateau.add(boutonsDom[i][j]);
                }
            }
        }

        plateau.setPreferredSize(new Dimension(1000 , 1000));
        enTeteSet(resteDom);
        add(plateau , BorderLayout.CENTER);
    }

    @SuppressWarnings(" unchecked ")
    public GridPlateau(Controleur<E> controleur, int size, Map<Integer, List<Piece<E>>> treeMap, ArrayList<Piece<E>> piecesPosable , boolean isDomino , int resteDom , MainPage m) {
        mainPage = m;
        setLayout(new BorderLayout());
        if (size<= 4){
            size = 5;
        }
        this.size = size;
        plateau.setLayout(new GridLayout(size, size));
        if (isDomino){
            boutonsDom = new DomButton[size][size];
        }
        int relativeX =(size/2);
        int relativeY = (size/2);
        int finalSize = size;
        treeMap.forEach(( y , ligne) -> {
            for (Piece<E> e: ligne) {
                boutonsDom[relativeY - y][relativeX + e.getX()] = new DomButton((Piece<Integer>) e, finalSize);
            }
        });
        for (Piece<E> e: piecesPosable) {
            AbstractAction action = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if (controleur.poseTo(e.getX(), e.getY(), isDomino)) {
                        mainPage.afficheListePiece();
                        mainPage.setToNextPlayerAction();
                    } else {
                    }
                }
            };
            boutonsDom[relativeY - e.getY()][relativeX + e.getX()] = new DomButton(action , size);
            boutonsDom[relativeY - e.getY()][relativeX + e.getX()].setAction(action);

            boutonsDom[relativeY - e.getY()][relativeX + e.getX()].changeIcon(size);
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (boutonsDom[i][j] == null) {
                    boutonsDom[i][j] = new DomButton();
                    boutonsDom[i][j].setEnabled(false);
                }
                plateau.add(boutonsDom[i][j]);
            }
        }
        enTeteSet(resteDom);
        add(plateau , BorderLayout.CENTER);
    }

    public GridPlateau(Controleur controleur, int size, Map<Integer, List<PieceCarcassonne>> treeMap , int resteDom) {
        setLayout(new BorderLayout());
        if (size< 4){
            size = 5;
        }
        this.size = size;
        plateau.setLayout(new GridLayout(size, size));
        boutonsCarcassonne = new DomButton.CarcassonneButton[size][size];
        int relativeX = (size/2) ;
        int relativeY = (size/2) ;
        int finalSize = size;
        treeMap.forEach((y , ligne) -> {
            for (Piece<ObjectCarcassonne> e: ligne) {
                boutonsCarcassonne[relativeY - y][relativeX + e.getX()] = new DomButton.CarcassonneButton(e.getOriginal() , finalSize);
                if (e.tourne>0){
                    for (int i = 0; i < e.tourne; i++) {
                        boutonsCarcassonne[relativeY - y][relativeX + e.getX()].turnImage(finalSize);
                        if (controleur.pionOnPiece(e)){
                            int[] pos = controleur.pionPos(e);
                            boutonsCarcassonne[relativeY - y][relativeX + e.getX()].dessinePion(pos[0],pos[1], mainPage.controleur.pionColor(e));
                        }
                    }
                }
            }
        });
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (boutonsCarcassonne[i][j] == null) {
                    boutonsCarcassonne[i][j] = new DomButton.CarcassonneButton();
                    boutonsCarcassonne[i][j].setEnabled(false);
                }
                plateau.add(boutonsCarcassonne[i][j]);
            }
        }

        plateau.setPreferredSize(new Dimension(1000 , 1000));
        enTeteSet(resteDom);
        add(plateau , BorderLayout.CENTER);
    }
    
    public static GridPlateau piecesVisibles (int size, Map<Integer, List<PieceCarcassonne>> treeMap , int resteDom , MainPage m){
        GridPlateau plateau1 = new GridPlateau(m);
        plateau1.setLayout(new BorderLayout());
        if (size< 4){
            size = 5;
        }
        plateau1.size = size;
        plateau1.plateau.setLayout(new GridLayout(size, size));
        plateau1.boutonsCarcassonne = new DomButton.CarcassonneButton[size][size];
        int relativeX = (size/2) ;
        int relativeY = (size/2) ;
        int finalSize = size;
        treeMap.forEach((y , ligne) -> {
            for (Piece<ObjectCarcassonne> e: ligne) {
                plateau1.boutonsCarcassonne[relativeY - y][relativeX + e.getX()] = new DomButton.CarcassonneButton(e.getOriginal() , finalSize);
                if (e.tourne>0){
                    for (int i = 0; i < e.tourne; i++) {
                        plateau1.boutonsCarcassonne[relativeY - y][relativeX + e.getX()].turnImage(finalSize);
                        if (m.controleur.pionOnPiece(e)){
                            int[] pos = m.controleur.pionPos(e);
                            plateau1.boutonsCarcassonne[relativeY - y][relativeX + e.getX()].dessinePion(pos[0],pos[1], m.controleur.pionColor(e));
                        }
                    }
                }
            }
        });
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (plateau1.boutonsCarcassonne[i][j] == null) {
                    plateau1.boutonsCarcassonne[i][j] = new DomButton.CarcassonneButton();
                    plateau1.boutonsCarcassonne[i][j].setEnabled(false);
                }
                plateau1.plateau.add(plateau1.boutonsCarcassonne[i][j]);
            }
        }

        plateau1.plateau.setPreferredSize(new Dimension(1000 , 1000));
        plateau1.enTeteSet(resteDom);
        plateau1.add(plateau1.plateau , BorderLayout.CENTER);
        return plateau1;
    }

    @SuppressWarnings(" unchecked ")
    public GridPlateau(Controleur<E> controleur, int size, Map<Integer, List<PieceCarcassonne>> treeMap, ArrayList<PieceCarcassonne> piecesPosable , int resteDom , MainPage m) {
        setLayout(new BorderLayout());
        mainPage = m;
        if (size<= 4){
            size = 5;
        }
        this.size = size;
        plateau.setLayout(new GridLayout(size, size));
        boutonsCarcassonne = new DomButton.CarcassonneButton[size][size];
        int relativeX =(size/2);
        int relativeY = (size/2);
        int finalSize = size;
        treeMap.forEach(( y , ligne) -> {
            for (Piece<ObjectCarcassonne> e: ligne) {
                boutonsCarcassonne[relativeY - y][relativeX + e.getX()] = new DomButton.CarcassonneButton( e.getOriginal(), finalSize);
                if (controleur.getHowMuchTurn(e)>0){
                    for (int i = 0; i < controleur.getHowMuchTurn(e); i++) {
                        boutonsCarcassonne[relativeY - y][relativeX + e.getX()].turnImage(finalSize);
                        if (controleur.pionOnPiece(e)){
                            int[] pos = controleur.pionPos(e);
                            boutonsCarcassonne[relativeY - y][relativeX + e.getX()].dessinePion(pos[0],pos[1], controleur.pionColor(e));
                        }
                    }
                }
            }
        });
        for (Piece<ObjectCarcassonne> e: piecesPosable) {
            AbstractAction action = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if (controleur.poseTo(e.getX(), e.getY(), false)) {
                        mainPage.setPosePion();
                    }else {
                    }
                }
            };
            boutonsCarcassonne[relativeY - e.getY()][relativeX + e.getX()] = new DomButton.CarcassonneButton(action , size);
            boutonsCarcassonne[relativeY - e.getY()][relativeX + e.getX()].setAction(action);

            boutonsCarcassonne[relativeY - e.getY()][relativeX + e.getX()].changeIcon(size);
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (boutonsCarcassonne[i][j] == null) {
                    boutonsCarcassonne[i][j] = new DomButton.CarcassonneButton();
                    boutonsCarcassonne[i][j].setEnabled(false);
                }
                plateau.add(boutonsCarcassonne[i][j]);
            }
        }
        enTeteSet(resteDom);
        add(plateau , BorderLayout.CENTER);
    }

    public GridPlateau(MainPage m) {
        mainPage = m;
    }


    private void enTeteSet(int resteDom){
        JPanel haut=new JPanel();
        JLabel reste = new JLabel("Domino restant: "+resteDom);
        haut.add(reste);

        haut.setPreferredSize(new Dimension(1000, (1000 / 5)));
        haut.setPreferredSize(new Dimension(1050 , 50 ));

        add(haut , BorderLayout.NORTH);
    }
}


