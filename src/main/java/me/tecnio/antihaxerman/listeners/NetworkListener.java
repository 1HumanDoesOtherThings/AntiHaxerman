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

package me.tecnio.antihaxerman.listeners;

import io.github.retrooper.packetevents.event.PacketListenerDynamic;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.event.impl.PacketSendEvent;
import io.github.retrooper.packetevents.event.priority.PacketEventPriority;
import me.tecnio.antihaxerman.data.PlayerData;
import me.tecnio.antihaxerman.manager.PlayerDataManager;
import me.tecnio.antihaxerman.manager.TransactionManager;

public final class NetworkListener extends PacketListenerDynamic {
    public NetworkListener() {
        super(PacketEventPriority.MONITOR);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        final PlayerData data = PlayerDataManager.getPlayerData().get(event.getPlayer().getUniqueId());
        if (data != null) {
            data.onPacketReceive(event);
        }

        TransactionManager.onPacketReceive(event);
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        final PlayerData data = PlayerDataManager.getPlayerData().get(event.getPlayer().getUniqueId());
        if (data != null) {
            data.onPacketSend(event);
        }

        TransactionManager.onPacketSend(event);
    }
}
