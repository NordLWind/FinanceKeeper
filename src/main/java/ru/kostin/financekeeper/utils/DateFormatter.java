package ru.kostin.financekeeper.utils;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
public interface DateFormatter {
    Date format(String rawDate) throws ParseException;
}
