package shadowverse.cards.Dragon.Tempo;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.Neutral.Temp.TheTower;
import shadowverse.cards.Neutral.Temp.TheTower_I;
import shadowverse.characters.Dragon;

import java.util.ArrayList;


public class SlaughteringDragonewt extends CustomCard {
    public static final String ID = "shadowverse:SlaughteringDragonewt";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:SlaughteringDragonewt");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/TheTower.png";

    public SlaughteringDragonewt() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, AbstractCard.CardType.ATTACK, Dragon.Enums.COLOR_BROWN, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.NONE);
        this.baseBlock = 8;
        this.baseDamage = 16;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = new Burn();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
            upgradeDamage(6);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
        AbstractCard a = new TheTower();
        AbstractCard b = new TheTower_I();
        if (this.upgraded){
            a.upgrade();
            b.upgrade();
        }
        stanceChoices.add(a);
        stanceChoices.add(b);
        addToBot(new ChooseOneAction(stanceChoices));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new SlaughteringDragonewt();
    }
}

