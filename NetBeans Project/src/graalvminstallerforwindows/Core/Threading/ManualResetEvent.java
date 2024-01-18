package graalvminstallerforwindows.Core.Threading;

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
