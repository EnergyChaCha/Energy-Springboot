package com.chacha.energy.domain.member.entity;

public enum Role {
    ADMIN("ADMIN"),
    USER("USER");

    String name;


    Role(String name) {
        this.name = name;
    }
}
