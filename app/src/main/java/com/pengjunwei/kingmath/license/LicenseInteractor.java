package com.pengjunwei.kingmath.license;

import com.pengjunwei.kingmath.pojo.SLicenseVerifyResult;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by WikiPeng on 2017/3/20 11:21.
 */
public class LicenseInteractor extends BaseInteractor {

    private interface WebInterface {
        /***/
        @POST("/license/verify.php")
        @FormUrlEncoded
        Observable<SLicenseVerifyResult> verify(@Field("c") String cellPhone
                , @Field("l") String license, @Field("s") String sign);
    }

    interface Interactor {

        Observable<SLicenseVerifyResult> verify(String license);
    }

    public static class WebInteractor implements Interactor {
        WebInterface webInterface = createService(WebInterface.class, "");

        @Override
        public Observable<SLicenseVerifyResult> verify(String license) {
            return webInterface.verify("", license, "");
        }
    }
}
