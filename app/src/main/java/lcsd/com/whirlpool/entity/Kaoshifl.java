package lcsd.com.whirlpool.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */
public class Kaoshifl {

    public static class TCate{

        private	String	vouch;	/*1*/
        private	String	status;	/*1*/
        private	String	tag;	/**/
        private	String	psize;	/*0*/
        private	String	ico;	/**/
        private	String	site_id;	/*1*/
        private	String	seo_title;	/**/
        private	String	app_title;	/*考试中心*/
        private	String	tpl_content;	/**/
        private	String	taxis;	/*65*/
        private	String	seo_desc;	/**/
        private	String	url;	/*http://180.76.234.185/index.php?id=exam&cate=test*/
        private	String	id;	/*720*/
        private	String	title;	/*考试中心*/
        private	String	tpl_list;	/**/
        private	String	seo_keywords;	/**/
        private	String	identifier;	/*test*/
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

        public static class OSublist implements Serializable {

            public int getFen() {
                return fen;
            }

            public void setFen(int fen) {
                this.fen = fen;
            }

            public String getIs_fen() {
                return is_fen;
            }

            public void setIs_fen(String is_fen) {
                this.is_fen = is_fen;
            }

            public static class TSublist implements Serializable{

                private	String	vouch;	/*1*/
                private	String	status;	/*1*/
                private	String	tag;	/**/
                private	String	psize;	/*0*/
                private	String	ico;	/**/
                private	String	site_id;	/*1*/
                private	String	seo_title;	/**/
                private	String	app_title;	/*惠而浦*/
                private	String	tpl_content;	/**/
                private	String	taxis;	/*5*/
                private	String	seo_desc;	/**/
                private	String	url;	/*http://180.76.234.185/index.php?id=exam&cate=huierpu*/
                private	String	id;	/*751*/
                private	String	title;	/*惠而浦*/
                private	String	tpl_list;	/**/
                private	String	seo_keywords;	/**/
                private	String	identifier;	/*huierpu*/
                private	String	parent_id;	/*742*/
                private int fen;
                private String is_fen;
                private String sublist;
                private boolean lock;
                private boolean is_test;

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

                public int getFen() {
                    return fen;
                }

                public void setFen(int fen) {
                    this.fen = fen;
                }

                public String getIs_fen() {
                    return is_fen;
                }

                public void setIs_fen(String is_fen) {
                    this.is_fen = is_fen;
                }

                public String getSublist() {
                    return sublist;
                }

                public void setSublist(String sublist) {
                    this.sublist = sublist;
                }

                public boolean isLock() {
                    return lock;
                }

                public void setLock(boolean lock) {
                    this.lock = lock;
                }

                public boolean isIs_test() {
                    return is_test;
                }

                public void setIs_test(boolean is_test) {
                    this.is_test = is_test;
                }
            }
            private ArrayList<TSublist> sublist;	/*List<TSublist>*/
            public void setSublist(ArrayList<TSublist> value){
                this.sublist = value;
            }
            public ArrayList<TSublist> getSublist(){
                return this.sublist;
            }

            private	String	vouch;	/*1*/
            private	String	status;	/*1*/
            private	String	tag;	/**/
            private	String	psize;	/*0*/
            private	String	ico;	/**/
            private	String	site_id;	/*1*/
            private	String	seo_title;	/**/
            private	String	app_title;	/*三洋*/
            private	String	tpl_content;	/**/
            private	String	taxis;	/*5*/
            private	String	seo_desc;	/**/
            private	String	url;	/*http://180.76.234.185/index.php?id=exam&cate=xiyiji1*/
            private	String	id;	/*742*/
            private	String	title;	/*洗衣机*/
            private	String	tpl_list;	/**/
            private	String	seo_keywords;	/**/
            private	String	identifier;	/*xiyiji1*/
            private	String	parent_id;	/*721*/
            private int fen;
            private String is_fen;

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
        private List<OSublist> sublist;	/*List<OSublist>*/
        public void setSublist(List<OSublist> value){
            this.sublist = value;
        }
        public List<OSublist> getSublist(){
            return this.sublist;
        }

        private	String	vouch;	/*1*/
        private	String	status;	/*1*/
        private	String	tag;	/**/
        private	String	psize;	/*0*/
        private	String	ico;	/**/
        private	String	site_id;	/*1*/
        private	String	seo_title;	/**/
        private	String	app_title;	/*随机测试*/
        private	String	tpl_content;	/**/
        private	String	taxis;	/*5*/
        private	String	seo_desc;	/**/
        private	String	url;	/*http://180.76.234.185/index.php?id=exam&cate=rand*/
        private	String	id;	/*721*/
        private	String	title;	/*随机测试*/
        private	String	tpl_list;	/**/
        private	String	seo_keywords;	/**/
        private	String	identifier;	/*rand*/
        private	String	parent_id;	/*720*/

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

    private	TCate	cate;	/*TCate*/

    public void setCate(TCate value){
        this.cate = value;
    }
    public TCate getCate(){
        return this.cate;
    }
}
