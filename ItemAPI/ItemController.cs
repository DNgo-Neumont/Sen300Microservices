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
            return "Hark netizen! The questboard is on-line!";
        }

        [HttpGet]
        [Route("getInventory")]
        public async Task<ActionResult<List<ItemDB>>> getAllQuests()
        {
            return await _db.Items.ToListAsync();
        }
        
        [HttpGet]
        [Route("getCompleted")]
        public async Task<List<Item>> getAllCompletedQuests() {
            return await _db.Items.Where(t => t.IsComplete == true).ToListAsync();
        }

        [HttpGet]
        [Route("getQuest/{id}")]
        public async Task<ActionResult<Item>> getQuest(long id)
        {
            var quest = await _db.Items.FindAsync(id);
            if (quest == null) {
                return NotFound();
            }
            return Ok(quest);
        }

        [HttpPost]
        public async Task<IResult> createQuest(Item quest)
        {
            _db.Items.Add(quest);
            await _db.SaveChangesAsync();
            return Results.Created($"/{quest.Id}", quest);
        }

        [HttpPut]
        [Route("updateQuest/")]
        public async Task<IResult> updateQuest(Items quest)
        {
            var currQuest = await _db.Items.FindAsync(quest.Id);
            if (currQuest != null) {
                currQuest.Name = quest.Name;
                currQuest.IsComplete = quest.IsComplete;
                await _db.SaveChangesAsync();
                return Results.Created($"/{quest.Id}", quest);
            }
            return Results.NoContent();
        }

        [HttpDelete]
        [Route("deleteQuest/{id}")]
        public async Task<IResult> deleteQuest(long id) {
            if (await _db.Items.FindAsync(id) is Item quest) {
                _db.Items.Remove(quest);
                await _db.SaveChangesAsync();
                return Results.Ok(quest);
            }
            return Results.NotFound();
        }
    }
}