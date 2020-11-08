using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;
using RukaLjubavi.Api.Contracts.Requests;
using RukaLjubavi.Api.Hubs;
using RukaLjubavi.Api.Services;

namespace RukaLjubavi.Api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class NotifikacijaController : ControllerBase
    {
        private readonly INotifikacijaService _notifikacijaService;
        private readonly IHubContext<NotificationHub> _hubContext;

        public NotifikacijaController(
            INotifikacijaService notifikacijaService, 
            IHubContext<NotificationHub> hubContext
            )
        {
            _notifikacijaService = notifikacijaService;
            _hubContext = hubContext;
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
            var notificationResponse = _notifikacijaService.Insert(request);

            lock (NotificationHub.ConnectedUsers)
            {
                if (NotificationHub.ConnectedUsers.ContainsKey(notificationResponse.KorisnikId.ToString()))
                {
                    var connectionId = NotificationHub.ConnectedUsers[notificationResponse.KorisnikId.ToString()];
                    _hubContext.Clients.Client(connectionId).SendAsync("OnNotificationReceived", notificationResponse);
                }
            }

            return Ok(notificationResponse);
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
