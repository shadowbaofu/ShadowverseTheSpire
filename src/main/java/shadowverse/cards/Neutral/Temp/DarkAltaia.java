package shadowverse.cards.Neutral.Temp;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.Neutral.Neutral.Altaia;
import shadowverse.characters.AbstractShadowversePlayer;

public class DarkAltaia extends CustomCard {
    public static final String ID = "shadowverse:DarkAltaia";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/DarkAltaia.png";


    public DarkAltaia() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.baseDamage = 6;
        this.tags.add(AbstractShadowversePlayer.Enums.DARK_ANGEL);
        this.cardsToPreview = new Altaia();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
            this.cardsToPreview.upgrade();
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
        addToBot(new DrawCardAction(2));
    }


    @Override
    public AbstractCard makeCopy() {
        return new DarkAltaia();
    }
}


