namespace RukaLjubavi.Api.Contracts
{
    public class AuthenticatedUser
    {
        public int Id { get; set; }
        public string Email { get; set; }
        public bool IsVerifikovan { get; set; }
        public string Token { get; set; }
        public Models.TipKorisnika TipKorisnika { get; set; }
    }
    public class AuthenticatedBenefiktor : AuthenticatedUser
    {
        public string NazivKompanije { get; set; }
        public string Pdvbroj { get; set; }
    }
    public class AuthenticatedDonator : AuthenticatedUser
    {
        public string Ime { get; set; }
        public string Prezime { get; set; }
        public string Jmbg { get; set; }
    }

}
