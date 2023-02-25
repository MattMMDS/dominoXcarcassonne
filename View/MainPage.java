package View;

import Controleur.Controleur;
import Model.Piece;
import Model.PieceCarcassonne;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainPage <E> extends JFrame {

    public boolean isDomino = true;
    public JPanel actualFrame;

    public ArrayList<PopUpPlayer> joueurs;
    public int indJoueur;
    public Controleur controleur;


    public MainPage (){
        setTitle("DominoXCarcassonne");
        this.setSize(1000,1000);
        this.actualFrame = selectGame();
        majAffichage();
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocation(400 , 0);
    }

    public <E> MainPage(Controleur<E> eControleur) {
        controleur = eControleur;
        setTitle("DominoXCarcassonne");
        setSize(1000,1000);
        actualFrame = selectGame();
        majAffichage();
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocation(400 , 0);
    }

    public void majAffichage(){
        setContentPane(actualFrame);
        SwingUtilities.updateComponentTreeUI(this);
    }
    public void afficheListePiece(int size, Map<Integer, List<Piece<E>>> treeMap) {
        setLayout(new BorderLayout(8,8));
        GridPlateau<E> p = new GridPlateau<>(size , treeMap , controleur.getPioches(), isDomino , this);
        actualFrame = p;
        joueurs.get(indJoueur).setVisible(true);
        majAffichage();
    }

    @SuppressWarnings(" unchecked ")
    public <A> void afficheListePiecePosable(int size , Map<Integer, List<Piece<A>>> treeMap, ArrayList<Piece<A>> piecesPosable) {
        setLayout(new BorderLayout(8,8));
        GridPlateau<A> p = new GridPlateau<A>(controleur, size , treeMap , piecesPosable , isDomino, controleur.getPioches() , this);
        p.mainPage = (MainPage<A>) this;
        actualFrame = p;
        majAffichage();
    }

    public void afficheListePiece(int size, Map<Integer, List<PieceCarcassonne>> treeMap , boolean ignored) {
        setLayout(new BorderLayout(8,8));
        GridPlateau<E> p = new GridPlateau<>(this);
        p = new GridPlateau<E>(controleur, size , treeMap , controleur.getPioches());
        actualFrame = p;
        joueurs.get(indJoueur).setVisible(true);
        majAffichage();
    }

    @SuppressWarnings(" unchecked ")
    public <A> void afficheListePiecePosable(int size , Map<Integer, List<PieceCarcassonne>> treeMap, ArrayList<PieceCarcassonne> piecesPosable , boolean ignored) {
        setLayout(new BorderLayout(8,8));
        GridPlateau<A> p = new GridPlateau<>(this);
        p = new GridPlateau<A>(controleur, size , treeMap , piecesPosable , controleur.getPioches() ,this);
        actualFrame = p;
        majAffichage();
    }

    public JPanel selectGame(){
        JPanel res = new JPanel();
        JButton btnCarcassonne = new JButton();
        JButton btnDomino = new JButton();

        JLabel titre = new JLabel ("Domino X Carcassonne");
        JLabel text1 = new JLabel ("Choisissez entre");
        JLabel text2 = new JLabel ("ou");
        //adjust size and set layout
        res.setPreferredSize (new Dimension (1000, 1000));
        res.setLayout (null);

        //add components
        res.add (btnDomino);
        res.add (btnCarcassonne);
        res.add (titre);
        res.add (text1);
        res.add (text2);

        //TODO a revoir placement
        btnDomino.setBounds (315, 325, 135, 50);
        btnCarcassonne.setBounds (495, 325, 135, 50);
        text2.setBounds (460, 340, 18, 25);

        titre.setBounds (418, 50, 164, 25);
        text1.setBounds (420, 270, 120, 25);

        btnCarcassonne.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                isDomino = false;
                MainPage.this.actualFrame = askPlayerCarc();
                MainPage.this.setContentPane(MainPage.this.actualFrame);
                SwingUtilities.updateComponentTreeUI(MainPage.this);
            }
        });
        btnDomino.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                isDomino = true;
                MainPage.this.actualFrame = askPlayerDom();
                MainPage.this.setContentPane(MainPage.this.actualFrame);
                SwingUtilities.updateComponentTreeUI(MainPage.this);
            }
        });
        btnCarcassonne.setText("Carcassonne");
        btnDomino.setText("Domino");
        return res;
    }

    private JPanel askPlayerCarc() {
        JPanel res= new JPanel();

        JLabel textJoueur = new JLabel ("Combien de joueur ");
        JLabel textBots = new JLabel ("Combien de bots");
        JLabel textWarning = new JLabel ("Attention vous n'avez le droit qu'a 5 joueurs maximum");
        JSlider joueursSliders = new JSlider (0, 6);
        JSlider botsSliders = new JSlider (0, 6);
        JLabel textNbJoueur = new JLabel ("2");
        JLabel textNbBots = new JLabel ("0");
        JButton valider = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (joueursSliders.getValue() + botsSliders.getValue() > 1 && joueursSliders.getValue() + botsSliders.getValue() < 7){
                    controleur.newGame(72 , false , isDomino);
                    createPlayers(joueursSliders.getValue() , botsSliders.getValue());
                }
            }
        });

        valider.setText("Valider");

        //set components properties
        joueursSliders.setOrientation (JSlider.HORIZONTAL);
        joueursSliders.setMinorTickSpacing (1);
        joueursSliders.setMajorTickSpacing (2);
        joueursSliders.setPaintTicks (true);
        joueursSliders.setPaintLabels (true);
        joueursSliders.setValue(2);

        botsSliders.setOrientation (JSlider.HORIZONTAL);
        botsSliders.setMinorTickSpacing (1);
        botsSliders.setMajorTickSpacing (2);
        botsSliders.setPaintTicks (true);
        botsSliders.setPaintLabels (true);
        botsSliders.setValue(0);

        //adjust size and set layout
        res.setPreferredSize (new Dimension (1000, 1000));
        res.setLayout (null);

        res.add (textJoueur);
        res.add (textBots);
        res.add (textWarning);
        res.add (joueursSliders);
        res.add (botsSliders);
        res.add(textNbJoueur);
        res.add(textNbBots);
        res.add(valider);

        textJoueur.setBounds (290, 310, 145, 25);
        joueursSliders.setBounds (280, 390, 165, 50);
        textNbJoueur.setBounds (373, 350, 10, 25);


        textBots.setBounds (515, 310, 140, 25);
        botsSliders.setBounds (495, 390, 165, 50);
        textNbBots.setBounds (563, 350, 10, 25);

        textWarning.setBounds (377, 100, 246, 25);

        valider.setBounds (440, 525, 120, 50);


        botsSliders.addChangeListener(changeEvent -> {
            textNbBots.setText(String.valueOf(botsSliders.getValue()));
            joueursSliders.setMaximum(6 - botsSliders.getValue());
            textNbBots.repaint();
        });

        joueursSliders.addChangeListener(changeEvent -> {
            textNbJoueur.setText(String.valueOf(joueursSliders.getValue()));
            botsSliders.setMaximum(6 - joueursSliders.getValue());
            textNbJoueur.repaint();
        });
        return res;
    }

    public JPanel askPlayerDom(){
        JPanel res= new JPanel();

        JLabel textJoueur = new JLabel ("Combien de joueur ");
        JLabel textBots = new JLabel ("Combien de bots");
        JLabel textDom = new JLabel ("Combien de domino dans la pioche");
        JSlider joueursSliders = new JSlider (0, 10);
        JSlider botsSliders = new JSlider (0, 9);
        JSlider domSliders = new JSlider (0, 99);
        JLabel textNbJoueur = new JLabel ("2");
        JLabel textNbDom = new JLabel ("20");
        JLabel textNbBots = new JLabel ("0");
        JButton valider = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (domSliders.getValue() != 0 && joueursSliders.getValue() + botsSliders.getValue() > 1){
                    controleur.newGame(domSliders.getValue() , false , isDomino);
                    createPlayers(joueursSliders.getValue() , botsSliders.getValue());
                }
            }
        });

        valider.setText("Valider");


        //set components properties
        joueursSliders.setOrientation (JSlider.HORIZONTAL);
        joueursSliders.setMinorTickSpacing (1);
        joueursSliders.setMajorTickSpacing (2);
        joueursSliders.setPaintTicks (true);
        joueursSliders.setPaintLabels (true);
        joueursSliders.setValue(2);

        botsSliders.setOrientation (JSlider.HORIZONTAL);
        botsSliders.setMinorTickSpacing (1);
        botsSliders.setMajorTickSpacing (2);
        botsSliders.setPaintTicks (true);
        botsSliders.setPaintLabels (true);
        botsSliders.setValue(0);

        domSliders.setOrientation (JSlider.HORIZONTAL);
        domSliders.setMinorTickSpacing (5);
        domSliders.setMajorTickSpacing (10);
        domSliders.setPaintTicks (true);
        domSliders.setPaintLabels (true);
        domSliders.setValue(20);

        //adjust size and set layout
        res.setPreferredSize (new Dimension (1000, 1000));
        res.setLayout (null);

        res.add (textJoueur);
        res.add (textBots);
        res.add (textDom);
        res.add (joueursSliders);
        res.add (botsSliders);
        res.add (domSliders);
        res.add(textNbJoueur);
        res.add(textNbBots);
        res.add(textNbDom);
        res.add(valider);

        textJoueur.setBounds (290, 310, 145, 25);
        joueursSliders.setBounds (280, 390, 165, 50);
        textNbJoueur.setBounds (373, 350, 10, 25);


        textBots.setBounds (515, 310, 140, 25);
        botsSliders.setBounds (495, 390, 165, 50);
        textNbBots.setBounds (563, 350, 10, 25);

        textDom.setBounds (377, 100, 246, 25);
        domSliders.setBounds (375, 180, 250, 50);
        textNbDom.setBounds (481, 140, 18, 25);

        valider.setBounds (440, 525, 120, 50);


        botsSliders.addChangeListener(changeEvent -> {
            textNbBots.setText(String.valueOf(botsSliders.getValue()));
            textNbBots.repaint();
        });

        domSliders.addChangeListener(changeEvent -> {
            textNbDom.setText(String.valueOf(domSliders.getValue()));
            textNbDom.repaint();
        });

        joueursSliders.addChangeListener(changeEvent -> {
            textNbJoueur.setText(String.valueOf(joueursSliders.getValue()));
            textNbJoueur.repaint();
        });
        return res;
    }

    public void createPlayers(int nbJoueurs, int nbBots){
        int rows = (nbJoueurs + nbBots + 1)/3;
        JPanel tmp = new JPanel(new GridLayout(rows , 3));
        JPanel[] joueurs = new JPanel[nbJoueurs+nbBots+1];
        JTextField[] names = new JTextField[nbJoueurs];
        boolean[] bots = new boolean[nbBots];
        Color[] colors = new Color[]{Color.GRAY , Color.BLACK , Color.YELLOW , Color.blue , Color.green , Color.red};
        for (int i = 0; i < nbJoueurs; i++) {
            joueurs[i] = new JPanel();
            names[i] = new JTextField (4);
            JLabel text = new JLabel ("Entrez votre nom");
            joueurs[i].add (text);
            joueurs[i].add (names[i]);

            if (!isDomino){
                JPanel color = new JPanel();
                color.setPreferredSize(new Dimension(25,25));
                color.setBackground(colors[i]);
                joueurs[i].add(color);
            }
            tmp.add(joueurs[i]);
        }
        for (int i = nbJoueurs; i < (nbJoueurs+nbBots); i++) {
            joueurs[i] = new JPanel();
            JLabel text = new JLabel ("Sélectionnez le niveau du bots "+ (i - nbJoueurs +1));
            int finalI = i;
            JButton nivBot = new JButton();
            nivBot.setAction(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if (nivBot.getText().equals("Normal")){
                        nivBot.setText("Fort");
                        bots[finalI -nbJoueurs] = true;
                    }else {
                        nivBot.setText("Normal");
                        bots[finalI -nbJoueurs] = false;
                    }
                    nivBot.repaint();
                }
            });
            nivBot.setText("Normal");


            joueurs[i].add (text);

            text.setBounds (125, 20, 130, 45);

            if (!isDomino){
                JPanel color = new JPanel();
                color.setPreferredSize(new Dimension(25,25));
                color.setBackground(colors[i]);
                joueurs[i].add(color);
                text.setText("Bots "+ (i - nbJoueurs +1));
            }else{
                joueurs[i].add (nivBot);
            }

            tmp.add(joueurs[i]);
        }
        joueurs[nbJoueurs + nbBots] = new JPanel();
        JButton validate = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String[] name = new String[nbJoueurs];
                boolean flag = true;
                for (int i = 0; i < nbJoueurs; i++) {
                    if (names[i].getText().equals("")){
                        flag = false;
                    }else{
                        name[i] = names[i].getText();
                    }
                }
                if (flag){
                    controleur.createPlayer(isDomino , name , bots , colors);
                    MainPage.this.controleur.affichage(isDomino);
                }
            }
        });
        validate.setText("Valider");
        joueurs[nbJoueurs + nbBots].add(validate);
        tmp.add(joueurs[nbJoueurs + nbBots]);
        actualFrame = tmp;
        majAffichage();
    }

    public void toNextPlayer(boolean isAfterDump) {
        if (!isAfterDump){
            joueurs.get(indJoueur).setVisible(false);
            indJoueur = controleur.getActualPlayerInd();
            controleur.toNextPLayer();
            indJoueur = controleur.getActualPlayerInd();
            joueurs.get(indJoueur).majPlayer();
            joueurs.get(indJoueur).setVisible(true);
        }else{
            joueurs.get(indJoueur).setVisible(false);
            joueurs.remove(indJoueur);
            joueurs.get(indJoueur).majPlayer();
            joueurs.get(indJoueur).setVisible(true);
        }
    }

    public void toNextPlayer() {
        joueurs.get(indJoueur).setVisible(false);
        indJoueur = controleur.getActualPlayerInd();
        controleur.toNextPLayer();
        indJoueur = controleur.getActualPlayerInd();
        joueurs.get(indJoueur).majPlayer();
        joueurs.get(indJoueur).setVisible(true);
        afficheListePiece();
    }

    public void setToNextPlayerAction(){
        joueurs.get(indJoueur).toNextPlayer(controleur , false);
    }

    public void afficheListePiece() {
        controleur.affichage(isDomino);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void affichageTour() {
        controleur.affichagePosage(isDomino);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void win(Controleur c) {
        JPanel fin = new JPanel(null);
        Object[][] winners = c.getWinners();
        JLabel text;
        if (winners.length==1){
            text = new JLabel ("Le grand vainqueur est : "+ winners[0][0] +" avec "+ winners[0][1] +" il est le seul a ne pas avoir abandonné");
            text.setBounds (272, 470, 575, 30);
        }else{
            text = new JLabel("Voila le classement des vainqueurs");
            text.setBounds (372, 75, 255, 30);
            String[] columnNames = {"Nom", "Points"};
            JTable gagnant = new JTable(winners , columnNames){
                private static final long serialVersionUID = 1L;

                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            gagnant.setBounds(250 , 150 , 500 , 700);
            gagnant.setCellSelectionEnabled(false);
            gagnant.setColumnSelectionAllowed(false);
            JTableHeader header = gagnant.getTableHeader();
            header.setBounds(250 , 120 , 500 , 30);
            fin.add(header);
            fin.add(gagnant);
        }
        fin.add(text);
        actualFrame = fin;
        majAffichage();
    }

    public void setPosePion() {
        joueurs.get(indJoueur).posePion(controleur);
    }
}
