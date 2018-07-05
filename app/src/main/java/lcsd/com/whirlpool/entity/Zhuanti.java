package lcsd.com.whirlpool.entity;

/**
 * Created by Administrator on 2017/6/29.
 */
public class Zhuanti {
    private boolean istrue;
    private	String	id;	/*615*/
    private	String	title;	/*高老师讲冰箱*/
    private	String	ico;	/**/
    private	String	identifier;	/*gao*/

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

    public void setIdentifier(String value){
        this.identifier = value;
    }
    public String getIdentifier(){
        return this.identifier;
    }

    public boolean istrue() {
        return istrue;
    }

    public void setIstrue(boolean istrue) {
        this.istrue = istrue;
    }
}
