package shadowverse.cards.Elf.Wood;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.Shadowverse;
import shadowverse.action.InvocationAction;
import shadowverse.cards.Neutral.Temp.Lymaga_NoAcc;
import shadowverse.cards.Witch.AbstractAccelerateCard;
import shadowverse.characters.Elf;


public class Lymaga
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:Lymaga";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Lymaga");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Lymaga.png";
    public static boolean dupCheck = true;

    public Lymaga() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.ATTACK, Elf.Enums.COLOR_GREEN, CardRarity.RARE, CardTarget.ENEMY,0,CardType.SKILL);
        this.baseDamage = 32;
        this.cardsToPreview = new GreenWoodGuardian();
        this.exhaust = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(10);
        }
    }


    public void triggerOnOtherCardPlayed(AbstractCard c) {
        dupCheck = true;
    }


    public void applyPowers() {
        super.applyPowers();
        int count = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c instanceof GreenWoodGuardian)
                count++;
        }
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + count;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }


    public void atTurnStart() {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c instanceof GreenWoodGuardian)
                count++;
        }
        if (count >= 6 && dupCheck) {
            dupCheck = false;
            if (AbstractDungeon.player.discardPile.contains(this)) {
                addToBot(new ReduceCostForTurnAction(this, 9));
                addToBot(new DiscardToHandAction(this));
            } else if (AbstractDungeon.player.drawPile.contains(this)) {
                addToBot(new ReduceCostForTurnAction(this, 9));
                addToBot(new InvocationAction(this));
            }
        } else if (count < 6) {
            dupCheck = true;
        }
    }

    @Override
    public void triggerOnCardPlayed(AbstractCard cardPlayed) {
        dupCheck = true;
    }


    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("Lymaga"));
        addToBot(new WaitAction(0.8F));
        addToBot(new SFXAction("ATTACK_HEAVY"));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("Lymaga_Acc"));
        AbstractCard c = this.cardsToPreview.makeStatEquivalentCopy();
        AbstractCard a = new Lymaga_NoAcc();
        if (this.upgraded)
            a.upgrade();
        addToBot(new MakeTempCardInHandAction(c, 1));
        addToBot(new MakeTempCardInDiscardAction(a, 1));
    }

    public AbstractCard makeCopy() {
        return new Lymaga();
    }
}


