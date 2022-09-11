package api.model;

public class UserModel {
    private String email;
    private String name;

    public UserModel(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public UserModel() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
