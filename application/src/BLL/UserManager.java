package BLL;

import BE.Users.User;
import BE.Users.UserEnum;
import DAL.UserDAO;

import java.util.List;

public class UserManager {

    private UserDAO userDAO;

    public UserManager()
    {
        userDAO = new UserDAO();
    }

    public List<User> getAllUsers()
    {
        return userDAO.getAllUsers();
    }

    public User createUser(User user)
    {
        return userDAO.createUser(user);
    }

    public void updateUser(User user, UserEnum newRole)
    {
        userDAO.updateUser(user, newRole);
    }

    public void deleteUsers(User user)
    {
        userDAO.deleteUser(user);
    }

    public void deepUpdateUser(User user, String firstName, String lastName, String password, String email) {
        userDAO.deepUpdateUser(user, firstName, lastName, password, email);
    }
}
