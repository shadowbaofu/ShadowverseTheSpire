package shadowverse.cards.Nemesis.Artifact;


import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.action.DestroyAction;
import shadowverse.cards.Neutral.Temp.ParadigmShift;
import shadowverse.cards.Witch.AbstractAccelerateCard;
import shadowverse.characters.Nemesis;
import shadowverse.powers.MiriamPower;
import shadowverse.stance.Resonance;


public class Miriam extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:Miriam";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Miriam");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Miriam.png";

    public Miriam() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.UNCOMMON, CardTarget.SELF, 0, CardType.SKILL);
        this.baseBlock = 12;
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
            upgradeMagicNumber(2);
        }
    }


    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("Miriam"));
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new ApplyPowerAction(p, p, new MiriamPower(p, this.magicNumber), this.magicNumber));
        if (p.stance.ID.equals(Resonance.STANCE_ID)) {
            addToBot(new GainEnergyAction(1));
        }
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("Miriam_Acc"));
        addToBot(new DestroyAction(1, new MakeTempCardInHandAction(new ParadigmShift())));
    }

    public AbstractCard makeCopy() {
        return (AbstractCard) new Miriam();
    }
}
