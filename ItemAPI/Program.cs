using Microsoft.EntityFrameworkCore;
using Steeltoe.Discovery.Client;
using Steeltoe.Common.Discovery;
using Steeltoe.Discovery.Eureka;
using Steeltoe.Discovery;

var builder = WebApplication.CreateBuilder(args);
builder.Services.AddControllers();
builder.Services.AddDiscoveryClient(builder.Configuration);
//builder.Services.AddDbContext<QuestDB>(opt => opt.UseInMemoryDatabase("QuestBoard"));
//docker run --name eureka -d --net netSEN300 -p 8761:8761 steeltoeoss/eureka-server eureka registry run command
builder.Services.AddDiscoveryClient(builder.Configuration);
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

app.Run();