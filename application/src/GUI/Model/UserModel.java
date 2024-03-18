package GUI.Model;

import BE.Users;
import BLL.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserModel {
    private UserModel instance;

    private UserManager userManager;

    private ObservableList<Users> allUsers;

    private Users user;

    public UserModel() {
        userManager = new UserManager();

        allUsers = FXCollections.observableArrayList();
        allUsers.addAll(userManager.getAllUsers());
    }

    public UserModel getInstance()
    {
        if(instance == null)
        {
            instance = new UserModel();
        }
        return instance;
    }

    public ObservableList<Users> getObservableUsers()
    {
        return allUsers;
    }

    public void clearObservableUsers()
    {
        allUsers.clear();
    }

    public Users createUser(Users NewUser)
    {
        Users user = userManager.createUser(NewUser);
        allUsers.add(user);
        return user;
    }

    public void deleteUser(Users user)
    {
        userManager.deleteUsers(user);
        allUsers.remove(user);
    }

    public void updateUser(Users user)
    {
        userManager.updateUser(user);
    }
}
