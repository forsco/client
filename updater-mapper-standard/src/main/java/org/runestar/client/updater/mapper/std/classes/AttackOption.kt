package org.runestar.client.updater.mapper.std.classes

import org.runestar.client.updater.mapper.IdentityMapper
import org.runestar.client.updater.mapper.annotations.DependsOn
import org.runestar.client.updater.mapper.annotations.MethodParameters
import org.runestar.client.updater.mapper.extensions.and
import org.runestar.client.updater.mapper.extensions.predicateOf
import org.runestar.client.updater.mapper.tree.Class2
import org.runestar.client.updater.mapper.tree.Field2
import org.runestar.client.updater.mapper.tree.Method2
import org.objectweb.asm.Type.INT_TYPE

@DependsOn(Enumerated::class)
class AttackOption : IdentityMapper.Class() {

    override val predicate = predicateOf<Class2> { it.instanceFields.size == 1 }
            .and { it.interfaces.contains(type<Enumerated>()) }
            .and { it.instanceMethods.size == 1 }
            .and { it.instanceFields.all { it.type == INT_TYPE } }
            .and { it.staticFields.count { it.type == it.klass.type } == 4 }

    @MethodParameters()
    @DependsOn(Enumerated.ordinal::class)
    class ordinal : IdentityMapper.InstanceMethod() {
        override val predicate = predicateOf<Method2> { it.mark == method<Enumerated.ordinal>().mark }
    }

    class id : IdentityMapper.InstanceField() {
        override val predicate = predicateOf<Field2> { it.type == INT_TYPE }
    }
}