package miw.tfm.parchis.models;

import miw.tfm.parchis.mongo.dto.GameStateEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

@Component
public class GameState {
    private Parchis parchis;
    private UserModel user;
    private String gameName;
    private Boolean isSaved;

    public GameState(){}
    public GameState(GameStateEntity gameStateEntity){
        this.gameName = gameStateEntity.getGameName();
        this.user = new UserModel(gameStateEntity.getUser());
        this.parchis = new Parchis(gameStateEntity.getParchis());
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }


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

    public Boolean getSaved() {
        return isSaved;
    }

    public void setSaved(Boolean saved) {
        isSaved = saved;
    }


}
