package shadowverse.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowverse.cards.Neutral.Temp.*;
import shadowverse.powers.GraveAdjudicatorPower;

public class DestructionPatch {
    @SpirePatch(clz = CardGroup.class, method = "moveToExhaustPile")
    public static class Destruction {
        @SpirePrefixPatch
        public static SpireReturn Post(CardGroup group, AbstractCard c) {
            if (c instanceof WhiteArtifact||c instanceof BlackArtifact||c instanceof WhiteArtifact2||c instanceof BlackArtifact2 || c instanceof GraveAdjudicator){
                if (c instanceof GraveAdjudicator){
                    if (!AbstractDungeon.player.hasPower(GraveAdjudicatorPower.POWER_ID)){
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new GraveAdjudicatorPower(AbstractDungeon.player)));
                    }
                }
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
