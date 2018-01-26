package org.runestar.client.plugins.std.debug

import org.runestar.client.game.api.live.LiveCanvas
import org.runestar.client.game.api.live.Stats
import org.runestar.client.plugins.PluginSettings
import org.runestar.client.utils.ColorForm
import org.runestar.client.utils.DisposablePlugin
import org.runestar.client.utils.FontForm
import org.runestar.general.Skill
import org.runestar.general.fonts.RUNESCAPE_CHAT_FONT
import java.awt.Color
import java.awt.Font

class SkillsDebug : DisposablePlugin<PluginSettings>() {

    override val defaultSettings = PluginSettings()

    override fun start() {
        super.start()
        add(LiveCanvas.repaints.subscribe { g ->
            g.font = RUNESCAPE_CHAT_FONT
            g.color = Color.WHITE

            var y = 35
            val x = 5
            Stats.forEach { k, v ->
                g.drawString("$k=$v", x, y)
                y += g.font.size + 5
            }
        })
    }
}