package ru.sdevteam.vinv.ui.controls;

public class FlowLayout extends ContainerControl
{
	public enum LayoutType { CUSTOM, VERTICAL, HORIZONTAL }
	
	//
	// Тип компоновки
	//
	private LayoutType layout=LayoutType.CUSTOM;
	public LayoutType getLayout() { return layout; }
	public void setLayout(LayoutType type) { layout=type; reorganize(); }
	
	private HorizontalAlignment halign=HorizontalAlignment.LEFT;
	public HorizontalAlignment getHorizontalAlignment() { return halign; }
	public void setHorizontalAlignment(HorizontalAlignment a) { halign=a; reorganize(); }
	private VerticalAlignment valign=VerticalAlignment.TOP;
	public VerticalAlignment getVerticalAlignment() { return valign; }
	public void setVerticalAlignment(VerticalAlignment a) { valign=a; reorganize(); }
	
	//
	// Размерности компоновки
	//
	private int xStart, yStart, margin;
	public int getStartPointX() { return xStart; }
	public int getStartPointY() { return yStart; }
	public int getMargin() { return margin; }
	
	public void setStartPoint(int x, int y)
	{ xStart=x; yStart=y; reorganize(); }
	
	public void setMargin(int m) { margin=m; reorganize(); }
	
	
	public FlowLayout(LayoutType type)
	{
		super();
		layout=type;
	}
	public FlowLayout(int x, int y, int width, int height, LayoutType type)
	{
		super(x, y, width, height);
		layout=type;
		xStart=x;
		yStart=y;
	}
	
	
	@Override
	public void addControl(Control c)
	{
		if(layout==LayoutType.HORIZONTAL)
		{
			if(controls.size()>0)
				c.setX(controls.lastElement().getX()+controls.lastElement().getWidth()+margin);
			else
				c.setX(xStart);
			
			alignY(c, yStart);
		}
		else
		{
			if(controls.size()>0)
				c.setY(controls.lastElement().getY()+controls.lastElement().getHeight()+margin);
			else
				c.setY(yStart);
			
			alignX(c, xStart);
		}
		
		super.addControl(c);
	}
	
	@Override
	public void removeControl(Control c)
	{
		super.removeControl(c);
		reorganize();
	}
	
	protected void reorganize()
	{
		if(controls.size()==0) return;
		if(layout==LayoutType.CUSTOM) return;
		
		Control c=controls.elementAt(0);
		c.moveTo(xStart, yStart);
		for(int i=1; i<controls.size(); i++)
		{
			c=controls.elementAt(i-1);
			if(layout==LayoutType.HORIZONTAL)
			{
				controls.elementAt(i).setX(c.getX()+c.getWidth()+margin);
				alignY(controls.elementAt(i), yStart);
			}
			else
			{
				controls.elementAt(i).setY(c.getY()+c.getHeight()+margin);
				alignX(controls.elementAt(i), xStart);
			}
		}
		c=controls.lastElement();
		if(layout==LayoutType.HORIZONTAL)
		{
			setWidth(c.getX()+c.getWidth()-getX());
		}
		else
		{
			setHeight(c.getY()+c.getHeight()-getY());
		}
	}
	private void alignY(Control c, int y)
	{
		switch(valign)
		{
		case TOP:
			c.setY(y);
			break;
		case CENTER:
			c.setY(y-c.getHeight()/2);
			break;
		case BOTTOM:
			c.setY(y-c.getHeight());
			break;
		}
	}
	private void alignX(Control c, int x)
	{
		switch(halign)
		{
		case LEFT:
			c.setX(x);
			break;
		case CENTER:
			c.setX(x-c.getWidth()/2);
			break;
		case RIGHT:
			c.setX(x-c.getWidth());
			break;
		}
	}
	
	@Override
	protected void onLocationChanged(int oldx, int oldy)
	{
		super.onLocationChanged(oldx, oldy);
		xStart+=getX()-oldx;
		yStart+=getY()-oldy;
		reorganize();
	}
}
