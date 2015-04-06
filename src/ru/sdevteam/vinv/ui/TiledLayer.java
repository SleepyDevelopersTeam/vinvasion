package ru.sdevteam.vinv.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import ru.sdevteam.vinv.game.IMoveable;

public class TiledLayer implements IMoveable
{
	private BufferedImage mapImage;
	private int tileWidth;
	private int tileHeight;
	private int tilesWidth;
	private int tilesHeight;
	private int map[][];
	private float x;
	private float y;
	
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
		for (int i=0;i<tiles.length;i++)
			for (int j=0;j<tiles[i].length;j++)
				map[i][j]=tiles[i][j];
	}
	public void setTileIndexAt(int row, int col, int index)
	{
		map[row][col]=index;
	}

	public int getTileIndexAt(int row, int col)
	{
		return map[row][col];
	}

	protected BufferedImage getTileImage(int index)
	{
		int x=index%tilesWidth*tileWidth;
		int y=index/tilesWidth*tileHeight;
		return this.mapImage.getSubimage(x, y, tileWidth, tileHeight);
	}
	public void paint(Graphics g, float x, float y, float w, float h)
	{
		int xTileLU=(int)x/(int)getTileWidth();
		int yTileLU=(int)y/(int)getTileHeight();
		int xTileRB=(int)(x+w)/(int)getTileWidth();
		int yTileRB=(int)(y+h)/(int)getTileHeight();
		int i=xTileLU;
		int j=yTileLU;
		while (j<=yTileRB)
		{
			while (i<=xTileRB)
			{
				//TO DO: optimize!
				int numberOfArea=map[j][i];
				g.drawImage(getTileImage(numberOfArea), (int)j*tileWidth, (int)i*tileHeight, null);
				i++;
			}
			i=xTileLU;
			j++;
		}
	}
	
	public int getPixelsWidth()
	{
		return tilesWidth*tileWidth;
	}
	public int getPixelsHeight()
	{
		return tilesHeight*tileHeight;
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
		return x;
		
	}

	@Override
	public float getY() 
	{
		return y;
	}

	@Override
	public void setX(float nx) 
	{
		x=nx;
		
	}

	@Override
	public void setY(float ny) 
	{
		y=ny;
	}

	@Override
	public void moveTo(float nx, float ny) 
	{
		x=nx;
		y=ny;	
	}
	
	@Override
	public void moveBy(float dx, float dy) 
	{
		x+=dx;
		y+=dy;
	}
}
