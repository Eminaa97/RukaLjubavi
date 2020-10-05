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
    public class KategorijaController : ControllerBase
    {
        private readonly IKategorijaService _kategorijaService;

        public KategorijaController(IKategorijaService kategorijaService)
        {
            _kategorijaService = kategorijaService;
        }

        [HttpGet]
        public IActionResult Get()
        {
            return Ok(_kategorijaService.Get());
        }

        [HttpGet("{Id}")]
        public IActionResult Get(int Id)
        {
            return Ok(_kategorijaService.Get(Id));
        }

        [HttpPost]
        public IActionResult Insert(KategorijaInsertRequest request)
        {
            return Ok(_kategorijaService.Insert(request));
        }

    }
}
