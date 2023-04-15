package shadowverse.patch;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.GameOverScreen;
import com.megacrit.cardcrawl.screens.GameOverStat;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.relics.BanShadowverse;

import java.util.ArrayList;

public class PointPatch {

    public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("shadowverse:YouWeek");

    @SpirePatch(clz = GameOverScreen.class, method = "checkScoreBonus")
    public static class checkPointPatch{
        @SpireInsertPatch(rloc = 1,localvars = { "points" })
        public void Insert(boolean victory, int points){
            if (AbstractDungeon.player.hasRelic(BanShadowverse.ID) && AbstractDungeon.player instanceof AbstractShadowversePlayer){
                points -= 1249;
            }
        }

    }

    @SpirePatch(clz = VictoryScreen.class, method = "createGameOverStats")
    public static class VictoryScreenPatch {
        public static void Postfix(VictoryScreen __instance) {
            if (AbstractDungeon.player.hasRelic(BanShadowverse.ID) && AbstractDungeon.player instanceof AbstractShadowversePlayer) {
                ArrayList<GameOverStat> stats =  ReflectionHacks.getPrivate(__instance, GameOverScreen.class, "stats");
                int idx = -1;
                for (int i = 0; i < stats.size(); i++) {
                    if ((stats.get(i)).label != null &&
                            (stats.get(i)).label.equals(CardCrawlGame.languagePack.getScoreString("Heartbreaker").NAME))
                        idx = i;
                }
                stats.remove(idx);
                stats.add(idx, new GameOverStat(uiStrings.TEXT[0], uiStrings.TEXT[0], Integer.toString(-999)));
            }
        }
    }
}
