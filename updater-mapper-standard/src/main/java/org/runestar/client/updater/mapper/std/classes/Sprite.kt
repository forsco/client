package org.runestar.client.updater.mapper.std.classes

import org.objectweb.asm.Opcodes.ISUB
import org.runestar.client.updater.mapper.IdentityMapper
import org.runestar.client.updater.mapper.OrderMapper
import org.runestar.client.updater.mapper.annotations.DependsOn
import org.runestar.client.updater.mapper.extensions.and
import org.runestar.client.updater.mapper.extensions.predicateOf
import org.runestar.client.updater.mapper.extensions.type
import org.runestar.client.updater.mapper.tree.Class2
import org.runestar.client.updater.mapper.tree.Field2
import org.runestar.client.updater.mapper.tree.Instruction2
import org.runestar.client.updater.mapper.tree.Method2
import org.objectweb.asm.Opcodes.PUTFIELD
import org.objectweb.asm.Type.INT_TYPE
import org.runestar.client.updater.mapper.annotations.MethodParameters
import org.runestar.client.updater.mapper.extensions.Predicate

@DependsOn(Rasterizer2D::class, Client.worldToMinimap::class)
class Sprite : IdentityMapper.Class() {
    override val predicate = predicateOf<Class2> { it.superType == type<Rasterizer2D>() }
            .and { c -> method<Client.worldToMinimap>().arguments.first { it in c.jar } == c.type }

    class pixels : IdentityMapper.InstanceField() {
        override val predicate = predicateOf<Field2> { it.type == IntArray::class.type }
    }

    class width : OrderMapper.InConstructor.Field(Sprite::class, 0, 6) {
        override val constructorPredicate = predicateOf<Method2> { it.arguments.size >= 3 }
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldType == INT_TYPE }
    }

    class subWidth : OrderMapper.InConstructor.Field(Sprite::class, 1, 6) {
        override val constructorPredicate = predicateOf<Method2> { it.arguments.size >= 3 }
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldType == INT_TYPE }
    }

    class height : OrderMapper.InConstructor.Field(Sprite::class, 2, 6) {
        override val constructorPredicate = predicateOf<Method2> { it.arguments.size >= 3 }
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldType == INT_TYPE }
    }

    class subHeight : OrderMapper.InConstructor.Field(Sprite::class, 3, 6) {
        override val constructorPredicate = predicateOf<Method2> { it.arguments.size >= 3 }
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldType == INT_TYPE }
    }

    class xOffset : OrderMapper.InConstructor.Field(Sprite::class, 4, 6) {
        override val constructorPredicate = predicateOf<Method2> { it.arguments.size >= 3 }
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldType == INT_TYPE }
    }

    class yOffset : OrderMapper.InConstructor.Field(Sprite::class, 5, 6) {
        override val constructorPredicate = predicateOf<Method2> { it.arguments.size >= 3 }
        override val predicate = predicateOf<Instruction2> { it.opcode == PUTFIELD && it.fieldType == INT_TYPE }
    }

    @MethodParameters()
    class copy : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == type<Sprite>() }
                .and { it.instructions.any { it.opcode == ISUB } }
    }

    @MethodParameters()
    class compressedCopy : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == type<Sprite>() }
                .and { it.instructions.none { it.opcode == ISUB } }
    }
}