using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace Controllers
{
    [ApiController]
    [Route("itemAPI")]
    public class MyController : ControllerBase
    {
        private readonly ItemDB _db;

        public MyController(ILogger<MyController> logger, ItemDB db)
        {
            _db = db;
        }

        [HttpGet]
        [Route("test")]
        public ActionResult<string> test()
        {
            return "Hello netizen! The inventory is on-line!";
        }

        [HttpGet]
        [Route("getInventory")]
        public async Task<ActionResult<List<Item>>> getAllQuests()
        {
            return await _db.itemTable.ToListAsync();
        }
        
        [HttpGet]
        [Route("getItem/{id}")]
        public async Task<ActionResult<Item>> getQuest(long id)
        {
            var quest = await _db.itemTable.FindAsync(id);
            if (quest == null) {
                return NotFound();
            }
            return Ok(quest);
        }

        [HttpPost]
        public async Task<IResult> createQuest(Item quest)
        {
            _db.itemTable.Add(quest);
            await _db.SaveChangesAsync();
            return Results.Created($"/{quest.Id}", quest);
        }

        [HttpPut]
        [Route("updateItem/")]
        public async Task<IResult> updateQuest(Item item)
        {
            var currItem = await _db.itemTable.FindAsync(item.Id);
            if (currItem != null) {
                currItem.Title = item.Title;
                currItem.unitPrice = item.unitPrice;
                currItem.Description = item.Description;
                await _db.SaveChangesAsync();
                return Results.Created($"/{item.Id}", item);
            }
            return Results.NoContent();
        }

        [HttpDelete]
        [Route("deleteQuest/{id}")]
        public async Task<IResult> deleteQuest(long id) {
            if (await _db.itemTable.FindAsync(id) is Item quest) {
                _db.itemTable.Remove(quest);
                await _db.SaveChangesAsync();
                return Results.Ok(quest);
            }
            return Results.NotFound();
        }
    }
}