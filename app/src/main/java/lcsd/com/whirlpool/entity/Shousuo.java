package lcsd.com.whirlpool.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */
public class Shousuo {
    public static class TRslist{

        private	String	id;	/*3751*/
        private	String	title;	/*五、惠而浦冰箱主销产品讲解*/
        private	String	project_name;	/*培训专题*/
        private	String	project_id;	/*42*/
        private	String	cate_id;	/*829*/
        private	String	thumb;	/**/
        private	String	url;	/*http://180.76.234.185/index.php?id=3751&project=train*/
        private	String	cate_name;	/*初级培训师认证*/

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

        public void setProject_name(String value){
            this.project_name = value;
        }
        public String getProject_name(){
            return this.project_name;
        }

        public void setProject_id(String value){
            this.project_id = value;
        }
        public String getProject_id(){
            return this.project_id;
        }

        public void setCate_id(String value){
            this.cate_id = value;
        }
        public String getCate_id(){
            return this.cate_id;
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

        public void setCate_name(String value){
            this.cate_name = value;
        }
        public String getCate_name(){
            return this.cate_name;
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
