import java.sql.*;

public class Application {

    public static void main(String[] args) {

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/skypro", "postgres", "123");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employee WHERE id = 1")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(
                        "id: " + resultSet.getInt("id")
                        + " name: " + resultSet.getString("first_name")
                        + " last name: " + resultSet.getString("last_name")
                        + " gender: " + resultSet.getString("gender")
                        + " age: " + resultSet.getInt("age")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
