using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using RukaLjubavi.Api.Contracts.Requests;
using RukaLjubavi.Api.Models;
using RukaLjubavi.Api.Services;

namespace RukaLjubavi.Api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class DonacijaController : ControllerBase
    {
        private readonly IDonacijaService _donacijaService;

        public DonacijaController( IDonacijaService _donacijaService )
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

        [Authorize]
        [HttpPost]
        public IActionResult Insert([FromBody] DonacijaInsertRequest request)
        {
            return Ok(_donacijaService.Insert(request));
        }

        [Authorize]
        [HttpPost("prihvatiDonaciju/{id}")]
        public IActionResult Prihvati(int id)
        {
            return Ok(_donacijaService.Prihvati(id));
        }

        [Authorize]
        [HttpPost("PromjeniStatus/{id}")]
        public IActionResult PromjeniStatus(int id, StatusDonacije statusDonacije)
        {
            return Ok(_donacijaService.PromjeniStatus(id, statusDonacije));
        }
    }
}
