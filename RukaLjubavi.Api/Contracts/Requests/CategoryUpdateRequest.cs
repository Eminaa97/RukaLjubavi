using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace RukaLjubavi.Api.Contracts.Requests
{
    public class CategoryUpdateRequest
    {
        public IList<int> Kategorije { get; set; }
    }
}
