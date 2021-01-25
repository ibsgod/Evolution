
public class Food 
{
	int x;
	int y;
	int centx;
	int centy;
	Food(int x, int y, Game g)
	{
		this.x = x;
		this.y = y;
		centx = x + 8;
		centy = y + 8;
		g.foods.add(this);
	}
}
