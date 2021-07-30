package database;

import models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class DatabaseAccess {
    private Notification createNotification(ResultSet rs) throws SQLException {
        return new Notification(
                rs.getInt("id"), rs.getString("title"), rs.getString("message")
        );
    }
    private void createNotification(String title, String message, String role, int user) {
        try {
            String sql = "insert into notifications(title, message,recipient_role, recipient_id) " +
                    "values (?, ?, ?, ?)";
            Connection con = connect();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, title);
            st.setString(2, message);
            st.setString(3, role);
            st.setInt(4, user);
            st.executeUpdate();
            con.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Connection connect() {
        Connection conn  = null;
        try {
            Class.forName("org.postgresql.Driver");
            String database = "procureem";
            String url = "jdbc:postgresql://localhost:5432/";
            String password = "Abeyabey900!";
            String user = "postgres";
            conn = DriverManager.getConnection(url + database, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return conn;
    }
    private User createUser(ResultSet rs) throws SQLException {
        return  new User(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("role")
        );
    }
    private Supplier createSupplier(ResultSet rs) throws SQLException {
        return  new Supplier(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("role")
        );
    }
    private SupplierQuote create(ResultSet rs) throws SQLException{
        return  new SupplierQuote(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("specification"),
                rs.getInt("quantity"),
                rs.getDouble("max_unit_price"),
                rs.getString("delivery_due"),
                rs.getString("range"),
                rs.getString("support_period"),
                rs.getString("additional_requirements"),
                rs.getInt("buyer_id"),
                rs.getInt("seller"),
                rs.getInt("price")
        );
    }
    private Object createRFQFromResultSet(ResultSet rs, boolean list) throws SQLException {
        if (list) {
            List<Quote> quotes = new ArrayList<>();
            while (rs.next()) {
                quotes.add(
                        new Quote(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("specification"),
                                rs.getInt("quantity"),
                                rs.getDouble("max_unit_price"),
                                rs.getString("delivery_due"),
                                rs.getString("range"),
                                rs.getString("support_period"),
                                rs.getString("additional_requirements"),
                                rs.getInt("buyer_id"),
                                rs.getInt("seller_id")
                        )
                );
            }
            return quotes;
        }else {
            if (rs.next()) {
                return  new Quote(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("specification"),
                        rs.getInt("quantity"),
                        rs.getDouble("max_unit_price"),
                        rs.getString("delivery_due"),
                        rs.getString("range"),
                        rs.getString("support_period"),
                        rs.getString("additional_requirements"),
                        rs.getInt("buyer_id"),
                        rs.getInt("seller_id")
                );
            }
        }
        return null;
    }
    public User userLogin(String username, String password) {
        String sql = "SELECT * FROM users WHERE username= ? AND password = ?";
        User user = null;
        Connection con = connect();
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                user = createUser(rs);
            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }
    public boolean registerUser(String name, String username, String password, String role) {
        try {
            Connection conn = connect();
            String sql = "INSERT INTO users(name, username, password, role) VALUES(?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.setString(4, role);
            stmt.executeUpdate();
            conn.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    public boolean createRFQ(Quote rfq) {
        String sql = "INSERT INTO rfq(name, specification, quantity, " +
                "max_unit_price, delivery_due, range, support_period, " +
                "additional_requirements, buyer_id," +
                "seller_id)" +
                "VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {
            Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, rfq.getName());
            stmt.setString(2, rfq.getSpecification());
            stmt.setInt(3, rfq.getQuantity());
            stmt.setDouble(4, rfq.getMaxUnitPrice());
            stmt.setString(5, rfq.getDeliveryDue());
            stmt.setString(6, rfq.getRange());
            stmt.setString(7, rfq.getSupportPeriod());
            stmt.setString(8, rfq.getAdditionalRequirements());
            stmt.setInt(9, rfq.getBuyerId());
            stmt.setInt(10, rfq.getSellerId());
            stmt.executeUpdate();
            conn.close();
            createNotification("New RFQ Created", "A new RFQ has been created. " +
                    "Please check it and submit your quote.", "S", 0);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    public List<Quote> getAllBuyersQuotes(int buyerId) {
        String sql = "SELECT * FROM rfq WHERE buyer_id = ? and seller_id = 0";
        try {
            Connection  conn = connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, buyerId);
            ResultSet rs = stmt.executeQuery();

            return (List<Quote>) createRFQFromResultSet(rs, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }
    public boolean updateRFQ(Quote rfq) {
        try {
            String sql = "update rfq set name=?, specification=?, quantity=?," +
                    "max_unit_price=?, delivery_due=?, range=?, support_period=?, additional_requirements=?," +
                    "buyer_id=?, seller_id=? where id=?";
           Connection conn = connect();
           PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, rfq.getName());
            stmt.setString(2, rfq.getSpecification());
            stmt.setInt(3, rfq.getQuantity());
            stmt.setDouble(4, rfq.getMaxUnitPrice());
            stmt.setString(5, rfq.getDeliveryDue());
            stmt.setString(6, rfq.getRange());
            stmt.setString(7, rfq.getSupportPeriod());
            stmt.setString(8, rfq.getAdditionalRequirements());
            stmt.setInt(9, rfq.getBuyerId());
            stmt.setInt(10, rfq.getSellerId());
            stmt.setInt(11, rfq.getId());
            stmt.executeUpdate();
            conn.close();
            createNotification("RFQ-"+rfq.getId()+" updated", "The  RFQ has been edited. " +
                    "Please check it and update your quote appropriately.", "S", rfq.getSellerId());
            return true;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return false;
    }
    public List<Quote> getAllQuotes() {
        try {
            String sql = "SELECT * FROM rfq WHERE seller_id=0 OR seller_id = NULL";
            Connection connection = connect();
            PreparedStatement stmt = connection.prepareStatement(sql);
            return (List<Quote>) createRFQFromResultSet(stmt.executeQuery(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    public List<Supplier> allSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        try {
            String sql = "SELECT * FROM users WHERE role = ?";
            Connection con = connect();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, "Seller");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                suppliers.add(createSupplier(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suppliers;
    }
    public int getSupplierTotalOrders() {
        return 0;
    }
    public int getSupplierTotalQuotations() {
        return 0;
    }
    public boolean submitQuote(Quote rfq, int seller, double price) {
        try {
            String sql = "insert into supplier_rfq(rfq_id, seller_id, price) values(?,?,?)";
            Connection con = connect();
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, rfq.getId());
            st.setInt(2, seller);
            st.setDouble(3, price);
            st.executeUpdate();
            con.close();
            createNotification("New submission for your RFQ-"+rfq.getId(), "A seller has show interest in your rfq. " +
                    "Please check please checkout the submission.", "B", rfq.getBuyerId());
            return true;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<SupplierQuote> getSupplierQuotations(int supplier) {
        List<SupplierQuote> quotes = new ArrayList<>();
        try {
            String sql = "select rfq.*, supplier_rfq.price, supplier_rfq.seller_id as seller from rfq inner join supplier_rfq on rfq.id = supplier_rfq.rfq_id" +
                    " where supplier_rfq.seller_id = ? and rfq.seller_id = 0";
            Connection con = connect();
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, supplier);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                quotes.add(create(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return quotes;
    }
    public List<Notification> getNotifications(int userId, String role) {
        List<Notification> notifications =  new ArrayList<>();

        try {
            String sql = "select * from notifications where recipient_id = "+userId +" order by id";
            Connection con = connect();
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                notifications.add(createNotification(rs));
            }
            if (role.equals("S")) {
                sql = "select * from notifications where recipient_role = ? and recipient_id = 0 order by id";
                st = con.prepareStatement(sql);
                st.setString(1, role);
                rs = st.executeQuery();
                while (rs.next()) {
                    notifications.add(createNotification(rs));
                }
            }
            con.close();
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        notifications.sort(Comparator.comparing(Notification::getId));
        return notifications;
    }
    public List<SupplierQuote> getOrderQuotes(int rfq) {
        List<SupplierQuote> quotes = new ArrayList<>();
        try {
            String sql = "select rfq.*, supplier_rfq.price, supplier_rfq.seller_id as seller from rfq inner join supplier_rfq on rfq.id = supplier_rfq.rfq_id" +
                    " where rfq.id = ?";
            Connection con = connect();
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, rfq);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                quotes.add(create(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return quotes;
    }
    public void confirmSupplierQuotation(SupplierQuote selected) {
        try {
            String sql = "update rfq set  actual_unit_price = ? , seller_id =? " +
                    "where id = ?";
            Connection con = connect();
            PreparedStatement st = con.prepareStatement(sql);
            st.setDouble(1, selected.getSupplierPrice());
            st.setInt(2, selected.getSellerId());
            st.setInt(3, selected.getId());
            st.executeUpdate();
            con.close();
            createNotification("RFQ-"+selected.getId()+" Assigned", "Your quotation has won the RFQ. " +
                    "Please check it and confirm that you are working on delivery", "S", selected.getSellerId());
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private PostOrder createPostOrder(ResultSet rs) throws SQLException {
        return new PostOrder(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("specification"),
                rs.getInt("quantity"),
                rs.getDouble("actual_unit_price"),
                rs.getString("delivery_due"),
                rs.getString("support_period"),
                rs.getInt("buyer_id"),
                rs.getInt("seller_id")
        );
    }
    public List<PostOrder> getUserPostOrders(int id, boolean isBuyer) {
        List<PostOrder> postOrders = new ArrayList<>();
        try {
            String sql;
            if (isBuyer) {
                sql = "select * from rfq where buyer_id = ? and not seller_id = 0";
            }else {
                sql = "select * from rfq where seller_id = ?";
            }
            Connection connection = connect();
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet set = st.executeQuery();
            while (set.next()) {
                postOrders.add(createPostOrder(set));
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return postOrders;
    }

}
