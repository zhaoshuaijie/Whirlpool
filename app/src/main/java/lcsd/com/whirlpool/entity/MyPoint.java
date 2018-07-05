package lcsd.com.whirlpool.entity;

/**
 * Created by Administrator on 2017/9/30.
 */
public class MyPoint {
    private Integer val;	/*1*/
    private String id;	/*237459*/
    private String dateline;	/*1506669383*/
    private String note;	/*阅读#3459*/

    public void setVal(Integer value) {
        this.val = value;
    }

    public Integer getVal() {
        return this.val;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getId() {
        return this.id;
    }

    public void setDateline(String value) {
        this.dateline = value;
    }

    public String getDateline() {
        return this.dateline;
    }

    public void setNote(String value) {
        this.note = value;
    }

    public String getNote() {
        return this.note;
    }
}
