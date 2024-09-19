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
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.neow.NeowReward;
import shadowverse.helper.StartPackHelper;

import java.util.ArrayList;
import java.util.Iterator;

public class NeowPatch {
    @SpireEnum
    public static NeowReward.NeowRewardType CARDPACK;

    @SpireEnum
    public static NeowReward.NeowRewardType STARTPACK;

    @SpirePatch(
            clz = NeowEvent.class,
            method = "miniBlessing"
    )
    public static class MiniPatch {
        @SpirePostfixPatch()
        public static void patch(NeowEvent __instance, ArrayList<NeowReward> ___rewards) {
            NeowReward n = new NeowReward(true);
            n.type = STARTPACK;
            n.optionLabel = "[" + CardCrawlGame.languagePack.getUIString("shadowverse:StartPack").TEXT[0];
            ___rewards.add(n);
            __instance.roomEventText.addDialogOption(n.optionLabel);
        }
    }

    @SpirePatch(
            clz = NeowEvent.class,
            method = "update"
    )
    public static class updatePatch {
        @SpirePrefixPatch
        public static void patch(NeowEvent __instance, boolean ___pickCard) {
            if (!___pickCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                Iterator var2 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while (var2.hasNext()) {
                    AbstractCard c = (AbstractCard) var2.next();
                    group.addToBottom(c.makeCopy());
                }

                AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, CardCrawlGame.languagePack.getUIString("shadowverse:CardPack").TEXT[2]);
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }
        }
    }

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
                AbstractDungeon.gridSelectScreen.open(group, 8, true, (CardCrawlGame.languagePack.getUIString("shadowverse:CardPack")).TEXT[1]);
            }
            if (__instance.type == STARTPACK) {
                AbstractDungeon.player.masterDeck.group = new ArrayList<>();
                CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                ArrayList<AbstractCard> pack = StartPackHelper.getPack();
                for (AbstractCard c : pack) {
                    group.addToTop(c);
                }
                AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, CardCrawlGame.languagePack.getUIString("shadowverse:StartPack").TEXT[1]);
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
            if (category == 2) {
                __result.add(new NeowReward.NeowRewardDef(STARTPACK, CardCrawlGame.languagePack.getUIString("shadowverse:StartPack").TEXT[0]));
            }
            return __result;
        }
    }
}
