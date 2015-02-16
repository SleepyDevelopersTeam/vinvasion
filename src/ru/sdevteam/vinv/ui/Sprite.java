package ru.sdevteam.vinv.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import ru.sdevteam.vinv.game.IMoveable;

public class Sprite implements IDrawable, IUpdatable, IMoveable
{
	//
	//  оординаты
	//
	private float x=0F, y=0F;
	public float getX() { return x; }
	public float getY() { return y; }
	public void setX(float nx) { x=nx; }
	public void setY(float ny) { y=ny; }
	public void moveTo(float nx, float ny) { x=nx; y=ny; }
	public void moveBy(float dx, float dy) { x+=dx; y+=dy; }
	
	//
	// –азмеры
	//
	private int w=0, h=0;
	public int getWidth() { return w; }
	public int getHeight() { return h; }
	
	//
	//  артинка
	//
	private BufferedImage source=null;
	public int getSourceWidth()
	{
		if(source!=null) return source.getWidth();
		return 0;
	}
	public int getSourceHeight()
	{
		if(source!=null) return source.getHeight();
		return 0;
	}
	
	//
	// ќтображение
	//
	private boolean visible=true;
	public boolean isVisible() { return visible; }
	public void setVisibility(boolean flag) { visible=flag; }
	public void show() { visible=true; }
	public void hide() { visible=false; }
	
	//
	// ¬оспроизведение
	//
	private boolean playing=false;
	public boolean isPlaying() { return playing; }
	public void play()
	{
		playing=true;
		lastChangeTime=System.currentTimeMillis();
	}
	public void pause()
	{
		playing=false;
		timeWithoutChanges+=(int)(System.currentTimeMillis()-lastChangeTime);
	}
	
	private int curFrame=0, totalFrames=0;
	public int currentFrame() { return curFrame; }
	public synchronized void nextFrame()
	{
		curFrame++;
		if(curFrame>=totalFrames) curFrame=0;
		lastChangeTime=System.currentTimeMillis();
		onEnterFrame();
	}
	public synchronized void prevFrame()
	{
		curFrame--;
		if(curFrame<0) curFrame=totalFrames-1;
		lastChangeTime=System.currentTimeMillis();
		onEnterFrame();
	}
	public synchronized void gotoFrame(int frame)
	{
		assert frame>=0 && frame<totalFrames: "Ќомер кадра должен быть неотрицательным и меньше количества кадров";
		curFrame=frame;
		lastChangeTime=System.currentTimeMillis();
		onEnterFrame();
	}
	
	public int getFramesCount() { return totalFrames; }
	private int fpw; // frames per width (of source img.)
	private void countFrames()
	{
		if(source==null)
		{
			totalFrames=0;
			return;
		}
		if(getSourceWidth()==w)
		{
			if(getSourceHeight()==h)
			{
				totalFrames=1;
				return;
			}
		}
		// –аботает корректно только дл€ ровного количества кадров
		totalFrames=(getSourceWidth()*getSourceHeight())/(w*h);
		
		fpw=getSourceWidth()/w;
	}
	
	private int frameDur=33; // длительность одного кадра в миллисекундах
	public int getFrameDuration() { return frameDur; }
	public void setFrameDuration(int millis) { frameDur=millis; }
	
	private long lastChangeTime=-1;
	private int timeWithoutChanges=0;
	
	
	public Sprite()
	{
		countFrames();
		cw=ch=0;
	}
	public Sprite(BufferedImage src)
	{
		source=src;
		cw=w=getSourceWidth();
		ch=h=getSourceHeight();
		countFrames();
		play();
	}
	public Sprite(BufferedImage src, int width, int height)
	{
		source=src;
		cw=w=width; ch=h=height;
		countFrames();
		play();
	}
	
	
	@Override
	public synchronized void update()
	{
		if(source==null) return;
		if(playing)
		{
			if(timeWithoutChanges>=frameDur)
			{
				nextFrame();
				lastChangeTime=System.currentTimeMillis();
				timeWithoutChanges-=frameDur;
				onEnterFrame();
			}
			else
			{
				timeWithoutChanges+=(int)(System.currentTimeMillis()-lastChangeTime);
			}
		}
	}

	@Override
	public synchronized void paint(Graphics g)
	{
		int framex, framey;
		framex=curFrame%fpw;
		framey=curFrame/fpw;
		
		Image frame=source.getSubimage(framex, framey, w, h);
		// TODO: здесь решить, как отрисовывать спрайты с дробными координатами
		g.drawImage(frame, (int)x, (int)y, null);
	}
	
	protected void onEnterFrame()
	{
		// ...
	}
	
	//
	// —толкновени€
	//
	
	// collision rectangle
	private int cx=0, cy=0, cw, ch;
	
	public void setCollisionRectangle(int x, int y, int width, int height)
	{
		cx=x; cy=y; cw=width; ch=height;
	}
	
	// проверка столкновени€ с точкой (x;y)
	public boolean contains(int x, int y)
	{
		if(x<this.x+cx) return false;
		if(x>this.x+cx+cw) return false;
		if(y<this.y+cy) return false;
		if(y>this.y+cy+ch) return false;
		return true;
	}
	
	public boolean collidesWith(Sprite s)
	{
		if(x+cx<s.x+s.cx)
		{
			if(s.x+s.cx+s.cw<x+cx) return false;
		}
		else
		{
			if(s.x+s.cx>x+cx+cw) return false;
		}
		// столкновение по x прошло
		
		if(y+cy<s.y+s.cy)
		{
			if(s.y+s.cy+s.ch<y+cy) return false;
		}
		else
		{
			if(s.y+s.cy>y+cy+ch) return false;
		}
		// столкновение по y прошло
		
		return true;
	}
	
	// проверка столкновени€ с пр€моугольником
	public boolean collidesWith(float rx, float ry, float rWidth, float rHeight)
	{
		if(x+cx<rx)
		{
			if(rx+rWidth<x+cx) return false;
		}
		else
		{
			if(rx>x+cx+cw) return false;
		}
		// столкновение по x прошло
		
		if(y+cy<ry)
		{
			if(ry+rHeight<y+cy) return false;
		}
		else
		{
			if(ry>y+cy+ch) return false;
		}
		// столкновение по y прошло
		
		return true;
	}
}
