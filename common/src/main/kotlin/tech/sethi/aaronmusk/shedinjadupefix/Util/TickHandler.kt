package tech.sethi.aaronmusk.cobblemonraids.Utils

import dev.architectury.event.events.common.TickEvent
import net.minecraft.server.MinecraftServer
import tech.sethi.aaronmusk.shedinjadupefix.ShedinjaDupeFix

class TickHandler {
    init {
        TickEvent.SERVER_POST.register(TickEvent.Server {
            processTasks(it)
        })
    }

    private fun processTasks(server: MinecraftServer) {
        val currentTick = server.worlds.first().time

        ShedinjaDupeFix.tasks[currentTick]?.let { tasks ->
            try {
                for (task in tasks) {
                    task.action()
                }
                ShedinjaDupeFix.tasks.remove(currentTick) // Remove the executed tasks from the storage
            } catch (_: Exception) { }

        }
    }
}