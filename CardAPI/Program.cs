using Microsoft.EntityFrameworkCore;
using Microsoft.AspNetCore.Mvc;
using System.Text.Json; 
using Steeltoe.Discovery.Client;
using Steeltoe.Common.Discovery;
using Steeltoe.Discovery.Eureka;
using Steeltoe.Discovery;

var builder = WebApplication.CreateBuilder(args);
builder.Services.AddControllers();
builder.Services.AddDiscoveryClient(builder.Configuration);
//builder.Services.AddDbContext<QuestDB>(opt => opt.UseInMemoryDatabase("QuestBoard"));
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

bool properValidation = false;

app.MapGet("/creditCard/", () => {
    return "It's Working!";
});

app.MapPost("/creditCard/checkCard", (Card card) => {
    DateTime today = new DateTime();
    today = DateTime.Today;

    if ((card.ccNumber == "" || card.ccNumber == null) || (card.CCV == "" || card.CCV == null)) {
        return false;
    }

    if (properValidation) {
        string checkString = card.ccNumber.Replace(",","").Replace("-","").Replace(" ","");
        List<int> checkValues = new List<int>();

        for (int i = 0; i < checkString.Length; i++) {
            checkValues.Add(checkString[i]);
        }

        if(card.expirationDate >= today) {
            if (checkString.Length >= 13 && checkString.Length <= 19) {
                int checkDigit = checkValues[checkValues.Count - 1];
                for (int i = 0; i < checkValues.Count; i++) {
                    if (i % 2 != ((checkValues.Count - 1) % 2)) {
                        int num = checkValues[i] * 2;
                        if (i > 9) {
                            num = 1  + (num - 10);
                        }
                        checkValues[i] = num;
                    }
                }
                string sumCheckValues = (checkValues.Sum() * 9).ToString();

                if(sumCheckValues[sumCheckValues.Length - 1].Equals(checkDigit)) {
                    return true;
                }
            }
        }
        return false;
    }
    else {
        if (card.expirationDate.CompareTo(today) >= 0 && ((card.ccNumber[0].Equals('4') || card.ccNumber[0].Equals('5')))) {
            return true;
        }
        return false;
    }
});

app.Run();

public class Card{
    public string? ccNumber {get; set;}
    public DateTime expirationDate {get; set;}
    public string? CCV {get; set;}
}