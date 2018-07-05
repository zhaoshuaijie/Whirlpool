package lcsd.com.whirlpool.entity;

import java.util.List;

/***
 * Json To JavaBean
 */
public class Product {

    public static class TCate {

        private String vouch;	/*1*/
        private String status;	/*1*/
        private String tag;	/**/
        private String psize;	/*0*/
        private String ico;	/**/
        private String site_id;	/*1*/
        private String seo_title;	/**/
        private String app_title;	/*产品宝典*/
        private String tpl_content;	/**/
        private String taxis;	/*30*/
        private String seo_desc;	/**/
        private String url;	/*http://huierpu.5kah.com/index.php?id=products&cate=goods*/
        private String id;	/*154*/
        private String title;	/*产品宝典*/
        private String tpl_list;	/*products_list*/
        private String seo_keywords;	/**/
        private String identifier;	/*goods*/
        private String parent_id;	/*0*/

        public void setVouch(String value) {
            this.vouch = value;
        }

        public String getVouch() {
            return this.vouch;
        }

        public void setStatus(String value) {
            this.status = value;
        }

        public String getStatus() {
            return this.status;
        }

        public void setTag(String value) {
            this.tag = value;
        }

        public String getTag() {
            return this.tag;
        }

        public void setPsize(String value) {
            this.psize = value;
        }

        public String getPsize() {
            return this.psize;
        }

        public void setIco(String value) {
            this.ico = value;
        }

        public String getIco() {
            return this.ico;
        }

        public void setSite_id(String value) {
            this.site_id = value;
        }

        public String getSite_id() {
            return this.site_id;
        }

        public void setSeo_title(String value) {
            this.seo_title = value;
        }

        public String getSeo_title() {
            return this.seo_title;
        }

        public void setApp_title(String value) {
            this.app_title = value;
        }

        public String getApp_title() {
            return this.app_title;
        }

        public void setTpl_content(String value) {
            this.tpl_content = value;
        }

        public String getTpl_content() {
            return this.tpl_content;
        }

        public void setTaxis(String value) {
            this.taxis = value;
        }

        public String getTaxis() {
            return this.taxis;
        }

        public void setSeo_desc(String value) {
            this.seo_desc = value;
        }

        public String getSeo_desc() {
            return this.seo_desc;
        }

        public void setUrl(String value) {
            this.url = value;
        }

        public String getUrl() {
            return this.url;
        }

        public void setId(String value) {
            this.id = value;
        }

        public String getId() {
            return this.id;
        }

        public void setTitle(String value) {
            this.title = value;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTpl_list(String value) {
            this.tpl_list = value;
        }

        public String getTpl_list() {
            return this.tpl_list;
        }

        public void setSeo_keywords(String value) {
            this.seo_keywords = value;
        }

        public String getSeo_keywords() {
            return this.seo_keywords;
        }

        public void setIdentifier(String value) {
            this.identifier = value;
        }

        public String getIdentifier() {
            return this.identifier;
        }

        public void setParent_id(String value) {
            this.parent_id = value;
        }

        public String getParent_id() {
            return this.parent_id;
        }

    }

    public static class TTree {

        public static class OSublist {

            public static class TSublist {

                public List<TRslist> getRslist() {
                    return rslist;
                }

                public void setRslist(List<TRslist> rslist) {
                    this.rslist = rslist;
                }

                public static class SSublist {

                    public static class TRslist {

                        private String id;	/*1854*/
                        private String title;	/*洗衣机型号二*/

                        public void setId(String value) {
                            this.id = value;
                        }

                        public String getId() {
                            return this.id;
                        }

                        public void setTitle(String value) {
                            this.title = value;
                        }

                        public String getTitle() {
                            return this.title;
                        }

                    }

                    private List<TRslist> rslist;	/*List<TRslist>*/

                    public void setRslist(List<TRslist> value) {
                        this.rslist = value;
                    }

                    public List<TRslist> getRslist() {
                        return this.rslist;
                    }

