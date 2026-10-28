
package gr.automation.platform.services.users;

public enum UserEndpoints {

    REGISTER("/users/register"),
    LOGIN("/users/login"),
    UPDATE("/users/update");

    private final String path;

    UserEndpoints(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
