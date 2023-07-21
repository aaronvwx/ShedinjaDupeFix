package tech.sethi.aaronmusk.cobblemonraids.Utils

import net.minecraft.server.world.ServerWorld

data class Task(val world: ServerWorld, val tick: Long, val action: () -> Unit)