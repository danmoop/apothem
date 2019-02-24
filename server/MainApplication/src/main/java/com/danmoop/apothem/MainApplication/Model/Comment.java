package com.danmoop.apothem.MainApplication.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Comment
{
    private String author;
    private String message;
    private String createdOn;

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void generateTime()
    {
        LocalDateTime localDateTime = LocalDateTime.now();

        this.createdOn = localDateTime.getDayOfMonth()
                + "." + localDateTime.getMonthValue()
                + "." + localDateTime.getYear()
                + " " + localDateTime.getHour()
                + ":" + localDateTime.getMinute()
                + ":" + localDateTime.getSecond();
    }
}