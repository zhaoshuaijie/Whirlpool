package lcsd.com.whirlpool.entity;

/**
 * Created by Administrator on 2017/7/17.
 */
public class ScList {
    private	String	total;	/*10*/
    private	String	id;	/*43*/
    private	String	title;	/*新闻资讯*/
    private	String	ico;	/*res/201707/17/52e3bf91fc7a51f4.png*/
    private	String	url;	/*http://180.76.234.185/index.php?c=usercp&f=fav&pid=43*/

    public void setTotal(String value){
        this.total = value;
    }
    public String getTotal(){
        return this.total;
    }

    public void setId(String value){
        this.id = value;
    }
    public String getId(){
        return this.id;
    }

    public void setTitle(String value){
        this.title = value;
    }
    public String getTitle(){
        return this.title;
    }

    public void setIco(String value){
        this.ico = value;
    }
    public String getIco(){
        return this.ico;
    }

    public void setUrl(String value){
        this.url = value;
    }
    public String getUrl(){
        return this.url;
    }

}
