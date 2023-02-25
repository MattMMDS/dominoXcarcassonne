package View;

import Controleur.Controleur;
import Model.ObjectCarcassonne;
import Model.Piece;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PopUpPlayer<E> extends JFrame {
    private JButton btnPioche;
    private JButton pivoter;
    private JButton dump;
    private JLabel name;
    private DomButton pieceDom;
    private JButton abandonner;
    private JLabel points;
    private DomButton.CarcassonneButton pieceCarcassonne;
    private JPanel pieceCarcassonnePion;
    private Color color;

    public MainPage mainPage;

    private Piece hand;
    private Piece<ObjectCarcassonne> handCarc;

    private boolean isDomino;
    private boolean isBot = false;

    public PopUpPlayer(Controleur<E> c , String nom, int point, boolean isDomino , MainPage mainPage) {
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(playerPage(c , nom, point, isDomino , null));
        setSize(350 , 395);
        this.mainPage = mainPage;
    }

    public PopUpPlayer(Controleur<E> c , String nom, int point, boolean isDomino , MainPage mainPage , boolean isBot , Color color) {
        this.isBot = isBot;
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(playerPage(c , nom, point, isDomino , color));
        setSize(350 , 395);
        this.mainPage = mainPage;
    }

    public PopUpPlayer(Controleur<E> c , String nom, int point, boolean isDomino , MainPage mainPage , Color color) {
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(playerPage(c , nom, point, isDomino , color));
        setSize(350 , 395);
        this.mainPage = mainPage;
    }

    public JPanel playerPage(Controleur<E> c , String nom, int point, boolean isDomino, Color color){
        JPanel res = new JPanel();
        if (!isBot){
            btnPioche = new JButton ();
            pivoter = new JButton ();
            dump = new JButton ("Passer");
            name = new JLabel (nom);
            abandonner = new JButton ("Give Up");
            this.isDomino = isDomino;

            if (point>1){
                points = new JLabel (point+" points");
            }else {
                points = new JLabel (point+" point");
            }

            setPiocher(c , isDomino);
            setDump(c);
            setGiveUp(c);
            setPivoterAction(c , isDomino);
            dump.setEnabled(false);
            pivoter.setEnabled(false);
            pieceDom =  new DomButton();


            res.setPreferredSize (new Dimension (350, 395));
            res.setLayout (null);


            res.add (name);
            if (isDomino){
                res.add (pieceDom);
                pieceDom.setBounds (45, 190, 150, 150);
            }else{
                pieceCarcassonne = new DomButton.CarcassonneButton();
                this.color = color;
                JPanel colorPan = new JPanel();
                colorPan.setPreferredSize(new Dimension(25,25));
                colorPan.setBackground(color);
                res.add(colorPan);
                res.add (pieceCarcassonne);
                pieceCarcassonne.setBounds (45, 190, 150, 150);
            }
            res.add (btnPioche);
            res.add (abandonner);
            res.add (points);
            res.add (pivoter);
            res.add (dump);


            btnPioche.setBounds (50, 85, 95, 45);
            pivoter.setBounds (205, 190, 105, 35);
            dump.setBounds (205, 255, 105, 35);
            name.setBounds (113, 0, 110, 35);
            abandonner.setBounds (205, 85, 95, 45);
            points.setBounds (110, 25, 100, 25);
        }else{
            JButton btnSuivant = new JButton ("Passer au joueur suivant ");
            name = new JLabel(String.valueOf(nom.charAt(nom.length()-1)));
            JLabel textName = new JLabel ("Tour de l'ordinateur " + nom.charAt(nom.length()-1));
            points = new JLabel (String.valueOf(point));

            btnSuivant.setAction(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if (!isDomino){
                        c.askToDraw(true);
                    }
                    c.playOrdi();
                    if (c.isNotEndGame()) {
                        PopUpPlayer.this.mainPage.toNextPlayer();
                    } else {
                        PopUpPlayer.this.mainPage.win(c);
                        PopUpPlayer.this.setVisible(false);
                    }
                }
            });

            btnSuivant.setText("Passer au joueur suivant");

            res.setLayout (null);

            res.add (btnSuivant);
            res.add (textName);
            res.add (points);

            if (!isDomino){
                JPanel colorPan = new JPanel();
                colorPan.setPreferredSize(new Dimension(25,25));
                colorPan.setBackground(color);
                res.add(colorPan);
            }

            //set component bounds (only needed by Absolute Positioning)
            btnSuivant.setBounds (60, 150, 240, 25);
            textName.setBounds (100, 5, 165, 35);
            points.setBounds (160, 55, 35, 20);

        }
        return res;
    }

   public void setPivoterAction(Controleur c , boolean isDomino){
        pivoter.setAction(new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (isDomino){
                    hand = c.askToTurn();
                    pieceDom.changeIcon(hand, 8);
                    SwingUtilities.updateComponentTreeUI(pieceDom);
                }else{
                    handCarc = c.askToTurnCarc(false);
                    pieceCarcassonne.turnImage();
                    SwingUtilities.updateComponentTreeUI(pieceCarcassonne);
                }
            }
        });
        pivoter.setText("Pivoter ->");
   }

    public void setPiocher(Controleur c , boolean isDomino){
        btnPioche.setAction(new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (isDomino){
                    hand = c.askToDraw();
                    pieceDom.changeIcon(hand , 8);
                    SwingUtilities.updateComponentTreeUI(pieceDom);
                }else {
                    handCarc = c.askToDraw(true);
                    pieceCarcassonne.changeIcon(handCarc , 8);
                    SwingUtilities.updateComponentTreeUI(pieceCarcassonne);
                }
                mainPage.affichageTour();
                btnPioche.setEnabled(false);
                dump.setEnabled(true);
                pivoter.setEnabled(true);
            }
        });
        btnPioche.setText("Piocher");
    }


    public void setDump(Controleur c){
        dump.setAction(new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PopUpPlayer.this.hand = null;
                if (isDomino){
                    pieceDom = new DomButton();
                    SwingUtilities.updateComponentTreeUI(pieceDom);
                }else{
                    pieceCarcassonne= new DomButton.CarcassonneButton();
                    SwingUtilities.updateComponentTreeUI(pieceCarcassonne);
                }
                btnPioche.setEnabled(false);
                dump.setEnabled(false);
                pivoter.setEnabled(false);
                c.askToDump();
                PopUpPlayer.this.toNextPlayer(c , false);
                mainPage.afficheListePiece();
            }
        });
        dump.setText("Passer");
    }

    public void setGiveUp(Controleur c){
        abandonner.setAction(new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                c.giveUp();
                PopUpPlayer.this.toNextPlayer(c , true);
            }
        });
        abandonner.setText("Give Up");
    }

    protected void toNextPlayer(Controleur controleur , boolean isAfterGiveUp) {
        JPanel n = new JPanel();
        if (controleur.isNotEndGame()) {
            if (isAfterGiveUp) {
                name.setText(name.getText() + " a abandonner");
            } else {
                points.setText(String.valueOf(controleur.getActualPlayer().points));
                if (isDomino){
                    n.add(points);
                }
            }
        } else {
            name.setText("C'est la fin de la parti");
        }
        n.add(name);
        JButton next = new JButton();
        n.add(next);
        next.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (controleur.isNotEndGame()) {
                    PopUpPlayer.this.mainPage.toNextPlayer(isAfterGiveUp);
                } else {
                    PopUpPlayer.this.mainPage.win(controleur);
                    PopUpPlayer.this.setVisible(false);
                }
            }
        });
        if (controleur.isNotEndGame()) {
            next.setText("Passer au joueur suivant");
        } else {
            next.setText("Afficher les résultats");
        }
        this.setContentPane(n);
        SwingUtilities.updateComponentTreeUI(this);
    }

    @SuppressWarnings(" unchecked ")
    public void majPlayer() {
        setContentPane(playerPage(mainPage.controleur , name.getText() , mainPage.controleur.getPointActualPlayer() , isDomino , color));
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void posePion(Controleur c) {
        pieceCarcassonnePion = new JPanel(new GridLayout (3 , 3));
        pieceCarcassonnePion.setBounds (45, 190, 150, 150);
        JLabel text = new JLabel("Placer votre pion");
        text.setBounds (25, 170, 150, 25);
        add(text);
        remove(pieceCarcassonne);
        remove(pieceDom);
        dump.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainPage.toNextPlayer();
            }
        });
        dump.setText("Passer");
        pivoter.setEnabled(false);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int finalJ = j;
                int finalI = i;
                pieceCarcassonnePion.add(new JButton(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        c.posePion(handCarc , finalI, finalJ, color);
                        mainPage.afficheListePiece();
                        mainPage.setToNextPlayerAction();
                    }
                }));

            }
        }
        add(pieceCarcassonnePion);
        SwingUtilities.updateComponentTreeUI(this);
    }
}