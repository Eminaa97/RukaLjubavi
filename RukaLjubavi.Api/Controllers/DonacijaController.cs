using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using RukaLjubavi.Api.Contracts.Requests;
using RukaLjubavi.Api.Services;

namespace RukaLjubavi.Api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class DonacijaController : ControllerBase
    {
        private readonly IDonacijaService _donacijaService;

        public DonacijaController(
            IDonacijaService _donacijaService
            )
        {
            this._donacijaService = _donacijaService;
        }

        [HttpGet]
        public IActionResult Get([FromQuery] DonacijaSearchRequest request)
        {
            return Ok(_donacijaService.Get(request));
        }

        [HttpGet("{id}")]
        public IActionResult GetDonacija(int id)
        {
            return Ok(_donacijaService.Get(id));
        }

        [HttpPost]
        public IActionResult Insert([FromBody] DonacijaInsertRequest request)
        {
            return Ok(_donacijaService.Insert(request));
        }


        [HttpPost("prihvatiDonaciju/{id}")]
        public IActionResult Prihvati(int id)
        {
            return Ok(_donacijaService.Prihvati(id));
        }
    }
}
