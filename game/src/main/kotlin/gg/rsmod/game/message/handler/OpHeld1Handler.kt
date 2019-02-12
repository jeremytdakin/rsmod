package gg.rsmod.game.message.handler

import gg.rsmod.game.message.MessageHandler
import gg.rsmod.game.message.impl.OpHeld1Message
import gg.rsmod.game.model.INTERACTING_ITEM
import gg.rsmod.game.model.INTERACTING_ITEM_ID
import gg.rsmod.game.model.INTERACTING_ITEM_SLOT
import gg.rsmod.game.model.entity.Client

/**
 * @author Tom <rspsmods@gmail.com>
 */
class OpHeld1Handler : MessageHandler<OpHeld1Message> {

    override fun handle(client: Client, message: OpHeld1Message) {
        @Suppress("unused")
        val componentParent = message.componentHash shr 16
        @Suppress("unused")
        val componentChild = message.componentHash and 0xFFFF

        if (message.slot < 0 || message.slot >= client.inventory.capacity) {
            return
        }

        if (!client.lock.canItemInteract()) {
            return
        }

        val item = client.inventory[message.slot] ?: return

        if (item.id != message.item) {
            logVerificationFail(client, "Item action 1: id=%d, slot=%d, component=(%d, %d), inventory=(%d, %d)",
                    message.item, message.slot, componentParent, componentChild, item.id, item.amount)
            return
        }

        log(client, "Item action 1: id=%d, slot=%d, component=(%d, %d), inventory=(%d, %d)",
                message.item, message.slot, componentParent, componentChild, item.id, item.amount)

        client.attr[INTERACTING_ITEM_SLOT] = message.slot
        client.attr[INTERACTING_ITEM_ID] = item.id
        client.attr[INTERACTING_ITEM] = item

        if (!client.world.plugins.executeItem(client, item.id, 1)) {
            client.message("Unhandled item action: [item=${item.id}, slot=${message.slot}, option=1]")
        }
    }

}