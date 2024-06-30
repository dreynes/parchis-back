package miw.tfm.parchis.models;

import miw.tfm.parchis.mongo.dto.UserEntity;

public class UserModel {
    private String username;
    private String password;


    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public UserModel(UserEntity userEntity) {
        this.username = userEntity.getUsername();
        this.password = userEntity.getPassword();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
