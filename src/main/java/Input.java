import daos.ProducteDAO_MySQL;
import daos.SlotDAO;
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

    /**
     * Aquest métode s'utilitza per fer la compra del producte a traves del nombre de slot.
     * Demanant quin es el slot que vol i cridant la funció modificarQuantitat, integrada en el SlotDAO_MySQL
     * @throws SQLException
     */
    public void agafarSlot() throws SQLException {
        System.out.println("Digues el numero de slot que vols comprar el producte");
        int numSlot = Integer.parseInt(lector.nextLine());
        SlotDAO_MySQL s = new SlotDAO_MySQL();
        s.modificarQuantitat(numSlot);
    }

    public void modificarMaquina() throws SQLException
    {
        System.out.println("Que vols fer?");
        System.out.println("1- Modificar posició");
        System.out.println("2- Modificar stock");
        System.out.println("3- Afegir slots");
        int opcio = Integer.parseInt(lector.nextLine());

        ProducteDAO_MySQL p = new ProducteDAO_MySQL();
        switch (opcio){
            case 1: {

                break;
            }
            case 2: {
                break;
            }
            case 3: {
                crearSlot();
                break;
            }
        }
    }

    public void crearSlot() throws SQLException {

        SlotDAO_MySQL slot = new SlotDAO_MySQL();

        System.out.println("Digues el numero de posició");
        int posicio = Integer.parseInt(lector.nextLine());
        System.out.println("Digues la quantitat del producte");
        int quantitat = Integer.parseInt(lector.nextLine());
        System.out.println("A quin producte vols fer referencia amb aquest slot?");
        String codiProducte = lector.nextLine();
        Slot s = new Slot(posicio, quantitat,codiProducte);
        slot.createSlot(s);
    }

    public String readLine() {
        return lector.nextLine();
    }
}
