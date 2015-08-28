package by.hut.flat.calendar.widget.datetime.labeler;

import java.util.Calendar;

import by.hut.flat.calendar.dialog.date.TimeObject;


/**
 * A Labeler that displays months
 */
public class MonthLabeler extends Labeler {
    private final String mFormatString;

    public MonthLabeler(String formatString) {
        super(180, 60);
        mFormatString = formatString;
    }

    @Override
    public TimeObject add(long time, int val) {
        return timeObjectfromCalendar(Util.addMonths(time, val));
    }

    @Override
    protected TimeObject timeObjectfromCalendar(Calendar c) {
        return Util.getMonth(c, mFormatString);
    }
}