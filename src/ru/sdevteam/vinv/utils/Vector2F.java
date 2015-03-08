package ru.sdevteam.vinv.utils;

public class Vector2F
{
	public static final float 	UP		=(float)Math.PI*3/2,
								DOWN	=(float)Math.PI/2,
								LEFT	=(float)Math.PI,
								RIGHT	=0F;
	
	public static final Vector2F ex=new Vector2F(1F, 0F),
								ey=new Vector2F(0F, 1F),
								O=new Vector2F(0F, 0F);
	
	private float x, y;
	
	//
	// CONSTRUCTORS
	//
	
	public Vector2F(float x, float y)
	{ this.x=x; this.y=y; }
	
	public Vector2F(Vector2F direction, float magnitude)
	{
		Vector2F v=direction.copy();
		v.setMagnitude(magnitude);
		this.x=v.x; this.y=v.y;
	}
	public Vector2F(float dx, float dy, float mag)
	{
		x=dx; y=dy;
		if(!(dx==dy && dy==0)) setMagnitude(mag);
	}
	
	//
	// COORDINATES
	//
	
	public float getX() { return x; }
	public float getY() { return y; }
	public void setX(float val) { x=val; }
	public void setY(float val) { y=val; }
	public void moveTo(float nx, float ny)
	{ x=nx; y=ny; }
	public void moveBy(float dx, float dy)
	{ x+=dx; y+=dy; }
	
	//
	// POLAR
	//
	
	public float getMagnitude()
	{
		return (float)Math.sqrt(x*x+y*y);
	}
	public float getDirection()
	{
		if(y==0 && x==0) return Float.NaN;
		if(y==0) return x>0? RIGHT : LEFT;
		if(x==0) return y>0? DOWN : UP;
		// приводим из (-п;п) к промежутку (0; 2п)
		// (2п никогда не будет возвращено по причине,
		// что это значение отлавливается одним из
		// условий выше)
		return (float)Math.atan2(y, x)+(y<0?LEFT*2:0F);
		// (LEFT==(float)п)
	}
	
	public void setMagnitude(float mag)
	{
		assert mag>0: "Magnitude should be positive or zero";
		multipleBy(mag/getMagnitude());
	}
	public void rotate(float newAngle)
	{
		float mag=getMagnitude();
		x=(float)Math.cos(newAngle)*mag;
		y=(float)Math.sin(newAngle)*mag;
	}
	
	
	//
	// OPERATIONS
	//
	
	public void multipleBy(float coeff)
	{
		x*=coeff; y*=coeff;
	}
	public void divideBy(float coeff)
	{
		x/=coeff; y/=coeff;
	}
	public void add(Vector2F v)
	{
		x+=v.x; y+=v.y;
	}
	public void sub(Vector2F v)
	{
		x-=v.x; y-=v.y;
	}
	
	
	//
	// OTHER
	//
	
	public Vector2F copy()
	{ return new Vector2F(x, y); }
	
	public Vector2F getUnitary()
	{
		Vector2F v=copy();
		v.setMagnitude(1);
		/* or
		float a=v.getDirection();
		v.x=(float)Math.cos(a);
		v.y=(float)Math.sin(a);
		*/
		return v;
	}
	
	@Override
	public String toString()
	{
		return "("+x+"; "+y+")";
	}
	
	//
	// STATIC MATH
	//
	
	// Метод округляет значение angle до некоторого угла из дискретного спектра
	// мощности spectrumWidth
	public static int getDiscreteRotation(int spectrumWidth, float angle)
	{
		// ширина сегмента (расстояние между соседними значениями спектра)
		float segmentWidth=(float)Math.PI*2/spectrumWidth;
		// округляем
		int dr=(int)(angle/segmentWidth+0.5F);
		if(dr==spectrumWidth) dr=0;
		return dr;
	}
}
