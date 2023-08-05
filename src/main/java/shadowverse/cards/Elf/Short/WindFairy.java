package shadowverse.cards.Elf.Short;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowverse.action.BounceAction;
import shadowverse.cards.Neutral.Temp.Whisp;
import shadowverse.cards.Witch.AbstractAccelerateCard;
import shadowverse.characters.Elf;


public class WindFairy
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:WindFairy";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:WindFairy");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/WindFairy.png";

    public WindFairy() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.ATTACK, Elf.Enums.COLOR_GREEN, CardRarity.COMMON, CardTarget.ENEMY, 0, CardType.SKILL);
        this.baseDamage = 12;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = new Whisp();
        this.exhaust = true;
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(4);
        }
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse)
            return false;
        boolean hasAttack = false;
        if (EnergyPanel.getCurrentEnergy() < baseCost && this.type == accType) {
            for (AbstractCard c : p.discardPile.group) {
                if (c.type == AbstractCard.CardType.ATTACK || c.type == CardType.POWER)
                    hasAttack = true;
            }
            if (!hasAttack) {
                this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
                canUse = false;
            }
        }
        return canUse;
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("WindFairy"));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new BounceAction(1));
        addToBot(new SFXAction("WindFairy_Accelerate"));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), 1));
    }


    public AbstractCard makeCopy() {
        return new WindFairy();
    }
}

