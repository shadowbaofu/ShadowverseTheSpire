package shadowverse.cards.Dragon.Default;


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.MiracleEffect;
import shadowverse.cards.Neutral.Temp.OtohimeVanguard;
import shadowverse.cards.AbstractAccelerateCard;
import shadowverse.characters.Dragon;


public class DragonEmpressOtohime extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:DragonEmpressOtohime";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:DragonEmpressOtohime");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/DragonEmpressOtohime.png";

    public DragonEmpressOtohime() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.RARE, CardTarget.SELF, 0, CardType.SKILL);
        this.baseBlock = 12;
        this.cardsToPreview = new OtohimeVanguard();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(4);
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("DragonEmpressOtohime"));
        addToBot(new VFXAction(new BorderFlashEffect(Color.BLUE, true), 0.7f));
        addToBot(new VFXAction(new MiracleEffect(Color.LIGHT_GRAY.cpy(), Color.SKY.cpy(), "HEAL_3")));
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), 4));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("DragonEmpressOtohime_Acc"));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), 1));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new DragonEmpressOtohime();
    }
}

