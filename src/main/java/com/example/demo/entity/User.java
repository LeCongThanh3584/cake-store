package com.example.demo.entity;

public class User extends BaseEntity {
    private String username;
    private String fullName;
    private String password;
    private Integer role;
    private Integer status;
    private Integer idStore;

    public User(Integer id) {
        super(id);
    }

    public User() {
    }

    public Integer getIdStore() {
        return idStore;
    }

    public void setIdStore(Integer idStore) {
        this.idStore = idStore;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", status=" + status +
                ", storeId=" + idStore +
                '}';
    }

    public User(String username, String fullName, String password, Integer role, Integer status, Integer idStore) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.role = role;
        this.status = status;
        this.idStore = idStore;
    }
}
