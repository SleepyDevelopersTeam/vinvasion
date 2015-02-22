package ru.sdevteam.vinv.main;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.SwingUtilities;



public class MainFrame extends Frame implements MouseListener, KeyListener, MouseWheelListener, MouseMotionListener
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
		if(SwingUtilities.isLeftMouseButton(e))
		{
			ru.sdevteam.vinv.main.MouseEvent m = new ru.sdevteam.vinv.main.MouseEvent(e.getX(), e.getY(), 0, 
					ru.sdevteam.vinv.main.MouseEvent.Type.PRESSED, ru.sdevteam.vinv.main.MouseEvent.Button.LEFT);
			Input.pushMouseEvent(m);
		}
		else if (SwingUtilities.isRightMouseButton(e))
		{
			ru.sdevteam.vinv.main.MouseEvent m = new ru.sdevteam.vinv.main.MouseEvent(e.getX(), e.getY(), 0, 
					ru.sdevteam.vinv.main.MouseEvent.Type.PRESSED, ru.sdevteam.vinv.main.MouseEvent.Button.LEFT);
			Input.pushMouseEvent(m);
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		if(SwingUtilities.isLeftMouseButton(e))
		{
			ru.sdevteam.vinv.main.MouseEvent m = new ru.sdevteam.vinv.main.MouseEvent(e.getX(), e.getY(), 0, 
					ru.sdevteam.vinv.main.MouseEvent.Type.RELEASED, ru.sdevteam.vinv.main.MouseEvent.Button.LEFT);
			Input.pushMouseEvent(m);
		}
		else if (SwingUtilities.isRightMouseButton(e))
		{
			ru.sdevteam.vinv.main.MouseEvent m = new ru.sdevteam.vinv.main.MouseEvent(e.getX(), e.getY(), 0, 
					ru.sdevteam.vinv.main.MouseEvent.Type.RELEASED, ru.sdevteam.vinv.main.MouseEvent.Button.LEFT);
			Input.pushMouseEvent(m);
		}
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) 
	{
		ru.sdevteam.vinv.main.MouseEvent m = new ru.sdevteam.vinv.main.MouseEvent(e.getX(), e.getY(), 0, 
				ru.sdevteam.vinv.main.MouseEvent.Type.MOTION, ru.sdevteam.vinv.main.MouseEvent.Button.NONE);
		Input.pushMouseEvent(m);
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) 
	{
		ru.sdevteam.vinv.main.MouseEvent m = new ru.sdevteam.vinv.main.MouseEvent(e.getX(), e.getY(), e.getWheelRotation(), 
				ru.sdevteam.vinv.main.MouseEvent.Type.SCROLL , ru.sdevteam.vinv.main.MouseEvent.Button.WHEEL);
		Input.pushMouseEvent(m);
	}
}
