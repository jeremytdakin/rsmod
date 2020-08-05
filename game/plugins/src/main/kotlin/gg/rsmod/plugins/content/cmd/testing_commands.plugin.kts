package gg.rsmod.plugins.content.cmd

import gg.rsmod.game.model.priv.Privilege

on_command("cerb", Privilege.ADMIN_POWER) {
    val args = player.getCommandArgs()
    tryWithUsage(player, args, "Invalid format! Example of proper command <col=801700>::tele 3200 3200</col>") { values ->
       // val x = values[0].toInt()
        val x = 1240
        val z = 1253
        val height = if (values.size > 2) values[2].toInt() else 0
        player.moveTo(x, z, height)
    }
}

fun tryWithUsage(player: Player, args: Array<String>, failMessage: String, tryUnit: Function1<Array<String>, Unit>) {
    try {
        tryUnit.invoke(args)
    } catch (e: Exception) {
        player.message(failMessage)
        e.printStackTrace()
    }
}