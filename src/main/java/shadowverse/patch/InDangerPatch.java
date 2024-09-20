package shadowverse.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import shadowverse.characters.AbstractShadowversePlayer;

public class InDangerPatch {

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "damage"
    )
    public static class WrathPatch {
        @SpireInsertPatch(rlocs = 15, localvars = {"damageAmount"})
        public static void patch(AbstractPlayer __instance, DamageInfo info, int damageAmount) {
            if (damageAmount > 0 && __instance instanceof AbstractShadowversePlayer) {
                ((AbstractShadowversePlayer) __instance).wrathLastTurn++;
            }
        }
    }
}
