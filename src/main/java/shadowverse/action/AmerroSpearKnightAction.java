package shadowverse.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import shadowverse.cards.Bishop.Ward.Wilbert;
import shadowverse.cards.Dragon.Default.Georgius;
import shadowverse.cards.Elf.Long.Hero;
import shadowverse.cards.Nemesis.Condemned.Judith;
import shadowverse.cards.Nemesis.Tokens.Maisha;
import shadowverse.cards.Royal.Hero.*;
import shadowverse.cards.Royal.Levin.Albert;
import shadowverse.cards.Royal.NatMech.Johann;
import shadowverse.cards.Witch.Natura.Maiser;

import java.util.ArrayList;
import java.util.Iterator;

public class AmerroSpearKnightAction extends AbstractGameAction {
    private boolean retrieveCard = false;
    private boolean upgraded;

    public AmerroSpearKnightAction(boolean upgraded) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgraded = upgraded;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(this.generateCardChoices(), CardRewardScreen.TEXT[1], true);
            this.tickDuration();
        } else {
            if (!this.retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                    if (this.upgraded) {
                        disCard.costForTurn -= 1;
                        disCard.isCostModified = true;
                    }
                    disCard.current_x = -1000.0F * Settings.xScale;
                    if (AbstractDungeon.player.hand.size() < 10) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    }

                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }

                this.retrieveCard = true;
            }

            this.tickDuration();
        }
    }

    private ArrayList<AbstractCard> generateCardChoices() {
        ArrayList<AbstractCard> derp = new ArrayList();
        AbstractCard[] heros = {
                new Alexander(),
                new AmerroSpearKnight(),
                new Arthur(),
                new FlameSoldier(),
                new HeroicEntry(),
                new HeroOfAntiquity(),
                new Icyclone(),
                new IronwroughtDefender(),
                new MachKnight(),
                new ValiantFencer(),
                new Windslasher(),
        };
        AbstractCard[] heros1 = {
                new Johann(),
                new Albert(),
                new Judith(),
                new Hero(),
                new Maiser(),
                new Georgius(),
                new Wilbert(),
                new Maisha(),
        };
        while (derp.size() != 2) {
            boolean dupe = false;
            int roll = AbstractDungeon.cardRandomRng.random(heros.length - 1);
            AbstractCard tmp = heros[roll];
            Iterator var5 = derp.iterator();
            while (var5.hasNext()) {
                AbstractCard c = (AbstractCard) var5.next();
                if (c.cardID.equals(tmp.cardID)) {
                    dupe = true;
                    break;
                }
            }

            if (!dupe) {
                derp.add(tmp.makeCopy());
            }
        }
        int roll = AbstractDungeon.cardRandomRng.random(heros1.length - 1);
        AbstractCard tmp = heros[roll];
        derp.add(tmp.makeCopy());
        return derp;
    }
}
