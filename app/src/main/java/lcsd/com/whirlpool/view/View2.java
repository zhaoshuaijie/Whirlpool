package lcsd.com.whirlpool.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.activity.ImagePageActivity;
import lcsd.com.whirlpool.http.AppConfig;

/**
 * Created by Administrator on 2017/6/15.
 */
public class View2 extends FrameLayout implements View.OnClickListener{
    private static View2 view2=null;
    private Context context;
    private ImageView iv;
    private String param;

    public static View2 getInstance(Context context,String param) {
        if (view2 == null)
            return new View2(context,param);
        return view2;
    }
    public View2(Context context,String param) {
        super(context);
        this.context = context;
        this.param=param;
        LayoutInflater.from(context).inflate(R.layout.view2, this);
        initView();
        initData();
    }

    private void initView() {
        iv= (ImageView) findViewById(R.id.view2_iv);
        iv.setOnClickListener(this);
        Glide.with(context).load(AppConfig.mainurl+param).into(iv);
    }

    private void initData() {

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, ImagePageActivity.class);
        String[] imgs={param};
        intent.putExtra("imgs", imgs);
        context.startActivity(intent);
    }
}
