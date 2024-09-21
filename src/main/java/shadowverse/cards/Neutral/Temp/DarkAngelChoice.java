package shadowverse.cards.Neutral.Temp;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.action.DarkAngelAction;
import shadowverse.characters.Vampire;

public class DarkAngelChoice extends CustomCard {
    public static final String ID = "shadowverse:DarkAngelChoice";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:DarkAngelChoice");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/WingedInversion.png";

    public DarkAngelChoice() {
        super(ID, NAME, IMG_PATH, -2, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            initializeDescription();
        }
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        onChoseThisOption();
    }

    public void onChoseThisOption() {
        addToBot(new DarkAngelAction(this.upgraded));
    }

    public AbstractCard makeCopy() {
        return new DarkAngelChoice();
    }
}
