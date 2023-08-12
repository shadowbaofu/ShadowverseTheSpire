package shadowverse.cards.Bishop.Default;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.HeartBuffEffect;
import shadowverse.action.PlaceAmulet;
import shadowverse.cards.Neutral.Temp.SomnolentStrength;
import shadowverse.cards.AbstractAccelerateCard;
import shadowverse.characters.Bishop;
import shadowverse.powers.TheStrengthPower;

public class TheStrength
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:TheStrength";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:TheStrength");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/TheStrength.png";

    public TheStrength() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.POWER, Bishop.Enums.COLOR_WHITE, CardRarity.RARE, CardTarget.SELF, 1, CardType.SKILL);
        this.cardsToPreview = new SomnolentStrength();
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }


    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("TheStrength"));
        addToBot(new VFXAction(new HeartBuffEffect(p.hb.cX, p.hb.cY)));
        addToBot(new VFXAction(new BorderFlashEffect(Color.GOLDENROD, true)));
        addToBot(new ApplyPowerAction(p, p, new TheStrengthPower(p)));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("TheStrength_Acc"));
        addToBot(new PlaceAmulet(this.cardsToPreview.makeStatEquivalentCopy(), null));
    }

    public AbstractCard makeCopy() {
        return (AbstractCard) new TheStrength();
    }
}


