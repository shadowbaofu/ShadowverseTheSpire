package shadowverse.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.ReaperEffect;
import shadowverse.characters.AbstractShadowversePlayer;


public class RunieAction
        extends AbstractGameAction {
    private AbstractPlayer abstractPlayer = AbstractDungeon.player;
    private AbstractCard self;

    public RunieAction(int amount, AbstractCard self) {
        this.amount = amount;
        this.self = self;
    }


    public void update() {
        for (AbstractCard c : abstractPlayer.hand.group) {
            if (c.hasTag(AbstractShadowversePlayer.Enums.SPELL_BOOST) && c != self) {
                c.flash();
                addToBot(new SFXAction("spell_boost"));
                addToBot(new ReduceCostAction(c));
            }
            if (c.hasTag(AbstractShadowversePlayer.Enums.SPELL_BOOST_ATTACK) && c != self) {
                c.flash();
                c.magicNumber = ++c.baseMagicNumber;
                addToBot(new SFXAction("spell_boost"));
            }
        }
        if (this.amount >= 1) {
            addToBot(new DrawCardAction(abstractPlayer, 1));
        }
        if (this.amount >= 3) {
            addToBot(new AttackDamageRandomEnemyAction(self, AbstractGameAction.AttackEffect.LIGHTNING));
        }
        if (this.amount >= 5) {
            addToBot(new VFXAction(new ReaperEffect()));
            addToBot(new AttackDamageRandomEnemyAction(self, AbstractGameAction.AttackEffect.NONE));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(abstractPlayer, abstractPlayer, self.block));
            addToBot(new HealAction(abstractPlayer, abstractPlayer, 3));
            addToTop(new WaitAction(Settings.ACTION_DUR_MED));
        }
        if (this.amount >= 7) {
            self.resetAttributes();
            addToBot(new MakeTempCardInHandAction(self.makeStatEquivalentCopy(), 3));
        }
        this.isDone = true;
    }
}

