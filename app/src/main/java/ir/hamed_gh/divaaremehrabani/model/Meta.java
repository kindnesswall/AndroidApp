package ir.hamed_gh.divaaremehrabani.model;

/**
 * Created by Hamed on 5/6/16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Meta {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("error_code")
    @Expose
    private Integer errorCode;
    @SerializedName("error_message")
    @Expose
    private Object errorMessage;
    @SerializedName("errors")
    @Expose
    private List<Object> errors = new ArrayList<Object>();

    /**
     * @return The token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token The token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return The errorCode
     */
    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode The error_code
     */
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return The errorMessage
     */
    public Object getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage The error_message
     */
    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return The errors
     */
    public List<Object> getErrors() {
        return errors;
    }

    /**
     * @param errors The errors
     */
    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

}