package Model;

import Controleur.Controleur;

public class OrdiPro extends Ordi{

	public OrdiPro(String nom, boolean isDomino) {
		super(nom, isDomino);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
		int[][] tab = new int[4][tabPosable[0].length];
		int i = 0, j = 0;
		for (int tempi = 0; tempi < 4; tempi++) {
			if (tempi>0) current.tournerUnePiece();
			for (int tempj = 0; tempj<tabPosable[tempi].length; tempj++) {
				if (tabPosable[tempi][tempj] != -1){
					Piece pie = (Piece) p.piecesPosable.get(tabPosable[tempi][tempj]);
					tab[tempi][tempj] = pie.comptePoint(this, p.get(pie.x, pie.y+1), p.get(pie.x+1, pie.y), p.get(pie.x, pie.y-1), p.get(pie.x-1, pie.y));
				}
			}
		}
		current.tournerUnePiece();
		i = max2(tab);
		j = max(tab[i]);
		tournePiece(i);
		Piece choisie = (Piece) p.piecesPosable.get(i);
		return p.placePiece(current , choisie.x , choisie.y , this);
		}
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
	
	private static int max2(int[][] tab) {
		int index = 0;
		int max = 0;
		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[i].length; j++) {
				if (tab[i][j] > max) {
					index = i; 
					max = tab[i][j];
				}
			}
		}
		return index;
	}
	
	private static int max(int[] tab) {
		int index = 0;
		for (int i = 0; i < tab.length; i++) {
			if (tab[i] > tab[index]) index = i; 
		}
		return index;
	}

}
