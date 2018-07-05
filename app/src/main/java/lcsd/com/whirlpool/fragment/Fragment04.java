package lcsd.com.whirlpool.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.activity.RankActivity;
import lcsd.com.whirlpool.activity.RankAddActivity;
import lcsd.com.whirlpool.activity.WebActivity;

/**
 * Created by wei on 2017/6/7.
 */
public class Fragment04 extends Fragment implements View.OnClickListener{
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_main_04, container, false);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initView();
    }

    private void initView() {
        getActivity().findViewById(R.id.f4_ll_gz).setOnClickListener(this);
        getActivity().findViewById(R.id.f4_ll_jf).setOnClickListener(this);
        getActivity().findViewById(R.id.f4_ll_xz).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.f4_ll_gz:
                context.startActivity(new Intent(context, WebActivity.class).putExtra("url","http://180.76.234.185/jifenguize.html").putExtra("title","规则"));
                break;
            case R.id.f4_ll_jf:
                context.startActivity(new Intent(context, RankActivity.class));
                break;
            case R.id.f4_ll_xz:
                context.startActivity(new Intent(context, RankAddActivity.class));
                break;
        }
    }
}
