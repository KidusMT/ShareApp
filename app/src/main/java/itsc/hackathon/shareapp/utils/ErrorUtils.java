package itsc.hackathon.shareapp.utils;

import java.io.IOException;
import java.lang.annotation.Annotation;

import itsc.hackathon.shareapp.data.network.ApiCall;
import itsc.hackathon.shareapp.data.network.model.ApiError;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {

    public static ApiError parseError(Response<?> response) {
        Converter<ResponseBody, ApiError> converter =
                ApiCall.Factory.retrofit()
                        .responseBodyConverter(ApiError.class, new Annotation[0]);

        ApiError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ApiError();
        }

        return error;
    }
}
