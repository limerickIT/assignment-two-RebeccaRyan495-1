using Microsoft.VisualStudio.TestTools.UnitTesting;
using RestSharp;
using System.Net;

namespace RESTAPICLIENT
{
    [TestClass]
    public class UnitTest1
    {
        [TestMethod]
        public void TestGETMethod()
        {
            RestClient client = new RestClient("http://localhost:8888/beers/HAOS");
            RestRequest request = new RestRequest(Method.GET);
            request.AddHeader("Accept", "text/html, application/xhtml+xml, application/xml;q=0.9, image/webp, */*;q=0.8");

            IRestResponse response = client.Execute(request);


           Assert.AreEqual(response.StatusCode, HttpStatusCode.OK);
        }

        [TestMethod]
        public void TestPOSTMethod()
        {
            RestClient client = new RestClient("http://localhost:8888/beers/");
            RestRequest request = new RestRequest(Method.POST);
            request.AddHeader("Accept", "text/html, application/xhtml+xml, application/xml;q=0.9, image/webp, */*;q=0.8");

            var body = new
            {
                id = "1",
                brewery_id = "812",
                name = "test from c# client",
                cat_id = "8",
                style_id = "116",
                abv="4.6",
                ibu="0.0",
                srm="0.0",
                description="test from client",
                add_user="0",
                last_mod= "2019-03-09T13:16:58.000+00:00",
                image="1.jpg",
                buy_price="13.93",
                sell_price = "13.93"
            };
            request.RequestFormat = DataFormat.Json;
            request.AddJsonBody(body);
            IRestResponse response = client.Execute(request);


            Assert.AreEqual(response.StatusCode, HttpStatusCode.Created);
        }
    }
}
