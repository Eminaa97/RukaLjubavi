using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.SignalR;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace RukaLjubavi.Api.Hubs
{
    [Authorize]
    public class NotificationHub : Hub
    {
        public static Dictionary<string, string> ConnectedUsers = new Dictionary<string, string>();

        public override Task OnConnectedAsync()
        {
            lock (ConnectedUsers)
                if (!ConnectedUsers.ContainsKey(Context.UserIdentifier))
                    ConnectedUsers.Add(Context.UserIdentifier, Context.ConnectionId);

            return base.OnConnectedAsync();
        }

        public override Task OnDisconnectedAsync(Exception exception)
        {
            lock (ConnectedUsers)
                if (!ConnectedUsers.ContainsKey(Context.UserIdentifier))
                    ConnectedUsers.Remove(Context.UserIdentifier);

            return base.OnDisconnectedAsync(exception);
        }
    }
}
