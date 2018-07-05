package lcsd.com.whirlpool.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/27.
 */
public class SjKaoshi {

    public static class TTest{

        public static class TOption{

            private	String	title;	/*银色箱体 */
            private	String	no;	/*A*/

            public void setTitle(String value){
                this.title = value;
            }
            public String getTitle(){
                return this.title;
            }

            public void setNo(String value){
                this.no = value;
            }
            public String getNo(){
                return this.no;
            }

        }
        private	List<TOption>	option;	/*List<TOption>*/
        public void setOption(List<TOption> value){
            this.option = value;
        }
        public List<TOption> getOption(){
            return this.option;
        }

        private	String	id;	/*2581*/
        private	String	title;	/*帝度DG-F90322BS命名规则S表示（ ）。(单选)*/
        private	String	keys;	/*A*/
        private	String	thumb;	/**/

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

        public void setKeys(String value){
            this.keys = value;
        }
        public String getKeys(){
            return this.keys;
        }

        public void setThumb(String value){
            this.thumb = value;
        }
        public String getThumb(){
            return this.thumb;
        }

    }
    private List<TTest> test;	/*List<TTest>*/
    public void setTest(List<TTest> value){
        this.test = value;
    }
    public List<TTest> getTest(){
        return this.test;
    }

    private	String	point;	/*0.2*/

    public void setPoint(String value){
        this.point = value;
    }
    public String getPoint(){
        return this.point;
    }
}
