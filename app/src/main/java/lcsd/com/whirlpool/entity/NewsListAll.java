package lcsd.com.whirlpool.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/6/27.
 * 资讯页面
 */
public class NewsListAll {
    private String total;
    private String psize;
    private String pegeid;
    private Cate cate;
    private List<Tree> tree;
    private List<Slider> slider;
    private List<Rslist> rslist;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPsize() {
        return psize;
    }

    public void setPsize(String psize) {
        this.psize = psize;
    }

    public String getPegeid() {
        return pegeid;
    }

    public void setPegeid(String pegeid) {
        this.pegeid = pegeid;
    }

    public Cate getCate() {
        return cate;
    }

    public void setCate(Cate cate) {
        this.cate = cate;
    }

    public List<Tree> getTree() {
        return tree;
    }

    public void setTree(List<Tree> tree) {
        this.tree = tree;
    }

    public List<Slider> getSlider() {
        return slider;
    }

    public void setSlider(List<Slider> slider) {
        this.slider = slider;
    }

    public List<Rslist> getRslist() {
        return rslist;
    }

    public void setRslist(List<Rslist> rslist) {
        this.rslist = rslist;
    }

    public class Cate{
        private	String	vouch;
        private	String	status;
        private	String	tag;
        private	String	psize;
        private	String	ico;
        private	String	site_id;
        private	String	seo_title;
        private	String	app_title;
        private	String	tpl_content;
        private	String	taxis;
        private	String	seo_desc;
        private	String	url;
        private	String	id;
        private	String	title;
        private	String	tpl_list;
        private	String	seo_keywords;
        private	String	identifier;
        private	String	parent_id;

        public String getVouch() {
            return vouch;
        }

        public void setVouch(String vouch) {
            this.vouch = vouch;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getPsize() {
            return psize;
        }

        public void setPsize(String psize) {
            this.psize = psize;
        }

        public String getIco() {
            return ico;
        }

        public void setIco(String ico) {
            this.ico = ico;
        }

        public String getSite_id() {
            return site_id;
        }

        public void setSite_id(String site_id) {
            this.site_id = site_id;
        }

        public String getSeo_title() {
            return seo_title;
        }

        public void setSeo_title(String seo_title) {
            this.seo_title = seo_title;
        }

        public String getApp_title() {
            return app_title;
        }

        public void setApp_title(String app_title) {
            this.app_title = app_title;
        }

        public String getTpl_content() {
            return tpl_content;
        }

        public void setTpl_content(String tpl_content) {
            this.tpl_content = tpl_content;
        }

        public String getTaxis() {
            return taxis;
        }

        public void setTaxis(String taxis) {
            this.taxis = taxis;
        }

        public String getSeo_desc() {
            return seo_desc;
        }

        public void setSeo_desc(String seo_desc) {
            this.seo_desc = seo_desc;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTpl_list() {
            return tpl_list;
        }

        public void setTpl_list(String tpl_list) {
            this.tpl_list = tpl_list;
        }

        public String getSeo_keywords() {
            return seo_keywords;
        }

        public void setSeo_keywords(String seo_keywords) {
            this.seo_keywords = seo_keywords;
        }

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }
    }
    public class Tree{
        private	String	vouch;
        private	String	status;
        private	String	tag;
        private	String	psize;
        private	String	ico;
        private	String	site_id;
        private	String	seo_title;
        private	Object	sublist;
        private	String	app_title;
        private	String	tpl_content;
        private	String	taxis;
        private	String	seo_desc;
        private	String	url;
        private	String	id;
        private	String	title;
        private	String	tpl_list;
        private	String	seo_keywords;
        private	String	identifier;
        private	String	parent_id;

        public String getVouch() {
            return vouch;
        }

        public void setVouch(String vouch) {
            this.vouch = vouch;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getPsize() {
            return psize;
        }

        public void setPsize(String psize) {
            this.psize = psize;
        }

        public String getIco() {
            return ico;
        }

        public void setIco(String ico) {
            this.ico = ico;
        }

        public String getSite_id() {
            return site_id;
        }

        public void setSite_id(String site_id) {
            this.site_id = site_id;
        }

        public String getSeo_title() {
            return seo_title;
        }

        public void setSeo_title(String seo_title) {
            this.seo_title = seo_title;
        }

        public Object getSublist() {
            return sublist;
        }

        public void setSublist(Object sublist) {
            this.sublist = sublist;
        }

        public String getApp_title() {
            return app_title;
        }

        public void setApp_title(String app_title) {
            this.app_title = app_title;
        }

        public String getTpl_content() {
            return tpl_content;
        }

        public void setTpl_content(String tpl_content) {
            this.tpl_content = tpl_content;
        }

        public String getTaxis() {
            return taxis;
        }

        public void setTaxis(String taxis) {
            this.taxis = taxis;
        }

        public String getSeo_desc() {
            return seo_desc;
        }

        public void setSeo_desc(String seo_desc) {
            this.seo_desc = seo_desc;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTpl_list() {
            return tpl_list;
        }

        public void setTpl_list(String tpl_list) {
            this.tpl_list = tpl_list;
        }

        public String getSeo_keywords() {
            return seo_keywords;
        }

        public void setSeo_keywords(String seo_keywords) {
            this.seo_keywords = seo_keywords;
        }

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }
    }
    //幻灯片
    public class Slider{
        private	String	id;	/*1369*/
        private	String	hits;	/*35*/
        private	String	title;	/*金山 WPS - 免费正版办公软件(支持Win/Linux/手机)*/
        private	String	attr;	/*c*/
        private	String	dateline;	/*1424916504*/
        private	String	thumb;	/*Object*/
        private	String	url;	/*http://huierpu.5kah.com/index.php?id=1369*/
        private	String	video;	/*Object*/
        private	String	note;	/*一直以来办公软件市场份额都是被微软的 Office 牢牢占据，但其价格较贵，国内大多都是用着盗版。其实国产免费的 WPS 已经完完全全可以和Office媲美，且完美兼容各种doc、docx、xls、xlsx、ppt等微软文档格式！*/

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHits() {
            return hits;
        }

        public void setHits(String hits) {
            this.hits = hits;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAttr() {
            return attr;
        }

        public void setAttr(String attr) {
            this.attr = attr;
        }

        public String getDateline() {
            return dateline;
        }

        public void setDateline(String dateline) {
            this.dateline = dateline;
        }


        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }
    }
    //新闻列表
    public class Rslist{
        private	String	id;	/*1369*/
        private	String	hits;	/*35*/
        private	String	title;	/*金山 WPS - 免费正版办公软件(支持Win/Linux/手机)*/
        private	String	attr;	/*c*/
        private	String	dateline;	/*1424916504*/
        private	String	thumb;	/*Object*/
        private	String	url;	/*http://huierpu.5kah.com/index.php?id=1369*/
        private	String	video;	/*Object*/
        private	String	note;	/*一直以来办公软件市场份额都是被微软的 Office 牢牢占据，但其价格较贵，国内大多都是用着盗版。其实国产免费的 WPS 已经完完全全可以和Office媲美，且完美兼容各种doc、docx、xls、xlsx、ppt等微软文档格式！*/

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHits() {
            return hits;
        }

        public void setHits(String hits) {
            this.hits = hits;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAttr() {
            return attr;
        }

        public void setAttr(String attr) {
            this.attr = attr;
        }

        public String getDateline() {
            return dateline;
        }

        public void setDateline(String dateline) {
            this.dateline = dateline;
        }


        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }


        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }
    }
}
