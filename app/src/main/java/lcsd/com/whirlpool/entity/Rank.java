package lcsd.com.whirlpool.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/27.
 */
public class Rank {
    public static class TRslist{

        private	String	id;	/*33*/
        private	String	point;	/*134*/
        private	Integer	rank;	/*1*/
        private	String	nickname;	/*ghh*/
        private	String	avatar;	/*res/201707/27/1b3f346c8c69f694.png*/
        private	String	fullname;	/*找找你*/

        public void setId(String value){
            this.id = value;
        }
        public String getId(){
            return this.id;
        }

        public void setPoint(String value){
            this.point = value;
        }
        public String getPoint(){
            return this.point;
        }

        public void setRank(Integer value){
            this.rank = value;
        }
        public Integer getRank(){
            return this.rank;
        }

        public void setNickname(String value){
            this.nickname = value;
        }
        public String getNickname(){
            return this.nickname;
        }

        public void setAvatar(String value){
            this.avatar = value;
        }
        public String getAvatar(){
            return this.avatar;
        }

        public void setFullname(String value){
            this.fullname = value;
        }
        public String getFullname(){
            return this.fullname;
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
    private	Integer	psize;	/*10*/
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
