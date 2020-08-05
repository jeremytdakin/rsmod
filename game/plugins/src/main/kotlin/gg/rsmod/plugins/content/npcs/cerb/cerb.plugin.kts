package gg.rsmod.plugins.content.npcs.cerb

import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.dsl.set_combat_def

spawn_npc(Npcs.CERBERUS, x = 1238, z = 1251, walkRadius = 0)

arrayOf(Npcs.CERBERUS).forEach { npc ->
    set_combat_def(npc) {
        configs {
            attackSpeed = 8
            respawnDelay = 13
        }

        aggro {
            radius = 3
            searchDelay = 1
            aggroMinutes = 10
        }

        stats {
            hitpoints = 700
            attack = 1
            strength = 1
            defence = 1
            magic = 1
        }

        bonuses {
            defenceStab = 70
            defenceSlash = 90
            defenceCrush = 90
            defenceMagic = 80
            defenceRanged = 70
        }

        anims {
            attack = 4491
            block = 4490
            death = 4495
        }
    }
}