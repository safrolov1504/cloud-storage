package workWithSQL;

public class User {
    private String login;
    private String password;
    private String firstName;
    private String secondName;
    private String role;
    private String profession;


    public User(String login, String password, String firstName, String secondName, String role, String profession) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.role = role;
        this.profession = profession;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getRole() {
        return role;
    }

    public String getProfession() {
        return profession;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", role='" + role + '\'' +
                ", profession='" + profession + '\'' +
                '}';
    }
}
