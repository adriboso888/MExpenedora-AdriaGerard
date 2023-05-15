package daos;
import model.Producte;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ProducteDAO {

    //CRUD

    public void createProducte(Producte p) throws SQLException;
    public Producte readProducte() throws SQLException;
    public ArrayList<Producte> readProductes()throws SQLException;
    public boolean updateProducte(Producte p)throws SQLException;
    public boolean deleteProducte(Producte p)throws SQLException;
    public boolean deleteProducte(String codiProducte)throws SQLException;

}
