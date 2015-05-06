package ru.sdevteam.vinv.main;

public class Profile 
{
	private String name;
	private int level;
	private Profile activeProfile;
	
	Profile (String aName, int aLevel)
	{
		level = aLevel;
		name = aName;
	}
	
	public void setActiveProfile(Profile aProfile)
	{
		activeProfile = aProfile;
	}
	
	public Profile getActiveProfile()
	{
		return activeProfile;
	}
	
	
	public static String[] allProfiles;
}
