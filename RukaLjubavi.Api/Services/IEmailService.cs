using System.Threading.Tasks;

namespace RukaLjubavi.Api.Services
{
    public interface IEmailService
    {
        void Send(string emailTo, string subject, string message);
        Task SendAsync(string emailTo, string subject, string message);
    }
}
