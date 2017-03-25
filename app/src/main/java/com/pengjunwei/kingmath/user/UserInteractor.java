package com.pengjunwei.kingmath.user;

import com.pengjunwei.kingmath.base.BaseInteractor;
import com.pengjunwei.kingmath.pojo.SLicenseListResult;
import com.pengjunwei.kingmath.pojo.SLicenseVerifyResult;
import com.pengjunwei.kingmath.pojo.SLoginResult;
import com.pengjunwei.kingmath.tool.RxSchedulersHelper;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by WikiPeng on 2017/3/24 13:47.
 */
public class UserInteractor extends BaseInteractor {

    private interface WebInterface {
        /**
         * 创建注册码
         */
        @POST("/kingmath/license/create.php")
        @FormUrlEncoded
        Observable<SLicenseListResult> create(@Field("num") int number
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

        /***/
        @POST("/kingmath/user/login.php")
        @FormUrlEncoded
        Observable<SLoginResult> login(@Field("userName") String userName
                , @Field("password") String password, @Field("s") String sign);
    }

    interface Interactor {
        Observable<SLoginResult> login(String userName, String password);
    }

    public static class WebInteractor implements Interactor {
        WebInterface webInterface = createService(WebInterface.class, BASE_URL);

        @Override
        public Observable<SLoginResult> login(String userName, String password) {
            return webInterface.login(userName, password, "112233")
                    .compose(RxSchedulersHelper.<SLoginResult>applyMainSchedulers());
        }
    }
}
