package shadowverse.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.PrismaticShard;
import shadowverse.action.ResonanceAction;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Nemesis;
import shadowverse.characters.Vampire;
import shadowverse.stance.Resonance;

import java.lang.reflect.Field;

public class ResonancePatch {
    private static int shuffleCount=0;

    @SpirePatch(clz = DrawCardAction.class, method = "endActionWithFollowUp")
    public static class RPatch{
        @SpireInsertPatch(rloc = 4)
        public static void Insert(DrawCardAction drawCardAction) throws NoSuchFieldException, IllegalAccessException {
            Field followUpAction = drawCardAction.getClass().getDeclaredField("followUpAction");
            followUpAction.setAccessible(true);
            AbstractGameAction f = (AbstractGameAction) followUpAction.get(drawCardAction);
            if (f == null){
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ResonanceAction());
            }
        }
    }
    @SpirePatch(clz = MakeTempCardInDrawPileAction.class, method = "update")
    public static class ResonancePatch2{
        @SpirePostfixPatch
        public static void CheckResonance(MakeTempCardInDrawPileAction makeTempCardInDrawPileAction) {
            if (makeTempCardInDrawPileAction.isDone)
             AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ResonanceAction());
        }
    }

    @SpirePatch(clz = EmptyDeckShuffleAction.class, method = "update")
    public static class ResonancePatch3{
        @SpirePrefixPatch
        public static void CheckResonance2(EmptyDeckShuffleAction emptyDeckShuffleAction) {
            if (AbstractDungeon.player instanceof Nemesis ||((AbstractDungeon.player.hasRelic(PrismaticShard.ID)
                    //||(AbstractDungeon.player.hasRelic(AlterplaneArbiter.ID) && AbstractDungeon.player.getRelic(AlterplaneArbiter.ID).counter == 4)
            )&&AbstractDungeon.player instanceof AbstractShadowversePlayer && AbstractDungeon.player.chosenClass!= Vampire.Enums.Vampire)) {
                if (AbstractDungeon.player.drawPile.group.size() % 2 == 0 && !AbstractDungeon.player.stance.ID.equals(Resonance.STANCE_ID)) {
                    shuffleCount++;
                    if (shuffleCount==6){
                        shuffleCount=0;
                        ((AbstractShadowversePlayer) AbstractDungeon.player).resonanceCount--;
                    }
                }
            }
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "applyStartOfCombatLogic")
    public static class ResonancePatch4{
        @SpirePostfixPatch
        public static void CheckResonance(AbstractPlayer p) {
            shuffleCount = 0;
        }
    }
}
