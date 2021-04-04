using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GraalVM_InstallerForWindows.Core
{
    class GrallVMVersionManager
    {

        private Hashtable fGraalVMVersions = new Hashtable();


        public GrallVMVersionManager()
        {

        }




        private void GetGraalVMVersionsFromGitHub()
        {
          

        }




        public Hashtable GraalVMVersions
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
