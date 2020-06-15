package com.example.demo.board.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String zip;
    private String address;
    private String state;
    private String city;
}
