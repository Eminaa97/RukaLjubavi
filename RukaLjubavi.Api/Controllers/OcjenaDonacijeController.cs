using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using RukaLjubavi.Api.Contracts.Requests;
using RukaLjubavi.Api.Services;

namespace RukaLjubavi.Api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class OcjenaDonacijeController : ControllerBase
    {
        private readonly IOcjenaDonacijeService _ocjenaDonacijeService;

        public OcjenaDonacijeController(
            IOcjenaDonacijeService _ocjenaDonacijeService
            )
        {
            this._ocjenaDonacijeService = _ocjenaDonacijeService;
        }

        [HttpGet]
        public IActionResult Get([FromQuery] OcjenaDonacijeSearchRequest request)
        {
            return Ok(_ocjenaDonacijeService.Get(request));
        }

        [HttpGet("{id}")]
        public IActionResult GetDonacija(int id)
        {
            return Ok(_ocjenaDonacijeService.Get(id));
        }

        [Authorize]
        [HttpPost]
        public IActionResult Insert([FromBody] OcjenaDonacijeInsertRequest request)
        {
            return Ok(_ocjenaDonacijeService.Insert(request));
        }

    }
}
