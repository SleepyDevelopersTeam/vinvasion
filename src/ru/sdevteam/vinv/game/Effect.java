package ru.sdevteam.vinv.game;

public enum Effect
{
    NONE, DAMAGE, BURNING, FROST, POISON;
    private Effect currentEffect;
    public void affect(Bug b)
    {
    	 switch(this.currentEffect)
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
}
