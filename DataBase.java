package banking;

import java.sql.*;

import static banking.Main.*;

public class DataBase {
    static Connection con = null;
    static PreparedStatement ps = null;
    static ResultSet rs = null;
    private static String url;
    public static void createNewDataBase(String filename) {
        url = "jdbc:sqlite:" + filename;
        try {
            con = DriverManager.getConnection(url);
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                        "id INTEGER PRIMARY KEY," +
                        "number TEXT NOT NULL," +
                        "pin TEXT NOT NULL," +
                        "balance INTEGER DEFAULT 0)");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insert(Main account) {
        String sql = "INSERT INTO card(number, pin) VALUES (?,?)";
        try {
            con = DriverManager.getConnection(url);
            try {
                ps = con.prepareStatement(sql);
                ps.setString(1, account.getNumber());
                ps.setString(2, account.getPin());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void insertBalance(Main account, int nAdd) {
        String sql = "UPDATE card SET balance = balance + ? WHERE number = ?";
        try  {
            con = DriverManager.getConnection(url);
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.setInt(1, nAdd);
                preparedStatement.setString(2, inputNumber);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        public static Main getAccount(String inputNumber, String inputPin) {
        String sql = "SELECT number, pin, balance FROM card WHERE number = ? AND pin = ?";

        try {
            con = DriverManager.getConnection(url);
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.setString(1, inputNumber);
                preparedStatement.setString(2, inputPin);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return new Main(
                            resultSet.getString("number"),
                            resultSet.getString("pin"),
                            resultSet.getInt("balance"));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean getCardNumber(String cNumber) {
        String sql = "SELECT number, pin, balance FROM card WHERE number = ?";

        try {
            con = DriverManager.getConnection(url);
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.setString(1, cNumber);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return true;
                } else {
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public static int returnBalance() {
        String sql = "SELECT balance, number, pin FROM card WHERE number = ?";
        try {
            con = DriverManager.getConnection(url);
            try {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, inputNumber);
                rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    return rs.getInt("balance");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }
    public static void balanceNow(int transferBalance) {
        String sql = "UPDATE card SET balance = balance - ? WHERE number = ?";
        try {
            con = DriverManager.getConnection(url);
            try {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setInt(1, transferBalance);
                preparedStatement.setString(2, inputNumber);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void balanceAnother(int transferBalance) {
        String sql = "UPDATE card SET balance = balance + ? WHERE number = ?";
        try {
            con = DriverManager.getConnection(url);
            try {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setInt(1, transferBalance);
                preparedStatement.setString(2, cNumber);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deleteAccount() {
        String sql = "DELETE FROM card WHERE number = ?";
        try {
            con = DriverManager.getConnection(url);
            try {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, inputNumber);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("The account has been closed!");
        stop2 = false;
    }
}

