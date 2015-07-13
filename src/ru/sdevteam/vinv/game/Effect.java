package ru.sdevteam.vinv.game;

public enum Effect
{
    NONE, DAMAGE, BURNING, FROST, POISON;
    public void affect(Bug b)
    {
    	 switch(this)
         {
             case NONE:
            	 break;
             case DAMAGE:
            	 b.setHp(b.getHp()-20);
            	 break;
             case BURNING:
            	 b.setHp(b.getHp()-10);
            	 			break;
             case FROST:
            	//TODO:speed
            	 			break;
             case POISON:
            	 b.setHp(b.getHp()-5);
            	 			break;
                          
         }
    }
    public String getName()
    {
    	switch(this)
        {
            case NONE:
            	return "NONE";
            case DAMAGE:
            	return "DAMAGE";
            case BURNING:
            	return "BURNING";
            case FROST:
            	return "FROST";
            case POISON:
            	return "POISON";
                         
        }
    	return null;
    }
}
