package shadowverse.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.neow.NeowReward;

import java.util.ArrayList;

public class NeowPatch {
    @SpireEnum
    public static NeowReward.NeowRewardType CARDPACK;

    @SpirePatch(
            clz = NeowReward.class,
            method = "activate"
    )
    public static class ActivatePatch {
        @SpirePrefixPatch
        public static void patch(NeowReward __instance) {
            if (__instance.type == CARDPACK) {
                CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                for (int i = 0; i < 8; i++) {
                    int roll = AbstractDungeon.cardRandomRng.random(99);
                    AbstractCard.CardRarity cardRarity;
                    if (roll < 70) {
                        cardRarity = AbstractCard.CardRarity.COMMON;
                    } else if (roll < 95) {
                        cardRarity = AbstractCard.CardRarity.UNCOMMON;
                    } else {
                        cardRarity = AbstractCard.CardRarity.RARE;
                    }
                    AbstractCard tmp;
                    do {
                        tmp = CardLibrary.getAnyColorCard(cardRarity);
                    } while (!(tmp.cardID.startsWith("shadowverse:")));
                    group.addToBottom(tmp);
                }
                AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, CardCrawlGame.languagePack.getUIString("shadowverse:CardPack").TEXT[1]);
            }
        }
    }

    @SpirePatch(
            clz = NeowReward.class,
            method = "getRewardOptions"
    )
    public static class AddRewardsPatch {
        @SpirePostfixPatch
        public static ArrayList<NeowReward.NeowRewardDef> patch(ArrayList<NeowReward.NeowRewardDef> __result, NeowReward __instance, int category) {
            if (category == 1) {
                __result.add(new NeowReward.NeowRewardDef(CARDPACK, CardCrawlGame.languagePack.getUIString("shadowverse:CardPack").TEXT[0]));
            }
            return __result;
        }
    }
}
