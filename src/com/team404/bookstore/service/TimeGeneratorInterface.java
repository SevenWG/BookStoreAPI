package com.team404.bookstore.service;

import java.sql.Timestamp;

public interface TimeGeneratorInterface {
    public String GetOrderGenerationTime();
    public Timestamp GetTimestampValue();
}
