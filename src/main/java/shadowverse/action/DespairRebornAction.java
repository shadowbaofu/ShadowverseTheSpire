package shadowverse.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;
import java.util.Collections;

public class DespairRebornAction extends AbstractGameAction {
    public static final float DURATION = Settings.ACTION_DUR_MED;
    private ArrayList<AbstractCard> list = new ArrayList<AbstractCard>();
    private ArrayList<String> tmp = new ArrayList<String>();
    private AbstractPlayer p = AbstractDungeon.player;

    private boolean upgrade;

    public DespairRebornAction(boolean upgrade){
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = DURATION;
        this.upgrade = upgrade;
    }


    @Override
    public void update() {
        for (AbstractCard c: AbstractDungeon.commonCardPool.group){
            if (!c.isLocked&&(c.type == AbstractCard.CardType.ATTACK||c.type == AbstractCard.CardType.POWER) && CardLibrary.getCard(c.cardID).cost>2){
                if (tmp.contains(c.cardID)) {
                    continue;
                }
                tmp.add(c.cardID);
                list.add(c);
            }
        }
        for (AbstractCard c: AbstractDungeon.uncommonCardPool.group){
            if (!c.isLocked&&(c.type == AbstractCard.CardType.ATTACK||c.type == AbstractCard.CardType.POWER) && CardLibrary.getCard(c.cardID).cost>2){
                if (tmp.contains(c.cardID)) {
                    continue;
                }
                tmp.add(c.cardID);
                list.add(c);
            }
        }
        for (AbstractCard c: AbstractDungeon.rareCardPool.group){
            if (!c.isLocked&&(c.type == AbstractCard.CardType.ATTACK||c.type == AbstractCard.CardType.POWER) && CardLibrary.getCard(c.cardID).cost>2){
                if (tmp.contains(c.cardID)) {
                    continue;
                }
                tmp.add(c.cardID);
                list.add(c);
            }
        }
        Collections.shuffle(list);
        for (int i =0;i<4;i++){
            if (list.size()>i){
                AbstractCard tempCard = list.get(i).makeSameInstanceOf();
                if (tempCard.costForTurn > 0) {
                    tempCard.cost = 0;
                    tempCard.costForTurn = 0;
                    tempCard.freeToPlayOnce = true;
                    tempCard.isCostModifiedForTurn = true;
                }
                if (upgrade){
                    tempCard.upgrade();
                }
                tempCard.exhaustOnUseOnce = true;
                tempCard.exhaust = true;
                tempCard.isEthereal = true;
                tempCard.applyPowers();
                tempCard.lighten(true);
                tempCard.setAngle(0.0F);
                tempCard.drawScale = 0.12F;
                tempCard.targetDrawScale = 0.75F;
                tempCard.current_x = Settings.WIDTH / 2.0F;
                tempCard.current_y = Settings.HEIGHT / 2.0F;
                p.hand.addToTop(tempCard);
            }
        }
        p.hand.refreshHandLayout();
        p.hand.applyPowers();
        this.isDone = true;
    }
}
