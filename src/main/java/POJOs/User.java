package POJOs;

public class User {
    private int id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;

    public User() {
        id = 2;
        email = "janet.weaver@reqres.in";
        first_name = "Janet";
        last_name = "Weaver";
        avatar = "https://reqres.in/img/faces/2-image.jpg";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
