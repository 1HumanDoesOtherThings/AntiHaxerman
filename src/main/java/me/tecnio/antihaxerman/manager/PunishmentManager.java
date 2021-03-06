/*
 * Copyright (C) 2020 Tecnio
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>
 */

package me.tecnio.antihaxerman.manager;

import me.tecnio.antihaxerman.AntiHaxerman;
import me.tecnio.antihaxerman.Config;
import me.tecnio.antihaxerman.check.Check;
import me.tecnio.antihaxerman.data.PlayerData;
import me.tecnio.antihaxerman.utils.other.ChatUtils;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public final class PunishmentManager {
    public static void punish(Check check, PlayerData data) {
        if (check.getCheckInfo().autoBan()) {
            final List<String> punishments = Config.PUNISH_COMMANDS.getOrDefault(check.getCheckInfo().name(), new ArrayList<>());

            for (final String cmd : punishments) {
                final String command = ChatUtils.color(cmd.replaceAll("%player%", data.getPlayer().getName()).replaceAll("%check%", check.getCheckInfo().name()).replaceAll("%checktype%", check.getCheckInfo().type()));

                Bukkit.getScheduler().runTask(AntiHaxerman.getInstance(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command));
            }
        }
    }
}
