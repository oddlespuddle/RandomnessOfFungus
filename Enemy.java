/**
 * Enemy class is an Actor representing a potential battle.
 * @author Alexander Wong and Jiaming Chen
 * Period: 2
 * Date: 2016-05-14 (ISO)
 */

import info.gridworld.actor.Actor;
import java.awt.Color;

public class Enemy extends Actor{
	public static final Color ENEMY_COLOR = Color.WHITE;
	private EnemyType type;
	
	/**
	 * Stores the type of this enemy and makes it appear white.
	 * @param type - This enemy's type.
	 */
	public Enemy(EnemyType type)
	{
		super();
		this.type = type;
		setColor(ENEMY_COLOR);
	}
	
	/**
	 * Returns this enemy's type.
	 */
	public EnemyType getType() 
	{
		return type;
	}
	
}
