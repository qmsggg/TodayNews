package me.weyye.todaynews.utils;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by RayYeung on 2016/7/11.
 */
public class ImageLoaderUtils {

    public static ImageLoader getImageLoader() {
        return ImageLoader.getInstance();
    }


    public static PauseOnScrollListener getPauseOnScrollListener() {
        return new PauseOnScrollListener(getImageLoader(), true, true);
    }


    public static void displayImage(String uri, ImageView view) {
        getImageLoader().displayImage(uri, view, ImageOptHelper.getImgOptions());
    }


    public static void displayAvatar(String uri, ImageView view) {
        getImageLoader().displayImage(uri, view, ImageOptHelper.getAvatarOptions());
    }

    public static void displayBigImage(String uri, ImageView view) {
        getImageLoader().displayImage(uri, view, ImageOptHelper.getBigImgOptions());
    }

    public static void loadImage(String path, final LoadingListener listener) {
        getImageLoader().loadImage(path, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                listener.onLoadingComplete(loadedImage);
            }
        });
    }

    public interface LoadingListener {
        void onLoadingComplete(Bitmap loadedImage);
    }

}
