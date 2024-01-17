package graalvminstallerforwindows.Core;

import graalvminstallerforwindows.Core.Utilities.HTTPRequestData;
import java.util.HashMap;

/**
 *
 * @author Nikos Siatras
 */
public class GraalVMDownloadsManager
{

    private static HashMap<String, String> fDownloads;

    static
    {
        fDownloads = new HashMap<>();
    }

    /**
     * Returns a HashMap with all available GraalVM downloads.
     *
     * @return
     */
    public static HashMap<String, String> getAvailableDownloads()
    {
        if (fDownloads.isEmpty())
        {
            String data = HTTPRequestData.GetHTML(Settings.fDownloadsListFile);
            String[] lines = data.split("\n");
            for (String line : lines)
            {
                String[] parts = line.trim().split("\\|");
                if (parts.length > 1)
                {
                    fDownloads.put(parts[0], parts[1]);
                }
            }
        }

        return fDownloads;
    }

}
