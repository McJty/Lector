package mcjty.lector.sound;

import mcjty.lector.config.GeneralConfiguration;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SoundTools {

    public static void playPageTurn(World world, BlockPos pos) {
        if (GeneralConfiguration.basePageTurnVolume > 0.01f) {
            SoundController.playPageturn(world, pos, GeneralConfiguration.basePageTurnVolume);
        }
    }

}
