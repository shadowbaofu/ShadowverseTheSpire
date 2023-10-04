package shadowverse.cards.Neutral.Temp;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.characters.Elf;



public class SoaringSpirit extends CustomCard {
    public static final String ID = "shadowverse:SoaringSpirit";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:SoaringSpirit");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/SoaringSpirit.png";

    public SoaringSpirit() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.ATTACK, Elf.Enums.COLOR_GREEN, CardRarity.SPECIAL, CardTarget.SELF);
        this.baseBlock = 12;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(4);
        }
    }


    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("SoaringSpirit"));
        addToBot(new GainBlockAction(p,this.block));
        for (AbstractCard c : p.hand.group) {
            if (c.color == Elf.Enums.COLOR_GREEN && (CardLibrary.getCard(c.cardID) != null && c!=this)) {
                if (c.cost>0){
                    c.freeToPlayOnce = true;
                }
                c.flash();
            }
        }
        for (AbstractCard c : p.drawPile.group) {
            if (c.color == Elf.Enums.COLOR_GREEN && (CardLibrary.getCard(c.cardID) != null && c!=this)) {
                if (c.cost>0){
                    c.freeToPlayOnce = true;
                }
                c.flash();
            }
        }
        for (AbstractCard c : p.discardPile.group) {
            if (c.color == Elf.Enums.COLOR_GREEN && (CardLibrary.getCard(c.cardID) != null && c!=this)) {
                if (c.cost>0){
                    c.freeToPlayOnce = true;
                }
                c.flash();
            }
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new SoaringSpirit();
    }
}
