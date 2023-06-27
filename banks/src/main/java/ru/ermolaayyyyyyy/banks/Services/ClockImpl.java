package ru.ermolaayyyyyyy.banks.Services;

import lombok.Getter;
import ru.ermolaayyyyyyy.banks.Interfaces.Clock;

import java.util.Timer;
import java.util.TimerTask;

public class ClockImpl implements Clock {
    private final long day = 1000 * 60 * 60 * 24;
    private final long month = day * 30;
    private int invocationNumber = 0;
    private TimerTask timerTask1;
    private TimerTask timerTask2;
    @Getter
    public Timer timer1;
    public Timer timer2;
    public ClockImpl(TimerTask timerTask1, TimerTask timerTask2)
    {
        timer1 = new Timer();
        timer1.schedule(timerTask1, day, day);
        this.timerTask1 = timerTask1;

        timer1 = new Timer();
        timer1.schedule(timerTask2, month, month);
        this.timerTask2 = timerTask2;
    }
    @Override
    public void accelerate(int daysNumber) {
        for (int i = 0; i < daysNumber; i++)
        {
            timerTask1.run();
            update();
        }
    }

    private void update()
    {
        invocationNumber += 1;
        if (invocationNumber == 30)
        {
            invocationNumber = 0;
            timerTask2.run();
        }
    }
}
