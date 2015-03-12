package ru.sdevteam.vinv.main;

import java.util.Vector;

public class Timer extends Thread
{
	private int interval;
	private Vector<OnTickListener> listeners;
	private boolean paused;
	
	public Timer(String name)
	{
		super(name);
		this.listeners = new Vector<OnTickListener>();
		paused = true;
		setDaemon(true);
	}
	
	public int getInterval()
	{
		return this.interval;
	}
	
	public void setInterval(int val)
	{
		interval = val;
	}
	
	public void start()
	{
		super.start();
		this.paused = false;
	}
	
	public void pause()
	{
		this.paused = true;
	}

	public void unpause()
	{
		this.paused = false;
	}
		
	public boolean isPaused()
	{
		return this.paused;
	}

	@Override
	public void run()
	{
		while (true)
		{
			if (this.isPaused()) { continue; }
			long curr = System.currentTimeMillis();
			for (int i=0; i < this.listeners.size(); i++)
			{
				this.listeners.elementAt(i).onTick();
			}
			
			try
			{
				int dur = (int)(System.currentTimeMillis() - curr);
				if (dur < this.interval)
				{
					sleep(this.interval - dur);
				}
			}
			catch (InterruptedException io)
			{
				System.out.println("Interrupted exception" + io);
				break;
			}	
		}
	}

	public void addOnTickListener(OnTickListener item)
	{
		this.listeners.add(item);
	}

	public void removeOnTickListener(OnTickListener item)
	{
		this.listeners.remove(item);
	}

	
}
