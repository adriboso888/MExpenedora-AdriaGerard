import daos.DAOFactory;
import daos.ProducteDAO;
import daos.ProducteDAO_MySQL;
import daos.SlotDAO_MySQL;
import model.Producte;
import model.Slot;

import java.sql.Array;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Scanner;

public class Application {

    //Passar al DAO -->     //TODO: llegir les propietats de la BD d'un fitxer de configuració (Properties)
    //En general -->        //TODO: Afegir un sistema de Logging per les classes.

    private static DAOFactory df = DAOFactory.getInstance();
    private static ProducteDAO producteDAO = new ProducteDAO_MySQL();            //TODO: passar a una classe DAOFactory
    private static SlotDAO_MySQL slotDAO = new SlotDAO_MySQL();

    public static void main(String[] args) throws SQLException {

        Scanner lector = new Scanner(System.in);            //TODO: passar Scanner a una classe InputHelper
        int opcio = 0;

        do {
            mostrarMenu();
            opcio = lector.nextInt();

            switch (opcio) {
                case 1 -> mostrarMaquina();
                case 2 -> comprarProducte();
                case 10 -> mostrarInventari();
                case 11 -> afegirProductes(lector);
                case 12 -> modificarMaquina();
                case 13 -> mostrarBenefici();
                case -1 -> System.out.println("Bye...");
                default -> System.out.println("Opció no vàlida");
            }

        } while (opcio != -1);

    }


    private static void modificarMaquina() {

        /**
         * Ha de permetre:
         *      - modificar les posicions on hi ha els productes de la màquina (quin article va a cada lloc)
         *      - modificar stock d'un producte que hi ha a la màquina
         *      - afegir més ranures a la màquina
         */

    }

    /**
     * Crearem un producte nou passant-ho per parametres, demanant a l'usuari que introdueixi totes les dades
     *
     * @param lector
     * @throws SQLException
     */
    private static void afegirProductes(Scanner lector) throws SQLException {

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

        Producte p = new Producte(codiProducte, nomProducte, descripcioProducte, preuCompra, preuVenda);

        try {

            //Demanem de guardar el producte p a la BD
            producteDAO.createProducte(p);

            //Agafem tots els productes de la BD i els mostrem (per compvoar que s'ha afegit)
            ArrayList<Producte> productes = producteDAO.readProductes();
            for (Producte prod : productes) {
                System.out.println(prod);
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { //excepcio en cas de que la clau primaria estigui repetida
                modificarProducte(lector, p);
            } else {
                e.printStackTrace();
            }
        }

    }

    /**
     * Metode que s'utilitza en cas de que volguem introduir un producte amb un codi que ja existeix
     * s'utilitzara per demanar a l'usuari que vol fer, en cas de que vulgui cambiar el codi del producte
     * el modificara pel nou que s'he l'hi demanara
     * @param lector
     * @param p passarem per parametre el producte que volem modificar
     * @throws SQLException
     */
    private static void modificarProducte(Scanner lector, Producte p) throws SQLException {
        System.out.println("El producte que estas intentant entrar ja existeix");
        System.out.println("Que vols fer ara?" +
                "\n1- Cambiar Codi" +
                "\n2- Sortir");
        int opcio = Integer.parseInt(lector.nextLine());
        switch (opcio){
            case 1: {
                String nouCodi;
                System.out.println("Digues el nou codi");
                nouCodi = lector.nextLine();
                p.setCodiProducte(nouCodi);
                producteDAO.createProducte(p);
            } case 2: {
                break;
            }
        }
    }


    /**
     * L'utilitzem per mostrar tot l'inventari de la màquina, mostran tots els productes que té
     */
    private static void mostrarInventari() {

        try {
            //Agafem tots els productes de la BD i els mostrem
            ArrayList<Producte> productes = producteDAO.readProductes();
            for (Producte prod : productes) {
                System.out.println(prod);
            }

        } catch (SQLException e) {          //TODO: tractar les excepcions
            e.printStackTrace();
        }
    }

    private static void comprarProducte() {

        /**
         * Mínim: es realitza la compra indicant la posició on es troba el producte que es vol comprar
         * Ampliació (0.5 punts): es permet entrar el NOM del producte per seleccionar-lo (abans cal mostrar els
         * productes disponibles a la màquina)
         *
         * Tingueu en compte que quan s'ha venut un producte HA DE QUEDAR REFLECTIT a la BD que n'hi ha un menys.
         * (stock de la màquina es manté guardat entre reinicis del programa)
         */


    }

    /**
     * S'encarrega de mostrar la posició, quantitat i nom del producte
     *
     * @throws SQLException
     */
    private static void mostrarMaquina() throws SQLException {

        ArrayList<Slot> llistaSlots = slotDAO.readSlots();
        ArrayList<Producte> llistaProducte = producteDAO.readProductes();
        mostrarProductes(llistaSlots, llistaProducte);
    }

    /**
     * Fa el bucle buscant totes les dades
     *
     * @param llistaSlots    pasa la llista de slots
     * @param llistaProducte pasa la llista productes
     * @throws SQLException
     */
    private static void mostrarProductes(ArrayList<Slot> llistaSlots, ArrayList<Producte> llistaProducte) throws SQLException {
        for (Slot s : llistaSlots) {
            System.out.println("Posició: " + s.getPosicio());
            System.out.println("Quantitat: " + s.getQuantitat());
            for (Producte p : llistaProducte) {
                if (s.getCodiProducte().equals(p.getCodiProducte())) {
                    System.out.println("Nom: " + p.getNom());
                }
            }
            System.out.println("==============================");
        }
    }

    private static void mostrarMenu() {
        System.out.println("\nMenú de la màquina expenedora");
        System.out.println("=============================");
        System.out.println("Selecciona la operació a realitzar introduïnt el número corresponent: \n");


        //Opcions per client / usuari
        System.out.println("[1] Mostrar Posició / Nom producte / Stock de la màquina");
        System.out.println("[2] Comprar un producte");

        //Opcions per administrador / manteniment
        System.out.println();
        System.out.println("[10] Mostrar llistat productes disponibles (BD)");
        System.out.println("[11] Afegir productes disponibles");
        System.out.println("[12] Assignar productes / stock a la màquina");
        System.out.println("[13] Mostrar benefici");

        System.out.println();
        System.out.println("[-1] Sortir de l'aplicació");
    }

    private static void mostrarBenefici() {

        /** Ha de mostrar el benefici de la sessió actual de la màquina, cada producte té un cost de compra
         * i un preu de venda. La suma d'aquesta diferència de tots productes que s'han venut ens donaran el benefici.
         *
         * Simplement s'ha de donar el benefici actual des de l'últim cop que s'ha engegat la màquina. (es pot fer
         * amb un comptador de benefici que s'incrementa per cada venda que es fa)
         */

        /** AMPLIACIÓ **
         * En entrar en aquest menú ha de permetre escollir entre dues opcions: veure el benefici de la sessió actual o
         * tot el registre de la màquina.
         *
         * S'ha de crear una nova taula a la BD on es vagi realitzant un registre de les vendes o els beneficis al
         * llarg de la vida de la màquina.
         */
    }
}
