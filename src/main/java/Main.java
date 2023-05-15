import daos.ProducteDAO;
import daos.ProducteDAO_MySQL;
import model.Producte;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        ProducteDAO pd = new ProducteDAO_MySQL();
        ArrayList<Producte> llistaProductes = null;
        try {
            llistaProductes = (ArrayList<Producte>) pd.readProductes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        llistaProductes.forEach(p -> System.out.println(p));

        Producte p = new Producte("poma1", "Poma", "Poma Bona", 0.8f, 2);

        try {
            pd.createProducte(p);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}