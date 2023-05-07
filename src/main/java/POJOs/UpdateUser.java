package POJOs;

public class UpdateUser {

    private String name;
    private String job;

    public UpdateUser(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public UpdateUser() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
