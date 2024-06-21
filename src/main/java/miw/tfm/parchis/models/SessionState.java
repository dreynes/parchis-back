package miw.tfm.parchis.models;

import org.springframework.stereotype.Component;

@Component
public class SessionState {
    private Parchis parchis;
    private UserModel user;

    public Parchis getParchis() {
        return parchis;
    }

    public void setParchis(Parchis parchis) {
        this.parchis = parchis;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
