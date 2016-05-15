/**
 * Enemy class is an Actor representing a potential battle.
 * @author Alexander Wong and Jiaming Chen
 * Period: 2
 * Date: 2016-05-01 (ISO)
 */

import info.gridworld.actor.Actor;
import java.awt.Color;

public class Enemy extends Actor{
	public static final Color ENEMY_COLOR = Color.WHITE;
	private EnemyType type;
	private int turns;
	
	public Enemy(EnemyType type, int turns)
	{
		super();
		this.type = type;
		this.turns = turns;
		setColor(ENEMY_COLOR);
	}
	
	public EnemyType getType() {
		return type;
	}
	
}
