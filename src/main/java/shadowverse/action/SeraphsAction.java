package shadowverse.action;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import shadowverse.cardmods.SeraphsMod;
import shadowverse.cards.AbstractAmuletCard;
import shadowverse.cards.AbstractCrystalizeCard;
import shadowverse.cards.AbstractNoCountDownAmulet;

import java.util.ArrayList;
import java.util.Collections;

public class SeraphsAction extends AbstractGameAction {
    public static final float DURATION = Settings.ACTION_DUR_MED;
    private ArrayList<AbstractCard> list = new ArrayList<AbstractCard>();
    private ArrayList<String> box = new ArrayList<String>();
    private AbstractPlayer p = AbstractDungeon.player;


    public SeraphsAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = DURATION;
    }


    @Override
    public void update() {
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (CardLibrary.getCard(c.cardID) != null && ((c instanceof AbstractAmuletCard && !(c instanceof AbstractNoCountDownAmulet)) || c instanceof AbstractCrystalizeCard)) {
                if (!box.contains(c.cardID)) {
                    box.add(c.cardID);
                    AbstractCard tmp = c.makeSameInstanceOf();
                    tmp.resetAttributes();
                    CardModifierManager.addModifier(tmp, new SeraphsMod());
                    list.add(tmp);
                }
            }
        }
        if (list != null && list.size() != 0) {
            Collections.shuffle(list);
            for (int i = 0; i < p.maxOrbs; i++) {
                if (list.size() > i){
                    addToBot(new PlaceAmulet(list.get(i), null));
                }
            }
        }
        this.isDone = true;
    }
}
