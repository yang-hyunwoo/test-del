package test.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Response<T> {

    private String resultCode;
    private T result;



    public static Response<String> error(String errorCode,String message){
        return new Response<String>(errorCode,message);
    }


    @Override
    public String toString() {
        return "Response{" +
                "resultCode='" + resultCode + '\'' +
                ", result=" + result +
                '}';
    }

    public static <T> Response<T> success(T result){
        return new Response<>("SUCCESS",result);
    }

    public String toStream() {
        return "{" +
                "\"resultCode\":" + "\"" + resultCode +"\"," +
                "\"result\":" + "\"" + result + "\"" + "}";
    }

}
