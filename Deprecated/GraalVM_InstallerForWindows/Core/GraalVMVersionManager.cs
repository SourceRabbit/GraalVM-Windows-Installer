/*
Copyright (C) 2021  Nikos Siatras
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
SourceRabbit GCode Sender is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program.
*/

using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GraalVM_InstallerForWindows.Core
{
    class GraalVMVersionManager
    {

        private static Dictionary<string,string> fGraalVMVersions = new Dictionary<string,string>();
        

        public GraalVMVersionManager()
        {

        }



        /// <summary>
        /// Load GraalVM Version from the Config.fDownloadsListPath
        /// </summary>
        private static void GetGraalVMVersionsFromGitHub()
        {
            string response = WebTools.GetWebResponse(Config.fDownloadsListPath);
            string[] lines = response.Split('\n');
            foreach(string line in lines)
            {
                if (line.Contains("|"))
                {
                    string[] parts = line.Split('|');

                    string versionName = parts[0];
                    string url = parts[1];

                    if (!fGraalVMVersions.ContainsKey(versionName))
                    {
                        fGraalVMVersions.Add(versionName, url);
                    }
                }
            }
        }




        public static Dictionary<string,string> GraalVMVersions
        {
            get
            {

                if (fGraalVMVersions.Count == 0)
                {
                    GetGraalVMVersionsFromGitHub();
                }

                return fGraalVMVersions;
            }
        }
    }
}
