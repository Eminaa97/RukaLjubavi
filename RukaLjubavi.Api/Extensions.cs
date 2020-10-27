using System;
using System.Linq;
using System.Security.Claims;

namespace RukaLjubavi.Api
{
    public static class Extensions
    {
        public static int GetUserId(this ClaimsPrincipal principal)
        {
            var claim = principal.Claims.FirstOrDefault(x => x.Type == ClaimTypes.NameIdentifier);

            if (claim != null)
                return int.Parse(claim.Value);

            throw new ArgumentNullException("userId");
        }
    }
}
