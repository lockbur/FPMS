
package com.forms.prms.web.task.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Scheduler
{
    // 内部类创建任务
    class SchedulerTimerTask
            extends TimerTask
    {
        // 定时调度任务
        private SchedulerTask    schedulerTask;
        // 定时调度任务开始时间类
        private ScheduleIterator iterator;

        public SchedulerTimerTask (SchedulerTask schedulerTask,
                ScheduleIterator iterator)
        {
            this.schedulerTask = schedulerTask;
            this.iterator = iterator;
        }

        public void run()
        {
            // 调用任务
            schedulerTask.run();
            reschedule(schedulerTask, iterator);
        }
    }

    // 创建一Timer类
    private final Timer timer = new Timer();

    public Scheduler ()
    {
        
    }

    // 销毁Timer类
    public void cancel()
    {
        if (timer != null)
            timer.cancel();
    }

    //启动timer
    public void schedule(SchedulerTask schedulerTask, ScheduleIterator iterator)
    {

        Date time = iterator.next();
        if (time == null)
        {
            schedulerTask.cancel();
        }
        else
        {
            synchronized (schedulerTask.lock)
            {
                if (schedulerTask.state != SchedulerTask.VIRGIN)
                {
                    throw new IllegalStateException(
                            "Task already scheduled or cancelled");
                }
                schedulerTask.state = SchedulerTask.SCHEDULED;
                schedulerTask.timerTask = new SchedulerTimerTask(schedulerTask,
                        iterator);
                //启动任务
                timer.schedule(schedulerTask.timerTask, time);
            }
        }
    }
//重新设置任务执行时间
    private void reschedule(SchedulerTask schedulerTask,
            ScheduleIterator iterator)
    {
        

        Date time = iterator.next();
        if (time == null)
        {
            schedulerTask.cancel();
        }
        else
        {
            synchronized (schedulerTask.lock)
            {
                if (schedulerTask.state != SchedulerTask.CANCELLED)
                {

                    schedulerTask.timerTask = new SchedulerTimerTask(
                            schedulerTask, iterator);
                    timer.schedule(schedulerTask.timerTask, time);
                }
            }
        }
    }

}
