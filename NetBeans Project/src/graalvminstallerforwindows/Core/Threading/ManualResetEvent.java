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

    private final Object fLock = new Object();
    private volatile boolean fIsOpen;

    public ManualResetEvent(boolean initialState)
    {
        fIsOpen = initialState;
    }

    public void Reset()
    {
        fIsOpen = false;
    }

    public void WaitOne()
    {
        synchronized (fLock)
        {
            while (!fIsOpen)
            {
                try
                {
                    fLock.wait();
                }
                catch (InterruptedException ex)
                {
                }
            }
        }
    }

    public boolean WaitOne(long milliseconds) throws InterruptedException
    {
        synchronized (fLock)
        {
            if (fIsOpen)
            {
                return true;
            }

            fLock.wait(milliseconds);
            return fIsOpen;
        }
    }

    public void Set()
    {
        synchronized (fLock)
        {
            fIsOpen = true;
            fLock.notifyAll();
        }
    }

    public boolean getState()
    {
        return fIsOpen;
    }
}
