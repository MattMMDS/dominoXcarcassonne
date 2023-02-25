package Model;

import Controleur.Controleur;

import java.util.Random;

public class Ordi extends Joueur{

	public Ordi(String nom, boolean isDomino) {
		super(nom, isDomino);
	}
	
	@SuppressWarnings("rawtypes")
	public boolean playerAction(Controleur c,Plateau p) {
		int[][] tabPosable = new int[4][p.piecesPosable.size()];
		for(int i = 0; i < 4; i++) {
			if (i>0) current.tournerUnePiece();
			for(int j = 0; j < p.piecesPosable.size(); j++) {
				Piece temp = (Piece) p.piecesPosable.get(j);
				if(current.isPosable(temp.x,temp.y,p)) tabPosable[i][j] = j;
				else tabPosable[i][j] = -1;
			}
		}
		current.tournerUnePiece();
		if (isEmpty(tabPosable)) {
			this.dumpCurrent();
			return true;
		}else {
			int[] ij = choosePos(tabPosable);
			int i = (ij[0]);
			int j = (ij[1]);
			tournePiece(i);
			Piece choisie = (Piece) p.piecesPosable.get(tabPosable[i][j]);
			return p.placePiece(current , choisie.x , choisie.y , this);
		}
	}

	private int[] choosePos(int[][] tab){
		Random rand = new Random();
		int i = (rand.nextInt(4));
		int j = (rand.nextInt(tab[i].length));
		if (tab[i][j] == -1) return choosePos(tab);
		return new int[]{i,j};
	}

	private void tournePiece(int i) {
		while(i!=0) {
			current.tournerUnePiece();
			i--;
		}
	}

	private static boolean isEmpty(int[][] tab) {
		for (int i = 0; i < tab.length; i ++) {
			for (int j = 0; j < tab[i].length; j ++) {
				if (tab[i][j] != -1) return false;
			}
		}
		return true;
	}

}
