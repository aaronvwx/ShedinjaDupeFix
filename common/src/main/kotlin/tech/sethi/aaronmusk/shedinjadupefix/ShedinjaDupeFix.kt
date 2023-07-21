package tech.sethi.aaronmusk.shedinjadupefix

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.api.events.pokemon.evolution.EvolutionAcceptedEvent
import com.cobblemon.mod.common.api.events.pokemon.evolution.EvolutionCompleteEvent
import com.cobblemon.mod.common.api.pokemon.evolution.Evolution
import com.cobblemon.mod.common.api.storage.party.PartyPosition
import com.cobblemon.mod.common.pokemon.Pokemon
import dev.architectury.event.EventResult
import dev.architectury.event.events.common.CommandPerformEvent
import dev.architectury.event.events.common.LifecycleEvent
import net.minecraft.entity.Entity
import net.minecraft.item.ItemStack
import net.minecraft.server.MinecraftServer
import net.minecraft.server.world.ServerWorld
import org.apache.logging.log4j.LogManager
import tech.sethi.aaronmusk.cobblemonraids.Utils.Task
import tech.sethi.aaronmusk.cobblemonraids.Utils.TickHandler
import java.util.UUID

object ShedinjaDupeFix  {


    const val MOD_ID = "shedinjadupefix"
    val LOGGER = LogManager.getLogger()
    val partyPosL = mutableListOf<PartyPosition>()
    val playerUUID = mutableListOf<UUID>()

    val tasks: MutableMap<Long, MutableList<Task>> = mutableMapOf()

    var mcserver: MinecraftServer? = null

    @JvmStatic
    fun init() {
        LOGGER.info("AaronMusk's Cobblemon Shedinja Dupe Fix Initialized!")

        LifecycleEvent.SERVER_STARTED.register { server ->
            mcserver = server
            TickHandler()
        }

        CobblemonEvents.EVOLUTION_COMPLETE.subscribe { event ->
            val partyPos = Cobblemon.storage.getParty(event.pokemon.getOwnerUUID()!!).getFirstAvailablePosition()
            if (event.pokemon.species.name == "Ninjask" && partyPos != null) {
                partyPosL.add(partyPos)
                playerUUID.add(event.pokemon.getOwnerUUID()!!)
                addTask(mcserver!!.worlds.first(), 1) { removeHeldItem().run() }
            }
        }
    }

    fun addTask(world: ServerWorld, tickDelay: Long, action: () -> Unit) {
        val currentTick = world.time
        val taskTick = currentTick + tickDelay
        val task = Task(world, taskTick, action)
        tasks.getOrPut(taskTick) { mutableListOf() }.add(task)
    }

    fun removeHeldItem() = Runnable {
        val possibleShedinja = Cobblemon.storage.getParty(playerUUID[0])[partyPosL[0]]
        if (possibleShedinja != null && possibleShedinja.species.name == "Shedinja") {
            possibleShedinja.removeHeldItem()
            partyPosL.removeAt(0)
            playerUUID.removeAt(0)
        }
    }

}