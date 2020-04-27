using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using logicProject.Models.DBContext;

namespace logicProject.Controllers
{
    public class LocationController : Controller
    {
        // GET: Location
       
        private LogicEntities db = new LogicEntities();

        [HttpGet]
        public ActionResult Location()
        {
            return View(db.CollectionPoint);
        }
        [HttpPost]
        public ActionResult Location(bool? point)
        {
            
            return View(db.CollectionPoint);
        }



    }
}