                    private String vouch;	/*1*/
                    private String status;	/*1*/
                    private String tag;	/**/
                    private String psize;	/*0*/
                    private String ico;	/**/
                    private String site_id;	/*1*/
                    private String seo_title;	/**/
                    private Object sublist;	/*Object*/
                    private String app_title;	/*分类一*/
                    private String tpl_content;	/**/
                    private String taxis;	/*5*/
                    private String seo_desc;	/**/
                    private String url;	/*http://huierpu.5kah.com/index.php?id=products&cate=fenleiyi*/
                    private String id;	/*605*/
                    private String title;	/*分类一*/
                    private String tpl_list;	/**/
                    private String seo_keywords;	/**/
                    private String identifier;	/*fenleiyi*/
                    private String parent_id;	/*603*/

                    public void setVouch(String value) {
                        this.vouch = value;
                    }

                    public String getVouch() {
                        return this.vouch;
                    }

                    public void setStatus(String value) {
                        this.status = value;
                    }

                    public String getStatus() {
                        return this.status;
                    }

                    public void setTag(String value) {
                        this.tag = value;
                    }

                    public String getTag() {
                        return this.tag;
                    }

                    public void setPsize(String value) {
                        this.psize = value;
                    }

                    public String getPsize() {
                        return this.psize;
                    }

                    public void setIco(String value) {
                        this.ico = value;
                    }

                    public String getIco() {
                        return this.ico;
                    }

                    public void setSite_id(String value) {
                        this.site_id = value;
                    }

                    public String getSite_id() {
                        return this.site_id;
                    }

                    public void setSeo_title(String value) {
                        this.seo_title = value;
                    }

                    public String getSeo_title() {
                        return this.seo_title;
                    }

                    public void setSublist(Object value) {
                        this.sublist = value;
                    }

                    public Object getSublist() {
                        return this.sublist;
                    }

                    public void setApp_title(String value) {
                        this.app_title = value;
                    }

                    public String getApp_title() {
                        return this.app_title;
                    }

                    public void setTpl_content(String value) {
                        this.tpl_content = value;
                    }

                    public String getTpl_content() {
                        return this.tpl_content;
                    }

                    public void setTaxis(String value) {
                        this.taxis = value;
                    }

                    public String getTaxis() {
                        return this.taxis;
                    }

                    public void setSeo_desc(String value) {
                        this.seo_desc = value;
                    }

                    public String getSeo_desc() {
                        return this.seo_desc;
                    }

                    public void setUrl(String value) {
                        this.url = value;
                    }

                    public String getUrl() {
                        return this.url;
                    }

                    public void setId(String value) {
                        this.id = value;
                    }

                    public String getId() {
                        return this.id;
                    }

                    public void setTitle(String value) {
                        this.title = value;
                    }

                    public String getTitle() {
                        return this.title;
                    }

                    public void setTpl_list(String value) {
                        this.tpl_list = value;
                    }

                    public String getTpl_list() {
                        return this.tpl_list;
                    }

                    public void setSeo_keywords(String value) {
                        this.seo_keywords = value;
                    }

                    public String getSeo_keywords() {
                        return this.seo_keywords;
                    }

                    public void setIdentifier(String value) {
                        this.identifier = value;
                    }

                    public String getIdentifier() {
                        return this.identifier;
                    }

                    public void setParent_id(String value) {
                        this.parent_id = value;
                    }

                    public String getParent_id() {
                        return this.parent_id;
                    }

                }

                private List<TRslist> rslist;
                private List<SSublist> sublist;	/*List<TSublist>*/

                public void setSublist(List<SSublist> value) {
                    this.sublist = value;
                }

                public List<SSublist> getSublist() {
                    return this.sublist;
                }

                private String vouch;	/*1*/
                private String status;	/*1*/
                private String tag;	/**/
                private String psize;	/*0*/
                private String ico;	/**/
                private String site_id;	/*1*/
                private String seo_title;	/**/
                private String app_title;	/*滚筒*/
                private String tpl_content;	/**/
                private String taxis;	/*1*/
                private String seo_desc;	/**/
                private String url;	/*http://huierpu.5kah.com/index.php?id=products&cate=guntong*/
                private String id;	/*603*/
                private String title;	/*滚筒*/
                private String tpl_list;	/**/
                private String seo_keywords;	/**/
                private String identifier;	/*guntong*/
                private String parent_id;	/*601*/

                public void setVouch(String value) {
                    this.vouch = value;
                }

                public String getVouch() {
                    return this.vouch;
                }

                public void setStatus(String value) {
                    this.status = value;
                }

                public String getStatus() {
                    return this.status;
                }

                public void setTag(String value) {
                    this.tag = value;
                }

