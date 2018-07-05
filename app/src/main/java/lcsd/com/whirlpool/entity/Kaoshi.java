package lcsd.com.whirlpool.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/11.
 */
public class Kaoshi implements Serializable {
    //题目
    private String timu;
    //选项
    private String A;
    private String B;
    private String C;
    private String D;
    private String E;
    private String F;
    //正确答案
    private String correct;
    //选择的答案
    private String check;
    //题目图片
    private String img;
    private String id;
    private int type;           //1：为单选 2：为多选

    public String getTimu() {
        return timu;
    }

    public void setTimu(String timu) {
        this.timu = timu;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getE() {
        return E;
    }

    public void setE(String e) {
        E = e;
    }

    public String getF() {
        return F;
    }

    public void setF(String f) {
        F = f;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
