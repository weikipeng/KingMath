package com.pengjunwei.kingmath.license;

import android.util.Base64;

import com.pengjunwei.kingmath.base.BaseInteractor;
import com.pengjunwei.kingmath.pojo.SLicenseListResult;
import com.pengjunwei.kingmath.pojo.SLicenseVerifyResult;
import com.pengjunwei.kingmath.tool.DateTool;
import com.pengjunwei.kingmath.tool.MD5;
import com.pengjunwei.kingmath.tool.RxSchedulersHelper;

import java.io.UnsupportedEncodingException;

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
        Observable<SLicenseVerifyResult> verify(@Field("c") String cellPhone, @Field("l") String license, @Field("s") String sign);

        /***/
        @POST("/kingmath/license/auto_verify.php")
        @FormUrlEncoded
        Observable<SLicenseVerifyResult> autoVerify(@Field("c") String cellPhone, @Field("l") String license, @Field("channel") String channel, @Field("s") String sign);

    }

    public interface Interactor {
        Observable<SLicenseVerifyResult> verify(String phoneNumber, String license);

        Observable<SLicenseVerifyResult> autoVerify(String phoneNumber, String license, String channel);
    }

    public static class WebInteractor implements Interactor {
        WebInterface webInterface = createService(WebInterface.class, BASE_URL);

        @Override
        public Observable<SLicenseVerifyResult> verify(String phoneNumber, String license) {
            return webInterface.verify(phoneNumber, license, "112233").compose(RxSchedulersHelper.<SLicenseVerifyResult>applyMainSchedulers());
        }

        @Override
        public Observable<SLicenseVerifyResult> autoVerify(String phoneNumber, String license, String channel) {
            return webInterface.autoVerify(phoneNumber, license, channel, getSign(phoneNumber, license, channel))
                    .compose(RxSchedulersHelper.<SLicenseVerifyResult>applyMainSchedulers());
        }

        protected String getSign(String phonNumber, String license, String channel) {
            String result = "MH25KXFYWR5CSJKN67VKP2H95FRBM2";
            String date   = DateTool.getYMD();
            String number   = "";
            try {
                number = Base64.encodeToString(phonNumber.getBytes("utf-8"), Base64.NO_WRAP);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                license = Base64.encodeToString(license.getBytes("utf-8"), Base64.NO_WRAP);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            result = result + date + number + license + channel;
            result = MD5.md5(result);
            return result;
        }
    }
}
