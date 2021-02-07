package com.AlbertAbuav;

import com.AlbertAbuav.utils.DateUtils;

import java.time.LocalDate;
import java.util.Date;

public class TestDate {

    public static void main(String[] args) {

        // Hardcoded date
        Date date1 = DateUtils.getDate(25, 3, 2003);
        System.out.println(DateUtils.beautifyDate(date1));

        // getting the local date and it will be reset every time the program will turn on
        Date date2 = DateUtils.javaDateFromLocalDate(LocalDate.now());
        System.out.println(DateUtils.beautifyDate(date2));

        // getting the local date plus a week and it will be reset every time the program will turn on
        Date date3 = DateUtils.javaDateFromLocalDate(LocalDate.now().plusWeeks(1));
        System.out.println(DateUtils.beautifyDate(date3));
    }
}
