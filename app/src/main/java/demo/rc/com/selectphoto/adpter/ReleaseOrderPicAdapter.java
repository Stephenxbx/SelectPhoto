package demo.rc.com.selectphoto.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import demo.rc.com.selectphoto.R;

public class ReleaseOrderPicAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<String> PathPics;
    private Context context;
    private BitmapUtils bitmapUtils;
    private DeleterPicListener deleterPicListener;

    public ReleaseOrderPicAdapter(Context context, List<String> PathPics, BitmapUtils bitmapUtils, DeleterPicListener deleterPicListener) {
        this.PathPics = PathPics;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.bitmapUtils = bitmapUtils;
        this.deleterPicListener = deleterPicListener;
    }

    @Override
    public int getCount() {
        return PathPics.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater
                    .inflate(R.layout.item_releaseorder_pic, null);
            viewHolder.iv_pic = (ImageView) view.findViewById(R.id.item_iv_releaseorderpic);
            viewHolder.iv_deleter = (ImageView) view.findViewById(R.id.item_iv_releaseorder_deleterpic);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.iv_deleter.setVisibility(View.GONE);
        bitmapUtils.display(viewHolder.iv_pic, PathPics.get(i));
        viewHolder.iv_deleter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleterPicListener != null) {
                    deleterPicListener.deleterpic(i);
                }
            }
        });
        return view;
    }

    class ViewHolder {
        ImageView iv_pic;
        ImageView iv_deleter;
    }

    public interface DeleterPicListener {
        void deleterpic(int position);
    }
}
