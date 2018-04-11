package org.runestar.client.plugins.dev

import org.runestar.client.game.raw.Client.accessor
import org.runestar.client.plugins.spi.AbstractPlugin
import org.runestar.client.plugins.spi.PluginSettings

class DevOptionsDebug : AbstractPlugin<DevOptionsDebug.Settings>() {

    override val defaultSettings = Settings()

    override fun start() {
        if (ctx.settings.boundingBoxes2D.draw) {
            accessor.boundingBoxes_draw2D = true
        }
        if (ctx.settings.boundingBoxes3D.draw) {
            accessor.boundingBoxes_draw3D = true
        }
        if (ctx.settings.boundingBoxes3D.drawAll) {
            accessor.boundingBoxes_3DDrawMode = accessor.boundingBox3DDrawMode_all
        } else {
            accessor.boundingBoxes_3DDrawMode = accessor.boundingBox3DDrawMode_mouseOver
        }
        if (ctx.settings.displayFps) {
            accessor.displayFps = true
        }
        if (!ctx.settings.useBoundingBoxes3D) {
            accessor.useBoundingBoxes3D = false
        }
        if (ctx.settings.boundingBoxes2D.drawObjectGeometry) {
            accessor.boundingBoxes_drawObjectGeometry2D = true
        }
//        if (settings.numberMenuOptions) {
//            accessor.numberMenuOptions = true
//        }
    }

    override fun stop() {
        accessor.boundingBoxes_draw2D = false
        accessor.boundingBoxes_draw3D = false
        accessor.displayFps = false
        accessor.useBoundingBoxes3D = true
        accessor.boundingBoxes_drawObjectGeometry2D = false
//        accessor.numberMenuOptions = false
    }

    class Settings(
            val boundingBoxes3D: ThreeD = ThreeD(),
            val boundingBoxes2D: TwoD = TwoD(),
            val displayFps: Boolean = false,
            val useBoundingBoxes3D: Boolean = true
//            val numberMenuOptions: Boolean = false
    ) : PluginSettings() {

        data class ThreeD(
                val draw: Boolean = false,
                val drawAll: Boolean = true
        )

        data class TwoD(
                val draw: Boolean = false,
                val drawObjectGeometry: Boolean = false
        )
    }
}