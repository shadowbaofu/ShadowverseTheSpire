package shadowverse.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import shadowverse.characters.AbstractShadowversePlayer;

import java.util.ArrayList;
import java.util.Collections;

public class ArmedReanimateAction extends AbstractGameAction {
    public static final float DURATION = Settings.ACTION_DUR_MED;
    private ArrayList<AbstractCard> list = new ArrayList<AbstractCard>();
    private ArrayList<Integer> costTmp = new ArrayList<>();
    private ArrayList<AbstractCard> finalList = new ArrayList<>();
    private AbstractPlayer p = AbstractDungeon.player;

    private static final String TEXT = CardCrawlGame.languagePack.getUIString("shadowverse:Retain").TEXT[0];
    private static final String TEXT2 = CardCrawlGame.languagePack.getUIString("shadowverse:Exhaust").TEXT[0];

    public ArmedReanimateAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = DURATION;
    }


    @Override
    public void update() {
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (CardLibrary.getCard(c.cardID) != null && CardLibrary.getCard(c.cardID).type == AbstractCard.CardType.ATTACK && c.hasTag(AbstractShadowversePlayer.Enums.ARMED)) {
                AbstractCard tmp = c.makeSameInstanceOf();
                tmp.resetAttributes();
                list.add(tmp);
            }
        }
        if (list != null && list.size() != 0) {
            for (AbstractCard c : list) {
                costTmp.add(c.cost);
            }
            int max = Collections.max(costTmp);
            for (AbstractCard finalCard : list) {
                if (finalCard.cost == max) {
                    finalList.add(finalCard);
                }
            }
            Collections.shuffle(finalList);
            AbstractCard tempCard = finalList.get(0).makeStatEquivalentCopy();
            tempCard.retain= true;
            tempCard.selfRetain = true;
            tempCard.rawDescription += " NL "+TEXT2;
            tempCard.exhaustOnUseOnce = true;
            tempCard.exhaust = true;
            tempCard.rawDescription += " NL " + TEXT;
            tempCard.initializeDescription();
            tempCard.applyPowers();
            AbstractDungeon.player.hand.addToTop(tempCard);
        }
        p.hand.refreshHandLayout();
        p.hand.applyPowers();
        this.isDone = true;
    }
}
