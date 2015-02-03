package ru.sdevteam.vinv.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Sprite implements IDrawable, IUpdatable
{
	//
	// Координаты
	//
	private float x=0F, y=0F;
	public float getX() { return x; }
	public float getY() { return y; }
	public void setX(float nx) { x=nx; }
	public void setY(float ny) { y=ny; }
	public void moveTo(float nx, float ny) { x=nx; y=ny; }
	public void moveBy(float dx, float dy) { x+=dx; y+=dy; }
	
	//
	// Размеры
	//
	private int w=0, h=0;
	public int getWidth() { return w; }
	public int getHeight() { return h; }
	
	//
	// Картинка
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
	// Отображение
	//
	private boolean visible=true;
	public boolean isVisible() { return visible; }
	public void setVisibility(boolean flag) { visible=flag; }
	public void show() { visible=true; }
	public void hide() { visible=false; }
	
	//
	// Воспроизведение
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
	public void nextFrame()
	{
		// TODO: use syncronized
		curFrame++;
		if(curFrame>=totalFrames) curFrame=0;
		lastChangeTime=System.currentTimeMillis();
	}
	public void prevFrame()
	{
		// TODO: use syncronized
		curFrame--;
		if(curFrame<0) curFrame=totalFrames-1;
		lastChangeTime=System.currentTimeMillis();
	}
	public void gotoFrame(int frame)
	{
		assert frame>=0 && frame<totalFrames: "Номер кадра должен быть неотрицательным и меньше количества кадров";
		curFrame=frame;
		lastChangeTime=System.currentTimeMillis();
	}
	
	public int getFramesCount() { return totalFrames; }
	private int fpw, fph;
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
		// Работает корректно только для ровного количества кадров
		totalFrames=(getSourceWidth()*getSourceHeight())/(w*h);
		
		fpw=getSourceWidth()/w;
		fph=getSourceHeight()/h;
	}
	
	private int frameDur=33; // длительность одного кадра в миллисекундах
	public int getFrameDuration() { return frameDur; }
	public void setFrameDuration(int millis) { frameDur=millis; }
	
	private long lastChangeTime=-1;
	private int timeWithoutChanges=0;
	
	
	public Sprite()
	{
		countFrames();
	}
	public Sprite(BufferedImage src)
	{
		source=src;
		w=getSourceWidth();
		h=getSourceHeight();
		countFrames();
		play();
	}
	public Sprite(BufferedImage src, int width, int height)
	{
		source=src;
		w=width; h=height;
		countFrames();
		play();
	}
	
	
	@Override
	public void update()
	{
		if(source==null) return;
		if(playing)
		{
			if(timeWithoutChanges>=frameDur)
			{
				nextFrame();
				lastChangeTime=System.currentTimeMillis();
				timeWithoutChanges-=frameDur;
			}
			else
			{
				timeWithoutChanges+=(int)(System.currentTimeMillis()-lastChangeTime);
			}
		}
	}

	@Override
	public void paint(Graphics g)
	{
		// TODO: implement painting
		int framex, framey;
		framex=curFrame%fpw;
		framey=curFrame/fpw;
		
		Image frame=source.getSubimage(framex, framey, w, h);
		// TODO: здесь решить, как отрисовывать спрайты с дробными координатами
		g.drawImage(frame, (int)x, (int)y, null);
	}
	
	//
	// Столкновения
	//
	
	// collision rectangle
	private int cx, cy, cw, ch;
	
	public void setCollisionRectangle(int x, int y, int width, int height)
	{
		cx=x; cy=y; cw=width; ch=height;
	}
	
	
}
