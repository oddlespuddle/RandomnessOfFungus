
public enum EnemyType {
	RNGESUS("YOU ARE EXPERIENCING AN APPARITION OF RNGESUS",
			"EnemySprites/RNGesus.jpg",
			"EnemyMusic/RNGesus.wav"),
	TROLL("I AM A TROLL",
			"EnemySprites/Troll.jpg",
			"EnemyMusic/Troll.wav"),
	RICK_ASTLEY("NEVER GONNA GIVE YOU UP",
			"EnemySprites/RickAstley.gif",
			"EnemyMusic/RickAstley.wav"),
	NYAN_CAT("NYANNYANYANYANYANYAN",
			"EnemySprites/NyanCat.jpg",
			"EnemyMusic/NyanCat.wav");
	
	private final String text;
	private final String spritePath;
	private final String musicPath;
	
	EnemyType(String text, String spritePath, String musicPath)
	{
		this.text = text;
		this.spritePath = spritePath;
		this.musicPath = musicPath;
	}
	
	public String getText()
	{
		return text;
	}
	
	public String getSpritePath() 
	{
		return spritePath;
	}
	
	public String getMusicPath()
	{
		return musicPath;
	}
}
