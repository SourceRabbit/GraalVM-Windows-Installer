package graalvminstallerforwindows.Core.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

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
public class HTTPRequestData
{

    public static String GetHTML(String url)
    {
        String result = "";
        try
        {
            int chr;

            URL u = new URL(url);
            URLConnection hc = u.openConnection();

            hc.setRequestProperty("Accept-Charset", "UTF-8");
            hc.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

            BufferedReader in = new BufferedReader(new InputStreamReader(hc.getInputStream(), "utf-8"));
            while ((chr = in.read()) != -1)
            {
                result += (char) chr;
            }
        }
        catch (IOException ex)
        {

        }

        return result;
    }
}
