package lcsd.com.whirlpool.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/7.
 */
public class UserInfo implements Serializable{

    private	String	position;	/*总经理*/
    private	String	honor;	/*初入江湖*/
    private	String	nickname;	/*帅气*/
    private	String	department;	/*北京-洗衣机*/
    private	String	department_id;	/*1*/
    private	String	avatar;	/*res/201707/24/5882b91397d37d12.png*/
    private	String	point;	/*110*/
    private	Integer	rank;	/*1*/
    private	String	position_id;	/*1*/
    private	String	gender;	/*1*/
    private	String	user_id;	/*33*/
    private	String	special;	/*啦啊*/
    private	String	fullname;	/*找找你*/
    private	String	mobile;	/*18110574434*/
    private	String	group;	/*员工*/
    private	String	group_id;	/*2*/

    public void setPosition(String value){
        this.position = value;
    }
    public String getPosition(){
        return this.position;
    }

    public void setHonor(String value){
        this.honor = value;
    }
    public String getHonor(){
        return this.honor;
    }

    public void setNickname(String value){
        this.nickname = value;
    }
    public String getNickname(){
        return this.nickname;
    }

    public void setDepartment(String value){
        this.department = value;
    }
    public String getDepartment(){
        return this.department;
    }

    public void setDepartment_id(String value){
        this.department_id = value;
    }
    public String getDepartment_id(){
        return this.department_id;
    }

    public void setAvatar(String value){
        this.avatar = value;
    }
    public String getAvatar(){
        return this.avatar;
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

    public void setPosition_id(String value){
        this.position_id = value;
    }
    public String getPosition_id(){
        return this.position_id;
    }

    public void setGender(String value){
        this.gender = value;
    }
    public String getGender(){
        return this.gender;
    }

    public void setUser_id(String value){
        this.user_id = value;
    }
    public String getUser_id(){
        return this.user_id;
    }

    public void setSpecial(String value){
        this.special = value;
    }
    public String getSpecial(){
        return this.special;
    }

    public void setFullname(String value){
        this.fullname = value;
    }
    public String getFullname(){
        return this.fullname;
    }

    public void setMobile(String value){
        this.mobile = value;
    }
    public String getMobile(){
        return this.mobile;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public static class TPopedom implements Serializable{

        private	String	skill;	/*销售技巧*/
        private	String	news;	/*新闻资讯*/
        private	String	train;	/*培训专题*/
        private	String	new_products;	/*新品快讯*/
        private	String	team;	/*团队活动*/
        private	String	products;	/*产品宝典*/
        private	String	exam;	/*考试中心*/
        private	String	live;	/*直播平台*/

        public void setSkill(String value){
            this.skill = value;
        }
        public String getSkill(){
            return this.skill;
        }

        public void setNews(String value){
            this.news = value;
        }
        public String getNews(){
            return this.news;
        }

        public void setTrain(String value){
            this.train = value;
        }
        public String getTrain(){
            return this.train;
        }

        public void setNew_products(String value){
            this.new_products = value;
        }
        public String getNew_products(){
            return this.new_products;
        }

        public void setTeam(String value){
            this.team = value;
        }
        public String getTeam(){
            return this.team;
        }

        public void setProducts(String value){
            this.products = value;
        }
        public String getProducts(){
            return this.products;
        }

        public void setExam(String value){
            this.exam = value;
        }
        public String getExam(){
            return this.exam;
        }

        public void setLive(String value){
            this.live = value;
        }
        public String getLive(){
            return this.live;
        }

    }
    private	TPopedom	popedom;	/*TPopedom*/
    public void setPopedom(TPopedom value){
        this.popedom = value;
    }
    public TPopedom getPopedom(){
        return this.popedom;
    }

}
