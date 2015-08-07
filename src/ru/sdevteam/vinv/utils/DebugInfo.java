package ru.sdevteam.vinv.utils;

public class DebugInfo
{	
	private static float paintFps=1, updateFps=1;
	
	public static synchronized float getPaintFPS() { return paintFps; }
	public static synchronized void setPaintFPS(float val) { paintFps=val; }
	public static synchronized float getUpdateFPS() { return updateFps; }
	public static synchronized void setUpdateFPS(float val) { updateFps=val; }
	
	private static String[] msg=new String[] { "", "", "", "" };
	public static String getMessage(int index) { return msg[index]; }
	
	public static void addMessage(String text) // уи-и-ха! индусы рулят!
	{ msg[0]=msg[1]; msg[1]=msg[2]; msg[2]=msg[3]; msg[3]=text; }
}
