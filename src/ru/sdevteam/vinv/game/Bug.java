package ru.sdevteam.vinv.game;

public class Bug extends Destructable
{
    public enum Type {NULL,NORMAL,AIR};
	
	protected boolean active=true;
	public boolean isActive(){return active;}
	public void setActive(boolean b){active=b;}
	
	private boolean air;
	public boolean isAir(){return air;}
	
    private Bug.Type type;
    public Bug.Type getType(){return type;}

    private String name;
    public String getName(){return name;}

    private int speed;
    public int getSpeed(){return speed;}
	public void setSpeed(int s){speed=s;}
	
	protected int cost;
	public int getCost(){return cost;}

    public Bug()
    {
        setType(Type.NULL);
        sprite= new BugSprite(this);
    }

	public static Bug getBug(Bug.Type type)
	{
		Bug b = new Bug();
		b.setType(type);
		return b;
	}
	
	@Override
	public void bindEffect(Effect eff)
	{
		super.bindEffect(eff);
		
	}
	
    public  void onDestructed(){}

    public void setType(Type type)
    {
        this.type=type;
        switch(this.type)
        {
            case NULL:   speed = 0;
                         setMaxHp(0);
                         setHp(0);
						 cost = 0;
                         break;
            case NORMAL: speed = 5;//(int)(2+Math.random()*5);
                         setMaxHp(100);
                         setHp(50);
						 cost = 2;
                         break;
			case AIR:	speed = 7;//(int)(2+Math.random()*5);
						setMaxHp(70);
						setHp(70);
						cost = 1;
						break;
        }
    }
	
    //String getDescription();
    //(Buffered)Image getImage(); // возвращает изображение жука
	
	public void update()
    {
    	super.update();
    }

}
