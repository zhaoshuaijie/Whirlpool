package lcsd.com.whirlpool.entity;

import java.io.Serializable;

/**
 * Created by jie on 2018/6/29.
 */
public class KaoshiZxContent implements Serializable {
    private String is_fen;

    private int fen;

    private String id;

    private String site_id;

    private String parent_id;

    private String status;

    private String title;

    private String taxis;

    private String tpl_list;

    private String tpl_content;

    private String psize;

    private String seo_title;

    private String seo_keywords;

    private String seo_desc;

    private String identifier;

    private String tag;

    private String ico;

    private String vouch;

    private String app_title;

    private String url;

    private boolean is_test;

    public void setIs_fen(String is_fen) {
        this.is_fen = is_fen;
    }

    public String getIs_fen() {
        return this.is_fen;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    public String getSite_id() {
        return this.site_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getParent_id() {
        return this.parent_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTaxis(String taxis) {
        this.taxis = taxis;
    }

    public String getTaxis() {
        return this.taxis;
    }

    public void setTpl_list(String tpl_list) {
        this.tpl_list = tpl_list;
    }

    public String getTpl_list() {
        return this.tpl_list;
    }

    public void setTpl_content(String tpl_content) {
        this.tpl_content = tpl_content;
    }

    public String getTpl_content() {
        return this.tpl_content;
    }

    public void setPsize(String psize) {
        this.psize = psize;
    }

    public String getPsize() {
        return this.psize;
    }

    public void setSeo_title(String seo_title) {
        this.seo_title = seo_title;
    }

    public String getSeo_title() {
        return this.seo_title;
    }

    public void setSeo_keywords(String seo_keywords) {
        this.seo_keywords = seo_keywords;
    }

    public String getSeo_keywords() {
        return this.seo_keywords;
    }

    public void setSeo_desc(String seo_desc) {
        this.seo_desc = seo_desc;
    }

    public String getSeo_desc() {
        return this.seo_desc;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return this.tag;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public String getIco() {
        return this.ico;
    }

    public void setVouch(String vouch) {
        this.vouch = vouch;
    }

    public String getVouch() {
        return this.vouch;
    }

    public void setApp_title(String app_title) {
        this.app_title = app_title;
    }

    public String getApp_title() {
        return this.app_title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public int getFen() {
        return fen;
    }

    public void setFen(int fen) {
        this.fen = fen;
    }

    public boolean isIs_test() {
        return is_test;
    }

    public void setIs_test(boolean is_test) {
        this.is_test = is_test;
    }
}
