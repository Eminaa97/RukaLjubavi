using Microsoft.AspNetCore.Authorization;
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
        public IActionResult Search([FromBody] UserSearchRequest request)
        {
            return Ok(_korisnikService.Get(request));
        }

        [HttpPost("resetpassword")]
        [Authorize]
        public IActionResult ResetPasword(PasswordResetRequest request)
        {
            return Ok(_korisnikService.ResetPasword(User.GetUserId(), request));
        }

        [HttpPatch("kategorije/update")]
        [Authorize]
        public IActionResult UpdateCategories(CategoryUpdateRequest request)
        {
            _korisnikService.UpdateCategories(User.GetUserId(), request);
            return Ok();
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

        [HttpGet("donatori")]
        public IActionResult GetDonatori()
        {
            return Ok(_korisnikService.GetDonatori());
        }

        [HttpGet("benefiktori/{id}")]
        public IActionResult GetBenefiktor(int id)
        {
            return Ok(_korisnikService.GetBenefiktor(id));
        }

        [HttpGet("benefiktori")]
        public IActionResult GetBenefiktori([FromQuery] BenefiktorSearchRequest searchRequest)
        {
            return Ok(_korisnikService.GetBenefiktori(searchRequest));
        }

        [HttpPost("donatori/register")]
        public IActionResult Register([FromBody] DonatorInsertRequest request)
        {
            return Ok(_korisnikService.Insert(request));
        }

        [HttpPost("benefiktori/register")]
        public IActionResult Register([FromBody] BenefiktorInsertRequest request)
        {
            return Ok(_korisnikService.Insert(request));
        }

        [HttpPatch("donatori/update")]
        [Authorize]
        public IActionResult Update([FromBody] DonatorUpdateRequest request)
        {
            return Ok(_korisnikService.Update(request.KorisnikId, request));
        }

        [HttpPatch("benefiktori/update")]
        [Authorize]
        public IActionResult Update([FromBody] BenefiktorUpdateRequest request)
        {
            return Ok(_korisnikService.Update(request.KorisnikId, request));
        }

        [HttpPost("login")]
        public IActionResult Login([FromBody] UserLoginRequest request)
        {
            return Ok(_korisnikService.Login(request));
        }
    }
}
