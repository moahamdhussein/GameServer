package request;

import com.google.gson.annotations.SerializedName;
/**
 *
 * @author esraa
 */
public enum RequestType {
    LOGIN("login_request"),
    REGISTER("register_request"),
    AVAILABLE_PLAYERS("available_players_request"),
    GAME("game_board");
   @SerializedName("type")
    private final String type;
    RequestType(String type) {
        this.type = type;
    }
}
