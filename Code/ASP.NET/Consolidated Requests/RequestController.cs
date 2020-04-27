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

        public ActionResult RequestsList()
        {
           
            return View();
        }

        [HttpPost]
        public ActionResult RequestsList(FormCollection values)
        {
            string fromdate = values["FromDate"];
            string todate = values["Todate"];

            DateTime fromDate = DateTime.Parse(fromdate);
            DateTime toDate = DateTime.Parse(todate);

            ViewBag.Fromdate = fromdate;
            ViewBag.Todate = todate;


            if (fromDate > toDate)
            {
                ViewBag.msg = "Please select the Correct Date";
                return View();
            }    

            else
            {

                List<RequestListViewModel> list = new List<RequestListViewModel>();
                //var requests = db.Request.Where(r => r.Status == "Outstanding").Include(d => d.Department);
                var request = (from r in db.Request join d in db.Department on r.DeptId equals d.DeptId join ds in db.DepartmentStaff on r.StaffId equals ds.StaffId join cp in db.CollectionPoint on d.CollectionPt equals cp.CollectionPtId where (r.ReqDate >= fromDate && r.ReqDate <= toDate) || r.Status=="Outstanding" select new { department = d, requests = r, staff = ds, collectiopoint = cp }).ToList();
                foreach (var a in request)
                {
                    list.Add(new RequestListViewModel()
                    {
                        department = a.department,
                        staff = a.staff,
                        requests = a.requests,
                        collectionPoint = a.collectiopoint
                    });
                }

                return View(list);
            }
        }


            public ActionResult Requestitems(int id)
            {

                ViewBag.Id = id;
                List<RequestItemsViewModel> list = new List<RequestItemsViewModel>();
                var requestitems = (from rd in db.RequestDetail join p in db.Product on rd.ProductId equals p.ProductId where rd.RequestId == id select new { requestDetail = rd, product = p }).ToList();
                foreach (var b in requestitems)
                {
                    list.Add(new RequestItemsViewModel()
                    {
                        requestDetail = b.requestDetail,
                        product = b.product

                    });
                }

                return View(list);
            }

        
    }
}