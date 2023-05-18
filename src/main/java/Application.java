import daos.*;
import model.Producte;
import model.Slot;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLRecoverableException;
import java.util.ArrayList;
import java.util.Scanner;

public class Application {

    static Input in = new Input();

    static DAOFactory df = DAOFactory.getInstance();
    private static ProducteDAO producteDAO = new ProducteDAO_MySQL();
    private static SlotDAO slotDAO = new SlotDAO_MySQL();

    public static void main(String[] args) throws SQLException {

        int opcio;

        do {
            mostrarMenu();
            opcio = Integer.parseInt(in.readLine());

            switch (opcio) {
                case 1 -> mostrarMaquina();
                case 2 -> comprarProducte();
                case 10 -> mostrarInventari();
                case 11 -> afegirProductes();
                case 12 -> modificarMaquina();
                case 13 -> mostrarBenefici();
                case -1 -> System.out.println("Bye...");
                default -> System.out.println("Opció no vàlida");
            }

        } while (opcio != -1);

    }


    /**
     * Permet modificar diferents coses de la màquina com la posicio, quantitat o el producte
     * @throws SQLException
     */
    private static void modificarMaquina() throws SQLException {
        in.modificarMaquina();
    }

    /**
     * Crearem un producte nou passant-ho per parametres, demanant a l'usuari que introdueixi totes les dades
     *
     * @throws SQLException
     */
    private static void afegirProductes() throws SQLException {

        Producte p = in.addProducte();

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
                modificarProducte(p);
            } else {
                e.printStackTrace();
            }
        }

    }

    /**
     * Metode que s'utilitza en cas de que volguem introduir un producte amb un codi que ja existeix
     * s'utilitzara per demanar a l'usuari que vol fer, en cas de que vulgui cambiar el codi del producte
     * el modificara pel nou que s'he l'hi demanara
     * @param p passarem per parametre el producte que volem modificar
     * @throws SQLException
     */
    private static void modificarProducte(Producte p) throws SQLException {
        System.out.println("El producte que estas intentant entrar ja existeix");
        System.out.println("Que vols fer ara?" +
                "\n1- Cambiar Codi" +
                "\n2- Sortir");
        int opcio = Integer.parseInt(in.readLine());
        switch (opcio){
            case 1: {
                String nouCodi;
                System.out.println("Digues el nou codi");
                nouCodi = in.readLine();
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

        } catch (SQLRecoverableException e) {
            System.out.println("s'ha perdut la connexió amb la base de dades");

        }catch (SQLException ex)
        {
            System.out.println("Hi ha hagut un error inesperat");
        }
    }

    private static void comprarProducte() throws SQLException {

        mostrarMaquina();
        in.agafarProducte();

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

    /**
     * Métode que mostra el menú
     */
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
