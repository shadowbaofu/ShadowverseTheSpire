package shadowverse.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowverse.characters.AbstractShadowversePlayer;

import java.util.ArrayList;
import java.util.Collections;

public class CeridwenAction extends AbstractGameAction {
    public static final float DURATION = Settings.ACTION_DUR_MED;
    private ArrayList<AbstractCard> list = new ArrayList<AbstractCard>();
    private ArrayList<Integer> costtempCard = new ArrayList<>();
    private ArrayList<AbstractCard> finalList = new ArrayList<>();
    private AbstractPlayer p = AbstractDungeon.player;

    public CeridwenAction(int amount) {
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = DURATION;
    }


    @Override
    public void update() {
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.type == AbstractCard.CardType.ATTACK && c.cost <= this.amount) {
                AbstractCard tempCard = c.makeSameInstanceOf();
                tempCard.resetAttributes();
                list.add(tempCard);
            }
        }
        if (list != null && list.size() != 0) {
            for (AbstractCard c : list) {
                costtempCard.add(c.cost);
            }
            int max = Collections.max(costtempCard);
            for (AbstractCard finalCard : list) {
                if (finalCard.cost == max) {
                    if (finalList.size()>0)
                        continue;
                    finalList.add(finalCard);
                }
            }
            Collections.shuffle(finalList);
            AbstractCard tempCard = finalList.get(0);
            if (tempCard.costForTurn > 0) {
                tempCard.costForTurn = 0;
                tempCard.isCostModifiedForTurn = true;
            }
            tempCard.baseDamage = 0;
            tempCard.baseBlock = 0;
            tempCard.exhaustOnUseOnce = true;
            tempCard.exhaust = true;
            tempCard.isEthereal = true;
            tempCard.initializeDescription();
            tempCard.applyPowers();
            tempCard.lighten(true);
            tempCard.setAngle(0.0F);
            tempCard.drawScale = 0.12F;
            tempCard.targetDrawScale = 0.75F;
            tempCard.current_x = Settings.WIDTH / 2.0F;
            tempCard.current_y = Settings.HEIGHT / 2.0F;
            p.hand.addToTop(tempCard);
        }
        p.hand.refreshHandLayout();
        p.hand.applyPowers();
        this.isDone = true;
    }
}
