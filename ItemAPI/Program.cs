using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);
builder.Services.AddControllers();
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
app.UseCors();
app.MapControllers();

app.MapGet("/", () => "Hello World!");
app.Run();