                public String getTag() {
                    return this.tag;
                }

                public void setPsize(String value) {
                    this.psize = value;
                }

                public String getPsize() {
                    return this.psize;
                }

                public void setIco(String value) {
                    this.ico = value;
                }

                public String getIco() {
                    return this.ico;
                }

                public void setSite_id(String value) {
                    this.site_id = value;
                }

                public String getSite_id() {
                    return this.site_id;
                }

                public void setSeo_title(String value) {
                    this.seo_title = value;
                }

                public String getSeo_title() {
                    return this.seo_title;
                }

                public void setApp_title(String value) {
                    this.app_title = value;
                }

                public String getApp_title() {
                    return this.app_title;
                }

                public void setTpl_content(String value) {
                    this.tpl_content = value;
                }

                public String getTpl_content() {
                    return this.tpl_content;
                }

                public void setTaxis(String value) {
                    this.taxis = value;
                }

                public String getTaxis() {
                    return this.taxis;
                }

                public void setSeo_desc(String value) {
                    this.seo_desc = value;
                }

                public String getSeo_desc() {
                    return this.seo_desc;
                }

                public void setUrl(String value) {
                    this.url = value;
                }

                public String getUrl() {
                    return this.url;
                }

                public void setId(String value) {
                    this.id = value;
                }

                public String getId() {
                    return this.id;
                }

                public void setTitle(String value) {
                    this.title = value;
                }

                public String getTitle() {
                    return this.title;
                }

                public void setTpl_list(String value) {
                    this.tpl_list = value;
                }

                public String getTpl_list() {
                    return this.tpl_list;
                }

                public void setSeo_keywords(String value) {
                    this.seo_keywords = value;
                }

                public String getSeo_keywords() {
                    return this.seo_keywords;
                }

                public void setIdentifier(String value) {
                    this.identifier = value;
                }

                public String getIdentifier() {
                    return this.identifier;
                }

                public void setParent_id(String value) {
                    this.parent_id = value;
                }

                public String getParent_id() {
                    return this.parent_id;
                }

            }

            private List<TSublist> sublist;	/*List<TSublist>*/

            public void setSublist(List<TSublist> value) {
                this.sublist = value;
            }

            public List<TSublist> getSublist() {
                return this.sublist;
            }

            private String vouch;	/*1*/
            private String status;	/*1*/
            private String tag;	/**/
            private String psize;	/*0*/
            private String ico;	/**/
            private String site_id;	/*1*/
            private String seo_title;	/**/
            private String app_title;	/*三洋*/
            private String tpl_content;	/**/
            private String taxis;	/*1*/
            private String seo_desc;	/**/
            private String url;	/*http://huierpu.5kah.com/index.php?id=products&cate=sanyang*/
            private String id;	/*601*/
            private String title;	/*三洋*/
            private String tpl_list;	/**/
            private String seo_keywords;	/**/
            private String identifier;	/*sanyang*/
            private String parent_id;	/*594*/

            public void setVouch(String value) {
                this.vouch = value;
            }

            public String getVouch() {
                return this.vouch;
            }

            public void setStatus(String value) {
                this.status = value;
            }

            public String getStatus() {
                return this.status;
            }

            public void setTag(String value) {
                this.tag = value;
            }

            public String getTag() {
                return this.tag;
            }

            public void setPsize(String value) {
                this.psize = value;
            }

            public String getPsize() {
                return this.psize;
            }

            public void setIco(String value) {
                this.ico = value;
            }

            public String getIco() {
                return this.ico;
            }

            public void setSite_id(String value) {
                this.site_id = value;
            }

            public String getSite_id() {
                return this.site_id;
            }

            public void setSeo_title(String value) {
                this.seo_title = value;
            }

            public String getSeo_title() {
                return this.seo_title;
            }

            public void setApp_title(String value) {
                this.app_title = value;
            }

            public String getApp_title() {
                return this.app_title;
            }

            public void setTpl_content(String value) {
                this.tpl_content = value;
            }

            public String getTpl_content() {
                return this.tpl_content;
            }

            public void setTaxis(String value) {
                this.taxis = value;
            }

            public String getTaxis() {
                return this.taxis;
            }

            public void setSeo_desc(String value) {
                this.seo_desc = value;
            }

            public String getSeo_desc() {
                return this.seo_desc;
            }

