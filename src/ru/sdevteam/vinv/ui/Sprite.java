package ru.sdevteam.vinv.ui;

import java.awt.Graphics;
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
	public void setVisible(boolean flag) { visible=flag; }
	public void show() { visible=true; }
	public void hide() { visible=false; }
	
	//
	// Воспроизведение
	//
	private boolean playing=false;
	public boolean isPlaying() { return playing; }
	public void play() { playing=true; }
	public void pause() { playing=false; }
	
	private int curFrame=0, totalFrames=0;
	public int currentFrame() { return curFrame; }
	public void nextFrame()
	{
		// TODO: use syncronized
		curFrame++;
		if(curFrame>=totalFrames) curFrame=0;
	}
	public void prevFrame()
	{
		// TODO: use syncronized
		curFrame--;
		if(curFrame<0) curFrame=totalFrames-1;
	}
	public void gotoFrame(int frame)
	{
		// TODO: implement
	}
	
	public int getFramesCount() { return totalFrames; }
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
	}
	
	private int frameDur=33; // длительность одного кадра в миллисекундах
	public int getFrameDuration() { return frameDur; }
	public void setFrameDuration(int millis) { frameDur=millis; }
	
	
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
	}
	public Sprite(BufferedImage src, int width, int height)
	{
		source=src;
		w=width; h=height;
		countFrames();
	}
	
	
	@Override
	public void update()
	{
		// TODO: implement frame changing
	}

	@Override
	public void paint(Graphics g)
	{
		// TODO: implement painting
	}
}
