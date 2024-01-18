package graalvminstallerforwindows.Core;

import graalvminstallerforwindows.Core.Utilities.HTTPRequestData;
import java.util.HashMap;

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
public class DownloadsManager
{

    private static final HashMap<String, String> fDownloads;

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
