package demo.rc.com.selectphoto.utils;

import android.content.Context;

import com.lidroid.xutils.BitmapUtils;

/**
 * @author sk06
 * @ClassName: BitmapHelp
 * @Description: TODO(该类的作用：)
 * @date 2015年1月4日 下午3:51:19
 */
public class BitmapHelp {
    private BitmapHelp() {
    }

    private static BitmapUtils bitmapUtils;
    private static BitmapUtils bitmapUtilsAvatar;

    /**
     * BitmapUtils不是单例的 根据需要重载多个获取实例的方法
     *
     * @param appContext application context
     * @return
     */
    public static BitmapUtils getBitmapUtils(Context appContext) {
        if (bitmapUtils == null) {
            bitmapUtils = new BitmapUtils(appContext);
        }
        return bitmapUtils;
    }

    public static BitmapUtils getBitmapUtilsAvatar(Context appContext) {
        if (bitmapUtilsAvatar == null) {
            bitmapUtilsAvatar = new BitmapUtils(appContext);
        }
        return bitmapUtilsAvatar;
    }
}
