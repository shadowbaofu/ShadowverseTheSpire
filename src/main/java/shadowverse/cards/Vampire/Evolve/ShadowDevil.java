package shadowverse.cards.Vampire.Evolve;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.Neutral.Status.EvolutionPoint;
import shadowverse.characters.Vampire;
import shadowverse.powers.EpitaphPower;
import shadowverse.stance.Vengeance;


public class ShadowDevil
        extends CustomCard {
    public static final String ID = "shadowverse:ShadowDevil";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:ShadowDevil");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/ShadowDevil.png";

    public ShadowDevil() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Vampire.Enums.COLOR_SCARLET, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = 12;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }


    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("ShadowDevil"));
        addToBot(new GainBlockAction(p, this.block));
        if (p.hasPower(EpitaphPower.POWER_ID) || p.stance.ID.equals(Vengeance.STANCE_ID)) {
            addToBot(new MakeTempCardInHandAction(new EvolutionPoint(), 2));
            addToBot(new MakeTempCardInHandAction(new Miracle(), 2));
        }
    }


    public AbstractCard makeCopy() {
        return new ShadowDevil();
    }
}

