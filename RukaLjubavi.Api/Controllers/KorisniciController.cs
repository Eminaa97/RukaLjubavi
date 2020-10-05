using Microsoft.AspNetCore.Mvc;
using RukaLjubavi.Api.Contracts.Requests;
using RukaLjubavi.Api.Services;

namespace RukaLjubavi.Api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class KorisniciController : ControllerBase
    {
        private readonly IKorisnikService _korisnikService;

        public KorisniciController(
            IKorisnikService korisnikService
            )
        {
            _korisnikService = korisnikService;
        }

        [HttpPost]
        public IActionResult Get([FromBody] UserSearchRequest request)
        {
            return Ok(_korisnikService.Get(request));
        }

        [HttpGet("{id}")]
        public IActionResult GetKorisnik(int id)
        {
            return Ok(_korisnikService.Get(id));
        }

        [HttpGet("donatori/{id}")]
        public IActionResult GetDonator(int id)
        {
            return Ok(_korisnikService.GetDonator(id));
        }

        [HttpGet("benefiktori/{id}")]
        public IActionResult GetBenefiktor(int id)
        {
            return Ok(_korisnikService.GetBenefiktor(id));
        }

        [HttpPost("register")]
        public IActionResult Register([FromBody] UserInsertRequest request)
        {
            return Ok(_korisnikService.Insert(request));
        }

        [HttpPost("login")]
        public IActionResult Login([FromBody] UserLoginRequest request)
        {
            return Ok(_korisnikService.Login(request));
        }
    }
}
