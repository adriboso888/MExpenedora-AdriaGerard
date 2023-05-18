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

    public static  SlotDAO_MySQL slot = new SlotDAO_MySQL();
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
     * Aquest métode s'utilitza per fer la compra del producte a traves del nom.
     * Demanant quin es el nom que vol i cridant la funció modificarQuantitat, integrada en el SlotDAO_MySQL
     * @throws SQLException
     */
    public void agafarProducte() throws SQLException
    {
        System.out.println("Digues el nom del producte que vols comprar");
        String nom = lector.nextLine();
        slot.modificarQuantitat(nom);
    }

    /**
     * Menú on dona diferentes opcións sobre modificacións que es poden fer a la màquina
     *
     * @throws SQLException
     */
    public void modificarMaquina() throws SQLException
    {
        System.out.println("Que vols fer?");
        System.out.println("1- Modificar posició");
        System.out.println("2- Modificar stock");
        System.out.println("3- Afegir slots");
        System.out.println("0- Sortir");
        int opcio = Integer.parseInt(lector.nextLine());


        switch (opcio) {
            case 1 -> {
                modificarPosicio();
                System.out.println("La posició s'ha modificat correctament");
            }
            case 2 -> {
                modificarStock();
                System.out.println("El stock s'ha modificat correctament");
            }
            case 3 -> {
                crearSlot();
                System.out.println("S'ha creat el slot satisfactoriament");
            }
            case 0 -> {
            }
        }
    }

    /**
     * Aquesta funció s'encarrega de demanar les dades necesaries per crear un slot,
     * un cop demanades les dades es pasara a la funcio createSlot del SlotDAO
     * @throws SQLException
     */
    public void crearSlot() throws SQLException {

        System.out.println("Digues el numero de posició");
        int posicio = Integer.parseInt(lector.nextLine());
        System.out.println("Digues la quantitat del producte");
        int quantitat = Integer.parseInt(lector.nextLine());
        System.out.println("A quin producte vols fer referencia amb aquest slot?");
        String codiProducte = lector.nextLine();
        Slot s = new Slot(posicio, quantitat,codiProducte);
        slot.createSlot(s);
    }

    /**
     * Aquesta funcio s'utilitza per a modificar el stock d'un slot, demanant la posició del slot i el nou stock que es vol
     * un cop demanades les dades es pasara per parametre a SlotDAO
     * @throws SQLException
     */
    public void modificarStock() throws SQLException {
        System.out.println("Digues el nou stock que vols posar");
        int stock = Integer.parseInt(lector.nextLine());
        System.out.println("A quina posició el vols posar?");
        int posicio = Integer.parseInt(lector.nextLine());
        slot.modificarStock(stock, posicio);
    }

    /**
     * Aquesta funció s'encarrega de modificar la posició d'un producte
     * @throws SQLException
     */
    public void modificarPosicio() throws SQLException {
        System.out.println("Digues la posició que vols modificar");
        int posicioActual = Integer.parseInt(lector.nextLine());
        System.out.println("Digues la nova posició que vols");
        int posicioNova = Integer.parseInt(lector.nextLine());
        slot.modificarPosicio(posicioActual, posicioNova);
    }

    /**
     * Aquesta funció s'utilitza pel lector
     * @return retornara el lector
     */
    public String readLine() {
        return lector.nextLine();
    }
}
