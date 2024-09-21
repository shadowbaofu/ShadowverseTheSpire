package shadowverse.cards.Neutral.Temp;

import basemod.abstracts.CustomCard;
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

public class SeraphOfVirtue extends CustomCard {
    public static final String ID = "shadowverse:SeraphOfVirtue";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/SeraphOfVirtue.png";


    public SeraphOfVirtue() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.baseDamage = 8;
        this.cardsToPreview = new ReturnToHeaven();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeDamage(3);
        }
    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("SeraphOfSin"));
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()));
    }


    @Override
    public AbstractCard makeCopy() {
        return new SeraphOfVirtue();
    }
}


