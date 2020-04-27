using logicProject.Models.DBContext;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using logicProject.Models.EF;
using Newtonsoft.Json;
using System.Diagnostics;

namespace logicProject.Controllers
{
    public class AppointDeptStaffController : Controller
    {
		private LogicEntities db = new LogicEntities();

		// GET: AppointDeptStaff
		public ActionResult Index()
        {
            return View();
        }

		public ActionResult AppointRep() {
			using (db)
			{
				var departmentstaff = new SelectList(db.DepartmentStaff.ToList(), "StaffId", "StaffName");
				ViewData["DBdepartmentstaff"] = departmentstaff;
			}
			return View();
		}

		public JsonResult GetStaffList(string searchTerm) {
			var staffList = db.DepartmentStaff.ToList();

			if (searchTerm!=null) {
				staffList = db.DepartmentStaff.Where(x => x.StaffName.Contains(searchTerm)).ToList();

			}

			var modifiedData = staffList.Select(x => new
			{
				id = x.StaffId,
				text = x.StaffName
			});
			return Json(modifiedData, JsonRequestBehavior.AllowGet);
		}

		public ActionResult GetStaffListView()
		{
			string a = new System.Web.Script.Serialization.JavaScriptSerializer().Serialize(GetStaffList(null));
			//List<DepartmentStaff> list = JsonConvert.DeserializeObject<List<DepartmentStaff>>(a);
			return View();
		}
		[HttpPost]
		public ActionResult AppointStaff(string staffname)
		{
			Debug.WriteLine("name=" +staffname);
			return View();
		}
	}
}