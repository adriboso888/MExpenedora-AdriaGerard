package daos;

import model.Slot;

import java.sql.*;
import java.util.ArrayList;

public class SlotDAO_MySQL implements SlotDAO {
    //Dades de connexió a la base de dades
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_ROUTE = "jdbc:mysql://localhost:3306/expenedora";
    private static final String DB_USER = "root";
    private static final String DB_PWD = "41594459V";

    private Connection conn = null;

    public SlotDAO_MySQL() {

        try {
            Class.forName(DB_DRIVER);
            conn = DriverManager.getConnection(DB_ROUTE, DB_USER, DB_PWD);
            System.out.println("Conexió establerta satisfactoriament");
        } catch (Exception e) {
            System.out.println("S'ha produit un error en intentar connectar amb la base de dades. Revisa els paràmetres");
            System.out.println(e);
        }
    }

    /**
     * Aquesta funció s'encarrega de crear un slot
     *
     * @param s pasarem per parametre un slot
     * @throws SQLException
     */
    @Override
    public void createSlot(Slot s) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO slot VALUES (?,?,?)");
        ps.setInt(1, s.getPosicio());
        ps.setInt(2, s.getQuantitat());
        ps.setString(3, s.getCodiProducte());
        ps.executeUpdate();
    }

    @Override
    public Slot readSlot() throws SQLException {
        return null;
    }


    /**
     * Aquesta funció s'utilitza per guardar tots els slots dintre d'un array, per d'aquesta manera que pugui
     * tenir diferentes funcións com per exemple la de poguer llistar tots els slots amb la seva informació
     *
     * @return retornara l'arraylist amb tots els slots
     * @throws SQLException
     */
    @Override
    public ArrayList<Slot> readSlots() throws SQLException {
        ArrayList<Slot> llistaSlots = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM slot");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Slot s = new Slot();
            s.setPosicio(Integer.parseInt(rs.getString(1)));
            s.setQuantitat(Integer.parseInt(rs.getString(2)));
            s.setCodiProducte(rs.getString(3));


            llistaSlots.add(s);
        }

        return llistaSlots;
    }

    @Override
    public void updateSlots(Slot s) throws SQLException {


    }

    @Override
    public void deleteSlots(Slot s) throws SQLException {

    }

    @Override
    public void deleteSlot(int posicio) throws SQLException {

    }


    /**
     * La funció modificarQuantitat l'utilitzarem per complementar la funció de agafarSlot,
     * serveix per un cop pasat el nombre de slot que volem mirar la quantitat que té, i en cas que sigui
     * superior a 0 l'hi restara 1 del slot
     *
     * @param nom per parametre passarem el numero de slot que volem comprovar
     * @throws SQLException
     */
    @Override
    public float modificarQuantitat(String nom) throws SQLException {
        float benefici = 0;
        PreparedStatement pr = conn.prepareStatement("SELECT quantitat FROM slot, producte WHERE nom = ?");
        pr.setString(1, nom);
        ResultSet rs = pr.executeQuery();
        rs.next();

        String quantitat = rs.getString(1);
        if (!quantitat.equals("0")) {
            pr = conn.prepareStatement("UPDATE slot SET quantitat = quantitat-1  WHERE codi_producte = (SELECT codi_producte FROM producte WHERE nom = ?)");
            pr.setString(1, nom);
            pr.executeUpdate();
        }

        pr = conn.prepareStatement("SELECT preu_venta - preu_copmra as beneficiFinal FROM producte WHERE nom = ?");
        pr.setString(1, nom);
        rs = pr.executeQuery();

        if(rs.next())
        {
            benefici = rs.getFloat("beneficiFinal");
        }


        return benefici;
    }

    /**
     * Aquesta fucnió s'encarrega de modificar el stock d'una taula, per fer-ho pasem com a parametre el stock que volem
     * i la posició on volem que es posi
     *
     * @param stock       passem l'stock
     * @param posicioSlot passem la posició
     * @throws SQLException
     */
    @Override
    public void modificarSlot(int stock, int posicioSlot) throws SQLException {
        PreparedStatement pr = conn.prepareStatement("SELECT quantitat FROM slot WHERE posicio = ?");
        pr.setInt(1, posicioSlot);
        ResultSet rs = pr.executeQuery();
        rs.next();

        pr = conn.prepareStatement("UPDATE slot SET quantitat = ? WHERE posicio = ?");
        pr.setInt(1, stock);
        pr.setInt(2, posicioSlot);
        pr.executeUpdate();
    }

    /**
     * Aquesta funció modifica la posició on es guarda un producte
     * @param posicioActual pasem la posició que volem cambiar
     * @param posicioNova i la nova posició que volem
     * @throws SQLException
     */
    public void modificarPosicio(int posicioActual, int posicioNova) throws SQLException
    {
        PreparedStatement pr = conn.prepareStatement("SELECT posicio FROM slot WHERE posicio = ?");
        pr.setInt(1, posicioActual);
        ResultSet rs = pr.executeQuery();
        rs.next();

        pr = conn.prepareStatement("UPDATE slot SET posicio = ? WHERE posicio = ?");
        pr.setInt(1, posicioNova);
        pr.setInt(2, posicioActual);
        pr.executeUpdate();
    }
}
