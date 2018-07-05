package lcsd.com.whirlpool.view;


import android.widget.ListView;

/**
 * Created by Administrator on 2016/8/3.
 */

    //Scrollview嵌套Listview
public class ScrollViewWithListView extends ListView {
    public ScrollViewWithListView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Integer.MAX_VALUE >> 2,如果不设置，系统默认设置是显示两条
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}