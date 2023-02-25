import Controleur.Controleur;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Lancer le programme avec un des argument suivant : texte ou graphique");
            System.out.println("ou écrivez votre mode");
            Scanner sc = new Scanner(System.in);
            String rep = sc.nextLine();
            if (!rep.equals("")) {
                args = new String[]{rep};
            }
        }
        if (args.length == 1){
            if (args[0].equals("terminal")){
                Controleur<Integer> controleur = new Controleur<Integer>();
                controleur.launch(true);
            } else if (args[0].equals("graphique")) {
                Controleur controleur = new Controleur<>();
                controleur.launch( false);
            }else{
                System.out.println("Lancer le programme avec un des argument suivant : terminal ou graphique");
            }
        }
    }
}