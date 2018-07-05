package lcsd.com.whirlpool.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.activity.BaodianContentActivity;
import lcsd.com.whirlpool.activity.ZixunContentActivity;
import lcsd.com.whirlpool.adapter.ZujiAdapter;
import lcsd.com.whirlpool.dialog.ActionSheetDialog1;
import lcsd.com.whirlpool.http.AppConfig;
import lcsd.com.whirlpool.sql.Constant;
import lcsd.com.whirlpool.sql.DbManager;
import lcsd.com.whirlpool.sql.MySqliteHelper;
import lcsd.com.whirlpool.sql.Sqlentity;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by wei on 2017/6/7.
 */
public class Fragment02 extends Fragment {
    private Context context;
    private PtrClassicFrameLayout mPtrFrame;
    private ListView lv;
    private List<Sqlentity> list;
    private ZujiAdapter adapter;
    private MySqliteHelper helper;
    private ActionSheetDialog1 dialog,dialogall;
    private ImageView iv_delete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_main_02, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        helper = DbManager.getIntance(context);
        initView();
        initData();
        initSql(0);
    }

    private void initData() {
        list = new ArrayList<>();
        adapter = new ZujiAdapter(context, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (list.get(i).getType().equals("产品宝典")) {
                    startActivity(new Intent(context, BaodianContentActivity.class).putExtra("id", list.get(i).getId() + "").putExtra("title", list.get(i).getTitle()));
                }  else {
                    startActivity(new Intent(context, ZixunContentActivity.class).putExtra("id", list.get(i).getId() + "").putExtra("title", list.get(i).getTitle()).putExtra("url", AppConfig.Sy + "?id=" + list.get(i).getId()).putExtra("img", list.get(i).getImg()));
                }
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                showdeleteDialog(i);
                return true;
            }
        });
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.size()>0){
                    showdeleteAllDialog();
                }else {
                    Toast.makeText(context,"还没有浏览记录哦",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //删除单个记录对话框
    private void showdeleteDialog(final int i){
        dialog=new ActionSheetDialog1(context);
        dialog.builder().setTitle("删除该条记录").addSheetItem("删除", null, new ActionSheetDialog1.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                SQLiteDatabase db=helper.getWritableDatabase();
                int count=db.delete(Constant.TABLE_NAME,Constant.ID+"=?",new String[]{list.get(i).getId()+""});
                if(count>0){
                    list.remove(i);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context,"删除失败",Toast.LENGTH_SHORT).show();
                }
                db.close();
            }
        }).show();
    }

    //删除全部对话框
    private void showdeleteAllDialog(){
        dialogall=new ActionSheetDialog1(context);
        dialogall.builder().setTitle("全部删除").addSheetItem("删除", null, new ActionSheetDialog1.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                SQLiteDatabase db=helper.getWritableDatabase();
                int count=db.delete(Constant.TABLE_NAME,null,null);
                if(count>0){
                    list.clear();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context,"删除失败",Toast.LENGTH_SHORT).show();
                }
                db.close();
            }
        }).show();
    }

    private void initView() {
        lv = (ListView) getActivity().findViewById(R.id.lv_zj);
        mPtrFrame = (PtrClassicFrameLayout) getActivity().findViewById(R.id.rotate_header_list_view_frame2);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        iv_delete= (ImageView) getActivity().findViewById(R.id.f2_iv_delete);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {


            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                initSql(1);

            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, lv, header);
            }
        });
    }

    private void initSql(int i) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select * from " + Constant.TABLE_NAME + " order by " + Constant.TIME + " DESC";
        Cursor cursor = DbManager.selectDataBySql(db, sql, null);
        if (list != null) {
            list.clear();
        }
        List<Sqlentity> list1=new ArrayList<>();
        list1.addAll(DbManager.cursorToList(cursor));
        if(list1.size()>50){
            list.addAll(list1.subList(0,50));
        }else{
            list.addAll(list1);
        }
        adapter.notifyDataSetChanged();
        if (i == 1) {
            mPtrFrame.refreshComplete();
        }
        db.close();
    }
}
