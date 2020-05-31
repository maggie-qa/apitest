package apitest.db;

import java.util.Objects;

public class DbUser {

    /**
     * 用户ID
     */
    private String uid;
    /**
     * 用户名
     */
    private String loginname;
    /**
     * 密码
     */
    private String loginpass;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getLoginpass() {
        return loginpass;
    }

    public void setLoginpass(String loginpass) {
        this.loginpass = loginpass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DbUser)) return false;
        DbUser dbUser = (DbUser) o;
        return getUid() == dbUser.getUid() &&
                Objects.equals(getLoginname(), dbUser.getLoginname()) &&
                Objects.equals(getLoginpass(), dbUser.getLoginpass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUid(), getLoginname(), getLoginpass());
    }

    @Override
    public String toString() {
        return "DbUser{" +
                "uid=" + uid +
                ", loginname='" + loginname + '\'' +
                ", loginpass='" + loginpass + '\'' +
                '}';
    }
}
