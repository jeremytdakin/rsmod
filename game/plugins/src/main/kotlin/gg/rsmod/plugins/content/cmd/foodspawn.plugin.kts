package gg.rsmod.plugins.content.cmd

import gg.rsmod.game.model.priv.Privilege

on_command("food2", Privilege.ADMIN_POWER) {
    player.inventory.add(item = Items.MANTA_RAY, amount = player.inventory.freeSlotCount)
    player.message("You spawned some foodies.")
}