using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RukaLjubavi.Api.Models
{
    public class PdvBroj
    {
        [Key]
        public string PDVBroj { get; set; }
    }
}
