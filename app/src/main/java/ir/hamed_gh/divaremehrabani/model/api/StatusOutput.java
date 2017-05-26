package ir.hamed_gh.divaremehrabani.model.api;

import okhttp3.ResponseBody;

/**
 * Created by Mahshad on 11/27/2016 AD.
 */

public abstract class StatusOutput extends ResponseBody{

    public String tag;


//    @SerializedName("status")
//    public String status;
//
//    @SerializedName("errors")
//    public ArrayList<ResponseError> errors;
//
//    public class ResponseError {
//        @SerializedName("object_name")
//        public String objName;
//
//        @SerializedName("error_message")
//        public String errorMessage;
//
//        @SerializedName("error_code")
//        public String errorCode;
//    }
}
