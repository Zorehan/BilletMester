package GUI.Model;

import BE.Users.User;
import BE.Users.UserEnum;
import BLL.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserModel {
    private static UserModel instance;

    private UserManager userManager;

    private ObservableList<User> allUsers;

    private User user;

    public UserModel() {
        userManager = new UserManager();

        allUsers = FXCollections.observableArrayList();
        allUsers.addAll(userManager.getAllUsers());
    }

    public static UserModel getInstance()
    {
        if(instance == null)
        {
            instance = new UserModel();
        }
        return instance;
    }

    public ObservableList<User> getObservableUsers()
    {
        return allUsers;
    }

    public void clearObservableUsers()
    {
        allUsers.clear();
    }

    public User createUser(User NewUser)
    {
        User user = userManager.createUser(NewUser);
        allUsers.add(user);
        return user;
    }

    public void deleteUser(User user)
    {
        userManager.deleteUsers(user);
        allUsers.remove(user);
    }

    public void updateUser(User user, UserEnum newRole)
    {
        userManager.updateUser(user, newRole);
    }
}
