package org.opendaylight.nemo.tool.eclipse.plugin.rest.actions.model;

/**
 * Created by hj on 10/29/15.
 */
public class User {
    private String user_id;
    private String user_name;
    private String user_role;
    private String user_password;

    public User(String user_name, String user_password, String user_role) {
        this.user_name = user_name;
        this.user_role = user_role;
        this.user_password = user_password;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (user_id != null ? !user_id.equals(user.user_id) : user.user_id != null) return false;
        if (user_name != null ? !user_name.equals(user.user_name) : user.user_name != null) return false;
        if (user_password != null ? !user_password.equals(user.user_password) : user.user_password != null)
            return false;
        if (user_role != null ? !user_role.equals(user.user_role) : user.user_role != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = user_id != null ? user_id.hashCode() : 0;
        result = 31 * result + (user_name != null ? user_name.hashCode() : 0);
        result = 31 * result + (user_role != null ? user_role.hashCode() : 0);
        result = 31 * result + (user_password != null ? user_password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_role='" + user_role + '\'' +
                ", user_password='" + user_password + '\'' +
                '}';
    }
}
