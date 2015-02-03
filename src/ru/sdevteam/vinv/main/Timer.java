package ru.sdevteam.vinv.main;

public class Timer implements Runnable
{
	int interval;
	
	public int getInterval()
	{
		return 0;
	}
	
	public void setInterval(int val)
	{
		interval = val;
		return;
	}
	
	public void start()
	{
		return;
	}
	
	public void pause()
	{
		return;
	}

	public void unpause()
	{
		return;
	}
	
	public boolean isRunning()
	{
		return false;
	}

	@Override
	public void run()
	{
		return;
	}

	public void addOnTickListener(OnTickListener item)
	{
		return;
	}

	public void removeOnTickListener(OnTickListener item)
	{
		return;
	}

	
}
