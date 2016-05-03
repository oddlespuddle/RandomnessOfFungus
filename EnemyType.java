
public enum EnemyType {
	TEST_ENEMY("YOU ARE EXPERIENCING AN APPARITION OF RNGESUS",
			"EnemySprites/RNGesusSprite.jpg",
			"EnemyMusic/RickRoll.wav"),
	TUTORIAL("I AM A TROLL",
			"EnemySprites/TrollGuyCover.jpg",
			"EnemyMusic/trollsong.wav");
	
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
