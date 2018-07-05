package lcsd.com.whirlpool.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */
public class JiqiaoNext {

    public static class TRslist{

        private	String	id;	/*1866*/
        private	String	hits;	/*12*/
        private	String	title;	/*销售技巧*/
        private	String	attr;	/**/
        private	String	dateline;	/*1498662249*/
        private	String	thumb;	/**/
        private	String	url;	/*http://huierpu.5kah.com/index.php?id=1866*/
        private	String	note;	/*销售技巧*/
        private	String	video;	/**/

        public void setId(String value){
            this.id = value;
        }
        public String getId(){
            return this.id;
        }

        public void setHits(String value){
            this.hits = value;
        }
        public String getHits(){
            return this.hits;
        }

        public void setTitle(String value){
            this.title = value;
        }
        public String getTitle(){
            return this.title;
        }

        public void setAttr(String value){
            this.attr = value;
        }
        public String getAttr(){
            return this.attr;
        }

        public void setDateline(String value){
            this.dateline = value;
        }
        public String getDateline(){
            return this.dateline;
        }

        public void setThumb(String value){
            this.thumb = value;
        }
        public String getThumb(){
            return this.thumb;
        }

        public void setUrl(String value){
            this.url = value;
        }
        public String getUrl(){
            return this.url;
        }

        public void setNote(String value){
            this.note = value;
        }
        public String getNote(){
            return this.note;
        }

        public void setVideo(String value){
            this.video = value;
        }
        public String getVideo(){
            return this.video;
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
    private	String	psize;	/*10*/
    private	Integer	pageid;	/*1*/

    public void setTotal(Integer value){
        this.total = value;
    }
    public Integer getTotal(){
        return this.total;
    }

    public void setPsize(String value){
        this.psize = value;
    }
    public String getPsize(){
        return this.psize;
    }

    public void setPageid(Integer value){
        this.pageid = value;
    }
    public Integer getPageid(){
        return this.pageid;
    }

}
