package response;

import com.google.gson.annotations.SerializedName;

public enum ResponseType {
    GAME("game_board");
    @SerializedName("type")
    private final String type;

    private ResponseType(String type) {
        this.type = type;
    }
    
}
