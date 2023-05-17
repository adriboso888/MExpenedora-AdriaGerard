import model.Producte;

import java.util.Scanner;

public class Input {
    private static Scanner lector = null;

    public Input(){
        lector = new Scanner(System.in);
    }

    public static Input readProducte() {
        String codiProducte;
        String nomProducte;
        String descripcioProducte;
        float preuCompra;
        float preuVenda;
        lector.nextLine();
        System.out.println("Intordueix el codi del producte");
        codiProducte = lector.nextLine();
        System.out.println("Introdueix el nom del producte");
        nomProducte = lector.nextLine();
        System.out.println("Posa una descripció al producte");
        descripcioProducte = lector.nextLine();
        System.out.println("Digues el preu de compra");
        preuCompra = Float.parseFloat(lector.nextLine());
        System.out.println("Digues el preu de venda");
        preuVenda = Float.parseFloat(lector.nextLine());

        return new Producte(codiProducte, nomProducte,descripcioProducte,preuCompra,preuVenda);

    }
}
