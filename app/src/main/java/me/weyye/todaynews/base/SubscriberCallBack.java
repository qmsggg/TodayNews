package me.weyye.todaynews.base;

import android.os.Handler;
import android.os.Looper;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import me.weyye.todaynews.utils.ToastUtils;
import rx.Subscriber;

/**
 * Created by Administrator
 * on 2016/5/18.
 */
public abstract class SubscriberCallBack<T> extends Subscriber<ResultResponse<T>> {

    private Handler mDelivery;

    public SubscriberCallBack() {
        mDelivery = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onCompleted() {
        mDelivery = null;
    }

    @Override
    public void onError(final Throwable e) {
        e.printStackTrace();
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (e instanceof SocketTimeoutException) {
                    ToastUtils.showToast("网络连接超时");
                } else if (e instanceof SocketException) {
                    if (e instanceof ConnectException) {
                        ToastUtils.showToast("网络未连接");
                    } else {
                        ToastUtils.showToast("网络错误");
                    }
                }
                onError();
            }
        });
    }

    @Override
    public void onNext(ResultResponse response) {

        if (response.message.equals("success")) {
            onSuccess((T) response.data);
        } else {
//            if (response.Code == 400) {
                ToastUtils.showToast(response.message);
//            }
            onFailure(response);
        }


    }

    protected abstract void onSuccess(T response);

    protected void onError() {
    }

    protected void onFailure(ResultResponse response) {
    }

}