            public void setUrl(String value) {
                this.url = value;
            }

            public String getUrl() {
                return this.url;
            }

            public void setId(String value) {
                this.id = value;
            }

            public String getId() {
                return this.id;
            }

            public void setTitle(String value) {
                this.title = value;
            }

            public String getTitle() {
                return this.title;
            }

            public void setTpl_list(String value) {
                this.tpl_list = value;
            }

            public String getTpl_list() {
                return this.tpl_list;
            }

            public void setSeo_keywords(String value) {
                this.seo_keywords = value;
            }

            public String getSeo_keywords() {
                return this.seo_keywords;
            }

            public void setIdentifier(String value) {
                this.identifier = value;
            }

            public String getIdentifier() {
                return this.identifier;
            }

            public void setParent_id(String value) {
                this.parent_id = value;
            }

            public String getParent_id() {
                return this.parent_id;
            }

        }

        private List<OSublist> sublist;	/*List<TSublist>*/

        public void setSublist(List<OSublist> value) {
            this.sublist = value;
        }

        public List<OSublist> getSublist() {
            return this.sublist;
        }

        private String vouch;	/*1*/
        private String status;	/*1*/
        private String tag;	/**/
        private String psize;	/*0*/
        private String ico;	/**/
        private String site_id;	/*1*/
        private String seo_title;	/**/
        private String app_title;	/*洗衣机*/
        private String tpl_content;	/**/
        private String taxis;	/*15*/
        private String seo_desc;	/**/
        private String url;	/*http://huierpu.5kah.com/index.php?id=products&cate=xiyiji*/
        private String id;	/*594*/
        private String title;	/*洗衣机*/
        private String tpl_list;	/**/
        private String seo_keywords;	/**/
        private String identifier;	/*xiyiji*/
        private String parent_id;	/*154*/

        public void setVouch(String value) {
            this.vouch = value;
        }

        public String getVouch() {
            return this.vouch;
        }

        public void setStatus(String value) {
            this.status = value;
        }

        public String getStatus() {
            return this.status;
        }

        public void setTag(String value) {
            this.tag = value;
        }

        public String getTag() {
            return this.tag;
        }

        public void setPsize(String value) {
            this.psize = value;
        }

        public String getPsize() {
            return this.psize;
        }

        public void setIco(String value) {
            this.ico = value;
        }

        public String getIco() {
            return this.ico;
        }

        public void setSite_id(String value) {
            this.site_id = value;
        }

        public String getSite_id() {
            return this.site_id;
        }

        public void setSeo_title(String value) {
            this.seo_title = value;
        }

        public String getSeo_title() {
            return this.seo_title;
        }

        public void setApp_title(String value) {
            this.app_title = value;
        }

        public String getApp_title() {
            return this.app_title;
        }

        public void setTpl_content(String value) {
            this.tpl_content = value;
        }

        public String getTpl_content() {
            return this.tpl_content;
        }

        public void setTaxis(String value) {
            this.taxis = value;
        }

        public String getTaxis() {
            return this.taxis;
        }

        public void setSeo_desc(String value) {
            this.seo_desc = value;
        }

        public String getSeo_desc() {
            return this.seo_desc;
        }

        public void setUrl(String value) {
            this.url = value;
        }

        public String getUrl() {
            return this.url;
        }

        public void setId(String value) {
            this.id = value;
        }

        public String getId() {
            return this.id;
        }

        public void setTitle(String value) {
            this.title = value;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTpl_list(String value) {
            this.tpl_list = value;
        }

        public String getTpl_list() {
            return this.tpl_list;
        }

        public void setSeo_keywords(String value) {
            this.seo_keywords = value;
        }

        public String getSeo_keywords() {
            return this.seo_keywords;
        }

        public void setIdentifier(String value) {
            this.identifier = value;
        }

        public String getIdentifier() {
            return this.identifier;
        }

        public void setParent_id(String value) {
            this.parent_id = value;
        }

        public String getParent_id() {
            return this.parent_id;
        }

    }

    private List<TTree> tree;	/*List<TTree>*/

    public void setTree(List<TTree> value) {
        this.tree = value;
    }

    public List<TTree> getTree() {
        return this.tree;
    }

    private TCate cate;	/*TCate*/

    public void setCate(TCate value) {
        this.cate = value;
    }

    public TCate getCate() {
        return this.cate;
    }

}
