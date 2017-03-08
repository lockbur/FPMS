
package com.forms.prms.web.task.timer;

import java.util.Date;

public class MinuteIterator
        implements ScheduleIterator
{
    long                   totalseconde = 0;

    public MinuteIterator (int minute)
    {
        totalseconde = minute * 60 * 1000;
    }

    public Date next()
    {
        Date now =new Date();
        Date date =new Date(now.getTime()+totalseconde);
        return date;
    }
}
