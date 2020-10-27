namespace RukaLjubavi.Api.Contracts.Requests
{
    public class PasswordResetRequest
    {
        public string OldPassword { get; set; }
        public string NewPassword { get; set; }
        public string NewPasswordConfirm { get; set; }
    }
}
