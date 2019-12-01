package com.qr.core.rxcache;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String userName = "QQ";
    public String password = "QQ12345";
    public int age = 15;
    public double height = 5.5;
    public float width = 200;
    public String msg = "12345";
    public List<Role> roles = new ArrayList<>();

    public User() {
        roles.add(new Role());
    }
}


