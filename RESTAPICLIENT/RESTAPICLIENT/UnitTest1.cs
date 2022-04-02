using Microsoft.VisualStudio.TestTools.UnitTesting;
using RestSharp;
using System.Net;

namespace RESTAPICLIENT
{
    [TestClass]
    public class UnitTest1
    {
        [TestMethod]
        public void TestMethod1()
        {
            RestClient client = new RestClient("http://localhost:8888/beers/HAOS");
            RestRequest request = new RestRequest(Method.GET);
            request.AddHeader("Accept", "text/html, application/xhtml+xml, application/xml;q=0.9, image/webp, */*;q=0.8");

            IRestResponse response = client.Execute(request);


           Assert.AreEqual(response.StatusCode, HttpStatusCode.NotAcceptable);
        }
    }
}
