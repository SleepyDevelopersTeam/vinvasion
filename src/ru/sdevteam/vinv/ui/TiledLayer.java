package ru.sdevteam.vinv.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import ru.sdevteam.vinv.game.IMoveable;

public class TiledLayer implements IMoveable
{
	BufferedImage mapImage;
	int tileWidth;
	int tileHeight;
	int tilesWidth;
	int tilesHeight;
	
	public TiledLayer(BufferedImage source, int tileWidth, int tileHeight, int tilesWidth, int tilesHeight)
	{
		mapImage=source;
		this.tileHeight=tileHeight;
		this.tilesHeight=tilesHeight;
		this.tileWidth=tileWidth;
		this.tilesWidth=tilesWidth;
		
	};

	public void setMap(int[][] tiles)
	{
		
	}
	public void setTileIndexAt(int row, int col, int index)
	{
		
	}

	public int getTileIndexAt(int row, int col)
	{
		return 1;
	}

	protected BufferedImage getTileImage(int index)
	{
		return;
	}

	public void paint(Graphics g, float x, float y, float w, float h)
	{
		
	}
	
	public int getPixelsWidth()
	{
		return ;
	}
	public int getPixelsHeight()
	{
		return;
	}

	public int getTileWidth()
	{
		return tilesWidth;
	}
	public int getTileHeight()
	{
		return tileHeight;
	}

	public int getTilesWidth()
	{
		return tilesWidth;
	}
	public int getTilesHeight()
	{
		return tilesHeight;
	}
	
	@Override
	public float getX() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getY() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setX(float nx) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setY(float ny) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveTo(float nx, float ny) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveBy(float dx, float dy) 
	{
		// TODO Auto-generated method stub
		
	}
	
}
