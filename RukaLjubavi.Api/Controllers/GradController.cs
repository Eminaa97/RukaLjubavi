using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using RukaLjubavi.Api.Contracts.Requests;
using RukaLjubavi.Api.Services;

namespace RukaLjubavi.Api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class GradController : ControllerBase
    {
        private readonly IGradService _gradService;

        public GradController(IGradService gradService)
        {
            _gradService = gradService;
        }
        [HttpGet]
        public IActionResult Get()
        {
            return Ok(_gradService.Get());
        }

        [HttpGet("{Id}")]
        public IActionResult Get(int Id)
        {
            return Ok(_gradService.Get(Id));
        }

        [HttpGet("drzave/{Id}")]
        public IActionResult GetByDrzava(int DrzavaId)
        {
            return Ok(_gradService.Get(x=>x.DrzavaId == DrzavaId));
        }
        [Authorize]
        [HttpPost]
        public IActionResult Insert(GradInsertRequest request)
        {
            return Ok(_gradService.Insert(request));
        }


    }
}
