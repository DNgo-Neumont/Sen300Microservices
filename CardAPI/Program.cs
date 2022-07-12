using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);
builder.Services.AddControllers();
//builder.Services.AddDbContext<QuestDB>(opt => opt.UseInMemoryDatabase("QuestBoard"));

var app = builder.Build();

bool properValidation = true;

app.MapPost("/", (Card card) => {
    DateOnly today = new DateOnly();

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
                    if (i % 2 != 0) {
                        int num = checkValues[i] * 2;
                        if (i > 9) {
                            num = 1  + (num - 10);
                        }
                        checkValues[i] = num;
                    }
                }
            }
        }
        return false;
    }
    else {
        if (card.expirationDate >= today && (card.ccNumber[0].Equals('4') || card.ccNumber[0].Equals('5'))) {
            return true;
        }
        return false;
    }
});

app.Run();

public class Card{
    public string ccNumber {get; set;}
    public DateOnly expirationDate {get; set;}
    public string CCV {get; set;}

    public Card(string _ccNumber, DateOnly _expirationDate, string _CCV)
    {
        this.ccNumber = _ccNumber;
        this.expirationDate = _expirationDate;
        this.CCV = _CCV;
    }
}