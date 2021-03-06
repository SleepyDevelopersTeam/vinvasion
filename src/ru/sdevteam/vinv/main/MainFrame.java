package ru.sdevteam.vinv.main;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.SwingUtilities;

import ru.sdevteam.vinv.main.MouseEvent.Button;
import ru.sdevteam.vinv.utils.DebugInfo;



public class MainFrame extends Frame implements MouseListener, KeyListener, MouseWheelListener, MouseMotionListener
{
	private GameCanvas canvas;
	private Timer updateTimer;
	private Timer paintTimer;
	private int height;
	private int width;
	

	
	public MainFrame()
	{
		paintTimer = new Timer("Paint");
		updateTimer = new Timer("Update");
		
		Toolkit tool = Toolkit.getDefaultToolkit();
		Dimension screenSize = tool.getScreenSize();
		height = screenSize.height;
		width = screenSize.width;
		setSize(width, height);
		
		canvas = GameCanvas.createInstance(width, height);
		canvas.setSize(this.getSize());
		
		this.add(canvas);
		
		
		
		setUndecorated(true);
		setExtendedState(MAXIMIZED_BOTH);
		setFocusableWindowState(isEnabled());
		
		canvas.addKeyListener(this);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addMouseWheelListener(this);
		
		updateTimer.setInterval(33);
		updateTimer.addOnTickListener(new OnTickListener() 
		{
			long time;
			@Override
			public void onTick() 
			{
				time=System.currentTimeMillis();
				canvas.update();
				try
				{
					int interval=(int)(System.currentTimeMillis()-time);
					if(interval==0)
						DebugInfo.setUpdateFPS(9999.99F);
					else
						DebugInfo.setUpdateFPS(Math.round(1000F/interval));
				}
				catch(ArithmeticException ex)
				{
					DebugInfo.setUpdateFPS(9999.99F);
				}
			}
		});
		
		paintTimer.setInterval(33);
		paintTimer.addOnTickListener(new OnTickListener() {
			
			long time;
			@Override
			public void onTick() 
			{
				time=System.currentTimeMillis();
				canvas.repaint();
				try
				{
					int interval=(int)(System.currentTimeMillis()-time);
					if(interval==0)
						DebugInfo.setPaintFPS(9999.99F);
					else
						DebugInfo.setPaintFPS(Math.round(1000F/interval));
				}
				catch(ArithmeticException ex)
				{
					DebugInfo.setPaintFPS(9999.99F);
				}
			}
		});
		updateTimer.start();
		paintTimer.start();
		
		canvas.start();
		
		ResourceManager.init();
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
		//Nothing to do here...
	}
	@Override
	public void mouseEntered(MouseEvent arg0) 
	{
		//Nothing to do here...
	}
	@Override
	public void mouseExited(MouseEvent arg0) 
	{
		//Nothing to do here...
	}
	@Override
	public void mousePressed(MouseEvent e) 
	{
		if(SwingUtilities.isLeftMouseButton(e))
		{
			ru.sdevteam.vinv.main.MouseEvent m = new ru.sdevteam.vinv.main.MouseEvent(e.getX()/2, e.getY()/2, 0, 
					ru.sdevteam.vinv.main.MouseEvent.Type.PRESSED, ru.sdevteam.vinv.main.MouseEvent.Button.LEFT);
			Input.pushMouseEvent(m);	
		}
		else if (SwingUtilities.isRightMouseButton(e))
		{
			ru.sdevteam.vinv.main.MouseEvent m = new ru.sdevteam.vinv.main.MouseEvent(e.getX()/2, e.getY()/2, 0, 
					ru.sdevteam.vinv.main.MouseEvent.Type.PRESSED, ru.sdevteam.vinv.main.MouseEvent.Button.RIGHT);
			Input.pushMouseEvent(m);
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		if(SwingUtilities.isLeftMouseButton(e))
		{
			ru.sdevteam.vinv.main.MouseEvent m = new ru.sdevteam.vinv.main.MouseEvent(e.getX()/2, e.getY()/2, 0, 
					ru.sdevteam.vinv.main.MouseEvent.Type.RELEASED, ru.sdevteam.vinv.main.MouseEvent.Button.LEFT);
			Input.pushMouseEvent(m);
		}
		else if (SwingUtilities.isRightMouseButton(e))
		{
			ru.sdevteam.vinv.main.MouseEvent m = new ru.sdevteam.vinv.main.MouseEvent(e.getX()/2, e.getY()/2, 0, 
					ru.sdevteam.vinv.main.MouseEvent.Type.RELEASED, ru.sdevteam.vinv.main.MouseEvent.Button.RIGHT);
			Input.pushMouseEvent(m);
		}
	}
	@Override
	public void mouseDragged(MouseEvent e)
	{
		// TODO
		ru.sdevteam.vinv.main.MouseEvent.Button b=Button.NONE;
		if(SwingUtilities.isLeftMouseButton(e)) b=Button.LEFT;
		if(SwingUtilities.isRightMouseButton(e)) b=Button.RIGHT;
		ru.sdevteam.vinv.main.MouseEvent m = new ru.sdevteam.vinv.main.MouseEvent(e.getX()/2, e.getY()/2, 0, 
				ru.sdevteam.vinv.main.MouseEvent.Type.MOTION, b);
		Input.pushMouseEvent(m);
	}
	@Override
	public void mouseMoved(MouseEvent e) 
	{
		ru.sdevteam.vinv.main.MouseEvent m = new ru.sdevteam.vinv.main.MouseEvent(e.getX()/2, e.getY()/2, 0, 
				ru.sdevteam.vinv.main.MouseEvent.Type.MOTION, ru.sdevteam.vinv.main.MouseEvent.Button.NONE);
		Input.pushMouseEvent(m);
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) 
	{
		ru.sdevteam.vinv.main.MouseEvent m = new ru.sdevteam.vinv.main.MouseEvent(e.getX()/2, e.getY()/2, e.getWheelRotation(), 
				ru.sdevteam.vinv.main.MouseEvent.Type.SCROLL , ru.sdevteam.vinv.main.MouseEvent.Button.WHEEL);
		Input.pushMouseEvent(m);
	}
}
