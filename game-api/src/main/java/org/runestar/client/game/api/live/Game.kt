package org.runestar.client.game.api.live

import hu.akarnokd.rxjava2.swing.SwingObservable
import io.reactivex.Observable
import org.kxtra.swing.component.windowAncestor
import org.runestar.client.game.api.*
import org.runestar.client.game.raw.Client
import org.runestar.client.game.raw.Client.accessor
import org.runestar.client.game.raw.access.XClient
import org.runestar.client.game.raw.access.XPacketBuffer
import java.awt.Component
import java.awt.Container

object Game {

    val state get() = GameState.of(accessor.gameState)

    val stateChanges: Observable<GameState> = XClient.updateGameState.exit.map {
        checkNotNull(GameState.of(it.arguments[0] as Int)) { it.arguments[0] }
    }

    val ticks: Observable<Unit> = XPacketBuffer.readSmartByteShortIsaac.exit
            .filter { it.returned == 43 } // update npcs
            .map { Unit }
            .delay { XClient.doCycle.enter }

    val cycle get() = accessor.cycle

    val plane get() = accessor.plane

    val runEnergy get() = accessor.runEnergy

    val weight get() = accessor.weight

    val windowMode get() = WindowMode.of(accessor.clientPreferences.windowMode)

    /**
     * @see[java.awt.event.WindowListener]
     * @see[java.awt.event.WindowStateListener]
     * @see[java.awt.event.WindowFocusListener]
     */
    val windowEvents = SwingObservable.window(
            checkNotNull((accessor as Component).windowAncestor()) { "Client has no window" }
    )

    /**
     * @see[java.awt.event.ContainerListener]
     */
    val containerEvents = SwingObservable.container(accessor as Container)

    fun getVarbit(varbitId: Int): Int = Client.accessor.getVarbit(varbitId)

    fun getVarp(varpId: Int): Int = Client.accessor.varps_main[varpId]

    val varcs: Varcs = Varcs(Client.accessor.varcs)

    val clanChat: ClanChat? get() = Client.accessor.clanChat?.let { ClanChat(it) }

    val friendsSystem: FriendsSystem get() = FriendsSystem(Client.accessor.friendSystem)

    val specialAttackEnabled get() = getVarp(VarpId.SPECIAL_ATTACK_ENABLED) != 0

    /**
     * 0 - 100
     */
    val specialAttackPercent get() = getVarp(VarpId.SPECIAL_ATTACK_PERCENT) / 10

    val visibilityMap = VisibilityMap(Client.accessor.visibilityMap, LiveCamera)

    val destination: SceneTile? get() {
        val x = Client.accessor.destinationX
        val y = Client.accessor.destinationY
        if (x == 0 && y == 0) return null
        return SceneTile(x, y, plane)
    }
}