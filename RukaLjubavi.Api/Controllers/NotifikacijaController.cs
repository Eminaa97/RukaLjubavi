using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using RukaLjubavi.Api.Contracts.Requests;
using RukaLjubavi.Api.Services;

namespace RukaLjubavi.Api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class NotifikacijaController : ControllerBase
    {
        private readonly INotifikacijaService _notifikacijaService;

        public NotifikacijaController(INotifikacijaService notifikacijaService)
        {
            _notifikacijaService = notifikacijaService;
        }

        [HttpGet]
        public IActionResult Get()
        {
            return Ok(_notifikacijaService.Get());
        }

        [HttpGet("{Id}")]
        public IActionResult Get(int Id)
        {
            return Ok(_notifikacijaService.Get(Id));
        }

        [Authorize]
        [HttpPost]
        public IActionResult Insert(NotifikacijaInsertRequest request)
        {
            return Ok(_notifikacijaService.Insert(request));
        }

        [Authorize]
        [HttpPost("seen")]
        public IActionResult Update(NotifikacijaUpdateRequest request)
        {
            _notifikacijaService.Update(request);
            return Ok();
        }
    }
}
