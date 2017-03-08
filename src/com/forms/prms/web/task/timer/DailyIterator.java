package com.forms.prms.web.task.timer;

import java.util.Calendar;
import java.util.Date;

public class DailyIterator
        implements ScheduleIterator
{
    private final Calendar calendar = Calendar.getInstance();

    public DailyIterator(int hourOfDay, int minute, int second)
    {
        this(hourOfDay, minute, second, new Date());
    }

    public DailyIterator(int hourOfDay, int minute, int second, Date date)
    {
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0);
        if (!calendar.getTime().before(date))
        {
            calendar.add(Calendar.DATE, -1);
        }
    }

    public Date next()
    {
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }
}
