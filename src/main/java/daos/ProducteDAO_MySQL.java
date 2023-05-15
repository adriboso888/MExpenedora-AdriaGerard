package daos;

import model.Producte;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProducteDAO_MySQL implements ProducteDAO {

    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_ROUTE = "jdbc:mysql://localhost:3306/expenedora";
    private static final String DB_USER = "root";
    private static final String DB_PWD = "41594459V";

    private Connection conn = null;

    public ProducteDAO_MySQL()
    {
        try {
            Class.forName(DB_DRIVER);
            conn = DriverManager.getConnection(DB_ROUTE, DB_USER, DB_PWD);
            System.out.println("Conexió oberta");
        } catch (Exception e)
        {
            System.out.println("S'ha produit un error en intentar connectar amb la base de dades. Revista els paramentres");
            System.out.println(e);
        }
    }

    @Override
    public void createProducte(Producte p) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO producte VALUES(?,?,?,?,?)");
        ps.setString(1, p.getCodiProducte());
        ps.setString(2, p.getNom());
        ps.setString(3, p.getDescripcio());
        ps.setFloat(4, p.getPreuCompra());
        ps.setFloat(5, p.getPreuVenta());

        int rowCount = ps.executeUpdate();

    }

    @Override
    public Producte readProducte() throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Producte> readProductes() throws SQLException {
        ArrayList<Producte> llistaProductes = new ArrayList<Producte>();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM producte");

        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
            Producte p = new Producte();
            p.setCodiProducte(rs.getString(1));
            p.setNom(rs.getString(2));
            p.setDescripcio(rs.getString(3));
            p.setPreuCompra(Float.parseFloat(rs.getString(4)));
            p.setPreuVenta(Float.parseFloat(rs.getString(5)));
            llistaProductes.add(p);
        }
        return llistaProductes;
    }

    @Override
    public boolean updateProducte(Producte p) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteProducte(Producte p) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteProducte(String codiProducte) throws SQLException {
        return false;
    }
}
