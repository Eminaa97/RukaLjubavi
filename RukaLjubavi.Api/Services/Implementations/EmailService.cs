using Microsoft.Extensions.Options;
using RukaLjubavi.Api.Configuration;
using System.Net;
using System.Net.Mail;
using System.Threading.Tasks;

namespace RukaLjubavi.Api.Services.Implementations
{
    public class EmailService : IEmailService
    {
        private readonly IOptions<EmailSettings> _email;

        public EmailService(IOptions<EmailSettings> email)
        {
            _email = email;
        }

        public void Send(string emailTo, string subject, string message)
        {
            using SmtpClient Client = new SmtpClient();
            using MailMessage msg = new MailMessage();
            MailAddress fromAddress = new MailAddress(_email.Value.SenderEmail);

            Client.Host = _email.Value.MailServer;
            Client.EnableSsl = _email.Value.EnableSsl;
            Client.Port = _email.Value.MailPort;
            Client.UseDefaultCredentials = _email.Value.UseDefaultCredentials;
            Client.Credentials = new NetworkCredential(_email.Value.SenderEmail, _email.Value.SenderEmailPassword);

            msg.From = fromAddress;
            msg.Subject = subject;
            msg.IsBodyHtml = true;
            msg.Body = message;
            msg.To.Add(emailTo);

            Client.Send(msg);
        }

        public Task SendAsync(string emailTo, string subject, string message)
        {
            using SmtpClient Client = new SmtpClient();
            using MailMessage msg = new MailMessage();
            MailAddress fromAddress = new MailAddress(_email.Value.SenderEmail);

            Client.Host = _email.Value.MailServer;
            Client.EnableSsl = _email.Value.EnableSsl;
            Client.Port = _email.Value.MailPort;
            Client.UseDefaultCredentials = _email.Value.UseDefaultCredentials;
            Client.Credentials = new NetworkCredential(_email.Value.SenderEmail, _email.Value.SenderEmailPassword);

            msg.From = fromAddress;
            msg.Subject = subject;
            msg.IsBodyHtml = true;
            msg.Body = message;
            msg.To.Add(emailTo);

            return Client.SendMailAsync(msg);
        }
    }
}
