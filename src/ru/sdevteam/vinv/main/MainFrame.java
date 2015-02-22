package ru.sdevteam.vinv.main;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;



public class MainFrame extends Frame implements MouseListener, KeyListener
{
	private GameCanvas canvas;
	private Timer updateTimer;
	private Timer paintTimer;
	

	
	public MainFrame()
	{
		paintTimer = new Timer("Update");
		updateTimer = new Timer("Paint");
		canvas = new GameCanvas();
		
		setUndecorated(true);
		setExtendedState(MAXIMIZED_BOTH);
		
		addKeyListener(this);
		addMouseListener(this);
				      
		ResourceManager.init();
		
		updateTimer.setInterval(30);
		updateTimer.addOnTickListener(new OnTickListener() 
		{
			
			@Override
			public void onTick() 
			{
				canvas.update();
			}
		});
		
		paintTimer.setInterval(30);
		paintTimer.addOnTickListener(new OnTickListener() {
			
			@Override
			public void onTick() 
			{
				repaint();
			}
		});
		updateTimer.start();
		paintTimer.start();
	}
	@Override
	public void keyPressed(KeyEvent e) 
	{
		ru.sdevteam.vinv.main.KeyEvent key = new ru.sdevteam.vinv.main.KeyEvent(e.getKeyCode(), e.getKeyChar(), 
				ru.sdevteam.vinv.main.KeyEvent.Type.PRESSED );
		Input.pushKeyEvent(key);
	}
	@Override
	public void keyReleased(KeyEvent e) 
	{
		ru.sdevteam.vinv.main.KeyEvent key = new ru.sdevteam.vinv.main.KeyEvent(e.getKeyCode(), e.getKeyChar(), 
				ru.sdevteam.vinv.main.KeyEvent.Type.RELEASED);
		Input.pushKeyEvent(key);	
	}
	@Override
	public void keyTyped(KeyEvent e) 
	{
		ru.sdevteam.vinv.main.KeyEvent key = new ru.sdevteam.vinv.main.KeyEvent(e.getKeyCode(), e.getKeyChar(), 
				ru.sdevteam.vinv.main.KeyEvent.Type.TYPED);
		Input.pushKeyEvent(key);
	}
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		// TODO Auto-generated method stub	
	}
	@Override
	public void mouseEntered(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseExited(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub	
	}
	@Override
	public void mousePressed(MouseEvent e) 
	{
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseReleased(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
	}
}
