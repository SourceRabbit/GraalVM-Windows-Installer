package graalvminstallerforwindows.Core.Utilities;

import java.util.ArrayList;
import java.util.stream.Collectors;


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
public class EnvironmentVariablesManager
{

    public EnvironmentVariablesManager()
    {

    }

    public static void SetEnvironmentVariable(String variable, String value) throws Exception
    {
        final String dosCommand = "setx /M " + variable + " \"" + value + "\"";
        DosPromt.ExecuteDosPromtAndWaitToFinish(dosCommand);
    }

    public static void AddEnvironmentVariable(String variable, String value) throws Exception
    {
        final String[] currentValues = DosPromt.ExecuteDOSPromtAndGetResult("echo %" + variable + "%").split(";");

        final String appPath = System.getProperty("user.dir");
        ArrayList<String> finalValues = new ArrayList<>();
        for (String s : currentValues)
        {
            if (!s.equals("null") && !s.equals("") && !s.equals(value) && !finalValues.contains(s) && !s.startsWith(appPath) && !s.contains("\""))
            {
                finalValues.add(s);
            }
        }
        finalValues.add(value);

        // CAUTION --  ADD ';' at the end!
        final String newValuesString = finalValues.stream().collect(Collectors.joining(";")) + ";";

        final String dosCommand = "setx /M " + variable + " \"" + newValuesString + "\"";
        DosPromt.ExecuteDosPromtAndWaitToFinish(dosCommand);

    }

}
