using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);
builder.Services.AddControllers();
//builder.Services.AddDbContext<QuestDB>(opt => opt.UseInMemoryDatabase("QuestBoard"));

app.MapGet("/", () => "Hello World!");

app.Run();
