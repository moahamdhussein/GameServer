/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package response;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author esraa
 * @param <T>
 */
public class NetworkResponse<T> {
    ResponseStatus status;
    @SerializedName("response")
    T responseInfo;

    public T getResponseInfo() {
        return responseInfo;
    }

    public void setResponseInfo(T responseInfo) {
        this.responseInfo = responseInfo;
    }
    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }
    
    public enum ResponseStatus {
    SUCCESS("success"), 
    FAILURE("failure"),
    SERVER_ERROR("server_error");
    
    @SerializedName("status")
    private final String status;
    ResponseStatus(String status){
      this.status=status;
    }
}
}
