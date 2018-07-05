package lcsd.com.whirlpool.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/7.
 */
public class Register {

    public static class TContent {

        public static class TPosition {

            private String id;	/*1*/
            private String title;	/*总经理*/

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

        private List<TPosition> position;	/*List<TPosition>*/

        public void setPosition(List<TPosition> value) {
            this.position = value;
        }

        public List<TPosition> getPosition() {
            return this.position;
        }

        public static class TDepartment {

            private String id;	/*1*/
            private String title;	/*分公司1*/
            private String status;	/*1*/
            private String tel;	/*1234567*/
            private String longitude;	/*0*/
            private String addr;	/*分公司1地址*/
            private String latitude;	/*0*/
            private String zgxm;	/*张三*/
            private String taxis;	/*1*/
            private String note;	/**/

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

            public void setStatus(String value) {
                this.status = value;
            }

            public String getStatus() {
                return this.status;
            }

            public void setTel(String value) {
                this.tel = value;
            }

            public String getTel() {
                return this.tel;
            }

            public void setLongitude(String value) {
                this.longitude = value;
            }

            public String getLongitude() {
                return this.longitude;
            }

            public void setAddr(String value) {
                this.addr = value;
            }

            public String getAddr() {
                return this.addr;
            }

            public void setLatitude(String value) {
                this.latitude = value;
            }

            public String getLatitude() {
                return this.latitude;
            }

            public void setZgxm(String value) {
                this.zgxm = value;
            }

            public String getZgxm() {
                return this.zgxm;
            }

            public void setTaxis(String value) {
                this.taxis = value;
            }

            public String getTaxis() {
                return this.taxis;
            }

            public void setNote(String value) {
                this.note = value;
            }

            public String getNote() {
                return this.note;
            }

        }

        private List<TDepartment> department;	/*List<TDepartment>*/

        public void setDepartment(List<TDepartment> value) {
            this.department = value;
        }

        public List<TDepartment> getDepartment() {
            return this.department;
        }


    }

    private TContent content;	/*TContent*/
    private String status;	/*ok*/

    public void setContent(TContent value) {
        this.content = value;
    }

    public TContent getContent() {
        return this.content;
    }

    public void setStatus(String value) {
        this.status = value;
    }

    public String getStatus() {
        return this.status;
    }
}
