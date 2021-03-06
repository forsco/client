package org.runestar.client.updater.mapper.std.classes

import org.runestar.client.updater.mapper.IdentityMapper
import org.runestar.client.updater.mapper.annotations.DependsOn
import org.runestar.client.updater.mapper.annotations.SinceVersion
import org.runestar.client.updater.mapper.extensions.Predicate
import org.runestar.client.updater.mapper.extensions.and
import org.runestar.client.updater.mapper.extensions.predicateOf
import org.runestar.client.updater.mapper.tree.Class2
import org.runestar.client.updater.mapper.tree.Field2

@SinceVersion(157)
@DependsOn(Node::class, PacketBuffer::class)
class PacketBufferNode : IdentityMapper.Class() {

    override val predicate = predicateOf<Class2> { it.superType == type<Node>() }
            .and { it.instanceFields.any { it.type == type<PacketBuffer>() } }

    @DependsOn(PacketBuffer::class)
    class packetBuffer : IdentityMapper.InstanceField() {
        override val predicate = predicateOf<Field2> { it.type == type<PacketBuffer>() }
    }
}