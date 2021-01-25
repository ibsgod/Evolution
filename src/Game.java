import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;

public class Game implements Runnable
{
	MainBruh m;
	boolean isPlaying = false;
	int dayCounter = 0;
	int removedFood = 0;
	int initialFood = 20;
	int foodDec = 15;
	int finalFood = 10;
	ArrayList<Stats> lastdays = new ArrayList<Stats>();
	boolean playing = false; 
	boolean autoplay;
	double avgspd;
	double avgsns;
	double avgsze;
	double avgppl;
	int day = 0;
	int size = 580;
	int minx = 400;
	int miny = 20;
	int maxx = minx + size;
	int maxy = miny + size;
	int maxSize;
	Random rand = new Random();
	boolean running = false;
	Thread thread;
	Display display;
	int width, height;
	String title;
	BufferStrategy bs;
	Graphics g;
	ArrayList<Dude> playerDudes = new ArrayList<Dude>();
	ArrayList<Food> foods = new ArrayList<Food>();
	class Stats
	{
		double spd;
		double sns;
		double sze;
		int ppl;
		Stats(double a, double b, double d, int c)
		{
			spd = a;
			sns = b;
			sze = d;
			ppl = c;
		}
	}
	public Game(String title, int width, int height)
	{
		this.width = width;
		this.height = height;
		this.title = title;
	}
	public void tick()
	{
//		if (b.getStatus() == 2 && isPlaying)
//		{
//			try {
//				b.play();
//				isPlaying = false;
//			} catch (BasicPlayerException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		else
//		{
//			isPlaying = true;
//		}
		if (playing)
		{
			boolean done = true;
			boolean skip = false;
			for (int i = 0; i < playerDudes.size(); i++)
			{
				Dude d = playerDudes.get(i).move();
				if (d != null)
				{
					for (int j = 0; j < playerDudes.size(); j++)
					{
						if (playerDudes.get(j).equals(d))
						{
							if (j < i)
							{
								skip = true;
							}
						}
					}
					playerDudes.remove(d);
					if (skip)
					{
						i --;
					}
					playerDudes.get(i).prey = null;
				}
				if (playerDudes.get(i).energy >= playerDudes.get(i).speed + playerDudes.get(i).sense * 2 && playerDudes.get(i).fed < 2)
				{
					done = false;
				}
			}
			if (done)
			{
				playing = false;
				display.nextDay.setText("Next Day -->");
				display.nextDay.setVisible(true);
				int size = playerDudes.size();
				for (int i = 0; i < size; i++)
				{
					if (playerDudes.get(i).fed == 0)
					{
						playerDudes.remove(i);
						i--;
						size --;
						continue;
					}
					if (playerDudes.get(i).fed >= 2)
					{
						int speedBonus = 0;
						int senseBonus = 0;
						int sizeBonus = 0;
						int r = rand.nextInt(100);
						if (r < 10 && r % 2 == 0)
						{
							speedBonus = 4;
						}
						else if (r < 10)
						{
							speedBonus = -4;
						}
						r = rand.nextInt(100);
						if (r < 10 && r % 2 == 0)
						{
							senseBonus = 1;
						}
						else if (r < 10)
						{
							senseBonus = -1;
						}
						r = rand.nextInt(100);
						if (r < 10 && r % 2 == 0)
						{
							sizeBonus = 1;
						}
						else if (r < 10)
						{
							if (playerDudes.get(i).size > 1)
							{
								sizeBonus = -1;
								speedBonus += 4;
							}
						}
						if (playerDudes.get(i).size * 20 + sizeBonus * 10 + playerDudes.get(i).x < maxx)
						{
							new Dude(0, playerDudes.get(i).x + playerDudes.get(i).size * 10, playerDudes.get(i).y, playerDudes.get(i).size + sizeBonus, Math.max(4, playerDudes.get(i).speed + speedBonus), Math.max(1, playerDudes.get(i).sense + senseBonus), 1000, this);
						}
						else
						{
							new Dude(0, playerDudes.get(i).x - playerDudes.get(i).size * 10 - sizeBonus * 10, playerDudes.get(i).y, playerDudes.get(i).size + sizeBonus, Math.max(4, playerDudes.get(i).speed + speedBonus), Math.max(1, playerDudes.get(i).sense + senseBonus), 1000, this);
						}
					}
					playerDudes.get(i).fed = 0;
					playerDudes.get(i).energy = 1000;
				}
				display.pplLbl.setText("Population: " + playerDudes.size());
				double totalSpeed = 0;
				double totalSense = 0;
				double totalSize = 0;
				for (int i = 0; i < playerDudes.size(); i++)
				{
					totalSpeed += playerDudes.get(i).speed / 16.0 / playerDudes.size();
					totalSize += playerDudes.get(i).size / 4.0 / playerDudes.size();
					totalSense += playerDudes.get(i).sense / 4.0 / playerDudes.size();
				}
				display.speedLbl.setText("Avg speed: " + totalSpeed);
				display.sizeLbl.setText("Avg size: " + totalSize);
				display.senseLbl.setText("Avg sense: " + totalSense);
				if (playerDudes.size() > 0 && autoplay)
				{
					avgspd += totalSpeed;
					avgsze += totalSize;
					avgsns += totalSense;
					avgppl += playerDudes.size();
					lastdays.add(new Stats(totalSpeed, totalSense, totalSize, playerDudes.size()));
					if (day > 20)
					{
						avgspd -= lastdays.get(0).spd;
						avgsze -= lastdays.get(0).sze;
						avgsns -= lastdays.get(0).sns;
						avgppl -= lastdays.get(0).ppl;
						lastdays.remove(0);
					}
					display.totspeedLbl.setText("20 days avg speed: " + avgspd / Math.min(day, 20));
					display.totsizeLbl.setText("20 days avg size: " + avgsze / Math.min(day, 20));
					display.totsenseLbl.setText("20 days avg sense: " + avgsns / Math.min(day, 20));
					display.totpplLbl.setText("20 days avg population: " + avgppl / Math.min(day, 20));
					if (autoplay)
					{
						done = false;
						startRound();
						playing = true;
					}
				}
			}
		}
	}
	public void render()
	{
		display.repaint();
	}
	public void init()
	{
		display = new Display(title, width, height, this);
	}
	public void run() 
	{
		init();
		int fps = 30;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		while (running)
		{
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			lastTime = now;
			if (delta >= 1)
			{
				tick();
				render();
				delta --;
			}
		}
		stop();
	}
	public synchronized void start()
	{
		if (running)
		{
			return;
		}
		running = true;
//		b = new BasicPlayer();
//		try {
//			b.open(getClass().getResource("jazz.mp3"));
//			b.play();
//			isPlaying = true;
//		} catch (BasicPlayerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		startGame();
		thread = new Thread(this);
		thread.start();
	}
	public synchronized void stop()
	{
		if (!running)
		{
			return;
		}
		try
		{
			thread.join();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	public void startRound()
	{
		day ++;
		dayCounter ++;
		if (dayCounter >= foodDec && foodDec != 0)
		{
			removedFood ++;
			dayCounter = 0;
		}
		playing = true;
		display.dayLbl.setText("Day " + day);
		display.nextDay.setVisible(false);	
		foods.removeAll(foods);
		while (foods.size() < initialFood - removedFood || foods.size() < finalFood)
		{
			int ranx = rand.nextInt(size - 16) + minx;
			int rany = rand.nextInt(size - 16) + miny;
			while (ranx <= minx + maxSize && rany <= miny + maxSize && Math.pow(ranx + 8 - minx - maxSize, 2) + Math.pow(rany + 8 - miny - maxSize, 2) >= maxSize * maxSize
					|| ranx <= minx + maxSize && rany >= maxy - maxSize && Math.pow(ranx + 8 - minx - maxSize, 2) + Math.pow(rany + 8 - maxy + maxSize, 2) >= maxSize * maxSize
					|| ranx >= maxx - maxSize && rany <= miny + maxSize && Math.pow(ranx + 8 - maxx + maxSize, 2) + Math.pow(rany + 8 - miny - maxSize, 2) >= maxSize * maxSize
					|| ranx >= maxx - maxSize && rany >= maxy - maxSize && Math.pow(ranx + 8 - maxx + maxSize, 2) + Math.pow(rany + 8 - maxy + maxSize, 2) >= maxSize * maxSize)
			{
				ranx = rand.nextInt(size - 16) + minx;
				rany = rand.nextInt(size - 16) + miny;
			}
			new Food(ranx, rany, this);
		}
	}
	public void startGame()
	{
		new Dude(0, rand.nextInt(size) + minx, rand.nextInt(size) + miny, 4, 16, 4, 1000, this);
		new Dude(0, rand.nextInt(size) + minx, rand.nextInt(size) + miny, 4, 16, 4, 1000, this);
		new Dude(0, rand.nextInt(size) + minx, rand.nextInt(size) + miny, 4, 16, 4, 1000, this);
		new Dude(0, rand.nextInt(size) + minx, rand.nextInt(size) + miny, 4, 16, 4, 1000, this);
	}
}
