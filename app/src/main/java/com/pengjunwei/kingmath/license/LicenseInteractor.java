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
        /**
         * 创建注册码
         */
        @POST("/kingmath/license/create.php")
        @FormUrlEncoded
        Observable<SLicenseListResult> create(@Field("c") String corporation, @Field("num") int number
                , @Field("s") String sign);


        /**
         * 获取注册码
         */
        @POST("/kingmath/license/list.php")
        @FormUrlEncoded
        Observable<SLicenseListResult> getList(@Field("limit") int limit, @Field("offset") int offset
                , @Field("s") String sign);

        /***/
        @POST("/kingmath/license/verify.php")
        @FormUrlEncoded
        Observable<SLicenseVerifyResult> verify(@Field("c") String cellPhone
                , @Field("l") String license, @Field("s") String sign);
    }

    interface Interactor {

        Observable<SLicenseListResult> create(String corporation, int number);

        /**
         * 获取注册码
         */
        Observable<SLicenseListResult> getList(int limit, int offset, String sign);


        Observable<SLicenseVerifyResult> verify(String phoneNumber, String license);
    }

    public static class WebInteractor implements Interactor {
        WebInterface webInterface = createService(WebInterface.class, BASE_URL);

        @Override
        public Observable<SLicenseListResult> create(String corporation, int number) {
            return webInterface.create(corporation, number, "1122").compose(RxSchedulersHelper.<SLicenseListResult>applyMainSchedulers());
        }

        @Override
        public Observable<SLicenseListResult> getList(int limit, int offset, String sign) {
            return webInterface.getList(limit, offset, sign).compose(RxSchedulersHelper
                    .<SLicenseListResult>applyMainSchedulers());
        }

        @Override
        public Observable<SLicenseVerifyResult> verify(String phoneNumber, String license) {
            return webInterface.verify(phoneNumber, license, "112233")
                    .compose(RxSchedulersHelper.<SLicenseVerifyResult>applyMainSchedulers());
        }
    }
}
