package BE.Users;

public class User {
    private int id;
    private UserEnum userType;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String userEmail;

    public User(int id, UserEnum userType, String firstName, String lastName, String userName, String password, String userEmail) {
        this.id = id;
        this.userType = userType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.userEmail = userEmail;

    }

    public int getId() {
        return id;
    }

    public UserEnum getUserType() {
        return userType;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}
