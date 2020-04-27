using logicProject.Models.DBContext;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Data.Entity;
using logicProject.Models.EF;
namespace logicProject.Controllers
{
    public class RequestController : Controller
    {
        private LogicEntities db = new LogicEntities();
        // GET: Request
        public ActionResult OrderStatus()
        {
            var request = db.Request.Include(d => d.Department);
            return View(request.ToList());
        }
        public ActionResult RequestForm()
        {

            return View();
        }

        public ActionResult ViewRequestDetails(int requestId)
        {
           
                List<Product> products = db.Product.ToList();
                List<Request> requests = db.Request.ToList();
            List<RequestDetail> requestDetails = db.RequestDetail.Where(reqd => reqd.RequestId == requestId).ToList();

            var requestRecord = from rd in requestDetails
                                join r in requests on rd.RequestId equals r.RequestId into table1
                                from r in table1.ToList()
                                join p in products on rd.ProductId equals p.ProductId into table2
                                from p in table2.ToList()
                                select new ViewModel
                                {
                                    requestDetails = rd,
                                    requests = r,
                                    products = p
                                     };
                return View(requestRecord);
            

        }
    }

    
}