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

package me.tecnio.antihaxerman.check.impl.aim;

import me.tecnio.antihaxerman.check.Check;
import me.tecnio.antihaxerman.check.CheckInfo;
import me.tecnio.antihaxerman.data.PlayerData;
import me.tecnio.antihaxerman.utils.math.MathUtils;

@CheckInfo(name = "Aim", type = "A")
public final class AimA extends Check {
    public AimA(PlayerData data) {
        super(data);
    }

    /*
    * Credits to Elevated https://github.com/ElevatedDev/Frequency
     */

    @Override
    public void onRotation() {
        if (data.attackTicks() < 3) {
            final float deltaYaw = data.getDeltaYaw();
            final float deltaPitch = data.getDeltaPitch();

            final float lastDeltaYaw = data.getLastDeltaYaw();
            final float lastDeltaPitch = data.getLastDeltaPitch();

            final double divisorYaw = MathUtils.getGcd((long) (deltaYaw * MathUtils.EXPANDER), (long) (lastDeltaYaw * MathUtils.EXPANDER));
            final double divisorPitch = MathUtils.getGcd((long) (deltaPitch * MathUtils.EXPANDER), (long) (lastDeltaPitch * MathUtils.EXPANDER));

            final double constantYaw = divisorYaw / MathUtils.EXPANDER;
            final double constantPitch = divisorPitch / MathUtils.EXPANDER;

            final double currentX = deltaYaw / constantYaw;
            final double currentY = deltaPitch / constantPitch;

            final double previousX = lastDeltaYaw / constantYaw;
            final double previousY = lastDeltaPitch / constantPitch;

            if (deltaYaw > 0.0 && deltaPitch > 0.0 && deltaYaw < 20.f && deltaPitch < 20.f) {
                final double moduloX = currentX % previousX;
                final double moduloY = currentY % previousY;

                final double floorModuloX = Math.abs(Math.floor(moduloX) - moduloX);
                final double floorModuloY = Math.abs(Math.floor(moduloY) - moduloY);

                final boolean invalidX = moduloX > 90.d && floorModuloX > 0.1;
                final boolean invalidY = moduloY > 90.d && floorModuloY > 0.1;

                if (data.isUsingCinematic()) decreaseBufferBy(0.1);

                if (invalidX && invalidY) {
                    if (increaseBuffer() > 8) {
                        flag();
                    }
                } else {
                    decreaseBufferBy(0.5);
                }
            }
        }
    }
}
