package daos;

public class DAOFactory {
    private static DAOFactory instance;
    private DAOFactory() {
        // init ConnectionFactory
    }
    public static DAOFactory getInstance() {
        if (instance == null)
            instance = new DAOFactory();
        return instance;
    }
    public CustomerDAO getCustomerDAO() {
        // implementar-ho
    }
    public AccountDAO getAccountDAO() {
        // implementar-ho
    }
    public OrderDAO getOrderDAO() {
        // implementar-ho
    }
}