package com.runesuite.client.game.api.live

import com.runesuite.client.game.api.Projectile
import com.runesuite.client.game.raw.Client.accessor
import com.runesuite.client.game.raw.access.XProjectile

object Projectiles {

    val all: List<Projectile> get() {
        val nodes = accessor.projectiles
        val list = ArrayList<Projectile>()
        var node = nodes.sentinel.previous
        while (node != nodes.sentinel) {
            list.add(Projectile(node as XProjectile))
            node = node.previous
        }
        return list
    }
}