package graalvminstallerforwindows.Core.Threading;

/*
    Copyright (C) 2024 Nikolaos Siatras
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    This software is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program.
 */
/**
 *
 * @author Nikos Siatras
 */
public class ManualResetEvent
{

    private boolean fInitialState;

    public ManualResetEvent(boolean initialState)
    {
        fInitialState = initialState;
    }

    public synchronized void Reset()
    {
        fInitialState = false;
    }

    public synchronized void WaitOne()
    {
        while (!fInitialState)
        {
            try
            {
                wait();
            }
            catch (InterruptedException ex)
            {
            }
        }
    }

    public synchronized void WaitOne(long milliseconds) throws InterruptedException
    {
        if (!fInitialState)
        {
            wait(milliseconds);
        }
    }

    public synchronized void WaitWithoutException(long milliseconds)
    {
        try
        {
            if (!fInitialState)
            {
                wait(milliseconds);
            }
        }
        catch (Exception ex)
        {

        }
    }

    public synchronized void Set()
    {
        fInitialState = true;
        notify();
    }

    public boolean getState()
    {
        return fInitialState;
    }
}
