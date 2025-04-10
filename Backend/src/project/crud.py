from project.database import text, async_engine

async def get_admins():
    async with async_engine.connect() as conn:
        res = await conn.execute(text("select * from Admin"))
        print(res.first())
