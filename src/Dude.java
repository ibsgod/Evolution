import java.util.ArrayList;
import java.util.Random;

public class Dude 
{
	boolean hunted = false;
	int fed = 0;
	int x;
	int y;
	int size;
	int speed;
	int sense;
	Dude prey = null;
	int type;
	int energy;
	int velx = 0;
	int vely = 0;
	int centx;
	int centy;
	Food sensed = null;
	Game g;
	ArrayList<Food> foods = new ArrayList<Food>();
	Dude(int type, int x, int y, int size, int speed, int sense, int energy, Game g)
	{
		this.type = type;
		this.x = x;
		this.y = y;
		this.g = g;
		foods = g.foods;
		if (x < g.minx)
		{
			this.x = g.minx;
		}
		if (x + size * 10 > g.maxx)
		{
			this.x = g.maxx - size * 10;
		}
		if (y < g.miny)
		{
			this.y = g.miny;
		}
		if (y + size * 10 > g.maxy)
		{
			this.y = g.maxy - size * 10;
		}
		this.size = size;
		this.speed = speed;
		this.sense = sense;
		this.energy = energy;
		switch (type)
		{
			case 0:
				g.playerDudes.add(this);
				break;
		}
	}
	public Dude move()
	{
		if (fed >= 2 && !(prey != null && hunted))
		{
			return null;
		}
		if (size * 5 > g.maxSize)
		{
			g.maxSize = size * 10;
		}
		centx = x + size * 5;
		centy = y + size * 5;
		if (hunted)
		{
			prey = null;
		}
		int close = -1;
		double min = Integer.MAX_VALUE;
		if (prey == null)
		{
			for (int i = 0; i < g.playerDudes.size(); i++)
			{
				if (Math.pow(g.playerDudes.get(i).centx - centx, 2) + Math.pow(g.playerDudes.get(i).centy - centy, 2) <= 900 * sense * sense)
				{
					if (g.playerDudes.get(i).size < size && g.playerDudes.get(i).speed < speed && Math.pow(g.playerDudes.get(i).centx - centx, 2) + Math.pow(g.playerDudes.get(i).centy - centy, 2) < min)
					{
						close = i;
						min = Math.pow(g.playerDudes.get(i).centx - centx, 2) + Math.pow(g.playerDudes.get(i).centy - centy, 2);
					}
					else if (g.playerDudes.get(i).size > size && g.playerDudes.get(i).speed > speed)
					{
						hunted = true;
						prey = g.playerDudes.get(i);
						velx = 0;
						vely = 0;
						break;
					}
				}
			}
			if (close != -1)
			{
				hunted = false;
				prey = g.playerDudes.get(close);
				velx = 0;
				vely = 0;
			}
		}
		close = -1;
		min = Integer.MAX_VALUE;
		if (sensed == null && prey == null)
		{
			for (int i = 0; i < foods.size(); i++)
			{
				try
				{
					if (Math.pow(foods.get(i).centx - centx, 2) + Math.pow(foods.get(i).centy - centy, 2) <= 900 * sense * sense)
					{
						if (Math.pow(foods.get(i).centx - centx, 2) + Math.pow(foods.get(i).centy - centy, 2) < min)
						{
							close = i;
							min = Math.pow(foods.get(i).centx - centx, 2) + Math.pow(foods.get(i).centy - centy, 2);
						}
					}
				}
				catch(Exception e){};
			}
			if (close != -1)
			{
				sensed = foods.get(close);
				velx = 0;
				vely = 0;
			}
		}
		if (energy < speed + sense * 2)
		{
			return null;
		}
		else if (sensed != null && Math.pow(sensed.centx - centx, 2) + Math.pow(sensed.centy - centy, 2) <= size * size * 25)
		{
			fed ++;
			for (int i = 0; i < g.playerDudes.size(); i++)
			{
				if (!g.playerDudes.get(i).equals(this) && g.playerDudes.get(i).sensed != null && g.playerDudes.get(i).sensed.equals(sensed))
				{
					g.playerDudes.get(i).sensed = null;
				}
			}
			g.foods.remove(sensed);
			sensed = null;
		}
		else if (sensed != null)
		{
			if (Math.abs(sensed.centx - centx) >= speed)
			{
				x += Math.abs(sensed.centx - centx) / (sensed.centx - centx) * speed; 
			}
			else if (Math.abs(sensed.centx - centx) != 0)
			{
				x += sensed.centx - centx;
			}
			if (Math.abs(sensed.centy - centy) >= speed)
			{
				y += Math.abs(sensed.centy - centy) / (sensed.centy - centy) * speed;
			}
			else if (Math.abs(sensed.centy - centy) != 0)
			{
				y += sensed.centy - centy;
			}
		}
		else if (!hunted && prey != null && Math.pow(prey.centx - centx, 2) + Math.pow(prey.centy - centy, 2) <= size * size * 25)
		{
			fed += 1;
			for (int i = 0; i < g.playerDudes.size(); i++)
			{
				if (!g.playerDudes.get(i).equals(this) && g.playerDudes.get(i).prey != null && g.playerDudes.get(i).prey.equals(prey))
				{
					g.playerDudes.get(i).prey = null;
				}
			}
			return prey;
		}
		else if (!hunted && prey != null)
		{
			if (Math.abs(prey.centx - centx) >= speed)
			{
				x += Math.abs(prey.centx - centx) / (prey.centx - centx) * speed; 
			}
			else if (Math.abs(prey.centx - centx) != 0)
			{
				x += prey.centx - centx;
			}
			if (Math.abs(prey.centy - centy) >= speed)
			{
				y += Math.abs(prey.centy - centy) / (prey.centy - centy) * speed;
			}
			else if (Math.abs(prey.centy - centy) != 0)
			{
				y += prey.centy - centy;
			}
		}
		else if (prey != null)
		{
			if (Math.abs(prey.centx - centx) >= speed)
			{
				x -= Math.abs(prey.centx - centx) / (prey.centx - centx) * speed; 
			}
			else if (Math.abs(prey.centx - centx) != 0)
			{
				x -= prey.centx - centx;
			}
			if (Math.abs(prey.centy - centy) >= speed)
			{
				y -= Math.abs(prey.centy - centy) / (prey.centy - centy) * speed;
			}
			else if (Math.abs(prey.centy - centy) != 0)
			{
				y -= prey.centy - centy;
			}
		}
		else
		{
			Random rand = new Random();
			int rando = rand.nextInt(3);
			switch(rando)
			{
				case 0:
					break;
				case 1:
					if (velx < speed)
						velx += speed/4;
					break;
				case 2:
					if (velx > -speed)
						velx -= speed/4;
					break;
			}
			rando = rand.nextInt(3);
			switch(rando)
			{
				case 0:
					break;
				case 1:
					if (vely < speed)
						vely += speed/4;
					break;
				case 2:
					if (vely > -speed)
						vely -= speed/4;
					break;
			}
		}
		if (x + velx + size * 10 < g.maxx && x + velx > g.minx)
			x += velx;
		else
			velx = 0;
		if (y + vely + size * 10 < g.maxy && y + vely > g.miny)
			y += vely;
		else
			vely = 0;
		if (x < g.minx)
		{
			x = g.minx;
			velx = 0;
		}
		if (x + size * 10 > g.maxx)
		{
			x = g.maxx - size * 10;
			velx = 0;
		}
		if (y < g.miny)
		{
			y = g.miny;
			vely = 0;
		}
		if (y + size * 10 > g.maxy)
		{
			y = g.maxy - size * 10;
			vely = 0;
		}
		energy -= (speed * Math.max(0, speed/4 - 3) + size * Math.max(0, size*2 - 6) + sense);
		return null;
	}
}
