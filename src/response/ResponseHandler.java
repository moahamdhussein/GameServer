package response;
import com.google.gson.Gson;
/**
 *
 * @author esraa
 */
public class ResponseHandler {
       public static <T> String createJsonResponse( NetworkResponse<T> response) {
        Gson gson = new Gson();
        return gson.toJson(response);
        
    }
}
