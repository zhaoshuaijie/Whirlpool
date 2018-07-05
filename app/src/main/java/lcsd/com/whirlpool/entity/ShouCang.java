package lcsd.com.whirlpool.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */
public class ShouCang {
    public static class TRslist{

        private	String	id;	/*46*/
        private	String	lid;	/*2222*/
        private	String	addtime;	/*1500860692*/
        private	String	title;	/*DB90311YE*/
        private	String	pid;	/*393*/
        private	String	user_id;	/*33*/
        private	String	thumb;	/*res/201707/20/1a45a6be2fcaa568.jpg*/
        private	String	note;	/**/

        public void setId(String value){
            this.id = value;
        }
        public String getId(){
            return this.id;
        }

        public void setLid(String value){
            this.lid = value;
        }
        public String getLid(){
            return this.lid;
        }

        public void setAddtime(String value){
            this.addtime = value;
        }
        public String getAddtime(){
            return this.addtime;
        }

        public void setTitle(String value){
            this.title = value;
        }
        public String getTitle(){
            return this.title;
        }

        public void setPid(String value){
            this.pid = value;
        }
        public String getPid(){
            return this.pid;
        }

        public void setUser_id(String value){
            this.user_id = value;
        }
        public String getUser_id(){
            return this.user_id;
        }

        public void setThumb(String value){
            this.thumb = value;
        }
        public String getThumb(){
            return this.thumb;
        }

        public void setNote(String value){
            this.note = value;
        }
        public String getNote(){
            return this.note;
        }

    }
    private List<TRslist> rslist;	/*List<TRslist>*/
    public void setRslist(List<TRslist> value){
        this.rslist = value;
    }
    public List<TRslist> getRslist(){
        return this.rslist;
    }

    private	Integer	total;	/*1*/
    private	Integer	psize;	/*20*/
    private	Integer	pageid;	/*1*/

    public void setTotal(Integer value){
        this.total = value;
    }
    public Integer getTotal(){
        return this.total;
    }

    public void setPsize(Integer value){
        this.psize = value;
    }
    public Integer getPsize(){
        return this.psize;
    }

    public void setPageid(Integer value){
        this.pageid = value;
    }
    public Integer getPageid(){
        return this.pageid;
    }
}
