package org.runestar.client.game.api

import org.runestar.client.game.raw.Client.accessor
import org.runestar.client.game.raw.access.XAttackOption

enum class AttackOption(val accessor: XAttackOption) {

    ALWAYS_RIGHT_CLICK(accessor.attackOption_alwaysRightClick),
    DEPENDS_ON_COMBAT_LEVELS(accessor.attackOption_dependsOnCombatLevels),
    HIDDEN(accessor.attackOption_hidden),
    LEFT_CLICK_WHERE_AVAILABLE(accessor.attackOption_leftClickWhereAvailable);

    companion object {

        @JvmField val LOOKUP = values().associateBy { it.accessor }

        fun of(accessor: XAttackOption): AttackOption {
            return LOOKUP.getValue(accessor)
        }
    }
}