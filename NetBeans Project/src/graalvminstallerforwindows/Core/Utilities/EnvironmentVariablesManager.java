package graalvminstallerforwindows.Core.Utilities;

import java.util.LinkedList;
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
        final String dosCommand = "setx " + variable + " \"" + value + ";" + "\"";
        DosPromt.ExecuteDOSPromt(dosCommand);
    }

    public static void AddEnvironmentVariable(String variable, String value) throws Exception
    {
        // Read current values of "Variable"
        final String currentVariableValuesString = DosPromt.ExecuteDOSPromt("echo %" + variable + "%");

        // Create an array of the values without duplicates
        final String[] valuesArray = currentVariableValuesString.split(";");
        final LinkedList<String> nonDuplicateValues = new LinkedList<>();
        for (String v : valuesArray)
        {
            if (!nonDuplicateValues.contains(v) && !v.equals(""))
            {
                nonDuplicateValues.add(v);
            }
        }
        // Add the new value to the array
        if (!nonDuplicateValues.contains(value) && !value.equals(""))
        {
            nonDuplicateValues.add(value);
        }

        // Call the set command
        final String finalValues = nonDuplicateValues.stream().collect(Collectors.joining(";"));
        EnvironmentVariablesManager.SetEnvironmentVariable(variable, finalValues);
    }

}
