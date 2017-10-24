package org.zackratos.appstore.result;

/**
 *
 * Created by xiboke on 2017/10/24.
 */

public class UserInfo {

    /**
     * id : 277165
     * email : 869649338@qq.com
     * username : Zackratos
     * mobi : 13862063070
     */

    private int id;
    private String email;
    private String username;
    private String mobi;

    private UserInfo(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.username = builder.username;
        this.mobi = builder.mobi;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getMobi() {
        return mobi;
    }

    public static class Builder {
        private int id;
        private String email;
        private String username;
        private String mobi;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder userName(String username) {
            this.username = username;
            return this;
        }

        public Builder mobi(String mobi) {
            this.mobi = mobi;
            return this;
        }

        public UserInfo build() {
            return new UserInfo(this);
        }
    }
}
