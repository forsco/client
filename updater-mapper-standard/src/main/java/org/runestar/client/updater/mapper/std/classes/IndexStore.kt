package org.runestar.client.updater.mapper.std.classes

import org.runestar.client.updater.mapper.IdentityMapper
import org.runestar.client.updater.mapper.OrderMapper
import org.runestar.client.updater.mapper.annotations.DependsOn
import org.runestar.client.updater.mapper.annotations.MethodParameters
import org.runestar.client.updater.mapper.extensions.and
import org.runestar.client.updater.mapper.extensions.predicateOf
import org.runestar.client.updater.mapper.extensions.type
import org.runestar.client.updater.mapper.tree.Class2
import org.runestar.client.updater.mapper.tree.Instruction2
import org.runestar.client.updater.mapper.tree.Method2
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

@DependsOn(BufferedFile::class)
class IndexStore : IdentityMapper.Class() {
    override val predicate = predicateOf<Class2> { it.superType == Any::class.type }
            .and { it.instanceFields.count { it.type == type<BufferedFile>() } == 2 }

    @DependsOn(BufferedFile::class)
    class dataFile : OrderMapper.InConstructor.Field(IndexStore::class, -2, 4) {
        override val predicate = predicateOf<Instruction2> { it.opcode == Opcodes.PUTFIELD && it.fieldType == type<BufferedFile>() }
    }

    @DependsOn(BufferedFile::class)
    class indexFile : OrderMapper.InConstructor.Field(IndexStore::class, -1, 4) {
        override val predicate = predicateOf<Instruction2> { it.opcode == Opcodes.PUTFIELD && it.fieldType == type<BufferedFile>() }
    }

    class index : OrderMapper.InConstructor.Field(IndexStore::class, -2) {
        override val predicate = predicateOf<Instruction2> { it.opcode == Opcodes.PUTFIELD && it.fieldType == Type.INT_TYPE }
    }

    class maxEntrySize : OrderMapper.InConstructor.Field(IndexStore::class, -1) {
        override val predicate = predicateOf<Instruction2> { it.opcode == Opcodes.PUTFIELD && it.fieldType == Type.INT_TYPE }
    }

    @MethodParameters("entry")
    class read : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == ByteArray::class.type }
    }

//    @MethodParameters()
    @DependsOn(BufferedFile.write::class)
    class write0 : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == Type.BOOLEAN_TYPE }
                .and { it.instructions.any { it.isMethod && it.methodId == method<BufferedFile.write>().id } }
    }

    //    @MethodParameters()
    @DependsOn(write0::class)
    class write : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.returnType == Type.BOOLEAN_TYPE }
                .and { it.instructions.any { it.isMethod && it.methodId == method<write0>().id } }
    }
}