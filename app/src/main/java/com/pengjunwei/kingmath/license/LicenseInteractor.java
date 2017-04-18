package com.pengjunwei.kingmath.license;

import com.pengjunwei.kingmath.base.BaseInteractor;
import com.pengjunwei.kingmath.pojo.SLicenseListResult;
import com.pengjunwei.kingmath.pojo.SLicenseVerifyResult;
import com.pengjunwei.kingmath.tool.RxSchedulersHelper;

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
        @POST("/kingmath/license/verify.php")
        @FormUrlEncoded
        Observable<SLicenseVerifyResult> verify(@Field("c") String cellPhone
                , @Field("l") String license, @Field("s") String sign);
    }

    public interface Interactor {
        Observable<SLicenseVerifyResult> verify(String phoneNumber, String license);
    }

    public static class WebInteractor implements Interactor {
        WebInterface webInterface = createService(WebInterface.class, BASE_URL);

        @Override
        public Observable<SLicenseVerifyResult> verify(String phoneNumber, String license) {
            return webInterface.verify(phoneNumber, license, "112233")
                    .compose(RxSchedulersHelper.<SLicenseVerifyResult>applyMainSchedulers());
        }
    }
}
