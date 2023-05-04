package POJOs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @JsonProperty(value = "id")
    private int id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty(value = "last_name")
    private String lastName;

    @JsonProperty(value = "avatar")
    private String avatar;

    public User() {
        id = 2;
        email = "janet.weaver@reqres.in";
        firstName = "Janet";
        lastName = "Weaver";
        avatar = "https://reqres.in/img/faces/2-image.jpg";

    }

    public User(int id, String first_name, String last_name, String email, String avatar) {
        super();
        this.id = id;
        this.firstName = first_name;
        this.lastName = last_name;
        this.email = email;
        this.avatar = avatar;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return String.format("{id=%d, email=%s, first_name=%s, last_name=%s, avatar=%s}", id, email, firstName, lastName, avatar);
    }

}
