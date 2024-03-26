package BLL;

import BE.Users;
import DAL.UserDAO;

import java.util.List;

public class UserManager {

    private UserDAO userDAO;

    public UserManager()
    {
        userDAO = new UserDAO();
    }

    public List<Users> getAllUsers()
    {
        return userDAO.getAllUsers();
    }

    public Users createUser(Users user)
    {
        return userDAO.createUser(user);
    }

    public void updateUser(Users user, String newRole)
    {
        userDAO.updateUser(user, newRole);
    }

    public void deleteUsers(Users user)
    {
        userDAO.deleteUser(user);
    }
}
