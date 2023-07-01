package ru.kostin.financekeeper.utils.format;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DateFormatterImpl implements DateFormatter {
    private static final String DATE_PATTERN = "dd-MM-yyyy hh:mm";

    @Override
    public Date format(String rawDate) throws ParseException {
        if (rawDate == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
        return new Timestamp(format.parse(rawDate).getTime());
    }
}
