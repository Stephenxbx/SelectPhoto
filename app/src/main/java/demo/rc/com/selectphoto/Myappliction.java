package demo.rc.com.selectphoto;

import android.app.Application;
import android.graphics.Bitmap;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;

import demo.rc.com.selectphoto.utils.BitmapHelp;

/**
 * Created by Administrator on 2016/11/18.
 */

public class Myappliction extends Application {
    public BitmapUtils BITMAPUTILS;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public final Myappliction getMyApplication() {
        return (Myappliction) getApplicationContext();
    }
    public BitmapUtils getBitmapUtils() {
        if (BITMAPUTILS == null) {
            BITMAPUTILS = BitmapHelp.getBitmapUtils(this);
            BITMAPUTILS.configDefaultLoadingImage(R.drawable.defaultimage);
            BITMAPUTILS.configDefaultLoadFailedImage(R.drawable.defaultimage);
            BITMAPUTILS.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
            BITMAPUTILS.configMemoryCacheEnabled(false);
            // 设置最大宽高, 不设置时更具控件属性自适应.
            BITMAPUTILS.configDefaultBitmapMaxSize(BitmapCommonUtils
                    .getScreenSize(this).scaleDown(3));

        }
        return BITMAPUTILS;
    }
}
