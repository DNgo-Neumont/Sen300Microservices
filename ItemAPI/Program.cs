using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);
builder.Services.AddControllers();
//builder.Services.AddDbContext<QuestDB>(opt => opt.UseInMemoryDatabase("QuestBoard"));
builder.Services.AddDbContext<ItemDB>(opt => opt.UseSqlServer(builder.Configuration.GetConnectionString("docker_db1")));

var app = builder.Build();
app.MapControllers();

app.MapGet("/", () => "Hello World!");

app.Run();