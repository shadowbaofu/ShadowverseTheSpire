package shadowverse.cards.Neutral.Neutral;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Altaia extends CustomCard {
    public static final String ID = "shadowverse:Altaia";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Altaia.png";


    public Altaia() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.BASIC, CardTarget.ENEMY);
        this.baseDamage = 6;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
        }
    }

    public void applyPowers() {
        super.applyPowers();
        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.color == CardColor.COLORLESS)
                count++;
        }
        int realBaseDamage = this.baseDamage;
        this.baseDamage += count * this.baseDamage;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.color == CardColor.COLORLESS)
                count++;
        }
        int realBaseDamage = this.baseDamage;
        this.baseDamage += count * this.baseDamage;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("Altaia"));
        calculateCardDamage(abstractMonster);
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new SelectCardsInHandAction(1, TEXT[0], false, true, card -> card.type == CardType.ATTACK
                && card.color != CardColor.COLORLESS, abstractCards -> {
            for (AbstractCard c : abstractCards) {
                c.color = CardColor.COLORLESS;
                c.applyPowers();
            }
        }));
    }


    @Override
    public AbstractCard makeCopy() {
        return new Altaia();
    }
}


