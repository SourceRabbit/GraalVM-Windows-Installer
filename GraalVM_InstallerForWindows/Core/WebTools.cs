using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace GraalVM_InstallerForWindows.Core
{
    class WebTools
    {
        public WebTools()
        {

        }


        /// <summary>
        /// Get web response from URL
        /// </summary>
        /// <param name="url">The url to get response from.</param>
        /// <returns>Text, propably HTML...</returns>
        public static string GetWebResponse(string url)
        {
            ServicePointManager.Expect100Continue = true;
            ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls
                   | SecurityProtocolType.Tls11
                   | SecurityProtocolType.Tls12
                   | SecurityProtocolType.Ssl3;

            WebRequest request = WebRequest.Create(url);

            WebResponse response = request.GetResponse();

            Stream dataStream = response.GetResponseStream();

            StreamReader reader = new StreamReader(dataStream);

           return reader.ReadToEnd();

        }



    } 
}
