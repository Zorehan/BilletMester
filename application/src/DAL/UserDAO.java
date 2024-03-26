package DAL;

import BE.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private DatabaseConnector databaseConnector;

    public UserDAO()
    {
        databaseConnector = new DatabaseConnector();
    }

    public List<Users> getAllUsers()
    {
        ArrayList<Users> allUsers = new ArrayList<>();
        try(Connection conn = databaseConnector.getConnection();
            Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM dbo.Users;";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next())
            {
                int id = rs.getInt("id");
                String userType = rs.getString("userType");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String userEmail = rs.getString("userEmail");

                Users user = new Users(id,userType,firstName,lastName,username,password,userEmail);
                allUsers.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allUsers;
    }

    public Users createUser(Users user)
    {
        String sql = "INSERT INTO dbo.Users (userType, firstName, lastName, username, password, userEmail) VALUES(?,?,?,?,?,?);";
        try(Connection conn = databaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setString(1, user.getUserType());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getUserName());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getUserEmail());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if(rs.next())
            {
                id = rs.getInt(1);
            }

            Users createdUser = new Users(id, user.getUserType(), user.getFirstName(), user.getLastName(), user.getUserName(), user.getPassword(), user.getUserEmail());
            return createdUser;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUser(Users user, String newRole) {
        String sql = "UPDATE dbo.Users SET userType = ?, firstName = ?, lastName = ?, username = ?, password = ?, userEmail = ? WHERE id = ?;";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newRole); // Update userType with the new role
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getUserName());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getUserEmail());
            stmt.setInt(7, user.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteUser(Users user)
    {
        String sql = "DELETE FROM dbo.Users WHERE id = ?;";
        try(Connection conn = databaseConnector.getConnection())
        {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, user.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
