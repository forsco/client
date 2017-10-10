package com.runesuite.client.game.api

import com.runesuite.client.game.api.live.Projection
import java.awt.Point

/**
 * Exact point in the loaded 3D game space
 */
data class Position(
        val x: Int,
        val subX: Int,
        val y: Int,
        val subY: Int,
        val height: Int,
        val plane: Int
) {
    internal constructor(localX: Int, localY: Int, height: Int, plane: Int) :
            this(localX shr 7, localX and com.runesuite.client.game.api.Position.Companion.MAX_SUB, localY shr 7, localY and com.runesuite.client.game.api.Position.Companion.MAX_SUB, height, plane)

    internal val localX get() = (x shl 7) or subX

    internal val localY get() = (y shl 7) or subY

    val sceneTile get() = com.runesuite.client.game.api.SceneTile(x, y, plane)

    val isLoaded get() = sceneTile.isLoaded

    internal fun plusLocal(localX: Int = 0, localY: Int = 0, height: Int = 0): com.runesuite.client.game.api.Position {
        return com.runesuite.client.game.api.Position(localX + this.localX, localY + this.localY, height + this.height, plane)
    }

    operator fun plus(position: com.runesuite.client.game.api.Position) =
            com.runesuite.client.game.api.Position(position.localX + localX, position.localY + localY, position.height + height, position.plane + plane)

    fun toScreen(projection: Projection = Projection.Viewport.Live): Point {
        check(isLoaded) { this }
        return projection.toScreen(this)
    }

    companion object {
        const val MAX_SUB = 127
        const val MID_SUB = 64
        const val MIN_SUB = 0
    }
}

