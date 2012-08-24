/*
 *   This file is part of Skript.
 *
 *  Skript is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Skript is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Skript.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * 
 * Copyright 2011, 2012 Peter Güttinger
 * 
 */

package ch.njol.skript.conditions;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Checker;

/**
 * @author Peter Güttinger
 * 
 */
public class CondPlayedBefore extends Condition {
	
	static {
		Skript.registerCondition(CondPlayedBefore.class,
				"%offlineplayer% [(has|did)] [already] play[ed] [on (this|the) server] (before|already)",
				"%offlineplayer% (has not|hasn't|did not|didn't) [(already|yet)] play[ed] [on (this|the) server] (before|already|yet)");
	}
	
	private Expression<OfflinePlayer> player;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(final Expression<?>[] exprs, final int matchedPattern, final int isDelayed, final ParseResult parseResult) {
		player = (Expression<OfflinePlayer>) exprs[0];
		setNegated(matchedPattern == 1);
		return true;
	}
	
	@Override
	public boolean check(final Event e) {
		return player.check(e, new Checker<OfflinePlayer>() {
			@Override
			public boolean check(final OfflinePlayer p) {
				return p.hasPlayedBefore();
			}
		}, this);
	}
	
	@Override
	public String toString(final Event e, final boolean debug) {
		return player.toString(e, debug) + " " + (isNegated() ? "hasn't" : "has") + " played on this server before";
	}
	
}