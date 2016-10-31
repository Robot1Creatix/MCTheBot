package com.creatix.mc.thebot.bot;

import java.awt.Color;

public class Classification {
	
	public String name, id;
	public float accessLevel;
	
	public static enum ClassType{ 
		IRRELEVANT(Color.white), CONTINGENCY(Color.yellow), ANALOGUEINTERFACE(Color.yellow, Color.black), THREAT(Color.red), ENEMYAGENT(Color.red, Color.white);
		public Color c1, c2;
		ClassType(Color c1, Color c2) {
			this.c1 = c1;
			this.c2 = c2;
		}
		
		ClassType(Color c) {
			this(c, c);
		}
	}
	public ClassType type;
	public Classification(String name, String id, float accessLevel, ClassType t) {
		this.name = name;
		this.id = id;
		this.accessLevel = accessLevel;
		this.type = t;
	}
}
