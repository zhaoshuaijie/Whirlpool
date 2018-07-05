package lcsd.com.whirlpool.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/18.
 */
public class Zhibo {
    public static class TRslist{

        private	String	id;	/*2223*/
        private	String	zb_url;	/*http://112.27.65.42:1935/live/zonghe/playlist.m3u8*/
        private	Integer	hits;	/*7*/
        private	String	title;	/*测试*/
        private	String	zb_status;	/*1*/
        private	String	attr;	/**/
        private	String	dateline;	/*1500859497*/
        private	String	hk_url;	/*http://112.27.65.42:1935/live/zonghe/playlist.m3u8*/
        private	String	thumb;	/*res/201707/18/thumb_4027.png*/
        private	String	url;	/*http://180.76.234.185/index.php?id=2223*/

        public void setId(String value){
            this.id = value;
        }
        public String getId(){
            return this.id;
        }

        public void setZb_url(String value){
            this.zb_url = value;
        }
        public String getZb_url(){
            return this.zb_url;
        }

        public void setHits(Integer value){
            this.hits = value;
        }
        public Integer getHits(){
            return this.hits;
        }

        public void setTitle(String value){
            this.title = value;
        }
        public String getTitle(){
            return this.title;
        }

        public void setZb_status(String value){
            this.zb_status = value;
        }
        public String getZb_status(){
            return this.zb_status;
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

        public void setHk_url(String value){
            this.hk_url = value;
        }
        public String getHk_url(){
            return this.hk_url;
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

    }
    private	List<TRslist>	rslist;	/*List<TRslist>*/
    public void setRslist(List<TRslist> value){
        this.rslist = value;
    }
    public List<TRslist> getRslist(){
        return this.rslist;
    }

    public static class TCate{

        private	String	vouch;	/*1*/
        private	String	status;	/*1*/
        private	String	tag;	/**/
        private	String	psize;	/*0*/
        private	String	ico;	/**/
        private	String	site_id;	/*1*/
        private	String	seo_title;	/**/
        private	String	app_title;	/*直播*/
        private	String	tpl_content;	/**/
        private	String	taxis;	/*70*/
        private	String	seo_desc;	/**/
        private	String	url;	/*http://180.76.234.185/index.php?id=live&cate=zhibo*/
        private	String	id;	/*738*/
        private	String	title;	/*直播*/
        private	String	tpl_list;	/**/
        private	String	seo_keywords;	/**/
        private	String	identifier;	/*zhibo*/
        private	String	parent_id;	/*0*/

        public void setVouch(String value){
            this.vouch = value;
        }
        public String getVouch(){
            return this.vouch;
        }

        public void setStatus(String value){
            this.status = value;
        }
        public String getStatus(){
            return this.status;
        }

        public void setTag(String value){
            this.tag = value;
        }
        public String getTag(){
            return this.tag;
        }

        public void setPsize(String value){
            this.psize = value;
        }
        public String getPsize(){
            return this.psize;
        }

        public void setIco(String value){
            this.ico = value;
        }
        public String getIco(){
            return this.ico;
        }

        public void setSite_id(String value){
            this.site_id = value;
        }
        public String getSite_id(){
            return this.site_id;
        }

        public void setSeo_title(String value){
            this.seo_title = value;
        }
        public String getSeo_title(){
            return this.seo_title;
        }

        public void setApp_title(String value){
            this.app_title = value;
        }
        public String getApp_title(){
            return this.app_title;
        }

        public void setTpl_content(String value){
            this.tpl_content = value;
        }
        public String getTpl_content(){
            return this.tpl_content;
        }

        public void setTaxis(String value){
            this.taxis = value;
        }
        public String getTaxis(){
            return this.taxis;
        }

        public void setSeo_desc(String value){
            this.seo_desc = value;
        }
        public String getSeo_desc(){
            return this.seo_desc;
        }

        public void setUrl(String value){
            this.url = value;
        }
        public String getUrl(){
            return this.url;
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

        public void setTpl_list(String value){
            this.tpl_list = value;
        }
        public String getTpl_list(){
            return this.tpl_list;
        }

        public void setSeo_keywords(String value){
            this.seo_keywords = value;
        }
        public String getSeo_keywords(){
            return this.seo_keywords;
        }

        public void setIdentifier(String value){
            this.identifier = value;
        }
        public String getIdentifier(){
            return this.identifier;
        }

        public void setParent_id(String value){
            this.parent_id = value;
        }
        public String getParent_id(){
            return this.parent_id;
        }

    }
    public static class TTree{

        private	String	vouch;	/*1*/
        private	String	status;	/*1*/
        private	String	tag;	/**/
        private	String	psize;	/*0*/
        private	String	ico;	/**/
        private	String	site_id;	/*1*/
        private	String	seo_title;	/**/
        private	String	app_title;	/*直播平台*/
        private	String	tpl_content;	/**/
        private	String	taxis;	/*5*/
        private	String	seo_desc;	/**/
        private	String	url;	/*http://180.76.234.185/index.php?id=live&cate=zhibo_pingtai*/
        private	String	id;	/*739*/
        private	String	title;	/*直播列表*/
        private	String	tpl_list;	/**/
        private	String	seo_keywords;	/**/
        private	String	identifier;	/*zhibo_pingtai*/
        private	String	parent_id;	/*738*/

        public void setVouch(String value){
            this.vouch = value;
        }
        public String getVouch(){
            return this.vouch;
        }

        public void setStatus(String value){
            this.status = value;
        }
        public String getStatus(){
            return this.status;
        }

        public void setTag(String value){
            this.tag = value;
        }
        public String getTag(){
            return this.tag;
        }

        public void setPsize(String value){
            this.psize = value;
        }
        public String getPsize(){
            return this.psize;
        }

        public void setIco(String value){
            this.ico = value;
        }
        public String getIco(){
            return this.ico;
        }

        public void setSite_id(String value){
            this.site_id = value;
        }
        public String getSite_id(){
            return this.site_id;
        }

        public void setSeo_title(String value){
            this.seo_title = value;
        }
        public String getSeo_title(){
            return this.seo_title;
        }

        public void setApp_title(String value){
            this.app_title = value;
        }
        public String getApp_title(){
            return this.app_title;
        }

        public void setTpl_content(String value){
            this.tpl_content = value;
        }
        public String getTpl_content(){
            return this.tpl_content;
        }

        public void setTaxis(String value){
            this.taxis = value;
        }
        public String getTaxis(){
            return this.taxis;
        }

        public void setSeo_desc(String value){
            this.seo_desc = value;
        }
        public String getSeo_desc(){
            return this.seo_desc;
        }

        public void setUrl(String value){
            this.url = value;
        }
        public String getUrl(){
            return this.url;
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

        public void setTpl_list(String value){
            this.tpl_list = value;
        }
        public String getTpl_list(){
            return this.tpl_list;
        }

        public void setSeo_keywords(String value){
            this.seo_keywords = value;
        }
        public String getSeo_keywords(){
            return this.seo_keywords;
        }

        public void setIdentifier(String value){
            this.identifier = value;
        }
        public String getIdentifier(){
            return this.identifier;
        }

        public void setParent_id(String value){
            this.parent_id = value;
        }
        public String getParent_id(){
            return this.parent_id;
        }

    }
    private	List<TTree>	tree;	/*List<TTree>*/
    public void setTree(List<TTree> value){
        this.tree = value;
    }
    public List<TTree> getTree(){
        return this.tree;
    }

    private	Integer	total;	/*1*/
    private	TCate	cate;	/*TCate*/
    private	String	psize;	/*10*/
    private	Integer	pageid;	/*1*/

    public void setTotal(Integer value){
        this.total = value;
    }
    public Integer getTotal(){
        return this.total;
    }

    public void setCate(TCate value){
        this.cate = value;
    }
    public TCate getCate(){
        return this.cate;
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
