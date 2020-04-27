using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using logicProject.Models.EF;
using Newtonsoft.Json;
using System.Diagnostics;
using logicProject.Models.DBContext;

namespace logicProject.Controllers
{
	public class AuthorizationController : Controller
	{

		private LogicEntities db = new LogicEntities();

		// GET: Authorization
		public ActionResult Index()
		{
			return View();
		}

		public JsonResult GetStaffList(string searchTerm)
		{
			var staffList = db.DepartmentStaff.ToList();

			if (searchTerm != null)
			{
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
		public ActionResult AppointStaff(string staffname, DateTime getStartDate, DateTime getEndDate)
		{
			var result = db.DepartmentStaff.SingleOrDefault(b => b.StaffName == staffname);
			var resultStaffId = result.StaffId;
			var resultDeptId = int.Parse(result.DeptId);
			//Debug.WriteLine("staff id is:" + resultId);

			using (LogicEntities db = new LogicEntities())
			{
				//DateTime now = DateTime.Now;
				var authorization = db.Set<Authorization>();
				authorization.Add(new Authorization { AuthNo = 1, DeptId = resultDeptId, StaffId = resultStaffId, StartDate = getStartDate , EndDate = getEndDate });
				db.SaveChanges();
			}
			return Json(new { isok = true, message = "Authorization Successful" });
		}
	}

}