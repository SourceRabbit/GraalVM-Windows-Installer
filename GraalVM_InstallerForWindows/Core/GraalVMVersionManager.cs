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
