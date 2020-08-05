package gg.rsmod.plugins.content.npcs.cerb

import gg.rsmod.game.model.combat.AttackStyle
import gg.rsmod.game.model.combat.CombatClass
import gg.rsmod.game.model.combat.CombatStyle
import gg.rsmod.plugins.content.combat.*
import gg.rsmod.plugins.content.combat.formula.DragonfireFormula
import gg.rsmod.plugins.content.combat.formula.MeleeCombatFormula
import gg.rsmod.plugins.content.combat.formula.RangedCombatFormula
import gg.rsmod.plugins.content.combat.strategy.RangedCombatStrategy

on_npc_combat(Npcs.CERBERUS) {
    npc.queue {
        combat(this)
    }
}

suspend fun combat(it: QueueTask) {
    val npc = it.npc
    var target = npc.getCombatTarget() ?: return

    while (npc.canEngageCombat(target)) {
        npc.facePawn(target)
        if (npc.moveToAttackRange(it, target, distance = 30, projectile = true) && npc.isAttackDelayReady()) {
            if (world.chance(1, 2) && npc.canAttackMelee(it, target, moveIfNeeded = false)) {
                melee_attack(npc, target)
                if(target is Player) {target.message("Melee")}
            }else {
                when (world.random(1)) {
                    0 -> range_attack(npc, target)
                    1 -> magic_attack(npc, target)
                }
            }
            npc.postAttackLogic(target)
        }
        it.wait(1)
        target = npc.getCombatTarget() ?: break
    }

    npc.resetFacePawn()
    npc.removeCombatTarget()
}

fun melee_attack(npc: Npc, target: Pawn) {
        // Headbutt attack
        npc.prepareAttack(CombatClass.MELEE, CombatStyle.STAB, AttackStyle.ACCURATE)
        npc.animate(4491)

    if (MeleeCombatFormula.getAccuracy(npc, target) >= world.randomDouble()) {
        target.hit(world.random(35), type = HitType.HIT, delay = 1)
    } else {
        target.hit(damage = 0, type = HitType.BLOCK, delay = 1)
    }
}

fun range_attack(npc: Npc, target: Pawn) {
    val projectile = npc.createProjectile(target, gfx = 1243, startHeight = 43, endHeight = 31, delay = 40, angle = 15, steepness = 127)
    npc.prepareAttack(CombatClass.RANGED, CombatStyle.RANGED, AttackStyle.ACCURATE)
    npc.animate(4492)
    if(target is Player) { target.message("Range") }
    world.spawn(projectile)
    npc.dealHit(target = target, formula = RangedCombatFormula, delay = RangedCombatStrategy.getHitDelay(npc.getFrontFacingTile(target), target.getCentreTile()) - 1)
}

fun magic_attack(npc: Npc, target: Pawn) {
    val projectile = npc.createProjectile(target, gfx = 1242, startHeight = 43, endHeight = 31, delay = 40, angle = 15, steepness = 127)
    npc.prepareAttack(CombatClass.MAGIC, CombatStyle.MAGIC, AttackStyle.ACCURATE)
    npc.animate(4492)
    if(target is Player) { target.message("Mage") }
    world.spawn(projectile)
    npc.dealHit(target = target, formula = RangedCombatFormula, delay = RangedCombatStrategy.getHitDelay(npc.getFrontFacingTile(target), target.getCentreTile()) - 1)
}