package lcsd.com.whirlpool.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import lcsd.com.whirlpool.R;
import lcsd.com.whirlpool.activity.ImagePageActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/6/15.
 */
public class View3 extends FrameLayout{
    private static View3 view3=null;
    private Context context;
    private String s;
    private WebView wb;
    private ArrayList<String> images=new ArrayList<>();

    public static View3 getInstance(Context context,String s) {
        if (view3 == null)
            return new View3(context,s);
        return view3;
    }
    public View3(Context context,String s) {
        super(context);
        this.context = context;
        this.s=s;
        LayoutInflater.from(context).inflate(R.layout.view3, this);
        initView();
        initData();
    }

    private void initView() {
        wb= (WebView) findViewById(R.id.wb);
        Document docs = Jsoup.parse(s);
        Elements elementss = docs.getAllElements();
        if (elementss.size() != 0) {
            for (Element element : elementss) {
                element.attr("style", "max-width:100%;height:auto;line-height:28px;");
            }
        }
        String newhtmls = docs.toString();
        wb.loadDataWithBaseURL(null, newhtmls, "text/html", "utf-8", null);

        WebSettings settings=wb.getSettings();

        //防止中文乱码
        settings.setDefaultTextEncodingName("UTF -8");
        settings.setJavaScriptEnabled(true);

        wb.setWebChromeClient(new WebChromeClient());
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        //载入js
        wb.addJavascriptInterface(new JavascriptInterface(context), "imagelistner");

        wb.setWebViewClient(new WebViewClient() {
            // 网页跳转
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            // 网页加载结束
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // html加载完成之后，添加监听图片的点击js函数
                addImageClickListner();
            }
        });
    }

    private void initData() {
    }
    // 注入js函数监听
    private void addImageClickListner() {
        //遍历页面中所有img的节点，因为节点里面的图片的url即objs[i].src，保存所有图片的src.
        //为每个图片设置点击事件，objs[i].onclick
        wb.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{" +
                "window.imagelistner.readImageUrl(objs[i].src);  " +
                " objs[i].onclick=function()  " +
                " {  " +
                " window.imagelistner.openImage(this.src);  " +
                "  }  " +
                "}" +
                "})()");
    }
    // js通信接口
    public class JavascriptInterface {

        private Context context;

        public JavascriptInterface(Context context) {
            this.context = context;
        }

        @android.webkit.JavascriptInterface
        public void readImageUrl(String img) {     //把所有图片的url保存在ArrayList<String>中
            images.add(img);
        }

        @android.webkit.JavascriptInterface  //对于targetSdkVersion>=17的，要加这个声明
        public void openImage(String clickimg)//点击图片所调用到的函数
        {
            int index = 0;
            ArrayList<String> list = addImages();
            for (String url : list)
                if (url.equals(clickimg)) index = list.indexOf(clickimg);//获取点击图片在整个页面图片中的位置
            String[] imgs = new String[list.size()];
            for(int i=0;i<imgs.length;i++){
                imgs[i]=list.get(i);
            }
            Intent intent = new Intent();
            intent.putExtra("imgs", imgs);
            intent.putExtra("index", index);
            intent.setClass(context, ImagePageActivity.class);
            context.startActivity(intent);//启动ViewPagerActivity,用于显示图片
        }
    }


    //去重复
    private ArrayList<String> addImages() {
        ArrayList<String> list = new ArrayList<>();
        Set set = new HashSet();
        for (String cd:images) {
            if(set.add(cd)){
                list.add(cd);
            }
        }
        return list;
    }
}
