using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using RukaLjubavi.Api.Services;

namespace RukaLjubavi.Api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class DrzavaController : ControllerBase
    {
        private readonly IDrzavaService _drzavaService;

        public DrzavaController(IDrzavaService drzavaService)
        {
            _drzavaService = drzavaService;
        }

        [HttpGet]
        public IActionResult Get()
        {
            return Ok(_drzavaService.Get());
        }
    }
}
