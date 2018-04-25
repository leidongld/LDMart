package com.example.leidong.ldmart.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 卖家Bean
 * @author Lei dong
 */

@Entity
public class Seller {
    @Id(autoincrement = true)
    private Long id;
    private String username;
    private String password;
    private String address;
    private String phone;

    @Generated(hash = 1557179703)
    public Seller(Long id, String username, String password, String address,
            String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.address = address;
        this.phone = phone;
    }

    @Generated(hash = 203966309)
    public Seller() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
