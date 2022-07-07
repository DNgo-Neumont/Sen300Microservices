public class Item
{
    public long Id { get; set; }
    public string? Title { get; set; }
    public string? Description { get; set;}
    public decimal unitPrice { get; set; } = 0.0m;
}