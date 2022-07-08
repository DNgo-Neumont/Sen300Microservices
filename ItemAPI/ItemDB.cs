using Microsoft.EntityFrameworkCore;
public class ItemDB : DbContext
{
    public ItemDB(DbContextOptions<ItemDB> options) : base(options) { }
    public DbSet<Item> itemTable => Set<Item>();
    
    public ItemDB()
    {

    }
}