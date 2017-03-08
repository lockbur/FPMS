
package com.forms.prms.web.task.timer;

import java.util.Date;

public class SecondIterator
        implements ScheduleIterator
{
    long                   totalseconde = 0;

    public SecondIterator (int second)
    {
        totalseconde = second * 1000;
    }

    public Date next()
    {
        Date now =new Date();
        Date date =new Date(now.getTime()+totalseconde);
        return date;
    }
}
