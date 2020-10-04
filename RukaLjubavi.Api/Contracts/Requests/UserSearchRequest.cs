namespace RukaLjubavi.Api.Contracts.Requests
{
    public class UserSearchRequest
    {
        public int? Id { get; set; }
        public string Email { get; set; }
        public bool? IsVerifikovan { get; set; }
        public string MjestoPrebivalista { get; set; }
        public int? MjestoPrebivalistaId { get; set; }
    }
}
