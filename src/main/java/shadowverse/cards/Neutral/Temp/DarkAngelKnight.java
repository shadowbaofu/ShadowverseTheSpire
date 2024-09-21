package shadowverse.cards.Neutral.Temp;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import shadowverse.cards.Neutral.Neutral.AngelKnight;

public class DarkAngelKnight extends CustomCard {
    public static final String ID = "shadowverse:DarkAngelKnight";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/DarkAngelKnight.png";
    public boolean triggered;


    public DarkAngelKnight() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
        this.baseBlock = 5;
        this.cardsToPreview = new AngelKnight();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
            this.cardsToPreview.upgrade();
        }
    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("AngelKnight"));
        addToBot(new GainBlockAction(abstractPlayer, this.block));
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new StrengthPower(abstractPlayer,1),1));
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new DexterityPower(abstractPlayer,1),1));
    }


    @Override
    public AbstractCard makeCopy() {
        return new DarkAngelKnight();
    }
}

