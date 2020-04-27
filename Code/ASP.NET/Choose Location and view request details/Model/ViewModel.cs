using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace logicProject.Models.EF
{
    public class ViewModel
    {
        public RequestDetail requestDetails { get; set; }
        public Request requests { get; set; }
        public Product products { get; set; }
    }
}