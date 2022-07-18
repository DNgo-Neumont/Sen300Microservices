using Microsoft.EntityFrameworkCore;
using Steeltoe.Discovery.Client;
using Steeltoe.Common.Discovery;
using Steeltoe.Discovery.Eureka;
using Steeltoe.Discovery;

var builder = WebApplication.CreateBuilder(args);
builder.Services.AddControllers();
builder.Services.AddDiscoveryClient(builder.Configuration);
//builder.Services.AddDbContext<QuestDB>(opt => opt.UseInMemoryDatabase("QuestBoard"));
builder.Services.AddDbContext<ItemDB>(opt => opt.UseSqlServer(builder.Configuration.GetConnectionString("docker_db2")));

builder.Services.AddCors(options =>
{
    options.AddDefaultPolicy(
        policy =>

        {

            policy.AllowAnyOrigin().AllowAnyHeader().AllowAnyMethod();
            policy.WithHeaders("Access-Control-Allow-Origin","origin-list");

        });
});

var app = builder.Build();
app.MapControllers();
app.UseCors();

app.MapGet("/", () => "Hello World!");

app.MapGet("/testConnection", async (IDiscoveryClient idc) =>
{
    //return "this is the root of dotnet-eureka-client";
    DiscoveryHttpClientHandler _handler = new DiscoveryHttpClientHandler(idc);
    var client = new HttpClient(_handler, false);
    return await client.GetStringAsync("http://CARD-API/test") + " more from dotnet-api2";
}
);

app.Run();