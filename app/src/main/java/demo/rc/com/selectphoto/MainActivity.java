package demo.rc.com.selectphoto;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import demo.rc.com.selectphoto.adpter.ReleaseOrderPicAdapter;
import demo.rc.com.selectphoto.utils.BitmapHelp;
import demo.rc.com.selectphoto.utils.ExtraKeys;
import demo.rc.com.selectphoto.utils.PhotoUtils;
import demo.rc.com.selectphoto.view.ActionSheetDialog;
import demo.rc.com.selectphoto.view.HorizontalListView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {
    private ImageView iv_addphoto;   //点击加载图片的ImageView
    private HorizontalListView hlv_servicepic;//图片展示
    private File image;
    private String imagePath;
    public BitmapUtils BITMAPUTILS;

    /*
      *   存放图片文件
      * */
    private List<File> FilePics;
    /*
 *   存放选择了图片的路径
 * */
    private List<String> PathPics;
    /*
   *  维修图片适配器
   * */
    private ReleaseOrderPicAdapter releasepic_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //进行初始化操作
        init();
        if (savedInstanceState != null) {
            if (TextUtils.isEmpty(imagePath)) {
                imagePath = savedInstanceState.getString("filePath");
            }
        }

    }

    private void init() {
        FilePics = new ArrayList<>();
        PathPics = new ArrayList<>();
        releasepic_adapter = new ReleaseOrderPicAdapter(this, PathPics, getBitmapUtils(), deleterPicListener);
        iv_addphoto = (ImageView) findViewById(R.id.release_order_iv_addphoto);
        hlv_servicepic = (HorizontalListView) findViewById(R.id.release_order_hlv_servicepic);
        hlv_servicepic.setAdapter(releasepic_adapter);
        hlv_servicepic.setOnItemClickListener(this);
        hlv_servicepic.setOnItemLongClickListener(this);
        iv_addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show(view);
            }
        });
    }

    ReleaseOrderPicAdapter.DeleterPicListener deleterPicListener = new ReleaseOrderPicAdapter.DeleterPicListener() {
        @Override
        public void deleterpic(int position) {

        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("filePath", imagePath);
    }


    public void show(View view) {
        new ActionSheetDialog(MainActivity.this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("用相机选择图片", ActionSheetDialog.SheetItemColor.Blue.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                                Intent intent = new Intent();
                                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                                File file = new File(Environment.getExternalStorageDirectory()
                                        + "/MyPhoto");
                                if (!file.exists()) {
                                    file.mkdirs();
                                }
                                image = new File(file, System.currentTimeMillis()
                                        + "myphoto.jpg");
                                imagePath = image.getPath();
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));
//                                // getOutputMediaFile();
                                startActivityForResult(intent, 0);

                            }
                        })
                .addSheetItem("去相册选择图片", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                //执行拍照前，应该先判断SD卡是否存在
                                String SDState = Environment.getExternalStorageState();
                                if (SDState.equals(Environment.MEDIA_MOUNTED)) {
                                    Intent intent1 = getImageClipIntent();
                                    startActivityForResult(intent1, 4);
                                } else {
                                    Toast.makeText(MainActivity.this, "内存卡不存在", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                ).show();

    }

    /**
     * 获取选择后的图片
     */
    public Intent getImageClipIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        return intent;
    }

    //回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 10:
                if (resultCode == MainActivity.this.RESULT_OK) {
                    Bundle bundle = data.getBundleExtra(ExtraKeys.Key_Msg1);
                }
                break;
        }
        if (requestCode != 0 && requestCode != 4) {
            return;
        } else {
            switch (requestCode) {
                case 0:
                    if (imagePath != null) {
                        image = new File(imagePath);
                        if (image.exists()) {
                            FilePics.add(PhotoUtils.scal(imagePath));
                            PathPics.add(imagePath);
                            releasepic_adapter.notifyDataSetChanged();
                        }
                    }
                    break;
                case 4:
                    try {
                        Uri uri = getPictureUri(data);
                        String path = "";
                        String[] proj = {MediaStore.Images.Media.DATA};
                        Cursor cursor = MainActivity.this.managedQuery(uri, proj, null, null, null);
                        if (cursor != null) {
                            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            cursor.moveToFirst();
                            path = cursor.getString(column_index);// 图片在的路径
                        }
                        FilePics.add(PhotoUtils.scal(path));
                        PathPics.add(path);
                        releasepic_adapter.notifyDataSetChanged();

                        break;
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "选取文件失败", Toast.LENGTH_SHORT).show();
                        Log.e("Exception", e.getMessage(), e);
                    }
            }
        }
    }

    //获取路径
    public Uri getPictureUri(android.content.Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = MainActivity.this.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.ImageColumns._ID},
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }
//获取我们图片工具
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

    /**
     * 长按Item进行删除
     *
     * @param adapterView
     * @param view
     * @param positions
     * @param l
     * @return
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int positions, long l) {
        Dialog alertDialog = new android.support.v7.app.AlertDialog.Builder(MainActivity.this).
                setTitle("温馨提示？").
                setMessage("是否删除该图片？").
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        FilePics.remove(positions);
                        PathPics.remove(positions);
                        releasepic_adapter.notifyDataSetChanged();
                    }
                }).
                setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                }).
                create();
        alertDialog.show();
        return false;
    }

    /**
     * 长按删除图片
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.release_order_hlv_servicepic:
                if (position == PathPics.size()) {
                    show(view);
                }
                break;
        }
    }
}

