package lcsd.com.whirlpool.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.activity.KaoshicontentActivity;
import lcsd.com.whirlpool.http.AppConfig;

/**
 * Created by Administrator on 2017/9/15.
 */
public class FragmentContent extends Fragment {
    private TextView tv, tv_d;
    private RadioGroup group;
    private RadioButton b1, b2, b3, b4,b5,b6;
    private LinearLayout ll_rd, ll_cb;
    private CheckBox cb1, cb2, cb3, cb4,cb5,cb6;
    private int item;
    private ImageView iv1,iv2;

    public static Fragment getInstance(Bundle bundle) {
        FragmentContent fragment = new FragmentContent();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentcontent, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    private void initView(View view) {
        item = getArguments().getInt("item");
        tv = (TextView) view.findViewById(R.id.who);
        group = (RadioGroup) view.findViewById(R.id.who_group);
        b1 = (RadioButton) view.findViewById(R.id.rd_1);
        b2 = (RadioButton) view.findViewById(R.id.rd_2);
        b3 = (RadioButton) view.findViewById(R.id.rd_3);
        b4 = (RadioButton) view.findViewById(R.id.rd_4);
        b5= (RadioButton) view.findViewById(R.id.rd_5);
        b6= (RadioButton) view.findViewById(R.id.rd_6);
        tv_d = (TextView) view.findViewById(R.id.how);
        ll_rd = (LinearLayout) view.findViewById(R.id.ll_rd);
        ll_cb = (LinearLayout) view.findViewById(R.id.ll_cb);
        cb1 = (CheckBox) view.findViewById(R.id.cb1);
        cb2 = (CheckBox) view.findViewById(R.id.cb2);
        cb3 = (CheckBox) view.findViewById(R.id.cb3);
        cb4 = (CheckBox) view.findViewById(R.id.cb4);
        cb5= (CheckBox) view.findViewById(R.id.cb5);
        cb6= (CheckBox) view.findViewById(R.id.cb6);
        iv1= (ImageView) view.findViewById(R.id.item_kstp1);
        iv2= (ImageView) view.findViewById(R.id.item_kstp2);

        if (KaoshicontentActivity.List.get(item).getType() == 1) {
            ll_rd.setVisibility(View.VISIBLE);
            ll_cb.setVisibility(View.GONE);

            tv.setText(KaoshicontentActivity.List.get(item).getTimu());
            b1.setText(KaoshicontentActivity.List.get(item).getA());
            b2.setText(KaoshicontentActivity.List.get(item).getB());
            b3.setText(KaoshicontentActivity.List.get(item).getC());
            b4.setText(KaoshicontentActivity.List.get(item).getD());

            if(KaoshicontentActivity.List.get(item).getE()!=null){
                b5.setVisibility(View.VISIBLE);
                b5.setText(KaoshicontentActivity.List.get(item).getE());
            }

            if(KaoshicontentActivity.List.get(item).getF()!=null){
                b6.setVisibility(View.VISIBLE);
                b6.setText(KaoshicontentActivity.List.get(item).getF());
            }
            if(KaoshicontentActivity.List.get(item).getImg()!=null&& KaoshicontentActivity.List.get(item).getImg().length()>1){
                iv2.setVisibility(View.VISIBLE);
                Glide.with(getContext()).load(AppConfig.mainurl+ KaoshicontentActivity.List.get(item).getImg()).into(iv2);
            }else {
                iv2.setVisibility(View.GONE);
            }

        } else {
            ll_rd.setVisibility(View.GONE);
            ll_cb.setVisibility(View.VISIBLE);
            tv_d.setText(KaoshicontentActivity.List.get(item).getTimu());
            cb1.setText(KaoshicontentActivity.List.get(item).getA());
            cb2.setText(KaoshicontentActivity.List.get(item).getB());
            cb3.setText(KaoshicontentActivity.List.get(item).getC());
            cb4.setText(KaoshicontentActivity.List.get(item).getD());

            if(KaoshicontentActivity.List.get(item).getE()!=null){
                cb5.setVisibility(View.VISIBLE);
                cb5.setText(KaoshicontentActivity.List.get(item).getE());
            }

            if(KaoshicontentActivity.List.get(item).getF()!=null){
                cb6.setVisibility(View.VISIBLE);
                cb6.setText(KaoshicontentActivity.List.get(item).getF());
            }
            if(KaoshicontentActivity.List.get(item).getImg()!=null&& KaoshicontentActivity.List.get(item).getImg().length()>1){
                iv1.setVisibility(View.VISIBLE);
                Glide.with(getContext()).load(AppConfig.mainurl+ KaoshicontentActivity.List.get(item).getImg()).into(iv1);
            }else {
                iv1.setVisibility(View.GONE);
            }
        }

    }

    private void initData() {
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rd_1:
                        KaoshicontentActivity.List.get(item).setCheck("A");
                        break;
                    case R.id.rd_2:
                        KaoshicontentActivity.List.get(item).setCheck("B");
                        break;
                    case R.id.rd_3:
                        KaoshicontentActivity.List.get(item).setCheck("C");
                        break;
                    case R.id.rd_4:
                        KaoshicontentActivity.List.get(item).setCheck("D");
                        break;
                    case R.id.rd_5:
                        KaoshicontentActivity.List.get(item).setCheck("E");
                        break;
                    case R.id.rd_6:
                        KaoshicontentActivity.List.get(item).setCheck("F");
                        break;
                }
            }
        });

        cb1.setOnCheckedChangeListener(listener);
        cb2.setOnCheckedChangeListener(listener);
        cb3.setOnCheckedChangeListener(listener);
        cb4.setOnCheckedChangeListener(listener);
        cb5.setOnCheckedChangeListener(listener);
        cb6.setOnCheckedChangeListener(listener);
    }

    private CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            String A = "",B="",C="",D="",E="",F="";
            if(cb1.isChecked()){
                A="A";
            }
            if(cb2.isChecked()){
                B="B";
            }
            if(cb3.isChecked()){
                C="C";
            }
            if(cb4.isChecked()){
                D="D";
            }
            if(cb5.isChecked()){
                E="E";
            }
            if(cb6.isChecked()){
                F="F";
            }
            KaoshicontentActivity.List.get(item).setCheck(A+B+C+D+E+F);
        }
    };

}
