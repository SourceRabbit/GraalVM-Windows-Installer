package graalvminstallerforwindows.Core.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Nikos Siatras
 */
public class DosPromt
{

    public static void ExecuteDOSPromt(String command) throws IOException
    {
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true)
        {
            line = r.readLine();
            if (line == null)
            {
                break;
            }
            System.out.println(line);
        }
    }
}
