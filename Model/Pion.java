package Model;

import java.awt.*;

public class Pion {
    public Joueur joueur;
    public Color color;

    public Pion(Joueur joueur) {
        this.joueur = joueur;
    }

    public Pion(Joueur joueur, Color color) {
        this.joueur = joueur;
        this.color = color;
    }
}
