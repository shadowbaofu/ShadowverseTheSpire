package shadowverse.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import shadowverse.characters.AbstractShadowversePlayer;

public class InDangerPatch {

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "damage"
    )
    public static class WrathPatch {
        @SpirePostfixPatch()
        public static void patch(AbstractPlayer __instance, DamageInfo info, int ___damageAmount) {
            if (___damageAmount > 0 && __instance instanceof AbstractShadowversePlayer) {
                ((AbstractShadowversePlayer) __instance).wrathLastTurn++;
            }
        }
    }
}
