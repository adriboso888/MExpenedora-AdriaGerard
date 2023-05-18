import daos.ProducteDAO_MySQL;
import daos.SlotDAO_MySQL;
import model.Producte;
import model.Slot;

import java.sql.SQLException;
import java.util.Scanner;

public class Input {
    private static Scanner lector = null;
    private static int benefici;

    public Input(){
        lector = new Scanner(System.in);
    }

    /**
     * Fem la creació del producte dintre d'aquest métode
     * @return retorna un nou producte
     */
    public static Producte addProducte() {
        String codiProducte;
        String nomProducte;
        String descripcioProducte;
        float preuCompra;
        float preuVenda;
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

    public void agafarSlot() throws SQLException {
        System.out.println("Digues el numero de slot que vols comprar");
        int numSlot = Integer.parseInt(lector.nextLine());
        SlotDAO_MySQL s = new SlotDAO_MySQL();
        s.modificarQuantitat(numSlot);
    }

    public String readLine() {
        return lector.nextLine();
    }
}
