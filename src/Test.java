import database.DatabaseAccess;

public class Test {
    public static void main(String[] args) {
        DatabaseAccess da = new DatabaseAccess();
        da.registerUser("Super Buyer", "buyer123", "pass", "buyer");
        System.out.println(da.userLogin("buyer123", "pass"));
        System.out.println(da.getSupplierQuotations(3).size());

        System.out.println(da.getNotifications(6, "B").size());
    }
}
