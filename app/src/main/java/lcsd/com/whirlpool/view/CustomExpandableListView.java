package lcsd.com.whirlpool.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

//解决Scrollview嵌套外层问题
public class CustomExpandableListView extends ExpandableListView {

    public CustomExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}