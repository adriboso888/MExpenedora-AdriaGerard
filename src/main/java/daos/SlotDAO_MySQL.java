package daos;

import model.Producte;
import model.Slot;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void createSlot(Slot s) throws SQLException {

    }

    @Override
    public Slot readSlot() throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Slot> readSlots() throws SQLException {
        ArrayList<Slot> llistaSlots = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM slot");
        ResultSet rs = ps.executeQuery();

        while(rs.next())
        {
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
}
