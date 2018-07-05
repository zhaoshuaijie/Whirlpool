package lcsd.com.whirlpool.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */
public class Kaoshi1 {
    public static class TOption{

        private	String	cnt;	/*考题一选项a*/
        private	String	no;	/*A*/

        public void setCnt(String value){
            this.cnt = value;
        }
        public String getCnt(){
            return this.cnt;
        }

        public void setNo(String value){
            this.no = value;
        }
        public String getNo(){
            return this.no;
        }

    }
    private List<TOption> option;	/*List<TOption>*/
    public void setOption(List<TOption> value){
        this.option = value;
    }
    public List<TOption> getOption(){
        return this.option;
    }

    private	String	title;	/*考题一*/
    private	String	keys;	/*A*/
    private	String	thumb;	/*res/201707/11/8f17b23e7cbcaafb.png*/

    public void setTitle(String value){
        this.title = value;
    }
    public String getTitle(){
        return this.title;
    }

    public void setKeys(String value){
        this.keys = value;
    }
    public String getKeys(){
        return this.keys;
    }

    public void setThumb(String value){
        this.thumb = value;
    }
    public String getThumb(){
        return this.thumb;
    }
}
