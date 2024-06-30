package miw.tfm.parchis.mongo.dto;
import miw.tfm.parchis.models.GameState;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "gameState")
public class GameStateEntity {

    @Id
    private String id;
    private ParchisEntity parchis;
    private UserEntity user;
    private String gameName;

    public GameStateEntity (){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ParchisEntity getParchis() {
        return parchis;
    }

    public void setParchis(ParchisEntity parchis) {
        this.parchis = parchis;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public GameStateEntity (GameState gameState){
        this.gameName = gameState.getGameName();
        this.parchis = new ParchisEntity(gameState.getParchis());
        this.user = new UserEntity(gameState.getUser());
    }
}
